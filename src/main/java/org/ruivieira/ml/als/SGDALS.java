package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.Comparator;
import java.util.List;

public class SGDALS {


    private final double lambda_user = 0.7;
    private final double lambda_item = 0.7;
    private double learning_rate = 0.01;
    private int rank;

    public SGDALS(int rank) {
        this.rank = rank;
    }


    public LatentFactors run(LatentFactors factors, List<Rating> ratings) {


        this.learning_rate = this.learning_rate / 1.3;

        final int userIds = ratings.stream().max(Comparator.comparing(Rating::getUser)).get().getUser();
        final int itemIds = ratings.stream().max(Comparator.comparing(Rating::getItem)).get().getItem();

        RandomGenerator rng = new MersenneTwister();

        if (factors.getUsers().getRowDimension() < userIds) {
            // grow users
            RealMatrix newUserFactors = MatrixUtils.createRealMatrix(userIds, this.rank);
            for (int row = 0 ; row < factors.getUsers().getRowDimension() ; row++) {
                newUserFactors.setRowVector(row, factors.getUsers().getRowVector(row));
            }
            for (int row = factors.getUsers().getRowDimension(); row < userIds ; row++) {
                for (int col = 0 ; col < this.rank ; col++) {
                    newUserFactors.setEntry(row, col, rng.nextDouble());
                }
            }
            factors = new LatentFactors(newUserFactors, factors.getItems());
        }

        if (factors.getItems().getColumnDimension() < itemIds) {
            // grow items
            RealMatrix newItemFactors = MatrixUtils.createRealMatrix(this.rank, itemIds);
            for (int col = 0 ; col < factors.getItems().getColumnDimension() ; col++) {
                newItemFactors.setColumnVector(col, factors.getItems().getColumnVector(col));
            }
            for (int col = factors.getItems().getColumnDimension() ; col < userIds ; col++) {
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
