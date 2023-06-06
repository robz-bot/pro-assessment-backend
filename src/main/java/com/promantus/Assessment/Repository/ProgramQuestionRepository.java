package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.ProgramQuestion;

public interface ProgramQuestionRepository extends MongoRepository<ProgramQuestion, String> {

	ProgramQuestion findById(Long id);

	List<ProgramQuestion> findAll();

	List<ProgramQuestion> findAllQuestionDistinctBy();

	ProgramQuestion findByQuestion(String question);

	@Query("{'question': {$regex: ?0,$options: \"i\"} }})")
	List<ProgramQuestion> findByQuestionRegex(String question);

	@Query("{'program': {$regex: ?0,$options: \"i\"} }})")
	List<ProgramQuestion> findByProgramRegex(String program);

	boolean existsByQuestion(String question);

	Page<ProgramQuestion> findAllByIsActive(boolean b, Pageable paging);

	ProgramQuestion findByIdAndIsActive(Long id, boolean getisActive);

	@Query("{'question': {$regex: ?0,$options: \"i\"}, 'isActive':true }")

	List<ProgramQuestion> findByQuestionAndIsActiveRegex(String keyword, boolean b);

	@Query("{'program': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	List<ProgramQuestion> findByProgramAndIsActiveRegex(String keyword, boolean b);

	List<ProgramQuestion> findAllByIsActive(boolean b, Sort orderByUpdatedOnDesc);

	@Query("{'question': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	Page<ProgramQuestion> findByQuestionAndIsActiveRegex(String keyword, boolean b, Pageable paging);

	@Query("{'program': {$regex: ?0,$options: \"i\"}, 'isActive':true,  }")
	Page<ProgramQuestion> findByProgramAndIsActiveRegex(String keyword, boolean b, Pageable paging);
	
	List<ProgramQuestion> findAllByIsActive(boolean b);
	
	@Query("{'date': {$regex: ?0,$options: \"i\"}, 'isActive':true,  }")
	Page<ProgramQuestion> findByDateAndIsActiveRegex(String keyword, boolean b, Pageable paging);

	List<ProgramQuestion> findAllByTeamIdAndIsActive(Long id, boolean b);

}
