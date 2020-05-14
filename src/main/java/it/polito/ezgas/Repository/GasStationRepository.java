package it.polito.ezgas.Repository;

import it.polito.ezgas.entity.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation,Integer> {
	
	@Query("SELECT gs FROM GasStation gs WHERE gs.gasStationId=?1")
	GasStation findById(Integer Id);
	

}
