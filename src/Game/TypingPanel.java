package Game;

import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class TypingPanel extends AccuratePanel {
    private final AccurateLabel textLabel;
    private String prompt;
    private String displayedText;
    private Font font;
    private float fontSize;

    public TypingPanel() {
        this("The quick brown fox jumps over the lazy dog.");
    }

    public TypingPanel(String message) {
        super("TypingPanel");
        textLabel = new AccurateLabel("Prompt");
        prompt = "";
        displayedText = prompt;
        font = ResourcesManager.getAsFont(ResourceEnum.DroidSansMono_ttf);
        fontSize = 1;
        setBackground(new Color(45, 45, 45));
        setPrompt(message);

        textLabel.setForeground(new Color(255, 255, 255));
        textLabel.setFont(font);
        textLabel.setWrapped(true);

        add(textLabel);
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
        setText(prompt);
    }

    public String getPrompt() {
        return prompt;
    }

    private void setText(String text) {
        displayedText = text;
    }

    public void processKeyType(char key) {
        if (displayedText.isEmpty()) {
            return;
        }
        if (displayedText.charAt(0) == key) {
            displayedText = displayedText.substring(1);
            System.out.println("Got " + key);
        }
    }

    public void tick(float timeMod) {
        textLabel.setLocation(0, 0);
        textLabel.setSize(getSize());
        textLabel.setVerticalAlignment(SwingConstants.TOP);
        float newFontSize = textLabel.getHeight() * 0.1f;
        if (fontSize != newFontSize) {
            fontSize = newFontSize;
            textLabel.setFont(font.deriveFont(fontSize));
        }
        textLabel.setText(displayedText);
    }
}
