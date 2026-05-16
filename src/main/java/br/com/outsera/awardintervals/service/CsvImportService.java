package br.com.outsera.awardintervals.service;

import br.com.outsera.awardintervals.model.Movie;
import br.com.outsera.awardintervals.repository.MovieRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvImportService {

	private static final String CSV_FILE = "Movielist.csv";

	private final MovieRepository movieRepository;

	public CsvImportService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Transactional
	public void importMovies() throws IOException {
		if (movieRepository.count() > 0) {
			return;
		}

		List<Movie> movies = readMovies();
		movieRepository.saveAll(movies);
	}

	private List<Movie> readMovies() throws IOException {
		ClassPathResource resource = new ClassPathResource(CSV_FILE);
		List<Movie> movies = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) {
					continue;
				}
				movies.add(parseMovie(line));
			}
		}

		return movies;
	}

	private Movie parseMovie(String line) {
		String[] columns = line.split(";", -1);
		if (columns.length != 5) {
			throw new IllegalArgumentException("Invalid CSV line: " + line);
		}

		return new Movie(
				Integer.valueOf(columns[0].trim()),
				columns[1].trim(),
				columns[2].trim(),
				columns[3].trim(),
				"yes".equalsIgnoreCase(columns[4].trim()));
	}
}
