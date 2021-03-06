package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void list() throws Exception {
        List<Restaurant> restaurans = new ArrayList<>();
        restaurans.add(Restaurant.builder()
                    .id(1004L)
                    .categoryId(1L)
                    .name("JokerHouse")
                    .address("Seoul")
                    .build());

        given(restaurantService.getRestaurants("Seoul",1L))
                .willReturn(restaurans);

        mvc.perform(get("/restaurants?region=Seoul&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JokerHouse\"")));
    }

    @Test
    void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                    .id(1004L)
                    .categoryId(1L)
                    .name("JokerHouse")
                    .address("Seoul")
                    .build();
        MenuItem menuItem = MenuItem.builder()
                    .name("Kimchi")
                    .build();
        restaurant.setMenuItems(Arrays.asList(menuItem));
        Review review = Review.builder()
                .name("JOKER")
                .score(5)
                .description("Good")
                .build();
        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JokerHouse\"")))
                .andExpect(content().string(containsString("Kimchi")))
                .andExpect(content().string(containsString("Good")));
    }

    @Test
    void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }
}