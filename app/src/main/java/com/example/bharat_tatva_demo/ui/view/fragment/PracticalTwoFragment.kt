package com.example.bharat_tatva_demo.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bharat_tatva_demo.data.response.UserData
import com.example.bharat_tatva_demo.databinding.FragmentTwoBinding
import com.example.bharat_tatva_demo.ui.view.activity.MainActivity
import com.example.bharat_tatva_demo.ui.view.adapter.UserAdapter
import com.example.bharat_tatva_demo.ui.viewmodel.PracticalOneViewModel
import com.example.bharat_tatva_demo.utils.PaginationScrollListener
import com.example.bharat_tatva_demo.utils.hide
import com.example.bharat_tatva_demo.utils.show
import com.example.bharat_tatva_demo.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class PracticalTwoFragment : Fragment() {

    private val viewModel: PracticalOneViewModel by viewModels()
    private lateinit var binding: FragmentTwoBinding
    private var mUserList: ArrayList<UserData> = ArrayList()
    private var mAdapter: UserAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var isLoadMore : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intObserver()
    }

    private fun intObserver() {
        viewModel.userDataList.observe(this, {
            if (!it.isNullOrEmpty()) {
                mUserList.addAll(it)
                mAdapter?.notifyDataSetChanged()

            }
        })

        viewModel.isLoading.observe(this, {
            if (it != null) {
                if (it) {
                    binding.progressBar.show()
                } else {
                    binding.progressBar.hide()
                }
            }
        })

        viewModel.errorMessage.observe(this, {
            if (!it.isNullOrEmpty()) {
                showToast(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        setupRecyclerView()
        viewModel.getUserData()
    }

    private fun setupRecyclerView() {
        mAdapter = UserAdapter(mUserList)
        binding.apply {
            rvUser.layoutManager = linearLayoutManager
            rvUser.adapter = mAdapter
        }
        linearLayoutManager?.let {
            binding.rvUser.addOnScrollListener(object : PaginationScrollListener(it) {
                override fun isLoading(): Boolean {
                    return viewModel.isLoading.value!!
                }

                override fun loadMoreItems() {
                    viewModel.loadData()
                }
            })
        }
    }

    override fun onDestroyView() {
        (activity as MainActivity).showButtons()
        super.onDestroyView()
    }
}