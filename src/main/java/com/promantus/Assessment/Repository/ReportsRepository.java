package com.promantus.Assessment.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Entity.User;

public interface ReportsRepository extends MongoRepository<Reports, String> {

	List<Reports> findByUserId(Long userId);

	Reports findById(Long id);

	User findByReportedOn(LocalDateTime reportedOn);


}
