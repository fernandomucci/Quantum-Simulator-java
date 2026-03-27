# Complex-Math-Engine
Complex Number Calculator in Java 🧮⚛️

A robust terminal-based calculator built in Java to perform fundamental operations and geometric conversions with complex numbers. 

This project was developed not only as a mathematical tool, but as the **foundational layer for building a future Quantum Algorithm Simulator**, where complex numbers and polar representations (quantum phases) are essential.

## 🚀 Features

The system features an interactive menu and handles parsing text expressions, extracting values, and exception handling (such as division by zero and smart root formatting).

**Binary Operations:**
* Addition and Subtraction.
* Multiplication.
* Division.

**Unary Operations and Geometry:**
* Modulus Calculation (with smart root formatting, e.g., `√2`).
* Conjugate Calculation.
* **Conversion to Polar:** Transforms `a + bi` into Radius and Angle (θ).
* **Conversion to Cartesian:** Transforms Polar Coordinates into `a + bi`.

**Technical Highlight:**
* **Custom Pi Parser:** The calculator features a syntax analyzer (parser) capable of interpreting natural trigonometric user inputs, such as `pi/3`, `-pi/2`, or `2pi/3`, converting them directly into radians.