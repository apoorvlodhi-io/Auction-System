package com.arango.auction.repository;

import com.arango.auction.model.User;
import com.arangodb.springframework.repository.ArangoRepository;

public interface UserRepository extends ArangoRepository<User, String> {
}
