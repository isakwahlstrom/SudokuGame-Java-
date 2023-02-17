package se.kth.isakwah.labb4.model;

import java.io.*;

/** Implementation of serialization and deserialization
 * of SudokoModel class.
 **/
public class SudokuIO {
    /**
     * Method to save to file.
     * @param file to save to
     * @param model model written to the file.
     * @throws IOException if IO has failed in some way
     */
    public static void serializeToFile(File file, SudokuModel model) throws IOException  {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(model);
        } finally {
            if(out!=null) {
                out.close();
            }
        }
    }

    /**
     * Method to load from file.
     * @param file to load from
     * @return the model to update
     * @throws IOException if IO has failed in some way
     * @throws ClassNotFoundException if class was not found
     */
    public static SudokuModel deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            SudokuModel model = (SudokuModel) in.readObject();
            return model;
        } finally {
            if(in!=null) {
                in.close();
            }
        }
    }

    private SudokuIO() {}
}

