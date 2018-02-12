/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package de.javagl.obj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A class that may read MTL data, and return the materials as a
 * list of {@link Mtl} objects.
 */
public class MtlReader
{
    /**
     * Read the MTL data from the given stream, and return
     * it as {@link Mtl} objects.
     * The caller is responsible for closing the given stream.
     *
     * @param inputStream The stream to read from.
     * @return The list of Mtl object.
     * @throws IOException If an IO error occurs
     */
    public static List<Mtl> read(InputStream inputStream)
        throws IOException
    {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream));
        return readImpl(reader);
    }

    /**
     * Read the MTL data from the given reader, and return
     * it as {@link Mtl} objects.
     * The caller is responsible for closing the given reader.
     *
     * @param reader The reader to read from.
     * @return The list of Mtl object.
     * @throws IOException If an IO error occurs
     */
    public static List<Mtl> read(Reader reader)
        throws IOException
    {
        if (reader instanceof BufferedReader)
        {
            return readImpl((BufferedReader)reader);
        }
        return readImpl(new BufferedReader(reader));
    }

    /**
     * Read the MTL data from the given reader, and return
     * it as {@link Mtl} objects.
     * The caller is responsible for closing the given reader.
     *
     * @param reader The reader to read from.
     * @return The list of Mtl object.
     * @throws IOException If an IO error occurs
     */
    private static List<Mtl> readImpl(BufferedReader reader)
        throws IOException
    {
        List<Mtl> mtlList = new ArrayList<Mtl>();

        DefaultMtl currentMtl = null;

        while(true)
        {
            String line = reader.readLine();
            if(line == null)
            {
                break;
            }

            line = line.trim();

            //System.out.println("read line: "+line);

            // Combine lines that have been broken
            boolean finished = false;
            while(line.endsWith("\\"))
            {
                line = line.substring(0, line.length() - 2);
                String nextLine = reader.readLine();
                if (nextLine == null)
                {
                    finished = true;
                    break;
                }
                line += " " + nextLine;
            }
            if (finished)
            {
                break;
            }

            StringTokenizer st = new StringTokenizer(line);
            if(!st.hasMoreTokens())
            {
                continue;
            }

            String identifier = st.nextToken();
            if (identifier.equalsIgnoreCase("newmtl"))
            {
                String name = line.substring("newmtl".length()).trim();
                currentMtl = new DefaultMtl(name);
                mtlList.add(currentMtl);
            }
            else if (identifier.equalsIgnoreCase("Ka"))
            {
                float ka0 = parse(st.nextToken());
                float ka1 = parse(st.nextToken());
                float ka2 = parse(st.nextToken());
                currentMtl.setKa(ka0, ka1, ka2);
            }
            else if (identifier.equalsIgnoreCase("Ks"))
            {
                float ks0 = parse(st.nextToken());
                float ks1 = parse(st.nextToken());
                float ks2 = parse(st.nextToken());
                currentMtl.setKs(ks0, ks1, ks2);
            }
            else if (identifier.equalsIgnoreCase("Kd"))
            {
                float kd0 = parse(st.nextToken());
                float kd1 = parse(st.nextToken());
                float kd2 = parse(st.nextToken());
                currentMtl.setKd(kd0, kd1, kd2);
            }
            else if (identifier.equalsIgnoreCase("map_Kd"))
            {
                String mapKd = line.substring("map_Kd".length()).trim();
                currentMtl.setMapKd(mapKd);
            }
            else if (identifier.equalsIgnoreCase("d"))
            {
                float d = parse(st.nextToken());
                currentMtl.setD(d);
            }
            else if (identifier.equalsIgnoreCase("Ns"))
            {
                float ns = parse(st.nextToken());
                currentMtl.setNs(ns);
            }
        }
        return mtlList;
    }

    /**
     * Parse a float from the given string, wrapping number format
     * exceptions into an IOException
     *
     * @param s The string
     * @return The float
     * @throws IOException If the string does not contain a valid float value
     */
    private static float parse(String s) throws IOException
    {
        try
        {
            return Float.parseFloat(s);
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
    }


    /**
     * Private constructor to prevent instantiation
     */
    private MtlReader()
    {
        // Private constructor to prevent instantiation
    }
}
