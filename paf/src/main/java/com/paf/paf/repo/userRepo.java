package com.paf.paf.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.paf.paf.model.user;

@Repository
public interface userRepo extends MongoRepository<user,String>{
    
}
