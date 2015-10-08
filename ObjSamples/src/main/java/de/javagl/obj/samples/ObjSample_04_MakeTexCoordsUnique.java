/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.obj.samples;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjFace;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import de.javagl.obj.ReadableObj;

/**
 * An example showing how to make sure that vertex texture coordinates
 * are unique. When two faces of the OBJ refer to the same vertex, but
 * with different texture coordinates, then the vertex will be 
 * duplicated so that it may be used once with each texture coordinate.
 */
@SuppressWarnings("javadoc")
public class ObjSample_04_MakeTexCoordsUnique 
{
    public static void main(String[] args) throws Exception
    {
        // Read an OBJ file
        InputStream objInputStream = 
            new FileInputStream("./data/simpleSample.obj");
        Obj obj = ObjReader.read(objInputStream);
        
        System.out.println(
            "Mapping from vertex to texture coordinate indices before " + 
            "making texture coordinates unique:");
        printVertexToTexCoords(obj);

        // Make texture coordinates unique
        Obj unique = ObjUtils.makeTexCoordsUnique(obj);
        
        System.out.println(
            "Mapping from vertex to texture coordinate indices after " + 
            "making texture coordinates unique:");
        printVertexToTexCoords(unique);
    }
    
    private static void printVertexToTexCoords(ReadableObj obj)
    {
        Map<Integer, Set<Integer>> map = computeVertexToTexCoordsIndices(obj);
        for (int i=0; i<obj.getNumVertices(); i++)
        {
            System.out.println(
                "Vertex " + i + " of " + obj.getNumVertices() + 
                " is used with texture coordinates "
                + map.get(i));
        }
    }
    
    private static Map<Integer, Set<Integer>> computeVertexToTexCoordsIndices(
        ReadableObj obj)
    {
        Map<Integer, Set<Integer>> map = 
            new IdentityHashMap<Integer, Set<Integer>>();
        for (int i = 0; i < obj.getNumFaces(); i++)
        {
            ObjFace face = obj.getFace(i);
            for (int j=0; j<face.getNumVertices(); j++)
            {
                int vertexIndex = face.getVertexIndex(j);
                int texCoordIndex = face.getTexCoordIndex(j);
                Set<Integer> list = map.get(vertexIndex);
                if (list == null)
                {
                    list = new LinkedHashSet<Integer>();
                    map.put(vertexIndex, list);
                }
                list.add(texCoordIndex);
            }
        }
        return map;
    }
}
