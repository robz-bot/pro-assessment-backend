package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.GeneralQuestion;

public interface GeneralQuestionRepository extends MongoRepository<GeneralQuestion, String>{

	GeneralQuestion findById(Long id);

	List<GeneralQuestion> findAll();

}
