package com.example.appdeezer1;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongHolder> {

    private Activity context;
    private ControllerPlaylist controllerPlaylist;

    SongsAdapter(Activity context, ControllerPlaylist controllerPlaylist) {
        this.context = context;
        this.controllerPlaylist = controllerPlaylist;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_song, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {

        Picasso.get().load(controllerPlaylist.selectPlaylist.getTracks().get(position).getAlbum().getImageUrl()).into(holder.image_song);
        holder.name_song.setText("Nombre de la canción: " + controllerPlaylist.selectPlaylist.getTracks().get(position).getTitle());
        holder.name_artist.setText("Artista de la canción: " + controllerPlaylist.selectPlaylist.getTracks().get(position).getArtist().getName());
        holder.release_year.setText("Año de lanzamiento: " + controllerPlaylist.selectPlaylist.getTracks().get(position).getAlbum().getReleaseDate());
        holder.pos = position;

    }

    @Override
    public int getItemCount() {
        return controllerPlaylist.selectPlaylist != null ? controllerPlaylist.selectPlaylist.getTracks().size() : 0;
    }

    public class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int pos;
        ImageView image_song;
        TextView name_song;
        TextView name_artist;
        TextView release_year;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            image_song = itemView.findViewById(R.id.image_song);
            name_song = itemView.findViewById(R.id.name_song);
            name_artist = itemView.findViewById(R.id.name_artist);
            release_year = itemView.findViewById(R.id.release_year);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.card) {
                Intent in = new Intent(context, SongActivity.class);
                in.putExtra("songID", controllerPlaylist.selectPlaylist.getTracks().get(pos).getId());
                context.startActivity(in);
            }
        }
    }
}
