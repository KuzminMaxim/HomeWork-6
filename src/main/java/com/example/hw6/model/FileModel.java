package com.example.hw6.model;

import org.springframework.web.multipart.MultipartFile;


public class FileModel {

    private MultipartFile fileData;

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

}
