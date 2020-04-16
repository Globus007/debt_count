package com.gleb.st.debt_count.entity.calculation;

import com.gleb.st.debt_count.entity.debtor.Contract;
import com.gleb.st.debt_count.entity.debtor.Bill;
import com.gleb.st.debt_count.entity.debtor.Debtor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class CalculationInputData {

    private Debtor debtor;
    private Contract contract;
    private List<Bill> bills;
    private Date calculationDate;
    private double totalBalance;

    public CalculationInputData() {}

    public Debtor getDebtor() {
        return debtor;
    }

    public void setDebtor(Debtor debtor) {
        this.debtor = debtor;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "CalculationData{" +
                "debtor=" + debtor +
                ", contract=" + contract +
                ", bills=" + bills +
                ", calculationDate=" + calculationDate +
                ", totalBalance=" + totalBalance +
                '}';
    }
}
