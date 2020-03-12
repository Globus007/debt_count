package com.gleb.st.debt_count.entity.debtor;

public class Debtor {

    private int id;
    private String name;
    private String props;

    public Debtor() {}

    public Debtor(String name, String address) {
        this.name = name;
        this.props = address;
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

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "Debtor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", props='" + props + '\'' +
                '}';
    }
}
