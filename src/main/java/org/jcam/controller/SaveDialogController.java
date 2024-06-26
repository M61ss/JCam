package org.jcam.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import lombok.NonNull;
import org.jcam.lib.AlertWindows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class SaveDialogController {
    @FXML
    StackPane root;
    @FXML
    Button selectDirButton;
    @FXML
    ChoiceBox<String> typeChoiceBox;
    @FXML
    TextField selectDirTxtField;
    @FXML
    TextField fileNameTxtField;
    @FXML
    ImageView preview;

    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private Image imageToSave;

    public void initialize() {
        fileNameTxtField.setEditable(true);
        selectDirTxtField.setEditable(false);
    }

    public void save() {
        if (selectDirTxtField.getText().isEmpty()) {
            AlertWindows.throwAlert("Error","No directory selected","A directory is required");
            throw new RuntimeException();
        }
        if (fileNameTxtField.getText().isEmpty()) {
            AlertWindows.throwAlert("Error","No file selected","A filename is required");
            throw new RuntimeException();
        }
        File target = createFile();
        try {
            if (!target.createNewFile()) {
                AlertWindows.throwAlert("Error","Could not create new file","File already exists");
                throw new RuntimeException();
            }
        } catch (IOException e) {
            AlertWindows.throwAlert("Error","Could not create new file","An unexpected error occurred");
            throw new RuntimeException(e);
            // You could launch an alert pane...
        }
        try {
            // Necessary step to work with jpg and jpeg.
            // You can find the explanation for why it is this way at this link:
            // https://stackoverflow.com/a/57674578
            BufferedImage awtImage = new BufferedImage((int)imageToSave.getWidth(), (int)imageToSave.getHeight(), BufferedImage.TYPE_INT_RGB);
            SwingFXUtils.fromFXImage(imageToSave, awtImage);
            //Removing the dot from the type, since it must not be included
            ImageIO.write(awtImage, typeChoiceBox.getSelectionModel().getSelectedItem().substring(1), target);
        } catch (IOException e) {
            AlertWindows.throwAlert("Error","Could not save image","An unexpected error occurred");
            throw new RuntimeException(e);
        }
    }

    public void onSelectDir() {
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = directoryChooser.showDialog(root.getScene().getWindow());
        if (dir != null) {
            selectDirTxtField.setText(dir.getAbsolutePath());
        }
    }

    private File createFile() {
        String dir = selectDirTxtField.getText();
        String separator = FileSystems.getDefault().getSeparator();
        String filename = fileNameTxtField.getText();
        String extension = typeChoiceBox.getSelectionModel().getSelectedItem();
        return new File(dir + separator + filename + extension);
    }

    public void initTypeChoiceBox() {
        typeChoiceBox.getItems().addAll(".png", ".jpg", ".jpeg");
        typeChoiceBox.getSelectionModel().selectFirst();
    }

    public void initPreview(@NonNull Image image) {
        preview.setImage(image);
        imageToSave = image;
    }
}
