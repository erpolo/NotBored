package com.felipeycamila.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.felipeycamila.notbored.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

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
            val participants = binding.etParticipants.text.toString()
            if (participants.isBlank() || participants.toInt() > 0 ){
                val intentToActivitiesActivity = Intent(this,ActivitiesActivity::class.java)
                intentToActivitiesActivity.putExtra("participants",participants)
                startActivity(intentToActivitiesActivity)
            }else{
                Snackbar.make(binding.root,getString(R.string.greaterNumber),Snackbar.LENGTH_LONG).show()
            }
        }
    }

}