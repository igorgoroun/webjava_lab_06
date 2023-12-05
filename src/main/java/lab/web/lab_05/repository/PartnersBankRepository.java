package lab.web.lab_05.repository;

import lab.web.lab_05.db.PartnersBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnersBankRepository extends JpaRepository<PartnersBank, Long> {
}