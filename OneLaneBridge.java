public class OneLaneBridge extends Bridge {

    private final int BRIDGE_LIMIT; // Amount of cars that can be on the bridge
    private Object bridgeFull = new Object(); // Condition variable to wait() on

    /**
     * Instantiate a new OneLaneBridge object
     * 
     * @param bridgeLimit   The amount of cars that can be on the bridge
     */
    public OneLaneBridge(int bridgeLimit) {
        BRIDGE_LIMIT = bridgeLimit; // Limit of cars on bridge
    }

    @Override
    public void arrive(Car car) throws InterruptedException {
        // Synchronize on the bridgeFull object
        synchronized (bridgeFull) {
            try {
                // While the size of the bridge == the amount of cars on the bridge
                // While car's direction != the direction of the bridge
                // ... cars are on the bridge already
                while (this.bridge.size() == BRIDGE_LIMIT || car.getDirection() != this.direction) {
                    // wait() on the bridgeFull object
                    bridgeFull.wait();
                }
            } catch (InterruptedException e) {
                // Print the stacktrace of the exception
                e.printStackTrace();
            }
            
            // Set the car's time to the current time
            car.setEntryTime(this.currentTime);
            
            // Increment the current time
            this.currentTime++;
            
            // Add the car to the bridge
            this.bridge.add(car);

            // Print
            System.out.println("Bridge (dir=" + this.direction + "): " + this.bridge);
        }
    }

    @Override
    public void exit(Car car) throws InterruptedException {
        // Synchronize on the bridgeFull object
        synchronized (bridgeFull) {
            
            // If the first car on the bridge is equal to the car passed...
            if (this.bridge.get(0).equals(car)) {
                
                // Remove the car from the bridge
                this.bridge.remove(0);
                
                // Print
                System.out.println("Bridge (dir=" + this.direction + "): " + this.bridge);
                
                // If the bridge size is less than the amount of cars that can be on the bridge
                if (this.bridge.size() < BRIDGE_LIMIT) {
                    // Notify all threads
                    bridgeFull.notifyAll();
                }
            } 
            
            // If the car passed isn't the first one on the bridge
            else {
                // wait() on bridgeFull
                while (!this.bridge.get(0).equals(car)) {
                    bridgeFull.wait();
                }
                
                // Remove the first car in line
                this.bridge.remove(0);
                
                // Print
                System.out.println("Bridge (dir=" + this.direction + "): " + this.bridge);

                // If the bridge size is less than the amount of cars that can be on the bridge
                if (this.bridge.size() < BRIDGE_LIMIT) {
                    // Notify all threads
                    bridgeFull.notifyAll();
                }
            }
        }
        
        // If there are no cars on the bridge...
        if (this.bridge.isEmpty()) {
            // Change direction
            this.direction = !this.direction;
        }
    }
}
