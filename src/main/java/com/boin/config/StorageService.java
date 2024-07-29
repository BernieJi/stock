package com.boin.config;

import com.boin.log.SlackService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class StorageService {

    @Autowired
    private SlackService slackService;

    @Autowired
    private Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public void uploadFile(MultipartFile file, String folder) throws IOException {
        try{
            String objectName = folder + "/" + file.getOriginalFilename();
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            storage.create(blobInfo, file.getBytes());
        } catch (StorageException e){
            slackService.sendMessage(String.format("%s error happened : %s",this.getClass(),e.toString()));
        }

    }
}


