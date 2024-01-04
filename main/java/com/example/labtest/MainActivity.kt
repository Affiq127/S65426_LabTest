package com.example.labtest

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.labtest.ui.theme.LabTestTheme
import android.view.View
import android.widget.RadioButton
import kotlin.math.pow
import kotlin.random.Random
import kotlin.math.max

class MainActivity : ComponentActivity() {

    private lateinit var difficultyRadioGroup: RadioGroup
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var pointsTextView: TextView

    private var currentLevel: Int = 1
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup)
        questionTextView = findViewById(R.id.questionTextView)
        answerTextView = findViewById(R.id.answerTextView)
        pointsTextView = findViewById(R.id.pointsTextView)

        // Set Level 1 as default checked state
        val level1RadioButton: RadioButton = findViewById(R.id.level1RadioButton)
        level1RadioButton.isChecked = true

        difficultyRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.level1RadioButton -> currentLevel = 1
                R.id.level2RadioButton -> currentLevel = 2
                R.id.level3RadioButton -> currentLevel = 3
            }
            generateQuestion()
        }

        generateQuestion()
    }

    private fun generateQuestion() {
        val operand1 = generateRandomNumber(currentLevel)
        val operand2 = generateRandomNumber(currentLevel)
        val operator = generateRandomOperator()

        val question = "$operand1 $operator $operand2"
        val correctAnswer = evaluateExpression(operand1, operand2, operator)

        questionTextView.text = question
        answerTextView.text = ""
    }

    private fun generateRandomNumber(maxDigits: Int): Int {
        val max = 10.0.pow(maxDigits.toDouble()).toInt()
        return Random.nextInt(max)
    }

    private fun generateRandomOperator(): String {
        val operators = arrayOf("+", "-", "*", "/")
        return operators.random()
    }

    private fun evaluateExpression(operand1: Int, operand2: Int, operator: String): Int {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }

    fun submitAnswer(view: View) {
        val userInput = answerTextView.text.toString().toIntOrNull()
        if (userInput != null) {
            val correctAnswer = evaluateExpression(
                questionTextView.text.toString().split(" ")[0].toInt(),
                questionTextView.text.toString().split(" ")[2].toInt(),
                questionTextView.text.toString().split(" ")[1]
            )

            if (userInput == correctAnswer) {
                points++
                pointsTextView.text = "Points: $points"
                answerTextView.text = "Correct!"
            } else {
                points = max(0, points - 1)
                pointsTextView.text = "Points: $points"
                answerTextView.text = "Incorrect. Try again."
            }
        } else {
            answerTextView.text = "Please enter a valid number."
        }
    }
}

