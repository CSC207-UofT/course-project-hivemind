package entities;

import java.util.Objects;

/**
 * Represents a single Non-Perishable Food
 */
public class NonPerishableFood extends Food {

    /**
     * Construct a NonPerishableFood item, giving it the given name, quantity, and unit.
     * @param name of the Non-Perishable Food
     * @param quantity of the Non-Perishable Food
     * @param unit of measurement for the given quantity of Non-Perishable Food
     */
    public NonPerishableFood (String name, Double quantity, String unit) {
        super(name, quantity, unit);
    }

    /**
     * Override the equals' method to check content equality, rather than identity equality.
     * @param obj The object which is to be compared to NonPerishableFood.
     * @return True if the content in obj is equivalent to NonPerishableFood. False otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if (this==obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }

        NonPerishableFood other = (NonPerishableFood) obj;
        if (!Objects.equals(this.name, other.name)){
            return false;
        }else if (!Objects.equals(this.quantity, other.quantity)){
            return false;
        }else return Objects.equals(this.unit, other.unit);
    }
}