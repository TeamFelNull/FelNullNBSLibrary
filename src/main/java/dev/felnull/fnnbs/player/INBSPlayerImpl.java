package dev.felnull.fnnbs.player;

import dev.felnull.fnnbs.instrument.IInstrument;

public interface INBSPlayerImpl {
    public void play(IInstrument instrument, float volume, float pitch, float stereo);
}
