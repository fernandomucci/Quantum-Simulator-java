package math.linear.interfaces;
import math.core.ComplexNumber;
import math.linear.impl.ComplexMatrices;

/**
 * Interface que contém apenas as declarações (contrato) para operações de Matrizes.
 */
public interface IMatrix
{
    int getRows();
    int getCols();
    void setElement(int row, int col, ComplexNumber number);
    ComplexNumber getElement(int row, int col);
    ComplexMatrices add(ComplexMatrices other);
    ComplexMatrices inverse();
    ComplexMatrices scalarMultiply(ComplexNumber scalarNumber);
    ComplexMatrices matrixMultiplication(ComplexMatrices other);
    ComplexMatrices transposeComplexMatrices(ComplexMatrices m1);
    ComplexMatrices conjugMatrices(ComplexMatrices m1);
}