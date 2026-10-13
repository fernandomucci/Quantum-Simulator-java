package math.linear.impl;

public class Probabilistic
{
     public ComplexMatrices mByStateProb(ComplexMatrices M, ComplexMatrices stateX)
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

        ComplexMatrices stateY = mByStateProb(mStep, stateX);

        return stateY;
    }

    public boolean isStochastic(ComplexMatrices m1)
    {
        if (m1 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");
        }

        int row = m1.getRows();
        int col = m1.getCols();
        double tolerance = 1e-9;
        boolean stochastic = true;

        for (int i = 0; i < col; i++)
        {
            double columnSum = 0.0;

            for (int j = 0; j < row; j++)
            {
                double value = m1.getElement(j, i).getReal();
                columnSum += value;
            }

            if (Math.abs(columnSum - 1.0) > tolerance)
            {
                stochastic = false;
            }
        }

        return stochastic;
    }
}



