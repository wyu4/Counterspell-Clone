package ResourceClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/** Gathers and loads all the resource files (such as images). */
public class ResourcesManager {
    private static final String RESOURCE_DIR = "Resources";
    /** Hashmap that stores loaded files (absolute path, byte data). */
    private static final HashMap<String, byte[]> LOADED_FILES = new HashMap<>();

    /**
     * Loads a file to a hashmap if it isn't already loaded.
     * @param file File to read.
     * @throws IOException If an I/O exception occurs when reading the file's byte data
     */
    public static void loadFile(File file) throws IOException {
        byte[] tempData = new byte[0]; // Temporary var to store byte data

        // Check if file is valid, is readable, and isn't already loaded.
        if (
                !fileIsLoaded(file) &&
                file.exists() &&
                file.isFile() &&
                file.canRead()
        ) {
            // Try to get byte data
            tempData = Files.readAllBytes(file.toPath());

            // Add the data to the hashmap.
            LOADED_FILES.put(fileToKey(file), tempData);
        }
    }

    /**
     * Creates a valid key of a file for the hashmap
     * @param file Requested file
     * @return String
     */
    private static String fileToKey(File file) {
        return file.getName();
    }

    /**
     * Check if the file is already loaded.
     * @param file File to check
     * @return {@code true} if loaded, {@code false} if not loaded.
     */
    private static boolean fileIsLoaded(File file) {
        return LOADED_FILES.containsKey(fileToKey(file));
    }

    /**
     * Creates a file object using one of the enums.
     * @param e Enum corresponding to a resource file.
     * @return A file object
     */
    public static File enumToFile(ResourceEnum e) {
        return new File(switch (e) {
            case Cababas_png -> RESOURCE_DIR + "\\Cababas.png";
            case Counterspell_png -> RESOURCE_DIR + "\\CounterspellLogo.png";
        });
    }

    /**
     * Get file data
     * @param e Enum corresponding to a resource file.
     * @return {@code byte[]}
     */
    public static byte[] getAsByteData(ResourceEnum e) {
        File tempFile = enumToFile(e);

        // Try to load the temp file. Throw error if it fails.
        try {
            loadFile(tempFile);
        } catch (IOException er) {
            throw new RuntimeException(er);
        }

        // If the file was successfully loaded, return the loaded byte data
        if (fileIsLoaded(tempFile)) {
            return LOADED_FILES.get(fileToKey(tempFile));
        }

        // Return an empty byte array by default
        return new byte[0];
    }

    public static BufferedImage getAsBufferedImage(ResourceEnum e) {
        byte[] bytes = getAsByteData(e);

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        try (InputStream stream = new ByteArrayInputStream(bytes)) {
            BufferedImage temp = ImageIO.read(stream);
            if (temp == null) {
                throw new IOException("Byte data could not be converted into image.");
            } else {
                image = temp;
            }
        } catch (IOException ex) {
            System.out.println("Could not open image " + e + ": " + ex.getMessage());
        }

        return image;
    }

    public static Font getAsFont(ResourceEnum e) {
        byte[] bytes = getAsByteData(e);

        Font result = new Font(Font.MONOSPACED, Font.PLAIN, 10);
        try (InputStream stream = new ByteArrayInputStream(bytes)) {
            result = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(10f);
        } catch (IOException ex) {
            System.out.println("Could not open font " + e + ": " + ex.getMessage());
        } catch (FontFormatException ex) {
            System.out.println("Font " + e + " is formatted incorrectly: " + ex.getMessage());
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Testing resource manager by loading Cababas byte data. Input anything to continue...");
        console.next();
        console.close();
        System.out.println(Arrays.toString(getAsByteData(ResourceEnum.Cababas_png)));
    }
}
