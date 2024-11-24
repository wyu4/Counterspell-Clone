package Game;

import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class TypingPanel extends AccuratePanel {
    private final List<Integer> pressedChars;
    private final AccurateLabel textLabel;
    private String prompt;
    private List<Integer> characterSequence;
    private Font font;
    private float fontSize;

    public TypingPanel() {
        this("The quick brown fox jumps over the lazy dog.");
    }

    public TypingPanel(String message) {
        super("TypingPanel");
        pressedChars = new ArrayList<>();
        textLabel = new AccurateLabel("Prompt");
        prompt = "";
        font = ResourcesManager.getAsFont(ResourceEnum.DroidSansMono_ttf);
        fontSize = 1;
        characterSequence = new ArrayList<>();
        setBackground(new Color(45, 45, 45));
        textLabel.setForeground(new Color(255, 255, 255));
        setPrompt(message);
        add(textLabel);
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
        textLabel.setText("<html>" + prompt + "</html>");
    }

    public String getPrompt() {
        return prompt;
    }

    public void processKeyPress(int keyCode) {
        if (!pressedChars.contains((Integer) keyCode)) {
            pressedChars.add(keyCode);
            System.out.println(keyCode);
        }
    }

    public void processKeyRelease(int keycode) {
        pressedChars.remove((Integer) keycode);
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
    }
}
