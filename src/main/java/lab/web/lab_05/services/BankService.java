package lab.web.lab_05.services;

import lab.web.lab_05.db.Bank;
import lab.web.lab_05.db.Partner;
import lab.web.lab_05.db.PartnersBank;
import lab.web.lab_05.repository.BankRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BankService {
    private final BankRepository repository;

    public void create(Bank bank) {
        repository.save(bank);
    }

    public List<Bank> all() {
        return repository.findAll();
    }

    public Optional<Bank> byPK(int id) {
        return repository.findById(id);
    }
}
