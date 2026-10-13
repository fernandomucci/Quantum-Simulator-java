package ui.menu;

import static ui.input.ComplexParser.parseComplex;
import static ui.input.InputReader.readVector;
import static ui.input.InputReader.readVectorAsMatrix;

import java.util.Scanner;
import math.core.ComplexNumber;
import math.linear.analysis.InnerProduct;
import math.linear.impl.ComplexMatrices;
import math.linear.impl.ComplexVector;
import math.linear.interfaces.IInnerProduct;

/**
 * Menu for vector operations (addition, inverse, scalar multiplication)
 * and inner-product-based operations (norm, orthogonality, angle).
 */
public class VectorMenu
{
    private static final IInnerProduct innerProduct = new InnerProduct();

    public static void run(Scanner sc)
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
}