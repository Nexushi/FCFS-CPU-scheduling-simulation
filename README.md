# FCFS CPU Scheduling Simulation

A Java-based simulation of the **First Come First Serve (FCFS)** non-preemptive CPU scheduling algorithm. This program accepts process inputs and calculates both the individual and average Waiting Time (WT) and Turn-Around Time (TAT).

## Features
* **Strict Input Validation:** Ensures all Process IDs and Arrival Times are completely unique (acting as primary keys).
* **Dynamic Process Limits:** Enforces a strict requirement of 3 to 10 processes.
* **Early Exit Control:** Includes custom `STOP` and `CANCEL` commands right at the input prompt, allowing users to stop inputs early or cancel the program with explicit Yes/No options.
* **FIFO Sorting:** Automatically sorts all processes by their arrival time before crunching the CPU calculations.

## How to Run
1. Open your terminal in the project directory.

2. Compile the Java file:
    ```bash
    javac FCFS.java

3. Run the compiled program
    ```bash
    java FCFS