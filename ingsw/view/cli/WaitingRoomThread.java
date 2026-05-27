package it.polimi.ingsw.view.cli;

public class WaitingRoomThread implements Runnable {

    CLI cli;
    String message;
    long waitingTime;


    public WaitingRoomThread(String message, CLI cli, long waitingTime) {
        this.message = message;
        this.waitingTime = waitingTime;
        this.cli = cli;
    }


    @Override
    public void run() {
        long t = System.currentTimeMillis();
        long end = t + waitingTime * 1000;
        cli.outln("");
        while (System.currentTimeMillis() < end && !cli.getWaitingRoomInterrupt()) {
            cli.typewriterOut("\r" + message + "\r", 30);
        }
        cli.out("\r");
    }

}
