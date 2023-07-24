package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestObjsCreate
{
    @Test
    public void createFromIndexedTriangleData() 
        throws IOException
    {
        float[] vertices =
        {
            0,0,0,
            1,0,0,
            1,1,0,
            0,1,0,
        };
        float[] texCoords =
        {
            0,0,
            1,0,
            1,1,
            0,1,
        };
        float[] normals =
        {
            0,0,1,
            0,0,1,
            0,0,1,
            0,0,1,
        };
        int[] indices =
        {   
            0,1,3,
            0,1,2
        };
        
        Obj obj = Objs.createFromIndexedTriangleData(
            IntBuffer.wrap(indices), 
            FloatBuffer.wrap(vertices),
            FloatBuffer.wrap(texCoords),
            FloatBuffer.wrap(normals));
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(4, obj.getNumVertices());
        assertEquals(4, obj.getNumTexCoords());
        assertEquals(4, obj.getNumNormals());
        assertEquals(1, obj.getNumGroups());
        assertEquals(0, obj.getNumMaterialGroups());
    }
    
       
}