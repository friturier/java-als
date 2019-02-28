package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

public class LatentFactors {

    private final RealMatrix users;
    private final RealMatrix items;

    public LatentFactors(RealMatrix users, RealMatrix items) {
        this.users = users;
        this.items = items;
    }

    public RealMatrix getUsers() {
        return users;
    }

    public RealMatrix getItems() {
        return items;
    }

    public static LatentFactors create(int nUsers, int nItems, int rank) {
        final RealMatrix users = MatrixUtils.createRealMatrix(nUsers, rank);
        final RealMatrix items = MatrixUtils.createRealMatrix(nItems, rank);
        final RandomGenerator rng = new MersenneTwister();
        for (int row = 0 ; row < nUsers ; row++) {
            for (int k = 0 ; k < rank ; k++) {
                users.setEntry(row, k, rng.nextDouble());
            }
        }
        for (int row = 0 ; row < nItems ; row++) {
            for (int k = 0 ; k < rank ; k++) {
                items.setEntry(row, k, rng.nextDouble());
            }
        }
        return new LatentFactors(users, items.transpose());

    }
}
