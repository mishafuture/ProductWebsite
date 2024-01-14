package com.example.productssite.utils;

import com.example.productssite.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {

    /*private static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        byte[] temp = new byte[4*1024];
        int size;

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            while (!deflater.finished()) {
                size = deflater.deflate(temp);
                outputStream.write(temp, 0, size);
            }

            return outputStream.toByteArray();
        }
        catch (IOException ignored) {
            return data;
        }
    }

    private static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        byte[] temp = new byte[4*1024];
        int count;
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            while (!inflater.finished()) {
                count = inflater.inflate(temp);
                outputStream.write(temp, 0, count);
            }

            return outputStream.toByteArray();
        } catch (IOException | DataFormatException e) {
            return data;
        }
    }

    public static Image toImageEntity(MultipartFile file) throws IOException {
        return Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(compressImage(file.getBytes())).build();
    }*/
}
