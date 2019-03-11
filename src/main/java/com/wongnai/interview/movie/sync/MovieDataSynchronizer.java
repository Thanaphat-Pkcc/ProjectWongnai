package com.wongnai.interview.movie.sync;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MovieDataService;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;
	

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		List<Movie> ListMovies = new ArrayList<Movie>();
		List<MovieData> DataMovies = new ArrayList<MovieData>();
		DataMovies = movieDataService.fetchAll();
		
		/*
		for(int i=0;i<DataMovies.size();i++) {
			Movie movie = new Movie();
			movie.setName(DataMovies.get(i).getTitle());
			movie.setActors(DataMovies.get(i).getCast());
			
			
			
			//ListMovies.add(movie);
			movieRepository.save(movie);
			
		}*/
		
		for( MovieData index : DataMovies) {
            Movie movie = new Movie();
            movie.setName(index.getTitle());
            movie.setActors(index.getCast());
            movieRepository.save(movie);
        }
		
		
		
		
		
		
		
	}
}
