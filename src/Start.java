import Game.GameSession;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class Start {
    public static void main(String[] args) throws InterruptedException {
        Platform.setImplicitExit(false);
        new JFXPanel();

        GameSession session = new GameSession();
        session.startApp();
    }
}
