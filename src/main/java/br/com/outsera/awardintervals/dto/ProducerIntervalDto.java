package br.com.outsera.awardintervals.dto;

public record ProducerIntervalDto(
		String producer,
		Integer interval,
		Integer previousWin,
		Integer followingWin
) {
}
