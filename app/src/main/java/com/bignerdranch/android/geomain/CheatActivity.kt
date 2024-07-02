package com.bignerdranch.android.geomain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bignerdranch.android.geomain.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false
    lateinit var bindingCheatActivity: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCheatActivity = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(bindingCheatActivity.root)

        /**
         * Слушатель для кнопки "Show Answer" с отображением количества подсказок.
         */
        bindingCheatActivity.showAnswerButton.setOnClickListener {
            if (countCheckAnswer > 3) {
                Toast.makeText(this, "Больше нет подсказок!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            bindingCheatActivity.answerTextView.setText(answerText)
            setAnswerShowResult(true)
            Toast.makeText(
                this,
                "Вы воспользовались подсказкой $countCheckAnswer раз из 3",
                Toast.LENGTH_SHORT
            ).show()

        }
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        //Добавляем версию релиза для отображения
        bindingCheatActivity.versionApi.setText("API level " + Build.VERSION.RELEASE)
    }


    private fun setAnswerShowResult(isAnswerShow: Boolean) {
        val date = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShow)
        }
        setResult(Activity.RESULT_OK, date)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean):
                Intent {
            return Intent(
                packageContext,
                CheatActivity::class.java
            ).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
