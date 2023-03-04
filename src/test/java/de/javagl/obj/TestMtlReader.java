package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestMtlReader
{
    /**
     * An epsilon for floating-point comparisons
     */
    private static final float FLOAT_ERROR = 1e-6f;

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
        assertEquals(0, mtls.get(1).getNs(), FLOAT_ERROR);
        assertEquals(0.5f, mtls.get(1).getD(), FLOAT_ERROR);
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
        assertEquals(500, mtl.getNs(), FLOAT_ERROR);
        assertEquals(1.0f, mtl.getD(), FLOAT_ERROR);
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
        assertEquals(500, mtl.getNs(), FLOAT_ERROR);
        assertEquals(123.0f, mtl.getD(), FLOAT_ERROR);
        assertEquals("texture.png", mtl.getMapKd());
    }

    @Test
    public void readTextureOptionsWithAllOptions()
        throws IOException
    {

        String tokens[] = new String[]
        {
            "-blendu", "off",
            "-blendv", "off",
            "-boost", "0.4",
            "-cc", "on",
            "-mm", "0.2", "0.33",
            "-o", "0.01", "0.02", "0.03",
            "-s", "0.04", "0.05", "0.06",
            "-t", "0.07", "0.08", "0.09",
            "-texres", ".44",
            "-clamp", "on",
            "-bm", "3.45",
            "-imfchan", "g",
            "-type", "sphere",
            "texture.png"
        };

        TextureOptions options = MtlReader.readTextureOptions(
            new LinkedList<String>(Arrays.asList(tokens)));

        assertEquals(Boolean.FALSE, options.isBlendu());
        assertEquals(Boolean.FALSE, options.isBlendv());

        assertEquals(0.4f, options.getBoost(), FLOAT_ERROR);

        assertEquals(Boolean.TRUE, options.isCc());

        assertEquals(0.2f, options.getMm().get(0), FLOAT_ERROR);
        assertEquals(0.33f, options.getMm().get(1), FLOAT_ERROR);

        assertEquals(0.01f, options.getO().getX(), FLOAT_ERROR);
        assertEquals(0.02f, options.getO().getY(), FLOAT_ERROR);
        assertEquals(0.03f, options.getO().getZ(), FLOAT_ERROR);

        assertEquals(0.04f, options.getS().getX(), FLOAT_ERROR);
        assertEquals(0.05f, options.getS().getY(), FLOAT_ERROR);
        assertEquals(0.06f, options.getS().getZ(), FLOAT_ERROR);

        assertEquals(0.07f, options.getT().getX(), FLOAT_ERROR);
        assertEquals(0.08f, options.getT().getY(), FLOAT_ERROR);
        assertEquals(0.09f, options.getT().getZ(), FLOAT_ERROR);

        assertEquals(0.44f, options.getTexres(), FLOAT_ERROR);

        assertEquals(Boolean.TRUE, options.isClamp());

        assertEquals(3.45f, options.getBm(), FLOAT_ERROR);

        assertEquals("g", options.getImfchan());

        assertEquals("sphere", options.getType());
    }


    @Test
    public void readTextureOptionsWithSingleOriginOffsetValue()
        throws IOException
    {
        String tokens[] = new String[]
        {
            "-o", "0.1", "texture.png"
        };
        TextureOptions options = MtlReader.readTextureOptions(
            new LinkedList<String>(Arrays.asList(tokens)));

        assertEquals(0.1f, options.getO().getX(), FLOAT_ERROR);
        assertEquals(0.0f, options.getO().getY(), FLOAT_ERROR);
        assertEquals(0.0f, options.getO().getZ(), FLOAT_ERROR);
    }

    @Test
    public void readTextureOptionsWithDoubleOriginOffsetValue()
        throws Exception
    {
        String tokens[] = new String[]
        {
            "-o", "0.1", "0.2", "texture.png"
        };
        TextureOptions options = MtlReader.readTextureOptions(
            new LinkedList<String>(Arrays.asList(tokens)));

        assertEquals(0.1f, options.getO().getX(), FLOAT_ERROR);
        assertEquals(0.2f, options.getO().getY(), FLOAT_ERROR);
        assertEquals(0.0f, options.getO().getZ(), FLOAT_ERROR);
    }

    @Test
    public void readMtlWithPbrProperties()
            throws IOException {
        List<Mtl> mtls = MtlReader.read(getClass().getResourceAsStream(
            "/mtlWithPbrProperties.mtl"));

        assertEquals(1, mtls.size());

        Mtl mtl = mtls.get(0);
        assertEquals("Material.001", mtl.getName());
        assertEquals(0.5, mtl.getPr(), FLOAT_ERROR);
        assertEquals(0.7, mtl.getPm(), FLOAT_ERROR);
        assertEquals(0.1, mtl.getPs(), FLOAT_ERROR);
        assertEquals(0.4, mtl.getPc(), FLOAT_ERROR);
        assertEquals(0.03, mtl.getPcr(), FLOAT_ERROR);
        assertEquals(0.001, mtl.getAniso(), FLOAT_ERROR);
        assertEquals(0.01, mtl.getAnisor(), FLOAT_ERROR);
    }
}
