package com.paf.paf.util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;


public class postUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException{
        Path uploadPath = Paths.get("C:\\Users\\Amanda's PC\\OneDrive\\Desktop\\PAF Project\\paf-assignment-2023-2023_114_team\\IT21022388\\src\\main\\resources\\static\\imagesImage" + uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            
        }
    }
    
}
