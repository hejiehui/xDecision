package com.xrosstools.xdecision.ext;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.xrosstools.xdecision.Facts;
import com.xrosstools.xdecision.PathEvaluator;

public class AviatorPathEvaluator implements PathEvaluator {
    private AviatorEvaluatorInstance instance =  AviatorEvaluator.newInstance();

    @Override
    public Object evaluate(Facts facts, String factorExpression, Object[] paths) {
        Map<String, Object> env = toEnv(facts);
        for(Object path: paths) {
            Boolean match = (Boolean)instance.execute(String.format("%s %s", factorExpression, path), env, true);
            
            if(match)
                return path;
        }
        
        
        return null;
    }
    
    private Map<String, Object> toEnv(Facts facts) {
        Map<String, Object> env = new HashMap<String, Object>();
        for(String name: facts.getNames())
            env.put(name, facts.get(name));
        
        return env;
    }

}
