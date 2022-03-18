package com.felipeycamila.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.felipeycamila.notbored.databinding.ActivityInfoBinding
import java.lang.Exception

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val type = intent.getStringExtra("type")
            val participants = intent.getIntExtra("participants", -1)

            if(!type.isNullOrBlank() && participants != -1){
                getActivitys(type, participants)
            }
        }catch (e: Exception){
            Log.d("Error",e.message.toString())
        }

        initBackBtn()
        initTryAnotherBtn()
    }

    private fun getActivitys(type: String, participants: Int) {
        // llamar a la api
        initView()
    }

    private fun initView() {

    }

    private fun initTryAnotherBtn() {
        val btnTry = binding.btnTry
        btnTry.setOnClickListener {
            // Hacer el llamado denuevo a la api
        }
    }

    private fun initBackBtn() {
        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            finish()
        }
    }

}