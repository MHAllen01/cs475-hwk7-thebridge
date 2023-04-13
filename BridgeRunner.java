/**
 * Runs all threads
 */

public class BridgeRunner {

    public static void main(String[] args) {

        // TODO - check command line inputs

        // TODO - instantiate the bridge

        // TODO - allocate space for threads

        // TODO - start then join the threads

        // Try block to make sure the user input are integers
        try {
            // Gets the bridge limit from the command line
            int bridgeLimit = Integer.parseInt(args[0]);

            // Gets the car amount from the command line
            int carAmount = Integer.parseInt(args[1]);

            if (bridgeLimit > 0 && carAmount > 0) {
                // Valid user input

                // Instantiate a new bridge object to be used by the threads
                OneLaneBridge bridge = new OneLaneBridge(bridgeLimit);

                // Create the threads
                Thread[] cars = new Thread[carAmount];
                for (int i = 0; i<carAmount; i++) {
                    cars[i] = new Thread(new Car(i, bridge));
                    cars[i].start();
                }

                // Join the threads
                for (int i = 0; i < carAmount; i++) {
                    try {
                        cars[i].join();
                    } catch (InterruptedException ignored) {
                        // Ignore the exception thrown
                    }
                }

                System.out.println("All cars have crossed!!");
            } else {
                // User input was not positive
                System.err.println("Error: bridge limit and/or num cars must be positive.");
            }
        } catch (Exception ignored) {
            // User input was not a valid integer, ignore the exception
            System.err.println("Usage: javac BridgeRunner <bridge limit> <num cars>");
        }
    }

}