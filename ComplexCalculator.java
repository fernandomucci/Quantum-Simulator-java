import java.util.Scanner;

/**
 * Main driver class for the Complex Calculator.
 * Handles user interface, menu navigation, and robust input validation.
 */
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
            System.out.println("[4] Matrix Operations");
            System.out.println("[0] Exit");
            System.out.println("=================================");
            System.out.print("Choose an option: ");
            
            try
            {
                // Reading the whole line prevents scanner buffer issues
                choice = Integer.parseInt(sc.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }

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
                case 4:
                    matrixMenu(sc);
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
            System.out.println("Enter the first complex number (e.g., 2+3i or 3i+2):");
            ComplexNumber n1 = parseComplex(sc.nextLine().replace(" ", ""));
            
            System.out.println("Enter the second complex number:");
            ComplexNumber n2 = parseComplex(sc.nextLine().replace(" ", ""));

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
                    System.out.println(n1.divide(n2));
                    break;
                default:
                    System.out.println("Choose a valid menu option.");
            }
        } 
        catch (IllegalArgumentException | ArithmeticException e) 
        {
            System.out.println("Error: " + e.getMessage());
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

        ComplexNumber n1;

        try 
        {
            if (choice == 4)
            {
                System.out.println("Enter the number in polar representation (e.g., 3, pi/3):");
                n1 = parsePolar(sc.nextLine().replace(" ", "")); 
            }
            else
            {
                System.out.println("Enter the complex number (e.g., 2+3i or 3i+2):");
                n1 = parseComplex(sc.nextLine().replace(" ", ""));
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
        catch (IllegalArgumentException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Parses a standard complex number string into a ComplexNumber object.
     * Throws an exception if the format is invalid.
     */
    private static ComplexNumber parseComplex(String nc) throws IllegalArgumentException
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

    /**
     * Extracts the numeric coefficient of the imaginary part.
     */
    private static double extractValue(String s) throws NumberFormatException
    {
        String cleaned = s.replace("i", "");
        if (cleaned.isEmpty() || cleaned.equals("+")) return 1.0;
        if (cleaned.equals("-")) return -1.0;
        return Double.parseDouble(cleaned);
    }

    /**
     * Parses a polar string format (r, theta) into a Cartesian ComplexNumber.
     */
    private static ComplexNumber parsePolar(String nc) throws IllegalArgumentException
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

    /**
     * Converts an angle string (optionally containing 'pi') to radians.
     */
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

    /**
     * Formats square roots for cleaner console output.
     */
    public static String formatRoot(double value)
    {
        double square = value * value;
        long integer = (long) Math.round(square);

        if (Math.abs(square - integer) < 0.0001)
        {
            double root = Math.sqrt(square);
            if (Math.abs(root - Math.round(root)) < 0.0001)
            {
                return String.valueOf((long) Math.round(root));
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
                default:
                    System.out.println("Choose a valid menu option.");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to define and populate a ComplexVector safely.
     */
    public static ComplexVector readVector(Scanner sc, String name) 
    {
        int size = 0;
        while (true) 
        {
            try 
            {
                System.out.print("\nEnter the size of " + name + ": ");
                size = Integer.parseInt(sc.nextLine().trim());
                if (size > 0) break;
                System.out.println("Size must be greater than zero.");
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        
        ComplexVector vec = new ComplexVector(size);

        System.out.println("Entering elements for " + name + "...");
        for (int i = 0; i < size; i++) 
        {
            while (true) 
            {
                try 
                {
                    System.out.print("Element [" + i + "] (e.g., 2+3i): ");
                    String input = sc.nextLine().replace(" ", "");
                    vec.setElement(i, parseComplex(input));
                    break; // Move to the next element if successful
                } 
                catch (IllegalArgumentException e) 
                {
                    System.out.println("Invalid format. Try again.");
                }
            }
        }
        return vec;
    }

    public static void matrixMenu(Scanner sc)
    {
        System.out.println("\n=========== MATRIX MENU ===========");
        System.out.println("[1] Matrix Addition");
        System.out.println("[2] Additive Inverse");
        System.out.println("[3] Scalar Multiplication");
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
                    ComplexMatrices m3 = readMatrix(sc, "Matrix");
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m3.inverse());
                    break;
                case 3:
                    ComplexMatrices m4 = readMatrix(sc, "Matrix");
                    System.out.println("Enter the scalar (Complex Number, e.g., 2 or 3i):");
                    ComplexNumber scalar = parseComplex(sc.nextLine().replace(" ", ""));
                    
                    System.out.println("\n--- RESULT ---");
                    System.out.println(m4.scalarMultiply(scalar));
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

    /**
     * Prompts the user to define and populate a ComplexMatrices safely.
     */
    public static ComplexMatrices readMatrix(Scanner sc, String name) 
    {
        int rows = 0, cols = 0;
        while (true) 
        {
            try 
            {
                System.out.print("\nEnter the number of ROWS for " + name + ": ");
                rows = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Enter the number of COLUMNS for " + name + ": ");
                cols = Integer.parseInt(sc.nextLine().trim());
                
                if (rows > 0 && cols > 0) break;
                System.out.println("Rows and columns must be greater than zero.");
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid input. Please enter valid integers.");
            }
        }
        
        ComplexMatrices mat = new ComplexMatrices(rows, cols);

        System.out.println("Entering elements for " + name + "...");
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < cols; j++) 
            {
                while (true) 
                {
                    try 
                    {
                        System.out.print("Element [" + i + "][" + j + "] (e.g., 2+3i): ");
                        String input = sc.nextLine().replace(" ", "");
                        mat.setElement(i, j, parseComplex(input));
                        break; // Move to the next element if successful
                    } 
                    catch (IllegalArgumentException e) 
                    {
                        System.out.println("Invalid format. Try again.");
                    }
                }
            }
        }
        return mat;
    }
}