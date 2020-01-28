package kr.co.fastcampus.eatgo.controller;

import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController {

    @GetMapping("/restaurants")
    public List<Restaurant> list(){
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant(1004L,"bob zip","Seoul");
        restaurants.add(restaurant);
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable Long id){
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L,"bob zip","Seoul"));
        restaurants.add(new Restaurant(2020L,"Cyber food","Seoul"));
        Restaurant restaurant = restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
        return restaurant;

    };
}
