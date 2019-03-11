package com.wongnai.interview.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wongnai.interview.movie.external.MovieDataServiceImpl;
import com.wongnai.interview.movie.external.MoviesResponse;
import com.wongnai.interview.movie.search.DatabaseMovieSearchService;
import com.wongnai.interview.movie.search.InvertedIndexMovieSearchService;

@RestController
@RequestMapping("/movies")
public class MoviesController {
	private MovieDataServiceImpl movieDataServiceImpl;
	private MoviesResponse moviesResponse;
	
	
	
	/**
	 * Inject movie search service and use to handle search request.
	 * <p>
	 * You can specify an implementation using @Qualifier("beanName"), for example:
	 *
	 * <pre>
	 * {@literal @}Qualifier("databaseMovieSearchService")
	 * {@literal @}Qualifier("simpleMovieSearchService")
	 * {@literal @}Qualifier("invertedIndexMovieSearchService")
	 * </pre>
	 */
	@Autowired
	@Qualifier("databaseMovieSearchService")
	//@Qualifier("simpleMovieSearchService")
	private MovieSearchService movieSearchService;
	
	//@Autowired
	//@Qualifier("databaseMovieSearchService")
	//private DatabaseMovieSearchService databaseMovieSearchService;
	
	@Autowired
	@Qualifier("invertedIndexMovieSearchService")
	private InvertedIndexMovieSearchService invertedIndexMovieSearchService;

	@RequestMapping(method = RequestMethod.GET)
	public String helloWorld() {

		//moviesResponse = movieDataServiceImpl.fetchAll();
		//System.out.println("IN REQUESTMAPPING ::== "+moviesResponse);
		
		return "Hello World!";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Movie> searchTitleWithKeyword(@RequestParam("q") String keyword) {
		
		return movieSearchService.search(keyword);
		//return databaseMovieSearchService.search(keyword);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public List<Movie> searchTitleWithKeyword2(@RequestParam("q") String keyword) {
		
		//return movieSearchService.search(keyword);
		return invertedIndexMovieSearchService.search(keyword);
	}
}
