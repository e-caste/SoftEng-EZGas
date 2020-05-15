package it.polito.ezgas.Repository;

import it.polito.ezgas.entity.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation,Integer> {

	
	@Query("SELECT gs FROM GasStation gs WHERE gs.gasStationId = ?1")
	GasStation findById(Integer Id);
	
		

    @Query("SELECT gs FROM GasStation gs WHERE gs.carSharing = :carSharing")
    List<GasStation> findByCarSharing(@Param("carSharing") String carSharing);

}
