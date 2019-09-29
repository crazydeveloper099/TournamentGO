package com.go.gamez_era;

public class result_model_class
{
    int number;
    int kills;
    int amount;
    private String name;

    public result_model_class(int number, int kills, int amount, String name) {
        this.number = number;
        this.kills = kills;
        this.amount = amount;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
