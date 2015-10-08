/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjWriter;

/**
 * An example showing how to write on {@link Obj} into an OBJ file
 */
@SuppressWarnings("javadoc")
public class ObjSample_02_Write 
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);

        // Write an OBJ file
        OutputStream objOutputStream = 
            new FileOutputStream("./data/simpleSample_written.obj");
        ObjWriter.write(obj, objOutputStream);
    }
}
