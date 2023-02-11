package de.javagl.obj;


import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;


public class PbrPropertyReader {

    static HashMap<String, TypeToken<Class<?>, BiConsumer<Mtl, ?>>> TOKENS = new HashMap<>();

    //register all pbr attributes here
    static {
        BiConsumer<Mtl, Float> setPr = Mtl::setPr;
        TOKENS.put("pr", new TypeToken<>(Float.class, setPr));
    }

    //use biconsumer so there's no more if else
    public static void readPbrProperty(Mtl mtl, String command, Queue<String> tokens) throws IOException {
        //Read command
        TypeToken<Class<?>, BiConsumer<Mtl, ?>> typeToken = TOKENS.get(command.toLowerCase());
        if (typeToken == null) {
            return;
        }
        if (typeToken.type == Float.class) {
            ((BiConsumer<Mtl, Float>) typeToken.tokenConsumer).accept(mtl, Utils.parseFloat(tokens.poll()));
        }//TODO support String as input parameter, for the various map attributes
    }
}
