package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityListener{
    TabItem rock_tab, classic_tab, pop_tab;
    TabLayout tabLayout;
    ViewPager simpleViewPager;
    String music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        rock_tab = findViewById(R.id.rock_tab);
        classic_tab = findViewById(R.id.classic_tab);
        pop_tab = findViewById(R.id.pop_tab);
//        simpleViewPager = findViewById(R.id.simpleViewPager);

        music = "rock";
        new MusicQuery().execute();

        Fragment fragment = new FirstFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        music = "rock";
                        new MusicQuery().execute();
                        fragment = new FirstFragment();
                        break;
                    case 1:
                        music = "classic";
                        new MusicQuery().execute();
                        fragment = new SecondFragment();
                        break;
                    case 2:
                        music = "pop";
                        new MusicQuery().execute();
                        fragment = new ThirdFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void previewMusic(MusicInfoDetails item) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(item.getPreviewUrl()), "audio/*");
        startActivity(intent);

    }

    class MusicQuery extends AsyncTask<Void, Void, MusicInfoPojo>{

        @Override
        protected MusicInfoPojo doInBackground(Void... voids) {

            MusicInfoPojo data = new MusicInfoPojo();
            try {
                data = createPojo(setupNetWorkCall());
            } catch (Exception err){
                err.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(MusicInfoPojo s) {
            super.onPostExecute(s);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            FragmentResult fragmentResult = FragmentResult.newInstance(s);
            fragmentTransaction.add(R.id.fragment_container, fragmentResult);
            fragmentTransaction.commit();
        }
    }


    private MusicInfoPojo createPojo(String data) throws Exception{
        MusicInfoPojo dataPojo = new MusicInfoPojo();
        List<ItemDetails> tempItemDetailsList = new ArrayList<>();
        JSONObject musicResponse = new JSONObject(data);

        for (int i = 0; i < musicResponse.getJSONArray("results").length(); i++) {

            JSONObject firstItem = musicResponse.
                    getJSONArray("results").
                    getJSONObject(i);

            MusicInfoDetails musicInfoDetails = new MusicInfoDetails();

            if(firstItem.has("artistName")){
                musicInfoDetails.setArtistName(firstItem.getString("artistName"));
            }
            else{
                musicInfoDetails.setArtistName("");
            }
            if(firstItem.has("collectionName")){
                musicInfoDetails.setCollectionName(firstItem.getString("collectionName"));
            }
            else{
                musicInfoDetails.setCollectionName("");
            }
            if(firstItem.has("trackPrice")){
                musicInfoDetails.setTrackPrice(firstItem.getDouble("trackPrice"));
            }
            else{
                musicInfoDetails.setTrackPrice(0.00);
            }
            if(firstItem.has("artworkUrl100")){
                musicInfoDetails.setArtworkUrl100(firstItem.getString("artworkUrl100"));
            }
            else{
                musicInfoDetails.setArtworkUrl100("");
            }
            if(firstItem.has("previewUrl")){
                musicInfoDetails.setPreviewUrl(firstItem.getString("previewUrl"));
            }
            else{
                musicInfoDetails.setPreviewUrl("");
            }

            ItemDetails itemDetails = new ItemDetails();
            itemDetails.setMusicInfo(musicInfoDetails);

            tempItemDetailsList.add(itemDetails);

            dataPojo.setItems(tempItemDetailsList);

        }
        return dataPojo;
    }
    public String setupNetWorkCall() throws Exception{
        String BASE_URL = "";

        if(music.equals("rock")){
            BASE_URL =
                    "https://itunes.apple.com/search?term=rock&amp;amp;media=music&amp;amp;entity=song&amp;amp;limit=50";
        }
        if(music.equals("classic")){
            BASE_URL =
                    "https://itunes.apple.com/search?term=classick&amp;amp;media=music&amp;amp;entity=song&amp;amp;limit=50";
        }
        if(music.equals("pop")){
            BASE_URL =
                    "https://itunes.apple.com/search?term=pop&amp;amp;media=music&amp;amp;entity=song&amp;amp;limit=50";
        }

        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        connection.connect();
        int responseCode = connection.getResponseCode();
        InputStream inputStream = connection.getInputStream();
        String data = parseRawData(inputStream);

        return data;

    }
    public String parseRawData(InputStream is) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while((line = reader.readLine()) != null){
            builder.append(line +"\n");
        }
        if(builder.length() == 0 )
            return null;

        return builder.toString();
    }
    class DownloadImage extends AsyncTask<Void, Void, Bitmap>{

        String url;
        DownloadImage(String url){
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {

                URL downloadUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(10000);
                connection.setDoInput(true);
                connection.connect();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

            } catch (MalformedURLException urlException){
                urlException.printStackTrace();
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
