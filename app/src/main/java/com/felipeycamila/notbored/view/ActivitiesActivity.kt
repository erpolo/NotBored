package com.felipeycamila.notbored.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.felipeycamila.notbored.R
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

    //Start the view for this activity by displaying the list of activities
    private fun initListView() {
        val adapter =
            ArrayAdapter<String>(this, R.layout.template_types_item, R.id.tvItemTypes, typeList)
        binding.lvTypes.adapter = adapter
        binding.lvTypes.setOnItemClickListener { _, _, pos, _ ->
            toActivityInfo(typeList[pos])
        }
    }

    /**
     *This function is launched when an activity is clicked, it gathers all the information and calls the next activity (infoActivity)
     * @param type (the type of activity selected)
     */
    private fun toActivityInfo(type:String){
        val intentToInfoActivity = Intent(this, InfoActivity::class.java)
        intentToInfoActivity.putExtra(getString(R.string.participants_app),participants)
        intentToInfoActivity.putExtra(getString(R.string.type_app),type)
        intentToInfoActivity.putExtra(getString(R.string.price_app),price)
        startActivity(intentToInfoActivity)
    }
}