package org.jcam.lib;

import org.jcam.effects.LiveEffect;

import java.util.HashMap;
import java.util.Map;

public interface LiveEffectSingleton {
    Map<Class<? extends LiveEffect>, LiveEffect> uniqueInstances = new HashMap<>(); // Map already existing classes and their instances

    static <T extends LiveEffect> LiveEffect getInstance(Class<T> effectClass) {
        try {
            // Checks if the class instance has already being created
            if(uniqueInstances.get(effectClass) == null) {
                // if not creates a new instance
                uniqueInstances.put(effectClass, effectClass.getConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // In any case returns the unique Instance. So it is recommended to use this every time we want to access instance methods of an Effect class
        return uniqueInstances.get(effectClass);
    }
}
