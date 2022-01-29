package dev.felnull.fnnbs.instrument;

public interface IInstrument {
    public String getSoundName();

    public boolean isVanillaNote();

    public float getDefaultPitch();
}
