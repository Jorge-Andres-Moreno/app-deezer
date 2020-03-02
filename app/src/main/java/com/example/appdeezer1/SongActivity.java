package com.example.appdeezer1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class SongActivity extends AppCompatActivity {

    private ImageView image_song;
    private TextView name_song;
    private TextView name_artist;
    private TextView name_album;
    private TextView duration;
    private Toolbar toolbar;

    private ControllerPlaylist controllerPlaylist;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_song);

        //Customize App toolbar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controllerPlaylist = new ControllerPlaylist(this);

        image_song = findViewById(R.id.image_song);
        name_song = findViewById(R.id.name_song);
        name_artist = findViewById(R.id.name_artist);
        name_album = findViewById(R.id.name_album);
        duration = findViewById(R.id.duration);

        controllerPlaylist.searchSong((long) getIntent().getExtras().get("songID"), new Callback() {
            @Override
            public void notify(Object result, int statusCode) {
                Picasso.get().load(controllerPlaylist.selectSong.getAlbum().getBigImageUrl()).into(image_song);
                name_song.setText(controllerPlaylist.selectSong.getTitle());
                name_artist.setText(controllerPlaylist.selectSong.getArtist().getName());
                name_album.setText(controllerPlaylist.selectSong.getAlbum().getTitle());

                int total = controllerPlaylist.selectSong.getDuration();
                int minutos = total / 60;
                int segundos = total % 60;

                duration.setText(minutos + ":" + segundos);
            }
        });

        final Activity me = this;
        findViewById(R.id.button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button_play)
                    controllerPlaylist.playSong(me);
            }
        });

        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button_back) {
                    onBackPressed();
                }
            }
        });

    }
}
