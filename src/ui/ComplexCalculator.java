package ui;

import java.util.Scanner;
import ui.menu.BinaryMenu;
import ui.menu.MatrixMenu;
import ui.menu.UnaryMenu;
import ui.menu.VectorMenu;

/**
 * Entry point of the Complex Calculator.
 * Shows the main menu and routes each option to its own menu class
 * (see the ui.menu package). Reading and parsing of user input lives
 * in the ui.input package.
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
                    BinaryMenu.run(sc);
                    break;
                case 2:
                    UnaryMenu.run(sc);
                    break;
                case 3:
                    VectorMenu.run(sc);
                    break;
                case 4:
                    MatrixMenu.run(sc);
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
}