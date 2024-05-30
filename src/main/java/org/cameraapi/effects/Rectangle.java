package org.cameraapi.effects;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import org.cameraapi.common.FaceDetector;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.io.IOException;

public class Rectangle {
    MatOfRect faces;
    FaceDetector faceDetector;
    Mat image;
    GraphicsContext display;

    public Rectangle(FaceDetector faceDetector, GraphicsContext display) {
        this.faceDetector = faceDetector;
        this.display = display;
        faces = faceDetector.detectFaces();
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
            display.drawImage(SwingFXUtils.toFXImage(FaceDetector.Mat2BufferedImage(image),null),0,0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
