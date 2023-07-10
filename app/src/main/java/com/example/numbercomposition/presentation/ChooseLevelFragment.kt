package com.example.numbercomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.numbercomposition.R
import com.example.numbercomposition.databinding.FragmentChooseLevelBinding
import com.example.numbercomposition.domain.entity.Level
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {

    private var _binding : FragmentChooseLevelBinding? = null
    private val binding : FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            buttonLevelEasy.setOnClickListener {
                navigateToEasyGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                navigateToEasyGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                navigateToEasyGameFragment(Level.HARD)
            }
            buttonLevelTest.setOnClickListener {
                navigateToEasyGameFragment(Level.TEST)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun navigateToEasyGameFragment(level: Level){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,GameFragment.newInstance(level))
            .addToBackStack(GameFragment.Name)
            .commit()
    }

    companion object{
        const val NAME = "ChooseLevelFragment"
        fun newInstance() = ChooseLevelFragment()
    }
}