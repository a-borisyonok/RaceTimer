package com.seka.racetimer.presentation.ui.timers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.seka.racetimer.R
import com.seka.racetimer.databinding.TimerFragmentBinding
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.presentation.ui.adapters.timer.TimerAdapter
import com.seka.racetimer.util.Dialogs
import com.seka.racetimer.util.TimeMillisConverter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TimerFragment : Fragment() {

    private var _binding: TimerFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TimerViewModel by viewModels()
    private val adapter: TimerAdapter? get() = binding.recyclerView.adapter as? TimerAdapter

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TimerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                while (true) {
                    timerToolbar.subtitle = convertMillisToTime(System.currentTimeMillis())
                    delay(1000)
                }
            }

            recyclerView.adapter =
                TimerAdapter(requireContext()).apply {
                    setHasStableIds(true)
                }

            floatingActionButton.setOnClickListener {
                context?.let {
                    Dialogs().showEnterStartNumberDialog(
                        it,
                        viewModel
                    )
                }
            }
            deleteAllButton.setOnClickListener {
                context?.let { it1 -> Dialogs().showDeleteAllDialog(it1, viewModel, view) }
            }

            goToResults.setOnClickListener {
                view.findNavController().navigate(R.id.action_timerFragment_to_resultsFragment)
            }
        }

        viewModel.getParticipants().onEach(::render).launchIn(lifecycleScope)
    }


    private fun convertMillisToTime(millis: Long) =
        TimeMillisConverter().convertMillisToTime(millis)

    private fun render(participant: List<Participant>) {

        adapter?.submitList(participant)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}