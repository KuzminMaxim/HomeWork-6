package com.example.hw6.controller;

import com.example.hw6.model.UserModel;
import com.example.hw6.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Controller
public class FindUserController {
    private static final String FIND_USER_ENDPOINT = "/findUser";

    @GetMapping(value = FIND_USER_ENDPOINT)
    public String searchUserByFirstAndLastName(UserModel userModel, Model model){
        model.addAttribute("userModel", userModel);
        return "/findUserPage";
    }

    @PostMapping(value = FIND_USER_ENDPOINT)
    public String displayUser(UserModel userModel, Model model, HttpServletRequest request){
                if (UserService.findUserByNameAndSurname(userModel.getFirstName(), userModel.getLastName(), model).equals("notFound")){

                    String userNotFound = "User not found!";
                    model.addAttribute("userNotFound", userNotFound);
                    return "/findUserPage";
        } else {
                    String userAgent = request.getHeader("User-agent");
                    Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());
                    UserService.findUserByNameAndSurname(userModel.getFirstName(), userModel.getLastName(), model);
                    model.addAttribute("userAgent", userAgent);
                    model.addAttribute("currentTime", ts.toString());
                    return "/findUserPage";
        }
    }
}
