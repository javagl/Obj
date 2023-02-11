package de.javagl.obj;

/**
 * Stores the type information of bicosumer's second parameter,
 * in order to cast argument back to the required type at runtime.
 */
class TypeToken<T, R> {
    TypeToken(T type, R tokenConsumer) {
        this.type = type;
        this.tokenConsumer = tokenConsumer;
    }

    T type;
    R tokenConsumer;
}