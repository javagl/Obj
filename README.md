# Obj - a simple Wavefront OBJ file loader and writer

Maven dependency:

    <dependency>
      <groupId>de.javagl</groupId>
      <artifactId>obj</artifactId>
      <version>0.2.1</version>
    </dependency>
    
Direct JAR link: [https://oss.sonatype.org/content/repositories/releases/de/javagl/obj/0.2.0/obj-0.2.0.jar](https://oss.sonatype.org/content/repositories/releases/de/javagl/obj/0.2.0/obj-0.2.0.jar)

# Samples

Samples showing how to use this library are available in the [samples directory](https://github.com/javagl/Obj/tree/master/ObjSamples/src/main/java/de/javagl/obj/samples).
    

# Overview

This is a simple loader and writer for Wavefront `.OBJ` files. The elements
that are currently supported are

 - Vertices
 - Texture coordinates
 - Normals
 - Faces (with positive or negative indices)
 - Groups
 - Material groups
 - MTL files
 
The `Obj` interface is basically an in-memory representation of an OBJ file.
It combines a `ReadableObj`, which provides the contents of the OBJ file,
and a `WritableObj`, which may receive elements like vertices and faces
in order to build an OBJ in memory.

The `ObjReader` class may either create a new `Obj` object directly 
from an input stream, or pass the elements that are read from the input 
stream to a `WritableObj`.

The `ObjWriter` class offers a method to write a `ReadableObj` object
to an output stream.

The `ObjData` class offers various methods to obtain the data that is
stored in a `ReadableObj` as plain arrays or *direct* buffers. 

The `ObjUtils` class offers basic utility methods for general operations
on the OBJ data. For example, it may

 - convert a single group of an OBJ into a new OBJ
 - triangulate OBJ data
 - make sure that texture coordinates or or normal coordinates are unique
   for each vertex
 - convert an OBJ to an OBJ that is uses the same index sets for vertices,
   texture coordinates and normals
   
The latter operations are also summarized in one dedicated method, which
aims at preparing the OBJ so that it may easily be rendered with OpenGL:
    

    FileInputStream inputStream = ...;

    Obj obj = ObjUtils.convertToRenderable(
        ObjReader.read(inputStream));

    IntBuffer indices = ObjData.getFaceVertexIndices(obj);
    FloatBuffer vertices = ObjData.getVertices(obj);
    FloatBuffer texCoords = ObjData.getTexCoords(obj);
    FloatBuffer normals = ObjData.getNormals(obj);

These buffers may directly be used as the data for vertex buffer objects (VBO)
in OpenGL. 


# Change log

**0.2.1** (2015-10-26)

- Bugfix: Made `AbstractWritableObj#addNormal(FloatTuple)` non-final
- Added methods in `ObjReader` and `MtlReader` that accept a `Reader`
  instead of an `InputStream`, and methods in `ObjWriter` and 
  `MtlWriter` that accept a `Writer` instead of an `OutputStream`
- Added implementation of `ObjUtils.convertToRenderable` that
   receives a `WritableObj`  
  
**0.2.0** (2015-10-05) : 

- Initial public release on GitHub and Maven Central
   

