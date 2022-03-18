package com.felipeycamila.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felipeycamila.notbored.databinding.ActivityTermsAndConditionsBinding

class TermsAndConditions : AppCompatActivity() {

    private lateinit var binding: ActivityTermsAndConditionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

        initBackBtn()
    }

    private fun initBackBtn() {
        val backBtn = binding.btBack

        backBtn.setOnClickListener{
            finish()
        }
    }

}