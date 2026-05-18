package br.com.outsera.awardintervals;

import br.com.outsera.awardintervals.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AwardIntervalsApplicationTests {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldLoadMoviesFromCsvOnStartup() {
		assertThat(movieRepository.count()).isEqualTo(210);
		assertThat(movieRepository.findByWinnerTrue()).hasSize(46);
	}

	@Test
	void shouldReturnMinAndMaxProducerAwardIntervals() throws Exception {
		mockMvc.perform(get("/api/awards/intervals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.min").isArray())
				.andExpect(jsonPath("$.min.length()").value(2))
				.andExpect(jsonPath("$.min[?(@.producer=='Joel Silver')].interval").value(1))
				.andExpect(jsonPath("$.min[?(@.producer=='Producer Tied Min A')].interval").value(1))
				.andExpect(jsonPath("$.max").isArray())
				.andExpect(jsonPath("$.max.length()").value(2))
				.andExpect(jsonPath("$.max[?(@.producer=='Matthew Vaughn')].interval").value(13))
				.andExpect(jsonPath("$.max[?(@.producer=='Producer Tied Max A')].interval").value(13));
	}

	@Test
	void shouldReturnCorrectJsonStructure() throws Exception {
		mockMvc.perform(get("/api/awards/intervals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.min[0].producer").exists())
				.andExpect(jsonPath("$.min[0].interval").exists())
				.andExpect(jsonPath("$.min[0].previousWin").exists())
				.andExpect(jsonPath("$.min[0].followingWin").exists())
				.andExpect(jsonPath("$.max[0].producer").exists())
				.andExpect(jsonPath("$.max[0].interval").exists())
				.andExpect(jsonPath("$.max[0].previousWin").exists())
				.andExpect(jsonPath("$.max[0].followingWin").exists());
	}

	@Test
	void shouldReturnCorrectPreviousAndFollowingWins() throws Exception {
		mockMvc.perform(get("/api/awards/intervals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.min[?(@.producer=='Joel Silver')].previousWin").value(1990))
				.andExpect(jsonPath("$.min[?(@.producer=='Joel Silver')].followingWin").value(1991))
				.andExpect(jsonPath("$.max[?(@.producer=='Matthew Vaughn')].previousWin").value(2002))
				.andExpect(jsonPath("$.max[?(@.producer=='Matthew Vaughn')].followingWin").value(2015));
	}

	@Test
	void shouldReturnMinSortedByProducerName() throws Exception {
		mockMvc.perform(get("/api/awards/intervals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.min[0].producer").value("Joel Silver"))
				.andExpect(jsonPath("$.min[1].producer").value("Producer Tied Min A"));
	}

	@Test
	void shouldReturnMaxSortedByProducerName() throws Exception {
		mockMvc.perform(get("/api/awards/intervals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.max[0].producer").value("Matthew Vaughn"))
				.andExpect(jsonPath("$.max[1].producer").value("Producer Tied Max A"));
	}
}
