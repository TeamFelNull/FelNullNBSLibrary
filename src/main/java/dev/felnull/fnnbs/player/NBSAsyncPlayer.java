package dev.felnull.fnnbs.player;

import dev.felnull.fnnbs.NBS;
import dev.felnull.fnnbs.impl.player.NBSAsyncPlayerImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;

public interface NBSAsyncPlayer extends NBSPlayer {
    static NBSAsyncPlayer newNBSAsyncPlayer(@NotNull NBS nbs, @NotNull NBSRinger ringer, boolean loop) {
        return new NBSAsyncPlayerImpl(nbs, ringer, loop, null);
    }

    static NBSAsyncPlayer newNBSAsyncPlayer(@NotNull NBS nbs, @NotNull NBSRinger ringer, boolean loop, @Nullable Executor executor) {
        return new NBSAsyncPlayerImpl(nbs, ringer, loop, executor);
    }

    void start();

    void stop();

    boolean isPlaying();
}
