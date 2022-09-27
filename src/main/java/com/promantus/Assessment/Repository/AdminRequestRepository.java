package com.promantus.Assessment.Repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.AdminRequest;
import com.promantus.Assessment.Entity.Category;

public interface AdminRequestRepository extends MongoRepository<AdminRequest, String> {

	AdminRequest findByReqRaisedOn(LocalDateTime reqRaisedOn);

	AdminRequest findByEmail(String email);

	AdminRequest findById(Long id);


}
