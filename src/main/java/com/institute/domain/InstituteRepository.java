package com.institute.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.institute.domain.Institute;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String>{

	Institute findByName(String instituteName);

}
