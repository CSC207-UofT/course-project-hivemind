package entities;

public class NonPerishableFood extends Food {

    /**
     * Construct a NonPerishableFood item, giving it the given name, quantity, and unit.
     * @param name of the NonPerishableFood item
     * @param quantity of the NonPerishableFood item
     * @param unit of measurement for the given quantity
     */
    public NonPerishableFood (String name, Double quantity, String unit) {
        super(name, quantity, unit);
    }
}