package Game;

import GUIClasses.AccurateUIComponents.AccuratePanel;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.Color;
import java.util.List;

public class TypingPanel extends AccuratePanel implements NativeKeyListener {
    private String message;
    private List<Integer> pressedKeys;

    public TypingPanel() {
        this("The quick brown fox jumps over the lazy dog.");
    }

    public TypingPanel(String message) {
        super("TypingPanel");
        this.message = message;
        GlobalScreen.addNativeKeyListener(this);

        setBackground(new Color(45, 45, 45));
    }

    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        System.out.println(nativeEvent.getKeyCode());
    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {}

    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {}
}
