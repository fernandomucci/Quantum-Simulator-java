package ui.menu;

import static ui.input.ComplexParser.parseComplex;
import static ui.input.ComplexParser.parsePolar;

import java.util.Scanner;
import math.core.ComplexNumber;

/**
 * Menu for operations on a single complex number (modulus, conjugate,
 * polar and cartesian representations).
 */
public class UnaryMenu
{
    public static void run(Scanner sc)
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
                    System.out.println("Angle (\u03b8) = " + String.format("%.2f", Math.toDegrees(polar[1])) + " degrees");
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
}