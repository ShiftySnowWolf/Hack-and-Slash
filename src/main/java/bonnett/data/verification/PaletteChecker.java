package bonnett.data.verification;

import bonnett.Main;

import java.util.Arrays;
import java.util.List;

public class PaletteChecker {

    String[] valid = Main.validPalettes;
    String[] invalid = Main.invalidPalettes;

    public boolean check(String palette) {
        boolean exit;
        if (!(exit = Arrays.asList(invalid).contains(palette))) {
            if (!(exit = Arrays.asList(valid).contains(palette))) {
                List<String> addNewInvalidType = Arrays.asList(invalid);
                addNewInvalidType.add(palette);
                invalid = addNewInvalidType.toArray(String[]::new);
            }
        }
        return exit;
    }
}
