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

public class Menu extends AccuratePanel {
    private final AccurateLabel titleLabel;
    private final AccurateButton playButton;
    private final AccurateButton closeButton;
    private final Font titleFont;
    private float titleFontSize;

    public Menu() {
        setBackground(new Color(0, 0, 0));

        titleFont = ResourcesManager.getAsFont(ResourceEnum.BungeeSpice_ttf);

        titleLabel = new AccurateLabel("Title");
        titleLabel.setText("CLONE");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setAnchorPoint(0.5f, 0.1f);
        titleLabel.setFont(titleFont);

        closeButton = new AccurateButton("CloseButton");
        closeButton.setBackground(new Color(255, 0, 0));
        closeButton.setAnchorPoint(0.1f, 0.1f);

        playButton = new AccurateButton("PlayButton");
        playButton.setBackground(new Color(0, 255, 0));
        playButton.setAnchorPoint(0.5f, 0.9f);

        titleFontSize = 0f;

        add(titleLabel);
        add(playButton);
        add(closeButton);
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
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}