package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import GUIClasses.AnimatedTextLabel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scene extends AccuratePanel {
    private List<String[]> dialogues;
    private HashMap<String, AccurateLabel> characters;

    public Scene() {
        super("Cutscene");
        dialogues = new ArrayList<>();
        characters = new HashMap<>();
    }

    public void addDialogue(String speaker, String content) {
        dialogues.add(new String[] {speaker, content});
    }

    public boolean hasNextDialogue() {
        return dialogues.isEmpty();
    }

    private String[] getNextDialogue() {
        if (!hasNextDialogue()) {
            return null;
        }
        String[] data = dialogues.getFirst();
        dialogues.removeFirst();
        return data;
    }

    public void addCharacter(AccurateLabel character) {
        characters.put(character.getName(), character);
    }

    public void tick(float timeMod) {

    }
}