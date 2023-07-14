package com.example.numbercomposition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.numbercomposition.databinding.FragmentGameBinding
import com.example.numbercomposition.domain.entity.GameResult
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            with(binding) {
                add(tvOption1)
                add(tvOption2)
                add(tvOption3)
                add(tvOption4)
                add(tvOption5)
                add(tvOption6)
            }
        }
    }


    private val args by navArgs<GameFragmentArgs>()

    private val vmFactory by lazy {
        GameViewModelFactory(requireActivity().application, args.level)
    }

    private val vm: GameViewModel by lazy {
        ViewModelProvider(this,vmFactory)[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGame = null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setOnClickListenersInOptions()
    }

    private fun setOnClickListenersInOptions() {
        tvOptions.forEach { textView ->
            textView.setOnClickListener {
                vm.chooseAnswer(textView.text.toString().toInt())
            }
        }
    }

    private fun observeLiveData() {
        vm.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
        vm.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        vm.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {

            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        vm.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        vm.time.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        vm.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        vm.gameResult.observe(viewLifecycleOwner) {
            navigateGameFinishFragment(it)
        }
        vm.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun getColorByState(stateColor: Boolean): Int {
        val colorResId = if (stateColor) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun navigateGameFinishFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

}