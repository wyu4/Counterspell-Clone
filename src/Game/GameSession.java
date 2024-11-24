package Game;

import Game.ScenePresets.Tutorial;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSession implements ActionListener, NativeKeyListener {
    enum AppState {
        SPLASH_SCREEN,
        CUTSCENE
    }

    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    private final MainFrame mainFrame;
    private final SplashScreen splashScreen;
    private final Menu menu;
    private Scene currentScene;
    private final Timer runtime;

    private Long lastTick;
    private AppState appState;

    /**
     * Start a new session
     */
    public GameSession() {
        appState = AppState.SPLASH_SCREEN;
        mainFrame = new MainFrame();
        splashScreen = new SplashScreen();
        menu = new Menu();
        runtime = new Timer(1, this);
    }

    /**
     * Start the app.
     */
    public void startApp() throws InterruptedException{
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook: \n" + ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(this);

        menu.setVisible(false);

        mainFrame.add(splashScreen);
        mainFrame.add(menu);
        mainFrame.setVisible(true);

        menu.getCloseButton().addActionListener(this);
        menu.getPlayButton().addActionListener(this);

        runtime.start();
        splashScreen.setShouldShow(true);

        Thread.sleep(4000);

        splashScreen.setShouldShow(false);

        Thread.sleep(2000);
        menu.setVisible(true);
    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        if (appState == AppState.CUTSCENE && currentScene != null) {
            currentScene.requestNextDialogue();
        }
    }

    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        if (appState == AppState.CUTSCENE && currentScene != null) {
            currentScene.processKeyType(nativeEvent.getKeyChar());
        }
    }

    /**
     * Stop the app.
     */
    public void endApp() {
        System.out.println("Closing session...");
        runtime.stop();
        mainFrame.closeFrame();
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(runtime)) {
            if (lastTick == null) {
                lastTick = System.currentTimeMillis();
                return;
            }
            long currentTime = System.currentTimeMillis();
            float delta = currentTime - lastTick;
            float timeMod = delta / runtime.getDelay() / 1000f;

            mainFrame.setLocation(0, 0);
            mainFrame.setSize((float) toolkit.getScreenSize().getWidth(), (float) toolkit.getScreenSize().getHeight());

//            mainFrame.setSize((float) toolkit.getScreenSize().getWidth() * 0.5f, (float) toolkit.getScreenSize().getHeight() * 0.5f);
//        mainFrame.setSize(new FloatCoordinate(MouseInfo.getPointerInfo().getLocation()).multiply(0.95f));
            mainFrame.tick(timeMod);

            Dimension screenSize = mainFrame.getSize();

            splashScreen.setLocation(0, 0);
            splashScreen.setSize(screenSize);
            splashScreen.tick(timeMod);

            menu.setLocation(0, 0);
            menu.setSize(screenSize);
            menu.tick(timeMod);

            if (currentScene != null) {
                currentScene.setLocation(0, 0);
                currentScene.setSize(screenSize);
                currentScene.tick(timeMod);
            }

            mainFrame.repaint();
        } else if (e.getSource().equals(menu.getCloseButton())) {
            System.out.println("Close requested from menu");
            endApp();
        } else if (e.getSource().equals(menu.getPlayButton())) {
            System.out.println("Play requested from menu.");
            menu.setVisible(false);
            currentScene = new Tutorial();
            mainFrame.add(currentScene);
            mainFrame.revalidate();
            appState = AppState.CUTSCENE;
        }
    }
}
