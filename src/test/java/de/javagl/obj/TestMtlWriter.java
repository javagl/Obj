package de.javagl.obj;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestMtlWriter
{
    @Test
    public void writeMtl()
        throws IOException
    {
        String inputString = readResourceAsString(
            "/twoMaterialsA.mtl");
        List<Mtl> mtls = MtlReader.read(
            new ByteArrayInputStream(inputString.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MtlWriter.write(mtls, baos);
        String outputString = new String(baos.toByteArray());

        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }

    @Test
    public void writeComplexMtl()
        throws IOException
    {
        String inputString = readResourceAsString(
            "/complexMaterial.mtl");
        List<Mtl> mtls = MtlReader.read(
            new ByteArrayInputStream(inputString.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MtlWriter.write(mtls, baos);
        String outputString = new String(baos.toByteArray());

        //System.out.println(outputString);
        assertEquals(inputString, outputString);
    }

    @Test
    public void writePbrMtl()
        throws IOException
    {
        String inputString = readResourceAsString(
            "/pbrMaterial.mtl");
        List<Mtl> mtls = MtlReader.read(
            new ByteArrayInputStream(inputString.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MtlWriter.write(mtls, baos);
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