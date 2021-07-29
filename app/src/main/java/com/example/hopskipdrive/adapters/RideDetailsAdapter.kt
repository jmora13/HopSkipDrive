package com.example.hopskipdrive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hopskipdrive.R

class RideDetailsAdapter() : ListAdapter<String, RideDetailsAdapter.RideDetailsViewHolder>(
    RideDetailsComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideDetailsViewHolder {
        return RideDetailsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RideDetailsViewHolder, position: Int) {
        val current = getItem(position)
        //IF NOT THE LAST ITEM, INITIALIZE CARD VIEW AS A PICKUP, ELSE INIT AS A A DROPOFF
        if(position != itemCount-1) {
            holder.bind(current)
        } else{
            holder.bindDropOff(current)
        }
    }

    class RideDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val pickupTextView: TextView = itemView.findViewById(R.id.pickupTextView)
        private val shape: ImageView = itemView.findViewById(R.id.shape)
        private val address: TextView = itemView.findViewById(R.id.address)

        //BIND AS A PICKUP
        fun bind(waypoint: String){
            pickupTextView.text = "Pickup"
            shape.setImageResource(R.drawable.diamond)
            address.text = waypoint
        }
        //BIND AS A DROPOFF
        fun bindDropOff(waypoint: String){
            pickupTextView.text = "Drop-off"
            shape.setImageResource(R.drawable.dropoff_circle)
            address.text = waypoint
        }


        companion object{
            fun create(parent: ViewGroup): RideDetailsViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ride_details_recyclerview_item, parent, false)
                return RideDetailsViewHolder(view)
            }
        }
    }


    class RideDetailsComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.equals(newItem)
        }

    }
}