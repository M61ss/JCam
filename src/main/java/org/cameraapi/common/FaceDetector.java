package org.cameraapi.common;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class FaceDetector {
    private final CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt2.xml");
    private final Mat image;

    public FaceDetector(Image image) {
        Objects.requireNonNull(image);
        this.image = matify(SwingFXUtils.fromFXImage(image, null));
    }


    public MatOfRect detectFaces() {
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        return faceDetections;
    }


    // Not ashamed to admit that I copied it without knowing how it works...
    public static Mat matify(BufferedImage sourceImg) {

        DataBuffer dataBuffer = sourceImg.getRaster().getDataBuffer();
        byte[] imgPixels = null;
        Mat imgMat = null;

        int width = sourceImg.getWidth();
        int height = sourceImg.getHeight();

        if(dataBuffer instanceof DataBufferByte) {
            imgPixels = ((DataBufferByte)dataBuffer).getData();
        }

        if(dataBuffer instanceof DataBufferInt) {

            int byteSize = width * height;
            imgPixels = new byte[byteSize*3];

            int[] imgIntegerPixels = ((DataBufferInt)dataBuffer).getData();

            for(int p = 0; p < byteSize; p++) {
                imgPixels[p * 3] = (byte) ((imgIntegerPixels[p] & 0x00FF0000) >> 16);
                imgPixels[p*3 + 1] = (byte) ((imgIntegerPixels[p] & 0x0000FF00) >> 8);
                imgPixels[p*3 + 2] = (byte) (imgIntegerPixels[p] & 0x000000FF);
            }
        }

        if(imgPixels != null) {
            imgMat = new Mat(height, width, CvType.CV_8UC3);
            imgMat.put(0, 0, imgPixels);
        }


        return imgMat;
    }

    // I'm not ashamed for this one either
    public static BufferedImage Mat2BufferedImage(Mat matrix) throws IOException {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        byte[] ba =mob.toArray();
        return ImageIO.read(new ByteArrayInputStream(ba));
    }


    public Mat getImage() {
        return image;
    }
}
