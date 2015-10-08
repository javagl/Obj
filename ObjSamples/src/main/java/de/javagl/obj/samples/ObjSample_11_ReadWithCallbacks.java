/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.javagl.obj.BasicWritableObj;
import de.javagl.obj.FloatTuple;
import de.javagl.obj.ObjReader;

/**
 * An example showing how to read an OBJ file, passing the elements that 
 * are read from the file to callbacks 
 */
@SuppressWarnings("javadoc")
public class ObjSample_11_ReadWithCallbacks 
{
    public static void main(String[] args) throws Exception
    {
        // Create a BasicWritableObj, and set the callbacks
        // that should receive the vertices and faces that
        // are added to it
        BasicWritableObj obj = new BasicWritableObj();
        
        // One callback will add the vertices to a list
        List<FloatTuple> vertices = new ArrayList<FloatTuple>();
        obj.setVertexConsumer(t -> vertices.add(t));
        
        // One callback will print the read faces
        obj.setFaceConsumer(t -> System.out.println("Read face: "+t));
        
        // Read an OBJ file, passing the read elements to the 
        // BasicWritableObj, which will notify the callbacks
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        ObjReader.read(objInputStream, obj);
        
        // Print the vertices that have been collected by the callback
        System.out.println("Vertices:");
        for (FloatTuple vertex : vertices)
        {
            System.out.println(vertex);
        }
        
    }
}
