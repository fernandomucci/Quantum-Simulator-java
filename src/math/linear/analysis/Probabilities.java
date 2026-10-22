package math.linear.analysis;

import math.linear.impl.ComplexMatrices;

public class Probabilities
{
     // |amplitude|^2 of each entry of a column vector
    public double[] fromState(ComplexMatrices state)
    {
        if (state == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");
        }

        if (state.getCols() != 1)
        {
            throw new IllegalArgumentException("the state must be a column vector.");
        }

        double[] result = new double[state.getRows()];

        for (int i = 0; i < state.getRows(); i++)
        {
            double re = state.getElement(i, 0).getReal();
            double im = state.getElement(i, 0).getImag();
            result[i] = re * re + im * im;
        }

        return result;
    }

    public double total(ComplexMatrices state)
    {
        double sum = 0.0;

        for (double p : fromState(state))
        {
            sum += p;
        }

        return sum;
    }
}
