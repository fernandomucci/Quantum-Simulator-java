package ui.input;

import java.util.Scanner;
import math.core.ComplexNumber;
import math.linear.impl.ComplexMatrices;
import math.linear.impl.ComplexVector;

/**
 * Reads and validates data typed by the user: integers, probabilities,
 * vectors and matrices. Every method keeps asking until the input is valid.
 */
public class InputReader
{
    /**
     * Repeatedly prompts until the user enters a non-negative integer.
     */
    public static int readNonNegativeInt(Scanner sc, String prompt)
    {
        int value = 0;
        while (true)
        {
            try
            {
                System.out.print(prompt);
                value = Integer.parseInt(sc.nextLine().trim());
                if (value >= 0) break;
                System.out.println("Value cannot be negative.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }

    /**
     * Repeatedly prompts until the user enters a positive integer (> 0).
     */
    public static int readPositiveInt(Scanner sc, String prompt)
    {
        int value;
        while (true)
        {
            try
            {
                System.out.print(prompt);
                value = Integer.parseInt(sc.nextLine().trim());
                if (value > 0) break;
                System.out.println("Value must be greater than zero.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }

    /**
     * Repeatedly prompts until the user enters a valid probability
     * (a real number between 0 and 1, inclusive). Accepts either a
     * decimal (e.g. "0.5") or a fraction (e.g. "1/3") — fractions are
     * converted to their decimal value immediately; the result is
     * always displayed as a decimal afterward.
     */
    public static double readProbability(Scanner sc, String prompt)
    {
        double value;
        while (true)
        {
            try
            {
                System.out.print(prompt);
                String input = sc.nextLine().trim();

                if (input.contains("/"))
                {
                    String[] parts = input.split("/");
                    if (parts.length != 2)
                    {
                        throw new NumberFormatException("Invalid fraction format.");
                    }

                    double numerator = Double.parseDouble(parts[0].trim());
                    double denominator = Double.parseDouble(parts[1].trim());

                    if (denominator == 0)
                    {
                        throw new NumberFormatException("Denominator cannot be zero.");
                    }

                    value = numerator / denominator;
                }
                else
                {
                    value = Double.parseDouble(input);
                }

                if (value >= 0.0 && value <= 1.0)
                {
                    break;
                }

                System.out.println("Value must be between 0 and 1 (it's a probability).");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a decimal (e.g. 0.5) or a fraction (e.g. 1/3).");
            }
        }
        return value;
    }

    /**
     * Reads a square matrix whose entries are probabilities (0 to 1),
     * used as the transition matrix of a probabilistic system. Each
     * entry is validated individually as it's typed in; whether the
     * matrix as a whole is stochastic (columns summing to 1) is checked
     * afterward by the caller via Probabilistic.isStochastic.
     */
    public static ComplexMatrices readProbabilityMatrix(Scanner sc, String name)
    {
        int size = 0;
        while (true)
        {
            try
            {
                System.out.print("\nEnter the size (n) of the square " + name + " (n x n): ");
                size = Integer.parseInt(sc.nextLine().trim());
                if (size > 0) break;
                System.out.println("Size must be greater than zero.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        ComplexMatrices mat = new ComplexMatrices(size, size);

        System.out.println("Entering probabilities for " + name + "...");
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                double value = readProbability(sc, "Element [" + i + "][" + j + "] (0 to 1): ");
                mat.setElement(i, j, new ComplexNumber(value, 0));
            }
        }
        return mat;
    }

    /**
     * Reads a column vector (expectedSize x 1) whose entries are
     * probabilities, used as the state of a probabilistic system.
     */
    public static ComplexMatrices readProbabilityVector(Scanner sc, String name, int expectedSize)
    {
        ComplexMatrices mat = new ComplexMatrices(expectedSize, 1);

        System.out.println("Entering probabilities for " + name + "...");
        for (int i = 0; i < expectedSize; i++)
        {
            double value = readProbability(sc, "Element [" + i + "] (0 to 1): ");
            mat.setElement(i, 0, new ComplexNumber(value, 0));
        }
        return mat;
    }

    /**
     * Reads a (rows x cols) matrix whose entries are probabilities (0 to 1),
     * used for the slit-to-target probability matrix, which is generally
     * NOT square (number of slits usually differs from number of targets).
     */
    public static ComplexMatrices readRectangularProbabilityMatrix(Scanner sc, int rows, int cols)
    {
        ComplexMatrices mat = new ComplexMatrices(rows, cols);

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                double value = readProbability(sc, "Target [" + i + "] from Slit [" + j + "] (0 to 1): ");
                mat.setElement(i, j, new ComplexNumber(value, 0));
            }
        }
        return mat;
    }

    /**
     * Reads a (rows x cols) matrix of complex amplitudes, used for the
     * slit-to-target matrix of the quantum multi-slit experiment. Each
     * entry is asked again until it has a valid complex format.
     */
    public static ComplexMatrices readRectangularComplexMatrix(Scanner sc, int rows, int cols)
    {
        ComplexMatrices mat = new ComplexMatrices(rows, cols);

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                while (true)
                {
                    try
                    {
                        System.out.print("Target [" + i + "] from Slit [" + j + "] (e.g., 0.5+0.5i): ");
                        String input = sc.nextLine().replace(" ", "");
                        mat.setElement(i, j, ComplexParser.parseComplex(input));
                        break;
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println("Invalid format. Try again.");
                    }
                }
            }
        }
        return mat;
    }

    public static ComplexVector readVector(Scanner sc, String name) 
    {
        int size = 0;
        while (true) 
        {
            try 
            {
                System.out.print("\nEnter the size of " + name + ": ");
                size = Integer.parseInt(sc.nextLine().trim());
                if (size > 0) break;
                System.out.println("Size must be greater than zero.");
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        
        ComplexVector vec = new ComplexVector(size);

        System.out.println("Entering elements for " + name + "...");
        for (int i = 0; i < size; i++) 
        {
            while (true) 
            {
                try 
                {
                    System.out.print("Element [" + i + "] (e.g., 2+3i): ");
                    String input = sc.nextLine().replace(" ", "");
                    vec.setElement(i, ComplexParser.parseComplex(input));
                    break;
                } 
                catch (IllegalArgumentException e) 
                {
                    System.out.println("Invalid format. Try again.");
                }
            }
        }
        return vec;
    }

    /**
     * Helper method to read a vector structure directly into a column Matrix (Nx1)
     * to keep universal compatibility with the InnerProduct utility engine.
     */
    public static ComplexMatrices readVectorAsMatrix(Scanner sc, String name)
    {
        int size = 0;
        while (true) 
        {
            try 
            {
                System.out.print("\nEnter the size of " + name + ": ");
                size = Integer.parseInt(sc.nextLine().trim());
                if (size > 0) break;
                System.out.println("Size must be greater than zero.");
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        
        ComplexMatrices mat = new ComplexMatrices(size, 1);

        System.out.println("Entering elements for " + name + "...");
        for (int i = 0; i < size; i++) 
        {
            while (true) 
            {
                try 
                {
                    System.out.print("Element [" + i + "] (e.g., 2+3i): ");
                    String input = sc.nextLine().replace(" ", "");
                    mat.setElement(i, 0, ComplexParser.parseComplex(input));
                    break;
                } 
                catch (IllegalArgumentException e) 
                {
                    System.out.println("Invalid format. Try again.");
                }
            }
        }
        return mat;
    }

    public static ComplexMatrices readMatrix(Scanner sc, String name) 
    {
        int rows = 0, cols = 0;
        while (true) 
        {
            try 
            {
                System.out.print("\nEnter the number of ROWS for " + name + ": ");
                rows = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Enter the number of COLUMNS for " + name + ": ");
                cols = Integer.parseInt(sc.nextLine().trim());
                
                if (rows > 0 && cols > 0) break;
                System.out.println("Rows and columns must be greater than zero.");
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid input. Please enter valid integers.");
            }
        }
        
        ComplexMatrices mat = new ComplexMatrices(rows, cols);

        System.out.println("Entering elements for " + name + "...");
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < cols; j++) 
            {
                while (true) 
                {
                    try 
                    {
                        System.out.print("Element [" + i + "][" + j + "] (e.g., 2+3i): ");
                        String input = sc.nextLine().replace(" ", "");
                        mat.setElement(i, j, ComplexParser.parseComplex(input));
                        break;
                    } 
                    catch (IllegalArgumentException e) 
                    {
                        System.out.println("Invalid format. Try again.");
                    }
                }
            }
        }
        return mat;
    }

    /**
     * Reads a square matrix (n x n). Used for operations that only make
     * sense for square matrices, like Trace, Hermitian check, Unitary
     * check, and the Deterministic System's transition matrix, so the
     * user isn't asked to fill in elements only to fail validation
     * afterward.
     */
    public static ComplexMatrices readSquareMatrix(Scanner sc, String name)
    {
        int size = 0;
        while (true)
        {
            try
            {
                System.out.print("\nEnter the size (n) of the square " + name + " (n x n): ");
                size = Integer.parseInt(sc.nextLine().trim());
                if (size > 0) break;
                System.out.println("Size must be greater than zero.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        ComplexMatrices mat = new ComplexMatrices(size, size);

        System.out.println("Entering elements for " + name + "...");
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                while (true)
                {
                    try
                    {
                        System.out.print("Element [" + i + "][" + j + "] (e.g., 2+3i): ");
                        String input = sc.nextLine().replace(" ", "");
                        mat.setElement(i, j, ComplexParser.parseComplex(input));
                        break;
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println("Invalid format. Try again.");
                    }
                }
            }
        }
        return mat;
    }
}