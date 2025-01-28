package com.soriani.book.auth;

import com.soriani.book.role.RoleRepository;
import com.soriani.book.user.Token;
import com.soriani.book.user.TokenRepository;
import com.soriani.book.user.User;
import com.soriani.book.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String CODE_LETTERS = "0123456789";
    private static final int CODE_LENGTH = 6;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER").orElseThrow( () -> new IllegalStateException("Role User was not initialized")); //TODO - better exception handling
        User user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) {
        String newToken = generateAndSaveActivationToken(user);
        //send email
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user);
        return null;
    }

    private String generateActivationCode() {
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        IntStream.of(0, CODE_LENGTH).forEach(i -> code.append(CODE_LETTERS.charAt(random.nextInt(CODE_LETTERS.length()))));
        return code.toString();
    }

}
