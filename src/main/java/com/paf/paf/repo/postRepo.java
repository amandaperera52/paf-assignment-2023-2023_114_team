package com.paf.paf.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.paf.paf.model.post;

@Repository
public interface postRepo extends MongoRepository<post,String>{

    @Query("{userId: ?0}")
    List<post> findByUserId(String userId);

    @Query("{postId: ?0}")
    post findByPostId(String postId);
    
}
