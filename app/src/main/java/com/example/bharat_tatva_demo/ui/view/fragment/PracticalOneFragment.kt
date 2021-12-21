package com.example.bharat_tatva_demo.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bharat_tatva_demo.R
import com.example.bharat_tatva_demo.data.GridModel
import com.example.bharat_tatva_demo.databinding.FragmentOneBinding
import com.example.bharat_tatva_demo.ui.view.activity.MainActivity
import com.example.bharat_tatva_demo.ui.view.adapter.GridAdapter
import com.example.bharat_tatva_demo.ui.viewmodel.MainViewModel
import com.example.bharat_tatva_demo.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class PracticalOneFragment : Fragment() {

    private lateinit var binding : FragmentOneBinding
    private val viewModel: MainViewModel by viewModels()
    private var mAdapter : GridAdapter? = null
    private var gridList : ArrayList<GridModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneBinding.inflate(inflater,container,false)
        binding.mainData = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val layoutManager = StaggeredGridLayoutManager(viewModel.getSpanCountForGivenNumber(),
            RecyclerView.VERTICAL)
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

    override fun onDestroyView() {
        (activity as MainActivity).showButtons()
        super.onDestroyView()
    }
}