package ui.menu;
 
import java.util.Scanner;
import math.linear.impl.ComplexMatrices;
import math.systems.classical.Deterministic;
import math.systems.probabilistic.Probabilistic;
import math.systems.probabilistic.SlitsExperiment;
import math.systems.quantum.QuantumSlitsExperiment;
import static ui.input.InputReader.readNonNegativeInt;
import static ui.input.InputReader.readPositiveInt;
import static ui.input.InputReader.readProbabilityMatrix;
import static ui.input.InputReader.readProbabilityVector;
import static ui.input.InputReader.readRectangularComplexMatrix;
import static ui.input.InputReader.readRectangularProbabilityMatrix;
import static ui.input.InputReader.readSquareMatrix;
import static ui.input.InputReader.readVectorAsMatrix;

/**
 * Menus that run the system simulations: deterministic, probabilistic
 * and the multi-slit experiment. Each one reads a transition matrix and
 * a state, hands them to the math layer, and prints the result.
 */
public class SystemsMenu
{
    private static final QuantumSlitsExperiment quantumSlitsExperiment = new QuantumSlitsExperiment();
    private static final Deterministic deterministicSystem = new Deterministic();
    private static final Probabilistic probabilisticSystem = new Probabilistic();
    private static final SlitsExperiment slitsExperiment = new SlitsExperiment();

    /**
     * Runs a deterministic system simulation (the "marble experiment"):
     * the user provides a transition matrix describing how the system
     * moves between vertices/states, an initial state vector, and a
     * number of time steps. The program then applies the transition
     * matrix that many times and shows the resulting state.
     */
    public static void deterministicSystemMenu(Scanner sc)
    {
        System.out.println("\n===== DETERMINISTIC SYSTEM =====");

        try
        {
            ComplexMatrices transitionMatrix = readSquareMatrix(sc, "Transition Matrix");
            int size = transitionMatrix.getRows();

            System.out.println("Now enter the initial state (size must match the matrix: " + size + ").");
            ComplexMatrices initialState = readVectorAsMatrix(sc, "Initial State");

            if (initialState.getRows() != size)
            {
                System.out.println("Error: state vector size (" + initialState.getRows() +
                    ") must match the matrix size (" + size + ").");
                return;
            }

            int timeStep = readNonNegativeInt(sc, "Enter the number of time steps: ");

            ComplexMatrices finalState = deterministicSystem.multipleStepDynamics(transitionMatrix, timeStep, initialState);

            System.out.println("\n--- RESULT ---");
            System.out.println("State after " + timeStep + " time step(s):");
            System.out.println(finalState);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Runs a probabilistic system simulation: the user provides a
     * transition matrix of probabilities (each column must sum to 1) and
     * an initial probability distribution as the state vector. The matrix
     * is validated as stochastic before anything is computed, then applied
     * to the state for the chosen number of time steps.
     */
    public static void probabilisticSystemMenu(Scanner sc)
    {
        System.out.println("\n===== PROBABILISTIC SYSTEM =====");

        try
        {
            ComplexMatrices transitionMatrix = readProbabilityMatrix(sc, "Transition Matrix");

            if (!probabilisticSystem.isStochastic(transitionMatrix))
            {
                System.out.println("Error: this matrix is not stochastic — every column must sum to 1.");
                return;
            }

            int size = transitionMatrix.getRows();

            System.out.println("Now enter the initial state (size must match the matrix: " + size + ").");
            ComplexMatrices initialState = readProbabilityVector(sc, "Initial State", size);

            if (!probabilisticSystem.isStochastic(initialState))
            {
                System.out.println("Error: the initial state must be a valid probability distribution (must sum to 1).");
                return;
            }

            int timeStep = readNonNegativeInt(sc, "Enter the number of time steps: ");

            ComplexMatrices finalState = probabilisticSystem.multipleStepDynamics(transitionMatrix, timeStep, initialState);

            System.out.println("\n--- RESULT ---");
            System.out.println("Probability distribution after " + timeStep + " time step(s):");
            System.out.println(finalState);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Runs the generalized multi-slit experiment (Programming Drill 3.2.2):
     * the user chooses the number of slits and targets, enters the
     * probability of each slit sending a bullet to each target, and the
     * program builds the full transition matrix (gun -> slits -> targets),
     * squares it, and shows both the squared matrix and the resulting
     * state vector after two time clicks.
     */
    public static void multiSlitExperimentMenu(Scanner sc)
    {
        System.out.println("\n===== MULTI-SLIT EXPERIMENT =====");

        try
        {
            int numSlits = readPositiveInt(sc, "Enter the number of slits: ");
            int numTargets = readPositiveInt(sc, "Enter the number of targets: ");

            System.out.println("Now enter the probability of each slit sending a bullet to each target.");
            ComplexMatrices slitToTargetProbabilities = readRectangularProbabilityMatrix(sc, numTargets, numSlits);

            if (!probabilisticSystem.isStochastic(slitToTargetProbabilities))
            {
                System.out.println("Error: for each slit, the probabilities to all targets must sum to 1.");
                return;
            }

            ComplexMatrices transitionMatrix = slitsExperiment.buildTransitionMatrix(numSlits, numTargets, slitToTargetProbabilities);
            ComplexMatrices squaredMatrix = transitionMatrix.matrixMultiplication(transitionMatrix);

            int size = transitionMatrix.getRows();
            ComplexMatrices initialState = slitsExperiment.createInitialState(size);
            ComplexMatrices finalState = squaredMatrix.matrixMultiplication(initialState);

            System.out.println("\n--- RESULT ---");
            System.out.println("Transition matrix squared (after two time clicks):");
            System.out.println(squaredMatrix);
            System.out.println("Resulting state vector (bullet starts at the gun):");
            System.out.println(finalState);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void quantumMultiSlitExperimentMenu(Scanner sc)
    {
        System.out.println("\n===== QUANTUM MULTI-SLIT EXPERIMENT =====");
 
        try
        {
            int numSlits = readPositiveInt(sc, "Enter the number of slits: ");
            int numTargets = readPositiveInt(sc, "Enter the number of targets: ");
 
            System.out.println("Now enter the amplitude (a complex number) of each slit sending a photon to each target.");
            ComplexMatrices amplitudes = readRectangularComplexMatrix(sc, numTargets, numSlits);
 
            if (!quantumSlitsExperiment.isNormalizedPerSlit(amplitudes))
            {
                System.out.println("Error: for each slit, the squared moduli of its amplitudes to all targets must sum to 1.");
                return;
            }
 
            ComplexMatrices transitionMatrix = quantumSlitsExperiment.buildTransitionMatrix(numSlits, numTargets, amplitudes);
            ComplexMatrices squaredMatrix = transitionMatrix.matrixMultiplication(transitionMatrix);
 
            ComplexMatrices initialState = quantumSlitsExperiment.createInitialState(transitionMatrix.getRows());
            ComplexMatrices finalState = squaredMatrix.matrixMultiplication(initialState);
 
            System.out.println("\n--- RESULT ---");
            System.out.println("Transition matrix squared (after two time clicks):");
            System.out.println(squaredMatrix);
            System.out.println("Resulting state vector (photon starts at the flashlight):");
            System.out.println(finalState);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}