package com.codetest.highschool.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetest.highschool.R
import com.codetest.highschool.adapter.HighSchoolListAdapter
import com.codetest.highschool.databinding.FragmentListBinding
import com.codetest.highschool.model.HighSchoolListItem
import com.codetest.highschool.utils.Constants
import com.codetest.highschool.viewmodel.GetSchoolListResponse
import com.codetest.highschool.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

//School List Fragment class to display high school list
@AndroidEntryPoint
class SchoolListFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var _binding: FragmentListBinding
    private lateinit var mHighSchoolList: List<HighSchoolListItem>
    private lateinit var adapter: HighSchoolListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }
        viewModel.getSchoolList()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForEvents()
        _binding.customLayout.backImage.setOnClickListener {
            navigateToHome()
        }
        _binding.customLayout.appBarText.text = getString(R.string.app_bar_name)
    }

    private fun navigateToHome() {
        findNavController().navigate(
            R.id.home_dest
        )
    }

    private fun listenForEvents() {
        viewModel._uiEvent.observe(viewLifecycleOwner) { result ->
            when (result) {
                is GetSchoolListResponse.ShowLoading -> {
                    _binding.progressBar.visibility = View.VISIBLE
                }
                is GetSchoolListResponse.ResponseSuccess ->
                    result.highSchoolList.let {
                        mHighSchoolList = result.highSchoolList
                        _binding.progressBar.visibility = View.GONE
                        adapterCall()
                    }
                is GetSchoolListResponse.ResponseFailure -> {
                    _binding.progressBar.visibility = View.GONE
                    _binding.schoollistRecyclerview.visibility = View.GONE
                    _binding.errorText.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    //Adapter call to display the school list to recyclerview
    private fun adapterCall() {
        adapter =
            HighSchoolListAdapter(HighSchoolListAdapter.OnClickListener { schoolName ->
                navigateToDetailScreen(schoolName)
            }, mHighSchoolList)

        _binding.schoollistRecyclerview.layoutManager = LinearLayoutManager(activity)
        _binding.schoollistRecyclerview.adapter = adapter
    }

    //This method is to navigate from list screen to detail screen with navigation id..
    private fun navigateToDetailScreen(schoolName: String) {
        val bundle = Bundle()
        bundle.putString(Constants.SCHOOL_NAME, schoolName)
        findNavController().navigate(
            R.id.forecastDetail_dest, bundle
        )
    }
}