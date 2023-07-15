package com.example.numbercomposition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.numbercomposition.R
import com.example.numbercomposition.domain.entity.GameResult


interface OnOptionsClickListener{
    fun onOptionClick(option:Int)
}
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

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView,enough:Boolean){
    textView.setTextColor(getColorByState(textView.context,enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar,enough:Boolean){
    val color = getColorByState(progressBar.context,enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context,stateColor: Boolean): Int {
    val colorResId = if (stateColor) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView,number:Int){
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView,onClickListener:OnOptionsClickListener){
    textView.setOnClickListener {
        onClickListener.onOptionClick(textView.text.toString().toInt())
    }
}