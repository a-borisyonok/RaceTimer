package com.seka.racetimer.presentation.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.seka.racetimer.databinding.ResultsFragmentBinding
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.presentation.ui.adapters.results.ResultsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ResultsFragment : Fragment() {


    private var _binding: ResultsFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel: ResultsViewModel by viewModels()
    private val adapter: ResultsAdapter? get() = binding.resultsRv.adapter as? ResultsAdapter


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resultsRv.adapter = context?.let { ResultsAdapter(it) }
        viewModel.getResults().onEach(::render).launchIn(lifecycleScope)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun render(participant: List<Participant>) {
        adapter?.submitList(participant)
    }

}