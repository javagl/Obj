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
import de.javagl.obj.ObjUtils;

/**
 * An example showing how to triangulate an {@link Obj}. This will cause
 * all faces in the OBJ that have more than 3 vertices to be split into
 * triangles.
 */
@SuppressWarnings("javadoc")
public class ObjSample_03_Triangulate 
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);
        
        System.out.println("Faces before triangulating");
        for (int i = 0; i < obj.getNumFaces(); i++)
        {
            System.out.println(
                "Face " + i + " of " + obj.getNumFaces() + 
                ": " + obj.getFace(i));
        }

        // Triangulate the OBJ
        Obj triangulated = ObjUtils.triangulate(obj);
        
        System.out.println("Faces after triangulating");
        for (int i = 0; i < triangulated.getNumFaces(); i++)
        {
            System.out.println(
                "Face " + i + " of " + triangulated.getNumFaces() + 
                ": " + triangulated.getFace(i));
        }
    }
}
