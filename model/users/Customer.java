package model.users;

public class Customer extends User {

    private double moneyAvailable;
    private boolean hasMembership;

    public double getMoneyAvailable() {
        return moneyAvailable;
    }

    public void setMoneyAvailable(double moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }

    public boolean hasMembership() {
        return hasMembership;
    }

    public void setMembership(boolean hasMembership) {
        this.hasMembership = hasMembership;
    }

    public void purchaseTicket() {
    }
}
