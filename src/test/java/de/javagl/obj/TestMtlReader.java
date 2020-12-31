package de.javagl.obj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;


@SuppressWarnings("javadoc")
public class TestMtlReader {

    private static final float FLOAT_ERROR = 0.00001f;

    @Test
    public void readMtl() throws IOException {
        List<Mtl> mtls = MtlReader.read(getClass().getResourceAsStream(
            "/twoMaterialsA.mtl"));
        
        assertEquals(2, mtls.size());
        assertEquals(new DefaultFloatTuple(1,1,1), mtls.get(1).getKa());
        assertEquals(new DefaultFloatTuple(1,1,1), mtls.get(1).getKd());
        assertEquals(new DefaultFloatTuple(1,1,1), mtls.get(1).getKs());
        assertEquals(0, mtls.get(1).getNs(), 1e-6f);
        assertEquals(0.5f, mtls.get(1).getD(), 1e-6f);
        assertEquals(new TextureOptions.Builder()
                .setFileName("texture.png")
                .build(), mtls.get(1).getMapKd());
    }

    @Test
    public void readNewMtlLine() throws Exception {
        List<Mtl> mtlList = new ArrayList<>();
        Mtl mtl = MtlReader.parseLine("newmtl materialname", mtlList, null);
        assertEquals("materialname", mtl.getName());
        assertEquals(1, mtlList.size());
        assertEquals("materialname", mtlList.get(0).getName());
    }

