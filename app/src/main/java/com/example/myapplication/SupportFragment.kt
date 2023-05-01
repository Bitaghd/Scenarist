package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.myapplication.databinding.FragmentProjectDetailsBinding
import com.example.myapplication.databinding.FragmentSupportBinding
import com.example.myapplication.fragments.main.project_details.ProjectDetailsFragment


class SupportFragment : Fragment() {
    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         //Inflate the layout for this fragment
        _binding =  FragmentSupportBinding.inflate(inflater, container, false)
        replaceFragment()
        return binding.root
    }

    private fun replaceFragment(){
        var fragment: Fragment? = null
          //val fragmentTransaction = fragmentManager.beginTransaction()
        (MainActivity)
        binding.bottomNav.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.projectDetailsFragment -> {
                    fragment = ProjectDetailsFragment()
                }
                R.id.locationFragment
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}