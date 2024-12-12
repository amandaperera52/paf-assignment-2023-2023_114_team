package com.paf.paf.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class user {

    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String imageName;
    private HashMap<String,String> follow = new HashMap<>();
    private ArrayList<String> notification = new ArrayList<>();

    public void setHashMap(String reqId,String val){
        follow.put(reqId, val);
    }
    
    public String getFollowOrNot(String reqId){
        return follow.get(reqId);
    }
    public String getStatus(String name){
        return follow.get(name);
    }

    public void setNotification(String msh){
        notification.add(msh);
    }

    public ArrayList<String> getNotification(){
        return notification;
    }

    public void removeNoti(int num){
        notification.remove(num);
    }
    
    public Set<String> getFollowKeys() {
        return follow.keySet();
    }
    
    
}
