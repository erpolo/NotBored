package com.felipeycamila.notbored
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.felipeycamila.notbored.databinding.ActivityStartBinding
import com.google.android.material.snackbar.Snackbar

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    private var price: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStartBtn()
        initTermsAndCondition()
        initRadioButtons()
    }

    private fun initTermsAndCondition() {
        val tvTerms = binding.tvTerms
        tvTerms.setOnClickListener {
            val intentToTermsAndConditions = Intent(this, TermsAndConditions::class.java)
            startActivity(intentToTermsAndConditions)
        }
    }

    private fun initStartBtn() {
        binding.btStart.setOnClickListener {
            if (binding.cbTermsAndCondition.isChecked){
                val participants = binding.etParticipants.text.toString()
                if ((participants.isBlank() || participants.toInt() > 0)) {
                    val intentToActivitiesActivity = Intent(this, ActivitiesActivity::class.java)
                    intentToActivitiesActivity.putExtra(getString(R.string.participants_app), participants)
                    intentToActivitiesActivity.putExtra(getString(R.string.price_app), price)
                    startActivity(intentToActivitiesActivity)
                } else {
                    Snackbar.make(binding.root, getString(R.string.allowedNumber), Snackbar.LENGTH_LONG)
                        .show()
                }
            }else{
                Snackbar.make(binding.root, getString(R.string.acceptTerms), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initRadioButtons() {
        binding.rbFree.setOnClickListener { onRadioButtonClick(it as RadioButton) }
        binding.rbLow.setOnClickListener { onRadioButtonClick(it as RadioButton) }
        binding.rbMedium.setOnClickListener { onRadioButtonClick(it as RadioButton) }
        binding.rbHigh.setOnClickListener { onRadioButtonClick(it as RadioButton) }
    }

    private fun onRadioButtonClick(rbChecked : RadioButton){
        if(rbChecked.text.toString() == price){
            binding.rgPrice.clearCheck()
            price = ""
        }else{
            price = rbChecked.text.toString()
        }
    }
}