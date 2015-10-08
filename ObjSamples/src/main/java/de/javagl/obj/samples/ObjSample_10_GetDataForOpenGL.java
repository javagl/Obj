/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

/**
 * An example showing how to process an OBJ file and obtain the data that
 * may be used for rendering the OBJ with OpenGL
 */
@SuppressWarnings("javadoc")
public class ObjSample_10_GetDataForOpenGL
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);
        
        // Prepare the Obj so that its structure is suitable for
        // rendering with OpenGL: 
        // 1. Triangulate it
        // 2. Make sure that texture coordinates are not ambiguous
        // 3. Make sure that normals are not ambiguous
        // 4. Convert it to single-indexed data
        obj = ObjUtils.convertToRenderable(obj);
        
        // Obtain the data from the OBJ, as direct buffers:
        IntBuffer indices = ObjData.getFaceVertexIndices(obj, 3);
        FloatBuffer vertices = ObjData.getVertices(obj);
        FloatBuffer texCoords = ObjData.getTexCoords(obj, 2);
        FloatBuffer normals = ObjData.getNormals(obj);

        System.out.println("Indices:");
        System.out.println(createString(indices, 3));
        
        System.out.println("Vertices:");
        System.out.println(createString(vertices, 3));
        
        System.out.println("Texture coordinates:");
        System.out.println(createString(texCoords, 2));
        
        System.out.println("Normals:");
        System.out.println(createString(normals, 3));

    }
    
    private static String createString(IntBuffer buffer, int stride)
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<buffer.capacity(); i+=stride)
        {
            for (int j=0; j<stride; j++)
            {
                if (j > 0)
                {
                    sb.append(", ");
                }
                sb.append(buffer.get(i+j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private static String createString(FloatBuffer buffer, int stride)
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<buffer.capacity(); i+=stride)
        {
            for (int j=0; j<stride; j++)
            {
                if (j > 0)
                {
                    sb.append(", ");
                }
                sb.append(buffer.get(i+j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    
}
