package com.example.numbercomposition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.numbercomposition.R
import com.example.numbercomposition.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView,count:Int){
    textView.text = String.format(
    textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("currentScore")
fun bindCurrentScore(textView: TextView,count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView,count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("rightAnswersPercent")
fun bindRightAnswersPercent(textView: TextView,gameResult: GameResult){
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult)
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0){
        0
    }else{
        ((countOfRightAnswers/countOfQuestions.toDouble())*100).toInt()
    }
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(img:ImageView,gameResult: GameResult){
    img.setImageResource(getSmileRedId(gameResult))
}

private fun getSmileRedId(gameResult: GameResult): Int {
    return if (gameResult.winner){
        R.drawable.ic_smile
    }else{
        R.drawable.ic_sad
    }
}