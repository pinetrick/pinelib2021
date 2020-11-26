package com.blueberrysolution.pinelib21.view.recycle_view


import androidx.recyclerview.widget.*
import com.blueberrysolution.pinelib21.app.app


/**
 *         使用方法
adapter = StopDetailAdapter(this);
refreshLoadmoreListener = RefreshLoadmoreListener(swipe_refreshlayout, ::onRefresh)
RecyViewSetup(buses_list, adapter).setOnRefreshLoadmoreListener(refreshLoadmoreListener).build()


 */
class RecyViewSetup {


    var recycleView: RecyclerView;
    var layoutManager = LinearLayoutManager(app())
    var animation = DefaultItemAnimator()
    var divider = DividerItemDecoration(app(), LinearLayoutManager.VERTICAL)
    var adapter: RecyclerView.Adapter<*>;


    constructor(recycleView: RecyclerView, adapter: RecyclerView.Adapter<*>){
        this.recycleView = recycleView;
        this.adapter = adapter;
    }

    fun setColumn(column: Int): RecyViewSetup{
        layoutManager = GridLayoutManager(app(), column)
        return this;
    }



    fun build(): RecyViewSetup {

        //设置布局管理器
        recycleView.setLayoutManager(layoutManager)
        recycleView.adapter = adapter;
        recycleView.setItemAnimator(animation)
        recycleView.addItemDecoration(divider)





        return this;
    }


}



