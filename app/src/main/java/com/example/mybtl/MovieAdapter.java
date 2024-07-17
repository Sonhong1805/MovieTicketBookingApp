package com.example.mybtl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;

    private  String email;

    public MovieAdapter(Context context, ArrayList<Movie> movies, String email) {
        this.context = context;
        this.movies = movies;
        this.email = email;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        if (movie == null){
            return;
        }
        holder.imv_image.setImageResource(movie.getImage());
        holder.tv_name.setText(movie.getName());

        // gửi data movie sang trang chi tiết phim
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("id", movie.getId());
            intent.putExtra("name", movie.getName());
            intent.putExtra("image", movie.getImage());
            intent.putExtra("content", movie.getContent());
            intent.putExtra("category", movie.getCategory());
            intent.putExtra("trailer", movie.getTrailer());
            intent.putExtra("premiere", movie.getPremiere());
            intent.putExtra("price", movie.getPrice());
            intent.putExtra("email", email);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(movies !=null)
            return movies.size();
        return 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView imv_image;
        TextView tv_name;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_image = itemView.findViewById(R.id.image_movie);
            tv_name = itemView.findViewById(R.id.title_movie);
        }
    }
}
