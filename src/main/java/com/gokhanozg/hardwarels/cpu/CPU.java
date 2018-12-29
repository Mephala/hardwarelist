package com.gokhanozg.hardwarels.cpu;

/**
 * Created by Gokhan Ozgozen on 29-Dec-18.
 */
public class CPU {
    private String cpuName;
    private Double multiThreadPerformance;
    private Double singleThreadPerformance;
    private Double multiThreadDollarPricePerformance;
    private Double singleThreadDollarPricePerformance;
    private Double dollarPrice;
    private Double liraPrice;

    public Double getSingleThreadDollarPricePerformance() {
        return singleThreadDollarPricePerformance;
    }

    public void setSingleThreadDollarPricePerformance(Double singleThreadDollarPricePerformance) {
        this.singleThreadDollarPricePerformance = singleThreadDollarPricePerformance;
    }

    public Double getLiraPrice() {
        return liraPrice;
    }

    public void setLiraPrice(Double liraPrice) {
        this.liraPrice = liraPrice;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public Double getMultiThreadPerformance() {
        return multiThreadPerformance;
    }

    public void setMultiThreadPerformance(Double multiThreadPerformance) {
        this.multiThreadPerformance = multiThreadPerformance;
    }

    public Double getSingleThreadPerformance() {
        return singleThreadPerformance;
    }

    public void setSingleThreadPerformance(Double singleThreadPerformance) {
        this.singleThreadPerformance = singleThreadPerformance;
    }

    public Double getMultiThreadDollarPricePerformance() {
        return multiThreadDollarPricePerformance;
    }

    public void setMultiThreadDollarPricePerformance(Double multiThreadDollarPricePerformance) {
        this.multiThreadDollarPricePerformance = multiThreadDollarPricePerformance;
    }

    public Double getDollarPrice() {
        return dollarPrice;
    }

    public void setDollarPrice(Double dollarPrice) {
        this.dollarPrice = dollarPrice;
    }

    @Override
    public String toString() {
        return "CPU{" +
                "cpuName='" + cpuName + '\'' +
                '}';
    }
}
