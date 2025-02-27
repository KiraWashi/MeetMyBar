package org.meetmybar.meetmybarapi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    public static final int BITE_SIZE = 4 * 1024;

    public static byte[] compressImage(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[BITE_SIZE];

        while(!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp,0, size);
        }

        outputStream.close();

        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) throws DataFormatException, IOException {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Les données compressées ne peuvent pas être nulles ou vides");
        }

        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[BITE_SIZE];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                if (count == 0 && inflater.needsInput()) {
                    throw new DataFormatException("Format de données compressées invalide");
                }
                outputStream.write(tmp, 0, count);
            }
        } finally {
            inflater.end();
            outputStream.close();
        }

        return outputStream.toByteArray();
    }
}