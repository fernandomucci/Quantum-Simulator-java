
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
