package com.engineer.reader.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.engineer.reader.R;
import com.engineer.reader.base.BaseActivity;
import com.engineer.reader.db.ImgCacheManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LargeImageActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.image)
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (getIntent() != null) {
            String pic = getIntent().getStringExtra("picUrl");
            String picPath = ImgCacheManager.createFile(this, pic).getAbsolutePath();
            Glide.with(this)
                    .load(picPath)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImage);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
