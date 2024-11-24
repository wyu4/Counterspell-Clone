package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import GUIClasses.AnimatedTextLabel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Scene extends AccuratePanel {
    private final List<String[]> dialogues;
    private final HashMap<String, AccurateLabel> characters;
    private volatile boolean nextRequested;
    private final DialoguePanel dialoguePanel;
    private final AccuratePanel characterPanel;

    public Scene() {
        super("Cutscene");
        dialogues = new ArrayList<>();
        characters = new HashMap<>();
        characterPanel = new AccuratePanel("CharacterPanel");
        dialoguePanel = new DialoguePanel();
        nextRequested = false;

        dialoguePanel.setAnchorPoint(0.5f, 0.9f);
        characterPanel.setBackground(new Color(255, 255, 255));

        add(dialoguePanel);
        add(characterPanel);
    }

    public void addDialogue(String speaker, String content) {
        dialogues.add(new String[] {speaker, content});
    }

    public boolean hasNextDialogue() {
        return !dialogues.isEmpty();
    }

    private String[] getNextDialogue() {
        if (!hasNextDialogue()) {
            return null;
        }
        String[] data = dialogues.getFirst();
        dialogues.removeFirst();
        return data;
    }

    public void requestNextDialogue() {
        nextRequested = true;
    }

    public void addCharacter(AccurateLabel character) {
        characters.put(character.getName(), character);
    }

    public void tick(float timeMod) {
        FloatCoordinate screenSize = getAccurateSize();
        if (nextRequested) {
            nextRequested = false;
            String[] data = getNextDialogue();
            if (data != null && data.length >= 2) {
                dialoguePanel.setData(data[0], data[1]);
            }
        }

        characterPanel.setLocation(0, 0);
        characterPanel.setSize(screenSize);

        dialoguePanel.setLocation(screenSize.getX() * 0.5f, screenSize.getY() * 0.9f);
        dialoguePanel.setSize(screenSize.getX() * 0.8f, screenSize.getY() * 0.25f);
        dialoguePanel.tick(timeMod);
    }
}