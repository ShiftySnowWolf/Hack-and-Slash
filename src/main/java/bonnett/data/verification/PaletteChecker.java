package bonnett.data.verification;

import bonnett.Main;

import java.util.Arrays;
import java.util.List;

public class PaletteChecker {

    String[] valid = Main.validPalettes;
    String[] invalid = Main.invalidPalettes;

    public boolean check(String palette) {
        boolean exit;
        if (invalid == null || !(exit = Arrays.asList(invalid).contains(palette))) {
            if (!(exit = Arrays.asList(valid).contains(palette))) {
                if (invalid != null) {
                    List<String> addNewInvalidType = Arrays.asList(invalid);
                    addNewInvalidType.add(palette);
                    invalid = addNewInvalidType.toArray(String[]::new);
                } else { invalid = new String[]{palette}; }
            }
        }
        return exit;
    }
}
