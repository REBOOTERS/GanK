package com.engineer.reader.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.engineer.reader.R;
import com.engineer.reader.activitys.WebViewActivity;
import com.engineer.reader.base.BaseListFragment;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.common.recyclerview.base.ViewHolder;
import com.engineer.reader.event.SkinChangeEvent;
import com.engineer.reader.utils.ThemeUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends BaseListFragment<GanHuo> {
    String type = "all";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_common;
    }


    @Override
    public void fillValue(ViewHolder holder, final GanHuo ganHuo, int position) {
        ImageView mImage = holder.getView(R.id.image);
        TextView mText = holder.getView(R.id.text);
        if (ganHuo.getType().equals("福利")) {
            mImage.setVisibility(View.VISIBLE);
            mText.setVisibility(View.GONE);
            Picasso.with(getContext()).load(ganHuo.getUrl()).placeholder(R.mipmap.avatar).into(mImage);
        } else {
            mImage.setVisibility(View.GONE);
            mText.setVisibility(View.VISIBLE);
            mText.setLinkTextColor(ThemeUtils.getThemeColor(getActivity(), R.attr.colorPrimary));
            mText.setText(ganHuo.getDesc());
            mText.setMovementMethod(LinkMovementMethod.getInstance());

            mText.setOnClickListener(new View.OnClickListener() {
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

    }


    @Override
    protected String getUrl() {
        return "http://gank.io/api/data/" + type + "/"
                + String.valueOf(pageSize) + "/"
                + String.valueOf(page);
    }

    @Subscribe
    public void onEvent(SkinChangeEvent event) {
        headerAndFooterWrapper.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}