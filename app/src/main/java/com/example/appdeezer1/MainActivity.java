package com.example.appdeezer1;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private PlaylistAdapter adapter;
    private EditText search;
    private Toolbar toolbar;
    private ControllerPlaylist controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search_text);

        controller = new ControllerPlaylist(this);

        // Init recycler view with custom adapter for items in list
        adapter = new PlaylistAdapter(this,controller);
        recycler = findViewById(R.id.recycler_playlist);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        //Customize App toolbar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init handlers or listeners (buttons)

        findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button_search) {
                    controller.searchPlaylistOnDeezer(search.getText().toString(), new Callback() {
                        @Override
                        public void notify(Object result, int statusCode) {
                            Log.i("MAIN_REQUEST_PLAYLIST", statusCode == Callback.SUCESS_CODE ? "SUCESS" : "FAILED");
                            if (statusCode == Callback.SUCESS_CODE) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

}
