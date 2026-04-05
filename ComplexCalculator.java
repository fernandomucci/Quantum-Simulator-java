import java.util.Scanner;

public class ComplexCalculator
{
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
            System.out.println("[0] Exit");
            System.out.println("=================================");
            System.out.print("Choose an option: ");
            
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

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

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return;

        System.out.println("Enter the first complex number (e.g., 2+3i or 3i+2):");
        String c1 = sc.nextLine().replace(" ", "");
        System.out.println("Enter the second complex number:");
        String c2 = sc.nextLine().replace(" ", "");

        // Parse input strings into ComplexNumber objects
        ComplexNumber n1 = parseComplex(c1);
        ComplexNumber n2 = parseComplex(c2);

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
                try
                {
                    System.out.println(n1.divide(n2));
                }
                catch (ArithmeticException e)
                {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Choose a valid menu option.");
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

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return;

        ComplexNumber n1;

        if (choice == 4)
        {
            System.out.println("Enter the number in polar representation (e.g., 3, pi/3):");
            String polarStr = sc.nextLine().replace(" ", "");
            n1 = parsePolar(polarStr); 
        }
        else
        {
            System.out.println("Enter the complex number (e.g., 2+3i or 3i+2):");
            String c1 = sc.nextLine().replace(" ", "");
            n1 = parseComplex(c1);
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
                System.out.println("Angle (θ) = " + String.format("%.2f", Math.toDegrees(polar[1])) + " degrees");
                break;
            case 4:
                System.out.println("Cartesian Form:");
                System.out.println(n1);
                break;
            default:
                System.out.println("Choose a valid menu option.");
        }
    }

    // Parses a standard complex number string into a ComplexNumber object
    private static ComplexNumber parseComplex(String nc)
    {
        String[] parts = nc.split("(?=[+-])");
        double r = 0, i = 0;

        for (String p : parts)
        {
            // Skip empty tokens generated by split
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

    // Extracts the numeric coefficient of the imaginary part
    private static double extractValue(String s)
    {
        String cleaned = s.replace("i", "");
        if (cleaned.isEmpty() || cleaned.equals("+")) return 1.0;
        if (cleaned.equals("-")) return -1.0;
        return Double.parseDouble(cleaned);
    }

    // Parses a polar string format (r, theta) into a Cartesian ComplexNumber
    private static ComplexNumber parsePolar(String nc)
    {
        String[] parts = nc.split(","); 
                
        if (parts.length < 2)
        {
            System.out.println("Error: Invalid polar format. Use a comma to separate.");
            return new ComplexNumber(0, 0);
        }

        double r = Double.parseDouble(parts[0].trim());
        double angleRadians = readValueWithPi(parts[1]); 

        return ComplexNumber.fromPolar(r, angleRadians);
    }

    // Converts an angle string (optionally containing 'pi') to radians
    private static double readValueWithPi(String nc)
    {
        nc = nc.toLowerCase().trim();

        if (!nc.contains("pi"))
        {   
            return Math.toRadians(Double.parseDouble(nc));
        }

        double sign = 1.0;
        if (nc.startsWith("-"))
        {
            sign = -1.0;
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

    // Formats square roots for cleaner output (e.g., √2 instead of 1.414...)
    public static String formatRoot(double value)
    {
        double square = value * value;
        int integer = (int) Math.round(square);

        if (Math.abs(square - integer) < 0.0001)
        {
            double root = Math.sqrt(square);
            if (Math.abs(root - Math.round(root)) < 0.0001)
            {
                return String.valueOf((int) Math.round(root));
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
        System.out.println("[0] Back");
        System.out.println("===================================");
        System.out.print("Choose an option: ");

        int choice = sc.nextInt();
        sc.nextLine();

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
                    String scalarStr = sc.nextLine().replace(" ", "");
                    ComplexNumber scalar = parseComplex(scalarStr);
                    
                    System.out.println("\n--- RESULT ---");
                    System.out.println(v4.scalarMultiply(scalar));
                    break;
                default:
                    System.out.println("Choose a valid menu option.");
            }
        } 
        catch (Exception e) 
        {
            // Catches exceptions like vector dimension mismatch
            System.out.println("\nError: " + e.getMessage());
        }
    }

    // Helper method to instantiate and populate a ComplexVector from user input
    public static ComplexVector readVector(Scanner sc, String name) 
    {
        System.out.print("\nEnter the size of " + name + ": ");
        int size = sc.nextInt();
        sc.nextLine(); 
        
        ComplexVector vec = new ComplexVector(size);

        System.out.println("Entering elements for " + name + "...");
        for (int i = 0; i < size; i++) 
        {
            System.out.print("Element [" + i + "] (e.g., 2+3i): ");
            String input = sc.nextLine().replace(" ", "");
            
            vec.setElement(i, parseComplex(input));
        }
        return vec;
    }
}