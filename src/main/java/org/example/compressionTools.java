package org.example;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class compressionTools {

    // decompress ZLIB-compressed data
    public static byte[] decompressZlib(byte[] compressedData) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        inflater.end();

        return outputStream.toByteArray();
    }

    // deserialize AMF data
    public static Object deserializeAmf(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Amf3Input amfInput = new Amf3Input(new SerializationContext());
        amfInput.setInputStream(inputStream);
        Object result = amfInput.readObject();
        amfInput.close();
        return result;
    }

    // compress ZLIB-compressed data
    public static byte[] compressZlib(byte[] data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream, deflater);
        deflaterOutputStream.write(data);
        deflaterOutputStream.close();
        return outputStream.toByteArray();
    }

    // Serialize AMF data
    public static byte[] serializeAmf(Object object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Amf3Output amfOutput = new Amf3Output(new SerializationContext());
        amfOutput.setOutputStream(outputStream);
        amfOutput.writeObject(object);
        amfOutput.close();
        return outputStream.toByteArray();
    }

}
