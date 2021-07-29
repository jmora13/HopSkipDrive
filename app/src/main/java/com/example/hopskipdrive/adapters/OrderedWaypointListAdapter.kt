package com.example.hopskipdrive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hopskipdrive.R
import com.example.hopskipdrive.model.OrderedWaypoint

open class OrderedWaypointListAdapter(orderedWaypoints: List<OrderedWaypoint>) : ListAdapter<OrderedWaypoint, OrderedWaypointListAdapter.OrderedWaypointViewHolder>(
    OrderedWaypointComparator()
) {

    private var waypointList: List<OrderedWaypoint> = ArrayList()

    init {
        this.waypointList = orderedWaypoints
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderedWaypointViewHolder {
        return OrderedWaypointViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: OrderedWaypointViewHolder, position: Int) {
        var current = waypointList[position]
        holder.bind(current)
    }


    class OrderedWaypointViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val waypointTextView: TextView = itemView.findViewById(R.id.waypoint)

        //BIND INDIVIDUAL ADDRESS ITEMS
        fun bind(waypoint: OrderedWaypoint?){
           waypointTextView.text = "${bindingAdapterPosition+1}. ${waypoint!!.location.address}"
        }


        companion object{
            fun create(parent: ViewGroup): OrderedWaypointViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.waypoint_item, parent, false)
                return OrderedWaypointViewHolder(view)
            }
        }
    }


    class OrderedWaypointComparator : DiffUtil.ItemCallback<OrderedWaypoint>() {
        override fun areItemsTheSame(oldItem: OrderedWaypoint, newItem: OrderedWaypoint): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: OrderedWaypoint, newItem: OrderedWaypoint): Boolean {
            return oldItem.id == newItem.id
        }

    }

}