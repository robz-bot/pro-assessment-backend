package com.promantus.Assessment.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.ProgReports;

public interface ProgReportsRepository extends MongoRepository<ProgReports, String> {

}
