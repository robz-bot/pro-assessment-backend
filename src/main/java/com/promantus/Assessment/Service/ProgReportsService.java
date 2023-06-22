package com.promantus.Assessment.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.promantus.Assessment.Dto.ProgReportsDto;
import com.promantus.Assessment.Dto.ReportsDto;

public interface ProgReportsService {

	ProgReportsDto addProgReports(List<ProgReportsDto> progReportsDto, String lang) throws Exception;

	List<ProgReportsDto> getAllProgReports() throws Exception;

	Map<String, Object> getAllProgReportsPage(Pageable paging) throws Exception;

	ProgReportsDto getProgReportsById(String id) throws Exception;

	ProgReportsDto updateProgReports(ProgReportsDto progReportsDto, String lang) throws Exception;

	List<ProgReportsDto> searchByStatus(String status) throws Exception;

	List<ProgReportsDto> searchByPercentage(String percentage) throws Exception;

	byte[] downloadProgReports(List<ProgReportsDto> progReportsDtoList, String lang)throws IOException;

	Map<String, Object> searchProgReportPage(Pageable paging, String type, String keyword);

	List<ProgReportsDto> searchProg(String type, String keyword) throws Exception;

	List<ProgReportsDto> getProgByReportedOn(ProgReportsDto progReportsDto, String type) throws Exception;

}
