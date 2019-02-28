package org.ruivieira.ml.als;

public abstract class AbstractALS {

    protected final int rank;

    public AbstractALS(int rank) {
        this.rank = rank;
    }

    public double predict(LatentFactors factors, int i, int j) {
        return factors.getUsers().getRowVector(i).dotProduct(factors.getItems().getColumnVector(j));
    }

}
