package it.polito.ezgas.Repository;

import it.polito.ezgas.entity.PriceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceReportRepository extends JpaRepository<PriceReport, Integer> {
}
