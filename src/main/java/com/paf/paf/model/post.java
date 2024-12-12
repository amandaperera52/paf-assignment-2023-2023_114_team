package com.paf.paf.model;


import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "posts")
public class post {

    @Id
    private String postId;
    private String userId;
    private String postDescription;
    private String imageName;
    private int like = 0;
    private String comment;
    private HashMap<String,Boolean> likeDetails = new HashMap<>();

    public void setHashMap(String name, Boolean b){
        likeDetails.put(name,b);
    }

    public Boolean getLikeDetailsStatus(String name){
        return likeDetails.get(name);
    }

    public String getImageName(){
        return imageName;
    }
    
}
