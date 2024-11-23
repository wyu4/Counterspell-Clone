package Game;

import GUIClasses.AccurateUIComponents.AccurateFrame;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.Color;

public class MainFrame extends AccurateFrame implements NativeKeyListener {
    public MainFrame() {
        super("MainFrame");
        setUndecorated(true);
        setBackground(new Color(0, 0, 0));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 0));
    }

    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {}

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {}

    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {}

    /**
     * Processes a tick event.
     */
    public void tick(float timeMod) {}
}
