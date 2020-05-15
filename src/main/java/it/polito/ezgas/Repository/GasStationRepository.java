package it.polito.ezgas.Repository;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
<<<<<<< HEAD
=======
import org.springframework.data.repository.query.Param;
>>>>>>> 2f6b99268aa25426602b3823893278bf0e502092
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation,Integer> {
<<<<<<< HEAD
	
	@Query("SELECT gs FROM GasStation gs WHERE gs.gasStationId=?1")
	GasStation findById(Integer Id);
	

=======

    @Query("SELECT gs FROM GasStation gs WHERE gs.carSharing = :carSharing")
    List<GasStation> findByCarSharing(@Param("carSharing") String carSharing);
>>>>>>> 2f6b99268aa25426602b3823893278bf0e502092
}
