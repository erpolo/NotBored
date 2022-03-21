package com.felipeycamila.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.felipeycamila.notbored.databinding.ActivityInfoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    private lateinit var type: String
    private lateinit var participants: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            type = intent.extras?.getString("type")?.lowercase() ?: ""
            participants = intent.extras?.getString("participants") ?: ""
            getActivitys()
        } catch (e: Exception) /* preguntarle a mariano */ {
            Log.e("Error",e.message.toString())
        }

        initBackBtn()
        initTryAnotherBtn()
    }

    private fun getActivitys() {
        when {
            type.isBlank() && participants.isBlank() -> {
                getRandom()
            }
            type.isNotBlank() && participants.isNotBlank() -> {
                getActivityWithTypeAndParticipants(type, participants)
            }
            type.isBlank() -> {
                getRandomWithParticipants(participants)
            }
            participants.isBlank() -> {
                getActivityWithType(type)
            }
        }
    }

    private fun processResponse(response: Response<ActivityModel>, random: Boolean = false){
        if (response.isSuccessful) {
            runOnUiThread {
                initView(response.body(), random)
            }
        }else{
            Snackbar.make(binding.root,"Api not responding :'C ",Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getActivityWithTypeAndParticipants(type: String, participants: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getActivity(type, participants)
            processResponse(response)
        }
    }

    private fun getActivityWithType(type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getActivity(type)
            processResponse(response)
        }
    }

    private fun getRandomWithParticipants(participants: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getRandomActivity(participants)
            processResponse(response, true)
        }
    }

    private fun getRandom() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getRandomActivity()
            processResponse(response, true)
        }
    }

    private fun initView(values: ActivityModel?, random: Boolean = false) {
        values?.let {
            it.error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            } ?: run {
                when {
                    values.price == 0.0 -> binding.tvShowPrice.text = "Free"
                    values.price <= 0.3 -> binding.tvShowPrice.text = "Low"
                    values.price <= 0.6 -> binding.tvShowPrice.text = "Medium"
                    else -> binding.tvShowPrice.text = "High"
                }
                binding.tvNumParticipants.text = values.participants.toString()
                binding.tvType.text = values.type.replaceFirstChar { it.uppercase() }
                binding.tvTitleActivity.text = values.activity
                if (random) {
                    binding.tvTypeOpc.visibility = View.VISIBLE
                    binding.tvTypeOpc.text = values.type
                    binding.tvType.text = "Random"
                }
            }
        }
    }

    private fun initTryAnotherBtn() {
        val btnTry = binding.btnTry
        btnTry.setOnClickListener {
            try {
                getActivitys()
            }catch (e: Exception) /* preguntarle a mariano */{
                Log.e("Error",e.message.toString())
            }
        }
    }

    private fun initBackBtn() {
        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            finish()
        }
    }

}