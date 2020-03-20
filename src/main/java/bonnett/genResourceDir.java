package bonnett;

import java.io.File;

public class genResourceDir {
    public static void main(String[] args) {
        String pathName = "..\\dungeon_templates\\Template";
        String[] numToWord = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        boolean dunTemp = (new File(pathName).mkdirs());
        if (!dunTemp) {
            System.out.println("[HackandSlash]: Error in generating resource directory!");
        } else {
            for (int i = 0; i < 6; i++) {
                //Rooms
                switch (i) {
                    case 0: { for (int x = 0; x < 4; x++) {
                        String door = numToWord[x];
                        if (!new File(pathName + "\\rooms\\oneChunk\\" + door + "\\Door").mkdir()) {
                            System.err.println("Could not generate: oneChunk folder");
                        }
                    }
                    } break;
                    case 1: { for (int x = 0; x < 6; x++) {
                        String door = numToWord[x];
                        if (!new File(pathName + "\\rooms\\twoChunk\\" + door + "\\Door").mkdir()) {
                            System.err.println("Could not generate: twoChunk folder");
                        }
                    }
                    } break;
                    case 2: { for (int x = 0; x < 8; x++) {
                        String door = numToWord[x];
                        if (!new File(pathName + "\\rooms\\threeChunk\\" + door + "\\Door").mkdir()) {
                            System.err.println("Could not generate: threeChunk folder");
                        }
                    }
                    } break;
                    case 3: { for (int x = 0; x < 8; x++) {
                        String door = numToWord[x];
                        if (!new File(pathName + "\\rooms\\fourChunk\\" + door + "\\Door").mkdir()) {
                            System.err.println("Could not generate: fourChunk folder");
                        }
                    }
                    } break;
                    case 4: { for (int x = 0; x < 10; x++) {
                        String door = numToWord[x];
                        if (!new File(pathName + "\\rooms\\sixChunk\\" + door + "\\Door").mkdir()) {
                            System.err.println("Could not generate: sixChunk folder");
                        }
                    }
                    } break;
                    case 5: {
                        if (!new File(pathName + "\\rooms\\boss").mkdir()) {
                            System.err.println("Could not generate: boss_room folder");
                        }
                    } break;
                    default: {System.err.println("Could not generate: Room parent folder");}
                }
            }

            for (int i = 0; i < 4; i++) {
                //Small hallways
                switch (i) {
                    case 0: {
                        if (!new File(pathName + "\\small_hallways\\straight").mkdir()) {
                            System.err.println("Could not generate: small_hallway straight");
                        }
                    } break;
                    case 1: {
                        if (!new File(pathName + "\\small_hallways\\three_way").mkdir()) {
                            System.err.println("Could not generate: small_hallway three_way");
                        }
                    } break;
                    case 2: {
                        if (!new File(pathName + "\\small_hallways\\four_way").mkdir()) {
                            System.err.println("Could not generate: small_hallway four_way");
                        }
                    } break;
                    case 3: {
                        if (!new File(pathName + "\\small_hallways\\corner").mkdir()) {
                            System.err.println("Could not generate: small_hallway corner");
                        }
                    } break;
                }
            }

            for (int i = 0; i < 4; i++) {
                //Large hallways
                switch (i) {
                    case 0: {
                        if (!new File(pathName + "\\large_hallways\\straight").mkdir()) {
                            System.err.println("Could not generate: large_hallway straight");
                        }
                    } break;
                    case 1: {
                        if (!new File(pathName + "\\large_hallways\\three_way").mkdir()) {
                            System.err.println("Could not generate: large_hallway three_way");
                        }
                    } break;
                    case 2: {
                        if (!new File(pathName + "\\large_hallways\\four_way").mkdir()) {
                            System.err.println("Could not generate: large_hallway four_way");
                        }
                    } break;
                    case 3: {
                        if (!new File(pathName + "\\large_hallways\\corner").mkdir()) {
                            System.err.println("Could not generate: large_hallway corner");
                        }
                    } break;
                }
            }
        }
    }
}
