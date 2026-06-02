package math.linear.interfaces;
import math.core.ComplexNumber;
import math.linear.impl.ComplexVector;

/**
 * Interface que contém apenas as declarações (contrato) para operações de Vetores.
 */
public interface IVector
{
    void setElement(int index, ComplexNumber number);
    ComplexNumber getElement(int index);
    ComplexVector add(ComplexVector other);
    ComplexVector inverse();
    ComplexVector scalarMultiply(ComplexNumber scalarNumber);
}