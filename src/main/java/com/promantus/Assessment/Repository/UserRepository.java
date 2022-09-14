package com.promantus.Assessment.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.promantus.Assessment.Dto.UserDto;
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
}
