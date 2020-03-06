package com.gleb.st.debt_count.entity.creditor;

public class Creditor {

    private int id;
    private String name;
    private String paymentAccount;
    private String unp;
    private String address;

    public Creditor() {}

    public Creditor(String name, String paymentAccount, String unp, String address) {
        this.name = name;
        this.paymentAccount = paymentAccount;
        this.unp = unp;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Creditor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", paymentAccount='" + paymentAccount + '\'' +
                ", unp='" + unp + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
