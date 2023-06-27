package com.example.myapplication.fragments.main.details

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.fragments.main.scenes.ScenesAdapter
import com.example.myapplication.model.Details
import com.example.myapplication.viewmodel.DataViewModel

class Details : Fragment(), DetailsAdapter.RawClickListener {
    private lateinit var viewModel: DataViewModel
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var projectID: Int = 0
    private val adapter = DetailsAdapter(this@Details)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        val rv = binding.linksList
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        rv.apply {
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            addItemDecoration(divider)
        }
        viewModel.currentProject.observe(viewLifecycleOwner, Observer {
            projectID = it.id
        })

        Log.d(TAG, "Id here: $projectID")
        binding.include17.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include17.customTopBarLayout.title = getString(R.string.project_header)
        binding.include17.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addLinkButton.setOnClickListener {
            val action = DetailsDirections.actionDetailsToAddLink()
            findNavController().navigate(action)
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Id RIGHT here: $projectID")
        viewModel.getDetailsInProject(projectID).observe(viewLifecycleOwner, Observer {details->
            adapter.setData(details)

        })
    }

    override fun deleteDetails(details: Details) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.delete_yes){_, _->
            viewModel.deleteDetails(details)
            Toast.makeText(
                requireContext(),
                "${details.details_link} " + getString(R.string.delete_success),
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.delete_no){_, _->

        }
        builder.setTitle(getString(R.string.delete_confirm) + " ${details.details_link}?")
        builder.setMessage(getString(R.string.delete_desc) + " ${details.details_link}?")
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}