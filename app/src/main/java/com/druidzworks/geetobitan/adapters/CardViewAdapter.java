package com.druidzworks.geetobitan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.druidzworks.geetobitan.R;
import com.druidzworks.geetobitan.entities.Song;

import java.util.List;

/**
 * Created by Saibal Ghosh on 8/20/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.SongListHolder> {
    private List<Song> songList;

    public  CardViewAdapter(List<Song> SongList)
    {
        this.songList = SongList;
    }

    @Override
    public int getItemCount()
    {
        return songList.size();
    }

    @Override
    public void onBindViewHolder(SongListHolder songListHolder, int i)
    {
        Song song = songList.get(i);
        songListHolder.txtSongTitle.setText(song.get_title());

    }

    @Override
    public SongListHolder onCreateViewHolder(ViewGroup viewgroup, int i)
    {
        View itemView = LayoutInflater.
                from(viewgroup.getContext()).
                inflate(R.layout.itemview_main, viewgroup, false);

        return new SongListHolder(itemView);
    }

    public static class SongListHolder extends RecyclerView.ViewHolder
    {
        protected TextView txtSongTitle;

        public SongListHolder(View view)
        {
            super(view);
            txtSongTitle = (TextView) view.findViewById(R.id.songtitle);
        }
    }
}