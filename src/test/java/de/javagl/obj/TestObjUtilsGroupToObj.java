package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestObjUtilsGroupToObj
{
    @Test
    public void testGroupToObj() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareAndTriangleInTwoGroups.obj"));
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(5, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(3, obj.getNumGroups()); // 2 + default
        assertEquals(0, obj.getNumMaterialGroups());

        Obj groupObj0 = ObjUtils.groupToObj(obj, obj.getGroup("group0"), null);

        Obj groupObj1 = ObjUtils.groupToObj(obj, obj.getGroup("group1"), null);

        assertEquals(1, groupObj0.getNumFaces());
        assertEquals(4, groupObj0.getNumVertices());
        assertEquals(0, groupObj0.getNumTexCoords());
        assertEquals(0, groupObj0.getNumNormals());
        assertEquals(2, groupObj0.getNumGroups());
        assertEquals(0, groupObj0.getNumMaterialGroups());
        
        assertEquals(1, groupObj1.getNumFaces());
        assertEquals(3, groupObj1.getNumVertices());
        assertEquals(0, groupObj1.getNumTexCoords());
        assertEquals(0, groupObj1.getNumNormals());
        assertEquals(2, groupObj1.getNumGroups());
        assertEquals(0, groupObj1.getNumMaterialGroups());
    }
    
    
}