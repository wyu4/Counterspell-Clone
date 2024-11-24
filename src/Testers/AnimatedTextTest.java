package Testers;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateFrame;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import GUIClasses.AnimatedTextLabel;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedTextTest extends AccurateFrame implements NativeKeyListener, ActionListener {
    private final Timer runtime;
    private final AccuratePanel panel;
    private final AnimatedTextLabel label;

    public AnimatedTextTest() {
        super("Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0));
        setUndecorated(true);

        panel = new AccuratePanel();
        panel.setBackground(new Color(0, 0,0));
        label = new AnimatedTextLabel("Test");
        label.setGoalText("Hello World!");
        label.setForeground(new Color(255,255,255));

        panel.add(label);
        add(panel);
        setVisible(true);

        runtime = new Timer(1, this);

        runtime.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        label.setGoalText("WOAH WOAH WOAH! LMAPOOOOO");
    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.out.println("Exiting tester...");
            closeFrame();
        }
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook: \n" + ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new AnimatedTextTest());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FloatCoordinate screenSize = new FloatCoordinate(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(0, 0);
        setSize(screenSize.getX(), screenSize.getY()*0.5f);

        panel.setLocation(0, 0);
        panel.setSize(screenSize);

        label.setLocation(0, 0);
        label.setSize(screenSize.getX(), screenSize.getY()*0.25f);
        label.tick(1);

        repaint();
    }
}
