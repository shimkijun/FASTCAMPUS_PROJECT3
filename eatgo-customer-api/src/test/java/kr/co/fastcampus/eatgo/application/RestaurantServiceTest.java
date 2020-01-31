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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        mockRestaurnatRepository();
        mockMenuItemRepository();
        mockReviewRepository();
        restaurantService = new RestaurantService(restaurantRepository,menuItemRepository,reviewRepository);
    }


    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();

        menuItems.add(menuItem);
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);

    }

    private void mockRestaurnatRepository() {
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("bob zip")
                .address("Seoul")
                .build();

        restaurants.add(restaurant);

        given(restaurantRepository
                .findAllByAddressContainingAndCategoryId("Seoul",1L))
                .willReturn(restaurants);

        given(restaurantRepository
                .findById(1004L))
                .willReturn(Optional.of(restaurant));
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder()
                .name("BeRyong")
                .score(1)
                .description("Bad")
                .build());

        given(reviewRepository.findAllByRestaurantId(1004L))
            .willReturn(reviews);
    }
    @Test
    void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(1004L);
        verify(reviewRepository).findAllByRestaurantId(1004L);

        assertEquals(restaurant.getId(),1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertEquals(menuItem.getName(),"Kimchi");

        Review review = restaurant.getReviews().get(0);
        assertEquals(review.getDescription(),"Bad");
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
        String region = "Seoul";
        Long categoryId = 1L;
        List<Restaurant> restaurants = restaurantService.getRestaurants(region,categoryId);
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