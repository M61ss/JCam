package org.cameraapi.common;

import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.cameraapi.effects.Flip;
import org.cameraapi.model.WebcamUtils;

public class FrameShowThread extends Thread {
    private final ChoiceBox<Webcam> webcamList;
    private Webcam activeWebcam;
    private final ImageView webcamDisplay;

    public FrameShowThread(ChoiceBox<Webcam> webcamList, Webcam activeWebcam, ImageView webcamDisplay) {
        this.webcamList = webcamList;
        this.activeWebcam = activeWebcam;
        this.webcamDisplay = webcamDisplay;
    }

    @Override
    public void run() {
        webcamList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldWebcam, newWebcam) -> {
            activeWebcam = newWebcam;
            if(!activeWebcam.isOpen()) {
                WebcamUtils.openWebcam(activeWebcam);
            }
        });

        while (!interrupted()) {
            try {
                Image image = SwingFXUtils.toFXImage(activeWebcam.getImage(), null);
                webcamDisplay.setImage(image);
                Flip.viewportFlipper(webcamDisplay);
            } catch (Exception e) {
                System.out.println("Skipped frame: " + e.getMessage());
                break;
            }
        }
    }

    public void startShowingFrame() {
        if (!isAlive()) {
            start();
        }
        if (!isAlive()) {
            throw new IllegalThreadStateException("Failed to start showing frames.");
        }
    }

    public void stopShowingFrame() throws InterruptedException {
        if (isAlive()) {
            interrupt();
            join();
            if (isAlive()) {
                throw new IllegalThreadStateException("Failed to stop frameShowThread.");
            }
        } else {
            System.out.println("frameShowThread is not running. There is nothing to stop.");
        }
    }
}