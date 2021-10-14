package entities;

import java.time.LocalDate;

public class PerishableFood extends Food {
    private final boolean isExpired;
    private final LocalDate expiryDate;

    /**
     * Construct a PerishableFood item, giving it the given name, quantity, and expiry date.
     *
     * @param name of the PerishableFood item
     * @param quantity of the PerishableFood item
     * @param unit of measurement for the given quantity
     * @param expiryDate the expiry date of this food item, given as a LocalDate object
     */
    public PerishableFood (String name, Float quantity, String unit, LocalDate expiryDate) {
        super(name, quantity, unit);
        this.expiryDate = expiryDate;
        this.isExpired = this.updateExpired();
    }

    /**
     * Update the expiry status of the PerishableFood
     *
     * @return True if the PerishableFood is expired.
     */
    private boolean updateExpired() {
        LocalDate today = LocalDate.now();
        return today.isAfter(expiryDate);
    }

    /**
     * Return the expiry status of the PerishableFood
     * @return True if the PerishableFood is expired
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
}