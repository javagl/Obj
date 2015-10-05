package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestObjUtilsMakeTexCoordsUnique
{
    @Test
    public void testMakeTexCoordsUnique() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/twoTrianglesWithAmbiguousTexCoords.obj"));
        
        Obj unique = ObjUtils.makeTexCoordsUnique(obj);
        
        assertEquals(2, unique.getNumFaces());
        assertEquals(5, unique.getNumVertices());
        assertEquals(4, unique.getNumTexCoords());
        assertEquals(0, unique.getNumNormals());
        assertEquals(1, unique.getNumGroups());
        assertEquals(0, unique.getNumMaterialGroups());
    }
    
}