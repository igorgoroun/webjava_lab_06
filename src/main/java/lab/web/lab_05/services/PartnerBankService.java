package lab.web.lab_05.services;

import lab.web.lab_05.db.Partner;
import lab.web.lab_05.db.PartnersBank;
import lab.web.lab_05.repository.PartnerRepository;
import lab.web.lab_05.repository.PartnersBankRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PartnerBankService {

    private final PartnersBankRepository repository;


    public List<PartnersBank> all() {
        return repository.findAll();
    }

    public void create(PartnersBank pb) {
        repository.save(pb);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<PartnersBank> byPK(Long id) {
        return repository.findById(id);
    }

}
