package Testers;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateFrame;
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
        panel = new TypingPanel("The Importance of Being Earnest is a drawing-room comedy by Oscar Wilde. Premiered on 14 February 1895 in London, it depicts the affairs of two young men about town who lead double lives to evade unwanted social obligations, both assuming the name Ernest to woo two young women. Other characters are the formidable Lady Bracknell, the fussy governess Miss Prism and the benign and scholarly Canon Chasuble. The play, celebrated for its wit and repartee, parodies contemporary dramatic norms and comically satirises late-Victorian manners. The triumphant opening night was followed within weeks by Wilde's downfall and imprisonment for homosexual acts and the closure of the production, and Wilde wrote no more comic or dramatic works. From the early 20th century onwards, the play has been revived frequently and adapted for radio, television, film, operas and musicals.");
        runtime = new Timer(1,  this);

        add(panel);
        setVisible(true);
        runtime.start();
    }

    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        panel.processKeyType(nativeEvent.getKeyChar());
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
        GlobalScreen.addNativeKeyListener(new TypingPanelTest());
    }
}
