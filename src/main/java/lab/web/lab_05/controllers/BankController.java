package lab.web.lab_05.controllers;

import lab.web.lab_05.db.Bank;
import lab.web.lab_05.db.Partner;
import lab.web.lab_05.db.PartnersBank;
import lab.web.lab_05.repository.BankRepository;
import lab.web.lab_05.repository.PartnerRepository;
import lab.web.lab_05.repository.PartnersBankRepository;
import lab.web.lab_05.services.BankService;
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
public class BankController {
    private PartnerService partnerService;
    private BankService bankService;
    private PartnerBankService partnerBankService;
    private final PartnerRepository partnerRepository;
    private final BankRepository bankRepository;
    private final PartnersBankRepository partnersBankRepository;


    @GetMapping("/bank/all")
    public String bank_all(Model model) {
        model.addAttribute("banks", bankService.all());
        return "bank/list";
    }

    @GetMapping("/bank/new")
    public String bank_new(Model model) {
        model.addAttribute("form_title", "Новий банк");
        model.addAttribute("form_action", "/bank/create");
        model.addAttribute("form_data", null);
        return "bank/form";
    }
    @PostMapping("/bank/create")
    public String bank_create(
            @RequestParam String bankName,
            @RequestParam String telNum
    ) {
        Bank bank = new Bank();
        bank.setName(bankName);
        bank.setTelNumber(telNum);
        bankService.create(bank);
        return "redirect:/bank/all";
    }

    @GetMapping("/bank/{bank_id}/edit")
    public String bank_edit(
            @PathVariable int bank_id,
            Model model
    ) {
        Optional<Bank> bank = bankService.byPK(bank_id);
        bank.ifPresent(value -> model.addAttribute("form_data", value));
        model.addAttribute("form_title", "Змінити банк");
        model.addAttribute("form_action", "/bank/save");
        return "bank/form";
    }

    @PostMapping("/bank/save")
    public String bank_save(
            @RequestParam int bank_id,
            @RequestParam String bankName,
            @RequestParam String telNum
    ) {
        Optional<Bank> b = bankService.byPK(bank_id);
        if (b.isPresent()) {
            Bank bank = b.get();
            bank.setName(bankName);
            bank.setTelNumber(telNum);
            bankRepository.save(bank);
        }
        return "redirect:/bank/all";
    }

    @GetMapping("/bank/{bank_id}/delete")
    public String bank_delete(@PathVariable int bank_id) {
        bankRepository.deleteById(bank_id);
        return "redirect:/bank/all";
    }

    @GetMapping("/partner/{partner_id}/bank/new")
    public String partner_new_bank_account(
            @PathVariable int partner_id,
            Model model
    ) {
        Optional<Partner> partner = partnerService.getByPK(partner_id);

        if (partner.isPresent()) {
            model.addAttribute("partner", partner.get());
            model.addAttribute("banks", bankService.all());
            model.addAttribute("form_title", "Новий банківський рахунок");
            model.addAttribute("form_action", "/partner/" + partner_id + "/bank/create");
            model.addAttribute("form_data", null);
            return "bank/form_partner";
        }
        return "redirect:/partner/all";
    }

    @PostMapping("/partner/{partner_id}/bank/create")
    public String partner_create_bank_account(
            @PathVariable int partner_id,
            @RequestParam String iban,
            @RequestParam int bank_id,
            @RequestParam String new_bank_name
    ) {
        Optional<Partner> partner = partnerService.getByPK(partner_id);
        Optional<Bank> bank = bankService.byPK(bank_id);
        if (partner.isPresent()) {
            PartnersBank bank_account = new PartnersBank();
            bank_account.setIban(iban);
            bank_account.setPartner(partner.get());

            if (!new_bank_name.isBlank()) {
                Bank new_bank = new Bank();
                new_bank.setName(new_bank_name);
                bankRepository.save(new_bank);
                bank_account.setBank(new_bank);
            } else bank.ifPresent(bank_account::setBank);
            partnerBankService.create(bank_account);

            if (partner.get().getActualBankAccount() == null) {
                Partner p = partner.get();
                p.setActualBankAccount(bank_account);
                partnerRepository.save(p);
            }
            return "redirect:/partner/" + partner.get().getId();
        }
        return "redirect:/partner/all";
    }

    @GetMapping("/partner/{partner_id}/bank/{bank_id}/edit")
    public String partner_edit_bank_account(
            @PathVariable int partner_id,
            @PathVariable Long bank_id,
            Model model
    ) {
        Optional<Partner> partner = partnerService.getByPK(partner_id);
        Optional<PartnersBank> pb = partnerBankService.byPK(bank_id);

        if (partner.isPresent() && pb.isPresent()) {
            model.addAttribute("partner", partner.get());
            model.addAttribute("banks", bankService.all());
            model.addAttribute("form_title", "Змінити банківський рахунок");
            model.addAttribute("form_action", "/partner/" + partner_id + "/bank/save");
            model.addAttribute("form_data", pb.get());
            return "bank/form_partner";
        }
        return "redirect:/partner/all";
    }

    @PostMapping("/partner/{partner_id}/bank/save")
    public String partner_save_bank_account(
            @PathVariable int partner_id,
            @RequestParam Long partners_bank_id,
            @RequestParam int bank_id,
            @RequestParam String iban,
            @RequestParam String new_bank_name
    ) {
        Optional<Partner> partner = partnerService.getByPK(partner_id);
        Optional<Bank> bank = bankService.byPK(bank_id);
        Optional<PartnersBank> pb = partnerBankService.byPK(partners_bank_id);
        if (partner.isPresent() && bank.isPresent() && pb.isPresent() && pb.get().getPartner() == partner.get()) {
            PartnersBank bank_account = pb.get();
            bank_account.setIban(iban);
            if (!new_bank_name.isBlank()) {
                Bank new_bank = new Bank();
                new_bank.setName(new_bank_name);
                bankRepository.save(new_bank);
                bank_account.setBank(new_bank);
            } else bank.ifPresent(bank_account::setBank);
            partnersBankRepository.save(bank_account);
            return "redirect:/partner/" + partner.get().getId();
        } else {
            return "redirect:/partner/all";
        }
    }

    @GetMapping("/partner/{partner_id}/bank/{account_id}/delete")
    public String partner_bank_account_delete(@PathVariable int partner_id, @PathVariable Long account_id) {
        partnerBankService.delete(account_id);
        return "redirect:/partner/" + partner_id;
    }

    @GetMapping("/partner/bank/accounts")
    public String partner_bank_accounts_list(Model model) {
//        partnerBankService.delete(account_id);
        model.addAttribute("accounts", partnerBankService.all());
        return "bank/list_partner";
    }

}
