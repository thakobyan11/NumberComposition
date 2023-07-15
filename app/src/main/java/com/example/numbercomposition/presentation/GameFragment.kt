package com.example.numbercomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.numbercomposition.databinding.FragmentGameBinding
import com.example.numbercomposition.domain.entity.GameResult

class GameFragment : Fragment() {

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
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        observeLiveData()
    }


    private fun observeLiveData() {
        vm.gameResult.observe(viewLifecycleOwner) {
            navigateGameFinishFragment(it)
        }
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