package bonnett;

import java.io.File;

public class genResourceDir {
    public final void main() {

        //Pre Declaration
        System.out.println("[HackandSlash]: Attempting to generate file system!");
        String pathName = HackAndSlash.plugin.getDataFolder() + File.separator + "dungeon_templates" + File.separator + "Template";
        String[] numToWord = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};

        //File structure generation
        if (!(new File(pathName).mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate resource directory!");
        }
        for (int x = 0; x < 4; x++) {
            String door = numToWord[x];
                
            if (!(new File(pathName + File.separator + "rooms" + File.separator + "one_chunk" + File.separator + door + "_door").mkdirs())) {
                System.err.println("[HackandSlash]: Could not generate: one_chunk folder");
            }
        }
        for (int x = 0; x < 6; x++) {
            String door = numToWord[x];
                
            if (!(new File(pathName + File.separator + "rooms" + File.separator + "two_chunk" + File.separator + door + "_door").mkdirs())) {
                System.err.println("[HackandSlash]: Could not generate: two_chunk folder");
            }
        }
        for (int x = 0; x < 8; x++) {
            String door = numToWord[x];
                
            if (!(new File(pathName + File.separator + "rooms" + File.separator + "three_chunk" + File.separator + door + "_door").mkdirs())) {
                System.err.println("[HackandSlash]: Could not generate: three_chunk folder");
            }
        }
        for (int x = 0; x < 8; x++) {
            String door = numToWord[x];
                
            if (!(new File(pathName + File.separator + "rooms" + File.separator + "four_chunk" + File.separator + door + "_door").mkdirs())) {
                System.err.println("[HackandSlash]: Could not generate: four_chunk folder");
            }
        }
        for (int x = 0; x < 10; x++) {
            String door = numToWord[x];
                
            if (!(new File(pathName + File.separator + "rooms" + File.separator + "six_chunk" + File.separator + door + "_door").mkdirs())) {
                System.err.println("[HackandSlash]: Could not generate: six_chunk folder");
            }
        }

            
        if (!(new File(pathName + File.separator + "rooms" + File.separator + "boss").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: boss_room folder");
        }

        
        if (!(new File(pathName + File.separator + "small_hallways" + File.separator + "straight").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: small_hallway straight");
        }
        
        if (!(new File(pathName + File.separator + "small_hallways" + File.separator + "three_way").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: small_hallway three_way");
        }
        
        if (!(new File(pathName + File.separator + "small_hallways" + File.separator + "four_way").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: small_hallway four_way");
        }
        
        if (!(new File(pathName + File.separator + "small_hallways" + File.separator + "corner").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: small_hallway corner");
        }
        
        //Large hallways
        if (!(new File(pathName + File.separator + "large_hallways" + File.separator + "straight").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: large_hallway straight");
        }
        
        if (!(new File(pathName + File.separator + "large_hallways" + File.separator + "three_way").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: large_hallway three_way");
        }
        
        if (!(new File(pathName + File.separator + "large_hallways" + File.separator + "four_way").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: large_hallway four_way");
        }
        
        if (!(new File(pathName + File.separator + "large_hallways" + File.separator + "corner").mkdirs())) {
            System.err.println("[HackandSlash]: Could not generate: large_hallway corner");
        }

        //Schematic file generation

    }
}
