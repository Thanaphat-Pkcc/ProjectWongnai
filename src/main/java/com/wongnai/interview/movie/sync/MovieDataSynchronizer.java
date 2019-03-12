package com.wongnai.interview.movie.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private Map<String, List<Long>> InvertedIndex = new HashMap<>();
	
	@Transactional
	public void forceSync() {
		// TODO: implement this to sync movie into repository
		List<MovieData> DataMovies = new ArrayList<MovieData>();
		DataMovies = movieDataService.fetchAll();

		// : Add data to database
		for (MovieData index : DataMovies) {
			Movie movie = new Movie();
			movie.setName(index.getTitle());
			movie.setActors(index.getCast());
			movieRepository.save(movie);
		}

		// Implement inverted index Mapping <Term , Movie Ids>

		List<Movie> ListMovies = new ArrayList<Movie>();
		ListMovies = movieRepository.findAll();
		for (int i = 0; i < ListMovies.size(); i++) {
			// split word and mapping word to terms:id
			String aftersplit = ListMovies.get(i).getName().toUpperCase();
			String[] parts = aftersplit.split(" ");

			for (int j = 0; j < parts.length; j++) {

				List<Long> id = new ArrayList<Long>();

				if (InvertedIndex.containsKey(parts[j])) {

					id.addAll(InvertedIndex.get(parts[j]));

					if (!InvertedIndex.get(parts[j]).contains(ListMovies.get(i).getId())) {

						id.add(ListMovies.get(i).getId());

					}

					InvertedIndex.put(parts[j], id);

				} else {
					id.add(ListMovies.get(i).getId());
					InvertedIndex.put(parts[j], id);
				}

			}

		}

	}

	public Map<String, List<Long>> getInvertedIndex() {
		return InvertedIndex;
	}

	public void setInvertedIndex(Map<String, List<Long>> InvertedIndex) {
		this.InvertedIndex = InvertedIndex;
	}
}
