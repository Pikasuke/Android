package com.moi.kotlincalculatrice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Numbers
        tv1.setOnClickListener { appendOnExpression("1", true) }
        tv2.setOnClickListener { appendOnExpression("2", true) }
        tv3.setOnClickListener { appendOnExpression("3", true) }
        tv4.setOnClickListener { appendOnExpression("4", true) }
        tv5.setOnClickListener { appendOnExpression("5", true) }
        tv6.setOnClickListener { appendOnExpression("6", true) }
        tv7.setOnClickListener { appendOnExpression("7", true) }
        tv8.setOnClickListener { appendOnExpression("8", true) }
        tv9.setOnClickListener { appendOnExpression("9", true) }
        tv0.setOnClickListener { appendOnExpression("0", true) }
        tvDot.setOnClickListener { appendOnExpression(".", true) }
        //Operande
        tvOpen.setOnClickListener { appendOnExpression("(", false) }
        tvClose.setOnClickListener { appendOnExpression(")", false) }
        tvPlus.setOnClickListener { appendOnExpression("+", false) }
        tvMoins.setOnClickListener { appendOnExpression("-", false) }
        tvMul.setOnClickListener { appendOnExpression("*", false) }
        tvDiv.setOnClickListener { appendOnExpression("/", false) }

        tvClear.setOnClickListener {
            tvExpresion.text = ""
            tvResult.text = ""
        }

        tvBack.setOnClickListener {
            val str = tvExpresion.text.toString()
            if (str.isNotEmpty()) {
                tvExpresion.text = str.substring(0, str.length - 1)
            }
            tvResult.text = ""
        }

        tvEqual.setOnClickListener {
            try {
                val text = tvExpresion.text.toString()
                val expression = ExpressionBuilder(text).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble())
                    tvResult.text = longResult.toString()
                else
                    tvResult.text = result.toString()

            } catch (e: Exception) {
                Log.d("Exception", "message : ${e.message}")
            }
        }
    }

    fun appendOnExpression(str: String, canClear: Boolean) {
        if (tvResult.text.isNotEmpty()) {
            tvExpresion.text = ""
        }
        if (canClear) {
            tvResult.text = ""
            tvExpresion.append(str)
        } else {
            tvExpresion.append(tvResult.text)
            tvExpresion.append(str)
            tvResult.text = ""
        }
    }
}