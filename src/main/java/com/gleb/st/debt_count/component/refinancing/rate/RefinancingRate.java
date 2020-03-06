package com.gleb.st.debt_count.component.refinancing.rate;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class RefinancingRate {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd't'HH:mm:ss")
    private Date date;
    private double value;

    public RefinancingRate() {}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RefinancingRate{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }
}
