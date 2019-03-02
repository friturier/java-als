package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseRealMatrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TrainValidationSet {

    public SparseRealMatrix getTraining() {
        return training;
    }

    public SparseRealMatrix getValidation() {
        return validation;
    }

    private final SparseRealMatrix training;
    private final SparseRealMatrix validation;

    public TrainValidationSet(List<Rating> ratings, double ratio) {

        final int maxUser = ALSUtils.maxUser(ratings);
        final int maxItem = ALSUtils.maxItem(ratings);

        training = new OpenMapRealMatrix(maxUser, maxItem);

        final int k = (int) Math.ceil((double) ratings.size() * ratio);

        Set<Integer> indices = new HashSet<>();
        while (indices.size() < k) {
            indices.add(ThreadLocalRandom.current().nextInt(0, ratings.size()));
        }

        Set<Integer> complement = new HashSet<>();
        for (int i = 0 ; i < ratings.size() ; i++) {
            complement.add(i);
        }

        for (Integer index : indices) {
            Rating rating = ratings.get(index);
            training.setEntry(rating.getUser() - 1, rating.getItem() - 1, rating.getRating());
        }

        validation = new OpenMapRealMatrix(maxUser, maxItem);

        complement.removeAll(indices);
        for (Integer index : complement) {
            Rating rating = ratings.get(index);
            validation.setEntry(rating.getUser() - 1, rating.getItem() - 1, rating.getRating());
        }
    }
}
