package ui;

import java.util.Scanner;
import math.core.*;
import math.linear.impl.*;
import math.linear.interfaces.IInnerProduct;

/**
 * Main driver class for the Complex Calculator.
 * Handles user interface, menu navigation, and robust input validation.
 * Enhanced to support advanced linear algebra and quantum computing operations.
 */
public class ComplexCalculator
{
  
    private static final IInnerProduct innerProduct = new InnerProduct();

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0)
        {
            System.out.println("\n=================================");
            System.out.println("       COMPLEX CALCULATOR        ");
            System.out.println("=================================");
            System.out.println("[1] Binary Option (Numbers)");
            System.out.println("[2] Unary Option (Numbers)");
            System.out.println("[3] Vector Operations");
            System.out.println("[4] Matrix Operations");
            System.out.println("[0] Exit");
            System.out.println("=================================");
            System.out.print("Choose an option: ");
            
            try
            {
                choice = Integer.parseInt(sc.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }

            switch (choice)
            {
                case 1:
                    binaryMenu(sc);
                    break;
                case 2:
                    unaryMenu(sc);
                    break;
                case 3:
                    vectorMenu(sc);
                    break;
                case 4:
                    matrixMenu(sc);
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
        sc.close();
    }

    public static void binaryMenu(Scanner sc)
    {
        System.out.println("\n========== BINARY MENU ==========");
        System.out.println("[1] Addition");
        System.out.println("[2] Subtraction");
        System.out.println("[3] Multiplication");
        System.out.println("[4] Division");
        System.out.println("[0] Back");
        System.out.println("=================================");
        System.out.print("Choose an option: ");

        int choice;
        try
        {
            choice = Integer.parseInt(sc.nextLine().trim());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid input! Returning to main menu.");
            return;
        }

        if (choice == 0) return;

        try 
        {
            System.out.println("Enter the first complex number (e.g., 2+3i or 3i+2):");
            ComplexNumber n1 = parseComplex(sc.nextLine().replace(" ", ""));
            
            System.out.println("Enter the second complex number:");
            ComplexNumber n2 = parseComplex(sc.nextLine().replace(" ", ""));

            System.out.println("\n--- RESULT ---");
            switch (choice)
            {
                case 1:
                    System.out.println(n1.add(n2));
                    break;
                case 2:
                    System.out.println(n1.subtract(n2));
                    break;
                case 3:
                    System.out.println(n1.multiply(n2));
                    break;
                case 4:
                    System.out.println(n1.divide(n2));
                    break;
                default:
                    System.out.println("Choose a valid menu option.");
            }
        } 
        catch (IllegalArgumentException | ArithmeticException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void unaryMenu(Scanner sc)
    {
        System.out.println("\n=========== UNARY MENU ===========");
        System.out.println("[1] Modulus");
        System.out.println("[2] Conjugate");
        System.out.println("[3] Polar Representation");
        System.out.println("[4] Cartesian Representation");
        System.out.println("[0] Back");
        System.out.println("==================================");
        System.out.print("Choose an option: ");

        int choice;
        try
        {
            choice = Integer.parseInt(sc.nextLine().trim());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid input! Returning to main menu.");
            return;
        }

        if (choice == 0) return;

        ComplexNumber n1;
        try 
        {
            if (choice == 4)
            {
                System.out.println("Enter the number in polar representation (e.g., 3, pi/3):");
                n1 = parsePolar(sc.nextLine().replace(" ", "")); 
            }
            else
            {
                System.out.println("Enter the complex number (e.g., 2+3i or 3i+2):");
                n1 = parseComplex(sc.nextLine().replace(" ", ""));
            }

            System.out.println("\n--- RESULT ---");
            switch (choice)
            {
                case 1:
                    double mod = n1.modulus();
                    if (mod == (long) mod)
                    {
                        System.out.println("Modulus: " + (long) mod);
                    }
                    else
                    {
                        System.out.printf("Modulus: %.2f%n", mod);
                    }
                    break;
                case 2:
                    System.out.println(n1.conjugate());
                    break;
                case 3:
                    double[] polar = n1.toPolar();
                    System.out.println("Polar Form:");
                    System.out.println("r = " + formatRoot(polar[0]));
                    System.out.println("Angle (\u03b8) = " + String.format("%.2f", Math.toDegrees(polar[1])) + " degrees");
                    break;
                case 4:
                    System.out.println("Cartesian Form:");
                    System.out.println(n1);
                    break;
                default:
                    System.out.println("Choose a valid menu option.");
            }
        } 
        catch (IllegalArgumentException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static ComplexNumber parseComplex(String nc) throws IllegalArgumentException
    {
        nc = nc.toLowerCase();
        try 
        {
            String[] parts = nc.split("(?<![eE])(?=[+-])");
            double r = 0, i = 0;

            for (String p : parts)
            {
                if (p.trim().isEmpty()) continue;

                if (p.contains("i"))
                {
                    i += extractValue(p);
                }
                else
                {
                    r += Double.parseDouble(p);
                }
            }
            return new ComplexNumber(r, i);
        } 
        catch (NumberFormatException e) 
        {
            throw new IllegalArgumentException("Invalid complex number format.");
        }
    }

    private static double extractValue(String s) throws NumberFormatException
    {
        String cleaned = s.replace("i", "");
        if (cleaned.isEmpty() || cleaned.equals("+")) return 1.0;
        if (cleaned.equals("-")) return -1.0;
        return Double.parseDouble(cleaned);
    }

    private static ComplexNumber parsePolar(String nc) throws IllegalArgumentException
    {
        try 
        {
            String[] parts = nc.split(","); 
            if (parts.length < 2)
            {
                throw new IllegalArgumentException("Invalid polar format. Use a comma to separate r and theta.");
            }

            double r = Double.parseDouble(parts[0].trim());
            double angleRadians = readValueWithPi(parts[1]); 

            return ComplexNumber.fromPolar(r, angleRadians);
        } 
        catch (NumberFormatException e) 
        {
            throw new IllegalArgumentException("Invalid numeric format in polar representation.");
        }
    }

    private static double readValueWithPi(String nc) throws NumberFormatException
    {
        nc = nc.toLowerCase().trim();

        if (!nc.contains("pi"))
        {   
            return Double.parseDouble(nc);
        }

        double sign = 1.0;
        if (nc.startsWith("-"))
        {
            sign = -1.0;
            nc = nc.substring(1); 
        }
        else if (nc.startsWith("+"))
        { 
            nc = nc.substring(1);
        }

        double multiplier = 1.0;
        double divisor = 1.0;

        if (nc.contains("/"))
        {
            String[] parts = nc.split("/");
            String numerator = parts[0].replace("pi", "").trim();
            
            if (!numerator.isEmpty())
            {
                multiplier = Double.parseDouble(numerator);
            }
            divisor = Double.parseDouble(parts[1].trim());
        } 
        else
        {
            String numerator = nc.replace("pi", "").trim();
            if (!numerator.isEmpty())
            {
                multiplier = Double.parseDouble(numerator);
            }
        }

        return sign * multiplier * Math.PI / divisor;
    }

    public static String formatRoot(double value)
    {
        double square = value * value;
        long integer = (long) Math.round(square);

        if (Math.abs(square - integer) < 0.0001)
        {
            double root = Math.sqrt(square);
            if (Math.abs(root - Math.round(root)) < 0.0001)
            {
                return String.valueOf((long) Math.round(root));
            }
            return "\u221A" + integer;
        }

        return String.format("%.2f", value);
    }

    public static void vectorMenu(Scanner sc)
    {
        System.out.println("\n=========== VECTOR MENU ===========");
        System.out.println("[1] Vector Addition");
        System.out.println("[2] Additive Inverse");
        System.out.println("[3] Scalar Multiplication");
        System.out.println("[4] Vector Inner Product");
        System.out.println("[5] Vector Norm (Length)");
        System.out.println("[6] Check Orthogonality");
        System.out.println("[7] Check Orthonormality");
        System.out.println("[8] Angle Between Vectors");
        System.out.println("[0] Back");
        System.out.println("===================================");
        System.out.print("Choose an option: ");

        int choice;
        try
        {
            choice = Integer.parseInt(sc.nextLine().trim());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid input! Returning to main menu.");
            return;
        }

        if (choice == 0) return;

        try 
        {
            switch (choice)
            {
                case 1:
                    ComplexVector v1 = readVector(sc, "Vector 1");
                    ComplexVector v2 = readVector(sc, "Vector 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(v1.add(v2));
                    break;
                case 2:
                    ComplexVector v3 = readVector(sc, "Vector");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(v3.inverse());
                    break;
                case 3:
                    ComplexVector v4 = readVector(sc, "Vector");
                    System.out.println("Enter the scalar (Complex Number, e.g., 2 or 3i):");
                    ComplexNumber scalar = parseComplex(sc.nextLine().replace(" ", ""));
                    
                    System.out.println("\n--- RESULT ---");
                    System.out.println(v4.scalarMultiply(scalar));
                    break;
                case 4:
                    ComplexMatrices vecMat1 = readVectorAsMatrix(sc, "Vector 1");
                    ComplexMatrices vecMat2 = readVectorAsMatrix(sc, "Vector 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Vector Inner Product: " + innerProduct.innerProductValue(vecMat1, vecMat2));
                    break;
                case 5:
                    ComplexMatrices vecMat = readVectorAsMatrix(sc, "Vector");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Vector Norm (Length): " + innerProduct.normValue(vecMat));
                    break;
                case 6:
                    ComplexMatrices orthoV1 = readVectorAsMatrix(sc, "Vector 1");
                    ComplexMatrices orthoV2 = readVectorAsMatrix(sc, "Vector 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Orthogonal? " + innerProduct.orthogonal(orthoV1, orthoV2));
                    break;
                case 7:
                    ComplexMatrices orthonV1 = readVectorAsMatrix(sc, "Vector 1");
                    ComplexMatrices orthonV2 = readVectorAsMatrix(sc, "Vector 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Orthonormal? " + innerProduct.orthonormal(orthonV1, orthonV2));
                    break;
                case 8:
                    ComplexMatrices angleV1 = readVectorAsMatrix(sc, "Vector 1");
                    ComplexMatrices angleV2 = readVectorAsMatrix(sc, "Vector 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.printf("Angle between vectors: %.2f degrees%n", innerProduct.angle(angleV1, angleV2));
                    break;
                default:
                    System.out.println("Choose a valid menu option.");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("\nError: " + e.getMessage());
        }
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
                    vec.setElement(i, parseComplex(input));
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
    private static ComplexMatrices readVectorAsMatrix(Scanner sc, String name)
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
                    mat.setElement(i, 0, parseComplex(input));
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

    /**
     * Interactive menu for advanced Matrix operations.
     * Note: Quantum State Vectors can be processed here as Nx1 or 1xN matrices.
     */
    public static void matrixMenu(Scanner sc)
    {
        System.out.println("\n=========== MATRIX MENU ===========");
        System.out.println("[1] Matrix Addition");
        System.out.println("[2] Matrix Subtraction");
        System.out.println("[3] Additive Inverse");
        System.out.println("[4] Scalar Multiplication");
        System.out.println("[5] Matrix Multiplication");
        System.out.println("[6] Transpose Matrix");
        System.out.println("[7] Conjugate Matrix");
        System.out.println("[8] Dagger (Conjugate Transpose)");
        System.out.println("[9] Trace (Diagonal Sum)");
        System.out.println("[10] Matrix Inner Product");
        System.out.println("[11] Matrix Norm");
        System.out.println("[12] Matrix Distance");
        System.out.println("[13] Check Orthogonality");
        System.out.println("[14] Check Orthonormality");
        System.out.println("[15] Angle Between Matrices");
        System.out.println("[0] Back");
        System.out.println("===================================");
        System.out.print("Choose an option: ");

        int choice;
        try
        {
            choice = Integer.parseInt(sc.nextLine().trim());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid input! Returning to main menu.");
            return;
        }

        if (choice == 0) return;

        try 
        {
            switch (choice)
            {
                case 1:
                    ComplexMatrices m1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices m2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m1.add(m2));
                    break;
                case 2:
                    ComplexMatrices mx1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices mx2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(mx1.subtract(mx2));
                    break;
                case 3:
                    ComplexMatrices m3 = readMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m3.inverse());
                    break;
                case 4:
                    ComplexMatrices m4 = readMatrix(sc, "Matrix");
                    System.out.println("Enter the scalar (Complex Number, e.g., 2 or 3i):");
                    ComplexNumber scalar = parseComplex(sc.nextLine().replace(" ", ""));
                    
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m4.scalarMultiply(scalar));
                    break;
                case 5:
                    ComplexMatrices m5 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices m6 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m5.matrixMultiplication(m6));
                    break; 
                case 6:
                    ComplexMatrices m7 = readMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m7.transposeComplexMatrices());
                    break;
                case 7:
                    ComplexMatrices m8 = readMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m8.conjugMatrices());
                    break;
                case 8:
                    ComplexMatrices m9 = readMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m9.dagger());
                    break;
                case 9:
                    ComplexMatrices m10 = readMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Trace: " + m10.trace());
                    break;
                case 10:
                    ComplexMatrices mat1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices mat2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Matrix Inner Product <1, 2>: " + innerProduct.innerProductValue(mat1, mat2));
                    break;
                case 11:
                    ComplexMatrices matNorm = readMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Matrix Norm: " + innerProduct.normValue(matNorm));
                    break;
                case 12:
                    ComplexMatrices matx1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices matx2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Matrix distance <1, 2>: " + innerProduct.distance(matx1, matx2));
                    break;
                case 13:
                    ComplexMatrices orthoM1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices orthoM2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Orthogonal? " + innerProduct.orthogonal(orthoM1, orthoM2));
                    break;
                case 14:
                    ComplexMatrices orthonM1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices orthonM2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Orthonormal? " + innerProduct.orthonormal(orthonM1, orthonM2));
                    break;
                case 15:
                    ComplexMatrices angleM1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices angleM2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.printf("Angle between matrices: %.2f degrees%n", innerProduct.angle(angleM1, angleM2));
                    break;

                default:
                    System.out.println("Choose a valid menu option.");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("\nError: " + e.getMessage());
        }
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
                        mat.setElement(i, j, parseComplex(input));
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