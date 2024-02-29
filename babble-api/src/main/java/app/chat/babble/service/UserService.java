package app.chat.babble.service;

import app.chat.babble.model.User;
import app.chat.babble.model.UserRole;
import app.chat.babble.repository.UserRepository;
import app.chat.babble.security.CustomUserDetailsService;
import app.chat.babble.security.JwtTokenUtil;
import app.chat.babble.security.UserRolePermissionResolver;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Create a new user
    public User signUp(@Valid User user) {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user.setPermissions(UserRolePermissionResolver.getPermissionsForRole(user.getRole()));
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    public User getLoggedInUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUsername(user.getUsername());
    }

    // Retrieve all users
    @PreAuthorize("hasAuthority('VIEW_ALL_USERS')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve a user by ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // Retrieve a user by username
    User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Update an existing user
    public User updateUser(String id, User updatedUser) {
        // Check if the user with the given ID exists
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            // You can include other fields that can be updated
            return userRepository.save(existingUser);
        } else {
            // Handle case where user with the given ID does not exist
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    // Delete a user by ID
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void deleteUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setDeleted(true);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    private void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.iterator().next().getMessage());
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered");
        }
    }
}