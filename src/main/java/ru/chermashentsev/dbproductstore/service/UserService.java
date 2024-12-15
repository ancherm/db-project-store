package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.config.PasswordConfig;
import ru.chermashentsev.dbproductstore.model.User;
import ru.chermashentsev.dbproductstore.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.authenticateUser(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found or disabled: " + username);
        }
        System.out.println("YA TUT!");

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public void updatePassword(String username, String newPassword) {
        String encryptedPassword = passwordConfig.passwordEncoder().encode(newPassword);
        userRepository.updatePassword(username, encryptedPassword);
    }


}