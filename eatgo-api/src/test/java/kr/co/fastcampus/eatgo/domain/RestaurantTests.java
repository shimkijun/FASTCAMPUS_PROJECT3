package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantTests {

    @Test
    void creation(){
        Restaurant restaurant = new Restaurant("bob zip");
        assertEquals(restaurant.getName(),"bob zip");
    }

    @Test
    void information(){

    }
}