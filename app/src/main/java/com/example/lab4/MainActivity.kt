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
        Question(R.string.question_math, false))
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        truebutton = findViewById(R.id.true_button)
        falsebutton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.text_view)

        fun checkAnswer(userAnswer: Boolean) {
            val correctAnswer = questionBank[currentIndex].answer
            val messageResId = if (userAnswer == correctAnswer) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show()
        }

        truebutton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falsebutton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        fun updateQuestion() {
            val questionTextResId = questionBank[currentIndex].textResId
            questionTextView.setText(questionTextResId)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()



    }
}