package richi.hackathon.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import richi.hackathon.models.Country;

public interface CountryRepo extends JpaRepository<Country, Long> {
}
