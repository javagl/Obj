package de.javagl.obj;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class TestPbrPropertyReader {

    @Test
    public void testReader() throws IOException {
        Mtl mtl = new DefaultMtl("test");
        String command = "Pr";
        Queue<String> tokens = new ArrayDeque<>();
        tokens.add("0.001");
        PbrPropertyReader.readPbrProperty(mtl, command, tokens);

        assertEquals(mtl.getPr(), Float.valueOf(0.001F));
    }
}
