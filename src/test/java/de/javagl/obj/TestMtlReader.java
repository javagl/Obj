package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestMtlReader
{
    @Test
    public void readMtl() 
        throws IOException
    {
        List<Mtl> mtls = MtlReader.read(getClass().getResourceAsStream(
            "/twoMaterialsA.mtl"));
        
        assertEquals(2, mtls.size());
        assertEquals(new DefaultFloatTuple(1,1,1), mtls.get(1).getKa());
        assertEquals(new DefaultFloatTuple(1,1,1), mtls.get(1).getKd());
        assertEquals(new DefaultFloatTuple(1,1,1), mtls.get(1).getKs());
        assertEquals(0, mtls.get(1).getNs(), 1e-6f);
        assertEquals(0.5f, mtls.get(1).getD(), 1e-6f);
        assertEquals("texture.png", mtls.get(1).getMapKd());
    }
    
    @Test
    public void readMtlWithWhitespace()
        throws IOException
    {
        List<Mtl> mtls = MtlReader.read(getClass().getResourceAsStream(
            "/mtlWithWhitespace.mtl"));

        assertEquals(1, mtls.size());

        Mtl mtl = mtls.get(0);
        assertEquals("material0", mtl.getName());
        assertEquals(new DefaultFloatTuple(1,0,0), mtl.getKa());
        assertEquals(new DefaultFloatTuple(1,1,0), mtl.getKd());
        assertEquals(new DefaultFloatTuple(1,1,1), mtl.getKs());
        assertEquals(500, mtl.getNs(), 1e-6f);
        assertEquals(1.0f, mtl.getD(), 1e-6f);
        assertEquals("texture.png", mtl.getMapKd());
    }

    @Test
    public void readMtlWithBrokenLines()
        throws IOException
    {
        List<Mtl> mtls = MtlReader.read(getClass().getResourceAsStream(
            "/mtlWithBrokenLines.mtl"));

        assertEquals(1, mtls.size());

        Mtl mtl = mtls.get(0);
        assertEquals("material0", mtl.getName());
        assertEquals(new DefaultFloatTuple(1,0,0), mtl.getKa());
        assertEquals(new DefaultFloatTuple(1,1,0), mtl.getKd());
        assertEquals(new DefaultFloatTuple(1,1,1), mtl.getKs());
        assertEquals(500, mtl.getNs(), 1e-6f);
        assertEquals(123.0f, mtl.getD(), 1e-6f);
        assertEquals("texture.png", mtl.getMapKd());
    }
}
