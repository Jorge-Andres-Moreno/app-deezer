package com.example.appdeezer1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.ArrayList;

public class ControllerPlaylist {

    final private String applicationID = "398524";
    private DeezerConnect deezerConnect;
    private Context context;

    public ArrayList<Playlist> playlists;

    public ControllerPlaylist(Context context) {
        this.context = context;
        connection(context);
        Toast.makeText(context, deezerConnect != null ? "SUCESS CONECTION" : "FAILED CONECTION", Toast.LENGTH_SHORT).show();
        playlists = new ArrayList<>();
    }


    public boolean connection(Context context) {
        return (deezerConnect = new DeezerConnect(context, applicationID)) != null;
    }

    public void searchPlaylistOnDeezer(String playlist, final Callback callback) {
        Log.i("MAIN_REQUEST_PLAYLIST", "SUCESS");

        RequestListener requestListener = new JsonRequestListener() {
            @Override
            public void onResult(Object o, Object o1) {
                Toast.makeText(context, "onResult", Toast.LENGTH_SHORT).show();
                callback.notify(o, Callback.SUCESS_CODE);
                playlists = (ArrayList<Playlist>) o;

            }

            @Override
            public void onUnparsedResult(String s, Object o) {
                Toast.makeText(context, "onUnparsedResult", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Exception e, Object o) {
                Toast.makeText(context, "onException", Toast.LENGTH_SHORT).show();
                callback.notify(e, Callback.SUCESS_CODE);
            }
        };
        DeezerRequest deezerRequest = DeezerRequestFactory.requestSearchPlaylists(playlist);
        deezerRequest.setId("myRequest");
        deezerConnect.requestAsync(deezerRequest, requestListener);
    }


}
