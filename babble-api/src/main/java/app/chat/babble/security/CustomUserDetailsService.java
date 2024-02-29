package app.chat.babble.security;

import app.chat.babble.model.User;
import app.chat.babble.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null || user.isDeleted()) {
            throw new UsernameNotFoundException("User not found or deleted with username: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities((Collection) AuthorityUtils.createAuthorityList(user.getPermissions().stream().map(p -> p.name()).collect(Collectors.toSet())))
                .build();
    }
}
