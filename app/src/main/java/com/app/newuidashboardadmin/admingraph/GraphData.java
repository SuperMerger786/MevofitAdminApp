package com.app.newuidashboardadmin.admingraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphData {

    @SerializedName("instance_currency")
    @Expose
    private String instanceCurrency;
    @SerializedName("instance_currency_symbol")
    @Expose
    private String instanceCurrencySymbol;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("graphData")
    @Expose
    private List<GraphDatum> graphData = null;

    public String getInstanceCurrency() {
        return instanceCurrency;
    }

    public void setInstanceCurrency(String instanceCurrency) {
        this.instanceCurrency = instanceCurrency;
    }

    public String getInstanceCurrencySymbol() {
        return instanceCurrencySymbol;
    }

    public void setInstanceCurrencySymbol(String instanceCurrencySymbol) {
        this.instanceCurrencySymbol = instanceCurrencySymbol;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<GraphDatum> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphDatum> graphData) {
        this.graphData = graphData;
    }

}