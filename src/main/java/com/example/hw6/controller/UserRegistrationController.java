package com.example.hw6.controller;

import com.example.hw6.model.UserModel;
import com.example.hw6.services.UserService;
import com.example.hw6.validator.AgeValidator;
import com.example.hw6.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileWriter;
import java.io.IOException;

@Controller
public class UserRegistrationController {

    private EmailValidator emailValidator = new EmailValidator();
    private AgeValidator ageValidator = new AgeValidator();

    @Autowired
    public JavaMailSender emailSender;

    private static final String CREATE_USER_ENDPOINT = "/createUser";

    @GetMapping(value = "/")
    public String welcomePage(){
        return "/welcomePage";
    }

    @GetMapping(value = CREATE_USER_ENDPOINT)
    public String viewRegistrationForm(Model model, UserModel userModel){
        model.addAttribute("userModel", userModel);
        return "/userRegistrationPage";
    }

    @PostMapping(value = CREATE_USER_ENDPOINT)
    public String sendRegistrationForm(UserModel userModel, Model model){

        if (UserService.findUserByNameAndSurname(userModel.getFirstName(), userModel.getLastName(), model).equals("notFound")){
            if (emailValidator.validate(userModel.getEmail()) && ageValidator.validate(userModel.getAge())){
                try (FileWriter writer = new FileWriter("allUsers.txt", true)){
                    writer.write(userModel.getFirstName() + "/" + userModel.getMiddleName() + "/" + userModel.getLastName() + "/" +
                            userModel.getEmail() + "/" + userModel.getPlaceOfWork() + "/" + userModel.getAge() + "/" + userModel.getSalary());
                    writer.append('\n');

                    /////////////////////////send email/////////////////////////////////////////

                    SimpleMailMessage message = new SimpleMailMessage();

                    message.setTo(userModel.getEmail());
                    message.setSubject("Greeting");
                    message.setText("Hello from my app, " + userModel.getFirstName());

                    this.emailSender.send(message);

                    /////////////////////////////////////////////////////////////////////////////

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "/welcomePage";
            } else {
                if (!emailValidator.validate(userModel.getEmail())){
                    model.addAttribute("emailError", userModel.getEmail());
                }
                if (!ageValidator.validate(userModel.getAge())){
                    model.addAttribute("ageError", userModel.getAge());
                }
                return "/userRegistrationPage";
            }
        } else {
            return "/errorPage";
        }
    }
}
