package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@SpringBootTest
class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        mockRestaurnatRepository();
        restaurantService = new RestaurantService(restaurantRepository);
    }

    private void mockRestaurnatRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);
        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    @Test
    void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        assertEquals(restaurant.getId(),1004L);
    }

    @Test
    public void getRestaurantWithNotExisted() {
        Throwable exception = assertThrows(RestaurantNotFoundException.class,() -> {
            restaurantService.getRestaurant(404L);
        });

        assertEquals("Could Not Find Restaurant 404",exception.getMessage());
    }

    @Test
    void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        assertEquals(restaurants.get(0).getId(),1004L);
    }

    @Test
    void addRestaurant(){
        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });
        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);
        assertEquals(created.getId(),1234L);
    }

    @Test
    void updateRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L,"Sool zip","Busan");
        assertEquals(restaurant.getName(),"Sool zip");
        assertEquals(restaurant.getAddress(),"Busan");
    }
}