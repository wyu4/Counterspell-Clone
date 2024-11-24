package GUIClasses;

import GUIClasses.AccurateUIComponents.AccuratePanel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;

import java.awt.Color;

public class VideoPanel extends JFXPanel {
    private final MediaView viewer;

    public VideoPanel(MediaPlayer player) {
        setName("Video Panel");
        setBackground(new Color(0, 0, 0));
        setFocusable(false);

        // Create a media viewer (basically allows the user to view the data being outputted by MediaPlayer)
        viewer = new MediaView(player);
        viewer.setFocusTraversable(false);

        // Create a StackPane layout (will stack its components back-to-front)
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: white"); // Setting a background for space that isn't taken up by the video (it's a little weird, it takes in a String, not a Color obj)
        layout.setFocusTraversable(false);

        // Creating a new scene (type of container, like the ContentPane of a JFrame)
        Scene scene = new Scene(layout);

        // Setup
        layout.getChildren().add(viewer);

        // Apply the scene to the JFXPanel
        setScene(scene);
    }

    public void tick(float timeMod) {
        try {
            // Center video (centering the viewer)
            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            viewer.setX((screen.getWidth() - getSize().width) / 2);
            viewer.setY((screen.getHeight() - getSize().height) / 2);

            // Resize video (resizing the viewer)
            DoubleProperty width = viewer.fitWidthProperty();
            DoubleProperty height = viewer.fitHeightProperty();
            width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
            viewer.setPreserveRatio(true); // Keep the aspect ratio
        } catch (IllegalStateException ignore) {}
    }
}
