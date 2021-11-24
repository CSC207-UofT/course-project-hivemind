package entities;

/**
 * Represents a single Food item
 */
public abstract class Food {
    protected String name;
    protected Double quantity;
    protected String unit;

    /**
     * Construct a Food item, giving it the given name, quantity and unit.
     * @param name of the Food
     * @param quantity of the Food
     * @param unit of measurement for the given quantity of Food
     */
    public Food(String name, Double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Return the string representation of food object
     * @return String of desired food item
     */
    @Override
    public String toString(){
        return this.name + ": " + this.quantity.toString() + " " + this.unit;
    }

    /**
     * Return the name of the Food
     * @return The name of the Food
     */
    public String getFoodName() {
        return this.name;
    }

    /**
     * Return the available quantity of Food
     * @return The quantity available of the given Food
     */
    public Double getFoodQuantity() {return this.quantity;}

    /**
     * Change the available quantity of Food
     * @param amount represents a change in quantity of Food. Is positive when adding a quantity,
     *               negative when subtracting a quantity
     */
    public void addFoodQuantity(Double amount) {this.quantity += amount;}

    /**
     * Change the unit of measurement representing the quantity of Food
     * @param unit represents a new unit of measurement representing te quantity of Food
     */
    public void changeFoodUnit(String unit) {this.unit = unit;}

    /**
     * Return a string representation of the unit of measurement for quantity of Food
     * @return a string representing the unit of measurement for quantity of Food
     */
    public String getFoodUnit() {return this.unit;}

}