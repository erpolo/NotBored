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

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            type = intent.extras?.getString("type")?.lowercase() ?: ""
            participants = intent.extras?.getString("participants") ?: ""
            price = intent.extras?.getString("price") ?: ""

            getActivitys()
        } catch (e: Exception) /* preguntarle a mariano */ {
            Log.e("Error", e.message.toString())
        }

        initBackBtn()
        initTryAnotherBtn()
    }*/

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

    /*
    private fun getActivitys() {
        //me quede resolviendo la llamda a cada funcion dependiendo los parametros
        when {
            type.isBlank() && participants.isBlank() && price.isBlank()-> {
                getRandom()
            }
            //esta es nueva
            type.isBlank() && participants.isBlank() -> {
                getRandomWithPrice()
            }
            type.isBlank() && price.isBlank()-> {
                getRandomWithParticipants(participants)
            }
            //esta es nueva
            type.isBlank() -> {
                getRandomWithPriceAndPartic()
            }
            //esta es nueva
            type.isNotBlank() && participants.isNotBlank() && price.isNotBlank() -> {
                getActivityWithTypeAndParticAndPrice(type, participants, price)
            }
            //esta es nueva
            type.isNotBlank() && price.isNotBlank() -> {
                getActivityWithTypeAndPrice(type, price)
            }
            type.isNotBlank() && participants.isNotBlank() -> {
                getActivityWithTypeAndParticipants(type, participants)
            }
            participants.isBlank() && price.isBlank() -> {
                getActivityWithType(type)
            }
        }
    }
    //Las 4 primeras son las nuevas
    private fun getActivityWithTypeAndPrice(type: String, price: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getActivityPrice(type, price)
            processResponse(response)
        }

    }

    private fun getActivityWithTypeAndParticAndPrice(type: String, participants: String, price: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getActivity(type, participants, price)
            processResponse(response)
        }
    }

    private fun getRandomWithPriceAndPartic() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getRandomActivity(price, participants)
            processResponse(response)
        }
    }

    private fun getRandomWithPrice() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService().getRandomActivityPrice(price)
            processResponse(response)
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

    private fun processResponse(response: Response<ActivityModel>, random: Boolean = false) {
        if (response.isSuccessful) {
            runOnUiThread {
                initView(response.body(), random)
            }
        } else {
            Snackbar.make(binding.root, "Api not responding :'C ", Snackbar.LENGTH_LONG).show()
        }
    }*/

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
                //getActivitys()
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