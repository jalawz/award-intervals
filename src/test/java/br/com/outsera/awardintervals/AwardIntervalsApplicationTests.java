package br.com.outsera.awardintervals;

import br.com.outsera.awardintervals.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AwardIntervalsApplicationTests {

	@Autowired
	private MovieRepository movieRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldLoadMoviesFromCsvOnStartup() {
		assertThat(movieRepository.count()).isEqualTo(206);
		assertThat(movieRepository.findByWinnerTrue()).hasSize(42);
	}
}
