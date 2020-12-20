package com.blueberrysolution.pinelib21.view.recycle_view


import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blueberrysolution.pinelib21.app.app
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.util.*


/**
 *         使用方法
 *
主App需要则个框架
implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'


adapter = StopDetailAdapter(this);
refreshLoadmoreListener = RefreshLoadmoreListener(swipe_refreshlayout, ::onRefresh)
RecyViewSetup(buses_list, adapter).setOnRefreshLoadmoreListener(refreshLoadmoreListener).build()


 */
class RecyViewSetup : SwipeRefreshLayout.OnRefreshListener {


    var recycleView: RecyclerView;
    var layoutManager = LinearLayoutManager(app())
    var fLayoutManager: FlexboxLayoutManager? = null;
    var animation = DefaultItemAnimator()
    var divider = DividerItemDecoration(app(), LinearLayoutManager.VERTICAL)
    var adapter: RecyclerView.Adapter<*>;


    var swipe_refreshlayout: SwipeRefreshLayout? = null
    var onRefreshCallbackFunction: () -> Unit = ::onRefresh;
    var onLoadmoreCallbackFunction: () -> Unit = ::onLoadmoreInner;

    constructor(recycleView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        this.recycleView = recycleView;
        this.adapter = adapter;
    }

    fun setColumn(column: Int): RecyViewSetup {
        layoutManager = GridLayoutManager(app(), column)
        return this;
    }
    //自动换行
    fun setIsFlexBox(isFlexBox: Boolean): RecyViewSetup {
        if (isFlexBox) {
            fLayoutManager = FlexboxLayoutManager(app())
            fLayoutManager!!.flexDirection = FlexDirection.ROW
            fLayoutManager!!.justifyContent = JustifyContent.FLEX_START
        } else {
            fLayoutManager = null;
        }

        return this;
    }

    fun 清理默认分割线(): RecyViewSetup {
        if (recycleView.itemDecorationCount > 0)
            recycleView.removeItemDecorationAt(0)
        return this;
    }

    fun setOnRefreshLoadmoreListener(
        swipe_refreshlayout: SwipeRefreshLayout? = null,
        onRefresh: () -> Unit = ::onRefresh,
        onLoadmore: () -> Unit = ::onLoadmoreInner
    ): RecyViewSetup {
        this.swipe_refreshlayout = swipe_refreshlayout;
        this.onRefreshCallbackFunction = onRefresh;
        this.onLoadmoreCallbackFunction = onLoadmore;
        return this
    }

    var lastCallbackTime = Date();
    fun OnRefreshCallback() {
        var millSec = Date().time - lastCallbackTime.time;
        if (millSec > 500) {
            setIsRefreshing(true)
            lastCallbackTime = Date();
            onRefreshCallbackFunction();
        }

    }

    fun OnLoadMoreCallback() {
        var millSec = Date().time - lastCallbackTime.time;
        if (millSec > 500) {
            lastCallbackTime = Date();
            onLoadmoreCallbackFunction();
        }
    }

    fun setIsRefreshing(isRefresh: Boolean) {
        swipe_refreshlayout?.isRefreshing = isRefresh;
    }

    fun build(isInScoreView: Boolean = false): RecyViewSetup {

        //设置布局管理器
        if (fLayoutManager == null) {
            recycleView.setLayoutManager(layoutManager)
        } else {
            recycleView.setLayoutManager(fLayoutManager!!)
        }

        recycleView.adapter = adapter;
        recycleView.setItemAnimator(animation)
        recycleView.addItemDecoration(divider)
        if (isInScoreView) {
            recycleView.setHasFixedSize(true);
            recycleView.setNestedScrollingEnabled(false);
        }

        if (swipe_refreshlayout != null) {
            swipe_refreshlayout!!.setOnRefreshListener(this)

            var loadingMoreListener = object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    var layoutManager = recyclerView.layoutManager;
                    if (layoutManager is LinearLayoutManager) {
                        var firstVisablePosition = layoutManager.findFirstVisibleItemPosition();
                        if (firstVisablePosition == 0) {
                            if (!recyclerView.canScrollVertically(0)) {
                                OnRefreshCallback();
                            }

                        } else {
                            if (!recyclerView.canScrollVertically(1)) {
                                OnLoadMoreCallback();
                            }
                        }
                    } else {
                        if (!recyclerView.canScrollVertically(1)) {
                            OnLoadMoreCallback();
                        }
                    }

                }

            }

            recycleView.addOnScrollListener(loadingMoreListener);
        }



        return this;
    }

    override fun onRefresh() {

        OnRefreshCallback();
    }


    fun onLoadmoreInner() {

    }


}



