package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation,Long> {

    Reservation save(Reservation reservation);

    List<Reservation> findByRestaurantId(Long restaurantId);
}
