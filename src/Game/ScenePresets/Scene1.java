package Game.ScenePresets;

import Game.Scene;

public class Scene1 extends Scene {
    public Scene1() {
        super();
        super.addDialogue("Cababas", "Hey there!");
        super.addDialogue("Snowberry", "How are you?");
        super.addDialogue("Cababas", "Good, how are you, you fine gentleman?");
        super.addDialogue("Snowberry", "I am feeling delightful this wonderous moment.");
        super.addDialogue("Cababas", "Well, you look quite like the lad!");
        super.addDialogue("Snowberry", "Exactly!");
        requestNextDialogue();
        System.out.println("Scene1 created");
    }
}
