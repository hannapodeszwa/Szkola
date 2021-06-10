package pl.polsl.controller;

import java.util.Map;

public interface ParametrizedController {
    default void passArguments(Map params) {
        if (!params.isEmpty())
            System.out.println("Przesłano parametry, ale funkcja do ich obsługi nie jest zaimplementowana!");
    }
}
