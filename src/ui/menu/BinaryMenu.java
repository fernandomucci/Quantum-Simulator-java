package ui.menu;

import java.util.Scanner;
import math.core.ComplexNumber;
import static ui.input.ComplexParser.parseComplex;

/**
 * Menu for operations between two complex numbers, plus the entry
 * points to the system simulations (which don't take complex numbers
 * as input, so they're routed to SystemsMenu before the number input).
 */
public class BinaryMenu
{
    public static void run(Scanner sc)
    {
        System.out.println("\n========== BINARY MENU ==========");
        System.out.println("[1] Addition");
        System.out.println("[2] Subtraction");
        System.out.println("[3] Multiplication");
        System.out.println("[4] Division");
        System.out.println("[5] Deterministic System");
        System.out.println("[6] Probabilistic System");
        System.out.println("[7] Multi-Slit Experiment");
        System.out.println("[8] Quantum System");
        System.out.println("[9] Quantum Multi-Slit Experiment");
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

        // The system simulations take a matrix and a state vector, not
        // two complex numbers, so they're handled separately before the
        // shared complex-number input below.
        if (choice == 5)
        {
            SystemsMenu.deterministicSystemMenu(sc);
            return;
        }

        if (choice == 6)
        {
            SystemsMenu.probabilisticSystemMenu(sc);
            return;
        }

        if (choice == 7)
        {
            SystemsMenu.multiSlitExperimentMenu(sc);
            return;
        }

        if (choice == 8)
        {
            SystemsMenu.quantumSystemMenu(sc);
            return;
        }

        if (choice == 9)
        {
            SystemsMenu.quantumMultiSlitExperimentMenu(sc);
            return;
        }

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
}