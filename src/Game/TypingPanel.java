package Game;

import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class TypingPanel extends AccuratePanel {
    private final AccurateLabel textLabel;
    private String prompt;
    private String displayedText;
    private Font font;
    private float fontSize;
    private int wordCount;
    private int mistakes;
    private long startTime;
    private Runnable onEmpty;

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
        startTime = System.currentTimeMillis();
        wordCount = 0;
        mistakes = 0;
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

    public void onEmpty(Runnable task) {
        onEmpty = task;
    }

    public void resetStartTime() {
        startTime = System.currentTimeMillis();
    }

    private void setText(String text) {
        displayedText = text;
    }

    public void processKeyType(char key) {
        if (displayedText.isEmpty()) {
            return;
        }
        if (displayedText.charAt(0) == key) {
            if (displayedText.charAt(0) == ' ') {
                wordCount ++;
            }
            displayedText = displayedText.substring(1);
            if (displayedText.isEmpty()) {
                onEmpty.run();
            }
        } else {
            mistakes ++;
            textLabel.setForeground(new Color(255, 0, 0));
        }
    }

    public float calcWPM() {
        return (wordCount/(float)(System.currentTimeMillis() - startTime))*60000f;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void tick(float timeMod) {
        textLabel.setLocation(0, 0);
        textLabel.setSize(getSize());
        textLabel.setVerticalAlignment(SwingConstants.TOP);
        float newFontSize = textLabel.getHeight() * 0.15f;
        if (fontSize != newFontSize) {
            fontSize = newFontSize;
            textLabel.setFont(font.deriveFont(fontSize));
        }
        Color oldFontColor = textLabel.getForeground();
        textLabel.setForeground(
                new Color(
                        (int) Math.clamp(oldFontColor.getRed() + 5*timeMod,0,255),
                        (int) Math.clamp(oldFontColor.getGreen() + 5*timeMod,0,255),
                        (int) Math.clamp(oldFontColor.getBlue() + 5*timeMod,0,255)
                )
        );
        textLabel.setText(displayedText);
    }
}
