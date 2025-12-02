package de.seuhd.campuscoffee.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.api.mapper.UserDtoMapper;
import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.ports.UserDataService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Collections;

@Tag(name = "Users", description = "Operations related to user management.")
@Controller
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    //TODO: Implement user controller
    private final UserDataService userDataService;
    private final UserDtoMapper userDtoMapper;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) String loginName) {
        if (loginName != null) {
            try {
                User user = userDataService.getyLoginName(loginName);
                return ResponseEntity.ok(List.of(userMapper.toDto(user)));
            } catch (Exception e) {
                return ResponseEntity.ok(Collections.emptyList());
            }
        }
        List<User> users = userDataService.getAll();
        return ResponseEntity.ok(userMapper.toDtoList(users));
    }



    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toDomain(userDto);
        User savedUser = userDataService.upsert(user);
        return new ResponseEntity<>(userDtoMapper.toDto(savedUser), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id @Valid @RequestBody UserDto userDto) {
        try {
            User existingUser = userDataService.getById(id);
            User updatedUser = existingUser.toBuilder()
                    .username(userDto.loginNmae())
                    .email(userDto.emailAddress())
                    .firstName(userDto.firstName())
                    .lastName(userDto.lastName())
                    .updated(java.time.LocalDateTime.now())
                    .build();

            User savedUser = userDataService.upsert(updatedUser);
            return ResponseEntity.ok(userMapper.toDto(savedUser));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try{
            User user = userDataService.getById(id);
            return ResponseEntity.ok(userDtoMapper.toDto(user));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        try {
            userDataService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}