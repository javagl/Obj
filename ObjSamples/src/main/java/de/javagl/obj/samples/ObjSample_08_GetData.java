/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Arrays;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;

/**
 * An example showing how to obtain the indices, vertices, texture coordinates
 * and normals from an {@link Obj} as arrays or buffers
 */
@SuppressWarnings("javadoc")
public class ObjSample_08_GetData 
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);
        
        // Obtain the indices of the elements for the faces
        int faceVertexIndices[] = ObjData.getFaceVertexIndicesArray(obj);
        int faceTexCoordIndices[] = ObjData.getFaceTexCoordIndicesArray(obj);
        int faceNormalIndices[] = ObjData.getFaceNormalIndicesArray(obj);
        
        System.out.println("Face vertex indices:");
        System.out.println(Arrays.toString(faceVertexIndices));

        System.out.println("Face texture coordinate indices:");
        System.out.println(Arrays.toString(faceTexCoordIndices));

        System.out.println("Face normal indices:");
        System.out.println(Arrays.toString(faceNormalIndices));

        // Obtain the data of the vertices
        float vertices[] = ObjData.getVerticesArray(obj);
        float texCoords[] = ObjData.getTexCoordsArray(obj, 2);
        float normals[] = ObjData.getNormalsArray(obj);

        System.out.println("Vertices:");
        System.out.println(createString(vertices, 3));
        
        System.out.println("Texture coordinates:");
        System.out.println(createString(texCoords, 2));
        
        System.out.println("Normals:");
        System.out.println(createString(normals, 3));

        
        // The data may also be obtained as a direct buffer
        FloatBuffer verticesBuffer = ObjData.getVertices(obj);
        
        System.out.println("Vertices from buffer:");
        System.out.println(createString(verticesBuffer, 3));
        
        
        // The data may also be written into an existing buffer
        FloatBuffer existingVerticesBuffer = 
            FloatBuffer.allocate(obj.getNumVertices() * 3);
        ObjData.getVertices(obj, existingVerticesBuffer);
        
        System.out.println("Vertices from existing buffer:");
        System.out.println(createString(existingVerticesBuffer, 3));
    }
    
    
    private static String createString(float array[], int stride)
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<array.length; i+=stride)
        {
            for (int j=0; j<stride; j++)
            {
                if (j > 0)
                {
                    sb.append(", ");
                }
                sb.append(array[i+j]);
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
