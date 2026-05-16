package br.com.outsera.awardintervals.repository;

import br.com.outsera.awardintervals.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	List<Movie> findByWinnerTrue();
}
