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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * A class that may write {@link Mtl} objects into an MTL file 
 */
public class MtlWriter
{
    /**
     * Write the given {@link Mtl} objects to the given stream. The caller
     * is responsible for closing the stream.
     * 
     * @param mtls The {@link Mtl} objects
     * @param outputStream The stream to write to
     * @throws IOException If an IO error occurs
     */
    public static void write(
        Iterable<? extends Mtl> mtls, OutputStream outputStream) 
        throws IOException
    {
        OutputStreamWriter outputStreamWriter = 
            new OutputStreamWriter(outputStream);
        write(mtls, outputStreamWriter);
    }
    
    /**
     * Write the given {@link Mtl} objects to the given writer. The caller
     * is responsible for closing the writer.
     * 
     * @param mtls The {@link Mtl} objects
     * @param writer The writer to write to
     * @throws IOException If an IO error occurs
     */
    public static void write(
        Iterable<? extends Mtl> mtls, Writer writer) 
        throws IOException
    {
        for (Mtl mtl : mtls)
        {
            write(mtl, writer);
        }
    }
    
    /**
     * Write the given {@link Mtl} to the given writer
     * 
     * @param mtl The {@link Mtl}
     * @param writer The writer
     * @throws IOException If an IO error occurs
     */
    private static void write(Mtl mtl, Writer writer) throws IOException {
        writer.write(toFileString(mtl));
        writer.flush();
    }

    @VisibleForTesting
    static String toFileString(Mtl mtl) {
        StringBuilder sb = new StringBuilder("newmtl ")
                .append(mtl.getName()).append("\n")
                .append("  Ka ").append(FloatTuples.createString(mtl.getKa())).append("\n")
                .append("  Kd ").append(FloatTuples.createString(mtl.getKd())).append("\n")
                .append("  Ks ").append(FloatTuples.createString(mtl.getKs())).append("\n")
                .append("  Ns ").append(mtl.getNs()).append("\n")
                .append("  d ").append(mtl.getD()).append("\n")
                .append("  illum ").append(mtl.getIlluminationMode().getIntValue()).append("\n");
        appendTextureOptions(sb, "map_Ka", mtl.getMapKa());
        appendTextureOptions(sb, "map_Kd", mtl.getMapKd());
        appendTextureOptions(sb, "map_Ks", mtl.getMapKs());
        appendTextureOptions(sb, "map_Ns", mtl.getMapNs());
        appendTextureOptions(sb, "map_d", mtl.getMapD());
        appendTextureOptions(sb, "bump", mtl.getBumpMap());
        appendTextureOptions(sb, "disp", mtl.getDisplacementMap());
        appendTextureOptions(sb, "decal", mtl.getDecalMap());
        return sb.toString();
    }

    private static void appendTextureOptions(StringBuilder sb, String key, @Nullable TextureOptions opt) {
        if (opt != null) {
            sb.append("  ").append(key).append(toFileString(opt)).append("\n");
        }
    }
    
    @VisibleForTesting
    static String toFileString(TextureOptions opt) {
        StringBuilder sb = new StringBuilder();
        if (!opt.isBlenduEnabled()) {
            sb.append(" -blendu off");
        }
        if (!opt.isBlendvEnabled()) {
            sb.append(" -blendv off");
        }
        if (opt.getBoost() != 0f) {
            sb.append(" -boost ").append(opt.getBoost());
        }
        if (opt.getMmBrightness() != 0f || opt.getMmContrast() != 1f) {
            sb.append(" -mm ").append(opt.getMmBrightness()).append(" ").append(opt.getMmContrast());
        }
        if (opt.getOriginOffset().getX() != 0f ||
                opt.getOriginOffset().getY() != 0f ||
                opt.getOriginOffset().getZ() != 0f) {
            sb.append(" -o ").append(opt.getOriginOffset().getX())
                    .append(" ").append(opt.getOriginOffset().getY())
                    .append(" ").append(opt.getOriginOffset().getZ());
        }
        if (opt.getScale().getX() != 1f ||
                opt.getScale().getY() != 1f ||
                opt.getScale().getZ() != 1f) {
            sb.append(" -s ").append(opt.getScale().getX())
                    .append(" ").append(opt.getScale().getY())
                    .append(" ").append(opt.getScale().getZ());
        }
        if (opt.getTurbulence().getX() != 0f ||
                opt.getTurbulence().getY() != 0f ||
                opt.getTurbulence().getZ() != 0f) {
            sb.append(" -t ").append(opt.getTurbulence().getX())
                    .append(" ").append(opt.getTurbulence().getY())
                    .append(" ").append(opt.getTurbulence().getZ());
        }
        if (opt.isClampEnabled()) {
            sb.append(" -clamp on");
        }
        if (opt.getBumpMultiplier() != 0f) {
            sb.append(" -bm ").append(opt.getBumpMultiplier());
        }
        if (opt.getImfChannel() != null) {
            sb.append(" -imfchan ").append(opt.getImfChannel().getStringValue());
        }
        if (opt.getType() != null) {
            sb.append(" -type ").append(opt.getType().getStringValue());
        }
        sb.append(" ").append(opt.getFileName());
        return sb.toString();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private MtlWriter()
    {
        // Private constructor to prevent instantiation
    }
    
    
    
}
