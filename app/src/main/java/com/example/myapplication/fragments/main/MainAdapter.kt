package com.example.myapplication.fragments.main

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.ListFragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.SupportFragment
import com.example.myapplication.model.Projects
import com.example.myapplication.databinding.ProjectRawBinding
import com.example.myapplication.fragments.main.project_details.ProjectDetailsFragment
import com.example.myapplication.viewmodel.DataViewModel

class MainAdapter(val listener: RawClickListener):RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    private var projectsList = emptyList<Projects>()
    class MyViewHolder(val binding: ProjectRawBinding, val listener: RawClickListener):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ProjectRawBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = projectsList[position]
        holder.binding.projectNameTxt.text = currentItem.pr_name
        //setListeners(currentItem, holder)
        holder.binding.rawLayout.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToSupportFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
        holder.binding.deleteProjectID.setOnClickListener{
            listener.deleteProject(currentItem)
        }

    }

    private fun setListeners(currentItem: Projects, holder: MainAdapter.MyViewHolder) {
        holder.binding.rawLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, SupportFragment::class.java)
            intent.putExtra("id", currentItem.id)
            holder.itemView.context.startActivity(intent)
            //val action = MainFragmentDirections.actionMainFragmentToSupportFragment(intent)
            //holder.itemView.findNavController().navigate(action)
        }
        holder.binding.deleteProjectID.setOnClickListener{
            listener.deleteProject(currentItem)
        }
    }

    fun setData(projects: List<Projects>){
        this.projectsList = projects
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }

    interface RawClickListener{
        fun deleteProject(projects: Projects)
    }


}