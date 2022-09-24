package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Entity.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findById(long userId);

	void deleteById(long parseLong);

	User findByEmpCode(String empCode);

	User findByEmail(String email);

	User findByEmailIgnoreCase(String email);

	@Query("{\"$or\": [\r\n"
			+ "        { firstName: { '$regex': ?0, '$options': 'i' } },\r\n"
			+ "        { lastName: { '$regex': ?0, '$options': 'i' } }\r\n"
			+ "    ]}")
	List<User> findByFirstNameLastNameRegex(String keyword);

	List<User> findAllByAttempts(int i);

	User findByTeamId(String teamId);

	List<User> findAllByIsActive(boolean b, Sort orderByUpdatedOnDesc);

	List<User> findAllByEmpCode(String empCode);

	Page<User> findAllByIsActive(boolean b, Pageable paging);

	@Query("{\"$or\": [\r\n"
			+ "        { firstName: { '$regex': ?0, '$options': 'i' } },\r\n"
			+ "        { lastName: { '$regex': ?0, '$options': 'i' } }\r\n"
			+ "    ]}")
	Page<User> findByFirstNameLastNameRegex(String keyword, boolean b, Pageable paging);

	@Query("{'empCode': {$regex: ?0,$options: \"i\"} }})")
	Page<User> getAllEmpCodeRegex(String keyword, boolean b, Pageable paging);

	@Query("{'email': {$regex: ?0,$options: \"i\"} }})")
	Page<User> getAllEmailRegex(String keyword, boolean b, Pageable paging);

	@Query("{'manager': {$regex: ?0,$options: \"i\"} }})")
	Page<User> getAllManagerRegex(String keyword, boolean b, Pageable paging);

	@Query("{'teamId': {$regex: ?0,$options: \"i\"} }})")
	Page<User> getAllTeamRegex(String keyword, boolean b, Pageable paging);

	@Query("{'attepmts': {$regex: ?0,$options: \"i\"} }})")
	Page<User> getAllAttemptsRegex(int keyword, boolean b, Pageable paging);

	Page<User> findAllByTeamId(long parseLong, Pageable paging);

	Page<User> findAllByAttempts(int parseInt, boolean b, Pageable paging);
}
