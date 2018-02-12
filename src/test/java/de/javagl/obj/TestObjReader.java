package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestObjReader
{
    @Test
    public void readSquare() 
        throws IOException
    {
         Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/square.obj"));
        
        assertEquals(1, obj.getNumFaces());
        assertEquals(4, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(1, obj.getNumGroups());
        assertEquals(0, obj.getNumMaterialGroups());
        assertEquals(new Rect3D(0f, 4f, 0f, 4f, 0f, 0f),
                obj.getRect3D());
    }
    
    @Test
    public void readSquareAndTriangle() 
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
        assertEquals(new Rect3D(0f, 4f, 0f, 6f, 0f, 0f),
                obj.getRect3D());
    }

    @Test
    public void readSquareAndTriangleInTwoGroups() 
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
    }
    
    @Test
    public void readFourTrianglesInMixedGroups() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/fourTrianglesInMixedGroups.obj"));
        
        assertEquals(4, obj.getNumFaces());
        assertEquals(6, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(3, obj.getNumGroups()); // 2 + default
        assertEquals(4, obj.getNumMaterialGroups());
        assertEquals("twoMaterialsA.mtl", obj.getMtlFileNames().get(0));
    }
    
    @Test
    public void readTwoTrianglesOneInDefaultGroup() 
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
    }
    
    
    @Test
    public void readTwoTrianglesSharedInThreeGroups() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/twoTrianglesSharedInThreeGroups.obj"));
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(4, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(4, obj.getNumGroups()); // 3 + default
        assertEquals(0, obj.getNumMaterialGroups());
    }
    
    
    @Test
    public void readSquareAndTriangleWithRelativeIndices() 
        throws IOException
    {
        Obj obj = ObjReader.read(getClass().getResourceAsStream(
            "/squareAndTriangleWithRelativeIndices.obj"));
        
        assertEquals(2, obj.getNumFaces());
        assertEquals(5, obj.getNumVertices());
        assertEquals(0, obj.getNumTexCoords());
        assertEquals(0, obj.getNumNormals());
        assertEquals(1, obj.getNumGroups()); // default
        assertEquals(0, obj.getNumMaterialGroups());
    }
    
    
    @Test
    public void readMtls() 
        throws IOException
    {
        List<Mtl> mtls = MtlReader.read(getClass().getResourceAsStream(
            "/twoMaterialsA.mtl"));
        
        assertEquals(2, mtls.size());
        Mtl mtl0 = mtls.get(0);
        assertEquals("material0", mtl0.getName());
        assertEquals(new DefaultFloatTuple(1,0,0), mtl0.getKa());
        assertEquals(new DefaultFloatTuple(1,0,0), mtl0.getKd());
        assertEquals(new DefaultFloatTuple(1,1,1), mtl0.getKs());
        assertEquals(500, mtl0.getNs(), 1e-6);

    }
    
}