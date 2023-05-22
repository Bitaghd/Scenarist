package com.example.myapplication.fragments.main.scenes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ProjectRawBinding
import com.example.myapplication.fragments.main.MainAdapter
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene

class ScenesAdapter(val listener: RawClickListener):RecyclerView.Adapter<ScenesAdapter.SceneHolder>() {
    private var scenesList = emptyList<Scene>()
    class SceneHolder(val binding: ProjectRawBinding, val listener: RawClickListener):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneHolder {
        val binding = ProjectRawBinding.inflate(LayoutInflater.from(parent.context))
        return SceneHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return scenesList.size
    }

    override fun onBindViewHolder(holder: SceneHolder, position: Int) {
        val currentScene = scenesList[position]

        //Set the item name
        holder.binding.projectNameTxt.text = currentScene.scene_name


        //Move to the SceneDetails fragment
        holder.binding.rawLayout.setOnClickListener{
            val action = SceneFragmentDirections.actionSceneFragmentToSceneDetails(currentScene)
            holder.itemView.findNavController().navigate(action)
        }


        // Delete current scene
        holder.binding.deleteProjectID.setOnClickListener {
            listener.deleteScene(currentScene)
        }

    }

    fun setData(scene: List<Scene>) {
        this.scenesList = scene
        notifyDataSetChanged()

    }

    interface RawClickListener{
        fun deleteScene(scene: Scene)
    }

}