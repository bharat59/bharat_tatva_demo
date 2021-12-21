package com.example.bharat_tatva_demo.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bharat_tatva_demo.R
import com.example.bharat_tatva_demo.data.GridModel
import com.example.bharat_tatva_demo.databinding.ActivityMainBinding
import com.example.bharat_tatva_demo.ui.view.adapter.GridAdapter
import com.example.bharat_tatva_demo.ui.viewmodel.MainViewModel
import com.example.bharat_tatva_demo.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var mAdapter : GridAdapter? = null
    private var gridList : ArrayList<GridModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mainData = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        setContentView(binding.root)
        initObservers()
        init()
    }

    private fun init() {
        binding.btnSubmit.setOnClickListener {
            if (viewModel.checkIfNumberIsSquaredRoot()) {
                // create grids
                viewModel.getGridModelData()
                setupRecyclerGrid()
                viewModel.startGame()
            } else {
                showToast(getString(R.string.msg_number_is_not_rooted))
            }
        }
    }

    private fun initObservers() {
        viewModel.mGridModelList.observe(this,{
            if (!it.isNullOrEmpty()){
                gridList.clear()
                gridList.addAll(it)
                mAdapter?.notifyDataSetChanged()
            }
        })

        viewModel.isWinner.observe(this,{ bool ->
            if (bool != null && bool) {
                showToast(getString(R.string.msg_you_won_the_game))
            }
        })
    }

    private fun setupRecyclerGrid() {
        val layoutManager = StaggeredGridLayoutManager(viewModel.getSpanCountForGivenNumber(),RecyclerView.VERTICAL)
        mAdapter = GridAdapter(gridList, object : GridAdapter.GridClickListener{
            override fun onGridClick(position: Int) {
                viewModel.saveCheckPoint(position)
                showToast(position.plus(1).toString())
            }

        })
        binding.apply {
            rvGrids.layoutManager = layoutManager
            rvGrids.adapter = mAdapter
        }
        mAdapter?.notifyDataSetChanged()
    }
}