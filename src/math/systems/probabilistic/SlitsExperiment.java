package math.systems.probabilistic;
import math.core.ComplexNumber;
import math.linear.impl.ComplexMatrices;

    /**
 * Simulates a generalized multi-slit experiment (Programming Drill 3.2.2).
 *
 * Vertex layout of the transition matrix:
 *   0                = the gun
 *   1 .. numSlits     = the slits
 *   numSlits+1 .. end = the targets
 *
 * The gun fires with equal probability into any of the slits (generalizing
 * the 50/50 rule from the book's double-slit example to N slits). Each slit
 * then sends the bullet to a target according to a user-supplied
 * probability distribution. Once a bullet reaches a target, it stays there
 * with probability 1 (an absorbing state), exactly as in the book's example.
 */
public class SlitsExperiment
{
    /**
     * Builds the full transition matrix for the experiment.
     *
     * @param numSlits number of slits
     * @param numTargets number of targets
     * @param slitToTargetProbabilities a (numTargets x numSlits) matrix where
     *        column i is the probability distribution of a bullet passing
     *        through slit i landing on each target. Each column should sum
     *        to 1 — the caller can verify this with Probabilistic.isStochastic.
     */
    public ComplexMatrices buildTransitionMatrix(int numSlits, int numTargets, ComplexMatrices slitToTargetProbabilities)
    {
        if (numSlits <= 0 || numTargets <= 0)
        {
            throw new IllegalArgumentException("Number of slits and targets must be greater than zero.");
        }
 
        if (slitToTargetProbabilities == null)
        {
            throw new IllegalArgumentException("Slit-to-target probability matrix cannot be null.");
        }
 
        if (slitToTargetProbabilities.getRows() != numTargets || slitToTargetProbabilities.getCols() != numSlits)
        {
            throw new IllegalArgumentException(
                "Slit-to-target matrix must be " + numTargets + "x" + numSlits +
                " (targets x slits), but was " + slitToTargetProbabilities.getRows() +
                "x" + slitToTargetProbabilities.getCols() + "."
            );
        }
 
        int size = 1 + numSlits + numTargets;
        ComplexMatrices transition = new ComplexMatrices(size, size);
 
        // Gun -> each slit, with equal probability.
        double gunToSlitProbability = 1.0 / numSlits;
        for (int slit = 1; slit <= numSlits; slit++)
        {
            transition.setElement(slit, 0, new ComplexNumber(gunToSlitProbability, 0));
        }
 
        // Each slit -> each target, using the caller-supplied probabilities.
        for (int slit = 0; slit < numSlits; slit++)
        {
            int slitVertex = slit + 1;
 
            for (int target = 0; target < numTargets; target++)
            {
                int targetVertex = 1 + numSlits + target;
                ComplexNumber probability = slitToTargetProbabilities.getElement(target, slit);
                transition.setElement(targetVertex, slitVertex, probability);
            }
        }
 
        // Once a bullet reaches a target, it stays there (absorbing state).
        for (int target = 0; target < numTargets; target++)
        {
            int targetVertex = 1 + numSlits + target;
            transition.setElement(targetVertex, targetVertex, new ComplexNumber(1, 0));
        }
 
        return transition;
    }
 
    /**
     * Creates the initial state vector for the experiment: the bullet
     * always starts at the gun (vertex 0) with certainty.
     */
    public ComplexMatrices createInitialState(int size)
    {
        if (size <= 0)
        {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
 
        ComplexMatrices initialState = new ComplexMatrices(size, 1);
        initialState.setElement(0, 0, new ComplexNumber(1, 0));
        return initialState;
    }


      }   

