package Game;

import DataTypes.FloatCoordinate;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App implements ActionListener {
    enum AppState {
        SPLASH_SCREEN
    }

    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    private final MainFrame mainFrame;
    private final SplashScreen splashScreen;
    private final Menu menu;
    private final Timer runtime;

    private Long lastTick;
    private AppState appState;

    /**
     * Start a new session
     */
    public App() {
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

        menu.setVisible(false);

        mainFrame.add(splashScreen);
        mainFrame.add(menu);
        mainFrame.setVisible(true);

        runtime.start();
        splashScreen.setShouldShow(true);

        Thread.sleep(4000);

        splashScreen.setShouldShow(false);

        Thread.sleep(2000);
        menu.setVisible(true);
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
        if (lastTick == null) {
            lastTick = System.currentTimeMillis();
            return;
        }
        long currentTime = System.currentTimeMillis();
        float delta = currentTime - lastTick;
        float timeMod = delta / runtime.getDelay() / 1000f;

        mainFrame.setLocation(0, 0);
        mainFrame.setSize(new FloatCoordinate(MouseInfo.getPointerInfo().getLocation()).multiply(0.95f));
        mainFrame.tick(timeMod);

        Dimension screenSize = mainFrame.getSize();

        splashScreen.setLocation(0, 0);
        splashScreen.setSize(screenSize);
        splashScreen.tick(timeMod);

        menu.setLocation(0, 0);
        menu.setSize(screenSize);
        menu.tick(timeMod);

        mainFrame.repaint();
    }
}
