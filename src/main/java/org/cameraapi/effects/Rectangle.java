package org.cameraapi.effects;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import org.cameraapi.common.FaceDetector;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class Rectangle {
    MatOfRect faces;
    FaceDetector faceDetector;
    Mat image;
    ImageView display;

    public Rectangle(MatOfRect faces, FaceDetector faceDetector, ImageView display) {
        this.display = display;
        this.faces = faces;
        this.faceDetector = faceDetector;
        image = faceDetector.getImage();
        drawRect();
    }

    private void drawRect() {
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
    }


    public void markFaces(){
        try {
            display.setImage(SwingFXUtils.toFXImage(FaceDetector.Mat2BufferedImage(image),null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
