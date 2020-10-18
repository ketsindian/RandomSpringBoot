package com.example.demo.data;

public class SummaryByCity {

    private String cityName;

    private Double totalAmount;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "SummaryByCity{" +
                "cityId=" + cityName +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
