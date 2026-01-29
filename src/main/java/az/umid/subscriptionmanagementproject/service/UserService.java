package az.umid.subscriptionmanagementproject.service;




import az.umid.subscriptionmanagementproject.dto.CreateUserRequest;
import az.umid.subscriptionmanagementproject.entity.User;
import az.umid.subscriptionmanagementproject.exception.BusinessException;
import az.umid.subscriptionmanagementproject.exception.NotFoundException;
import org.springframework.stereotype.Service;
import az.umid.subscriptionmanagementproject.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
 private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    public List<User> search (String keyword){
        return userRepository.searchByName(keyword);
    }

    public User create(CreateUserRequest req) {
        userRepository.findByEmail(req.getEmail()).ifPresent(u -> {
            throw new BusinessException("Email already exists");
        });

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

}
