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

	@Query("{'answer': {$regex: ?0,$options: \"i\"} }})")
	List<GeneralQuestion> findByAnswerRegex(String answer);

	@Query("{'option1': {$regex: ?0,$options: \"i\"} }})")
	List<GeneralQuestion> findByOption1Regex(String keyword);

	@Query("{'option2': {$regex: ?0,$options: \"i\"} }})")
	List<GeneralQuestion> findByOption2Regex(String keyword);

	@Query("{'option3': {$regex: ?0,$options: \"i\"} }})")
	List<GeneralQuestion> findByOption3Regex(String keyword);

	@Query("{'option4': {$regex: ?0,$options: \"i\"} }})")
	List<GeneralQuestion> findByOption4Regex(String keyword);

	boolean existsByQuestion(String question);

}
