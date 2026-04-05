Quantum Computing in Java: A Pedagogical Framework ⚛️💻
This project is a practical, step-by-step implementation of the concepts from the book "Quantum Computing for Computer Science" (by Noson S. Yanofsky and Mirco A. Mannucci).

Rather than just solving isolated exercises, the goal of this repository is to apply Software Engineering principles to build a cohesive, Object-Oriented mathematical framework. Ultimately, these foundational layers will serve as the engine for a functional Quantum State Vector Simulator.

🗺️ Project Roadmap
This project is continuously evolving as I progress through the book's chapters. Here is the current status:

[x] Phase 1: The Math Engine (Complex Numbers) - Fundamental operations (Addition, Subtraction, Multiplication, Division).

Geometric conversions (Modulus, Conjugate, Polar/Cartesian representations).

Custom parser for trigonometric inputs (e.g., pi/3).

[x] Phase 2: Complex Vectors (State Vectors) - Object-oriented structure using composition to represent multiple quantum states (Qubits).

[ ] Phase 3: Complex Matrices & Tensor Products - Next Step

[ ] Phase 4: Quantum Gates (Pauli, Hadamard, CNOT, etc.)

[ ] Phase 5: Simulating Quantum Algorithms

🧮 Current Module: The Complex Math Engine & Testing Environment
Currently, the mathematical foundation is fully operational. It features an interactive terminal interface (ComplexCalculator) that serves as a testing ground for the underlying math engine.

🏗️ Core Architecture
The system is designed with a clean, modular Object-Oriented approach, prioritizing performance and encapsulation:

ComplexNumber: The core mathematical engine. An immutable class handling all pure arithmetic and geometric math without any UI bottlenecks.

ComplexVector: A composition-based data structure designed to represent state vectors (e.g., Qubits) for quantum simulations.

ComplexCalculator: The interactive terminal interface that parses user input and bridges it to the engine.

🚀 Features of the Testing Environment
Robust Parsing: Handles text expressions, extracts numerical values, and manages algebraic exceptions (such as division by zero).

Custom Pi Parser: A custom syntax analyzer capable of interpreting natural trigonometric user inputs, such as pi/3, -pi/2, or 2pi/3, converting them directly into radians.

Smart Formatting: Visual representation of square roots for better readability (e.g., printing √2 instead of 1.4142...).
