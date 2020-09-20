/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 TheoreticallyUseful
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package updatefixerupper.updatefixerupper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class UpdateFixerUpper implements ModInitializer {
    public static Logger logger = LogManager.getLogger("UFU");
    public static HashMap<String, Identifier> fixerMap = new HashMap<>();
    
    @Override
    public void onInitialize() {
        logger.info("Hello from UFU! Remember to make backups!");
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
    
                int lineNumber = 1;
                while(sc.hasNextLine())
                {
                    String line = sc.nextLine();
                    
                    // We can assume 4 because of the center text + at least 2 on each side for identifiers
                    if (!line.startsWith("//") && line.length() > 4) {
                        String cleanLine = line.replace(" ", "");
                        String[] split = cleanLine.split("->");
                        try {
                            fixerMap.put(split[0], new Identifier(split[1]));
                        } catch (IndexOutOfBoundsException e) {
                            logger.error("Failed to read line " + lineNumber + " that contains text " + '"' + line + '"');
                        }
                    }
                    
                    lineNumber++;
                }
                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
            logger.info("Finished reading ufu.txt, found " + fixerMap.size() + " valid pairs");
        }
    }
}
