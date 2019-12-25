package com.example.hw6.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

@Service
public class FileService {

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public void uploadFile(MultipartFile file) {

        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);


            try (FileReader fileForUpload = new FileReader(String.valueOf(copyLocation))) {
                try (Scanner scanner = new Scanner(fileForUpload)) {
                    while (scanner.hasNextLine()) {

                        String line = scanner.nextLine();
                        String[] cols = line.split("/");

                        try (FileWriter writer = new FileWriter("allUsers.txt", true)) {
                            writer.write(cols[0] + "/" + cols[1] + "/" + cols[2] + "/" +
                                    cols[3] + "/" + cols[4] + "/" + cols[5] + "/" + cols[6]);
                            writer.append('\n');
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}