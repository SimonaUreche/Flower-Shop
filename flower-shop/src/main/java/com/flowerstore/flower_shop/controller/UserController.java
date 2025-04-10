package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final IUserService iuserService;

    public UserController(IUserService iuserService) {
        this.iuserService = iuserService;
    }

    @Operation(summary = "Fetch all users", description = "This method returns a list of all users in the database.")
    @ApiResponse(responseCode = "200", description = "The list of users was successfully returned.")
    @GetMapping
    public ResponseEntity findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(iuserService.getAllUsers());
    }

    @Operation(summary = "Create a new user", description = "This method adds a new user to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully added."),
            @ApiResponse(responseCode = "400", description = "The request contains invalid data.")
    })
    @PostMapping
    public ResponseEntity saveNewUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(iuserService.addUser(user));
    }

    @Operation(summary = "Fetch a user by ID", description = "This method returns the user with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found and returned."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity getUser(@Parameter(description = "ID-ul utilizatorului", required = true) @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iuserService.getUserById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Update user information", description = "This method allows updating the data of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully updated."),
            @ApiResponse(responseCode = "400", description = "The provided data is invalid.")
    })
    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(iuserService.updateUser(user));
    }

    @Operation(summary = "Delete a user", description = "This method deletes the user with the specified ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was successfully deleted."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@Parameter(description = "ID-ul utilizatorului", required = true) @PathVariable Long id) {
        iuserService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successful operation");
    }
}
