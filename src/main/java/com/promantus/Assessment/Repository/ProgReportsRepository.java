package com.promantus.Assessment.Repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Dto.ProgReportsDto;
import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Entity.ProgReports;

public interface ProgReportsRepository extends MongoRepository<ProgReports, String> {

	ProgReports findById(Long id);

	List<ProgReports> findByStatus(String status);

	List<ProgReports> findByPercentage(String percentage);

	List<ProgReports> findByStatusRegex(String keyword);

	List<ProgReports> findByTeamId(String keyword);

	List<ProgReports> findByReportedOnRegex(String reportedOn);

	List<ProgReports> findByUserIdAndReportedOnRegex(Long userId, String reportedOn);
	
//	List<ProgReports> findAllByProgramLevel(String level);


}
