package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Long> {

    Review save(Review review);

    List<Review> findAllByRestaurantId(Long restaurantId);

    List<Review> findAll();


}
