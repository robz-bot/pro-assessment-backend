package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	Page<GeneralQuestion> findAllByIsActive(boolean b, Pageable paging);

	GeneralQuestion findByIdAndIsActive(Long id, boolean getisActive);

	@Query("{'question': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
//	@Query(" {\n"
//			+ "     'question': {\n"
//			+ "         $regex: '.*\\\\?0.*',\n"
//			+ "         $options: \"i\"\n"
//			+ "     },\n"
//			+ "     'isActive': true,\n"
//			+ " }")
	List<GeneralQuestion> findByQuestionAndIsActiveRegex(String keyword, boolean b);

	@Query("{'option1': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	List<GeneralQuestion> findByOption1AndIsActiveRegex(String keyword, boolean b);

	@Query("{'option3': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	List<GeneralQuestion> findByOption3AndIsActiveRegex(String keyword, boolean b);

	@Query("{'option2': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	List<GeneralQuestion> findByOption2AndIsActiveRegex(String keyword, boolean b);

	@Query("{'option4': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	List<GeneralQuestion> findByOption4AndIsActiveRegex(String keyword, boolean b);

	@Query("{'answer': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	List<GeneralQuestion> findByAnswerAndIsActiveRegex(String keyword, boolean b);

	List<GeneralQuestion> findAllByIsActive(boolean b, Sort orderByUpdatedOnDesc);

	@Query("{'question': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	Page<GeneralQuestion> findByQuestionAndIsActiveRegex(String keyword, boolean b, Pageable paging);

	@Query("{'option1': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	Page<GeneralQuestion> findByOption1AndIsActiveRegex(String keyword, boolean b, Pageable paging);

	@Query("{'option2': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	Page<GeneralQuestion> findByOption2AndIsActiveRegex(String keyword, boolean b, Pageable paging);

	@Query("{'option3': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	Page<GeneralQuestion> findByOption3AndIsActiveRegex(String keyword, boolean b, Pageable paging);

	@Query("{'option4': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	Page<GeneralQuestion> findByOption4AndIsActiveRegex(String keyword, boolean b, Pageable paging);

//	@Query("{'answer': {$regex: ?0,$options: \"i\"}, 'isActive':true }")
	@Query("{'answer': {$regex: ?0,$options: \"i\"}, 'isActive':true,  }")
	Page<GeneralQuestion> findByAnswerAndIsActiveRegex(String keyword, boolean b, Pageable paging);
	
	@Query("{$or:[{'option1': {$regex: ?0,$options: \"i\"}},{'option2': {$regex: ?0,$options: \"i\"}},{'option3': {$regex: ?0,$options: \"i\"}},{'option4': {$regex: ?0,$options: \"i\"}}],  'isActive':true }")
	Page<GeneralQuestion> getAllOptionsIsActiveRegex(String keyword, boolean b, Pageable paging);

	List<GeneralQuestion> findAllByIsActive(boolean b);



}
