package com.codetest.highschool.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codetest.com.codetest.highschool.model.SatDetails
import com.codetest.highschool.R
import com.codetest.highschool.databinding.FragmentSatDetailsBinding
import com.codetest.highschool.utils.Constants
import com.codetest.highschool.viewmodel.DetailsViewModel
import com.codetest.highschool.viewmodel.GetDetailResponse
import dagger.hilt.android.AndroidEntryPoint

//Detail Fragment class to display sat test details for the selected school
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var _binding: FragmentSatDetailsBinding
    private lateinit var mSatDetails: List<SatDetails>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSatDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }
        viewModel.getSatDetails()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForEvents()
        _binding.customLayout.backImage.setOnClickListener {
            navigateToHome()
        }
        _binding.customLayout.appBarText.text = getString(R.string.app_bar_details_name)
    }

    private fun navigateToHome() {
        findNavController().navigate(
            R.id.home_dest
        )
    }

    /*listen call for the events connected from viewModel class*/
    private fun listenForEvents() {
        viewModel._uiEvent.observe(viewLifecycleOwner) { result ->
            when (result) {
                is GetDetailResponse.ShowLoading -> {
                    _binding.detailsProgressBar.visibility = View.VISIBLE
                }
                is GetDetailResponse.ResponseSuccess ->
                    result.satDetails.let {
                        mSatDetails = result.satDetails
                        _binding.errorText.visibility = View.GONE
                        _binding.detailsProgressBar.visibility = View.GONE
                        setSatDetails()
                    }
                is GetDetailResponse.ResponseFailure -> {
                    _binding.schoolText.visibility = View.GONE
                    _binding.detailsProgressBar.visibility = View.GONE
                    _binding.errorText.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    /*Setting sat score details to the ui based on condition as if selected school is matching*/
    @SuppressLint("SetTextI18n")
    private fun setSatDetails() {
        for (i in mSatDetails.indices) {
            if (mSatDetails[i].school_name.equals(
                    arguments?.getString(Constants.SCHOOL_NAME), ignoreCase = true
                )
            ) {
                _binding.satLayout.visibility = View.VISIBLE
                _binding.errorText.visibility = View.GONE
                _binding.schoolText.text = arguments?.getString(Constants.SCHOOL_NAME)
                _binding.testTakersText.text =
                    getString(R.string.sat_test_takers) + mSatDetails[i].num_of_sat_test_takers
                _binding.readingScoreText.text =
                    getString(R.string.crticial_reading_score) + mSatDetails[i].sat_critical_reading_avg_score
                _binding.mathScoreText.text =
                    getString(R.string.mathematics_score) + mSatDetails[i].sat_math_avg_score
                _binding.writingScoreText.text =
                    getString(R.string.writing_score) + mSatDetails[i].sat_writing_avg_score
            }
        }

    }

}