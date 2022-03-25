package com.felipeycamila.notbored.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.felipeycamila.notbored.R
import com.felipeycamila.notbored.databinding.ActivityInfoBinding
import com.felipeycamila.notbored.model.ActivityModel
import com.felipeycamila.notbored.service.ApiAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    private var type: String? = null
    private var participants: String? = null
    private var maxprice: String? = null
    private var minprice: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValues()
        getActivity()
        initBackBtn()
        initTryAnotherBtn()
    }

    //This function saves the values and works them to be later used in the API call
    private fun setValues() {
        type = intent.extras?.getString(getString(R.string.type_app))?.lowercase()
        participants = intent.extras?.getString(getString(R.string.participants_app))
        val price = intent.extras?.getString(getString(R.string.price_app))?.lowercase()

        type = if (type.isNullOrBlank()) null else type
        participants = if (participants.isNullOrBlank()) null else participants

        when (price) {
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
    }

    //This function calls the API and if the response was successful it starts the view of this activity
    private fun getActivity() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiAdapter().getApiService()
                    .getActivity(type, participants, minprice, maxprice)
                if (response.isSuccessful) {
                    runOnUiThread {
                        initView(response.body())
                    }
                } else {
                    Snackbar.make(binding.root, getString(R.string.api_error), Snackbar.LENGTH_LONG)
                        .show()
                }
            } catch (e: UnknownHostException) {
                Log.e("Error", e.message.toString())
                Snackbar.make(binding.root, getString(R.string.error_io), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    /**
     * This function works with the API response, setting the UI for the user,
     * when there is no type of activity it sets what is necessary to show Random
     * @param values (api response)
     */
    private fun initView(values: ActivityModel?) {
        values?.let {
            it.error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            } ?: run {
                when {
                    values.price == 0.0 -> binding.tvShowPrice.text = getString(R.string.free)
                    values.price <= 0.3 -> binding.tvShowPrice.text = getString(R.string.low)
                    values.price <= 0.6 -> binding.tvShowPrice.text = getString(R.string.medium)
                    else -> binding.tvShowPrice.text = getString(R.string.high)
                }
                binding.tvNumParticipants.text = values.participants.toString()
                binding.tvType.text = values.type.replaceFirstChar { it.uppercase() }
                binding.tvTitleActivity.text = values.activity
                if (type.isNullOrBlank()) {
                    binding.tvTypeOpc.visibility = View.VISIBLE
                    binding.tvTypeOpc.text = values.type
                    binding.tvType.text = getString(R.string.random)
                }
            }
        }
    }

    //This function is launched when the user presses Try Another and calls the api again.
    private fun initTryAnotherBtn() {
        val btnTry = binding.btnTry
        btnTry.setOnClickListener {
            getActivity()
        }
    }

    private fun initBackBtn() {
        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            finish()
        }
    }

}