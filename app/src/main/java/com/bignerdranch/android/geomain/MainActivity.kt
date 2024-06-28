package com.bignerdranch.android.geomain

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.geomain.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
//    private lateinit var trueButton: Button
//    private lateinit var falseButton: Button
//    private lateinit var cheatButton: Button
//    private lateinit var nextButton: ImageButton
//    private lateinit var backButton: ImageButton
//    private lateinit var questionTextView: TextView

    /**
     * Связываем ViewModel и MainActivity
     */
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

//        trueButton = findViewById(R.id.true_button)
//        falseButton = findViewById(R.id.false_button)
//        nextButton = findViewById(R.id.next_button)
//        backButton = findViewById(R.id.back_button)
//        cheatButton = findViewById(R.id.cheat_button)
//        questionTextView = findViewById(R.id.question_text_view)

        // Вешаем слушатель на кнопку "True"

        binding.trueButton.setOnClickListener { checkAnswer(true) }

        //  Вешаем слушатель на кнопку "Fasle"

        binding.falseButton.setOnClickListener { checkAnswer(false) }

        //  Вешаем слушатель на кнопку "Next" или ">"

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        //  Вешаем слушатель на кнопку "Cheat", раздуваем intent

        binding.cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            startActivity(intent)
        }

        //  Вешаем слушатель на кнопку "Back" или "<"

        binding.backButton.setOnClickListener {
            quizViewModel.moveToBack()
            updateQuestion()
        }

        //  Добавляет возможноть изменять текст вопроса по нажатию на само поле вопроса.

        binding.questionTextView.setOnClickListener { view: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
        setContentView(binding.root)
    }

    /**
     * Функция смены вопроса.
     */
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    /**
     * Функция проверки ответа на вопрос.
     */
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0) // Не работает метод
        toast.show()
    }

    /**
     * Проверка жизненого цикла программы в Logcat.
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}


