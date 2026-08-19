package math.linear.impl;

import math.core.ComplexNumber;

public class InnerProduct
{
    public static ComplexNumber innerProductValue(ComplexMatrices m1,ComplexMatrices m2)
    {
        if (m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");
        }

         // Tr(A† * B) == sum of conj(A[i][j]) * B[i][j] over every position.
        // This avoids computing the full matrix product just to throw away
        // everything except the diagonal.
        ComplexNumber result = new ComplexNumber(0, 0);

        for (int i = 0; i < m1.getRows(); i++)
        {
            for (int j = 0; j < m1.getCols(); j++)
            {
                ComplexNumber a = m1.getElement(i, j).conjugate();
                ComplexNumber b = m2.getElement(i, j);
                result = result.add(a.multiply(b));
            }
        }


        return result;
    }

    public static ComplexNumber normValue(ComplexMatrices m1)
    {
        if(m1 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");   
        }

        //< v , v >
        ComplexNumber innerProductByItself = innerProductValue(m1, m1);

        //square < v , v >
        double normDouble = Math.sqrt(innerProductByItself.getReal());

        return new ComplexNumber(normDouble, 0);
       
    }

    public static ComplexNumber distance(ComplexMatrices m1, ComplexMatrices m2)
    {
        if(m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");   
        }

        ComplexMatrices matrixSub = m1.subtract(m2);

        return normValue(matrixSub);

    }

}
