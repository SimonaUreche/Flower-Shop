package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

//@RestController
//@RequestMapping("/users")
//@CrossOrigin(origins = "http://localhost:3000") //Permite cereri din React (care rulează pe portul 3000)
//public class UserController {
//    private final MockDataService mockDataService;
//
//    public UserController(MockDataService mockDataService) {
//        this.mockDataService = mockDataService;
//    }
//
//    @GetMapping
//    public List<User> getAllUsers() {
//        return mockDataService.getUsers();
//    }
//
//    @PostMapping
//    public User addUser(@RequestBody User user) {
//        return mockDataService.addUser(user);
//    }
//
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return mockDataService.getUsers().stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("User not found"));
//    }
//
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        return mockDataService.updateUser(id, updatedUser);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        mockDataService.deleteUser(id);
//    }
//}

@Controller
@RequestMapping("/user")
public class UserController {
    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping()
    public String showUsers(Model model) {
        List<User> users = iUserService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list-users";
    }

    @PostMapping("/save")
    public String saveUsers(@ModelAttribute User user) {
        iUserService.addUser(user);
        return "redirect:/user";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            User user = iUserService.getUserById(id);
            model.addAttribute("user", user);
            return "users/edit-user";
        } catch (NoSuchElementException e) {
            return "redirect:/user";
        }
    }

    @PostMapping("/update")
    public String updateUsers(@ModelAttribute User user) {
        iUserService.updateUser(user);
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsers(@PathVariable Long id) {
        iUserService.deleteUser(id);
        return "redirect:/user";
    }

}