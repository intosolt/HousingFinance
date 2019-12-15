package com.finance.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@AllArgsConstructor
@Table(name = "institute_finance")
public class InstituteFinance {
    protected InstituteFinance(){}

	
	  @Id
	  @Column(name = "id")
	  @GeneratedValue
	  private Long id;
	 

    @Column(name = "code")
    private String code;
    
    @Column
    private Integer year;

    @Column
    private Integer month;

    @Column
    private Integer amount;
    
    @Builder
    public InstituteFinance(Integer year, Integer month, Integer amount, String code) {
    	//this.id = (long) Math.random();
    	this.year = year;
    	this.month = month;
    	this.amount = amount;
    	this.code = code;
    }
    
    public Integer getYear() {
    	return year;
    }
    
    public Integer getMonth() {
    	return month;
    }
    
    public Integer getAmount() {
    	return amount;
    }
    
    public String getCode() {
    	return code;
    }
    
  
    @Override public String toString() {
    	return "InstituteFinance [year = " + year + ", month = "+ month + ", amount = " + amount +
    			  ", code = "+ code+"]"; 
    }

}
