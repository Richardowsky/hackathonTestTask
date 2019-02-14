package richi.hackathon.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import richi.hackathon.models.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
