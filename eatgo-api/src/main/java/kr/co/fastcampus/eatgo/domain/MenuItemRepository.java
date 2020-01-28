package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository {
    List<MenuItem> findAllByRestaurantId(Long id);
}
