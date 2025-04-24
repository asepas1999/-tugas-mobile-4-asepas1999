package com.project3_bukucerita;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StoryData {

    public static List<Story> getStories(Context context) {
        List<Story> stories = new ArrayList<>();

        try {
            //Baca file JSON
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse JSON
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("Data");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject storyObj = arr.getJSONObject(i);
                String title = storyObj.getString("title");
                String text = storyObj.getString("story_text");
                String audio = storyObj.getString("audio");
                String image = storyObj.getString("image");

                stories.add(new Story(title, text, audio, image));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return stories;
    }
}