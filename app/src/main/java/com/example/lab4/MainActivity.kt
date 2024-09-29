package com.example.lab4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var truebutton: Button
    private lateinit var falsebutton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_paper, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_rainbow, true),
        Question(R.string.question_moscow, true),
        Question(R.string.question_icebattle, false),
        Question(R.string.question_math, false)
    )

    private var currentIndex = 0
    private var correctAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("currentIndex", 0)
        }

        truebutton = findViewById(R.id.true_button)
        falsebutton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.text_view)

        truebutton.setOnClickListener { checkAnswer(true) }
        falsebutton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            truebutton.visibility = View.VISIBLE
            falsebutton.visibility = View.VISIBLE
            nextButton.visibility = View.INVISIBLE // Сбросить видимость для следующего вопроса
        }

        updateQuestion()

        // Изначально скрыть кнопку "Next", пока не будет дан ответ.
        nextButton.visibility = View.INVISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentIndex", currentIndex)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            correctAnswersCount++ // Увеличиваем счетчик правильных ответов
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        truebutton.visibility = View.INVISIBLE
        falsebutton.visibility = View.INVISIBLE

        if (currentIndex < questionBank.size - 1) {
            nextButton.visibility = View.VISIBLE
        } else {
            nextButton.visibility = View.INVISIBLE
            showFinalScore()
        }
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)

        // Сбросить видимость кнопок для нового вопроса.
        truebutton.visibility = View.VISIBLE
        falsebutton.visibility = View.VISIBLE
    }

    private fun showFinalScore() {
        val finalScoreMessage = getString(R.string.final_score_message, correctAnswersCount, questionBank.size)
        Toast.makeText(this, finalScoreMessage, Toast.LENGTH_LONG).show()
    }
}