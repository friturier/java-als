package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseRealMatrix;

import java.util.Comparator;
import java.util.List;

public class ALSUtils {

    public static SparseRealMatrix toMatrix(List<Rating> ratings) {

        final int max_user = maxUser(ratings);
        final int max_item = maxItem(ratings);

        final SparseRealMatrix ratingsMatrix = new OpenMapRealMatrix(max_user, max_item);
        for (Rating rating : ratings) {
            ratingsMatrix.setEntry(rating.getUser() - 1, rating.getItem() - 1, rating.getRating());
        }
        return ratingsMatrix;
    }

    public static int maxUser(List<Rating> ratings) {
        return ratings.stream().max(Comparator.comparing(Rating::getUser)).get().getUser();
    }

    public static int maxItem(List<Rating> ratings) {
        return ratings.stream().max(Comparator.comparing(Rating::getItem)).get().getItem();
    }

}
