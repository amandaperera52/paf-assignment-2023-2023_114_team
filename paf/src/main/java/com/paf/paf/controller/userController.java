package com.paf.paf.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paf.paf.model.user;
import com.paf.paf.repo.userRepo;
import com.paf.paf.util.postUtil;

@RestController
@CrossOrigin
@RequestMapping("/v1/user")
public class userController {

    @Autowired
    private userRepo repo;

    @PostMapping("/addUser")
    public void addUser(@RequestParam("files")MultipartFile files,String userName,String email, String password){
        String uploadDir = "Image";
        Arrays.asList(files).stream().forEach(file->{
            String fileName = (Objects.requireNonNull(file.getOriginalFilename()));
            System.out.println(fileName);
           
            user u = new user();
            u.setUserName(userName);
            u.setImageName(fileName);
            u.setEmail(email);
            u.setPassword(password);
            
            repo.save(u);
            try{
                postUtil.saveFile(uploadDir, fileName, file); 
            }catch(IOException e){

            }
        });
    }

    @GetMapping("/user/{id}")
    public user findOnePost(@PathVariable String id){
        return repo.findById(id).get();
    }

    @GetMapping("/user/{OwnerId}/{reqId}")
    public String userFollow(@PathVariable String OwnerId,@PathVariable String reqId){
        user owner =  repo.findById(OwnerId).get();
        String ONID = owner.getUserId();

        user reqstUser = repo.findById(reqId).get();
        String RQUID = reqstUser.getUserId();

        String res = owner.getFollowOrNot(RQUID);
        
       // String resp = reqstUser.getFollowOrNot(ONID);

        if(res == null ){
            owner.setHashMap(RQUID,"Follow");
            repo.save(owner);
            // reqstUser.setHashMap(ONID, "Follow");
            // repo.save(reqstUser);
            return "Follow";
        }else if(res == "Follow" ){
            owner.setHashMap(RQUID,"Requested");
            repo.save(owner);
            // reqstUser.setHashMap(ONID, "Requested");
            // repo.save(reqstUser);
            return "Requested";
        }else if(res == "Requested" ){
            owner.setHashMap(RQUID,"UnFollow");
            repo.save(owner);
            // reqstUser.setHashMap(ONID, "UnFollow");
            // repo.save(reqstUser);
            return "UnFollow";
        }else if(res == "Unfollow" ){
            owner.setHashMap(RQUID,"Follow");
            repo.save(owner);
            // reqstUser.setHashMap(ONID, "Follow");
            // repo.save(reqstUser);
            return "Follow";
        }else {
            return "Null";
        }

       
    }

    @GetMapping("/allUser")
    public List<user> displayAllUsers(){
        return repo.findAll();
    }

    @GetMapping("/getValue/{OwnerId}/{reqId}")
    public String getValue(@PathVariable String OwnerId,@PathVariable String reqId){
        user u = repo.findById(OwnerId).get();
        String b = u.getStatus(reqId);

       return b;
    }

    @PutMapping("/follow/{OwnerId}/{reqId}")
    public void setResquest(@RequestParam("notification") String noti,@PathVariable String OwnerId ,@PathVariable String reqId){
        
        user u = repo.findById(reqId).get();
        u.setNotification(noti);
        repo.save(u);

    }

    @PutMapping("/user/{OwnerId}/{reqId}")
    public void userFollowSetUp(@RequestParam("follow") String value,@PathVariable String OwnerId,@PathVariable String reqId){
        user owner =  repo.findById(OwnerId).get();
        String ONID = owner.getUserId();

        user reqstUser = repo.findById(reqId).get();
        String RQUID = reqstUser.getUserId();

        String res = owner.getFollowOrNot(RQUID);

            owner.setHashMap(RQUID,value);
            repo.save(owner);

            reqstUser.setHashMap(ONID, value);
            repo.save(reqstUser);
           
    }

    @GetMapping("/notification/{OwnerId}")
    public ArrayList<String> getAllNotification(@PathVariable String OwnerId){
        user u = repo.findById(OwnerId).get();

        return u.getNotification();
    }

    @DeleteMapping("/notification/delete/{ownerID}/{delNo}")
    public void  deleteNotification(@PathVariable String ownerID,@PathVariable String delNo){
        user u = repo.findById(ownerID).get();

        int no = Integer.parseInt(delNo);
        u.removeNoti(no);

        repo.save(u);

    }

    @GetMapping("/suggest/{myId}")
    public HashSet<user> suggestFrinends(@PathVariable String myId){
        HashSet<user> suggestList = new HashSet<user>();

        user me = repo.findById(myId).get();
        Set<String> myFriends = me.getFollowKeys();

        if(myFriends == null){

        }else{
        for(String frineds: myFriends){
            user newFrined = repo.findById(frineds).get();
            Set<String> newFrinedFriends = newFrined.getFollowKeys();

            for(String newfri: newFrinedFriends){
                if(!newfri.equalsIgnoreCase(frineds) && !newfri.equalsIgnoreCase(myId) && me.getFollowOrNot(frineds).equalsIgnoreCase("Follow")){
                   // if(newfri != myId){
                       // suggestList.add(frineds);
                        suggestList.add( repo.findById(frineds).get());
                  //  }
                }
            }
        }

    }

        return suggestList;
    }

    @GetMapping("/getOnlySuggest/{myId}")
    public List<user> getOnlySuggestAccount(@PathVariable String myId){
        List<user> suggestList = new ArrayList<user>();

        user me = repo.findById(myId).get();
        Set<String> myFriends = me.getFollowKeys();
        
        for(String frineds: myFriends){
            if(me.getFollowOrNot(frineds).equalsIgnoreCase("Follow")){
            user newFrined = repo.findById(frineds).get();
            Set<String> newFrinedFriends = newFrined.getFollowKeys();

            for(String newfri: newFrinedFriends){
                if(!newfri.equalsIgnoreCase(frineds)  && newFrined.getFollowOrNot(newfri).equalsIgnoreCase("Follow")){
                    //if(newfri == myId){
                       // suggestList.add(frineds);
                        suggestList.add( repo.findById(newfri).get());
                   // }
                }
            }
        }
        }

        return suggestList;
    }
}
