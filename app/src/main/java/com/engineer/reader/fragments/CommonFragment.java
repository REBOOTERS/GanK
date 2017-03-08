package com.engineer.reader.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.engineer.reader.R;
import com.engineer.reader.activitys.WebViewActivity;
import com.engineer.reader.base.BaseListFragment;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.common.recyclerview.base.ViewHolder;

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
        TextView text = holder.getView(R.id.text);
//        text.setText(Html.fromHtml("<a href=\""
//                + ganHuo.getUrl() + "\">"
//                + ganHuo.getDesc() + "</a>"
//                + "[" + ganHuo.getWho() + "]"));
        text.setText(ganHuo.getDesc());
        text.setMovementMethod(LinkMovementMethod.getInstance());

        text.setOnClickListener(new View.OnClickListener() {
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