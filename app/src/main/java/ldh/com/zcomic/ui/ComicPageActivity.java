package ldh.com.zcomic.ui;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;

/**
 * Created by allen liu on 2018/5/13.
 */

public class ComicPageActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.toolbar_chapter)
    Toolbar mToolbar;


    private String mUrl;
    private String mTitle;

    @Override
    protected int setLayoutResID() {
        return R.layout.comic_read_page;
    }

    @Override
    protected void initView() {

        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mTitle);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    protected void initListener() {
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
    });
          mWebView.loadUrl(mUrl);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


