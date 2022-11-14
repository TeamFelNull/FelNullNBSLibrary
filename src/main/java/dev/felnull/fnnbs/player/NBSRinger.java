package dev.felnull.fnnbs.player;

import dev.felnull.fnnbs.Layer;
import dev.felnull.fnnbs.NBS;
import dev.felnull.fnnbs.Note;
import dev.felnull.fnnbs.instrument.Instrument;
import org.jetbrains.annotations.NotNull;

public interface NBSRinger {
    default void ring(NBS nbs, Layer layer, Note note) {
        Instrument instrument = note.getInstrument(nbs);
        float vol = note.getRawVelocity() * layer.getRawVolume();
        float dfkey = note.getInstrument(nbs).getDefaultPitch() - instrument.getDefaultPitch();
        float pitch = (float) Math.pow(2.0f, (double) (note.getKey() - instrument.getDefaultPitch() + dfkey) / 12.0f);
        float pan = ((float) layer.getStereo() / 100f) - 1f;
        ring(instrument, vol, pitch, pan);
    }

    void ring(@NotNull Instrument instrument, float volume, float pitch, float pan);
}
