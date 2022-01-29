package dev.felnull.fnnbs.instrument;

public enum VanillaInstrument implements IInstrument {
    PIANO("block.note_block.harp"),
    DOUBLE_BASS("block.note_block.bass"),
    BASS_DRUM("block.note_block.basedrum"),
    SNARE_DRUM("block.note_block.snare"),
    CLICK("block.note_block.hat"),
    GUITAR("block.note_block.guitar"),
    FLUTE("block.note_block.flute"),
    BELL("block.note_block.bell"),
    CHIME("block.note_block.chime"),
    XYLOPHONE("block.note_block.xylophone"),
    IRON_XYLOPHONE("block.note_block.iron_xylophone"),
    COW_BELL("block.note_block.cow_bell"),
    DIDGERIDOO("block.note_block.didgeridoo"),
    BIT("block.note_block.bit"),
    BANJO("block.note_block.banjo"),
    PLING("block.note_block.pling");
    private final String registryName;

    VanillaInstrument(String registryName) {
        this.registryName = registryName;
    }

    public String getRegistryName() {
        return registryName;
    }

    @Override
    public String getSoundName() {
        return registryName;
    }

    @Override
    public boolean isVanillaNote() {
        return true;
    }

    @Override
    public float getDefaultPitch() {
        return 45;
    }
}
