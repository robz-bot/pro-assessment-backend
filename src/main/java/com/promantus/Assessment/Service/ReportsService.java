package com.promantus.Assessment.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.promantus.Assessment.Dto.ReportsDto;

public interface ReportsService {

	ReportsDto addReports(ReportsDto reportsDto, String lang) throws Exception;

	List<ReportsDto> getAllReports() throws Exception;

	ReportsDto updateReports(ReportsDto reportsDto, String lang) throws Exception;

	ReportsDto deleteReportsById(String reportsId) throws Exception;

	ReportsDto getReportsById(String id) throws Exception;

	List<ReportsDto> searchByExamStartDate(String reportedOn) throws Exception;

	List<ReportsDto> searchByPercentage(String percentage) throws Exception;

	List<ReportsDto> searchByStatus(String status) throws Exception;

	List<ReportsDto> search(String type, String keyword) throws Exception;

	byte[] downloadReports(List<ReportsDto> reportsDtoList, String lang) throws IOException;

	Map<String, Object> getAllReportsPage(Pageable paging) throws IOException;

	Map<String, Object> searchReportPage(Pageable paging, String type, String keyword)throws Exception;


}
