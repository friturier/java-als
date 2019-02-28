package org.ruivieira.ml.als;

public class Rating {

    private final int user;
    private final int item;
    private final double rating;

    public Rating(int user, int item, double rating) {

        this.user = user;
        this.item = item;
        this.rating = rating;
    }

    public int getUser() {
        return user;
    }

    public int getItem() {
        return item;
    }

    public double getRating() {
        return rating;
    }
}
