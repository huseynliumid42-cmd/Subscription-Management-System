package az.umid.subscriptionmanagementproject.Controller;


import az.umid.subscriptionmanagementproject.dto.CreateUserRequest;
import az.umid.subscriptionmanagementproject.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import az.umid.subscriptionmanagementproject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostConstruct
    public void init() {
        System.out.println("UserController LOADED ");
    }

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping //
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody CreateUserRequest req) {
        return userService.create(req);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam String keyword){
        return userService.search(keyword);
    }
}
