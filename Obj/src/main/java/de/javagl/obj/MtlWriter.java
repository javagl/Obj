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
    private static void write(Mtl mtl, Writer writer)
        throws IOException
    {
        writer.write("newmtl "+mtl.getName()+"\n");
        writer.write("Ka "+
            FloatTuples.createString(mtl.getKa())+"\n");
        writer.write("Kd "+
            FloatTuples.createString(mtl.getKd())+"\n");
        writer.write("Ks "+
            FloatTuples.createString(mtl.getKs())+"\n");
        if (mtl.getMapKd() != null)
        {
            writer.write("map_Kd "+mtl.getMapKd()+"\n");
        }
        writer.write("Ns "+mtl.getNs()+"\n");
        writer.write("d "+mtl.getD()+"\n");
        
        writer.flush();
    }
    


    /**
     * Private constructor to prevent instantiation
     */
    private MtlWriter()
    {
        // Private constructor to prevent instantiation
    }
    
    
    
}
