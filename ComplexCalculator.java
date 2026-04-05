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
            System.out.println("[1] Binary Option");
            System.out.println("[2] Unary Option");
            System.out.println("[0] Exit");
            System.out.println("=================================");
            System.out.print("Choose an option: ");
            
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

        // Here we create the objects using the text input!
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

        // If it's option 4, the user types polar and we convert it while reading
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
                // Since it was already read via parsePolar and transformed into cartesian, just print it!
                System.out.println("Cartesian Form:");
                System.out.println(n1);
                break;
            default:
                System.out.println("Choose a valid menu option.");
        }
    }



    // Single function to extract real and imaginary parts and create the ComplexNumber object
    private static ComplexNumber parseComplex(String nc)
    {
        String[] parts = nc.split("(?=[+-])");
        double r = 0, i = 0;

        for (String p : parts)
        {
            // Prevents trying to convert empty strings generated by split
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

    // Helper function for parseComplex: converts the imaginary part to numeric
    private static double extractValue(String s)
    {
        String cleaned = s.replace("i", "");
        if (cleaned.isEmpty() || cleaned.equals("+")) return 1.0;
        if (cleaned.equals("-")) return -1.0;
        return Double.parseDouble(cleaned);
    }

    // Reads the polar String and returns the ComplexNumber object in Cartesian
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

        // Uses the new class to create the number directly!
        return ComplexNumber.fromPolar(r, angleRadians);
    }

    // Helper for parsePolar: interprets if there is PI in the entered angle
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

    // Visual formatting of the square root (Stays here in the view, not in the calculation)
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
}