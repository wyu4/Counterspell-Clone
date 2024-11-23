import Game.App;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class Start {
    public static void main(String[] args) throws InterruptedException {
        App session = new App();
        session.startApp();
    }
}
