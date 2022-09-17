package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.TechQuestion;

public interface TechQuestionRepository extends MongoRepository<TechQuestion, String> {

	List<TechQuestion> findByTeamId(Long teamId);

	TechQuestion findAllQnByTeamId(Long teamId);

	TechQuestion findById(Long id);

	boolean existsByTeamId(long findId);

	List<TechQuestion> findAllQuestionDistinctBy();

	@Query("{'question': {$regex: ?0,$options: \"i\"} }})")
	List<TechQuestion> findByQuestionRegex(String question);

	@Query("{'answer': {$regex: ?0,$options: \"i\"} }})")
	List<TechQuestion> findByAnswerRegex(String answer);

	@Query("{'answer': {$regex: ?0,$options: \"i\"} }})")
	List<TechQuestion> findByOption1Regex(String keyword);

	@Query("{'answer': {$regex: ?0,$options: \"i\"} }})")
	List<TechQuestion> findByOption2Regex(String keyword);

	@Query("{'answer': {$regex: ?0,$options: \"i\"} }})")
	List<TechQuestion> findByOption3Regex(String keyword);

	@Query("{'answer': {$regex: ?0,$options: \"i\"} }})")
	List<TechQuestion> findByOption4Regex(String keyword);

	boolean existsByQuestion(String question);

	List<TechQuestion> findAllByIsActive(boolean b, Sort orderByUpdatedOnDesc);

	TechQuestion findByIdAndIsActive(Long id, boolean b);

	Page<TechQuestion> findAllByIsActive(boolean b,Pageable paging);

}
