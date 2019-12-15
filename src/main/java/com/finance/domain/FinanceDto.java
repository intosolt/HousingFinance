package com.finance.domain;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class FinanceDto implements Comparable<FinanceDto>{
	
	 private 
	static int FinanceDto;

	int total;
	String year;
	Map<String, Integer> detailamount;
	
	
	
	  public void Setyear(String year) { this.year = year; }
	  
	  public void Setotal(int total) { this.total = total; }
	 
	
	  public void Setdetailamount(Map<String, Integer> detailamount) { this.detailamount = detailamount; }
	  
	  
	  
	  public String getYear() {
		  return year;
	  }
	  
	  public Map<String, Integer> getDetailAmount(){
		  return detailamount;
	  }
	  
	  @JsonIgnore
	    public void addTotalAmount(Integer yearAmount){
	        this.total += yearAmount;
	    }

	    @JsonIgnore
	    public void putDetailAmount(Map<String,Integer> detailAmount){
	        Map<String,Integer> mergeMap = new HashMap<>();
	        mergeMap.putAll(this.detailamount);
	        mergeMap.putAll(detailAmount);
	        this.detailamount = mergeMap;
	    }

	
	/*
	 * @Builder public static FinanceDto builder(String year, Integer total,
	 * Map<String, Integer> detailamount) { return FinanceDto.builder() .year(year)
	 * .total(total) .detailamount(detailamount) .build(); }
	 */
	 
	    
	    @Override
	    public int compareTo(FinanceDto financeStatusByYear) {
	        return this.year.compareTo(financeStatusByYear.getYear());
	    }

	    
	   

	public String tostring() {
		return "FinanceDto [total = " + total + ", year =" + year + ", code = " + detailamount + "]";
	}

}



