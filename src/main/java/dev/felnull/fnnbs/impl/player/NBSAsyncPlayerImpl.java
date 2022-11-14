package dev.felnull.fnnbs.impl.player;

import dev.felnull.fnnbs.NBS;
import dev.felnull.fnnbs.player.NBSAsyncPlayer;
import dev.felnull.fnnbs.player.NBSRinger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class NBSAsyncPlayerImpl extends NBSPlayerImpl implements NBSAsyncPlayer {
    private final Executor executor;
    private final AtomicBoolean playing = new AtomicBoolean();

    public NBSAsyncPlayerImpl(NBS nbs, NBSRinger ringer, boolean loop, Executor executor) {
        super(nbs, ringer, loop);
        this.executor = executor;
    }

    @Override
    public void start() {
        if (playing.getAndSet(true))
            return;

        if (executor != null) {
            CompletableFuture.runAsync(this::runPlayer, executor);
        } else {
            CompletableFuture.runAsync(this::runPlayer);
        }
    }

    @Override
    public void stop() {
        playing.set(false);
    }

    @Override
    public boolean isPlaying() {
        return playing.get();
    }

    private void runPlayer() {
        while (isPlaying() && nbs.getSongLength() > tick.get()) {
            if (!step())
                break;

            long tickSpeed = (long) (1000f / ((float) nbs.getTempo() / 100f));
            try {
                Thread.sleep(tickSpeed);
            } catch (InterruptedException e) {
                break;
            }
        }

        playing.set(false);
    }
}
