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
import de.javagl.obj.Obj;
import de.javagl.obj.ObjGroup;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import de.javagl.obj.ReadableObj;
import de.javagl.obj.WritableObj;

/**
 * An example showing how to write a single group of an {@link ReadableObj}
 * to {@link WritableObj} that will process the elements via callbacks.
 * This is a general example for the concept of using a {@link WritableObj}
 * as the output of the {@link ObjUtils} methods.
 */
@SuppressWarnings("javadoc")
public class ObjSample_12_GroupToObjWithCallbacks 
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);
        
        // Create a BasicWritableObj, and set the callbacks
        // that should receive the vertices and faces that
        // are added to it
        BasicWritableObj groupObj = new BasicWritableObj();
        
        // One callback will add the vertices to a list
        List<FloatTuple> verticesOfGroup = new ArrayList<FloatTuple>();
        groupObj.setVertexConsumer(t -> verticesOfGroup.add(t));
        
        // One callback will print the added faces
        groupObj.setFaceConsumer(t -> System.out.println("Face of group: "+t));
        
        // Write the first group of the OBJ into the BasicWritableObj,
        // so that the vertices and faces of the group will be passed
        // to the registered callbacks
        ObjGroup group = obj.getGroup(0);
        ObjUtils.groupToObj(obj, group, null, groupObj);
        
        // Print the vertices that have been collected by the callback
        System.out.println("Vertices of group:");
        for (FloatTuple vertex : verticesOfGroup)
        {
            System.out.println(vertex);
        }
        
    }
}
