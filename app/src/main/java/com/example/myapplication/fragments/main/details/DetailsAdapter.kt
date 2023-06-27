package com.example.myapplication.fragments.main.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ProjectRawBinding
import com.example.myapplication.model.Details


class DetailsAdapter(val listener: RawClickListener): RecyclerView.Adapter<DetailsAdapter.DetailsHolder>() {
    private var detailsList = emptyList<Details>()

    interface RawClickListener{
        fun deleteDetails(details: Details)
    }

    class DetailsHolder(val binding: ProjectRawBinding, val listener: RawClickListener):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsHolder {
        val binding = ProjectRawBinding.inflate(LayoutInflater.from(parent.context))
        return DetailsHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    override fun onBindViewHolder(holder: DetailsHolder, position: Int) {
        val currentDetails= detailsList[position]

        //Set the item name
        holder.binding.projectNameTxt.text = currentDetails.details_link


        //Move to the SceneDetails fragment
//        holder.binding.rawLayout.setOnClickListener{
//            val action = SceneFragmentDirections.actionSceneFragmentToSceneDetails(currentDetails)
//            holder.itemView.findNavController().navigate(action)
//        }


        // Delete current scene
        holder.binding.deleteProjectID.setOnClickListener {
            listener.deleteDetails(currentDetails)
        }
    }

    fun setData(details: List<Details>) {
        this.detailsList = details
        notifyDataSetChanged()

    }


}
