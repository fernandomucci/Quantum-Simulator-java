package ui.input;

import math.core.ComplexNumber;

/**
 * Converts text typed by the user into complex numbers.
 * Pure parsing logic: no Scanner and no printing, so it can be reused
 * (and tested) independently of any menu.
 */
public class ComplexParser
{
    public static ComplexNumber parseComplex(String nc) throws IllegalArgumentException
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