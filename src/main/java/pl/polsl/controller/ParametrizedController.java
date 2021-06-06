package pl.polsl.controller;

import java.util.Map;

public interface ParametrizedController {
    public void passArguments(Map<String,String> params);
}
