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
            
            // Validate unique Process ID (Acts as a Primary Key for each process ofc)
            while (true) {
                System.out.print("Enter process ID for Process" + (i + 1) + ": ");
                String id = scanner.next();
                boolean isUnique = true;
                
                for (int j = 0; j < i; j++) {
                    if (processID[j].equals(id)) {
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
            
            // Validate unique Arrival Time
            while (true) {
                System.out.print("Enter arrival time for " + processID[i] + ": ");
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
        }
        
        // 3. Sort processes by Arrival Time (Required for First Come First Serve)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrivalTime[j] > arrivalTime[j + 1]) {
                    // Swappin Arrival Time
                    int tempAT = arrivalTime[j];
                    arrivalTime[j] = arrivalTime[j + 1];
                    arrivalTime[j + 1] = tempAT;
                    
                    // Swapapping Burst Time
                    int tempBT = burstTime[j];
                    burstTime[j] = burstTime[j + 1];
                    burstTime[j + 1] = tempBT;
                    
                    // Swap Process ID
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
            System.out.println("Process " + processID[i] + ": Waiting Time = " + waitingTime[i] + ", Turn-Around Time = " + turnAroundTime[i]);
        }
        
        System.out.println("\nAverage waiting time: " + (totalWaitingTime / n));
        System.out.println("Average turn-around time: " + (totalTurnAroundTime / n));
        
        scanner.close();
    }
}