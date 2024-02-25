package com.voven4ek.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.webkit.WebViewAssetLoader;
import androidx.webkit.WebViewClientCompat;

public class FormulaView extends WebView {
    public FormulaView(@NonNull Context context) {
        super(context);
    }

    public FormulaView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.setWebViewClient(new WebViewClientCompat());
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                view.evaluateJavascript("function init() {\n" +
                        "          const mf = document.getElementById('mf');\n" +
                        "          const out = document.getElementById('output');\n" +
                        "          const res = document.getElementById('result');\n" +
                        "          out.innerHTML = mf.value;\n" +
                        "          mf.addEventListener(\"input\", () => {\n" +
                        "            out.innerHTML = mf.value;\n" +
                        "          })\n" +
                        "          document.getElementById('save-to-png').addEventListener('click', (ev) => {\n" +
                        "            html2canvas(document.querySelector(\"#output\")).then((canvas) => {\n" +
                        "              res.innerHTML = canvas.toDataURL();\n" +
                        "            });\n" +
                        "          });\n" +
                        "      }\n" +
                        "      init();", value -> {});
            }
        });

        loadData("<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>untitled</title>\n" +
                "    <script defer src=\"https://unpkg.com/mathlive@0.98.6/dist/mathlive.min.js\"></script>\n" +
                "    <script src=\"https://html2canvas.hertzen.com/dist/html2canvas.js\"></script>\n" +
                " </head>\n" +
                "  <body>\n" +
                "    <div id=\"inputs\">\n" +
                "        <h2>Formula</h2>\n" +
                "        <math-field id=\"mf\" math-virtual-keyboard-policy=\"sandboxed\"></math-field>\n" +
                "        <h2>LaTeX</h2>\n" +
                "      </div>\n" +
                "      <math-field read-only style=\"display:inline-block; transform: scale(3); top: -900%; position: fixed\" id=\"output\">\n" +
                "        x=\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}\n" +
                "      </math-field>\n" +
                "      <div id=\"result\">\n" +
                "      \n" +
                "      </div>\n" +
                "      <div class=\"buttonbar\">\n" +
                "        <button id=\"save-to-png\">Save to PNG</button>\n" +
                "      </div>\n" +
                "  </body>\n" +
                "</html>", "text/html; charset=utf-8", "UTF-8");
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        evaluateJavascript("init()", value -> {});
    }
}
