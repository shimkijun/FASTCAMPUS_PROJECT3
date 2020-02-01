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
    void restaurantOwner(){
        User user = User.builder()
                .email("tester@example.com")
                .name("테스터")
                .level(1L)
                .build();
        assertEquals(user.isRestaurantOwner(),false);

        user.setRestaurantId(1004L);

        assertEquals(user.isRestaurantOwner(),true);

        assertEquals(user.getRestaurantId(),1004L);

    }

}