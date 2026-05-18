package br.com.outsera.awardintervals.controller;

import br.com.outsera.awardintervals.dto.AwardIntervalResponse;
import br.com.outsera.awardintervals.service.AwardIntervalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/awards")
@Tag(name = "Award Intervals", description = "API to analyze award intervals between producers")
public class AwardIntervalController {

	private final AwardIntervalService awardIntervalService;

	public AwardIntervalController(AwardIntervalService awardIntervalService) {
		this.awardIntervalService = awardIntervalService;
	}

	@GetMapping("/intervals")
	@Operation(
			summary = "Get award intervals",
			description = "Returns the producer with the longest and shortest interval between two consecutive Golden Raspberry Awards"
	)
	public ResponseEntity<AwardIntervalResponse> findAwardIntervals() {
		return ResponseEntity.ok(awardIntervalService.findAwardIntervals());
	}
}
