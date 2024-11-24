package Testers;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateFrame;
import Game.DialoguePanel;
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

public class DialogueTest extends AccurateFrame implements ActionListener, NativeKeyListener {
    private final Timer runtime;
    private final DialoguePanel panel;

    public DialogueTest() {
        super("Tester");
        setUndecorated(true);
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        getContentPane().setBackground(new Color(0, 0, 0,0));
        panel = new DialoguePanel();
        panel.setData("Cababas", "HELLO FRIENDS!!!");
        runtime = new Timer(1,  this);

        add(panel);
        setVisible(true);
        runtime.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        panel.setData("Cababas", "ME AM CABABO!");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        panel.setData("Snowberry", "I AM SNOWBERRY!!!!!!");
    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.out.println("Exiting tester...");
            closeFrame();
        }
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
        GlobalScreen.addNativeKeyListener(new DialogueTest());
    }
}
