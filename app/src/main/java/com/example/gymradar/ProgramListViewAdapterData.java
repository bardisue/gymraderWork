package com.example.gymradar;

public class ProgramListViewAdapterData {
    private String name;
    private String period;
    private int cost;

    public void setName(String name){this.name = name;}
    public void setPeriod(String period){this.period = period;}
    public void setCost(int cost){this.cost = cost;}

    public String getName(){return this.name;}
    public String getPeriod(){return this.period;}
    public int getCost(){return this.cost;}
}