package com.project3_bukucerita;

import java.io.Serializable;

public class Story implements Serializable {
    private String title;
    private String text;
    private String audio;
    private String image;

    public Story(String title, String text, String audio, String image) {
        this.title = title;
        this.text = text;
        this.audio = audio;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getAudio() {
        return audio;
    }

    public String getImage() {
        return image;
    }
}