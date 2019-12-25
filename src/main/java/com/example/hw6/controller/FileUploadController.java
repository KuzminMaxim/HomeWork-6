package com.example.hw6.controller;

import com.example.hw6.model.FileModel;
import com.example.hw6.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FileUploadController {

    private static final String UPLOAD_FILE_ENDPOINT = "/uploadFile";

    @Autowired
    FileService fileService;

    @GetMapping(value = UPLOAD_FILE_ENDPOINT)
    public String uploadFile(Model model){
        FileModel fileModel = new FileModel();
        model.addAttribute("fileModel", fileModel);
        return "uploadFilePage";
    }

    @PostMapping(value = UPLOAD_FILE_ENDPOINT)
    public String getDataFromUploadFile(FileModel fileModel) {

        fileService.uploadFile(fileModel.getFileData());

        return "/welcomePage";
    }
}
