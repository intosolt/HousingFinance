package com.finance.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceContext;

import javax.persistence.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.domain.InstituteFinanceRepository;
import com.institute.domain.InstituteRepository;
import com.institute.service.InstituteService;

import com.finance.service.FinanceService;

@RestController
public class FinanceController {

	@PersistenceContext
	private EntityManager entityManager;

	private final FinanceService financeService;
	private final InstituteService instituteService;
	private static final HttpHeaders httpHeaders = new HttpHeaders();
	private final InstituteRepository iRepository;
	private final InstituteFinanceRepository iFinanceRepository;

	public FinanceController(FinanceService financeService, InstituteService instituteService,
			InstituteRepository iRepository, InstituteFinanceRepository iFinanceRepository) {
		this.financeService = financeService;
		this.instituteService = instituteService;
		this.iRepository = iRepository;
		this.iFinanceRepository = iFinanceRepository;

	}

	@GetMapping("/Finance/AllFinanceList")
	public ResponseEntity getAllInstituteFinance(Map<String, Integer> bankFinanceMap, Map<String, String> bankNameMap) {
		try {
			return new ResponseEntity<>(financeService.listingFinanceStatus(bankFinanceMap, bankNameMap), httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("error", "INTERNAL SERVER ERROR"), httpHeaders,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/Finance/highest/year")
	public ResponseEntity getHighestYearBank() {
		try {
			return new ResponseEntity<>(financeService.getHighestYearBank(), httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("error", "INTERNAL SERVER ERROR"), httpHeaders,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @GetMapping("/") public String Home() throws Exception {
	 * 
	 * }
	 */

}
