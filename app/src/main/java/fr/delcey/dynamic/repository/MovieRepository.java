package fr.delcey.dynamic.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovieRepository {

    public LiveData<List<Movie>> getMovies() {
        MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();

        AsyncTask<Void, Void,List<Movie>> asyncTask = new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... voids) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<Movie> movies = new ArrayList<>();

                for (int i = 0; i < 3; i++) {
                    movies.add(
                        new Movie("" + i,
                            "Title " + i)
                    );
                }

                return movies;
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                moviesLiveData.setValue(movies);
            }
        };

        asyncTask.execute();

        return moviesLiveData;
    }

    public LiveData<MovieDetail> getMovieDetail(String id) {
        Log.d("PEACH", "getMovieDetail() called with: id = [" + id + "]");

        MutableLiveData<MovieDetail> movieDetailLiveData = new MutableLiveData<>();

        AsyncTask<Void, Void,MovieDetail> asyncTask = new AsyncTask<Void, Void, MovieDetail>() {
            @Override
            protected MovieDetail doInBackground(Void... voids) {
                try {
                    Thread.sleep(new Random().nextInt(4_000) + 1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return new MovieDetail(id, "Director of Movie " + id);
            }

            @Override
            protected void onPostExecute(MovieDetail movieDetail) {
                movieDetailLiveData.setValue(movieDetail);
            }
        };

        asyncTask.execute();

        return movieDetailLiveData;
    }
}
