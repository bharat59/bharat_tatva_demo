package com.example.bharat_tatva_demo.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun isLoading() : Boolean
    abstract fun loadMoreItems()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading()) {
            if (visibleItemCount + firstVisiblePosition >= totalItemCount && firstVisiblePosition >= 0){
             loadMoreItems()
            }
        }
    }
}