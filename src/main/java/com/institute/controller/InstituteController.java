package com.institute.controller;

import com.institute.domain.Institute;
import com.institute.service.InstituteService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class InstituteController {

	 private final Logger log = LoggerFactory.getLogger(InstituteController.class);
	 private final InstituteService instituteService;
	    private static final HttpHeaders httpHeaders = new HttpHeaders();

	  // Service를 호출 (WCF역할)
	    public InstituteController(InstituteService instituteService) {
	        this.instituteService = instituteService;
	    }

	 // 전체 금융기관목록 조회
		@GetMapping("Institute/InstituteList")
		public ResponseEntity getAllInstituteFinance() {
			try {
				return new ResponseEntity<>(instituteService.getAllInstitutes(), httpHeaders, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(Collections.singletonMap("error", "INTERNAL SERVER ERROR"), httpHeaders,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 

}
