package br.com.outsera.awardintervals.dto;

import java.util.List;

public record AwardIntervalResponse(
		List<ProducerIntervalDto> min,
		List<ProducerIntervalDto> max
) {
}
