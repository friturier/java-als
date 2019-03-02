package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.Comparator;
import java.util.List;

public class SGDALS extends AbstractALS {


    private final double lambda_user;
    private final double lambda_item;
    private double learning_rate;
    private final double rate;

    public SGDALS(int rank) {
        this(rank, 0.01, 0.01, 0.01, 1.0);
    }

    public SGDALS(int rank, double lambda_user, double lambda_item, double learning_rate, double rate) {
        super(rank);
        this.lambda_user = lambda_user;
        this.lambda_item = lambda_item;
        this.learning_rate = learning_rate;
        this.rate = rate;
    }


    public LatentFactors run(LatentFactors factors, List<Rating> ratings) {


        this.learning_rate = this.learning_rate / this.rate;

        final int maxUserId = ALSUtils.maxUser(ratings);
        final int maxItemId = ALSUtils.maxItem(ratings);

        RandomGenerator rng = new MersenneTwister();

        if (factors.getUsers().getRowDimension() < maxUserId) {
            // grow users
            RealMatrix newUserFactors = MatrixUtils.createRealMatrix(maxUserId, this.rank);
            for (int row = 0 ; row < factors.getUsers().getRowDimension() ; row++) {
                newUserFactors.setRowVector(row, factors.getUsers().getRowVector(row));
            }
            for (int row = factors.getUsers().getRowDimension(); row < maxUserId ; row++) {
                for (int col = 0 ; col < this.rank ; col++) {
                    newUserFactors.setEntry(row, col, rng.nextDouble());
                }
            }
            factors = new LatentFactors(newUserFactors, factors.getItems());
        }

        if (factors.getItems().getColumnDimension() < maxItemId) {
            // grow items
            RealMatrix newItemFactors = MatrixUtils.createRealMatrix(this.rank, maxItemId);
            for (int col = 0 ; col < factors.getItems().getColumnDimension() ; col++) {
                newItemFactors.setColumnVector(col, factors.getItems().getColumnVector(col));
            }
            for (int col = factors.getItems().getColumnDimension() ; col < maxUserId ; col++) {
                for (int row = 0 ; row < this.rank ; row++) {
                    newItemFactors.setEntry(row, col, rng.nextDouble());
                }
            }
            factors = new LatentFactors(factors.getUsers(), newItemFactors);

        }

            for (Rating rating : ratings) {
                RealVector userFactors = factors.getUsers().getRowVector(rating.getUser() - 1);
                RealVector itemFactors = factors.getItems().getColumnVector(rating.getItem() - 1);

                final double error = rating.getRating() - userFactors.dotProduct(itemFactors);

                factors.getItems().setColumnVector(rating.getItem() - 1, itemFactors.add(userFactors.mapMultiply(error).subtract(itemFactors.mapMultiply(this.lambda_item)).mapMultiply(this.learning_rate)));
                factors.getUsers().setRowVector(rating.getUser() - 1, userFactors.add(itemFactors.mapMultiply(error).subtract(userFactors.mapMultiply(this.lambda_user)).mapMultiply(this.learning_rate)));
            }

        return factors;
    }

}
