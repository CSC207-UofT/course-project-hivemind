package entities;

public abstract class Food {
    protected String name;
    protected Double quantity;

    /**
     * Construct a Food item, giving it the given name and quantity.
     *
     * @param name the name of this item
     * @param quantity the quantity of this item
     */
    public Food(String name, Double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Return the name of the Food item
     *
     * @return The name of the Food
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the available quantity of the Food item
     *
     * @return The quantity available of the given food object
     */
    public Double getQuantity() {return this.quantity;}

    /**
     * Change the available quantity of the Food item
     *
     * @param amount represents a change in quantity of Food. Is positive when adding a quantity,
     *               negative when subtracting a quantity
     */
    public void changeQuantity(double amount) {this.quantity += amount;}

}