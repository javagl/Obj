package de.javagl.obj;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestObjSplitting
{
    @Test
    public void testSplitByGroup() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareAndTriangleInTwoGroups.obj"));
        
        List<Obj> objs = ObjSplitting.splitByGroups(obj);
        assertEquals(2, objs.size());
    }

    @Test
    public void testSplitByGroupOnlyDefault() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/square.obj"));
        
        List<Obj> objs = ObjSplitting.splitByGroups(obj);
        assertEquals(1, objs.size());
    }
    
    @Test
    public void testSplitByMaterialGroup() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/fourTrianglesInMixedGroups.obj"));
        
        List<Obj> objs = ObjSplitting.splitByMaterialGroups(obj);
        assertEquals(4, objs.size());
    }
    
    @Test
    public void testSplitByMaterialGroupWithoutMaterial() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/square.obj"));
        
        List<Obj> objs = ObjSplitting.splitByMaterialGroups(obj);
        
        // There are no material groups in the "square.obj" 
        assertEquals(0, objs.size());
    }
   
    @Test
    public void testSplitByMaterialGroupForPartialGroups() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/fourTrianglesPartiallyInMaterialGroups.obj"));
        
        List<Obj> objs = ObjSplitting.splitByMaterialGroups(obj);

        // Only two of the triangles are in material groups 
        assertEquals(2, objs.size());
    }
    
    @Test
    public void testSplitByNumVertices() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/fourTrianglesInMixedGroups.obj"));
        
        int maxNumVertices = 4;
        List<Obj> objs = 
            ObjSplitting.splitByMaxNumVertices(obj, maxNumVertices);
        
        assertEquals(2, objs.size());
        for (ReadableObj r : objs)
        {
            assertTrue(r.getNumVertices() <= maxNumVertices);
        }
    }
    
    @Test
    public void testSplitByNumVerticesForInvalidInput() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/square.obj"));
        
        int maxNumVertices = 3;
        List<Obj> objs = 
            ObjSplitting.splitByMaxNumVertices(obj, maxNumVertices);
        assertEquals(1, objs.size());
        for (ReadableObj r : objs)
        {
            // This is larger than the maximum, but an OBJ containing
            // a square cannot be split otherwise:
            assertEquals(4, r.getNumVertices());
        }
    }
    
    
   
}