package de.javagl.obj;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestObjData
{
    @Test
    public void testGetVertices() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareTextured.obj"));
        obj = ObjUtils.convertToRenderable(obj);
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(4, obj.getNumVertices());
        assertEquals(4, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(1, obj.getNumGroups()); // default
        assertEquals(0, obj.getNumMaterialGroups());
        
        float[] actualVertices = ObjData.getVerticesArray(obj);
        float[] expectedVertices = 
        {
            0.0f, 0.0f, 0.0f,
            4.0f, 0.0f, 0.0f,
            4.0f, 4.0f, 0.0f,
            0.0f, 4.0f, 0.0f
        };
        assertArrayEquals(expectedVertices, actualVertices, 0.0f);
    }
    
    @Test
    public void testGetTexCoords() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareTextured.obj"));
        obj = ObjUtils.convertToRenderable(obj);
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(4, obj.getNumVertices());
        assertEquals(4, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(1, obj.getNumGroups()); // default
        assertEquals(0, obj.getNumMaterialGroups());
        
        float[] actualTexCoords = ObjData.getTexCoordsArray(obj, 2);
        float[] expectedTexCoords = 
        {
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
        };
        assertArrayEquals(expectedTexCoords, actualTexCoords, 0.0f);
    }
    
    @Test
    public void testGetTexCoordsFlipped() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareTextured.obj"));
        
        obj = ObjUtils.convertToRenderable(obj);
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(4, obj.getNumVertices());
        assertEquals(4, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(1, obj.getNumGroups()); // default
        assertEquals(0, obj.getNumMaterialGroups());
        
        float[] actualTexCoords = ObjData.getTexCoordsArray(obj, 2, true);
        float[] expectedTexCoords = 
        {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f
        };
        assertArrayEquals(expectedTexCoords, actualTexCoords, 0.0f);
    }
    

}
