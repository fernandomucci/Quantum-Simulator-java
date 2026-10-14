package math.systems.quantum;

import math.linear.impl.ComplexMatrices;

public class QuantumSystem
{
     public ComplexMatrices mByStateQuantum(ComplexMatrices M, ComplexMatrices stateX)
    {
        ComplexMatrices stateY = M.matrixMultiplication(stateX);

        return stateY;
    }

    public ComplexMatrices multipleStepDynamics(ComplexMatrices M, int timeStep, ComplexMatrices stateX)
    {
         if (timeStep < 0)
        {
            throw new IllegalArgumentException("Time step cannot be negative.");
        }

        ComplexMatrices mStep = ComplexMatrices.createIdentity(M.getRows()); 

        for(int i = 0; i < timeStep; i++)
        {
            mStep = mStep.matrixMultiplication(M);
        }

        ComplexMatrices stateY = mByStateQuantum(mStep, stateX);

        return stateY;
    }

    public boolean isNormalized(ComplexMatrices state)
    {
        if (state == null)
        {
            throw new IllegalArgumentException("the state cannot be null.");
        }

        double tolerance = 1e-9;
        double sum = 0.0;

        for (int i = 0; i < state.getRows(); i++)
        {
            double mod = state.getElement(i, 0).modulus();
            sum += mod * mod;
        }

        return Math.abs(sum - 1.0) <= tolerance;
    }
}
