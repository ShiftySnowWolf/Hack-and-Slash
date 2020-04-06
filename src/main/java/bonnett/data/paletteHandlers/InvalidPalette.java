package bonnett.data.paletteHandlers;

import bonnett.Main;

import java.util.Arrays;
import java.util.List;

import static bonnett.Main.*;

public class InvalidPalette {

    public boolean check(String palette) {
        boolean exit;
        if (invalidPalettes == null || !(exit = Arrays.asList(invalidPalettes).contains(palette))) {
            if (!(exit = !Arrays.asList(validPalettes).contains(palette))) {
                if (invalidPalettes != null) {
                    List<String> addNewInvalidType = Arrays.asList(invalidPalettes);
                    addNewInvalidType.add(palette);
                    invalidPalettes = addNewInvalidType.toArray(String[]::new);
                } else { invalidPalettes = new String[]{palette}; }
            }
        }
        return exit;
    }
    public static void add(String palette) {
        if (invalidPalettes != null) {
            List<String> addNewInvalidType = Arrays.asList(invalidPalettes);
            addNewInvalidType.add(palette);
            invalidPalettes = addNewInvalidType.toArray(String[]::new);
        } else { invalidPalettes = new String[]{palette}; }
    }
}
