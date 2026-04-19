
public class ComplexMatrices
{
    private ComplexNumber[][] elements;
    
    
    public ComplexMatrices(int row, int col)
    {
        this.elements = new ComplexNumber[row][col];
        
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                this.elements[i][j] = new ComplexNumber(0, 0);
            }
           
        }
    }

    // Method to set a complex number at a specific position
    public void setElement(int row, int col, ComplexNumber number)
    {
        if (row < 0 || row >= this.elements.length || col < 0 || col >= this.elements[0].length)
        {
            throw new IllegalArgumentException("Index out of bounds for Matrix.");
        }
        this.elements[row][col] = number;
    }

    // Method to get a number from a specific position
    public ComplexNumber getElement(int row, int col)
    {
        if (row < 0 || row >= this.elements.length || col < 0 || col >= this.elements[0].length)
        {
            throw new IllegalArgumentException("Index out of bounds for Matrix.");
        }
        return this.elements[row][col];
    }

    //Method to add two matrices
    public ComplexMatrices add(ComplexMatrices other)
    {
        // Math rule: Matrices must be the same size to be added (Rows AND Columns)
        if(this.elements.length != other.elements.length || this.elements[0].length != other.elements[0].length)
        {
             throw new IllegalArgumentException("Matrices must have the same size (rows and columns) to be added.");
        }

        //Create a new Matrice to store the result
        int row = this.elements.length;
        int col = this.elements[0].length;
        ComplexMatrices result = new ComplexMatrices(row, col);

        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                //get the number from both matrice
                ComplexNumber num1 = this.elements[i][j];
                ComplexNumber num2 = other.getElement(i, j);

                //already created in ComplexNumber
                ComplexNumber sum = num1.add(num2);

                //put the result in the new matrice
                result.setElement(i, j, sum);
            }
        }

        return result;
    }

    //Method to calculate the additive inverse of the vector
    public ComplexMatrices inverse()
    {
        //creat a new matrice to store the result
        int row = this.elements.length;
        int col = this.elements[0].length;
        ComplexMatrices result = new ComplexMatrices(row, col);

        //create -1 scalar in Complex Number format (-1 + 0i)
        ComplexNumber minusOne = new ComplexNumber(-1, 0);

        for(int i = 0; i < row; i ++)
        {
            for(int j = 0; j < col; j++)
            {
                //get de current Number
                ComplexNumber currentNumber = this.elements[i][j];

                //Multiply it by -1
                ComplexNumber invertedNumber = currentNumber.multiply(minusOne);

                //store the result in the new matrice
                result.setElement(i, j, invertedNumber);
            }
        }

        return result;

    }

    //Method to multiply by scalar
    public ComplexMatrices scalarMultiply(ComplexNumber scalaNumber)
    {
        //creat a new matrice to store the result
        int row = this.elements.length;
        int col = this.elements[0].length;
        ComplexMatrices result = new ComplexMatrices(row, col);

        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                //get the current number
                ComplexNumber currentNumber = this.elements[i][j];

                //multiply by scalar number
                ComplexNumber multiply = currentNumber.multiply(scalaNumber);

                //store the result in the matrice
                result.setElement(i, j, multiply);
            }
        }

        return result;
    }

   
    public ComplexMatrices matrixMultiplication(ComplexMatrices other)
    {
        // Retrieve dimensions for clarity and bound checking
        int thisRows = this.elements.length;
        int thisCols = this.elements[0].length;
        int otherRows = other.elements.length;
        int otherCols = other.elements[0].length;

        // Mathematical requirement: Columns of Matrix A must equal Rows of Matrix B
        if (thisCols != otherRows)
        {
            throw new IllegalArgumentException(
                "Incompatible dimensions: Number of columns in the first matrix (" + thisCols + 
                ") must match the number of rows in the second matrix (" + otherRows + ")."
            );
        }

        // Initialize the result matrix with dimensions (Rows of A) x (Columns of B)
        ComplexMatrices result = new ComplexMatrices(thisRows, otherCols);

        // Optimized matrix multiplication (i-k-j order) for sequential memory access
        for (int i = 0; i < thisRows; i++)
        {
            for (int k = 0; k < thisCols; k++)
            {
                // Cache a single element from Matrix A (this matrix)
                ComplexNumber numA = this.elements[i][k];
                
                // Iterate through the columns of Matrix B sequentially (j-loop)
                for (int j = 0; j < otherCols; j++)
                {
                    // Retrieve element from Matrix B
                    ComplexNumber numB = other.getElement(k, j);
                    
                    // Multiply the cached element of A with the current element of B
                    ComplexNumber product = numA.multiply(numB);
                    
                    // Accumulate the product into the partial sum of the result matrix
                    ComplexNumber currentResult = result.getElement(i, j);
                    result.setElement(i, j, currentResult.add(product));
                }
            }
        }

        return result;
    }


    //FORMAT
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        int row = this.elements.length;
        int col = this.elements[0].length;
        
            for (int i = 0; i < row; i++)
            {
                sb.append("  "); 
                for(int j = 0; j < col; j++)
                {
                    // O "%-18s" garante que cada coluna ocupe 18 caracteres, alinhando a matriz
                    sb.append(String.format("%-18s", this.elements[i][j].toString()));
                }
                sb.append("\n"); 
            }
        
        sb.append("]");
        return sb.toString();
    }

}
