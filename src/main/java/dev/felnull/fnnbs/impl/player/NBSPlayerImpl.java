package dev.felnull.fnnbs.impl.player;

import dev.felnull.fnnbs.Layer;
import dev.felnull.fnnbs.NBS;
import dev.felnull.fnnbs.Note;
import dev.felnull.fnnbs.player.NBSPlayer;
import dev.felnull.fnnbs.player.NBSRinger;

import java.util.concurrent.atomic.AtomicInteger;

public class NBSPlayerImpl implements NBSPlayer {
    protected final NBS nbs;
    private final NBSRinger ringer;
    private final boolean loop;
    protected AtomicInteger tick = new AtomicInteger();

    public NBSPlayerImpl(NBS nbs, NBSRinger ringer, boolean loop) {
        this.nbs = nbs;
        this.ringer = ringer;
        this.loop = loop;
    }

    @Override
    public boolean step() {
        if (tick.get() < 0 || nbs.getSongLength() <= tick.get())
            return false;

        for (int i = 0; i < nbs.getLayerCount(); i++) {
            Layer layer = nbs.getLayer(i);
            Note note = layer.getNote(tick.get());
            if (note != null)
                ringer.ring(nbs, layer, note);
        }

        tick.incrementAndGet();

        if (loop && nbs.getSongLength() <= tick.get())
            tick.set(nbs.isLoop() ? nbs.getLoopStart() : 0);

        return nbs.getSongLength() >= tick.get();
    }

    @Override
    public void setTick(int tick) {
        this.tick.set(tick);
    }
}
