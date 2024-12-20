package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import GUIClasses.AnimatedTextLabel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class DialoguePanel extends AccuratePanel {
    private final AnimatedTextLabel nameLabel, contentLabel;
    private float nameFontSize;

    public DialoguePanel() {
        super("DialoguePanel");
        nameFontSize = 0;

        nameLabel = new AnimatedTextLabel("Name");
        contentLabel = new AnimatedTextLabel("Content");

        nameLabel.setForeground(Color.WHITE);
        contentLabel.setForeground(Color.WHITE);
        contentLabel.setWrapped(true);
        contentLabel.setVerticalAlignment(SwingConstants.TOP);

        setBackground(new Color(0, 0, 0));

        nameLabel.setAnchorPoint(0.5f, 0);
        contentLabel.setAnchorPoint(0.5f, 1f);

        add(nameLabel);
        add(contentLabel);
    }

    public void setData(String name, String content) {
        nameLabel.setGoalText(name);
        contentLabel.setGoalText(content);
        System.out.println("Set dialogue data to " + name + " - " + content);
    }

    public void tick(float timeMod) {
        FloatCoordinate screenSize = getAccurateSize();

        nameLabel.setLocation(screenSize.getX()*0.5f, 0);
        nameLabel.setSize(screenSize.getX() * 0.9f, screenSize.getY()*0.2f);
        float newNameFontSize = nameLabel.getHeight() * 0.9f;
        if (nameFontSize != newNameFontSize) {
            nameLabel.setFont(ResourcesManager.getAsFont(ResourceEnum.DroidSansMono_ttf).deriveFont(newNameFontSize));
            contentLabel.setFont(ResourcesManager.getAsFont(ResourceEnum.RobotoMono_ttf).deriveFont(newNameFontSize));
            nameFontSize = newNameFontSize;
        }
        contentLabel.setLocation(screenSize.getX()*0.5f, screenSize.getY());
        contentLabel.setSize(screenSize.getX() * 0.9f, screenSize.getY()*0.8f);

        nameLabel.tick(timeMod);
        contentLabel.tick(timeMod);
    }
}