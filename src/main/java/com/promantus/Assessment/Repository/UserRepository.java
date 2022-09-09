package com.promantus.Assessment.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.promantus.Assessment.Entity.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findById(long userId);

	void deleteById(long parseLong);

	User findByEmpCode(String empCode);

	User findByEmail(String email);

	User findByEmailIgnoreCase(String email);


}
