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
    
    
}