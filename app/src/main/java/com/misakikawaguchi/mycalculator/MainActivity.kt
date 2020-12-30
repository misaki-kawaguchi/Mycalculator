package com.misakikawaguchi.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    // 最後に押されたキーが数値かどうか確認する
    var lastNumeric: Boolean = false

    // ドットを追加するかどうか確認する。trueの場合はドットを追加しないように制限する
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 電卓の数字をtvInputに表示するようにする
    fun onDigit(view: View) {
        tvInput.append((view as Button).text)

        // 最後に押されたキーが数値のため「true」にする
        lastNumeric = true
    }

    // 入力した値をクリアする
    fun onClear(view: View) {
        // 入力した値をクリアする
        tvInput.text = ""

        // falseにリセットする
        lastNumeric = false
        lastDot =false
    }

    // ドットを追加
    fun onDecimalPoint(view: View) {
        // 最後に押した値が数値且つドットを押していない場合
        if(lastNumeric && !lastDot) {
            // ドットを追加できるようにする
            tvInput.append(".")

            // 最後に押されたキーはドット
            lastNumeric = false
            // ドットを押せないようにする
            lastDot = false
        }
    }

    // 計算する
    fun onEqual(view: View) {
        // 最後の入力が数値のみの場合
        if (lastNumeric) {
            //値を読み取る
            var value = tvInput.text.toString()
            var prefix = ""

            // try ～ catch で、発生した例外を処理して、異常終了しないようにする
            try {

                // 値が「-」で始まる場合は、値を分離し、値を使用して計算を実行します。
                if(value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                // inputValueに「/」が含まれている場合
                if (value.contains("/")) {

                    // /を使用してinputValueを分割する
                    val splitValue = value.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    // プレフィックスが空でない場合（-がある場合）は、最初の値、つまり1を追加します。
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    // 割り算する。「.0」を取り除いたものが計算結果として表示される
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                    // inputValueに「*」が含まれている場合
                }else if (value.contains("*")) {

                    //*を使用してinputValueを分割する
                    val splitValue = value.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    // プレフィックスが空でない場合（-がある場合）は、最初の値、つまり1を追加します。
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    // 掛け算する
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                    // inputValueに「-」が含まれている場合
                }else if (value.contains("-")) {

                    // -を使用してinputValueを分割する
                    val splitValue = value.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    // プレフィックスがからでない場合（-がある場合）は、最初の値、つまり1を追加する
                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    // 引き算をする
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                    // inputValueに「+」は含まれている場合
                }else if(value.contains("+")) {

                    // +を使用してinputValueを分割する
                    val splitValue = value.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    // 足算をする
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

            } catch (e:ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    // 演算子を追加する
    fun onOperator(view: View) {
        // 最後に押したのが数字且つ演算子が使われていない場合
        if(lastNumeric && !isOperatorAdd(tvInput.text.toString())) {

            // 演算子を追加する
            tvInput.append((view as Button).text)

            // 最後に押したのは演算子
            lastNumeric = false
            // 演算子を押した後にドットを押せないようにする
            lastDot = false
        }
    }

    // いずれかの演算子が使われているかどうか確認する
    private fun isOperatorAdd(value: String) : Boolean {
        // 「-」で始まる場合は無視する
        return if (value.startsWith("-")) {
            false
        }else {
            (value.contains("/") || value.contains("*") || value.contains("-") || value.contains("+"))
        }
    }

    // 小数点以下のゼロを削除する
    private fun removeZeroAfterDot(result: String): String {
        var value = result

        // 「.0」が含まれている場合
        if(result.contains(".0")) {
            // 一番最初の文字から文字数-2までの値をvalueに代入する
            value = result.substring(0, result.length -2)
        }

        return value
    }
}