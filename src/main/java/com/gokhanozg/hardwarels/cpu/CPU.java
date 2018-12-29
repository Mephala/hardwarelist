package com.gokhanozg.hardwarels.cpu;

import java.util.Objects;

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
    private String codeName;
    private String cores;
    private String clock;
    private String socket;
    private String litrhography;
    private String l3Cache;
    private String tdp;
    private String released;

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getLitrhography() {
        return litrhography;
    }

    public void setLitrhography(String litrhography) {
        this.litrhography = litrhography;
    }

    public String getL3Cache() {
        return l3Cache;
    }

    public void setL3Cache(String l3Cache) {
        this.l3Cache = l3Cache;
    }

    public String getTdp() {
        return tdp;
    }

    public void setTdp(String tdp) {
        this.tdp = tdp;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

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
        if (dollarPrice != null) {
            this.multiThreadDollarPricePerformance = multiThreadPerformance / dollarPrice;
            this.singleThreadDollarPricePerformance = singleThreadPerformance / dollarPrice;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CPU)) return false;
        CPU cpu = (CPU) o;
        return Objects.equals(cpuName, cpu.cpuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuName);
    }
}
