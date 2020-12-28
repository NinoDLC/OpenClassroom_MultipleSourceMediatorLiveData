package fr.delcey.dynamic.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.delcey.dynamic.repository.Movie;
import fr.delcey.dynamic.repository.MovieDetail;
import fr.delcey.dynamic.repository.MovieRepository;

public class MainViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    private final MediatorLiveData<List<MainUiState>> mediatorLiveData = new MediatorLiveData<>();

    private final MediatorLiveData<Map<String, MovieDetail>> movieDetailLiveData = new MediatorLiveData<>();

    private final List<String> alreadyRequiredIds = new ArrayList<>();

    public MainViewModel() {
        // TODO A injecter plutôt
        this.movieRepository = new MovieRepository();

        movieDetailLiveData.setValue(new HashMap<>());

        LiveData<List<Movie>> moviesLiveData = movieRepository.getMovies();

        mediatorLiveData.addSource(moviesLiveData, movies -> {
            //noinspection ConstantConditions
            combine(movies, movieDetailLiveData.getValue());
        });

        mediatorLiveData.addSource(movieDetailLiveData, movieDetailsMap ->
            combine(moviesLiveData.getValue(), movieDetailsMap)
        );
    }

    private void combine(@Nullable List<Movie> movies,@NonNull Map<String, MovieDetail> movieDetailsMap) {
        if (movies == null) {
            return;
        }

        List<MainUiState> uiStateList = new ArrayList<>();

        for (Movie movie : movies) {
            MovieDetail existingMovieDetail = movieDetailsMap.get(movie.getId());

            if (existingMovieDetail == null) {
                if (!alreadyRequiredIds.contains(movie.getId())) {
                    alreadyRequiredIds.add(movie.getId());
                    movieDetailLiveData.addSource(movieRepository.getMovieDetail(movie.getId()), movieDetail -> {
                        Map<String, MovieDetail> existingMap = movieDetailLiveData.getValue();

                        //noinspection ConstantConditions
                        existingMap.put(movie.getId(), movieDetail);

                        movieDetailLiveData.setValue(existingMap);
                    });
                }
            } else {
                uiStateList.add(
                    new MainUiState(
                        movie.getTitle(),
                        existingMovieDetail.getDirector()
                    )
                );
            }
        }

        mediatorLiveData.setValue(uiStateList);
    }

    public LiveData<List<MainUiState>> getUiStateLiveData() {
        return mediatorLiveData;
    }
}