package entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a single Perishable Food
 */
public class PerishableFood extends Food {
    private boolean isExpired;
    private final LocalDate expiryDate;

    /**
     * Construct a PerishableFood item, giving it the given name, quantity, and expiry date.
     *
     * @param name of the PerishableFood item
     * @param quantity of the PerishableFood item
     * @param unit of measurement for the given quantity
     * @param expiryDate the expiry date of this food item, given as a LocalDate object
     */
    public PerishableFood (String name, Double quantity, String unit, LocalDate expiryDate) {
        super(name, quantity, unit);
        this.expiryDate = expiryDate;
        this.isExpired = this.updateExpired();
    }

    /**
     * Return the string representation of this perishable food object
     * @return String of desired perishable food item
     */
    @Override
    public String toString(){
        return this.name + ": " + this.quantity.toString() + " " + this.unit + ", Expires on: " +
                this.expiryDate.toString();
    }

    /**
     * Override the equals method to check content equality, rather than identity equality.
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

        PerishableFood other = (PerishableFood) obj;
        if (!Objects.equals(this.name, other.name)){
            return false;
        }else if (!Objects.equals(this.quantity, other.quantity)){
            return false;
        }else if (!Objects.equals(this.unit, other.unit)){
            return false;
        }else if (this.expiryDate.getYear() != other.expiryDate.getYear()){
            return false;
        }else if (this.expiryDate.getMonth() != other.expiryDate.getMonth()) {
            return false;
        }else return this.expiryDate.getDayOfMonth() == other.expiryDate.getDayOfMonth();
    }

    /**
     * Return the expiry status of the PerishableFood
     * @return True if the PerishableFood is expired. False otherwise.
     */
    public boolean getExpiryStatus() {
        return this.isExpired;
    }

    /**
     * Return the expiry date of the PerishableFood
     * @return the expiry date of the PerishableFood
     */
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Update the expiry status of the PerishableFood
     * @return True if the PerishableFood is expired.False otherwise.
     */
    private boolean updateExpired() {
        LocalDate today = LocalDate.now();
        return today.isAfter(expiryDate);
    }
}