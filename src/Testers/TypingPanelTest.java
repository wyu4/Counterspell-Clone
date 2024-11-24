package Testers;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateFrame;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import Game.TypingPanel;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TypingPanelTest extends AccurateFrame implements ActionListener, NativeKeyListener {
    private final Timer runtime;
    private final TypingPanel panel;

    public TypingPanelTest() {
        super("Tester");
        setUndecorated(true);
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        getContentPane().setBackground(new Color(0, 0, 0,0));
        panel = new TypingPanel();
        runtime = new Timer(1,  this);

        add(panel);
        setVisible(true);
        runtime.start();
    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.out.println("Exiting tester...");
            closeFrame();
        } else {
            panel.processKeyPress(nativeEvent.getKeyCode());
        }
    }

    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        panel.processKeyRelease(nativeEvent.getKeyCode());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!e.getSource().equals(runtime)) {
            return;
        }
        setSize(new FloatCoordinate(MouseInfo.getPointerInfo().getLocation()).multiply(0.9f));
        panel.setSize(getSize());
        panel.setLocation(0, 0);
        panel.tick(1f);

        repaint();
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook: \n" + ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new TypingPanelTest());
    }
}
