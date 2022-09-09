package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.ReportsDto;

public interface ReportsService {

	ReportsDto addReports(ReportsDto reportsDto, String lang) throws Exception;

	List<ReportsDto> getAllReports();

	ReportsDto updateReports(ReportsDto reportsDto, String lang);

	ReportsDto deleteReportsById(String reportsId);

	ReportsDto getReportsById(String id) throws Exception;
	

}
