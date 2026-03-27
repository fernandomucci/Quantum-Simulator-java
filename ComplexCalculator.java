import java.util.Scanner;

public class ComplexCalculator
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0)
        {
            System.out.println("Exit[0]");
            System.out.println("Binary Option[1]");
            System.out.println("Unary Option[2]");
            System.out.println("");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice)
            {
                case 1:
                    binaryMenu(sc);
                    break;

                case 2:
                    unaryMenu(sc);
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }

        sc.close();
    }

    // Single function to extract real and imaginary parts
    private static double[] parseComplex(String cn)
    {
        // We separate keeping the "i" to know which is which
        // The regex separates the string before the signs, e.g., 2+3i -> (2) (+3i)
        String[] parts = cn.split("(?=[+-])");
        double r = 0, i = 0;

        for (String p : parts)
        {
            // If it's an imaginary number, call extractValue, otherwise just convert to double
            if (p.contains("i"))
            {
                i = extractValue(p);
            }
            else
            {
                r = Double.parseDouble(p);
            }
        }
        return new double[]{r, i};
    }

    // Convert the part of the String that represents the imaginary number into a numeric value
    private static double extractValue(String s)
    {
        // Remove the 'i'
        String clean = s.replace("i", "");

        // If where the 'i' was there is nothing or a '+' sign, the 'i' was positive ( i )
        if (clean.isEmpty() || clean.equals("+")) return 1.0;

        // If where the 'i' was there is a '-', the 'i' was negative ( - i )
        if (clean.equals("-")) return -1.0;

        // If it doesn't fall into the cases above, treat it as a real number and parse it
        return Double.parseDouble(clean);
    }

    public static double[] add(String cn1, String cn2)
    {
        double[] n1 = parseComplex(cn1);
        double[] n2 = parseComplex(cn2);

        double real = n1[0] + n2[0];
        double imag = n1[1] + n2[1];

        return new double[]{real, imag};
    }

    public static double[] subtract(String cn1, String cn2)
    {
        double[] n1 = parseComplex(cn1);
        double[] n2 = parseComplex(cn2);

        double real = n1[0] - n2[0];
        double imag = n1[1] - n2[1];

        return new double[]{real, imag};
    }

    public static double[] multiply(String cn1, String cn2)
    {
        double[] n1 = parseComplex(cn1);
        double[] n2 = parseComplex(cn2);

        double real = (n1[0] * n2[0]) - (n1[1] * n2[1]);
        double imag = (n1[0] * n2[1]) + (n1[1] * n2[0]);

        return new double[]{real, imag};
    }

    public static double[] divide(String cn1, String cn2)
    {
        double[] n1 = parseComplex(cn1);
        double[] n2 = parseComplex(cn2);

        double den = Math.pow(n2[0], 2) + Math.pow(n2[1], 2);

        if (den == 0)
        {
            System.out.println("Error: Division by zero.");
            return null;
        }

        double real = (n1[0] * n2[0] + n1[1] * n2[1]) / den;
        double imag = (n1[1] * n2[0] - n1[0] * n2[1]) / den;

        return new double[]{real, imag};
    }

    public static double[] conjugate(String cn)
    {
        double[] n = parseComplex(cn);
        return new double[]{n[0], -n[1]};
    }

    public static double modulus(String cn)
    {
        double[] n = parseComplex(cn);

        double real = n[0];
        double imag = n[1];

        double squared = Math.pow(real, 2) + Math.pow(imag, 2);

        return Math.sqrt(squared);
    }

    public static double[] convertToPolar(String cn)
    {
        double p = radius(cn);

        double[] n = parseComplex(cn);
        double real = n[0];
        double imag = n[1];

        double theta = angleTheta(real, imag);

        return new double[]{p, theta};
    }

    public static double radius(String cn)
    {
        double p = modulus(cn);
        return p;
    }

    public static double angleTheta(double real, double imaginary)
    {
        double theta = Math.atan2(imaginary, real);
        return Math.toDegrees(theta);
    }

    public static double[] convertToCartesian(String cn)
    {
        double[] polar = parsePolar(cn);
        double modulus = polar[0];
        double angleRadians = polar[1]; // Already comes in radians from the parsePolar function

        double real = modulus * Math.cos(angleRadians);
        double imag = modulus * Math.sin(angleRadians);

        return new double[]{real, imag};
    }

    private static void printComplex(double r, double i)
    {
        String realStr = (r == (long) r) ? String.format("%d", (long) r) : String.format("%.2f", r);
        String imagStr = (Math.abs(i) == (long) Math.abs(i)) ? String.format("%d", (long) Math.abs(i)) : String.format("%.2f", Math.abs(i));

        // If both are zero
        if (r == 0 && i == 0)
        {
            System.out.println("0");
            return;
        }

        // If the real part is zero, print only the imaginary part
        if (r == 0)
        {
            System.out.println((i < 0 ? "-" : "") + imagStr + "i");
            return;
        }

        // If the imaginary part is zero, print only the real part
        if (i == 0)
        {
            System.out.println(realStr);
            return;
        }

        // If both exist
        String sign = (i > 0) ? " + " : " - ";
        System.out.println(realStr + sign + imagStr + "i");
    }

    public static void binaryMenu(Scanner sc)
    {
        int choice = -1;

        System.out.println("Back[0]");
        System.out.print("Addition[1] ");
        System.out.print("Subtraction[2] ");
        System.out.print("Multiplication[3] ");
        System.out.print("Division[4] ");
        System.out.println("");

        choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return;

        System.out.println("Enter the first complex number (ex: 2+3i or 3i+2):");
        String cn1 = sc.nextLine().replace(" ", "");
        System.out.println("Enter the second complex number:");
        String cn2 = sc.nextLine().replace(" ", "");

        switch (choice)
        {
            case 1:
                double[] resAdd = add(cn1, cn2);
                printComplex(resAdd[0], resAdd[1]);
                break;

            case 2:
                double[] resSub = subtract(cn1, cn2);
                printComplex(resSub[0], resSub[1]);
                break;

            case 3:
                double[] resMult = multiply(cn1, cn2);
                printComplex(resMult[0], resMult[1]);
                break;

            case 4:
                double[] resDiv = divide(cn1, cn2);
                if (resDiv != null)
                {
                    printComplex(resDiv[0], resDiv[1]);
                }
                else
                {
                    System.out.println("Error: Division by zero.");
                }
                break;

            default:
                System.out.println("Choose a valid option from the menu");
        }
    }

    public static void unaryMenu(Scanner sc)
    {
        int choice = -1;

        System.out.println("Back[0]");
        System.out.println("Modulus[1]");
        System.out.println("Conjugate[2]");
        System.out.println("Polar Representation[3]");
        System.out.println("Cartesian Representation[4]");
        System.out.println("");

        choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return;

        if (choice == 4)
        {
            System.out.println("Enter the number in polar representation (ex: 3, pi/3):");
        }
        else
        {
            System.out.println("Enter the complex number (ex: 2+3i or 3i+2):");
        }

        String cn1 = sc.nextLine().replace(" ", "");

        switch (choice)
        {
            case 1:
                double mod = modulus(cn1);
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
                double[] resConj = conjugate(cn1);
                printComplex(resConj[0], resConj[1]);
                break;

            case 3:
                double[] resPolar = convertToPolar(cn1);
                System.out.println("Polar Form: ");
                System.out.println("r = " + formatRoot(resPolar[0]));
                System.out.println("Angle (θ) = " + String.format("%.2f", resPolar[1]) + " degrees");
                break;

            case 4:
                double[] resCart = convertToCartesian(cn1);
                System.out.println("Cartesian Form:");
                printComplex(resCart[0], resCart[1]);
                break;

            default:
                System.out.println("Choose a valid option from the menu");
        }
    }

    public static String formatRoot(double value)
    {
        // Squares the number, for example, square root of 2 is 1.4142 * 1.4142 = 2
        double squared = value * value;

        // Transforms it to the nearest integer, in this case, it would be 2.0000001
        int integer = (int) Math.round(squared);

        // Checks if the number is almost an integer, with a tolerance of 0.0001
        if (Math.abs(squared - integer) < 0.0001)
        {
            // If it enters, it's a root
            double root = Math.sqrt(squared);

            // Checks if it is an integer root
            if (Math.abs(root - Math.round(root)) < 0.0001)
            {
                // If it is, return the integer number
                return String.valueOf((int) Math.round(root));
            }

            // Otherwise, returns the root / this is the code for √
            return "\u221A" + integer;
        }

        // If the number is not a root, just return the number
        return String.format("%.2f", value);
    }

    private static double readValueWithPi(String cn)
    {
        cn = cn.toLowerCase().trim();

        // If it doesn't have pi, it's a normal angle in degrees (ex: "90" or "-45")
        if (!cn.contains("pi"))
        {
            // Return in radians
            return Math.toRadians(Double.parseDouble(cn));
        }

        // If it has pi, we need to know if it's positive, negative, and if there is a division
        double sign = 1.0;
        if (cn.startsWith("-"))
        {
            sign = -1.0;
            cn = cn.substring(1); // Removes the minus sign so it doesn't interfere with parsing
        }

        double multiplier = 1.0;
        double divisor = 1.0;

        // If there is a division (ex: pi/3 or 2pi/3)
        if (cn.contains("/"))
        {
            String[] parts = cn.split("/");

            // Checks the top side (numerator)
            String numerator = parts[0].replace("pi", "").trim();
            if (!numerator.isEmpty())
            {
                multiplier = Double.parseDouble(numerator);
            }

            // Checks the bottom side (denominator)
            divisor = Double.parseDouble(parts[1].trim());
        }
        // If there is no division, but there is a multiplier (ex: "2pi")
        else
        {
            String numerator = cn.replace("pi", "").trim();
            if (!numerator.isEmpty())
            {
                multiplier = Double.parseDouble(numerator);
            }
        }

        // Final calculation: sign * multiplier * PI / divisor
        return sign * multiplier * Math.PI / divisor;
    }

    // Parses the polar number
    private static double[] parsePolar(String cn)
    {
        String[] parts = cn.split(",");

        if (parts.length < 2)
        {
            System.out.println("Error: Invalid polar format. Use a comma to separate.");
            return new double[]{0, 0};
        }

        double r = Double.parseDouble(parts[0].trim());
        double angleRadians = readValueWithPi(parts[1]);

        return new double[]{r, angleRadians};
    }
}