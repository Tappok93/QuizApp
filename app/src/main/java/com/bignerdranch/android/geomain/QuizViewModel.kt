package com.bignerdranch.android.geomain

import androidx.lifecycle.ViewModel
import android.util.Log
import android.widget.Toast

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    /**
     * Переход к следующему вопросу
     */

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    /**
     * Переход к предидущему вопросу
     */

    fun moveToBack() {
        if (currentIndex != 0) {
            currentIndex = (currentIndex - 1) % questionBank.size
        } else {
            return
        }
    }

    // Переменная содержащая ответ вопроса
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    // Переменная содержащая индекс вопроса

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
}