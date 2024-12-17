package org.jcam.effects;

import org.jcam.lib.LiveEffectSingleton;

public abstract class LiveEffect extends Effect implements LiveEffectSingleton {
    public LiveEffect() {
        super();
    }

    public static LiveEffect getInstance() {
        return null;
    }
}
