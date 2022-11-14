package dev.felnull.fnnbs.player;

import dev.felnull.fnnbs.NBS;
import dev.felnull.fnnbs.impl.player.NBSPlayerImpl;
import org.jetbrains.annotations.NotNull;

public interface NBSPlayer {
    static NBSPlayer newNBSPlayer(@NotNull NBS nbs, @NotNull NBSRinger ringer) {
        return new NBSPlayerImpl(nbs, ringer, false);
    }

    static NBSPlayer newNBSPlayer(@NotNull NBS nbs, @NotNull NBSRinger ringer, boolean loop) {
        return new NBSPlayerImpl(nbs, ringer, loop);
    }

    /**
     * 1 tick process
     *
     * @return True if there is a next step
     */
    boolean step();

    void setTick(int tick);
}
