package br.com.outsera.awardintervals.controller;

import br.com.outsera.awardintervals.dto.AwardIntervalResponse;
import br.com.outsera.awardintervals.service.AwardIntervalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/awards")
public class AwardIntervalController {

	private final AwardIntervalService awardIntervalService;

	public AwardIntervalController(AwardIntervalService awardIntervalService) {
		this.awardIntervalService = awardIntervalService;
	}

	@GetMapping("/intervals")
	public ResponseEntity<AwardIntervalResponse> findAwardIntervals() {
		return ResponseEntity.ok(awardIntervalService.findAwardIntervals());
	}
}
