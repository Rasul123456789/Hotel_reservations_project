package com.example.myapplication;

public class FoodItem {
    private String name;
    private int price;
    private boolean selected;

    public FoodItem(String name, int price) {
        this.name = name;
        this.price = price;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
