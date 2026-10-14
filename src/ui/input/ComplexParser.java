package ui.input;

import math.core.ComplexNumber;

/**
 * Converts text typed by the user into complex numbers.
 * Pure parsing logic: no Scanner and no printing, so it can be reused
 * (and tested) independently of any menu.
 *
 * parseComplex accepts the original format (2+3i, 3i+2, i, -i, 1e-3+2i)
 * and also small math expressions, so values like 1/sqrt(6) don't have to
 * be calculated by hand:
 *
 *   - numbers:        2, 0.5, .5, 1e-3
 *   - imaginary unit: i  (3i, 2.5i, -i)
 *   - constant:       pi
 *   - operators:      + - * /   and parentheses
 *   - square root:    sqrt(x) or the symbol (works for complex x too)
 *   - implicit product: 2i, 3sqrt(2), 2(1+i)
 *
 * Examples: 1/sqrt(2)   (-1+i)/sqrt(6)   (1-i)/sqrt(12)   2+3i
 *
 * Note: "1/2i" is read left to right as (1/2)*i. Use 1/(2i) if you mean
 * the other one.
 */
public class ComplexParser
{
    // Written as an escape so the file compiles regardless of the source encoding.
    private static final char SQRT_SYMBOL = '\u221A';

    public static ComplexNumber parseComplex(String nc) throws IllegalArgumentException
    {
        if (nc == null)
        {
            throw new IllegalArgumentException("Invalid complex number format.");
        }

        String text = nc.replaceAll("\\s+", "").toLowerCase();

        if (text.isEmpty())
        {
            throw new IllegalArgumentException("Invalid complex number format.");
        }

        try
        {
            return new ExpressionParser(text).parse();
        }
        catch (ArithmeticException e)
        {
            // e.g. division by zero inside the expression
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Recursive-descent evaluator for the grammar:
     *   expression := term (('+' | '-') term)*
     *   term       := unary (('*' | '/' | implicit product) unary)*
     *   unary      := ('+' | '-') unary | atom
     *   atom       := number | 'i' | 'pi' | '(' expression ')' | sqrt unary
     */
    private static class ExpressionParser
    {
        private final String text;
        private int pos = 0;

        ExpressionParser(String text)
        {
            this.text = text;
        }

        ComplexNumber parse()
        {
            ComplexNumber result = parseExpression();

            if (pos < text.length())
            {
                throw error();
            }

            return result;
        }

        private ComplexNumber parseExpression()
        {
            ComplexNumber result = parseTerm();

            while (pos < text.length())
            {
                char c = text.charAt(pos);

                if (c == '+')
                {
                    pos++;
                    result = result.add(parseTerm());
                }
                else if (c == '-')
                {
                    pos++;
                    result = result.subtract(parseTerm());
                }
                else
                {
                    break;
                }
            }

            return result;
        }

        private ComplexNumber parseTerm()
        {
            ComplexNumber result = parseUnary();

            while (pos < text.length())
            {
                char c = text.charAt(pos);

                if (c == '*')
                {
                    pos++;
                    result = result.multiply(parseUnary());
                }
                else if (c == '/')
                {
                    pos++;
                    result = result.divide(parseUnary());
                }
                else if (startsAtom())
                {
                    // implicit product: 3i, 2sqrt(2), 2(1+i)
                    result = result.multiply(parseUnary());
                }
                else
                {
                    break;
                }
            }

            return result;
        }

        private ComplexNumber parseUnary()
        {
            if (pos >= text.length())
            {
                throw error();
            }

            char c = text.charAt(pos);

            if (c == '+')
            {
                pos++;
                return parseUnary();
            }

            if (c == '-')
            {
                pos++;
                return new ComplexNumber(0, 0).subtract(parseUnary());
            }

            return parseAtom();
        }

        private ComplexNumber parseAtom()
        {
            char c = text.charAt(pos);

            if (c == '(')
            {
                pos++;
                ComplexNumber inner = parseExpression();

                if (pos >= text.length() || text.charAt(pos) != ')')
                {
                    throw error();
                }
                pos++;
                return inner;
            }

            if (c == SQRT_SYMBOL)
            {
                pos++;
                return sqrt(parseUnary());
            }

            if (text.startsWith("sqrt", pos))
            {
                pos += 4;
                return sqrt(parseUnary());
            }

            if (text.startsWith("pi", pos))
            {
                pos += 2;
                return new ComplexNumber(Math.PI, 0);
            }

            if (c == 'i')
            {
                pos++;
                return new ComplexNumber(0, 1);
            }

            if (isDigit(c) || c == '.')
            {
                return new ComplexNumber(parseNumber(), 0);
            }

            throw error();
        }

        // True if an atom (number, i, pi, sqrt, parenthesis) starts at the current position.
        private boolean startsAtom()
        {
            char c = text.charAt(pos);

            return isDigit(c) || c == '.' || c == '(' || c == 'i' || c == SQRT_SYMBOL
                || text.startsWith("sqrt", pos) || text.startsWith("pi", pos);
        }

        private double parseNumber()
        {
            int start = pos;

            while (pos < text.length() && isDigit(text.charAt(pos)))
            {
                pos++;
            }

            if (pos < text.length() && text.charAt(pos) == '.')
            {
                pos++;
                while (pos < text.length() && isDigit(text.charAt(pos)))
                {
                    pos++;
                }
            }

            // Exponent (1e-3) only counts if digits really follow the 'e'.
            if (pos < text.length() && text.charAt(pos) == 'e')
            {
                int p = pos + 1;

                if (p < text.length() && (text.charAt(p) == '+' || text.charAt(p) == '-'))
                {
                    p++;
                }

                if (p < text.length() && isDigit(text.charAt(p)))
                {
                    while (p < text.length() && isDigit(text.charAt(p)))
                    {
                        p++;
                    }
                    pos = p;
                }
            }

            try
            {
                return Double.parseDouble(text.substring(start, pos));
            }
            catch (NumberFormatException e)
            {
                throw error();
            }
        }

        private static boolean isDigit(char c)
        {
            return c >= '0' && c <= '9';
        }

        private IllegalArgumentException error()
        {
            return new IllegalArgumentException("Invalid complex number format.");
        }
    }

    /**
     * Principal square root of a complex number. Computed directly (not via
     * polar form) so sqrt(4) is exactly 2 and sqrt(-4) is exactly 2i.
     */
    private static ComplexNumber sqrt(ComplexNumber z)
    {
        double a = z.getReal();
        double b = z.getImag();

        if (b == 0)
        {
            return a >= 0 ? new ComplexNumber(Math.sqrt(a), 0) : new ComplexNumber(0, Math.sqrt(-a));
        }

        double m = z.modulus();
        double re = Math.sqrt((m + a) / 2);
        double im = Math.sqrt((m - a) / 2);

        return new ComplexNumber(re, b < 0 ? -im : im);
    }

    public static ComplexNumber parsePolar(String nc) throws IllegalArgumentException
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
}