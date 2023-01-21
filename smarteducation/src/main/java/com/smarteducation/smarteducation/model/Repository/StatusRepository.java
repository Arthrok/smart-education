package com.smarteducation.smarteducation.model.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.smarteducation.smarteducation.model.Schema.Status;

public interface StatusRepository extends MongoRepository<Status, String>{
    @Query("{'id': ?0, 'status' : ?1}")
    List<Status> findByidAndStatus(String id, int status);
}
