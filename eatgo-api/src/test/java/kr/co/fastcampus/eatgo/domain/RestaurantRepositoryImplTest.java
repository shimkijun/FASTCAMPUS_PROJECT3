package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantRepositoryImplTest {

    @Test
    void save(){
        RestaurantRepository restaurantRepository = new RestaurantRepositoryImpl();

        int oldCount = restaurantRepository.findAll().size();

        Restaurant restaurant = new Restaurant("BeRyong","Seoul");
        restaurantRepository.save(restaurant);

        assertEquals(restaurant.getId(),1234L);

        int newCount = restaurantRepository.findAll().size();

        assertEquals(newCount - oldCount , 1);

    }

}