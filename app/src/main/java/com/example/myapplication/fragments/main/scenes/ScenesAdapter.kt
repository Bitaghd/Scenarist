package com.example.myapplication.fragments.main.scenes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ProjectRawBinding
import com.example.myapplication.model.Scene

class ScenesAdapter():RecyclerView.Adapter<ScenesAdapter.SceneHolder>() {
    private var scenesList = emptyList<Scene>()
    class SceneHolder(val binding: ProjectRawBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneHolder {
        val binding = ProjectRawBinding.inflate(LayoutInflater.from(parent.context))
        return SceneHolder(binding)
    }

    override fun getItemCount(): Int {
        return scenesList.size
    }

    override fun onBindViewHolder(holder: SceneHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun setData(scene: List<Scene>) {
        this.scenesList = scene
        notifyDataSetChanged()

    }
}