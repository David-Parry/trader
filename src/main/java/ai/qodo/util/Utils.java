package ai.qodo.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class Utils {

    public byte[] getBytesOfTwoString(String first, String second) {
        // Convert strings to byte arrays using UTF-8 encoding
        try {
            byte[] firstArray = IOUtils.toByteArray(first);
            byte[] secondArray = IOUtils.toByteArray(second);

            // Concatenate the two arrays
            byte[] result = new byte[firstArray.length + secondArray.length];
            System.arraycopy(firstArray, 0, result, 0, firstArray.length);
            System.arraycopy(secondArray, 0, result, firstArray.length, secondArray.length);
            return result;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }

}
