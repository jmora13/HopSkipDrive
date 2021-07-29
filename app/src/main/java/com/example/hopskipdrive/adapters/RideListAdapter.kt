package com.example.hopskipdrive.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hopskipdrive.R
import com.example.hopskipdrive.RideDetailsActivity
import com.example.hopskipdrive.model.OrderedWaypoint
import com.example.hopskipdrive.model.Ride
import com.google.android.material.card.MaterialCardView
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RideListAdapter: ListAdapter<Ride, RideListAdapter.RideViewHolder>(RideComparator()), Serializable {

    //HASHMAP FOR <DATE, TOTAL EARNINGS>
    var dailyEstimatesMap = mutableMapOf<String,Int>()

    //HASHMAP FOR <DATE, LIST<STARTSAT, ENDSAT> DRIVE DURATION
    var dailyDriveDuration = mutableMapOf<String,List<String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        dailyEstimatesMap = getDailyEstimate(itemCount, currentList)
        dailyDriveDuration = getDailyDriveDuration(itemCount, currentList)
        return RideViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        val startSeparatorTime: TextView = holder.itemView.findViewById(R.id.startSeparatorTime)
        val endSeparatorTime: TextView = holder.itemView.findViewById(R.id.endSeparatorTime)
        val separatorView: MaterialCardView = holder.itemView.findViewById(R.id.separatorView)
        val separatorDate: TextView = holder.itemView.findViewById(R.id.separatorDate)
        val separatorEstimate: TextView = holder.itemView.findViewById(R.id.separatorEstimate)
        var date = parseDate(current.startsAt)

        //IF THE POSITION IS THE FIRST ITEM, OR A NEW DATE IS FOUND, SHOW SEPARATOR VIEW
        if(position == 0 || date != parseDate(getItem(position-1).startsAt)){
            initializeSeparator(separatorEstimate, separatorDate, startSeparatorTime, endSeparatorTime, separatorView, holder, current)
        }

        //SEND DATA TO RIDE DETAILS ACTIVITY
        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, RideDetailsActivity::class.java).apply{
                putExtra("locationsMap", getAddressesFromWayPoints(current.orderedWaypoints))
                putExtra("parsedDate", parseDate(current.startsAt))
                putExtra("startsAt", current.startsAt)
                putExtra("startTime", holder.parseTime(current.startsAt))
                putExtra("endTime", holder.parseTime(current.endsAt))
                putExtra("estimate", "$${String.format("%.2f", current.estimatedEarningsCents.toDouble() / 100)}")
                putExtra("id", current.tripId.toString())
                putExtra("inSeries", current.inSeries)
                putExtra("distance", current.estimatedRideMiles.toString())
                putExtra("duration", current.estimatedRideMinutes.toString())
            }
            holder.itemView.context.startActivity(i)
        }

    }

    //ITERATE THROUGH LIST AND GET ADDRESSES AND RETURN AS A HASHMAP
    private fun getAddressesFromWayPoints(waypointList: List<OrderedWaypoint>): Serializable{
        var map = mutableMapOf<String, List<Double>>()
        for(i in waypointList.indices){
            map.put(waypointList[i].location.address, listOf(waypointList[i].location.lat,waypointList[i].location.lng))
        }
        return map as Serializable
    }

    //SEPARATE FUNCTION FOR SEPARATOR VIEW
    fun initializeSeparator(estimate: TextView, separatorDate: TextView, startSeparatorTime: TextView, endSeparatorTime: TextView, view: MaterialCardView, holder: RideViewHolder, current: Ride){
        var date = parseDate(current.startsAt)
        separatorDate.text = date
        estimate.text = "$${String.format("%.2f", dailyEstimatesMap.get(date)!!.toDouble() / 100)}"
        startSeparatorTime.text = "${holder.parseTime(dailyDriveDuration.get(date)!![0])}"
        endSeparatorTime.text = " - ${holder.parseTime(dailyDriveDuration.get(date)!![1])}"
        view.visibility = View.VISIBLE
    }

    //ITERATE THROUGH LIST AND GET TOTAL EARNINGS AND RETURN AS A HASHMAP
    private fun getDailyEstimate(itemCount: Int, currentList: List<Ride>): MutableMap<String,Int>{
        var map = mutableMapOf<String,Int>()
        for(i in 0 until itemCount){
            var date = parseDate(currentList[i].startsAt)
            if(!map.contains(date)){
                map.put(date!!, currentList[i].estimatedEarningsCents )
            } else {
                map.put(date!! , map.get(date)!! + currentList[i].estimatedEarningsCents)
            }
        }
        return map
    }

    //ITERATE THROUGH LIST AND GET THE DATE : STARTSAT/ENDS AT TIME IN A LIST
    private fun getDailyDriveDuration(itemCount: Int, currentList: List<Ride>): MutableMap<String,List<String>>{
        var map = mutableMapOf<String,List<String>>()
        for(i in 0 until itemCount){
            var date = parseDate(currentList[i].startsAt)
            if(!map.contains(date)){
                map.put(date!!, listOf(currentList[i].startsAt, currentList[i].endsAt))
            } else {
                if(map.get(date)!![1] < currentList[i].endsAt) {
                    map.put(date!!, listOf(map.get(date)!![0], currentList[i].endsAt))
                }
            }
        }
        return map
    }

    //PARSE DATE FROM ISO 8601
    private fun parseDate(dateTime: String?): String?{ //TURNS INTO GREGORIAN CALENDAR FORMAT
        var formatter = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = formatter.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        formatter = SimpleDateFormat("E M/d")
        return formatter.format(date)
    }

    class RideViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val rideTimeStart: TextView = itemView.findViewById(R.id.rideTimeStart)
        private val rideTimeEnd: TextView = itemView.findViewById(R.id.rideTimeEnd)
        private val riderCount: TextView = itemView.findViewById(R.id.riderCount)
        private val rideEstimation: TextView = itemView.findViewById(R.id.rideEstimation)
        private val waypointRecyclerView: RecyclerView = itemView.findViewById(R.id.waypoint_recycler_view)

        fun bind(rideItem: Ride?){
            rideTimeStart.text = "${parseTime(rideItem!!.startsAt)}"
            rideTimeEnd.text = "— ${parseTime(rideItem!!.endsAt)}"

            //GETTING NUM RIDES AND PLURALIZING AS NECESSARY
            val uniqueRiderCount = getUniqueRiderCount(rideItem)
            if(uniqueRiderCount[0] == 1 && uniqueRiderCount[1] == 1) {
                riderCount.text = ("(${uniqueRiderCount[0]} rider · ${uniqueRiderCount[1]} booster)")
            } else if(uniqueRiderCount[0] > 1 && uniqueRiderCount[1] == 1){
                riderCount.text = ("(${uniqueRiderCount[0]} riders · ${uniqueRiderCount[1]} booster)")
            } else if(uniqueRiderCount[0] > 1 && uniqueRiderCount[1] > 1){
                riderCount.text = ("(${uniqueRiderCount[0]} riders · ${uniqueRiderCount[1]} boosters)")
            } else if(uniqueRiderCount[0] > 1 && uniqueRiderCount[1] == 0){
                riderCount.text = ("(${uniqueRiderCount[0]}) riders)")
            } else {
                riderCount.text = ("(${uniqueRiderCount[0]} rider)")
            }

            //GET EARNINGS ESTIMATE FOR THE RIDE
            rideEstimation.text = " $${String.format("%.2f",rideItem.estimatedEarningsCents.toDouble() / 100)}"

            //RECYCLERVEW FOR ADDRESS ITEMS
            waypointRecyclerView.apply {
                val orderedWaypointListAdapter = OrderedWaypointListAdapter(rideItem.orderedWaypoints)
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL,false)
                waypointRecyclerView.adapter = orderedWaypointListAdapter
                orderedWaypointListAdapter.submitList(rideItem.orderedWaypoints)
            }

        }

        //GET UNIQUE COUNT OF RIDERS AND BOOSTERS PER TRIP
        private fun getUniqueRiderCount(rideItem: Ride?): List<Int>{
            var list = mutableListOf<Int>()
            var boosterCount = 0
            for(i in rideItem!!.orderedWaypoints.indices){
                for(j in rideItem!!.orderedWaypoints[i].passengers.indices){
                    list.add(rideItem!!.orderedWaypoints[i].passengers[j].id)
                    if(rideItem!!.orderedWaypoints[i].passengers[j].boosterSeat == true){
                        boosterCount++
                    }
                }
            }
            return listOf(list.distinct().size, boosterCount)
        }

        //CONVERTS ISO 8601 TO 12H FORMAT
        fun parseTime(dateTime: String?): String{
            var time = dateTime?.split("T")
            var hour = time!![1].substring(0, 2).toInt()
            if(hour in 1..11){ //AM TIME
                if(hour in 1..9){
                    return time!![1].substring(1, 5) + "a " //REMOVE LEADING ZEROS FOR AM
                }
                return time!![1].substring(0, 5) + "a "
            } else if(hour >= 12){ //PM TIME
                if(hour == 12){
                    return "${hour}:" + time!![1].substring(3, 5) + "p " //IF 12 PM, DONT CHANGE HOUR
                } else {
                    return "${hour - 12}:" + time!![1].substring(3, 5) + "p " //OTHERWISE SUBTRACT 12
                }
            }
            return "12:" + time!![1].substring(3, 5) + "a " //FOR 12AM
        }


        companion object{
            fun create(parent:ViewGroup): RideViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ride_item, parent, false)
                return RideViewHolder(view)
            }
        }
    }

    class RideComparator : DiffUtil.ItemCallback<Ride>() {
        override fun areItemsTheSame(oldItem: Ride, newItem: Ride): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Ride, newItem: Ride): Boolean {
            return oldItem.tripId == newItem.tripId
        }

    }

}