package dev.felnull.fnnbs.instrument;

import java.util.Objects;

public class CustomInstrument implements IInstrument {
    private String name;
    private String fileName;
    private int pitch;
    private boolean autoPressKeys;

    public CustomInstrument(String name, String fileName, int pitch, boolean autoPressKeys) {
        this.name = name;
        this.fileName = fileName;
        this.pitch = pitch;
        this.autoPressKeys = autoPressKeys;
    }

    public String getName() {
        return name;
    }

    public int getPitch() {
        return pitch;
    }

    @Override
    public float getDefaultPitch() {
        return getPitch();
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isAutoPressKeys() {
        return autoPressKeys;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public void setAutoPressKeys(boolean autoPressKeys) {
        this.autoPressKeys = autoPressKeys;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean isVanillaNote() {
        return false;
    }

    @Override
    public String toString() {
        return "CustomInstrument{" +
                "name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", pitch=" + pitch +
                ", autoPressKeys=" + autoPressKeys +
                '}';
    }

    @Override
    public String getSoundName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomInstrument that = (CustomInstrument) o;
        return pitch == that.pitch && autoPressKeys == that.autoPressKeys && Objects.equals(name, that.name) && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fileName, pitch, autoPressKeys);
    }
}
