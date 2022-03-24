package com.felipeycamila.notbored

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.felipeycamila.notbored.databinding.ActivityActivitiesBinding

class ActivitiesActivity : AppCompatActivity() {

    private val typeList = listOf(
        "Education",
        "Recreational",
        "Social",
        "Diy",
        "Charity",
        "Cooking",
        "Relaxation",
        "Music",
        "Busywork"
    )

    private lateinit var binding: ActivityActivitiesBinding

    private lateinit var participants: String
    private lateinit var price: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        participants = intent.getStringExtra(getString(R.string.participants_app)).toString()
        price = intent.getStringExtra(getString(R.string.price_app)).toString()

        initListView()
        initRandomBtn()
    }

    private fun initRandomBtn() {
        binding.btRandom.setOnClickListener {
            toActivityInfo("")
        }
    }

    private fun initListView() {
        val adapter =
            ArrayAdapter<String>(this, R.layout.template_types_item, R.id.tvItemTypes, typeList)
        binding.lvTypes.adapter = adapter
        binding.lvTypes.setOnItemClickListener { _, _, pos, _ ->
            toActivityInfo(typeList[pos])
        }
    }

    private fun toActivityInfo(type:String){
        val intentToInfoActivity = Intent(this,InfoActivity::class.java)
        intentToInfoActivity.putExtra(getString(R.string.participants_app),participants)
        intentToInfoActivity.putExtra(getString(R.string.type_app),type)
        intentToInfoActivity.putExtra(getString(R.string.price_app),price)
        startActivity(intentToInfoActivity)
    }
}