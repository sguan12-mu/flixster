package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    // the movie to display
    Movie movie;

    // declare view binding
    ActivityMovieDetailsBinding activityMovieDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMovieDetailsBinding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = activityMovieDetailsBinding.getRoot();
        setContentView(view);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        activityMovieDetailsBinding.tvTitle.setText(movie.getTitle());
        activityMovieDetailsBinding.tvOverview.setText(movie.getOverview());

        // set movie poster
        String imageUrl;
        // if phone is landscape
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getBackdropPath();
            Glide.with(this).load(imageUrl).placeholder(R.drawable.flicks_backdrop_placeholder).into(activityMovieDetailsBinding.ivPoster);
        } else {
            imageUrl = movie.getPosterPath();
            Glide.with(this).load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder).into(activityMovieDetailsBinding.ivPoster);
        }

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        activityMovieDetailsBinding.rbVoteAverage.setRating(voteAverage / 2.0f);
    }
}