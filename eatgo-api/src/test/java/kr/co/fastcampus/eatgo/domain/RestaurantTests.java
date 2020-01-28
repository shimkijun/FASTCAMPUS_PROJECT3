package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantTests {

    @Test
    void creation(){
        Restaurant restaurant = new Restaurant(1004L,"bob zip","Seoul");
        assertEquals(restaurant.getId(),1004L);
        assertEquals(restaurant.getName(),"bob zip");
        assertEquals(restaurant.getAddress(),"Seoul");
    }

    @Test
    void information(){
        Restaurant restaurant = new Restaurant("bob zip","Seoul");
        assertEquals(restaurant.getInfomation(),"bob zip in Seoul");
    }
}