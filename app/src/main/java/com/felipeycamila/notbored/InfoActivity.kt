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
import java.io.IOException
import java.lang.Exception

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

        try {
            setValues()
            getActivity()
        } catch (e: IOException) {
            Snackbar.make(binding.root,getString(R.string.error_io), Snackbar.LENGTH_LONG).show()
            Log.e("Error", e.message.toString())
        }

        initBackBtn()
        initTryAnotherBtn()
    }


    private fun getActivity() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiAdapter().getApiService()
                .getActivity(type, participants, minprice, maxprice)
            if (response.isSuccessful) {
                runOnUiThread {
                    initView(response.body())
                }
            } else {
                Snackbar.make(binding.root, getString(R.string.api_error), Snackbar.LENGTH_LONG).show()
            }
        }
    }

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

    private fun initTryAnotherBtn() {
        val btnTry = binding.btnTry
        btnTry.setOnClickListener {
            try {
                getActivity()
            } catch (e: IOException) {
                Snackbar.make(binding.root,getString(R.string.error_io), Snackbar.LENGTH_LONG).show()
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