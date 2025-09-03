package com.example.flash

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Color

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView










class MainActivity : AppCompatActivity() {

    private lateinit var answer1: TextView
    private lateinit var answer2: TextView
    private lateinit var answer3: TextView
    private lateinit var answersLayout: LinearLayout
    private lateinit var eyeIcon: ImageView

    private var correctAnswerIndex = 2
    private var answersVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // rejwenn vue yo
        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.answer2)
        answer3 = findViewById(R.id.answer3)
        answersLayout = findViewById(R.id.answersLayout)
        eyeIcon = findViewById(R.id.eyeIcon)

        val answers = listOf(answer1, answer2, answer3)

        // jesyon klik sou chak rep
        answers.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                resetAnswers(answers)
                if (index == correctAnswerIndex) {
                    textView.setBackgroundColor(Color.parseColor("#90EE90")) // Vert clair
                } else {
                    textView.setBackgroundColor(Color.parseColor("#FF6B6B")) // Rouge clair
                    answers[correctAnswerIndex].setBackgroundColor(Color.parseColor("#90EE90"))
                }
            }
        }

        // klik deyo pou reset rep ln
        findViewById<View>(R.id.rootLayout).setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                if (!isInsideView(answer1, x, y) &&
                    !isInsideView(answer2, x, y) &&
                    !isInsideView(answer3, x, y) &&
                    !isInsideView(eyeIcon, x, y)
                ) {
                    resetAnswers(answers)
                }
            }
            false
        }

        // afichaj rep yo ap icon je an
        eyeIcon.setOnClickListener {
            if (answersVisible) {
                // kache rep yo
                answersLayout.visibility = View.GONE
                eyeIcon.setImageResource(R.drawable.ic_eye_off)
                answersVisible = false
            } else {
                // afiche rep yo epi chanje icon
                answersLayout.visibility = View.VISIBLE
                eyeIcon.setImageResource(R.drawable.ic_eye)
                answersVisible = true
                // reyinisyalize koulè yo lè nap reyafiche
                resetAnswers(answers)
            }
        }
    }

    // Remet koulè pa defo
    private fun resetAnswers(answers: List<TextView>) {
        answers.forEach {
            it.setBackgroundColor(Color.parseColor("#FFCC99")) // Couleur orange par défaut
        }
    }

    // Vérifye klik andan yn vue
    private fun isInsideView(view: View, x: Int, y: Int): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        val right = left + view.width
        val bottom = top + view.height
        return x in left..right && y in top..bottom
    }
}