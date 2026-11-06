package de.kaufdort.moinmetrics.service;


import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Service
public class TimerService {
    private long startTime;

    public synchronized void startTimer() {
        startTime = System.nanoTime();
    }

    public synchronized long getElapsedTime() {
        if (startTime == 0) {
            return 0;
        }
        return (System.nanoTime() - startTime) / 1_000_000;
    }


}
