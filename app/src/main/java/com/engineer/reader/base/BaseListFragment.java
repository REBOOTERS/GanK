package com.engineer.reader.base;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.engineer.reader.R;
import com.engineer.reader.activitys.WebViewActivity;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.common.recyclerview.CommonAdapter;
import com.engineer.reader.common.recyclerview.MultiItemTypeAdapter;
import com.engineer.reader.common.recyclerview.base.ViewHolder;
import com.engineer.reader.common.recyclerview.decoration.OffsetDecoration;
import com.engineer.reader.common.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.engineer.reader.http.HttpListener;
import com.engineer.reader.http.RequestManager;
import com.engineer.reader.utils.CommonUtils;
import com.engineer.reader.widget.MultipleStatusView;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseListFragment<T> extends BaseFragment
        implements OnRefreshListener, OnLoadMoreListener, MultiItemTypeAdapter.OnItemClickListener {

    @Bind(R.id.swipe_target)
    protected RecyclerView mRecyclerView;
    @Bind(R.id.content_view)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @Bind(R.id.multipleStatusView)
    MultipleStatusView mMultipleStatusView;
    protected CommonAdapter<T> commonAdapter;
    protected HeaderAndFooterWrapper headerAndFooterWrapper;
    protected List<T> list = new ArrayList<>();
    protected HashMap<String, String> map = new HashMap<>();
    protected int page = 1;
    protected int pageSize = 30;
    private Type type;


    protected OffsetDecoration decoration = new OffsetDecoration();

    public BaseListFragment() {
        type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    protected void setLoadMoreEnabled(boolean enable) {
        mSwipeToLoadLayout.setLoadMoreEnabled(enable);
    }

    public abstract int getItemLayout();

    public abstract void fillValue(ViewHolder holder, T t, int position);

    protected abstract String getUrl();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initViews() {
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mMultipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick()) {
                    return;
                }
                onRefresh();
            }
        });
        commonAdapter = new CommonAdapter<T>(getActivity(), getItemLayout(), list) {
            @Override
            public void convert(ViewHolder holder, T t, int position) {
                fillValue(holder, t, position);
            }
        };
        commonAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
        mRecyclerView.setAdapter(headerAndFooterWrapper);

        mRecyclerView.removeItemDecoration(decoration);
        mRecyclerView.addItemDecoration(decoration);
        onRefresh();
    }

    @Override
    protected void lazyFetchData() {
        onRefresh();

    }

    private void getData(final boolean isRefresh) {

        RequestManager.getList(tag, getUrl(), type, false, new HttpListener() {
            @Override
            public void onSuccess(Object o) {
                ArrayList<T> result = (ArrayList<T>) o;
                if (isRefresh) {
                    page = 2;
                    list.clear();
                } else {
                    page++;
                }
                list.addAll(result);
                if (list.size() > 0) {
                    mMultipleStatusView.showContent();
                } else {
                    mMultipleStatusView.showEmpty();
                }
                commonAdapter.notifyDataSetChanged();
                if (mSwipeToLoadLayout.isRefreshing()) {
                    mSwipeToLoadLayout.setRefreshing(false);
                }
                if (mSwipeToLoadLayout.isLoadingMore()) {
                    mSwipeToLoadLayout.setLoadingMore(false);
                }

            }

            @Override
            public void onFailure(int errorType, String message) {
                if (mSwipeToLoadLayout != null && mMultipleStatusView != null) {
                    if (list.size() < 1) {
                        if (errorType == 5) {
                            mMultipleStatusView.showNoNetwork();
                        } else {
                            mMultipleStatusView.showError();
                        }
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    if (mSwipeToLoadLayout.isRefreshing()) {
                        mSwipeToLoadLayout.setRefreshing(false);
                    }
                    if (mSwipeToLoadLayout.isLoadingMore()) {
                        mSwipeToLoadLayout.setLoadingMore(false);
                    }
                }
            }
        });

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        GanHuo ganHuo = (GanHuo) list.get(position);
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra("url", ganHuo.getUrl());
        intent.putExtra("title", ganHuo.getDesc());
        getContext().startActivity(intent);
        Activity mContext = (Activity) getContext();
        mContext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        if (list.size() < 1)
            mMultipleStatusView.showLoading();
        page = 1;
        getData(true);
    }

    @Override
    public void onLoadMore() {
        getData(false);
    }
}
