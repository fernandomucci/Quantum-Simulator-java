package math.linear.impl;
import math.core.ComplexNumber;

public class HermitianUnitaryMatrices
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

    public boolean IsUnitary(ComplexMatrices m1)
    {
        if(m1 == null)
        {
            throw new IllegalArgumentException("the matrices cannot be null");
        }

        int col = m1.getCols();
        int row = m1.getRows();


        if(col != row)
        {
            return false;
        }

        ComplexMatrices product = m1.dagger().matrixMultiplication(m1);
        double tolerance = 1e-9;

        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                ComplexNumber element = product.getElement(i, j);
                double expectedReal = (i == j) ? 1.0 : 0.0;

                if (Math.abs(element.getReal() - expectedReal) > tolerance
                    || Math.abs(element.getImag()) > tolerance)
                {
                    return false;
                }
            }
        }

        

        return true;
    }
}
