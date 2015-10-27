package de.javagl.obj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;
import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestObjWriter
{
    @Test
    public void readWriteSquare() 
        throws IOException
    {
        String inputString = readResourceAsString(
            "/square.obj");
        Obj obj = ObjReader.read(
            new ByteArrayInputStream(inputString.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjWriter.write(obj, baos);
        String outputString = new String(baos.toByteArray());
        
        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }
    
    @Test
    public void readWriteSquareAndTriangle() 
        throws IOException
    {
        String inputString = readResourceAsString(
            "/squareAndTriangle.obj");
        Obj obj = ObjReader.read(
            new ByteArrayInputStream(inputString.getBytes()));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjWriter.write(obj, baos);
        String outputString = new String(baos.toByteArray());
        
        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }

    @Test
    public void readWriteSquareAndTriangleInTwoGroups() 
        throws IOException
    {
        String inputString = readResourceAsString(
            "/squareAndTriangleInTwoGroups.obj");
        Obj obj = ObjReader.read(
            new ByteArrayInputStream(inputString.getBytes()));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjWriter.write(obj, baos);
        String outputString = new String(baos.toByteArray());
        
        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }
    
    @Test
    public void readWriteFourTrianglesInMixedGroups() 
        throws IOException
    {
        String inputString = readResourceAsString(
            "/fourTrianglesInMixedGroups.obj");
        Obj obj = ObjReader.read(
            new ByteArrayInputStream(inputString.getBytes()));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjWriter.write(obj, baos);
        String outputString = new String(baos.toByteArray());
        
        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }
    
    @Test
    public void readWriteTwoTrianglesOneInDefaultGroup() 
        throws IOException
    {
        String inputString = readResourceAsString(
            "/twoTrianglesOneInDefaultGroup.obj");
        Obj obj = ObjReader.read(
            new ByteArrayInputStream(inputString.getBytes()));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjWriter.write(obj, baos);
        String outputString = new String(baos.toByteArray());
        
        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }
    
    
    @Test
    public void readTwoTrianglesSharedInThreeGroups() 
        throws IOException
    {
        String inputString = readResourceAsString(
            "/twoTrianglesSharedInThreeGroups.obj");
        Obj obj = ObjReader.read(
            new ByteArrayInputStream(inputString.getBytes()));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjWriter.write(obj, baos);
        String outputString = new String(baos.toByteArray());
        
        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }
    
    private static String readResourceAsString(String name)
    {
        InputStream inputStream = 
            TestObjWriter.class.getResourceAsStream(name);
        String string = readAsString(inputStream);
        string = string.replaceAll("\r\n", "\n");
        return string;
    }
    
    private static String readAsString(InputStream inputStream) 
    {
        try (Scanner scanner = new Scanner(inputStream))
        {
            scanner.useDelimiter("\\A");
            String string = scanner.next();
            return string;
        }
    }    
    
    
}