package org.jcam.lib;

import javafx.scene.image.ImageView;
import lombok.NonNull;

public interface Applicable {
    void apply(@NonNull ImageView imageAffected);
}
