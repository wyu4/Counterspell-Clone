package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateButton;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Menu extends AccuratePanel {
    private final AccurateLabel titleLabel, background;
    private final AccurateButton playButton;
    private final AccurateButton closeButton;
    private final Font titleFont;
    private float titleFontSize, backgroundFontSize;

    public Menu() {
        setBackground(new Color(0, 0, 0));

        titleFont = ResourcesManager.getAsFont(ResourceEnum.BungeeSpice_ttf);

        titleLabel = new AccurateLabel("Title");
        titleLabel.setText("CLONE");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setAnchorPoint(0.5f, 0.1f);
        titleLabel.setFont(titleFont);

        background = new AccurateLabel("Background");
        background.setText("");
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setVerticalAlignment(SwingConstants.CENTER);
        background.setForeground(new Color(51, 51, 51));
        background.setWrapped(true);

        closeButton = new AccurateButton("CloseButton");
        closeButton.setBackground(new Color(255, 0, 0));
        closeButton.setAnchorPoint(0.1f, 0.1f);

        playButton = new AccurateButton("PlayButton");
        playButton.setBackground(new Color(0, 255, 0));
        playButton.setAnchorPoint(0.5f, 0.9f);

        titleFontSize = 0f;
        backgroundFontSize = 0f;

        add(titleLabel);
        add(playButton);
        add(closeButton);
        add(background);
    }

    public void tick(float timeMod) {
        FloatCoordinate screenSize = getAccurateSize();
        titleLabel.setLocation(screenSize.getX() * 0.5f, screenSize.getY() * 0.1f);
        titleLabel.setSize(screenSize.getX(),screenSize.getY() * 0.25f);
        float newTitleFontSize = titleLabel.getHeight() * 0.5f;
        if (titleFontSize != newTitleFontSize) {
            titleLabel.setFont(titleFont.deriveFont(newTitleFontSize));
        }
        closeButton.setLocation(screenSize.getX() * 0.1f, screenSize.getY() * 0.1f);
        closeButton.setSize(screenSize.getX() * 0.05f,screenSize.getY() * 0.05f);

        playButton.setLocation(screenSize.getX() * 0.5f, screenSize.getY() * 0.9f);
        playButton.setSize(screenSize.getX() * 0.25f,screenSize.getY() * 0.1f);

        background.setLocation(0, 0);
        background.setSize(screenSize);
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getPlayButton() {
        return playButton;
    }
}