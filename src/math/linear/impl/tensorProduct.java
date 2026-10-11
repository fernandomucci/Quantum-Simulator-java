package math.linear.impl;
import math.core.ComplexNumber;

public class tensorProduct
{
    public ComplexMatrices tensorProductCalc(ComplexMatrices m1, ComplexMatrices m2)
    {
        if(m1 == null || m2 == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");
        }

        int rowM1 = m1.getRows();
        int colM1 = m1.getCols();

        int rowM2 = m2.getRows();
        int colM2 = m2.getCols();

        ComplexMatrices mTensor = new ComplexMatrices((rowM1 * rowM2), (colM1 * colM2));

        //0(n^4) melhorar isso depois 
        for(int i = 0; i < rowM1; i++)
        {
            for(int j = 0; j < colM1; j++)
            {
                ComplexNumber currentM1 = m1.getElement(i, j);

                int offsetRow = i * rowM2;
                int offsetCol = j * colM2;

                for(int k = 0; k < rowM2; k++)
                {
                    for(int l = 0; l < colM2; l++)
                    {
                        ComplexNumber currentM2 = m2.getElement(k, l);
                    
                        ComplexNumber result = currentM1.multiply(currentM2);
                        
                        mTensor.setElement(offsetRow + k, offsetCol + l, result);
                    }
                }
            }
        }

        return mTensor;
    }

}
