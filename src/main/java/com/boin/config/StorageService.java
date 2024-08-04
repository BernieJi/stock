package com.boin.config;

import com.boin.common.BaseResponse;
import com.boin.log.SlackService;
import com.google.cloud.storage.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;


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

    // 上傳檔案
    public ResponseEntity<BaseResponse> uploadFile(MultipartFile file, String folder) throws IOException {
        var res = new BaseResponse<>();
        try{
            if(file.isEmpty()){
                return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
            }

            // 取得檔案類型
            String contentType = "";
            String unhandledContentType = file.getContentType();

            if(unhandledContentType.startsWith("image")){
                contentType = unhandledContentType.substring(6); // Ex: image/jpg
            }
            // TODO上傳檔案類型非圖片類型處理

            // 製作新檔名
            Random random = new Random();
            String newFileName = random.nextInt(10000) + System.currentTimeMillis() + "." + contentType;

            // 設定上傳資訊
            String objectName = folder + "/" + newFileName;
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            storage.create(blobInfo, file.getBytes());

            res.setCode("200");
            res.setMessage(objectName);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (StorageException e){
            slackService.sendMessage(String.format("%s error happened : %s",this.getClass(),e.toString()));
            res.setCode("500");
            res.setMessage(e.toString());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 刪除檔案
    public void deleteFile(String bucketName, String fileName){
        var res = new BaseResponse<>();
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            Blob blob = storage.get(blobId);
            boolean deleted = storage.delete(blobId);
        } catch (StorageException e) {
            slackService.sendMessage(String.format("%s error happened : %s", this.getClass(), e.toString()));
        }
    }

}


