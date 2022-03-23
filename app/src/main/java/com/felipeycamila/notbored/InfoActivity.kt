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
import java.lang.Exception

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    private lateinit var type: String
    private lateinit var participants: String
    private lateinit var price: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            type = intent.extras?.getString("type")?.lowercase() ?: ""
            participants = intent.extras?.getString("participants") ?: ""
            price = intent.extras?.getString("price") ?: ""

            getActivity(type, participants, price)
        } catch (e: Exception) /* preguntarle a mariano */ {
            Log.e("Error", e.message.toString())
        }

        initBackBtn()
        initTryAnotherBtn()
    }


    private fun getActivity(type: String, participants: String, price: String) {
        //esto lo podriamos pasar a una funcion setear parametros que luego llama a getActivity con los parametros correctos
        val typeR: String? = if (type.isBlank()) null else type
        val participantsR: String? = if (participants.isBlank()) null else participants
        var minprice: String? = null
        var maxprice: String? = null
        when (price.lowercase()) {
            "free" -> {
                maxprice = "0.0"
            }
            "low" -> {
                minprice = "0.1"
                maxprice = "0.3"
            }
            "medium" -> {
                minprice = "0.4"
                maxprice = "0.6"
            }
            "high" -> {
                minprice = "0.7"
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService()
                .getActivity(typeR, participantsR, minprice, maxprice)
            if (response.isSuccessful) {
                runOnUiThread {
                    initView(response.body())
                }
            } else {
                Snackbar.make(binding.root, "Api not responding :'C ", Snackbar.LENGTH_LONG).show()
            }
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
                getActivity(type, participants, price)
            } catch (e: Exception) /* preguntarle a mariano */ {
                Log.e("Error", e.message.toString())
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