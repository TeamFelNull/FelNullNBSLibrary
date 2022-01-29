package dev.felnull.fnnbs;

import java.util.Objects;

public class Note {
    private int instrument;
    private int key;
    private int velocity;
    private int panning;
    private int pitch;

    public Note(int instrument, int key, int velocity, int panning, int pitch) {
        this.instrument = instrument;
        this.key = key;
        this.velocity = velocity;
        this.panning = panning;
        this.pitch = pitch;
    }

    public int getInstrument() {
        return instrument;
    }

    public int getKey() {
        return key;
    }

    public int getPanning() {
        return panning;
    }

    public int getPitch() {
        return pitch;
    }

    public int getVelocity() {
        return velocity;
    }

    public float getRawVelocity() {
        return (float) velocity / 100f;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setPanning(int panning) {
        this.panning = panning;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "Note{" +
                "instrument=" + instrument +
                ", key=" + key +
                ", velocity=" + velocity +
                ", panning=" + panning +
                ", pitch=" + pitch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return instrument == note.instrument && key == note.key && velocity == note.velocity && panning == note.panning && pitch == note.pitch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, key, velocity, panning, pitch);
    }
}
