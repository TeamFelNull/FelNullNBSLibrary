package dev.felnull.fnnbs;

import dev.felnull.fnnbs.instrument.IInstrument;
import dev.felnull.fnnbs.instrument.VanillaInstrument;

import java.util.Objects;

public class InstrumentNote extends Note {
    private IInstrument instrument;

    public InstrumentNote(NBS nbs, Note note) {
        this(nbs, note.getInstrument(), note.getKey(), note.getVelocity(), note.getPanning(), note.getPitch());
    }

    public InstrumentNote(NBS nbs, int instrument, int key, int velocity, int panning, int pitch) {
        super(instrument, key, velocity, panning, pitch);
        this.instrument = getInstrument(nbs, instrument);
    }

    public IInstrument getNoteInstrument() {
        return instrument;
    }

    private static IInstrument getInstrument(NBS nbs, int id) {
        if (nbs.getVanillaInstrumentCount() > id && VanillaInstrument.values().length > id)
            return VanillaInstrument.values()[id];
        int cc = id - nbs.getVanillaInstrumentCount();
        return nbs.getCustomInstruments().get(cc);
    }

    @Override
    public String toString() {
        return "InstrumentNote{" +
                "instrument=" + instrument +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InstrumentNote that = (InstrumentNote) o;
        return Objects.equals(instrument, that.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), instrument);
    }
}
