package dev.felnull.fnnbs.player;

import dev.felnull.fnnbs.InstrumentNote;
import dev.felnull.fnnbs.Layer;
import dev.felnull.fnnbs.NBS;
import dev.felnull.fnnbs.Note;

import java.util.List;

public class NBSPlayer {
    private final NBS nbs;
    private final INBSPlayerImpl impl;
    private int tick;
    private boolean loop;
    private boolean forcedLoop;
    private int maxLoop = -1;
    private int currentLoop;

    public NBSPlayer(NBS nbs, INBSPlayerImpl impl) {
        this.nbs = nbs;
        this.impl = impl;
    }

    public NBS getNBS() {
        return nbs;
    }

    public boolean tick() {
        if (getNBS().getLength() <= tick)
            return false;
        List<Layer> layers = nbs.getLayers();
        for (Layer layer : layers) {
            Note note = layer.getNote(tick);
            if (note != null) {
                if (note instanceof InstrumentNote) {
                    InstrumentNote in = (InstrumentNote) note;
                    float vol = in.getRawVelocity() * layer.getRawVolume();
                    float dfkey = in.getNoteInstrument().getDefaultPitch() - 45f;
                    float pitch = (float) Math.pow(2.0f, (double) (in.getKey() - 45 + dfkey) / 12.0f);
                    float stereo = ((float) layer.getStereo() / 100f) - 1f;
                    impl.play(in.getNoteInstrument(), vol, pitch, stereo);
                }
            }
        }
        tick++;
        int lc = maxLoop < 0 ? Integer.MAX_VALUE : maxLoop;
        if (getNBS().getLength() <= tick && (loop && getNBS().isLoop() || forcedLoop) && currentLoop < lc) {
            tick = getNBS().getLoopStart();
            currentLoop++;
        }
        return true;
    }

    public void setMaxLoopCount(int maxLoop) {
        this.maxLoop = maxLoop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setForcedLoop(boolean forcedLoop) {
        this.forcedLoop = forcedLoop;
    }
}
