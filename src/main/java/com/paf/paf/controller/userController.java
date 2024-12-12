package com.paf.paf.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}
