package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    void creation(){
        User user = User.builder()
                .email("tester@example.com")
                .name("테스터")
                .level(100L)
                .build();

        assertEquals(user.getName(),"테스터");
        assertEquals(user.isAdmin(),true);
        assertEquals(user.isActive(),true);

        user.deactivate();

        assertEquals(user.isActive(),false);

    }

    @Test
    void accessTokenWithPassword(){
        User user = User.builder().password("ACCESSTOKEN").build();

        assertEquals(user.getAccessToken(),"ACCESSTOKE");
    }
    @Test
    void accessTokenWithoutPassword(){
        User user = new User();
        assertEquals(user.getAccessToken(),"");
    }
}