package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SparseRealMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ALSUtilsTest {

    private final List<Rating> ratings = new ArrayList<>();


    @Before
    public void setUp() throws Exception {

        ratings.add(new Rating(1, 2, 5.0));
        ratings.add(new Rating(10, 20, 3.4));

    }

    @Test
    public void toMatrix() {
        final SparseRealMatrix R = ALSUtils.toMatrix(ratings);
        Assert.assertEquals(R.getColumnDimension(), 20);
        Assert.assertEquals(R.getRowDimension(), 10);

    }

    @Test
    public void maxUser() {
        Assert.assertEquals(ALSUtils.maxUser(ratings), 10);
    }

    @Test
    public void maxItem() {
        Assert.assertEquals(ALSUtils.maxItem(ratings), 20);
    }

    @Test
    public void approximate() {
        final LatentFactors factors = LatentFactors.create(ALSUtils.maxUser(ratings), ALSUtils.maxItem(ratings), 4);
        final RealMatrix nR = ALSUtils.approximate(factors);
        final RealMatrix R = ALSUtils.toMatrix(ratings);
        Assert.assertEquals(nR.getColumnDimension(), R.getColumnDimension());
        Assert.assertEquals(nR.getRowDimension(), R.getRowDimension());
    }
}