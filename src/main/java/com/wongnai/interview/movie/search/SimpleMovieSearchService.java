package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;
	
	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class

		List<Movie> ListMovies = new ArrayList<Movie>();
		List<MovieData> DataMovies = new ArrayList<MovieData>();
		//get Movies from Service
		DataMovies = movieDataService.fetchAll();
		
		for (int i = 0; i < DataMovies.size(); i++) {
			Movie movie = new Movie();
			String QueryText = queryText.toUpperCase();
			if (DataMovies.get(i).getTitle() != null && DataMovies.get(i).getTitle().toUpperCase().contains(QueryText)) {
				
				String aftersplit = DataMovies.get(i).getTitle().toUpperCase();
				String[] parts = aftersplit.split(" ");
				for(int j=0;j<parts.length;j++) {
					
					//Check word to equals the QueryText
					if(parts[j].equals(QueryText)) {
						movie.setName(DataMovies.get(i).getTitle());
						movie.setActors(DataMovies.get(i).getCast());
						ListMovies.add(movie);
						
					}
				}
				
			}
		}
		
		return ListMovies;
	}
}
