package com.example.cities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

/**
 * Created by pedro on 23/08/2016.
 */
public class CitiesDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String[] links = getResources().getStringArray(R.array.pages);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());

        WebView webView = new WebView(getActivity());
        webView.setWebViewClient(new SwAWebClient());
        webView.setPadding(padding, padding, padding, padding);
        webView.loadUrl(links[getShownIndex()]);

        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.addView(webView);

        return scrollView;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    public static CitiesDetailsFragment newInstance(int index) {
        CitiesDetailsFragment citiesDetailsFragment = new CitiesDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        citiesDetailsFragment.setArguments(bundle);

        return citiesDetailsFragment;
    }

    private class SwAWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

}
