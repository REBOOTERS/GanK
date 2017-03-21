package com.engineer.reader.fragments;


import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.engineer.reader.R;
import com.engineer.reader.base.BaseGridFragment;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.common.recyclerview.base.ViewHolder;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuLiFragment extends BaseGridFragment<GanHuo> {

    @Override
    public int getItemLayout() {
        return R.layout.item_fuli;
    }

    @Override
    public void fillValue(ViewHolder holder, GanHuo ganHuo, int position) {
        ImageView mImage = holder.getView(R.id.image);
        Picasso.with(getContext()).load(ganHuo.getUrl()).placeholder(R.mipmap.avatar).into(mImage);
    }

    @Override
    protected String getUrl() {
        return "http://gank.io/api/data/福利/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
    }
}
