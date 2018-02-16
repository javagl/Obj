package de.javagl.obj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestObjUtilsAddComputedNormals
{
    @Test
    public void testAddComputedNormals() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareAndTriangleWithMissingNormals.obj"));
        
        // Structure:
        //   4
        //  / \
        // 3---2
        // |   |
        // 0---1
        assertEquals(2, obj.getNumFaces());
        assertEquals(3, obj.getNumNormals());
        
        // Initially, only the triangle has normals
        ObjFace square = obj.getFace(0);
        assertFalse(square.containsNormalIndices());
        
        ObjFace triangle = obj.getFace(1);
        assertTrue(triangle.containsNormalIndices());
        
        Obj result = ObjUtils.addComputedNormals(obj);
        
        // Two normals are added: One for vertices 0 and 1
        // and one for vertices 2 and 3
        assertEquals(5, result.getNumNormals());
        
        ObjFace squareWithNormals = result.getFace(0);
        assertTrue(squareWithNormals.containsNormalIndices());
        
        assertEquals(3, squareWithNormals.getNormalIndex(0));
        assertEquals(3, squareWithNormals.getNormalIndex(1));
        assertEquals(4, squareWithNormals.getNormalIndex(2));
        assertEquals(4, squareWithNormals.getNormalIndex(3));
    }
    
    @Test
    public void testAddComputedNormalsWithLines() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/triangleAndLines.obj"));
        
        // Structure:
        //     2---4
        //   / |
        // 0---1---3
        assertEquals(3, obj.getNumFaces());
        assertEquals(0, obj.getNumNormals());
        
        Obj result = ObjUtils.addComputedNormals(obj);

        // The triangle has three times the same normal, (0,0,1)
        ObjFace triangle = result.getFace(0);
        assertEquals(0, triangle.getNormalIndex(0));
        assertEquals(0, triangle.getNormalIndex(1));
        assertEquals(0, triangle.getNormalIndex(2));
        
        // The the first vertex of each line has the same
        // normal as the triangle. The second vertex does
        // not have a valid normal, it is (0,0,0)
        ObjFace line0 = result.getFace(1);
        assertEquals(0, line0.getNormalIndex(0));
        assertEquals(1, line0.getNormalIndex(1));

        ObjFace line1 = result.getFace(1);
        assertEquals(0, line1.getNormalIndex(0));
        assertEquals(1, line1.getNormalIndex(1));
    }
    
   
}

