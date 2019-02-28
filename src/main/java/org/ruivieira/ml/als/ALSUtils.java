package org.ruivieira.ml.als;

import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseRealMatrix;

import java.util.List;

public class ALSUtils {

    public static SparseRealMatrix toMatrix(List<Rating> ratings) {
        int max_user = 0;
        int max_item = 0;
        for (Rating rating : ratings) {
            max_user = Math.max(max_user, rating.getUser());
            max_item = Math.max(max_item, rating.getItem());
        }

        final SparseRealMatrix ratingsMatrix = new OpenMapRealMatrix(max_user, max_item);
        for (Rating rating : ratings) {
            ratingsMatrix.setEntry(rating.getUser() - 1, rating.getItem() - 1, rating.getRating());
        }
        return ratingsMatrix;
    }
}
