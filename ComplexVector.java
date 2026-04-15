public class ComplexVector
{
    private ComplexNumber[] elements;

    public ComplexVector(int size)
    {
        this.elements = new ComplexNumber[size];
        
        for(int i = 0; i < size; i++)
        {
            this.elements[i] = new ComplexNumber(0, 0);
        }
    }
        
    // Method to set a complex number at a specific position
    public void setElement(int index, ComplexNumber number)
    {
        if (index < 0 || index >= this.elements.length)
        {
            throw new IllegalArgumentException("Index out of bounds for Vector.");
        }
        this.elements[index] = number;
    }

    // Method to get a number from a specific position
    public ComplexNumber getElement(int index)
    {
        if (index < 0 || index >= this.elements.length)
        {
            throw new IllegalArgumentException("Index out of bounds for Vector.");
        }
        return this.elements[index];
    }



    //Method to add two complex vectors
    public ComplexVector add(ComplexVector other)
    {
       //Math rule: Vectors must be the same size to be added
        if (this.elements.length != other.elements.length)
        {
            throw new IllegalArgumentException("Vectors must have the same size to be added.");
        }

        //Create the new vector to store the result
        int size = this.elements.length;
        ComplexVector result = new ComplexVector(size);

        //Loop through each position and add the complex numbers
        for (int i = 0; i < size; i++)
        {
            // Get the numbers from both vectors
            ComplexNumber num1 = this.elements[i];
            ComplexNumber num2 = other.getElement(i);
            
            //already created in ComplexNumber
            ComplexNumber sum = num1.add(num2);
            
            // Put the result in the new vector
            result.setElement(i, sum);
        }

        return result;
    }

    // Method to calculate the additive inverse of the vector
    public ComplexVector inverse()
    {
        int size = this.elements.length;
        
        //Create a new vector to store the result
        ComplexVector result = new ComplexVector(size);
        
        //Create -1 scalar in Complex Number format (-1 + 0i)
        ComplexNumber minusOne = new ComplexNumber(-1, 0);

        for (int i = 0; i < size; i++)
        {
            // Get the current number
            ComplexNumber currentNumber = this.elements[i];
            
            // Multiply it by -1
            ComplexNumber invertedNumber = currentNumber.multiply(minusOne);
            
            // Store it in the same slot of the new vector
            result.setElement(i, invertedNumber);
        }

        return result;
    }



    public ComplexVector scalarMultiply(ComplexNumber scalarNumber)
    {
        int size = this.elements.length;

        //create new vector to store the result
        ComplexVector result = new ComplexVector(size);

        for(int i = 0; i < size; i++)
        {
            //Get the current number
            ComplexNumber currentNumber = this.elements[i];

            //multiply by scalarNumber
            ComplexNumber multiply = currentNumber.multiply(scalarNumber);

            //store it in the same slot of new vector
            result.setElement(i, multiply);
        }

        return result;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < this.elements.length; i++)
        {
            sb.append("  ").append(this.elements[i].toString()).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}