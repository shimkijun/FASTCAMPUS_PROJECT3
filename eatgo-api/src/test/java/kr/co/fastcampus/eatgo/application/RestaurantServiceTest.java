package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantServiceTest {

    private RestaurantService restaurantService;

    private RestaurantRepository restaurantRepository;

    private MenuItemRepository menuItemRepository;

    @BeforeEach
    void setUp(){
        restaurantRepository = new RestaurantRepositoryImpl();
        menuItemRepository = new MenuItemRepositoryImpl();
        restaurantService = new RestaurantService(restaurantRepository,menuItemRepository);

    }

    @Test
    void getRestaurant() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        assertEquals(restaurant.getId(),1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertEquals(menuItem.getName(),"Kimchi");
    }

    @Test
    void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        assertEquals(restaurants.get(0).getId(),1004L);
    }
}