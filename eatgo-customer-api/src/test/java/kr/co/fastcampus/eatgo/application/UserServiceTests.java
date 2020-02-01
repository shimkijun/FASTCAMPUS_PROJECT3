package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

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

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
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
//        "Email is already registered " + email
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

}