package com.engineer.reader.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.engineer.reader.R;
import com.engineer.reader.activitys.GalleryActivity;
import com.engineer.reader.base.BaseGridFragment;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.common.recyclerview.base.ViewHolder;
import com.engineer.reader.db.ImgCacheIntentService;
import com.engineer.reader.db.ImgCacheManager;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuLiFragment extends BaseGridFragment<GanHuo> {

    @Override
    public int getItemLayout() {
        return R.layout.item_fuli;
    }

    @Override
    public void fillValue(ViewHolder holder, final List<GanHuo> t, final int position) {
        ImageView mImage = holder.getView(R.id.image);
        if (!ImgCacheManager.isFileExist(getContext(), t.get(position).getUrl())) {
            ImgCacheIntentService.startActionBaz(getContext(), t.get(position).getUrl(), "");
        }
        Picasso.with(getContext()).load(t.get(position).getUrl()).placeholder(R.mipmap.avatar).into(mImage);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getContext(), GalleryActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("beans", (Serializable) t);
                mIntent.putExtras(mBundle);
                mIntent.putExtra("position", position);
                getContext().startActivity(mIntent);
            }
        });
    }





    @Override
    protected String getUrl() {
        return "http://gank.io/api/data/福利/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
    }

    @Override
    protected String getDataType() {
        return "福利";
    }

    @Override
    protected String getPageCount() {
        return String.valueOf(page);
    }
}
