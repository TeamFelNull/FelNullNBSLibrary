package dev.felnull.fnnbs.impl.instrument;


import dev.felnull.fnnbs.instrument.CustomInstrument;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomInstrumentImpl implements CustomInstrument {
    private final String name;
    private final String fileName;
    private final int pitch;
    private final boolean pressKeys;

    public CustomInstrumentImpl(String name, String fileName, int pitch, boolean autoPressKeys) {
        this.name = name;
        this.fileName = fileName;
        this.pitch = pitch;
        this.pressKeys = autoPressKeys;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean isPressKeys() {
        return pressKeys;
    }

    @Override
    public @NotNull String getSoundName() {
        return name;
    }

    @Override
    public boolean isVanillaNote() {
        return false;
    }

    @Override
    public float getDefaultPitch() {
        return pitch;
    }

    @Override
    public String toString() {
        return "CustomInstrumentImpl{" +
                "name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", pitch=" + pitch +
                ", pressKeys=" + pressKeys +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomInstrumentImpl that = (CustomInstrumentImpl) o;
        return pitch == that.pitch && pressKeys == that.pressKeys && Objects.equals(name, that.name) && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fileName, pitch, pressKeys);
    }
}
