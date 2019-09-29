package com.go.gamez_era;

public class list_view_constant {

        int number;
        private String name;



        public list_view_constant() {
        }

    public list_view_constant(String name,int number) {
        this.name = name;
        this.number=number;


    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
