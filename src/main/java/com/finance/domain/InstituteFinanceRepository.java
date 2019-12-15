package com.finance.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.finance.domain.InstituteFinance;

@Repository
public interface InstituteFinanceRepository extends JpaRepository<InstituteFinance, Long>{

}
