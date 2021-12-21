package com.example.bharat_tatva_demo.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bharat_tatva_demo.R
import com.example.bharat_tatva_demo.databinding.ActivityMainBinding
import com.example.bharat_tatva_demo.ui.view.fragment.PracticalOneFragment
import com.example.bharat_tatva_demo.ui.view.fragment.PracticalTwoFragment
import com.example.bharat_tatva_demo.utils.addFragment
import com.example.bharat_tatva_demo.utils.hide
import com.example.bharat_tatva_demo.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.btnPracticalOne.setOnClickListener {
            addFragment(R.id.fl_container,PracticalOneFragment())
            binding.groupBtn.hide()
        }
        binding.btnPracticalTwo.setOnClickListener {
            addFragment(R.id.fl_container, PracticalTwoFragment())
            binding.groupBtn.hide()
        }
    }

    fun showButtons() {
        binding.groupBtn.show()
    }
}