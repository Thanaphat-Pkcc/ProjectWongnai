package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MovieDataService;
import com.wongnai.interview.movie.sync.MovieDataSynchronizer;

@Component("databaseMovieSearchService")
public class DatabaseMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MovieDataService movieDataService;
	
	MovieDataSynchronizer movieDataSynchronizer = new MovieDataSynchronizer();
	
	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 3 => Please make all test in DatabaseMovieSearchServiceIntegrationTest run pass.
		// This database search method must use only MovieRepository.findByNameContains(String), you also have to implement
		// MovieDataSynchronizer.forceSync() to load all movie data, using MovieDataService, into MovieRepository.
		// Do not change @Component annotation on this class
		
		
		//System.out.println("Query from user ::== "+queryText.toLowerCase());
		
		return movieRepository.findByNameContaining(queryText.toUpperCase());
		//return movieRepository.findAll(queryText.toUpperCase());
	}
}
