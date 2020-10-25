package com.moi.kotlinquizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quizapp.Question
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizzQuestionAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz_question)

        val questionList = Constans.quetQuestion()
        val currentPosition = 1

        val question: Question? = questionList[currentPosition-1]
        progressBar.progress = currentPosition
    }
}