/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.io.FileInputStream;
import java.io.InputStream;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjGroup;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

/**
 * An example showing how to convert a single group of one {@link Obj} into
 * a new, stand-alone {@link Obj}
 */
@SuppressWarnings("javadoc")
public class ObjSample_05_GroupToObj 
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);
        
        // Create a new OBJ from the first group of the OBJ
        ObjGroup group = obj.getGroup(0);
        Obj groupObj = ObjUtils.groupToObj(obj, group, null);
        
        System.out.println("From    "+group);
        System.out.println("of      "+obj);
        System.out.println("created "+groupObj);
    }
}
