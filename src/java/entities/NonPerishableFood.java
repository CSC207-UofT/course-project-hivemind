package entities;

public class NonPerishableFood extends Food {

    /**
     * Construct a NonPerishableFood item, giving it the given name and quantity.
     *
     * @param name the name of this item
     * @param quantity the quantity of this item
     */
    public NonPerishableFood (String name, Float quantity) {
        super(name, quantity);
    }
}
