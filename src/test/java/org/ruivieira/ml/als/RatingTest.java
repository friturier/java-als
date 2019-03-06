package org.ruivieira.ml.als;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RatingTest {

    @Test
    public void getUser() {
        final Rating rating = new Rating(1, 2, 5.0);
        Assert.assertEquals(rating.getUser(), 1);
    }

    @Test
    public void getItem() {
        final Rating rating = new Rating(1, 2, 5.0);
        Assert.assertEquals(rating.getItem(), 2);

    }

    @Test
    public void getRating() {
        final Rating rating = new Rating(1, 2, 5.0);
        Assert.assertEquals(rating.getRating(), 5.0, 1e-10);

    }
}