package lab.web.lab_05.services;

import lab.web.lab_05.db.Partner;
import lab.web.lab_05.repository.PartnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PartnerService {

    private final PartnerRepository repository;


    public List<Partner> all() {
        return repository.findAll();
    }

    public void create(Partner partner) {
        repository.save(partner);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Optional<Partner> getByPK(int id) {
        return repository.findById(id);
    }

}
