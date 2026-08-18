package math.linear.impl;
import math.core.ComplexNumber;

public class HermitianMatrices
{
    public boolean isHermitian(ComplexMatrices m1)
    {
        if (m1 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");
        }

        int row = m1.getRows();
        int col = m1.getCols();

        if(row != col)
        {
            return false;
        }

        // Small tolerance to safely compare doubles (avoids floating point errors)
        double tolerance = 1e-9;

        for (int i = 0; i < row; i++)
        {
            for (int j = i; j < col; j++)
            {
                ComplexNumber conjugate = m1.getElement(j, i).conjugate();
                ComplexNumber current = m1.getElement(i, j);
        
                if (Math.abs(current.getReal() - conjugate.getReal()) > tolerance || Math.abs(current.getImag() - conjugate.getImag()) > tolerance)
                {
                    return false;
                }
            }

        }

        return true;
    }
}
