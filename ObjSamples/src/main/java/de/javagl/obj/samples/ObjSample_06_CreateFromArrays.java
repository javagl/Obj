/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import de.javagl.obj.Obj;
import de.javagl.obj.Objs;

/**
 * An example showing how to create an {@link Obj} from arrays containing
 * the indices and vertex properties (that is, from indexed triangle data)
 */
@SuppressWarnings("javadoc")
public class ObjSample_06_CreateFromArrays 
{
    public static void main(String[] args) throws Exception
    {
        float vertices[] = 
        {
            0,0,0,
            1,0,0,
            1,1,0,
            0,1,0,
        };
        float normals[] = 
        {
            0,0,1,
            0,0,1,
            0,0,1,
            0,0,1,
        };
        int indices[] = 
        {   
            0,1,3,
            0,1,2
        };
        
        Obj obj = Objs.createFromIndexedTriangleData(
            IntBuffer.wrap(indices), 
            FloatBuffer.wrap(vertices),
            null,
            FloatBuffer.wrap(normals));
        
        System.out.println("Created Obj "+obj);
    }
}
