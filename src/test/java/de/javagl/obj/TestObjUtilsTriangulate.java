package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestObjUtilsTriangulate
{
    @Test
    public void testTriangulateSquareAndTriangle() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareAndTriangle.obj"));
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(5, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(1, obj.getNumGroups());
        assertEquals(0, obj.getNumMaterialGroups());
        
        Obj triangulatedObj = ObjUtils.triangulate(obj);

        assertEquals(3, triangulatedObj.getNumFaces());
        assertEquals(5, triangulatedObj.getNumVertices());
        assertEquals(0, triangulatedObj.getNumTexCoords());
        assertEquals(0, triangulatedObj.getNumNormals());
        assertEquals(1, triangulatedObj.getNumGroups());
        assertEquals(0, triangulatedObj.getNumMaterialGroups());
    }
    
}