package com.engineer.reader.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.engineer.reader.R;
import com.engineer.reader.activitys.WebViewActivity;
import com.engineer.reader.base.BaseListFragment;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.common.recyclerview.base.ViewHolder;
import com.engineer.reader.utils.CommonUtils;

import me.xiaopan.android.widget.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends BaseListFragment<GanHuo> {

    public static final String ARG_TYPE = "type";

    private String type;

    public static CommonFragment newInstance(String type) {

        Bundle args = new Bundle();

        CommonFragment fragment = new CommonFragment();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            type = getArguments().getString(ARG_TYPE);

    }

    @Override
    public int getItemLayout() {
        return R.layout.item_common;
    }

    @Override
    public void fillValue(final ViewHolder holder, final GanHuo ganHuo, int position) {
        if (CommonUtils.isWeibo(ganHuo.getUrl())) {
            return;
        }

        ImageView imageView = holder.getView(R.id.image);
        if (ganHuo.getImages() != null && ganHuo.getImages().size() > 0) {
            if (!TextUtils.isEmpty(ganHuo.getImages().get(0))) {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(ganHuo.getImages().get(0)).into(imageView);
            }
        } else {
            imageView.setVisibility(View.GONE);
        }


        TextView text = holder.getView(R.id.text);
        text.setText(ganHuo.getDesc());
        text.setMovementMethod(LinkMovementMethod.getInstance());

        TextView who = holder.getView(R.id.who);
        who.setText(ganHuo.getWho());

        TextView time = holder.getView(R.id.updateTime);
        time.setText(CommonUtils.formatTime(ganHuo.getPublishedAt()));

        CardView shell = holder.getView(R.id.shell);
        shell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", ganHuo.getUrl());
                intent.putExtra("title", ganHuo.getDesc());
                getContext().startActivity(intent);
                Activity mContext = (Activity) getContext();
                mContext.overridePendingTransition(R.anim.fade_in, 0);
            }
        });
    }

    @Override
    protected String getUrl() {
        return "http://gank.io/api/data/" + type + "/"
                + String.valueOf(pageSize) + "/"
                + String.valueOf(page);
    }


}