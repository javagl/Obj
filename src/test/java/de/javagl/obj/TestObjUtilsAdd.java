package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestObjUtilsAdd
{
    @Test
    public void testAdd() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/twoTrianglesOneInDefaultGroup.obj"));
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(4, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(2, obj.getNumGroups()); // 1 + default
        assertEquals(0, obj.getNumMaterialGroups());
        
        Obj combinedObj = Objs.create();
        ObjUtils.add(obj, combinedObj);
        ObjUtils.add(obj, combinedObj);
        
        assertEquals(4, combinedObj.getNumFaces());
        assertEquals(8, combinedObj.getNumVertices());
        assertEquals(0, combinedObj.getNumTexCoords());
        assertEquals(0, combinedObj.getNumNormals());
        assertEquals(2, combinedObj.getNumGroups()); // Still only two groups!
        assertEquals(0, combinedObj.getNumMaterialGroups());
    }
    
    @Test
    public void testAddDifferent() 
        throws IOException
    {
        Obj obj0 = ObjReader.read(getClass().getResourceAsStream(
            "/twoTrianglesOneInDefaultGroup.obj"));
        
        Obj obj1 = ObjReader.read(getClass().getResourceAsStream(
            "/squareAndTriangleInTwoGroups.obj"));
        
        assertEquals(2, obj0.getNumFaces());
        assertEquals(4, obj0.getNumVertices());
        assertEquals(0, obj0.getNumTexCoords());
        assertEquals(0, obj0.getNumNormals());
        assertEquals(2, obj0.getNumGroups()); // 1 + default
        assertEquals(0, obj0.getNumMaterialGroups());

        assertEquals(2, obj1.getNumFaces());
        assertEquals(5, obj1.getNumVertices());
        assertEquals(0, obj1.getNumTexCoords());
        assertEquals(0, obj1.getNumNormals());
        assertEquals(3, obj1.getNumGroups()); // 2 + default 
        assertEquals(0, obj1.getNumMaterialGroups());
        
        Obj combinedObj = Objs.create();
        ObjUtils.add(obj0, combinedObj);
        ObjUtils.add(obj1, combinedObj);
        
        assertEquals(4, combinedObj.getNumFaces());
        assertEquals(9, combinedObj.getNumVertices());
        assertEquals(0, combinedObj.getNumTexCoords());
        assertEquals(0, combinedObj.getNumNormals());
        // The default group, "group0" and "group1"
        assertEquals(3, combinedObj.getNumGroups()); 
        assertEquals(0, combinedObj.getNumMaterialGroups());
    }
    
    
    @Test
    public void testAddWithMaterialGroups() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/fourTrianglesInMixedGroups.obj"));
        
        assertEquals(4, obj.getNumFaces());
        assertEquals(6, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(3, obj.getNumGroups()); // 2 + (empty) default
        assertEquals(4, obj.getNumMaterialGroups()); // 4 groups
        
        Obj combinedObj = Objs.create();
        ObjUtils.add(obj, combinedObj);
        ObjUtils.add(obj, combinedObj);
        
        assertEquals(8, combinedObj.getNumFaces());
        assertEquals(12, combinedObj.getNumVertices());
        assertEquals(0, combinedObj.getNumTexCoords());
        assertEquals(0, combinedObj.getNumNormals());
        assertEquals(3, combinedObj.getNumGroups());
        assertEquals(4, combinedObj.getNumMaterialGroups()); // Still 4
    }
   
    
}