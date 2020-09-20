package updatefixerupper.updatefixerupper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class UpdateFixerUpper implements ModInitializer {
    public static HashMap<String, Identifier> fixerMap = new HashMap<>();
    
    @Override
    public void onInitialize() {
        System.out.println("Hello from UFU! Remember to make backups!");
        File file = FabricLoader.getInstance().getConfigDir().resolve("ufu.txt").toFile();
        
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    // Had to create file
                    FileWriter myWriter = new FileWriter(file);
                    myWriter.write("// Make sure to make backups!\n");
                    myWriter.write("// Format is <old identifier> -> <new identifier>\n");
                    myWriter.write("// Example: removed_mod:bad_block_or_item -> added_mod:good_block_or_item\n");
                    myWriter.write("// You can press F3 to see block IDs on the right, and F3 + H to see item IDs\n");
                    myWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (file.exists() && file.canRead()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                Scanner sc = new Scanner(fis);
    
                while(sc.hasNextLine())
                {
                    String line = sc.nextLine();
                    
                    // We can assume 4 because of the center text + at least 2 on each side for identifiers
                    if (!line.startsWith("//") && line.length() > 4) {
                        line = line.replace(" ", "");
                        String[] split = line.split("->");
                        
                        fixerMap.put(split[0], new Identifier(split[1]));
                    }
                }
                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(fixerMap);
    }
}
