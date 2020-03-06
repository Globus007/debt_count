package com.gleb.st.debt_count.controller;

import com.gleb.st.debt_count.component.DebtCalculator;
import com.gleb.st.debt_count.component.refinancing.rate.reader.RefinancingRateReader;
import com.gleb.st.debt_count.entity.Bill;
import com.gleb.st.debt_count.entity.debt.Debt;
import com.gleb.st.debt_count.entity.RefinancingRate;
import com.gleb.st.debt_count.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.Date;
import java.text.DecimalFormat;


@Controller
public class DebtController {

    private Bill bill;
    private Debt debt;
    private Payment payment;

    @Autowired
    private RefinancingRateReader refinancingRateReader;

    @Autowired
    private DebtCalculator debtCalculator;

    @GetMapping("/showFormAddBill")
    public String addBill(Model model) {
        bill = new Bill();
        bill.setDate(Date.valueOf("2020-02-01"));
        bill.setPayDate(Date.valueOf("2020-02-10"));
        model.addAttribute("bill", bill);
        return "bill_form";
    }

    @GetMapping("/showFormAddDebt")
    public String addDebt(Model model) {
        debt = new Debt(bill.getId());
        debt.setCalculationDate(Date.valueOf("2020-02-23"));
        model.addAttribute("debt", debt);
        return "debt_form";
    }

    @GetMapping("/showFormAddPayment")
    public String addPayment(Model model) {
        payment = new Payment(bill.getId());
        payment.setDate(Date.valueOf("2020-02-20"));
        model.addAttribute("payment", payment);
        return "payment_form";
    }

    @PostMapping("/saveBill")
    public String saveBill(@Valid Bill bill, BindingResult bindingResult) {
        System.out.println(">>>>>>>" + bindingResult);
        if (bindingResult.hasErrors()) {
            return "bill_form";
        }
        this.bill = bill;
        System.out.println(">>>>>>>" + bill);
        return "redirect:/showFormAddDebt";
    }

    @PostMapping("/saveDebt")
    public String saveDebt(Debt debt) {
        this.debt = debt;
        System.out.println(debt);
        return "redirect:/calculatePenalty";
    }

    @PostMapping("/savePayment")
    public String savePayment(Payment payment) {
        this.payment = payment;
        System.out.println(payment);
        return "redirect:/calculatePenalty";
    }

    @GetMapping("/calculatePenalty")
    public String calculatePenalty(Model model) {

        double penalty = debtCalculator.calculatePenalty(bill, debt);
        double debtPercent = debtCalculator.calculatePercent(bill, debt);

        String formattedPenalty = new DecimalFormat("#0.00").format(penalty);
        String formattedDebtPercent = new DecimalFormat("#0.00").format(debtPercent);

        model.addAttribute("debtPercent", debtPercent);
        model.addAttribute("formattedDebtPercent", formattedDebtPercent);

        model.addAttribute("penalty", penalty);
        model.addAttribute("formattedPenalty", formattedPenalty);

        if (payment != null) {
            double penaltyWithPayment = debtCalculator.calculatePenalty(bill, debt, payment);
            double debtPercentWithPayment = debtCalculator.calculatePercent(bill, debt, payment);

            model.addAttribute("penaltyWithPayment", penaltyWithPayment);
            model.addAttribute("debtPercentWithPayment", debtPercentWithPayment);
        }

        // addition info for display calculation
        model.addAttribute("bill", bill);
        model.addAttribute("debt", debt);

        java.util.Date today = new java.util.Date();
        RefinancingRate refinancingRate = refinancingRateReader.getRefinancingRareOnDate(new Date(today.getTime()));
        model.addAttribute("refinancingRate", refinancingRate);

        long period = debtCalculator.countDelayPeriod(bill.getPayDate(), debt.getCalculationDate());
        model.addAttribute("period", period);

        return "calculation";
    }


}
