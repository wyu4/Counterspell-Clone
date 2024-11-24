package Game;

import Game.ScenePresets.Scene1;
import Game.ScenePresets.Tutorial;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSession implements ActionListener, NativeKeyListener {
    enum AppState {
        SPLASH_SCREEN,
        MENU,
        CUTSCENE
    }

    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    private final MainFrame mainFrame;
    private final SplashScreen splashScreen;
    private final Menu menu;
    private final LevelSelectionMenu levelSelectionMenu;
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
        levelSelectionMenu = new LevelSelectionMenu();
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
        levelSelectionMenu.setVisible(false);

        mainFrame.add(splashScreen);
        mainFrame.add(menu);
        mainFrame.add(levelSelectionMenu);
        mainFrame.setVisible(true);

        menu.getCloseButton().addActionListener(this);
        menu.getPlayButton().addActionListener(this);
        levelSelectionMenu.getBackButton().addActionListener(this);
        for (JButton button : levelSelectionMenu.getLevelButtons()) {
            button.addActionListener(this);
        }

        runtime.start();
        splashScreen.setShouldShow(true);

        Thread.sleep(4000);

        splashScreen.setShouldShow(false);

        Thread.sleep(2000);
        appState = AppState.MENU;
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

    private void playScene(Scene scene) {
        System.out.println("Play requested from level select menu.");
            menu.setVisible(false);
            levelSelectionMenu.setVisible(false);
            currentScene = scene;
            mainFrame.add(currentScene);
            mainFrame.revalidate();
            appState = AppState.CUTSCENE;
    }

    private void endCurrentScene() {
        appState = AppState.MENU;
        menu.setVisible(true);
        currentScene.setVisible(false);
        currentScene.processInputs(false, false);
        mainFrame.remove(currentScene);
        currentScene = null;
        System.out.println("Returned to menu.");
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
            float timeMod = delta / runtime.getDelay();

            mainFrame.setLocation(0, 0);
            mainFrame.setSize((float) toolkit.getScreenSize().getWidth(), (float) toolkit.getScreenSize().getHeight());

//            mainFrame.setSize((float) toolkit.getScreenSize().getWidth() * 0.5f, (float) toolkit.getScreenSize().getHeight() * 0.5f);
//        mainFrame.setSize(new FloatCoordinate(MouseInfo.getPointerInfo().getLocation()).multiply(0.95f));
            mainFrame.tick(timeMod);

            Dimension screenSize = mainFrame.getSize();

            splashScreen.setLocation(0, 0);
            splashScreen.setSize(screenSize);
            splashScreen.tick(timeMod);

            if (menu.isVisible()) {
                menu.setLocation(0, 0);
                menu.setSize(screenSize);
                menu.tick(timeMod);
            }

            if (levelSelectionMenu.isVisible()) {
                levelSelectionMenu.setLocation(0, 0);
                levelSelectionMenu.setSize(screenSize);
                levelSelectionMenu.tick(timeMod);
            }

            if (currentScene != null) {
                currentScene.setLocation(0, 0);
                currentScene.setSize(screenSize);
                currentScene.tick(timeMod);
            }

            lastTick = currentTime;

            mainFrame.repaint();
        } else if (e.getSource().equals(menu.getCloseButton())) {
            System.out.println("Close requested from menu");
            endApp();
        } else if (e.getSource().equals(menu.getPlayButton())) {
            System.out.println("Opening level selection menu from main menu.");
            levelSelectionMenu.setVisible(true);
            menu.setVisible(false);
        } else if (e.getSource().equals(levelSelectionMenu.getBackButton())) {
            appState = AppState.MENU;
            menu.setVisible(true);
            levelSelectionMenu.setVisible(false);
            System.out.println("Returned to menu.");
        } else if (e.getSource() instanceof JButton button && levelSelectionMenu.getLevelButtons().contains(button)) {
            Scene scene = switch (levelSelectionMenu.getLevelButtons().indexOf(button)) {
                case 1 -> new Scene1();
                default -> new Tutorial();
            };
            scene.onEnd(this::endCurrentScene);
            playScene(scene);
        }
    }
}
