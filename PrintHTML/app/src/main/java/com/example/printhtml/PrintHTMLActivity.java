package com.example.printhtml;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrintHTMLActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_html);

        String htmlDoc = "" +
                "<html>" +
                "<body>" +
                "<h1>Android Print Framework</h1>" +
                "<p>Printing test.</p>" +
                "</body>" +
                "</html>";

        this.webView = new WebView(this);
        this.webView.setWebViewClient(new WebViewClientTest());
        this.webView.loadDataWithBaseURL(null, htmlDoc, "text/HTML", "UTF-8", null);
    }

    private class WebViewClientTest extends WebViewClient {

        private static final String TAG = "WebClient";

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "Page finished loading: " + url);
            createWebPrintJob(view);
            // Release memory after print job.
            PrintHTMLActivity.this.webView = null;
        }

        private void createWebPrintJob(WebView webView) {
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter();
            String jobName = getString(R.string.app_name) + " test";

            printManager.print(jobName, printDocumentAdapter, new PrintAttributes.Builder().build());
        }
    }

}
