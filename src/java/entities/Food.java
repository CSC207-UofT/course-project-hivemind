package entities;

public abstract class Food {
    protected String name;
    protected Double quantity;

    public Food(String name, Double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public Double getQuantity() {return this.quantity;}

    public void changeQuantity(double amount) {this.quantity += amount;}

}