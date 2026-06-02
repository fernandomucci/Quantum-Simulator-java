package math.linear.impl;

import math.core.ComplexNumber;

public class InnerProduct
{
    public static ComplexNumber innerPeodutcValue(ComplexMatrices m1,ComplexMatrices m2)
    {
        if (m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("the matrices cannot be null.");
        }

        //Tr(A† * B)
        ComplexNumber result = m1.dagger().matrixMultiplication(m2).trace();

        return result;
    }
}
