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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

        Mtl currentMtl = null;

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

            line = line.trim();
            if (line.startsWith("newmtl"))
            {
                String name = line.substring("newmtl".length()).trim();
                currentMtl = new DefaultMtl(name);
                mtlList.add(currentMtl);
            }
            else if (!line.startsWith("#") && !line.isEmpty())
            {
                if (currentMtl == null)
                {
                    throw new IOException(
                        "Missing newmtl statement before " + line);
                }
                processLine(currentMtl, line);
            }
        }
        return mtlList;
    }

    /**
     * Process one (trimmed) line that is part of a <code>newmtl</code>
     * material definition, and write the result into the given {@link Mtl}.
     *
     * @param mtl The {@link Mtl}
     * @param line The line
     * @throws IOException If an IO error occurs
     */
    private static void processLine(Mtl mtl, String line)
        throws IOException
    {
        Queue<String> tokens =
            new LinkedList<String>(Arrays.asList(line.split("[ \t\n\r\f]+")));
        String command = tokens.poll();

        // Illumination mode
        if (command.equalsIgnoreCase("illum"))
        {
            int value = Utils.parseInt(tokens.poll());
            mtl.setIllum(value);
        }

        // Color values: R, and optional G and B
        if (command.equalsIgnoreCase("Ka"))
        {
            Float[] values = Utils.parseFloats(tokens, 3);
            mtl.setKa(values[0], values[1], values[2]);
        }
        else if (command.equalsIgnoreCase("Kd"))
        {
            Float[] values = Utils.parseFloats(tokens, 3);
            mtl.setKd(values[0], values[1], values[2]);
        }
        else if (command.equalsIgnoreCase("Ks"))
        {
            Float[] values = Utils.parseFloats(tokens, 3);
            mtl.setKs(values[0], values[1], values[2]);
        }
        else if (command.equalsIgnoreCase("Tf"))
        {
            Float[] values = Utils.parseFloats(tokens, 3);
            mtl.setTf(values[0], values[1], values[2]);
        }
        
        // Color values for PBR
        else if (command.equalsIgnoreCase("Ke"))
        {
            Float[] values = Utils.parseFloats(tokens, 3);
            mtl.setKe(values[0], values[1], values[2]);
        }
        

        // Single float values
        else if (command.equalsIgnoreCase("Tr"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setD(1.0f - value);
        }
        else if (command.equalsIgnoreCase("sharpness"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setSharpness(value);
        }
        else if (command.equalsIgnoreCase("d"))
        {
            String token = tokens.peek();
            if ("-halo".equals(token))
            {
                mtl.setHalo(true);
                tokens.poll();
            }
            float value = Utils.parseFloat(tokens.poll());
            mtl.setD(value);
        }
        else if (command.equalsIgnoreCase("Ni"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setNi(value);
        }
        else if (command.equalsIgnoreCase("Ns"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setNs(value);
        }

        // Single float values for PBR
        else if (command.equalsIgnoreCase("Pr"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setPr(value);
        }
        else if (command.equalsIgnoreCase("Pm"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setPm(value);
        }
        else if (command.equalsIgnoreCase("Ps"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setPs(value);
        }
        else if (command.equalsIgnoreCase("Pc"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setPc(value);
        }
        else if (command.equalsIgnoreCase("Pcr"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setPcr(value);
        }
        else if (command.equalsIgnoreCase("aniso"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setAniso(value);
        }
        else if (command.equalsIgnoreCase("anisor"))
        {
            float value = Utils.parseFloat(tokens.poll());
            mtl.setAnisor(value);
        }
        

        // Texture map definitions
        else
        {
            String textureOptionsString = Utils.consumeNextToken(line);
            readTextureMap(mtl, command, textureOptionsString);
        }
    }

    
    /**
     * Process the line of an MTL file that is supposed to contain a
     * texture map definition, and write the resulting texture
     * information into the given {@link Mtl}.<br>
     * <br>
     * The texture definition will be determined from the given command,
     * which may, for example, be <code>"map_Ka"</code> or
     * <code>"refl"</code>
     *
     * @param mtl The {@link Mtl}
     * @param command The command at the beginning of the line
     * @param textureOptionsString The texture options, i.e. the part of 
     * the line after the command
     * @throws IOException If an IO error occurs
     */
    private static void readTextureMap(
        Mtl mtl, String command, String textureOptionsString)
            throws IOException
    {
        if (command.equalsIgnoreCase("map_Ka"))
        {
            mtl.setMapKaOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("map_Kd"))
        {
            mtl.setMapKdOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("map_Ks"))
        {
            mtl.setMapKsOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("map_d"))
        {
            mtl.setMapDOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("map_Ns"))
        {
            mtl.setMapNsOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("bump")
            || command.equalsIgnoreCase("map_bump"))
        {
            mtl.setBumpOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("disp"))
        {
            mtl.setDispOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("decal"))
        {
            mtl.setDecalOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("refl"))
        {
            TextureOptions refl = readTextureOptions(textureOptionsString);
            mtl.getReflOptions().add(refl);
        }
        
        // Texture map definitions for PBR
        else if (command.equalsIgnoreCase("map_Pr"))
        {
            mtl.setMapPrOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("map_Pm"))
        {
            mtl.setMapPmOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("map_Ps"))
        {
            mtl.setMapPsOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("map_Ke"))
        {
            mtl.setMapKeOptions(readTextureOptions(textureOptionsString));
        }
        else if (command.equalsIgnoreCase("norm"))
        {
            mtl.setNormOptions(readTextureOptions(textureOptionsString));
        }
        
    }


    /**
     * Process the given texture options string and construct a
     * {@link TextureOptions} object from it
     *
     * @param textureOptionsString The texture options, i.e. the part of 
     * the line after the command
     * @return The {@link TextureOptions}
     * @throws IOException If an IO error occurs
     */
    static TextureOptions readTextureOptions(String textureOptionsString)
        throws IOException {

        String s = textureOptionsString;
        DefaultTextureOptions textureOptions = new DefaultTextureOptions();
        while (true)
        {
            // If the next token is one of the known options, then consume
            // the token. The next tokens will then contain the value(s)
            // of the options. These values are parsed, consume, and their
            // values are assigned to the texture options
            String optionName = Utils.extractNextToken(s);
            if (optionName.equalsIgnoreCase("-blendu"))
            {
                s = Utils.consumeNextToken(s);
                boolean value = Utils.parseNextBoolean(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setBlendu(value);
            }
            else if (optionName.equalsIgnoreCase("-blendv"))
            {
                s = Utils.consumeNextToken(s);
                boolean value = Utils.parseNextBoolean(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setBlendv(value);
            }
            else if (optionName.equalsIgnoreCase("-boost"))
            {
                s = Utils.consumeNextToken(s);
                float value = Utils.parseNextFloat(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setBoost(value);
            }
            else if (optionName.equalsIgnoreCase("-cc"))
            {
                s = Utils.consumeNextToken(s);
                boolean value = Utils.parseNextBoolean(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setCc(value);
            }
            else if (optionName.equalsIgnoreCase("-mm"))
            {
                s = Utils.consumeNextToken(s);
                float base = Utils.parseNextFloat(s);
                s = Utils.consumeNextToken(s);
                float gain = Utils.parseNextFloat(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setMm(base, gain);
            }
            else if (optionName.equalsIgnoreCase("-o"))
            {
                s = Utils.consumeNextToken(s);

                Float[] values = new Float[3];

                // The u value is required 
                String su = Utils.extractNextToken(s);
                s = Utils.consumeNextToken(s);
                values[0] = Utils.parseFloat(su);

                // The v/w values are optional. Only parse and consume
                // the next tokens if they are float values.
                String sv = Utils.extractNextToken(s);
                if (Utils.isFloat(sv))
                {
                    values[1] = Utils.parseFloat(sv);
                    s = Utils.consumeNextToken(s);
                    String sw = Utils.extractNextToken(s);
                    if (Utils.isFloat(sw))
                    {
                        values[2] = Utils.parseFloat(sw);
                        s = Utils.consumeNextToken(s);
                    }
                }
                textureOptions.setO(values[0], values[1], values[2]);
            }
            else if (optionName.equalsIgnoreCase("-s"))
            {
                s = Utils.consumeNextToken(s);

                Float[] values = new Float[3];

                // The u value is required 
                String su = Utils.extractNextToken(s);
                s = Utils.consumeNextToken(s);
                values[0] = Utils.parseFloat(su);

                // The v/w values are optional. Only parse and consume
                // the next tokens if they are float values.
                String sv = Utils.extractNextToken(s);
                if (Utils.isFloat(sv))
                {
                    values[1] = Utils.parseFloat(sv);
                    s = Utils.consumeNextToken(s);
                    String sw = Utils.extractNextToken(s);
                    if (Utils.isFloat(sw))
                    {
                        values[2] = Utils.parseFloat(sw);
                        s = Utils.consumeNextToken(s);
                    }
                }
                textureOptions.setS(values[0], values[1], values[2]);
            }
            else if (optionName.equalsIgnoreCase("-t"))
            {
                s = Utils.consumeNextToken(s);

                Float[] values = new Float[3];

                // The u value is required 
                String su = Utils.extractNextToken(s);
                s = Utils.consumeNextToken(s);
                values[0] = Utils.parseFloat(su);

                // The v/w values are optional. Only parse and consume
                // the next tokens if they are float values.
                String sv = Utils.extractNextToken(s);
                if (Utils.isFloat(sv))
                {
                    values[1] = Utils.parseFloat(sv);
                    s = Utils.consumeNextToken(s);
                    String sw = Utils.extractNextToken(s);
                    if (Utils.isFloat(sw))
                    {
                        values[2] = Utils.parseFloat(sw);
                        s = Utils.consumeNextToken(s);
                    }
                }
                textureOptions.setT(values[0], values[1], values[2]);
            }
            else if (optionName.equalsIgnoreCase("-texres"))
            {
                s = Utils.consumeNextToken(s);
                float value = Utils.parseNextFloat(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setTexres(value);
            }
            else if (optionName.equalsIgnoreCase("-clamp"))
            {
                s = Utils.consumeNextToken(s);
                boolean value = Utils.parseNextBoolean(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setClamp(value);
            }
            else if (optionName.equalsIgnoreCase("-bm"))
            {
                s = Utils.consumeNextToken(s);
                float value = Utils.parseNextFloat(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setBm(value);
            }
            else if (optionName.equalsIgnoreCase("-imfchan"))
            {
                s = Utils.consumeNextToken(s);
                String value = Utils.extractNextToken(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setImfchan(value);
            }
            else if (optionName.equalsIgnoreCase("-type"))
            {
                s = Utils.consumeNextToken(s);
                String value = Utils.extractNextToken(s);
                s = Utils.consumeNextToken(s);
                textureOptions.setType(value);
            }
            else
            {
                // The current token is not one of the known options.
                // Treat the remaining part of the options (including 
                // the current token) as the "file name" 
                textureOptions.setFileName(s);
                break;
            }
        }
        return textureOptions;
    }
    
    
    
    

    /**
     * Private constructor to prevent instantiation
     */
    private MtlReader()
    {
        // Private constructor to prevent instantiation
    }
}
