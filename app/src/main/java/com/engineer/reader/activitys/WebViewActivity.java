package com.engineer.reader.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.engineer.reader.R;
import com.engineer.reader.base.BaseActivity;


public class WebViewActivity extends BaseActivity {

    WebView mWebView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initWebView();

        if (getIntent() != null) {
            String url = getIntent().getStringExtra("url");
            String title = getIntent().getStringExtra("title");
            mToolbar.setTitle(title);
            mWebView.loadUrl(url);
        }
    }

    private void initWebView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mWebView = (WebView) findViewById(R.id.WebView);
        WebSettings settings = mWebView.getSettings();
        mWebView.setWebViewClient(new WebViewClient());
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fade_out);
    }
}
