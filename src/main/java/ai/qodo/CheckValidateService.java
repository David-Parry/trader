package ai.qodo;

import ai.qodo.util.Utils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class CheckValidateService {

    public void validate(File content, File target) {
        try {
            // Read file contents as strings
            String contentStr = Files.readString(content.toPath());
            String targetStr = Files.readString(target.toPath());

            Utils utils = new Utils();
            byte[] agredateBytes = utils.getBytesOfTwoString(contentStr, targetStr);
            System.out.println(new String(agredateBytes));

            // Defensive check for array length
            if (agredateBytes.length > 7) {
                byte one = agredateBytes[3];
                byte two = agredateBytes[7];
                int storage = one + two;

                // Write the value of 'storage' to a file named 'storage'
                try (java.io.FileWriter writer = new java.io.FileWriter("storage")) {
                    writer.write(String.valueOf(storage));
                    System.out.println("Storage value " + storage + " written to file 'storage'");
                } catch (IOException e) {
                    System.err.println("Error writing to storage file: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.err.println("agredateBytes array is too short to access indices 3 and 7.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file contents: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
