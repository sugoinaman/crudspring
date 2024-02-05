package com.example.dummyspring;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }
    //for the signup form

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result,Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        model.addAttribute("user",user);
        userRepository.save(user);
        return "redirect:/index";
    }
    //for adding a user


    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
    //displaying all users?

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));

        model.addAttribute("user", user);
        return "update-user";
    }
    //finding a user by id and it is passed as a model?

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            user.setId(id);
            //sets the id from user object to the id from mapping of /update/id, idk why.
            return "update-user";
        }
        return "redirect:/index";
    }

    @GetMapping("/delete{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid user"+id));
        userRepository.delete(user);
        return "redirect:/index";
    }

}