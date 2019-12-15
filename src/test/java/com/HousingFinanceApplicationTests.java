package com;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.finance.domain.FinanceDto;
import com.finance.domain.InstituteFinance;
import com.finance.service.FinanceService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


@SpringBootTest
class HousingFinanceApplicationTests {

	@InjectMocks
	FinanceService oFinanceService;

	/*
	 * @Before public void Setup() throws Exception{ String str =
	 * oFinanceService.SaveFileData("C:\\housing-finance-data.csv"); }
	 */
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void  testMappingAmountOfYearByBankIdAndYear() {
        List<InstituteFinance> oList = new ArrayList<>();
        oList.add(InstituteFinance.builder()
                .code("1").year(2015).month(1).amount(1000).build());
        oList.add(InstituteFinance.builder()
                .code("1").year(2015).month(2).amount(1000).build());
        oList.add(InstituteFinance.builder()
                .code("1").year(2016).month(1).amount(2000).build());
        oList.add(InstituteFinance.builder()
                .code("1").year(2016).month(2).amount(2000).build());
        

        Map<String, Integer> resultMap = oFinanceService.mappingAmountOfYearByBankIdAndYear(oList);

        assertThat(resultMap, is(notNullValue()));
        assertThat(resultMap.get("1/2015"), is(2000));
        assertThat(resultMap.get("1/2016"), is(4000));
    }
	
	@Test
    public void testListingFinanceStatus() {
        Map<String, Integer> FinanceMap = new HashMap<>();
        FinanceMap.put("1/2015", 2000);
        FinanceMap.put("1/2016", 4000);

        Map<String, String> InstitutekNameMap = new HashMap<>();
        InstitutekNameMap.put("1", "카카오페이");

        FinanceDto firstFinanceStatusByYear = FinanceDto.builder()
                .year("2015 년")
                .total(2000)
                .detailamount(Collections.singletonMap("카카오페이", 2000))
                .build();
        FinanceDto secondFinanceStatusByYear = FinanceDto.builder()
                .year("2016 년")
                .total(4000)
                .detailamount(Collections.singletonMap("카카오페이", 4000))
                .build();

        List<FinanceDto> resultList = oFinanceService.listingFinanceStatus(FinanceMap, InstitutekNameMap);

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList.get(0), is(firstFinanceStatusByYear));
        assertThat(resultList.get(1), is(secondFinanceStatusByYear));
    }


}
