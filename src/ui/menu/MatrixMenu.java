package ui.menu;

import static ui.input.ComplexParser.parseComplex;
import static ui.input.InputReader.readMatrix;
import static ui.input.InputReader.readProbabilityMatrix;
import static ui.input.InputReader.readSquareMatrix;

import java.util.Scanner;
import math.core.ComplexNumber;
import math.linear.analysis.HermitianUnitaryMatrices;
import math.linear.analysis.InnerProduct;
import math.linear.impl.ComplexMatrices;
import math.linear.impl.tensorProduct;
import math.linear.interfaces.IInnerProduct;
import math.systems.probabilistic.Probabilistic;

/**
 * Menu for matrix operations: basic algebra, inner-product-based
 * operations, property checks (Hermitian, Unitary, Stochastic)
 * and the tensor product.
 */
public class MatrixMenu
{
    private static final IInnerProduct innerProduct = new InnerProduct();
    private static final HermitianUnitaryMatrices hermitianChecker = new HermitianUnitaryMatrices();
    private static final HermitianUnitaryMatrices unitaryChecker = new HermitianUnitaryMatrices();
    private static final tensorProduct matrixTensor = new tensorProduct();
    private static final Probabilistic probabilisticSystem = new Probabilistic();

    /**
     * Interactive menu for advanced Matrix operations.
     * Note: Quantum State Vectors can be processed here as Nx1 or 1xN matrices.
     */
    public static void run(Scanner sc)
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
        System.out.println("[16] Check if Matrix is Hermitian");
        System.out.println("[17] Check if Matrix is Unitary");
        System.out.println("[18] Tensor Product");
        System.out.println("[19] Check if Matrix is Stochastic");
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
                    ComplexMatrices m10 = readSquareMatrix(sc, "Matrix");
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
                case 16:
                    ComplexMatrices hermM = readSquareMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Is Hermitian? " + hermitianChecker.isHermitian(hermM));
                    break;
                case 17:
                    ComplexMatrices uniM = readSquareMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Is Unitaty? " + unitaryChecker.IsUnitary(uniM));
                    break;
                case 18:
                    ComplexMatrices mTensor1 = readMatrix(sc, "Matrix 1");
                    ComplexMatrices mTensor2 = readMatrix(sc, "Matrix 2");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("The tensor product is: " + matrixTensor.tensorProductCalc(mTensor1, mTensor2));
                    break;
                case 19:
                    ComplexMatrices stochM = readProbabilityMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println("Is Stochastic? " + probabilisticSystem.isStochastic(stochM));
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