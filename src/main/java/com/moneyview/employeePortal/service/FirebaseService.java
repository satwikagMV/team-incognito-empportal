package com.moneyview.employeePortal.service;

import com.cloudinary.Cloudinary;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FirebaseService {

    @Resource
    @Autowired
    private FirebaseApp firebaseApp;
    private final String bucketName="empoyee-portal.appspot.com";
    public String uploadFile(MultipartFile file) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket(bucketName);
        String name = generateFileName(file.getOriginalFilename());
        bucket.create(name, file.getBytes(), file.getContentType());

        return getFileUrl(name);
    }

    public void deleteFile(String url) throws IOException{
        Bucket bucket = StorageClient.getInstance().bucket(bucketName);
        String fileName=parseFileName(url);
        if (StringUtils.isEmpty(fileName)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(fileName);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + getExtension(originalFileName);
    }


    private static String parseFileName(String url) {
        // Define the pattern to match the file name
        Pattern pattern = Pattern.compile("/o/(.+?)(?:\\?|$)");
        Matcher matcher = pattern.matcher(url);


        // Find the file name if the pattern matches
        if (matcher.find()) {
            String fileName = matcher.group(1);
            // If the file name contains additional query parameters, remove them
            int index = fileName.indexOf("?");
            if (index != -1) {
                fileName = fileName.substring(0, index);
            }
            return fileName;
        } else {
            return null; // Pattern didn't match, return null
        }
    }

    private String getFileUrl(String name) {
        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",bucketName, name);
    }
    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

}
