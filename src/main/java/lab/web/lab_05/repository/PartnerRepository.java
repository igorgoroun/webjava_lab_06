package lab.web.lab_05.repository;

import lab.web.lab_05.db.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
}