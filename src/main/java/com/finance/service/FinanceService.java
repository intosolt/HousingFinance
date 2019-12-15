package com.finance.service;

import com.finance.domain.FinanceDto;
import com.finance.domain.InstituteFinance;

import com.finance.domain.InstituteFinanceRepository;
import com.institute.domain.Institute;
import com.institute.domain.InstituteRepository;
import com.institute.service.InstituteService;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import java.util.regex.Pattern;



@Service
public class FinanceService {
	
	 private final InstituteFinanceRepository iFinanceRepository;
	 private final InstituteRepository iRepository;
	 
	 private Pattern pattern = Pattern.compile("\"([\\d,]+?)\"");
	 
	 private List<InstituteFinance> fList;
	 private List<Institute> iList;
	 private InstituteService instituteService;
	 private FinanceService  financeService;

	    public FinanceService(InstituteFinanceRepository iFinanceRepository, InstituteRepository iRepository) {
	        this.iFinanceRepository = iFinanceRepository;
	        this.iRepository = iRepository;
	    }

	    @RequestMapping(value = "/Finance/SaveFileData")
		public String SaveFileData(String filePath) throws Exception {
			try {

				String line;
				String cvsSplitBy = ",";
				

				List<List<String>> ret = new ArrayList<List<String>>();

				//BufferedReader br = new BufferedReader(new FileReader("C:\\housing-finance-data.csv"));
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				
				String strHead = br.readLine();
				List<String> tempList = new ArrayList<>();
				String[] field = strHead.split(cvsSplitBy);

				// 첫번째 라인은 Institute 테이블 Insert
				for (int i = 2; i < field.length; i++) {
					String s = field[i].substring(0, field[i].indexOf('('));

					//String code = "INST" + Integer.toString(i);
					String code = Integer.toString(i-1);
					Institute o = iRepository.save(new Institute(code, s));

				}

				while ((line = br.readLine()) != null) {

					List<String> tempData = new ArrayList<>();
					String[] fieldData = line.split(cvsSplitBy);

					int year = Integer.parseInt(fieldData[0]);
					int month = Integer.parseInt(fieldData[1]);

					if (year >= 2016) {
						StringBuilder parsedData = new StringBuilder();
						Matcher matcher = pattern.matcher(line.toString());
						int beforeEndIndex = 0;
						while (matcher.find()) {
							String data = matcher.group(1).replaceAll(",", "");
							parsedData.append(line.toString().substring(beforeEndIndex, matcher.start()));
							parsedData.append(data);
							beforeEndIndex = matcher.end();
						}
						parsedData.append(line.toString().substring(beforeEndIndex));

						fieldData = parsedData.toString().split(cvsSplitBy);
					}

					for (int j = 2; j < fieldData.length; j++) {
						String s1 = fieldData[j].toString();
						String code = Integer.toString(j-1);
						
						int amount = Integer.parseInt(fieldData[j].replace("\"", ""));

						InstituteFinance in = iFinanceRepository.save(new InstituteFinance(year, month, amount, code));
					
					}

					fList = iFinanceRepository.findAll();
				}

				br.close();
				
				
			/*
			 * //기관별 년도별 합계 조회 Map<String, Integer> bankFinanceMap =
			 * mappingAmountOfYearByBankIdAndYear(fList); // key == bankId/year
			 * 
			 * // 기관별 지원현황조회 Map<String, String> bankNameMap =
			 * iList.stream().collect(Collectors.toMap(Institute::getCode,
			 * Institute::getName));
			 * 
			 * 
			 * List<FinanceDto> financeStatusByYears =
			 * listingFinanceStatus(bankFinanceMap,bankNameMap);
			 * 
			 * 
			 * if(bankNameMap != null) {
			 * System.out.println("===== bankNameMap 조회=============================");
			 * 
			 * bankNameMap.forEach((key, value) -> System.out.println("Key: "+key+
			 * " :: Value: "+value));
			 * 
			 * }
			 * 
			 * 
			 * 
			 * Map<String, Object> resultMap = new HashMap<>(); resultMap.put("name",
			 * "주택금융 공급현황"); resultMap.put("status", financeStatusByYears);
			 * 
			 * Map<String, String> bankList = instituteService.getAllInstitutes();
			 * 
			 * if(bankList != null) { bankList.forEach((key, value) ->
			 * System.out.println("Key: "+key+ " :: Value: "+value)); }
			 */
				 

			} catch (Exception e) {
				e.printStackTrace();

			}
			return "success";
		}
	    
	    public Map<String, Integer> mappingAmountOfYearByBankIdAndYear(List<InstituteFinance> Finances) {
	        Map<String, Integer> bankFinanceMap = new HashMap<>();
	        StringBuilder stringBuilder = new StringBuilder();
	        for (InstituteFinance o : Finances) {
	            String key = stringBuilder.append(o.getCode())
	                    .append("/")
	                    .append(o.getYear())
	                    .toString();
	            stringBuilder.setLength(0);
	            bankFinanceMap.putIfAbsent(key, 0);
	            bankFinanceMap.compute(key, (s, integer) -> integer + o.getAmount());
	        }
	        return bankFinanceMap;
	    }
	    
	    public List<FinanceDto> listingFinanceStatus(Map<String, Integer> bankFinanceMap, Map<String, String> bankNameMap) {

	        List<FinanceDto> financeStatusByYears = new ArrayList<>();
	        StringBuilder stringBuilder = new StringBuilder();

	        bankFinanceMap.forEach((key, yearAmount) -> {
	            String[] keys = key.split("/");
	            String bankId = String.valueOf(keys[0]);
	            String year = stringBuilder.append(keys[1])
	                    .append(" 년")
	                    .toString();
	            stringBuilder.setLength(0);
	            
	            FinanceDto financeStatusByYear = FinanceDto.builder()
	                    .year(year)
	                    .total(yearAmount)
	                    .detailamount(Collections.singletonMap(bankNameMap.get(bankId), yearAmount))
	                    .build();

	            try {
	            	FinanceDto originalElement = financeStatusByYears.stream()
	                        .filter(o -> o.getYear().equals(financeStatusByYear.getYear()))
	                        .findFirst()
	                        .orElseThrow(NoSuchElementException::new);
	                originalElement.addTotalAmount(yearAmount);
	                originalElement.putDetailAmount(financeStatusByYear.getDetailAmount());
	            } catch (NoSuchElementException e) {
	                financeStatusByYears.add(financeStatusByYear);
	            }
	        });
	        Collections.sort(financeStatusByYears);

	        return financeStatusByYears;
	    }
	    
	    public Map<String, Object> getHighestYearBank() {
	        List<InstituteFinance> finances = iFinanceRepository.findAll();
	        Map<String, Integer> financeMap = mappingAmountOfYearByBankIdAndYear(finances); // key == bankId/year

	        String key = Collections.max(financeMap.entrySet(),
	                Comparator.comparingInt(Map.Entry::getValue)).getKey();

	        String[] keys = key.split("/");
	        String code = keys[0].toString();
	        Institute institute = iRepository.findById(code)
	                .orElseThrow(EntityNotFoundException::new);

	        String year = keys[1];

	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("year", year);
	        resultMap.put("institute", institute.getName());
	        return resultMap;
	    }


}
