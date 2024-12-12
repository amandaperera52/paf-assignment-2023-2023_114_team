package com.paf.paf.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paf.paf.model.post;
import com.paf.paf.model.user;
import com.paf.paf.repo.postRepo;
import com.paf.paf.repo.userRepo;
import com.paf.paf.util.postUtil;

@RequestMapping("/v1/post")
@RestController
@CrossOrigin
public class postController {

    @Autowired
    private postRepo repo;

    @Autowired
    private userRepo uRepo;
    
    @PostMapping("/{userId}/addPost")
    public void uploadPost(@RequestParam("files") MultipartFile[] files,
    @RequestParam("postDescription") String postDescription,
    @PathVariable("userId") String userId,
    @RequestParam("b") boolean b){
        String uploadDir = "Image";
        Arrays.asList(files).stream().forEach(file->{
            String fileName = (Objects.requireNonNull(file.getOriginalFilename()));
            System.out.println(fileName);
            post p = new post();
            p.setPostDescription(postDescription);
            p.setImageName(fileName);
            p.setUserId(userId);
            p.setHashMap(userId, b);
            
            repo.save(p);
            try{
                postUtil.saveFile(uploadDir, fileName, file); 
            }catch(IOException e){

            }
        });
    }
    @PutMapping("/{postId}/update")
    public void updatePost(@RequestParam("files") MultipartFile[] files,
    @RequestParam("postDescription") String postDescription,
    @PathVariable("postId") String postId){

        String uploadDir = "Image";
        Arrays.asList(files).stream().forEach(file->{

            String fileName = (Objects.requireNonNull(file.getOriginalFilename()));
            System.out.println(fileName);
            
            post exPost =  repo.findByPostId(postId);
            exPost.setPostDescription(postDescription);
            exPost.setImageName(fileName);
            
            repo.save(exPost);
            try{
                postUtil.saveFile(uploadDir, fileName, file); 
            }catch(IOException e){

            }
        });
    }


    @PutMapping("/like")
    public Boolean addLike(@RequestBody post p){
        
        post exPost = repo.findById(p.getPostId()).get();
        String eID = exPost.getUserId();
        Boolean b = exPost.getLikeDetailsStatus(eID);
        int num = exPost.getLike();

        if(b == false){
            exPost.setLike(++num);
            exPost.setHashMap(eID, true);
            repo.save(exPost);
            return true;
        }else {
            exPost.setLike(--num);
            exPost.setHashMap(eID, false);
            repo.save(exPost);
            return false;
        }


        /* 
        post exPost = repo.findById(p.getPostId()).get();

        int num = exPost.getLike();
        exPost.setLike(++num);

        repo.save(exPost);*/
    }

    @PutMapping("/comment")
    public void addComeent(@RequestBody post p,String msg){
        
        post exPost = repo.findById(p.getPostId()).get();

        exPost.setComment(msg);

        repo.save(exPost);
    }
    
    @PutMapping("/post/update")
    public void updateTask(@RequestParam("files")MultipartFile files,@RequestBody post p){
         
            //get the exsting document from DB
           // post exPost = repo.findById(p.getPostId()).get();
             post exPost = repo.findById(p.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));


            String uploadDir = "Image";
            Arrays.asList(files).stream().forEach(file->{
                String fileName = (Objects.requireNonNull(file.getOriginalFilename()));
                System.out.println(fileName);
            
                exPost.setImageName(fileName);
                exPost.setPostDescription(p.getPostDescription());
    
                repo.save(exPost);
                try{
                    postUtil.saveFile(uploadDir, fileName, file); 
                }catch(IOException e){
    
                }
            });
   
   
    }

    @GetMapping("/post/{userId}")
    public List<post> findOnePost(@PathVariable String userId){
        return repo.findByUserId(userId);
    }


    @GetMapping("/post")
    public List<post> getAllPost(){
        return repo.findAll();
    }

    @GetMapping("/post/{userId}/{postId}")
    public post findOneUserOnePost(@PathVariable String postId){
        return repo.findByPostId(postId);
    }

    @GetMapping("/post/get/{postId}")
    public post getImage(@PathVariable String postId){
        post p = repo.findByPostId(postId);
        return p;
    }

    @DeleteMapping("/post/{postId}")
    public void deletePost(@PathVariable String postId){
        repo.deleteById(postId);
    }

    @PostMapping("/{userId}/sharePost")
    public void sharePOst(@RequestParam("files") String files,
    @RequestParam("postDescription") String postDescription,
    @PathVariable("userId") String userId,
    @RequestParam("b") boolean b){
       
            post p = new post();
            p.setPostDescription(postDescription);
            p.setImageName(files);
            p.setUserId(userId);
            p.setHashMap(userId, b);
            
            repo.save(p);
       
    }

    @GetMapping("/{userID}/getuserDetails")
    public user getUSer(@RequestParam("userId") String userid){
        user u = uRepo.findById(userid).get();

        return u;
    }
}
