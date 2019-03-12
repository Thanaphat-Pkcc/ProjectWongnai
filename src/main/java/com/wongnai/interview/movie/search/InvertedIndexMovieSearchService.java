package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.sync.MovieDataSynchronizer;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MovieDataSynchronizer movieDataSynchronizer;
	
	@Override
	public List<Movie> search(String queryText) {

		// TODO: Step 4 => Please implement in-memory inverted index to search movie by
		// keyword.
		// You must find a way to build inverted index before you do an actual search.
		// Inverted index would looks like this:
		// -------------------------------
		// | Term | Movie Ids |
		// -------------------------------
		// | Star | 5, 8, 1 |
		// | War | 5, 2 |
		// | Trek | 1, 8 |
		// -------------------------------
		// When you search with keyword "Star", you will know immediately, by looking at
		// Term column, and see that
		// there are 3 movie ids contains this word -- 1,5,8. Then, you can use these
		// ids to find full movie object from repository.
		// Another case is when you find with keyword "Star War", there are 2 terms,
		// Star and War, then you lookup
		// from inverted index for Star and for War so that you get movie ids 1,5,8 for
		// Star and 2,5 for War. The result that
		// you have to return can be union or intersection of those 2 sets of ids.
		// By the way, in this assignment, you must use intersection so that it left for
		// just movie id 5.
		// create inverted index of all files
		
		List<Movie> ListMovies = new ArrayList<Movie>();
		Map<String, List<Long>> InvertedIndex = movieDataSynchronizer.getInvertedIndex();

		//Split query to search with term
		String QueryText = queryText.toUpperCase();
		String aftersplit = QueryText;
		String[] parts = aftersplit.split(" ");
		List<Long> MovieIds = new ArrayList<Long>();
		if(parts.length>1) {
			for(int i =0;i<parts.length-1;i++) {
				List<Long> id1 = new ArrayList<Long>();
				List<Long> id2 = new ArrayList<Long>();
				
				//Check intersect MovieIds
				if(i==0) {
					id1.addAll(InvertedIndex.get(parts[i]));
					id2.addAll(InvertedIndex.get(parts[i+1]));
					id1.retainAll(id2);
					MovieIds=id1;
				}
				id2.addAll(InvertedIndex.get(parts[i+1]));
				MovieIds.retainAll(id2);
				
				
			}
		}
		else {
			MovieIds = InvertedIndex.get(parts[0]);
		}
		//Query in database By MovieIds
		try {
			ListMovies = movieRepository.findNameByIndex(MovieIds);
		} catch (Exception e) {
			return null;
		}
		 
		 
		return ListMovies;
	}
}
