package math.linear.impl;
import math.core.ComplexNumber;
import math.linear.interfaces.IInnerProduct;

public class InnerProduct implements IInnerProduct
{
    @Override
    public ComplexNumber innerProductValue(ComplexMatrices m1,ComplexMatrices m2)
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
    @Override
    public ComplexNumber normValue(ComplexMatrices m1)
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

    @Override
    public ComplexNumber distance(ComplexMatrices m1, ComplexMatrices m2)
    {
        if(m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");   
        }

        ComplexMatrices matrixSub = m1.subtract(m2);

        return normValue(matrixSub);

    }

    @Override
    public boolean orthogonal(ComplexMatrices m1, ComplexMatrices m2)
    {
        if (m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("The matrix cannot be null.");
        }
 
        boolean isOrthogonal = false;
 
        double tolerance = 1e-9;
        ComplexNumber innerProduct = innerProductValue(m1, m2);
 
        double realPart = Math.abs(innerProduct.getReal());
        double imagPart = Math.abs(innerProduct.getImag());
 
        if (realPart < tolerance && imagPart < tolerance)
        {
            isOrthogonal = true;
        }
 
        return isOrthogonal;
    }
 
    @Override
    public boolean orthonormal(ComplexMatrices m1, ComplexMatrices m2)
    {
        if (m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("The matrix cannot be null.");
        }
 
        boolean isOrthonormal = false;
 
        boolean isOrthogonal = orthogonal(m1, m2);
 
        double normM1 = normValue(m1).getReal();
        double normM2 = normValue(m2).getReal();
 
        double tolerance = 1e-9;
 
        boolean m1IsUnitary = Math.abs(normM1 - 1.0) < tolerance;
        boolean m2IsUnitary = Math.abs(normM2 - 1.0) < tolerance;
 
        if (isOrthogonal && m1IsUnitary && m2IsUnitary)
        {
            isOrthonormal = true;
        }
 
        return isOrthonormal;
    }
 
    @Override
    public double angle(ComplexMatrices m1, ComplexMatrices m2)
    {
        if (m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("The matrix cannot be null.");
        }
 
        double normM1 = normValue(m1).getReal();
 
        double normM2 = normValue(m2).getReal();
 
        if (normM1 == 0.0 || normM2 == 0.0)
        {
            throw new ArithmeticException("Cannot compute the angle involving a zero vector.");
        }
 
        double innerProductMagnitude = innerProductValue(m1, m2).modulus();
 
        double cosTheta = innerProductMagnitude / (normM1 * normM2);
 
        if (cosTheta > 1.0) 
        {
            cosTheta = 1.0;
        }
        if (cosTheta < -1.0) 
        {
            cosTheta = -1.0;
        }
 
        double angleInRadians = Math.acos(cosTheta);
 
        double angleInDegrees = Math.toDegrees(angleInRadians);
 
        return angleInDegrees;
        
        
        
    }

}
