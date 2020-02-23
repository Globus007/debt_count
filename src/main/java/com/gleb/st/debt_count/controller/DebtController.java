package com.gleb.st.debt_count.controller;

import com.gleb.st.debt_count.entity.Bill;
import com.gleb.st.debt_count.entity.Debt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class DebtController {

    Bill bill;
    Debt debt;

    @GetMapping("/showFormAddBill")
    public String addBill(Model model) {
        bill = new Bill();
        bill.setDate(Date.valueOf("2020-02-01"));
        bill.setPayDate(Date.valueOf("2020-02-10"));
        model.addAttribute("bill", bill);
        return "bill_form";
    }

    @PostMapping("/saveBill")
    public String saveBill(@ModelAttribute Bill bill) {
        this.bill = bill;
        System.out.println(bill);
        return "redirect:/showFormAddDebt";
    }

    @PostMapping("/saveDebt")
    public String saveDebt(@ModelAttribute Debt debt) {
        this.debt = debt;
        System.out.println(debt);
        return "redirect:/calculatePenalty";
    }

    @GetMapping("/calculatePenalty")
    public String calculatePenalty(Model model) {

        LocalDate payDate = LocalDate.parse(bill.getPayDate().toString());
        LocalDate calculationDate = LocalDate.parse(debt.getCalculationDate().toString());
        System.out.println("payDate : " + payDate);
        System.out.println("calculationDate : " + calculationDate);

        long period = DAYS.between(payDate, calculationDate);
        model.addAttribute("period", period);
        System.out.println("period : " + period);

        double penalty = bill.getAmount() * debt.getPercent() * period;
        String formattedPenalty = new DecimalFormat("#0.00").format(penalty);
        model.addAttribute("penalty", penalty);
        model.addAttribute("formattedPenalty", formattedPenalty);
        System.out.println("penalty : " + formattedPenalty);

        model.addAttribute("bill", bill);
        model.addAttribute("debt", debt);

        return "calculation";
    }

    @GetMapping("/showFormAddDebt")
    public String addDebt(Model model) {
        debt = new Debt(bill.getId());
        debt.setCalculationDate(Date.valueOf("2020-02-23"));
        model.addAttribute("debt", debt);
        return "debt_form";
    }

}
