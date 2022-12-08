package com.promantus.Assessment.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Entity.User;

public interface ReportsRepository extends MongoRepository<Reports, String> {

	List<Reports> findByUserId(Long userId);

	Reports findById(Long id);

	User findByReportedOn(LocalDateTime reportedOn);

	@Query("{'reportedOn': {$regex: ?0,$options: \"i\"} }})")
	List<Reports> findByReportedOnRegex(String reportedOn);

	@Query("{'status': {$regex: ?0,$options: \"i\"} }})")
	List<Reports> findByStatus(String status);

	@Query("{'percentage': {$regex: ?0,$options: \"i\"} }})")
	List<Reports> findByPercentage(String percentage);

	@Query("{'status': {$regex: ?0,$options: \"i\"} }})")
	List<Reports> findByStatusRegex(String keyword);

	List<Reports> findByTeamId(String keyword);

}
