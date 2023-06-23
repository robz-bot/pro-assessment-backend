package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.ProgReports;
import com.promantus.Assessment.Entity.ProgramQuestion;

public interface ProgramQuestionRepository extends MongoRepository<ProgramQuestion, String> {

	ProgramQuestion findById(Long id);

//	List<ProgramQuestion> findAll();

	List<ProgramQuestion> findAllProgramDistinctBy();

//	@Query("{'question': {$regex: ?0,$options: \"i\"} }})")
//	List<ProgramQuestion> findByQuestionRegex(String question);

	@Query("{'program': {$regex: ?0} }})")
	Page<ProgramQuestion> findByProgramRegex(String program, Pageable paging);

	boolean existsByProgram(String program);

	Page<ProgramQuestion> findAllByIsActive(boolean b, Pageable paging);

	ProgramQuestion findByIdAndIsActive(Long id, boolean getisActive);

	@Query("{'program': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	List<ProgramQuestion> findByProgramAndIsActiveRegex(String keyword, boolean b);

	List<ProgramQuestion> findAllByIsActive(boolean b, Sort orderByUpdatedOnDesc);

//	@Query("{'question': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
//	Page<ProgramQuestion> findByQuestionAndIsActiveRegex(String keyword, boolean b, Pageable paging);
//
//	@Query("{'program': {$regex: ?0,$options: \"i\"}, 'isActive':true,  }")
//	Page<ProgramQuestion> findByProgramAndIsActiveRegex(String keyword, boolean b, Pageable paging);

	List<ProgramQuestion> findAllByIsActive(boolean b);

	@Query("{'date': {$regex: ?0,$options: \"i\"}, 'isActive':true,  }")
	Page<ProgramQuestion> findByDateAndIsActiveRegex(String keyword, boolean b, Pageable paging);

//	List<ProgramQuestion> findAllByTeamIdAndIsActive(Long id, boolean b);

	Page<ProgramQuestion> findAllByTeamIdAndIsActive(int parseInt, boolean b, Pageable paging);

	Page<ProgramQuestion> findByProgramAndIsActiveRegex(String keyword, boolean b, Pageable paging);

	Page<ProgramQuestion> findAllByTeamId(String id, Pageable paging);
	
	List<ProgramQuestion> findAllByTeamId(Long id);

	Page<ProgramQuestion> findByProgramLevelRegex(String keyword, Pageable paging);

	List<ProgramQuestion> findAllByTeamIdAndIsActive(String parseLong, boolean b);
	
	List<ProgramQuestion> findAllByTeamIdAndIsActive(Long parseLong, boolean b);

	Page<ProgramQuestion> findByTeamIdRegex(String keyword, Pageable paging);

	List<ProgReports> findAllByProgramLevel(String level);

}
