package com.test.ui.birddetail;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.test.MainActivity;
import com.test.R;
import com.test.databinding.FragmentDetailBinding;
import com.test.model.firstfragmentbird.BirdItem;

import java.lang.ref.WeakReference;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding fragmentDetailBinding;
    private BirdItem birdItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        MainActivity.RX.onNext("Details fragment created");
        birdItem = getArguments().getParcelable("detail");
        setToolBar();
        fragmentDetailBinding.setBirdItem(birdItem);

        return fragmentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDetailBinding.webView.getSettings().setJavaScriptEnabled(true);
        fragmentDetailBinding.webView.getSettings().setLoadWithOverviewMode(true);
        fragmentDetailBinding.webView.getSettings().setUseWideViewPort(true);
        fragmentDetailBinding.webView.setWebViewClient(new WebViewClient());
        fragmentDetailBinding.webView.loadUrl(birdItem.getWikiPediaUrl()); //https://www.google.com
    }

    private class WebViewClient extends android.webkit.WebViewClient {
        private WeakReference<ProgressBar> progressBarWeakReference =
                new WeakReference<ProgressBar>(fragmentDetailBinding.progressBar);

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (progressBarWeakReference.get() != null) {
                progressBarWeakReference.get().setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (error.toString() == "piglet")
                handler.cancel();
            else
                handler.proceed(); // Ignore SSL certificate errors
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressBarWeakReference.get() != null) {
                progressBarWeakReference.get().setVisibility(View.GONE);
            }
        }
    }

    /**
     * To set the tool bar tile and back left arrow button
     */
    private void setToolBar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("TestApp - " + birdItem.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentDetailBinding = null;
    }
}