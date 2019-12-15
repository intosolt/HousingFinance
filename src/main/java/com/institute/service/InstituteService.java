package com.institute.service;

import com.institute.domain.Institute;
import com.institute.domain.InstituteRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InstituteService {
	
	  private final InstituteRepository instituteRepository;

	    public InstituteService(InstituteRepository instituteRepository) {
	        this.instituteRepository = instituteRepository;
	    }

	  //��ü ������� ��ȸ - Service �� Repository�� ���� ���� (BSL�� �ش�)
	    public Map<String, String> getAllInstitutes() 
	    {
	    	List<Institute> institutes = instituteRepository.findAll();
	    	Map<String, String> resultMap = institutes.stream().collect(Collectors.toMap(Institute::getCode, Institute::getName));
	            return resultMap;
	    }
	    
	  

}