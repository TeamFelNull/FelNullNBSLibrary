package dev.felnull.fnnbs;

import dev.felnull.fnnbs.instrument.CustomInstrument;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NBS {
    private boolean old;
    private int version;
    private int vanillaInstrumentCount;
    private int length;
    private String name;
    private String author;
    private String originalAuthor;
    private String description;
    private int tempo;
    private boolean autoSaving;
    private int autoSavingDuration;
    private int timeSignature;
    private int minutesSpent;
    private int leftClicks;
    private int rightClicks;
    private int noteBlocksAdded;
    private int noteBlocksRemoved;
    private String importFileName;
    private boolean loop;
    private int loopCount;
    private int loopStart;
    private final List<Layer> layers = new ArrayList<>();
    private final List<CustomInstrument> customInstruments = new ArrayList<>();

    public NBS() {

    }

    public NBS(InputStream stream) throws IOException {
        int fl = readShort(stream);
        this.old = fl != 0;
        if (old) {
            this.length = fl;
        } else {
            this.version = stream.read();
            this.vanillaInstrumentCount = stream.read();
            this.length = readShort(stream);
        }
        int layerCount = readShort(stream);
        for (int i = 0; i < layerCount; i++) {
            layers.add(new Layer());
        }
        this.name = readString(stream);
        this.author = readString(stream);
        this.originalAuthor = readString(stream);
        this.description = readString(stream);
        this.tempo = readShort(stream);
        this.autoSaving = readBoolean(stream);
        this.autoSavingDuration = stream.read();
        this.timeSignature = stream.read();
        this.minutesSpent = readInt(stream);
        this.leftClicks = readInt(stream);
        this.rightClicks = readInt(stream);
        this.noteBlocksAdded = readInt(stream);
        this.noteBlocksRemoved = readInt(stream);
        this.importFileName = readString(stream);
        if (!old) {
            this.loop = readBoolean(stream);
            this.loopCount = stream.read();
            this.loopStart = readShort(stream);
        }

        int tick = -1;
        int nextJT = readShort(stream);
        while (nextJT != 0) {
            tick += nextJT;
            int layer = -1;
            int nextJL = readShort(stream);
            while (nextJL != 0) {
                layer += nextJL;
                int instrument = stream.read();
                int key = stream.read();
                int velocity = old ? 100 : stream.read();
                int panning = old ? 100 : stream.read();
                int pitch = old ? 0 : readSignedShort(stream);
                Note note = new Note(instrument, key, velocity, panning, pitch);
                layers.get(layer).addNote(tick, note);
                nextJL = readShort(stream);
            }
            nextJT = readShort(stream);
        }

        for (int i = 0; i < getLayerCount(); i++) {
            layers.get(i).setName(readString(stream));
            layers.get(i).setLock(readBoolean(stream));
            layers.get(i).setVolume(stream.read());
            layers.get(i).setStereo(stream.read());
        }

        int cic = stream.read();
        if (cic != -1) {
            for (int i = 0; i < cic; i++) {
                customInstruments.add(new CustomInstrument(readString(stream), readString(stream), stream.read(), readBoolean(stream)));
            }


            for (Layer layer : layers) {
                Map<Integer, Note> notes = layer.getNotes();
                notes.replaceAll((k, v) -> new InstrumentNote(this, v));
            }

        }
    }

    private int readSignedShort(InputStream stream) throws IOException {
        return stream.read() + (stream.read() << 8);
    }

    private boolean readBoolean(InputStream stream) throws IOException {
        return stream.read() >= 1;
    }

    private String readString(InputStream stream) throws IOException {
        byte[] dat = new byte[readInt(stream)];
        stream.read(dat);
        return new String(dat, StandardCharsets.UTF_8);
    }

    private int readInt(InputStream stream) throws IOException {
        return stream.read() + (stream.read() << 8) + (stream.read() << 16) + (stream.read() << 24);
    }

    private int readShort(InputStream stream) throws IOException {
        return (stream.read()) + (stream.read() << 8);
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getAutoSavingDuration() {
        return autoSavingDuration;
    }

    public int getLayerCount() {
        return layers.size();
    }

    public int getLeftClicks() {
        return leftClicks;
    }

    public int getLength() {
        return length + 1;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public int getLoopStart() {
        return loopStart;
    }

    public int getMinutesSpent() {
        return minutesSpent;
    }

    public int getNoteBlocksAdded() {
        return noteBlocksAdded;
    }

    public int getNoteBlocksRemoved() {
        return noteBlocksRemoved;
    }

    public int getRightClicks() {
        return rightClicks;
    }

    public int getTempo() {
        return tempo;
    }

    public int getTimeSignature() {
        return timeSignature;
    }

    public int getVanillaInstrumentCount() {
        return vanillaInstrumentCount;
    }

    public int getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getImportFileName() {
        return importFileName;
    }

    public String getOriginalAuthor() {
        return originalAuthor;
    }

    public boolean isAutoSaving() {
        return autoSaving;
    }

    public boolean isLoop() {
        return loop;
    }

    public boolean isOld() {
        return old;
    }

    public float getRawTempo() {
        return (float) getTempo() / 100f;
    }

    public long getTicksPerSecond() {
        return (long) (1000f / getRawTempo());
    }

    public long getLoopStartSecond() {
        return (long) 1000 / 20 * getLoopStart();
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAutoSaving(boolean autoSaving) {
        this.autoSaving = autoSaving;
    }

    public void setAutoSavingDuration(int autoSavingDuration) {
        this.autoSavingDuration = autoSavingDuration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImportFileName(String importFileName) {
        this.importFileName = importFileName;
    }

    public void setLeftClicks(int leftClicks) {
        this.leftClicks = leftClicks;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public void setLoopStart(int loopStart) {
        this.loopStart = loopStart;
    }

    public void setMinutesSpent(int minutesSpent) {
        this.minutesSpent = minutesSpent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoteBlocksAdded(int noteBlocksAdded) {
        this.noteBlocksAdded = noteBlocksAdded;
    }

    public void setNoteBlocksRemoved(int noteBlocksRemoved) {
        this.noteBlocksRemoved = noteBlocksRemoved;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public void setOriginalAuthor(String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    public void setRightClicks(int rightClicks) {
        this.rightClicks = rightClicks;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void setTimeSignature(int timeSignature) {
        this.timeSignature = timeSignature;
    }

    public void setVanillaInstrumentCount(int vanillaInstrumentCount) {
        this.vanillaInstrumentCount = vanillaInstrumentCount;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Layer getLayer(int num) {
        return layers.get(num);
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public int getCustomInstrumentCount() {
        return customInstruments.size();
    }

    public List<CustomInstrument> getCustomInstruments() {
        return customInstruments;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    /*public InputStream write() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        writeShort(stream, 0);
        stream.write(version);
        stream.write(vanillaInstrumentCount);
        writeShort(stream, length);
        writeShort(stream, getLayerCount());
        writeString(stream, name);
        writeString(stream, author);
        writeString(stream, originalAuthor);
        writeString(stream, description);
        writeShort(stream, tempo);
        writeBoolean(stream, autoSaving);
        stream.write(autoSavingDuration);
        stream.write(timeSignature);
        writeInt(stream, minutesSpent);
        writeInt(stream, leftClicks);
        writeInt(stream, rightClicks);
        writeInt(stream, noteBlocksAdded);
        writeInt(stream, noteBlocksRemoved);
        writeString(stream, importFileName);
        writeBoolean(stream, loop);
        stream.write(loopCount);
        writeShort(stream, loopStart);
        return new ByteArrayInputStream(stream.toByteArray());
    }

    private void writeInt(OutputStream stream, int val) throws IOException {
        stream.write(val & 0xFF);
        stream.write(val << 8 & 0xFF);
        stream.write(val << 16 & 0xFF);
        stream.write(val << 24 & 0xFF);
    }

    private void writeBoolean(OutputStream stream, boolean val) throws IOException {
        stream.write(val ? 1 : 0);
    }

    private void writeString(OutputStream stream, String val) throws IOException {
        byte[] bytes = val.getBytes(StandardCharsets.UTF_8);
        writeInt(stream, bytes.length);
        stream.write(bytes);
    }

    private void writeShort(OutputStream stream, int val) throws IOException {
        stream.write(val & 0xFF);
        stream.write(val << 8 & 0xFF);
        //  return (stream.read()) + (stream.read() << 8);
    }*/

    @Override
    public String toString() {
        return "NBS{" +
                "old=" + old +
                ", version=" + version +
                ", vanillaInstrumentCount=" + vanillaInstrumentCount +
                ", length=" + length +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", originalAuthor='" + originalAuthor + '\'' +
                ", description='" + description + '\'' +
                ", tempo=" + tempo +
                ", autoSaving=" + autoSaving +
                ", autoSavingDuration=" + autoSavingDuration +
                ", timeSignature=" + timeSignature +
                ", minutesSpent=" + minutesSpent +
                ", leftClicks=" + leftClicks +
                ", rightClicks=" + rightClicks +
                ", noteBlocksAdded=" + noteBlocksAdded +
                ", noteBlocksRemoved=" + noteBlocksRemoved +
                ", importFileName='" + importFileName + '\'' +
                ", loop=" + loop +
                ", loopCount=" + loopCount +
                ", loopStart=" + loopStart +
                ", layers=" + layers +
                ", customInstruments=" + customInstruments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NBS nbs = (NBS) o;
        return old == nbs.old && version == nbs.version && vanillaInstrumentCount == nbs.vanillaInstrumentCount && length == nbs.length && tempo == nbs.tempo && autoSaving == nbs.autoSaving && autoSavingDuration == nbs.autoSavingDuration && timeSignature == nbs.timeSignature && minutesSpent == nbs.minutesSpent && leftClicks == nbs.leftClicks && rightClicks == nbs.rightClicks && noteBlocksAdded == nbs.noteBlocksAdded && noteBlocksRemoved == nbs.noteBlocksRemoved && loop == nbs.loop && loopCount == nbs.loopCount && loopStart == nbs.loopStart && Objects.equals(name, nbs.name) && Objects.equals(author, nbs.author) && Objects.equals(originalAuthor, nbs.originalAuthor) && Objects.equals(description, nbs.description) && Objects.equals(importFileName, nbs.importFileName) && Objects.equals(layers, nbs.layers) && Objects.equals(customInstruments, nbs.customInstruments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(old, version, vanillaInstrumentCount, length, name, author, originalAuthor, description, tempo, autoSaving, autoSavingDuration, timeSignature, minutesSpent, leftClicks, rightClicks, noteBlocksAdded, noteBlocksRemoved, importFileName, loop, loopCount, loopStart, layers, customInstruments);
    }
}

