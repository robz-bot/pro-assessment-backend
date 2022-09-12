package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.GeneralQuestion;

public interface GeneralQuestionRepository extends MongoRepository<GeneralQuestion, String> {

	GeneralQuestion findById(Long id);

	List<GeneralQuestion> findAll();

	List<GeneralQuestion> findAllQuestionDistinctBy();

	GeneralQuestion findByQuestion(String question);

	@Query("{'question': {$regex: ?0,$options: \"i\"} }})")
	List<GeneralQuestion> findByQuestionRegex(String question);

	GeneralQuestion findByAnswer(String answer);

}
