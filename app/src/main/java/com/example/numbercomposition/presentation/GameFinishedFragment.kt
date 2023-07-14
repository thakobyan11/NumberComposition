package com.example.numbercomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.numbercomposition.R
import com.example.numbercomposition.databinding.FragmentGameFinishedBinding
import com.example.numbercomposition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinished = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParcelable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
        bindViews()
    }

    private fun setUpClickListeners() {
        binding.buttonRetry.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindViews(){
        with(binding){
            emojiResult.setImageResource(getSmileRedId())
            tvRequiredAnswers.text= String.format(
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun getPercentOfRightAnswers() = with(gameResult) {
        if (countOfQuestions == 0){
            0
        }else{
            ((countOfRightAnswers/countOfQuestions.toDouble())*100).toInt()
        }
    }

    private fun getSmileRedId(): Int {
        return if (gameResult.winner){
            R.drawable.ic_smile
        }else{
            R.drawable.ic_sad
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun parseParcelable() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let{
            gameResult = it
        }
    }

    companion object {

         const val KEY_GAME_RESULT = "result"
        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}