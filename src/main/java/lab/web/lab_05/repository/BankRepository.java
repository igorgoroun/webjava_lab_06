package lab.web.lab_05.repository;

import lab.web.lab_05.db.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
}