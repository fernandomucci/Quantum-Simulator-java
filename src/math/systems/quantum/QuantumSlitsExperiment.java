package math.systems.quantum;

import math.core.ComplexNumber;
import math.linear.impl.ComplexMatrices;

/**
 * Quantum version of the multi-slit experiment (Programming Drill 3.3.2).
 *
 * Same vertex layout as the probabilistic version:
 *   0                = the flashlight (light source)
 *   1 .. numSlits     = the slits
 *   numSlits+1 .. end = the targets (measuring devices)
 *
 * The difference is that transitions are complex AMPLITUDES instead of
 * probabilities. A probability is only obtained at the end, as the squared
 * modulus of an amplitude. Since amplitudes are added before being squared,
 * paths to the same target can reinforce or cancel each other (interference).
 */
public class QuantumSlitsExperiment
{
    /**
     * Checks that, for every slit, the squared moduli of its amplitudes to
     * all targets sum to 1. This is the quantum counterpart of
     * Probabilistic.isStochastic: probability is conserved, but through
     * |amplitude|^2 instead of the plain value.
     *
     * @param amplitudes a (numTargets x numSlits) matrix of complex amplitudes
     */
    public boolean isNormalizedPerSlit(ComplexMatrices amplitudes)
    {
        if (amplitudes == null)
        {
            throw new IllegalArgumentException("the matrice cannot be null.");
        }

        // Small tolerance to safely compare doubles (avoids floating point errors)
        double tolerance = 1e-9;

        for (int slit = 0; slit < amplitudes.getCols(); slit++)
        {
            double sum = 0.0;

            for (int target = 0; target < amplitudes.getRows(); target++)
            {
                double mod = amplitudes.getElement(target, slit).modulus();
                sum += mod * mod;
            }

            if (Math.abs(sum - 1.0) > tolerance)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Builds the full transition matrix for the experiment.
     *
     * @param numSlits number of slits
     * @param numTargets number of targets
     * @param slitToTargetAmplitudes a (numTargets x numSlits) matrix where
     *        column i holds the complex amplitudes of a photon passing
     *        through slit i reaching each target. For each column, the
     *        squared moduli should sum to 1 — the caller can verify this
     *        with isNormalizedPerSlit.
     */
    public ComplexMatrices buildTransitionMatrix(int numSlits, int numTargets, ComplexMatrices slitToTargetAmplitudes)
    {
        if (numSlits <= 0 || numTargets <= 0)
        {
            throw new IllegalArgumentException("Number of slits and targets must be greater than zero.");
        }

        if (slitToTargetAmplitudes == null)
        {
            throw new IllegalArgumentException("Slit-to-target amplitude matrix cannot be null.");
        }

        if (slitToTargetAmplitudes.getRows() != numTargets || slitToTargetAmplitudes.getCols() != numSlits)
        {
            throw new IllegalArgumentException(
                "Slit-to-target matrix must be " + numTargets + "x" + numSlits +
                " (targets x slits), but was " + slitToTargetAmplitudes.getRows() +
                "x" + slitToTargetAmplitudes.getCols() + "."
            );
        }

        int size = 1 + numSlits + numTargets;
        ComplexMatrices transition = new ComplexMatrices(size, size);

        // Flashlight -> each slit, with equal amplitude. It's 1/sqrt(numSlits)
        // and NOT 1/numSlits: the squared modulus of each amplitude is then
        // 1/numSlits, so the probabilities sum to 1 and the state keeps norm 1.
        double flashlightToSlitAmplitude = 1.0 / Math.sqrt(numSlits);
        for (int slit = 1; slit <= numSlits; slit++)
        {
            transition.setElement(slit, 0, new ComplexNumber(flashlightToSlitAmplitude, 0));
        }

        // Each slit -> each target, using the caller-supplied amplitudes.
        for (int slit = 0; slit < numSlits; slit++)
        {
            int slitVertex = slit + 1;

            for (int target = 0; target < numTargets; target++)
            {
                int targetVertex = 1 + numSlits + target;
                ComplexNumber amplitude = slitToTargetAmplitudes.getElement(target, slit);
                transition.setElement(targetVertex, slitVertex, amplitude);
            }
        }

        // Once a photon reaches a target, it stays there (absorbing state).
        for (int target = 0; target < numTargets; target++)
        {
            int targetVertex = 1 + numSlits + target;
            transition.setElement(targetVertex, targetVertex, new ComplexNumber(1, 0));
        }

        return transition;
    }

    /**
     * Creates the initial state vector for the experiment: the photon
     * always starts at the flashlight (vertex 0) with certainty.
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