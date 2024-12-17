package org.jcam.effects;

import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jcam.lib.Enableable;
import org.jcam.lib.Resettable;

public abstract class Effect implements Enableable, Resettable {
    private boolean enabled;
    @Getter
    @Setter
    private boolean applied;

    public Effect() {
        this.enabled = false;
        this.applied = false;
    }

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

    public boolean isDisabled() {
        return !enabled;
    }

    @Override
    public void reset(){
        applied = false;
        enabled = true;
    }
}
