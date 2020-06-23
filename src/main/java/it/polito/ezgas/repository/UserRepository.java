package it.polito.ezgas.repository;

import it.polito.ezgas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query ("SELECT u FROM User u WHERE u.userId = ?1")
    User findById(Integer Id);

    @Query ("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

}
