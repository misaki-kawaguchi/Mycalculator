package com.misakikawaguchi.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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

    }
}