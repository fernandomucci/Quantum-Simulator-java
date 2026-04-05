public class ComplexNumber
{
    // final because the number must be immutable
    private final double real;
    private final double imag;

    // Constructor (Cartesian Form)
    public ComplexNumber(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }

    // Static constructor for Polar Form
    public static ComplexNumber fromPolar(double ro, double angleRadians)
    {
        double r = ro * Math.cos(angleRadians);
        double i = ro * Math.sin(angleRadians);
        return new ComplexNumber(r, i);
    }


    // GETTERS

    public double getReal()
    {
        return this.real;
    }

    public double getImag()
    {
        return this.imag;
    }


    // OPERATIONS

    public ComplexNumber add(ComplexNumber other)
    {
        return new ComplexNumber(this.real + other.real, this.imag + other.imag);
    }

    public ComplexNumber subtract(ComplexNumber other)
    {
        return new ComplexNumber(this.real - other.real, this.imag - other.imag);
    }

    public ComplexNumber multiply(ComplexNumber other)
    {
        double newReal = (this.real * other.real) - (this.imag * other.imag);
        double newImag = (this.real * other.imag) + (this.imag * other.real);
        return new ComplexNumber(newReal, newImag);
    }

    public ComplexNumber divide(ComplexNumber other)
    {
        double den = Math.pow(other.real, 2) + Math.pow(other.imag, 2);
        
        if (den == 0)
        {
            throw new ArithmeticException("Division by zero in complex numbers.");
        }

        double newReal = (this.real * other.real + this.imag * other.imag) / den;
        double newImag = (this.imag * other.real - this.real * other.imag) / den;
        
        return new ComplexNumber(newReal, newImag);
    }

    public double modulus()
    {
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imag, 2));
    }

    public ComplexNumber conjugate()
    {
        return new ComplexNumber(this.real, -this.imag);
    }

    public double[] toPolar()
    {
        double modulus = this.modulus();
        double angle = Math.atan2(this.imag, this.real); // In radians
        return new double[]{modulus, angle};
    }


    // FORMATTING

    @Override
    public String toString()
    {
        String realStr = (this.real == (long) this.real) ? String.format("%d", (long) this.real) : String.format("%.2f", this.real);
        String imagStr = (Math.abs(this.imag) == (long) Math.abs(this.imag)) ? String.format("%d", (long) Math.abs(this.imag)) : String.format("%.2f", Math.abs(this.imag));

        if (this.real == 0 && this.imag == 0) return "0";
        if (this.real == 0) return (this.imag < 0 ? "-" : "") + imagStr + "i";
        if (this.imag == 0) return realStr;

        String sign = (this.imag > 0) ? " + " : " - ";
        return realStr + sign + imagStr + "i";
    }
}