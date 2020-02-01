package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository,passwordEncoder);
    }

    @Test
    void registerUser(){
                String email = "tester@example.com";
                String name = "tester";
                String password = "test";
        userService.registerUser(email,name,password);
        verify(userRepository).save(any());
    }

    @Test
    void registerWithExistedEmail(){
        String email = "tester@example.com";
        String name = "tester";
        String password = "test";
        User user = User.builder().build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        Throwable exception = assertThrows(EmailExistedException.class,() -> {
            userService.registerUser(email,name,password);
        });

        assertEquals("Email is already registered "+email,exception.getMessage());

        verify(userRepository,never()).save(any());
    }


    @Test
    void authenticateWithAttributes(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(),any())).willReturn(true);

        User user = userService.authenticate(email,password);

        assertEquals(user.getEmail(),email);
    }

    @Test
    void authenticateWithNotExistedEmail(){
        String email = "x@example.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        Throwable exception = assertThrows(EmailNotExitedException.class,() -> {
            userService.authenticate(email,password);
        });

        assertEquals("Email is not registered "+email,exception.getMessage());
    }

    @Test
    void authenticateWithWrongPassword(){
        String email = "test@example.com";
        String password = "x";


        User mockUser = User.builder()
                .email(email).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any())).willReturn(false);
        Throwable exception = assertThrows(PasswordWrongException.class,() -> {
            userService.authenticate(email,password);
        });

        assertEquals("Password is Wrong",exception.getMessage());
    }

}