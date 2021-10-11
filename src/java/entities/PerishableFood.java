package entities;

import java.time.LocalDate;

public class PerishableFood extends Food {
    private boolean isExpired;
    private final LocalDate expiryDate;

    /**
     * @param name the name of this food
     * @param expiryDate the expiry date of this food, given as a LocalDate object
     */
    public PerishableFood (String name, LocalDate expiryDate) {
        super(name);
        this.expiryDate = expiryDate;
        this.isExpired = this.updateExpired();
    }

    /**
     * @return returns whether this is expired yet
     */
    private boolean updateExpired() {
        LocalDate today = LocalDate.now();
        return today.isBefore(expiryDate);
    }

    public boolean getExpiryStatus() {
        return this.isExpired;
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }
}
