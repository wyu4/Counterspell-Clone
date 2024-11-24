package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class Menu extends AccuratePanel {
    private final AccurateLabel titleLabel;
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

        titleFontSize = 0f;

        add(titleLabel);
    }

    public void tick(float timeMod) {
        FloatCoordinate screenSize = getAccurateSize();
        titleLabel.setLocation(screenSize.getX() * 0.5f, screenSize.getY() * 0.1f);
        titleLabel.setSize(screenSize.getX(),screenSize.getY() * 0.25f);
        float newTitleFontSize = titleLabel.getHeight() * 0.5f;
        if (titleFontSize != newTitleFontSize) {
            titleLabel.setFont(titleFont.deriveFont(newTitleFontSize));
        }

    }
}