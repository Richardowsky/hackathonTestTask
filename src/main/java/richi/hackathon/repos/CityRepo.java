package richi.hackathon.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import richi.hackathon.models.City;

public interface CityRepo extends JpaRepository<City, Long> {
}
