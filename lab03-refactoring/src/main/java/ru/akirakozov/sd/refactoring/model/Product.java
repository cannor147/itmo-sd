package ru.akirakozov.sd.refactoring.model;

public class Product {
    private String name;
    private long price;

    public Product() {
        // No operations.
    }

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d", getName(), getPrice());
    }
}
