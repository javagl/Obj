/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.util.Arrays;

import de.javagl.obj.Obj;
import de.javagl.obj.Objs;

/**
 * An example showing how to create an {@link Obj} manually
 */
@SuppressWarnings("javadoc")
public class ObjSample_07_Create 
{
    public static void main(String[] args) throws Exception
    {
        Obj obj = Objs.create();
        
        obj.setMtlFileNames(Arrays.asList("simpleSample.mtl"));
        
        obj.addVertex(0, 0, 0);
        obj.addVertex(1, 0, 0);
        obj.addVertex(1, 1, 0);
        obj.addVertex(0, 1, 0);

        obj.addNormal(0, 0, 1);
        obj.addNormal(0, 0, 1);
        obj.addNormal(0, 0, 1);
        obj.addNormal(0, 0, 1);

        obj.setActiveMaterialGroupName("material0");
        
        obj.addFaceWithNormals(0, 1, 3);
        
        obj.setActiveGroupNames(Arrays.asList("group1"));
        
        // For the case that the indices of the attributes are different:
        int vertexIndices[] = new int[] { 0, 1, 2};
        int normalIndices[] = new int[] { 0, 0, 0};
        obj.addFace(vertexIndices, null, normalIndices);
        
        System.out.println("Created Obj "+obj);
    }
}
