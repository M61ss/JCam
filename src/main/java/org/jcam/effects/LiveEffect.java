package org.jcam.effects;

import javafx.scene.image.ImageView;
import lombok.NonNull;
import org.jcam.lib.LiveEffectSingleton;

public abstract class LiveEffect extends Effect implements LiveEffectSingleton {
    public LiveEffect() {
        super();
    }
}
