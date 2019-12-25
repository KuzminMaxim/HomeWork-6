package com.example.hw6.services;

import com.example.hw6.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@Service
public class UserService {
    public static String findUserByNameAndSurname(String name, String surname, Model model){
        try (FileReader file = new FileReader("allUsers.txt")) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {

                    String line = scanner.nextLine();
                    String[] cols = line.split("/");

                    if (cols[0].equals(name) && cols[2].equals(surname)) {
                        UserModel userModel = new UserModel();

                        userModel.setFirstName(cols[0]);
                        userModel.setMiddleName(cols[1]);
                        userModel.setLastName(cols[2]);
                        userModel.setEmail(cols[3]);
                        userModel.setPlaceOfWork(cols[4]);
                        userModel.setAge(Integer.parseInt(cols[5]));
                        userModel.setSalary(Double.parseDouble(cols[6]));

                        model.addAttribute("foundUser", userModel);
                        return "found";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "notFound";
    }
}
