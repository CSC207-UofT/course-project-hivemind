package entities;

public abstract class Food {
    protected String name;
    protected Double quantity;
    protected String unit;

    /**
     * Construct a Food item, giving it the given name, quantity and unit.
     * @param name of the Food item
     * @param quantity of the Food item
     * @param unit the unit of measurement for the given quantity
     */
    public Food(String name, Double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Return the string representation of food object
     * @return String of desired food item to string
     */
    @Override
    public String toString(){
        return this.name + ": " + this.quantity.toString() + " " + this.unit;
    }

    /**
     * Return the name of the Food item
     * @return The name of the Food
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the available quantity of the Food item
     * @return The quantity available of the given food object
     */
    public Double getQuantity() {return this.quantity;}

    /**
     * Change the available quantity of the Food item
     * @param amount represents a change in quantity of Food. Is positive when adding a quantity,
     *               negative when subtracting a quantity
     */
    public void addQuantity(Double amount) {this.quantity += amount;}

    /**
     * Change the unit of measurement representing quantity
     * @param unit represents a new unit of measurement representing te quantity of Food
     */
    public void changeUnit(String unit) {this.unit = unit;}

    /**
     * Return a string representation of the unit of measurement for quantity of Food
     * @return a string representing the unit of measurement for quantity of Food
     */
    public String getUnit() {return this.unit;}

}