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
import com.google.common.annotations.VisibleForTesting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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

            currentMtl = parseLine(line, mtlList, currentMtl);
        }
        return mtlList;
    }

    @VisibleForTesting
    @Nullable
    static DefaultMtl parseLine(String line, List<Mtl> mtlList, @Nullable DefaultMtl currentMtl) throws IOException {
        String[] tokens = line.split("[ \t\n\r\f]+");
        if(tokens.length == 0) {
            return currentMtl;
        }

        for (int i = 0; i < tokens.length; ++i) {
            switch (tokens[i].toLowerCase()) {
                case "newmtl": {
                    String name = line.substring("newmtl".length()).trim();
                    currentMtl = new DefaultMtl(name);
                    mtlList.add(currentMtl);
                    i = tokens.length;
                    break;
                }
                case "ka": {
                    float ka0 = parse(tokens[++i]);
                    float ka1 = parse(tokens[++i]);
                    float ka2 = parse(tokens[++i]);
                    currentMtl.setKa(ka0, ka1, ka2);
                    break;
                }
                case "ks": {
                    float ks0 = parse(tokens[++i]);
                    float ks1 = parse(tokens[++i]);
                    float ks2 = parse(tokens[++i]);
                    currentMtl.setKs(ks0, ks1, ks2);
                    break;
                }
                case "kd": {
                    float kd0 = parse(tokens[++i]);
                    float kd1 = parse(tokens[++i]);
                    float kd2 = parse(tokens[++i]);
                    currentMtl.setKd(kd0, kd1, kd2);
                    break;
                }
                case "map_ka": {
                    currentMtl.setMapKa(readTextureOptions(tokens, ++i, null));
                    break;
                }
                case "map_kd": {
                    currentMtl.setMapKd(readTextureOptions(tokens, ++i, null));
                    break;
                }
                case "map_ks": {
                    currentMtl.setMapKs(readTextureOptions(tokens, ++i, null));
                    break;
                }
                case "map_ns": {
                    currentMtl.setMapNs(readTextureOptions(tokens, ++i, null));
                    break;
                }
                case "map_d": {
                    currentMtl.setMapD(readTextureOptions(tokens, ++i, null));
                    break;
                }
                case "bump":
                case "map_bump": {
                    currentMtl.setBumpMap(readTextureOptions(tokens, ++i, TextureOptions.ImfChannel.LUMINANCE));
                    break;
                }
                case "disp": {
                    currentMtl.setDisplacementMap(readTextureOptions(tokens, ++i, null));
                    break;
                }
                case "decal": {
                    currentMtl.setDecalMap(readTextureOptions(tokens, ++i, TextureOptions.ImfChannel.MATTE));
                    break;
                }
                case "tr": {
                    float tr = parse(tokens[++i]);
                    currentMtl.setD(1f - tr);
                    break;
                }
                case "d": {
                    float d = parse(tokens[++i]);
                    currentMtl.setD(d);
                    break;
                }
                case "ns": {
                    float ns = parse(tokens[++i]);
                    currentMtl.setNs(ns);
                    break;
                }
                case "illum": {
                    currentMtl.setIlluminationMode(Mtl.IlluminationMode.fromIntValue(parseInt(tokens[++i])));
                    break;
                }
            }
        }
        return currentMtl;
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
     * Parse a string as a boolean converting "true" and "on" to true, anything else to false.
     *
     * @param s The string to parse.
     * @return Whether the string represents a true boolean value.
     */
    private static boolean parseBoolean(@NotNull String s) {
        switch (s.toLowerCase()) {
            case "true":
            case "on":
                return true;
            default:
                return false;
        }
    }

    @Nullable
    private static Float parseFloatOrNull(String s) {
        try {
            return parse(s);
        } catch (IOException ex) {
            return null;
        }
    }

    private static int parseInt(@NotNull String s) throws IOException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            throw new IOException(ex);
        }
    }

    private static int readUpToNFloats(String[] tokens, int i, float[] out) throws IOException {
        int numRead = 0;
        out[numRead] = parse(tokens[i + numRead++]);
        while (numRead < out.length) {
            Float value = parseFloatOrNull(tokens[i + numRead]);
            if (value == null) {
                break;
            }
            out[numRead++] = value;
        }
        return numRead;
    }

    @VisibleForTesting
    @NotNull
    static TextureOptions readTextureOptions(String[] tokens, int i,
            TextureOptions.ImfChannel defaultImfChannel) throws IOException {
        TextureOptions.Builder builder = new TextureOptions.Builder()
                .setImfChannel(defaultImfChannel);
        while (i < tokens.length) {
            switch (tokens[i++].toLowerCase()) {
                case "-blendu": {
                    builder.setIsBlenduEnabled(parseBoolean(tokens[i++]));
                    break;
                }
                case "-blendv": {
                    builder.setIsBlendvEnabled(parseBoolean(tokens[i++]));
                    break;
                }
                case "-boost": {
                    builder.setBoost(parse(tokens[i++]));
                    break;
                }
                case "-mm": {
                    builder.setModifyMapValues(parse(tokens[i++]), parse(tokens[i++]));
                    break;
                }
                case "-o": {
                    float[] values = new float[3];
                    i += readUpToNFloats(tokens, i, values);
                    builder.setOriginOffset(values[0], values[1], values[2]);
                    break;
                }
                case "-s": {
                    float[] values = new float[3];
                    i += readUpToNFloats(tokens, i, values);
                    builder.setScale(values[0], values[1], values[2]);
                    break;
                }
                case "-t": {
                    float[] values = new float[3];
                    i += readUpToNFloats(tokens, i, values);
                    builder.setTurbulence(values[0], values[1], values[2]);
                    break;
                }
                case "-texres": {
                    builder.setTextureResolution(parse(tokens[i++]));
                    break;
                }
                case "-clamp": {
                    builder.setIsClampEnabled(parseBoolean(tokens[i++]));
                    break;
                }
                case "-bm": {
                    builder.setBumpMultiplier(parse(tokens[i++]));
                    break;
                }
                case "-imfchan": {
                    builder.setImfChannel(TextureOptions.ImfChannel.fromStringValue(tokens[i++]));
                    break;
                }
                case "-type": {
                    builder.setType(TextureOptions.Type.fromStringValue(tokens[i++]));
                    break;
                }
                default: {
                    builder.setFileName(tokens[i - 1]);
                    break;
                }
            }
        }
        return builder.build();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private MtlReader()
    {
        // Private constructor to prevent instantiation
    }
}
