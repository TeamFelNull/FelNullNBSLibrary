package dev.felnull.fnnbs;

import java.util.Map;
import java.util.TreeMap;

public class Layer {
    private final Map<Integer, Note> notes = new TreeMap<>();
    private String name;
    private boolean lock;
    private int volume;
    private int stereo;

    public Layer() {
        this("", false, 100, 100);
    }

    public Layer(String name, boolean lock, int volume, int stereo) {
        this.name = name;
        this.lock = lock;
        this.volume = volume;
        this.stereo = stereo;
    }

    public int getStereo() {
        return stereo;
    }

    public int getVolume() {
        return volume;
    }

    public float getRawVolume() {
        return (float) volume / 100f;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public void setStereo(int stereo) {
        this.stereo = stereo;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNote(int tick, Note note) {
        notes.put(tick, note);
    }

    public Note getNote(int tick) {
        return notes.get(tick);
    }

    public Map<Integer, Note> getNotes() {
        return notes;
    }
}
