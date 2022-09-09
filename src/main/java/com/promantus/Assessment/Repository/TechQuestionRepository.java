package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.TechQuestion;

public interface TechQuestionRepository extends MongoRepository<TechQuestion, String>{

	List<TechQuestion> findByTeamId(Long teamId);

	TechQuestion findAllQnByTeamId(Long teamId);

	TechQuestion findById(Long id);

}
