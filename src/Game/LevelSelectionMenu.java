package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateButton;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class LevelSelectionMenu extends AccuratePanel {
    private final List<AccurateButton> buttons;
    private final AccurateButton backButton;
    private final AccuratePanel buttonsPanel;
    private float buttonFontSize;

    public LevelSelectionMenu() {
        super("LevelSelectionMenu");
        buttons = new ArrayList<>();
        buttonsPanel = new AccuratePanel("Content");
        backButton = new AccurateButton("Back");

        setBackground(new Color(0, 0, 0));

        backButton.setAnchorPoint(0.1f, 0.1f);
        backButton.setBackground(new Color(255, 255, 255));

        buttonsPanel.setAnchorPoint(0.5f, 0.5f);
        buttonsPanel.setLayout(new GridLayout(2, 4));
        buttonsPanel.setBackground(new Color(255, 255, 255, 20));

//        buttonsPanel.add(new JPanel());

        for (int i = 0; i < 8; i++) {
            AccurateButton select = new AccurateButton("LevelSelect" + i);
            select.setBackground(new Color(255, 255, 255, 0));
            select.setForeground(new Color(255, 255, 255));
            select.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            select.setContentAreaFilled(false);
            if (i == 0) {
                select.setText("Tutorial");
            } else {
                select.setText(String.valueOf(i));
            }
            buttons.add(select);
            buttonsPanel.add(select);
        }
//        buttonsPanel.revalidate();

        add(backButton);
        add(buttonsPanel);
    }

    public List<JButton> getLevelButtons() {
        return new ArrayList<>(buttons);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void tick(float timeMod) {
        FloatCoordinate screenSize = getAccurateSize();

        backButton.setLocation(screenSize.getX() * 0.1f, screenSize.getY() * 0.1f);
        backButton.setSize(screenSize.getX() * 0.05f,screenSize.getY() * 0.05f);

        buttonsPanel.setLocation(screenSize.multiply(0.5f));
        buttonsPanel.setSize(screenSize.multiply(0.7f));

        float newButtonFontSize = buttonsPanel.getHeight()*0.05f;
        if (newButtonFontSize != buttonFontSize) {
            buttonFontSize = newButtonFontSize;
            for (JButton button : buttons) {
                button.setFont(ResourcesManager.getAsFont(ResourceEnum.DroidSansMono_ttf).deriveFont(newButtonFontSize));
            }
        }

        buttonsPanel.revalidate();
    }
}
