package com.simple.ecommerce.Controller;


import com.simple.ecommerce.Entity.User;
import com.simple.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping(value = "/req/signup", consumes = "application/json")
//    public User createUser(@RequestBody User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }

    @PostMapping("/req/signup")
    //@ModelAttribute User user:
    //This annotation binds the form data (submitted by the user) to a User object
    public String registerUser(@ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "signup"; // Stay on signup if there are errors
        }// validation errors: empty fields....

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        user.setRole("USER"); // role is user by default
        userRepository.save(user);

        return "redirect:/req/login"; // Redirect to login page after signup
    }

}
