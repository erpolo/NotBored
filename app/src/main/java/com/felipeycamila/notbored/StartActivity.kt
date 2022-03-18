package com.felipeycamila.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felipeycamila.notbored.databinding.ActivityMainBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStartBtn()
        initTermsAndCondition()
    }

    private fun initTermsAndCondition() {
        val tvTerms = binding.tvTerms
        tvTerms.setOnClickListener {
            val intentToTermsAndConditions = Intent(this,TermsAndConditions::class.java)
            startActivity(intentToTermsAndConditions)
        }
    }

    private fun initStartBtn() {
        binding.btStart.setOnClickListener {
            val etParticipant = binding.etParticipants
            if (etParticipant.text.isBlank()){
                // hacer ahorita
            }
        }
    }

}