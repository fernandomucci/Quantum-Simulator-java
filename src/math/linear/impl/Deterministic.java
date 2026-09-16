package math.linear.impl;

public class Deterministic
{
    public ComplexMatrices mByState(ComplexMatrices M, ComplexMatrices stateX)
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

        ComplexMatrices stateY = mByState(mStep, stateX);

        return stateY;
    }
}
