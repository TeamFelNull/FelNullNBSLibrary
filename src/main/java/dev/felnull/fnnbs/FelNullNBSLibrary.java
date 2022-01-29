package dev.felnull.fnnbs;

import java.io.IOException;
import java.io.InputStream;

public class FelNullNBSLibrary {
    public static NBS loadNBS(InputStream stream) throws IOException {
        return new NBS(stream);
    }
}
