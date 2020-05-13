package it.polito.ezgas.Repository;

import it.polito.ezgas.entity.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GasStationRepository extends JpaRepository<GasStation,Integer> {
}
