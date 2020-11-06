package com.blueberrysolution.pinelib21.view.view_pager

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.blueberrysolution.pinelib21.context.a

class ViewPagerSetup {


    var recycleView: ViewPager2;
    var divider = DividerItemDecoration(a(), LinearLayoutManager.VERTICAL)
    var adapter: RecyclerView.Adapter<*>;


    constructor(recycleView: ViewPager2, adapter: RecyclerView.Adapter<*>){
        this.recycleView = recycleView;
        this.adapter = adapter;
    }



    fun build(): ViewPagerSetup {

        recycleView.adapter = adapter;
        recycleView.addItemDecoration(divider)



        return this;
    }


}