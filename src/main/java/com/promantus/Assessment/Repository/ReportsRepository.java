package com.promantus.Assessment.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.Reports;

public interface ReportsRepository extends MongoRepository<Reports, String> {

	Reports findByUserId(String userId);

	Reports findById(Long id);

}
