package it.polito.ezgas.Repository;

import it.polito.ezgas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
