package com.project3_bukucerita;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi tombol
        Button btnStart = findViewById(R.id.btnStart);
        Button btnShare = findViewById(R.id.btnShare);
        Button btnReview = findViewById(R.id.btnReview);

        //Pindah ke daftar cerita
        btnStart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StoryListActivity.class));
        });

        //Kirim link Play Store
        btnShare.setOnClickListener(v -> {
            String shareLink = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareit) + " " + shareLink);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Bagikan melalui"));
        });

        //Arahkan ke Play Store
        btnReview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));
            startActivity(intent);
        });
    }
}