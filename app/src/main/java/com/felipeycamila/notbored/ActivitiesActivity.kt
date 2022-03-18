package com.felipeycamila.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActivitiesActivity : AppCompatActivity() {

    private val typeList = listOf<String>("education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)
    }

}