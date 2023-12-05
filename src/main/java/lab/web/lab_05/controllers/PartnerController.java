package lab.web.lab_05.controllers;

import lab.web.lab_05.db.Partner;
import lab.web.lab_05.db.PartnersBank;
import lab.web.lab_05.repository.PartnerRepository;
import lab.web.lab_05.services.PartnerBankService;
import lab.web.lab_05.services.PartnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class PartnerController {
    private PartnerService partnerService;
    private PartnerBankService partnerBankService;
    private final PartnerRepository partnerRepository;

    @GetMapping("/partner/all")
    public String partners(Model model) {
        model.addAttribute("partners", partnerService.all());
        return "partner/list";
    }

    @GetMapping("/partner/new")
    public String partner_new(Model model) {
        model.addAttribute("form_title", "Новий контрагент");
        model.addAttribute("form_action", "/partner/create");
        model.addAttribute("form_data", null);
        return "partner/form";
    }

    @PostMapping("/partner/create")
    public String partner_create(
            @RequestParam String partner_name,
            @RequestParam String address,
            @RequestParam String tel_num,
            @RequestParam String itn,
            @RequestParam String reg,
            @RequestParam String notes
    ) {
        Partner p = new Partner(
                partner_name,
                address,
                tel_num,
                itn,
                reg,
                notes
        );
        partnerService.create(p);
        return "redirect:/partner/" + p.getId();
    }

    @GetMapping("/partner/{partner_id}/edit")
    public String partner_edit(
            @PathVariable int partner_id,
            Model model
    ) {
        Optional<Partner> partner = partnerService.getByPK(partner_id);
        partner.ifPresent(value -> model.addAttribute("form_data", value));
        model.addAttribute("form_title", "Змінити контрагента");
        model.addAttribute("form_action", "/partner/save");
        return "partner/form";
    }

    @PostMapping("/partner/save")
    public String partner_save(
            @RequestParam int partner_id,
            @RequestParam String partner_name,
            @RequestParam String address,
            @RequestParam String tel_num,
            @RequestParam String itn,
            @RequestParam String reg,
            @RequestParam Long actualBankAccount,
            @RequestParam String notes
    ) {
        Optional<Partner> p = partnerService.getByPK(partner_id);
        if (p.isPresent()) {
            Partner partner = p.get();
            partner.setName(partner_name);
            partner.setAddress(address);
            partner.setTelNumber(tel_num);
            partner.setItn(itn);
            partner.setReg(reg);
            partner.setNotes(notes);
            if (actualBankAccount > 0) {
                Optional<PartnersBank> pb = partnerBankService.byPK(actualBankAccount);
                pb.ifPresent(partner::setActualBankAccount);
            } else {
                partner.setActualBankAccount(null);
            }

            partnerRepository.save(partner);
            return "redirect:/partner/" + partner.getId();
        } else {
            return "redirect:/partner/all";
        }
    }

    @GetMapping("/partner/delete")
    public String partner_delete(@RequestParam int id) {
        partnerService.delete(id);
        return "redirect:/partner/all";
    }

    @GetMapping("/partner/{partner_id}")
    public String partner_view(@PathVariable int partner_id, Model model) {
        Optional<Partner> partner = partnerService.getByPK(partner_id);
        partner.ifPresent(value -> model.addAttribute("partner", value));
        return "partner/view";
    }

//    @GetMapping("/partner/banks")
//    public String partner_banks(@RequestParam int id, Model model) {
//        Optional<Partner> partner = partnerService.getByPK(id);
//        if (partner.isPresent()) {
//            model.addAttribute("partner", partner.get());
//            return "partner_banks";
//        } else {
//            return "redirect:/partner/all";
//        }
//    }


}
