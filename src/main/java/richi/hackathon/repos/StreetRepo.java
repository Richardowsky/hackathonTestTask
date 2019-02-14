package richi.hackathon.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import richi.hackathon.models.Street;

public interface StreetRepo extends JpaRepository<Street, Long> {
}
