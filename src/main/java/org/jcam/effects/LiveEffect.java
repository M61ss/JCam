package org.jcam.effects;

import org.jcam.lib.LiveEffectSingleton;

public abstract class LiveEffect extends Effect implements LiveEffectSingleton {
    public LiveEffect() {
        super();
    }

    @Override
    public void apply() {
        throw new IllegalCallerException("This method is unsupported by " + this.getClass().getName());
    }
}
