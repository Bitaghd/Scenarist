package com.example.myapplication.fragments.main.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ProjectRawBinding
import com.example.myapplication.fragments.main.scenes.SceneFragmentDirections
import com.example.myapplication.fragments.main.scenes.ScenesAdapter
import com.example.myapplication.model.Location
import com.example.myapplication.model.Scene

class LocationsAdapter(val listener: RawClickListener):RecyclerView.Adapter<LocationsAdapter.LocationHolder>() {
    private var locationsList = emptyList<Location>()
    class LocationHolder(val binding: ProjectRawBinding, val listener: RawClickListener):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val binding = ProjectRawBinding.inflate(LayoutInflater.from(parent.context))
        return LocationHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return locationsList.size
    }
    fun setData(location: List<Location>) {
        this.locationsList = location
        notifyDataSetChanged()

    }
    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val currLocation = locationsList[position]

        //Set the item name
        holder.binding.projectNameTxt.text = currLocation.location_name


        //Move to the SceneDetails fragment
        holder.binding.rawLayout.setOnClickListener{
            val action = LocationFragmentDirections.actionLocationFragmentToLocationDetails(currLocation)
            holder.itemView.findNavController().navigate(action)
        }

        // Delete current scene
        holder.binding.deleteProjectID.setOnClickListener {
            listener.deleteLocation(currLocation)
        }


    }

    interface RawClickListener{
        fun deleteLocation(location: Location)
    }
}