import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Ask for and validate the number of processes (Must be 3-10)
        int n = 0;
        while (true) {
            System.out.print("Enter the no. of process (3-10): ");
            n = scanner.nextInt();
            if (n >= 3 && n <= 10) {
                break;
            }
            System.out.println("Invalid input. Please enter a number between 3 and 10.");
        }
        
        // Arrays to store the process details
        String[] processID = new String[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        
        // 2. Inputing the process details and validate uniqueness (like a Primary Key)
        for (int i = 0; i < n; i++) {
            
            boolean stopEarly = false; // Flag to check if we are stopping the loop early
            
            // Validate unique Process ID (Acts as a Primary Key for each process ofc)
            while (true) {
                // Dynamically change the prompt text to show STOP/CANCEL options so the user knows they can stop or cancel at any time frfr
                if (i >= 3) {
                    System.out.print("Enter the process ID for Process" + (i + 1) + " (or type STOP/CANCEL): ");
                } else if (i > 0) {
                    System.out.print("Enter the process ID for Process" + (i + 1) + " (or type CANCEL): ");
                } else {
                    System.out.print("Enter the process ID for Process" + (i + 1) + ": ");
                }
                
                String id = scanner.next();
                
                // THEE NEW FEATURE: STOP and CANCEL handled right at the ID prompt
                if (id.equalsIgnoreCase("CANCEL")) {
                    while (true) {
                        System.out.print("Are you sure you want to cancel the program entirely? (Y/N): ");
                        String confirm = scanner.next().toLowerCase();
                        
                        if (confirm.equals("y") || confirm.equals("yes")) {
                            System.out.println("Processing cancelled. Goodbye :D");
                            scanner.close();
                            return; // Exits the entire program immediately
                        } else if (confirm.equals("n") || confirm.equals("no")) {
                            break; // Breaks out of the confirmation loop, does not cancel
                        } else {
                            System.out.println("Warning: Invalid input. Please type 'Y', 'Yes', 'N', or 'No'.");
                        }
                    }
                    continue; // Loops back to ask for the Process ID again if the user does not confirm cancellation
                }
                
                if (id.equalsIgnoreCase("STOP")) {
                    if (i < 3) {
                        System.out.println("Requirement check: You need at least 3 processes before stopping.");
                        continue; // Loops back to ask for the Process ID again
                    }
                    
                    boolean confirmedStop = false;
                    while (true) {
                        System.out.print("Are you sure you want to stop adding and calculate now? (Y/N): ");
                        String confirm = scanner.next().toLowerCase();
                        
                        if (confirm.equals("y") || confirm.equals("yes")) {
                            n = i; // Shrink the active process count
                            stopEarly = true;
                            confirmedStop = true;
                            break; // Breaks out of the confirmation loop
                        } else if (confirm.equals("n") || confirm.equals("no")) {
                            break; // Breaks out of the confirmation loop, does not stop
                        } else {
                            System.out.println("Warning: Invalid input. Please type 'Y', 'Yes', 'N', or 'No'.");
                        }
                    }
                    
                    if (confirmedStop) {
                        break; // Breaks out of the ID validation while-loop to calculate it
                    } else {
                        continue; // Loops back to ask for the Process ID again
                    }
                }
                
                // Check Uniqueness
                boolean isUnique = true;
                for (int j = 0; j < i; j++) {
                    if (processID[j] != null && processID[j].equals(id)) {
                        isUnique = false;
                        break;
                    }
                }
                
                if (isUnique) {
                    processID[i] = id;
                    break;
                }
                System.out.println("Process ID must be unique. Try again.");
            }
            
            // If the user successfully confirmed a STOP, break out of the main input loop entirely
            if (stopEarly) {
                System.out.println("Stopping input early. Moving to calculations for " + n + " processes...\n");
                break; 
            }
            
            // Validate the unique Arrival Time
            while (true) {
                System.out.print("Enter the arrival time for " + processID[i] + ": ");
                int at = scanner.nextInt();
                boolean isUnique = true;
                
                for (int j = 0; j < i; j++) {
                    if (arrivalTime[j] == at) {
                        isUnique = false;
                        break;
                    }
                }
                
                if (isUnique) {
                    arrivalTime[i] = at;
                    break;
                }
                System.out.println("Arrival time must be unique. Try again.");
            }
            
            // Input Burst Time
            System.out.print("Enter burst time for " + processID[i] + ": ");
            burstTime[i] = scanner.nextInt();
            System.out.println("-----------------------------------------");
        }
        
        // 3. Sort processes by Arrival Time (Required for First Come First Serve or aka FIFO)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrivalTime[j] > arrivalTime[j + 1]) {
                    // Swapping Arrival Time
                    int tempAT = arrivalTime[j];
                    arrivalTime[j] = arrivalTime[j + 1];
                    arrivalTime[j + 1] = tempAT;
                    
                    // Swapapping Burst Time
                    int tempBT = burstTime[j];
                    burstTime[j] = burstTime[j + 1];
                    burstTime[j + 1] = tempBT;
                    
                    // Swapping Process ID
                    String tempID = processID[j];
                    processID[j] = processID[j + 1];
                    processID[j + 1] = tempID;
                }
            }
        }
        
        // 4. Calculate Waiting Time and Turn-Around Time using whiteboard formulas
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        
        int currentTime = 0; // This will track our Start Time (ST) and End Time (ET)
        double totalWaitingTime = 0;
        double totalTurnAroundTime = 0;
        
        for (int i = 0; i < n; i++) {
            // If the CPU is idle waiting for the next process, jump time forward
            if (currentTime < arrivalTime[i]) {
                currentTime = arrivalTime[i];
            }
            
            int startTime = currentTime; // ST
            
            // Formula: WT = ST - AT
            waitingTime[i] = startTime - arrivalTime[i]; 
            
            int endTime = startTime + burstTime[i]; // ET
            
            // Formula: TAT = ET - AT
            turnAroundTime[i] = endTime - arrivalTime[i]; 
            
            // CPU moves forward to end of current process
            currentTime = endTime; 
            
            // Add to totals for finding averages later
            totalWaitingTime += waitingTime[i];
            totalTurnAroundTime += turnAroundTime[i];
        }
        
        // 5. Output the results
        System.out.println("\n-----------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("Process " + processID[i] + ": Waiting Time = " + waitingTime[i] + ", Turn-Around Time = " + turnAroundTime[i]); // THE RESULTS
        }
        
        System.out.println("\nAverage waiting time: " + (totalWaitingTime / n)); // THE AVERAGE RESULTS FOR WAITING TIME
        System.out.println("Average turn-around time: " + (totalTurnAroundTime / n)); // THE AVERAGE RESULTS FOR TURN-AROUND TIME
        
        // 6. Gantt chart output
        System.out.println("\n-----------------------------------------");
        System.out.println("Gantt Chart:");

        String topStr = "";
        String bottomStr = "";
        int timeTrack = 0;

        for (int i = 0; i < n; i++) { // IF the CPU is idle waiting for the next process
            if (timeTrack < arrivalTime[i]) {
                topStr += "| Idle ";
                bottomStr += timeTrack + "     ";
                int spaces = 9 - String.valueOf(timeTrack).length();
                for (int s = 0; s < spaces; s++) {
                    bottomStr += " ";
                }
                timeTrack = arrivalTime[i];
            }

            // Process execution
            topStr += "| " + processID[i] + " ";
            bottomStr += timeTrack + "     ";

            // Calclate spaces based on Process ID length and time length to keep the Gantt chart aligned
            int spaces = 7 +  processID[i].length() - String.valueOf(timeTrack).length();
            for (int s = 0; s < spaces; s++) {
                bottomStr += " ";
            }
            
            timeTrack += burstTime[i];
        }

        topStr += "|";
        bottomStr += timeTrack; // Add the final time at the end of the Gantt chart

        System.out.println(topStr);
        System.out.println(bottomStr);
        System.out.println("-----------------------------------------\\n");
        
        scanner.close();
    }
}