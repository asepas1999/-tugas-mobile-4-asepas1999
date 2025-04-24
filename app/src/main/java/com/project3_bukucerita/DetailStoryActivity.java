package com.project3_bukucerita;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Locale;

public class DetailStoryActivity extends AppCompatActivity {
    private TextView tvTitle, tvStory;
    private ImageView ivImage, btnPlay;
    private MediaPlayer mediaPlayer = null;
    private TextToSpeech tts = null;
    private String audio, storyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        tvTitle = findViewById(R.id.tvTitle);
        tvStory = findViewById(R.id.tvStory);
        ivImage = findViewById(R.id.ivImage);
        btnPlay = findViewById(R.id.btnPlay);

        Story story = (Story) getIntent().getSerializableExtra("story");
        if (story != null) {
            tvTitle.setText(story.getTitle());
            tvStory.setText(story.getText());
            storyText = story.getText();
            audio = story.getAudio();

            Picasso.get().load(story.getImage()).into(ivImage);
        }

        btnPlay.setOnClickListener(v -> {
            if ("GT".equalsIgnoreCase(audio)) {
                if (tts == null){
                    btnPlay.setImageResource(R.drawable.baseline_stop_arrow_24);
                    tts = new TextToSpeech(this, status -> {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(new Locale("id", "ID"));
                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(this, "Bahasa Indonesia tidak didukung!", Toast.LENGTH_SHORT).show();
                            } else {
                                tts.setPitch(1.0f);
                                tts.setSpeechRate(1.0f);
                                tts.speak(storyText, TextToSpeech.QUEUE_FLUSH, null, null);
                                Toast.makeText(this, "Mesin sekarang sedang membacakan teks untuk anda!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    btnPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    tts.stop();
                    Toast.makeText(this, "Mesin sekarang stop membacakan teks !", Toast.LENGTH_SHORT).show();
                    tts = null;
                }
            } else {
                if (mediaPlayer == null) {
                    try {
                        AssetFileDescriptor afd = getAssets().openFd(audio);
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        afd.close();
                        mediaPlayer.prepare();
                        mediaPlayer.setOnCompletionListener(mp -> {
                            btnPlay.setImageResource(R.drawable.baseline_stop_arrow_24);
                            mediaPlayer.seekTo(0);
                        });
                        mediaPlayer.start();
                        Toast.makeText(this, "Mesin sekarang sedang membacakan teks untuk anda!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    btnPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    mediaPlayer.stop();
                    Toast.makeText(this, "Mesin sekarang stop membacakan teks !", Toast.LENGTH_SHORT).show();
                    mediaPlayer = null;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}