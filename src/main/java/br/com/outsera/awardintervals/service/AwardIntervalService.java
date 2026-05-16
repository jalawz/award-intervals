package br.com.outsera.awardintervals.service;

import br.com.outsera.awardintervals.dto.AwardIntervalResponse;
import br.com.outsera.awardintervals.dto.ProducerIntervalDto;
import br.com.outsera.awardintervals.model.Movie;
import br.com.outsera.awardintervals.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class AwardIntervalService {

	private static final Pattern OXFORD_AND_SEPARATOR = Pattern.compile(",\\s+and\\s+");
	private static final Pattern AND_SEPARATOR = Pattern.compile("\\s+and\\s+");

	private final MovieRepository movieRepository;

	public AwardIntervalService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public AwardIntervalResponse findAwardIntervals() {
		List<ProducerIntervalDto> intervals = findConsecutiveWinIntervals();

		if (intervals.isEmpty()) {
			return new AwardIntervalResponse(List.of(), List.of());
		}

		int minInterval = intervals.stream()
				.mapToInt(ProducerIntervalDto::interval)
				.min()
				.orElseThrow();
		int maxInterval = intervals.stream()
				.mapToInt(ProducerIntervalDto::interval)
				.max()
				.orElseThrow();

		return new AwardIntervalResponse(
				filterByInterval(intervals, minInterval),
				filterByInterval(intervals, maxInterval));
	}

	private List<ProducerIntervalDto> findConsecutiveWinIntervals() {
		Map<String, List<Integer>> winsByProducer = new HashMap<>();

		for (Movie movie : movieRepository.findByWinnerTrue()) {
			for (String producer : splitProducers(movie.getProducers())) {
				winsByProducer.computeIfAbsent(producer, key -> new ArrayList<>()).add(movie.getYear());
			}
		}

		List<ProducerIntervalDto> intervals = new ArrayList<>();
		for (Map.Entry<String, List<Integer>> entry : winsByProducer.entrySet()) {
			List<Integer> years = entry.getValue().stream()
					.sorted()
					.toList();

			for (int i = 1; i < years.size(); i++) {
				int previousWin = years.get(i - 1);
				int followingWin = years.get(i);
				intervals.add(new ProducerIntervalDto(
						entry.getKey(),
						followingWin - previousWin,
						previousWin,
						followingWin));
			}
		}

		return intervals;
	}

	private List<String> splitProducers(String producers) {
		String normalized = OXFORD_AND_SEPARATOR.matcher(producers).replaceAll(", ");
		normalized = AND_SEPARATOR.matcher(normalized).replaceAll(", ");

		return Pattern.compile(",")
				.splitAsStream(normalized)
				.map(String::trim)
				.filter(producer -> !producer.isBlank())
				.toList();
	}

	private List<ProducerIntervalDto> filterByInterval(List<ProducerIntervalDto> intervals, int interval) {
		return intervals.stream()
				.filter(producerInterval -> producerInterval.interval() == interval)
				.sorted(Comparator.comparing(ProducerIntervalDto::producer))
				.toList();
	}
}
