package math.linear.impl;

import math.core.ComplexNumber;

public class InnerProduct
{
    public static ComplexNumber innerPeodutcValue(ComplexMatrices m1,ComplexMatrices m2)
    {
        if (m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");
        }

        //Tr(A† * B)
        ComplexNumber result = m1.dagger().matrixMultiplication(m2).trace();

        return result;
    }

    public static ComplexNumber normValue(ComplexMatrices m1)
    {
        if(m1 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");   
        }

        //< v , v >
        ComplexNumber innerProductByItself = innerPeodutcValue(m1, m1);

        //square < v , v >
        double normDouble = Math.sqrt(innerProductByItself.getReal());

        return new ComplexNumber(normDouble, 0);
       
    }
}
