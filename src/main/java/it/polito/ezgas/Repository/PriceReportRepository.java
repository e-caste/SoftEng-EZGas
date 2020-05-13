package it.polito.ezgas.Repository;

import it.polito.ezgas.entity.PriceReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceReportRepository extends JpaRepository<PriceReport, Integer> {
}