    @Test
    public void readIllumLine() throws Exception {
        List<Mtl> mtlList = new ArrayList<>();
        DefaultMtl mtl = new DefaultMtl("material");

        MtlReader.parseLine("illum 0", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.COLOR_ON_AMBIENT_OFF, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 1", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.COLOR_ON_AMBIENT_ON, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 2", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.HIGHLIGHT_ON, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 3", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.REFLECTION_ON_RAY_TRACE_ON, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 4", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_ON, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 5", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.REFLECTION_FRESNEL_ON_RAY_TRACE_ON, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 6", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_OFF_RAY_TRACE_ON, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 7", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.TRANSPARENCY_REFRACTION_ON_REFLECTION_FRESNEL_ON_RAY_TRACE_ON, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 8", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.REFLECTION_ON_RAY_TRACE_OFF, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 9", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.TRANSPARENCY_GLASS_ON_REFLECTION_RAY_TRACE_OFF, mtl.getIlluminationMode());

        MtlReader.parseLine("illum 10", mtlList, mtl);
        assertEquals(Mtl.IlluminationMode.SHADOW_ON_INVISIBLE_SURFACES, mtl.getIlluminationMode());
    }

    @Test
    public void readTextureOptions_successFileNameOnly() throws Exception {
        TextureOptions options = MtlReader.readTextureOptions(new String[] { "texture.png" }, 0, null);
        assertEquals("texture.png", options.getFileName());
    }

    @Test
    public void readTextureOptions_successWithAllOptions() throws Exception {
        TextureOptions options = MtlReader.readTextureOptions(new String[] {
                "-blendu", "off",
                "-blendv", "off",
                "-boost", "0.4",
                "-mm", "0.2", "0.33",
                "-o", "0.01", "0.02", "0.03",
                "-s", "0.04", "0.05", "0.06",
                "-t", "0.07", "0.08", "0.09",
                "-texres", ".44",
                "-clamp", "on",
                "-bm", "3.45",
                "-imfchan", "g",
                "-type", "sphere",
                "texture.png"}, 0, null);
        assertFalse(options.isBlenduEnabled());
        assertFalse(options.isBlendvEnabled());

        assertEquals(0.4f, options.getBoost(), FLOAT_ERROR);

        assertEquals(0.2f, options.getMmBrightness(), FLOAT_ERROR);
        assertEquals(0.33f, options.getMmContrast(), FLOAT_ERROR);

        assertEquals(0.01f, options.getOriginOffset().getX(), FLOAT_ERROR);
        assertEquals(0.02f, options.getOriginOffset().getY(), FLOAT_ERROR);
        assertEquals(0.03f, options.getOriginOffset().getZ(), FLOAT_ERROR);

        assertEquals(0.04f, options.getScale().getX(), FLOAT_ERROR);
        assertEquals(0.05f, options.getScale().getY(), FLOAT_ERROR);
        assertEquals(0.06f, options.getScale().getZ(), FLOAT_ERROR);

        assertEquals(0.07f, options.getTurbulence().getX(), FLOAT_ERROR);
        assertEquals(0.08f, options.getTurbulence().getY(), FLOAT_ERROR);
        assertEquals(0.09f, options.getTurbulence().getZ(), FLOAT_ERROR);

        assertEquals(.44f, options.getTexRes(), FLOAT_ERROR);

        assertTrue(options.isClampEnabled());

        assertEquals(3.45f, options.getBumpMultiplier(), FLOAT_ERROR);

        assertEquals(TextureOptions.ImfChannel.GREEN, options.getImfChannel());

        assertEquals(TextureOptions.Type.SPHERE, options.getType());
    }

    @Test
    public void readTextureOptions_failMissingFileName() throws Exception {
        try {
            MtlReader.readTextureOptions(new String[] {
                    "-blendu", "off",
                    "-blendv", "off",
                    "-boost", "0.4",
                    "-mm", "0.2", "0.33",
                    "-o", "0.01", "0.02", "0.03",
                    "-s", "0.04", "0.05", "0.06",
                    "-t", "0.07", "0.08", "0.09",
                    "-texres", ".44",
                    "-clamp", "on",
                    "-bm", "3.45",
                    "-imfchan", "g",
                    "-type", "sphere"}, 0, null);
            fail();
        } catch (IllegalStateException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void readTextureOptions_successSingleOriginOffsetValue() throws Exception {
        TextureOptions options = MtlReader.readTextureOptions(new String[] {
                "-o", "0.1", "texture.png"
        }, 0, null);

        assertEquals(.1f, options.getOriginOffset().getX(), FLOAT_ERROR);
        assertEquals(0f, options.getOriginOffset().getY(), FLOAT_ERROR);
        assertEquals(0f, options.getOriginOffset().getZ(), FLOAT_ERROR);
    }

    @Test
    public void readTextureOptions_successDoubleOriginOffsetValue() throws Exception {
        TextureOptions options = MtlReader.readTextureOptions(new String[] {
                "-o", "0.1", "0.2", "texture.png"
        }, 0, null);

        assertEquals(.1f, options.getOriginOffset().getX(), FLOAT_ERROR);
        assertEquals(.2f, options.getOriginOffset().getY(), FLOAT_ERROR);
        assertEquals(0f, options.getOriginOffset().getZ(), FLOAT_ERROR);
    }

    @Test
    public void readTextureOptions_successCheckAllImfChanValues() throws Exception {
        assertEquals(TextureOptions.ImfChannel.RED,
                MtlReader.readTextureOptions(new String[] { "-imfchan", "r", "texture.png" }, 0, null)
                        .getImfChannel());
        assertEquals(TextureOptions.ImfChannel.GREEN,
                MtlReader.readTextureOptions(new String[] { "-imfchan", "g", "texture.png" }, 0, null)
                        .getImfChannel());
        assertEquals(TextureOptions.ImfChannel.BLUE,
                MtlReader.readTextureOptions(new String[] { "-imfchan", "b", "texture.png" }, 0, null)
                        .getImfChannel());
        assertEquals(TextureOptions.ImfChannel.MATTE,
                MtlReader.readTextureOptions(new String[] { "-imfchan", "m", "texture.png" }, 0, null)
                        .getImfChannel());
        assertEquals(TextureOptions.ImfChannel.LUMINANCE,
                MtlReader.readTextureOptions(new String[] { "-imfchan", "l", "texture.png" }, 0, null)
                        .getImfChannel());
        assertEquals(TextureOptions.ImfChannel.Z_DEPTH,
                MtlReader.readTextureOptions(new String[] { "-imfchan", "z", "texture.png" }, 0, null)
                        .getImfChannel());
    }

    @Test
    public void readTextureOptions_successCheckAllTypeValues() throws Exception {
        assertEquals(TextureOptions.Type.SPHERE,
                MtlReader.readTextureOptions(new String[] { "-type", "sphere", "texture.png" }, 0, null)
                        .getType());
        assertEquals(TextureOptions.Type.CUBE_TOP,
                MtlReader.readTextureOptions(new String[] { "-type", "cube_top", "texture.png" }, 0, null)
                        .getType());
        assertEquals(TextureOptions.Type.CUBE_BOTTOM,
                MtlReader.readTextureOptions(new String[] { "-type", "cube_bottom", "texture.png" }, 0, null)
                        .getType());
        assertEquals(TextureOptions.Type.CUBE_FRONT,
                MtlReader.readTextureOptions(new String[] { "-type", "cube_front", "texture.png" }, 0, null)
                        .getType());
        assertEquals(TextureOptions.Type.CUBE_BACK,
                MtlReader.readTextureOptions(new String[] { "-type", "cube_back", "texture.png" }, 0, null)
                        .getType());
        assertEquals(TextureOptions.Type.CUBE_LEFT,
                MtlReader.readTextureOptions(new String[] { "-type", "cube_left", "texture.png" }, 0, null)
                        .getType());
        assertEquals(TextureOptions.Type.CUBE_RIGHT,
                MtlReader.readTextureOptions(new String[] { "-type", "cube_right", "texture.png" }, 0, null)
                        .getType());
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
        assertEquals("texture.png", mtl.getMapKd().getFileName());
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
        assertEquals("texture.png", mtl.getMapKd().getFileName());
    }
}
