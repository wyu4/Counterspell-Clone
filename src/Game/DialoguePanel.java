package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import GUIClasses.AnimatedTextLabel;

import javax.swing.SwingConstants;
import java.awt.Color;

public class DialoguePanel extends AccuratePanel {
    private final AnimatedTextLabel nameLabel, contentLabel;
    private float nameFontSize, contentFontSize;

    public DialoguePanel() {
        super("DialoguePanel");
        nameFontSize = 0;
        contentFontSize = 0;

        nameLabel = new AnimatedTextLabel("Name");
        contentLabel = new AnimatedTextLabel("Content");

        nameLabel.setForeground(Color.WHITE);
        contentLabel.setForeground(Color.WHITE);
        contentLabel.setWrapped(true);
        contentLabel.setVerticalAlignment(SwingConstants.TOP);

        setBackground(new Color(0, 0, 0));

        contentLabel.setAnchorPoint(0, 1f);

        add(nameLabel);
        add(contentLabel);
    }

    public void setData(String name, String data) {
        nameLabel.setGoalText(name);
        contentLabel.setGoalText(data);
    }

    public void tick(float timeMod) {
        FloatCoordinate screenSize = getAccurateSize();
        nameLabel.tick(timeMod);
        contentLabel.tick(timeMod);

        nameLabel.setLocation(0, 0);
        nameLabel.setSize(screenSize.getX(), screenSize.getY()*0.2f);
        float newNameFontSize = nameLabel.getHeight() * 0.9f;
        if (nameFontSize != newNameFontSize) {
            nameLabel.setFont(nameLabel.getFont().deriveFont(newNameFontSize));
            contentLabel.setFont(contentLabel.getFont().deriveFont(newNameFontSize));
            nameFontSize = newNameFontSize;
        }
        contentLabel.setLocation(0, screenSize.getY());
        contentLabel.setSize(screenSize.getX(), screenSize.getY()*0.8f);
    }
}