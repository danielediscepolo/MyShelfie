package it.polimi.ingsw.view.gui;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;


/**
 * The static method runAndWait allows a function to be scheduled for execution within the javafx thread owner.
 * The method blocks the execution of the calling thread until this execution occurs, thus allowing the sequential
 * paradigm to be maintained.
 */
public class CallbackRunner {

    public static <T> T runAndWait(Supplier<T> callback)  {
        if (Platform.isFxApplicationThread()) {
            // If we're already on the JavaFX application thread, execute the callback directly
            return callback.get();
        } else {
            // Create a CountDownLatch to wait for the callback to finish
            CountDownLatch latch = new CountDownLatch(1);
            // Use an AtomicReference to store the return value of the callback
            AtomicReference<T> result = new AtomicReference<>();

            // Run the callback on the JavaFX application thread
            Platform.runLater(() -> {
                try {
                    // Execute the callback and store its return value
                    result.set(callback.get());
                } finally {
                    // Signal that the callback has finished executing
                    latch.countDown();
                }
            });

            // Wait for the callback to finish
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Return the callback's return value
            return result.get();
        }
    }
}