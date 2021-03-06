package com.example.demo.service;

import java.util.*;

import com.example.demo.model.Movie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    RestTemplate restTemplate = new RestTemplate();

    public Optional<Iterable<Movie>> getAllMovies() {
        ResponseEntity<List<Movie>> response = restTemplate.exchange("https://filmstreamer.herokuapp.com/api/movies/movies",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Movie>>() {
                });
        List<Movie> movies = response.getBody();
        return Optional.of(movies);
    }

    public Optional<Movie> findMovieById(UUID movieUUID) {
        ResponseEntity<Movie> response = restTemplate.exchange("https://filmstreamer.herokuapp.com/api/movies/getMovie/" + movieUUID,
                HttpMethod.GET, null, new ParameterizedTypeReference<Movie>() {
                });
        Movie movie = response.getBody();
        return Optional.of(movie);
    }

    public Optional<Movie> deleteMovieById(UUID movieUUID) {
        ResponseEntity<Movie> response = restTemplate.exchange("https://filmstreamer.herokuapp.com/api/movies/deleteMovie/" + movieUUID,
                HttpMethod.DELETE, null, new ParameterizedTypeReference<Movie>() {
                });
        Movie movie = response.getBody();
        return Optional.of(movie);
    }

    public Optional<Movie> createMovie(Movie movie) {
        String url = "https://filmstreamer.herokuapp.com/api/movies/createMovie";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.ALL));

        HttpEntity<Movie> request = new HttpEntity<>(movie, headers);

        ResponseEntity<Movie> response = restTemplate.postForEntity(url, request, Movie.class);

        Movie newMovie = response.getBody();
        return Optional.of(newMovie);
    }

    public Optional<Movie> updateMovie(Movie movie) {
        Optional<Movie> movieFound = findMovieById(movie.getMovieUUID());
        if (movieFound.isPresent()) {
            String url = "https://filmstreamer.herokuapp.com/api/movies/updateMovie";
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.ALL));

            HttpEntity<Movie> request = new HttpEntity<>(movie, headers);

            ResponseEntity<Movie> response = restTemplate.postForEntity(url, request, Movie.class);

            Movie newMovie = response.getBody();
            return Optional.of(newMovie);
        }
        return null;
    }

}