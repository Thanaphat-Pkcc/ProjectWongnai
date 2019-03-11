package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> search(String queryText) {

		List<Movie> ListMovies = new ArrayList<Movie>();
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

		Map<String, List<Long>> m = new HashMap<>();
		// List<Long> id = new ArrayList<Long>();
		ListMovies = movieRepository.findAll("a");
		// List<TermInvertedIndexMovie> Listindex = new
		// ArrayList<TermInvertedIndexMovie>();
		for (int i = 0; i < ListMovies.size(); i++) {

			// TermInvertedIndexMovie termInvertedIndexMovie = new TermInvertedIndexMovie();
			// List<Long> id = new ArrayList<Long>();
			// System.out.println("Docuemnt number is:: == "+(i+1));
			Movie movie = new Movie();
			String QueryText = queryText.toUpperCase();

			String aftersplit = ListMovies.get(i).getName().toUpperCase();
			String[] parts = aftersplit.split(" ");
			// split word and mapping word to terms:id
			for (int j = 0; j < parts.length; j++) {

				// TermInvertedIndexMovie termInvertedIndexMovie = new TermInvertedIndexMovie();
				List<Long> id = new ArrayList<Long>();

				// System.out.println(parts[j]+" ");

				if (m.containsKey(parts[j])) {
					// System.out.println("::::::::::::::::::::IN IF CHECK
					// CONTAINSKEY::::::::::::::::::::"+"\n");
					// System.out.println(parts[j]+" +STRING+ "+m.keySet().toArray()[0]+" :##:
					// "+m.get(parts[j]));
					// System.out.println("::::::::::::::::::::IN IF CHECK
					// CONTAINSKEY::::::::::::::::::::");
					id.addAll(m.get(parts[j]));

				}

				// MAPPING TERMS OF WORD TO ID(TITLE)
				id.add(ListMovies.get(i).getId());
				m.put(parts[j], id);

				// m.put(parts[j], value)
				// id.add(ListMovies.get(i).getId());
				// m.put(parts[j], id);

				// if(m.containsKey(parts[j])) {
				// id.add(i, ListMovies.get(i).getId());
				// m.put(parts[j], id);
				// }
				// else {
				// id.add(i, ListMovies.get(i).getId());
				// id.add(ListMovies.get(i),ListMovies.get(i).getId());
				// m.put(parts[j], id);

				// }

				// if (!m.isEmpty())
				// {
				// System.out.println(Arrays.asList(m));
				// }

			}
			// System.out.println();
			// System.out.println("=====================IN OBJECT==========================
			// ");

		}
		// for(int i=0;i<Listindex.size();i++) {
		// System.out.println(Listindex.get(i).getTerm()+":
		// "+Listindex.get(i).getMovieIds());

		// }
		// queryText = "Boarding";
		System.out.println(queryText.toUpperCase());
		System.out.println(m.size());
		System.out.println(m.get(queryText.toUpperCase()));

		String QueryText = queryText.toUpperCase();
		// List<Long> id = new ArrayList<Long>();
		String aftersplit = QueryText;
		String[] parts = aftersplit.split(" ");
		// movieRepository.findNameByIndex(m.get("a"), queryText);

		// m.get("a");
		List<Long> id = new ArrayList<Long>();
		id = Collections.<Long>emptyList();
		System.out.println("Set id is empty :: == " + id);
		System.out.println("Length after split :: == " + parts.length);
		System.out.println("String after split :: == " + parts[0]);
		id = m.get(parts[0]);

		if (parts.length == 2) {
			System.out.println("IN FOR LOPP PARTS");
			try {
				System.out.println("Swap word :: == " + parts[1] + " " + parts[0].toUpperCase());
				ListMovies = movieRepository.findNameByIndex(id, queryText.toUpperCase());
				// ListMovies = movieRepository.findNameByIndex(id,parts[1]+"
				// "+parts[0].toUpperCase());
				if (ListMovies != null) {
					ListMovies = movieRepository.findNameByIndex(id, parts[1] + " " + parts[0].toUpperCase());
				}

				System.out.println("IN TRY ::==" + ListMovies);
				return ListMovies;
			} catch (Exception e) {
				System.out.println("IN CATCH EXCEPTION");
				try {
					System.out.println("Swap word :: == " + parts[1] + " " + parts[0].toUpperCase());
					ListMovies = movieRepository.findNameByIndex(id, parts[1] + " " + parts[0].toUpperCase());
					return ListMovies;
				} catch (Exception e2) {
					return null;
				}

			}
		}

		System.out.println("ID IN QUERY :: == " + id);
		try {
			ListMovies = movieRepository.findNameByIndex(id, queryText.toUpperCase());
			System.out.println("IN TRY");
		} catch (Exception e) {
			System.out.println("IN CATCH EXCEPTION");
			return null;
		}
		// ListMovies = movieRepository.findNameByIndex(id,queryText.toUpperCase());

		// System.out.println("=======================IN MAPPING========================
		// ");
		// System.out.println("Size MAP IS :: == "+m.size());
		// System.out.println("++++++++++++++++++++++++++++++++++");
		// if (!m.isEmpty())
		// {
		// System.out.println(Arrays.asList(m));
		// }

		// ListMovies = movieRepository.findNameByIndex(m.get(queryText.toUpperCase()));
		return ListMovies;
	}
}
