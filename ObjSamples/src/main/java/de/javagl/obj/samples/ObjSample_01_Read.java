/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.io.FileInputStream;
import java.io.InputStream;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;

/**
 * An example showing how to read an OBJ file, and accessing the elements
 * of the resulting {@link Obj}
 */
@SuppressWarnings("javadoc")
public class ObjSample_01_Read 
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);
        
        // Access some elements of the OBJ file
        System.out.println(obj.getVertex(0));
        System.out.println(obj.getTexCoord(0));
        System.out.println(obj.getNormal(0));
        System.out.println(obj.getFace(0));
        System.out.println(obj.getGroup(0));
        System.out.println(obj.getMaterialGroup(0));
    }
}
