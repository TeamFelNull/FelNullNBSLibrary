package dev.felnull.fnnbs.instrument;

public interface CustomInstrument extends Instrument {
    String getFileName();

    boolean isPressKeys();
}
