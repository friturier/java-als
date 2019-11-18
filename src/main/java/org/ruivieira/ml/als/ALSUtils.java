package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SparseRealMatrix;

import java.util.Comparator;
import java.util.List;

public class ALSUtils {

    /**
     * Converts a list of {@link Rating} into a sparse ratings matrix ({@link org.apache.commons.math3.linear.SparseRealMatrix}).
     * @param ratings a {@link java.util.List} of {@link Rating}.
     * @return A sparse ratings matrix ({@link org.apache.commons.math3.linear.SparseRealMatrix}).
     */
    public static SparseRealMatrix toMatrix(List<Rating> ratings) {

        final int max_user = maxUser(ratings);
        final int max_item = maxItem(ratings);

        final SparseRealMatrix ratingsMatrix = new OpenMapRealMatrix(max_user, max_item);
        for (Rating rating : ratings) {
            ratingsMatrix.setEntry(rating.getUser() - 1, rating.getItem() - 1, rating.getRating());
        }
        return ratingsMatrix;
    }

    /**
     * Returns the highest user id from a {@link java.util.List} of {@link Rating}.
     * @param ratings A {@link java.util.List} of {@link Rating}.
     * @return The highest user id as an integer.
     */
    public static int maxUser(List<Rating> ratings) {
        return ratings.stream().max(Comparator.comparing(Rating::getUser)).get().getUser();
    }

    /**
     * Returns the highest product id from a {@link java.util.List} of {@link Rating}.
     * @param ratings A {@link java.util.List} of {@link Rating}.
     * @return The highest product id as an integer.
     */
    public static int maxItem(List<Rating> ratings) {
        return ratings.stream().max(Comparator.comparing(Rating::getItem)).get().getItem();
    }

    public static RealMatrix approximate(LatentFactors factors) {
        return factors.getUsers().multiply(factors.getItems());
    }

}
