package math.linear.interfaces;
import math.core.ComplexNumber;
import math.linear.impl.ComplexMatrices;

public interface IInnerProduct
{
    ComplexNumber innerProductValue(ComplexMatrices m1, ComplexMatrices m2);
    ComplexNumber normValue(ComplexMatrices m1);
    ComplexNumber distance(ComplexMatrices m1, ComplexMatrices m2);
    boolean orthogonal(ComplexMatrices m1, ComplexMatrices m2);
    boolean orthonormal(ComplexMatrices m1, ComplexMatrices m2);
    double angle(ComplexMatrices m1, ComplexMatrices m2);
}
