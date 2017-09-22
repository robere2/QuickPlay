package co.bugg.quickplay.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileUtil {

    private FileUtil() {}

    /**
     * Create a directory with the path passed
     * @param dir Path of the directory
     * @return boolean whether or not the directory now exists
     */
    public static boolean createDir(File dir) {

        if(!dir.isDirectory()) {
            boolean createSuccessful = dir.mkdir();

            if(createSuccessful) {
                System.out.println("Directory " + dir.getPath() + " created.");
            } else {
                System.out.println("Failed to create directory " + dir.getPath() + "!");
            }
        }

        // Return whether or not the directory exists now, after trying to create it
        return dir.isDirectory();
    }

    /**
     * Create a file with the path passed
     * @param file Path to the file
     * @return boolean whether or not the file now exists
     */
    public static boolean createFile(File file) {

        if(!file.exists()) {
            try {
                boolean createSuccessful = file.createNewFile();

                if(createSuccessful) {
                    System.out.println("File " + file.getPath() + " created");
                } else {
                    System.out.println("Failed to create file " + file.getPath() + "!");
                }
            } catch (IOException e) {
                System.out.println("Failed to create file " + file.getPath() + "!");
                e.printStackTrace();
            }
        }

        // Return whether or not the file exists now, after trying to create it
        return file.exists();
    }

    /**
     * Serialize provided object into the provided file
     * @param file File to write to
     * @param object Object to write into file
     * @throws IOException When file doesn't exist or any other I/O error occurs
     */
    public static void serializeObject(File file, Object object) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(object);

        objectOutputStream.close();
        fileOutputStream.close();
    }
}
