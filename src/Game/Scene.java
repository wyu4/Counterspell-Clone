package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateButton;
import GUIClasses.AccurateUIComponents.AccurateImageIcon;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import GUIClasses.AnimatedTextLabel;

import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Scene extends AccuratePanel {
    private final List<String[]> dialogues;
    private final HashMap<String, AccurateImageIcon> characters;
    private volatile boolean nextRequested, processDialogues, processTyping;
    private final DialoguePanel dialoguePanel;
    private final TypingPanel typingPanel;
    private final AccuratePanel characterPanel;
    private final AccurateLabel backgroundLabel;
    private final AccurateButton backButton;
    private String currentCue;

    public Scene() {
        super("Cutscene");
        dialogues = new ArrayList<>();
        characters = new HashMap<>();
        characterPanel = new AccuratePanel("CharacterPanel");
        dialoguePanel = new DialoguePanel();
        backgroundLabel = new AccurateLabel();
        typingPanel = new TypingPanel();
        backButton = new AccurateButton();
        nextRequested = false;
        processDialogues = true;
        processTyping = false;

        dialoguePanel.setAnchorPoint(0.5f, 0.9f);
        characterPanel.setBackground(new Color(0, 0, 0, 0));

        typingPanel.setAnchorPoint(0.5f, 1f);
        typingPanel.setVisible(false);

        backButton.setAnchorPoint(0.1f, 0.1f);
        backButton.setBackground(new Color(255, 255, 255));

        add(backButton);
        add(dialoguePanel);
        add(typingPanel);
        add(characterPanel);
        add(backgroundLabel);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void addDialogue(String speaker, String content) {
        dialogues.add(new String[] {speaker, content});
    }

    public void addDialogue(String speaker, String content, String cue) {
        dialogues.add(new String[] {speaker, content, cue});
    }

    public void addCharacter(Component c) {
        characterPanel.add(c);
    }

    public void setTypePrompt(String prompt) {
        typingPanel.setPrompt(prompt);
        typingPanel.resetStartTime();
    }

    public void setBackgroundImage(AccurateImageIcon bg) {
        if (!backgroundLabel.getIcon().equals(bg)) {
            backgroundLabel.setIcon(bg);
        }
    }

    public Icon getBackgroundImage() {
        return backgroundLabel.getIcon();
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

    public String getCurrentCue() {
        return currentCue;
    }

    public void requestNextDialogue() {
        if (!processDialogues) {
            return;
        }
        nextRequested = true;
    }

    public void processKeyType(char key) {
        if (!processTyping) {
            return;
        }
        typingPanel.processKeyType(key);
    }

    public void processInputs(boolean dialogue, boolean typing) {
        processDialogues = dialogue;
        processTyping = typing;
    }

    public void addCharacter(String speaker, AccurateImageIcon character) {
        characters.put(speaker, character);
    }

    public void setDialoguePanelShowing(boolean state) {
        dialoguePanel.setVisible(state);
    }

    public void setTypingPanelShowing(boolean state) {
        typingPanel.setVisible(state);
    }

    public void setTypingPanelOnEmpty(Runnable task) {
        typingPanel.onEmpty(task);
    }

    public void tick(float timeMod) {
        FloatCoordinate screenSize = getAccurateSize();
        if (nextRequested) {
            nextRequested = false;
            String[] data = getNextDialogue();
            if (data != null && data.length >= 2) {
                dialoguePanel.setData(data[0], data[1]);
                for (String characterName : characters.keySet()) {
                    if (characterName.equals(data[0])) {
                        characters.get(characterName).setAlpha(0.5f);
                    } else {
                        characters.get(characterName).setAlpha(1f);
                    }
                }
                if (data.length >= 3) {
                    currentCue = data[2];
                }
            }
        }

        backgroundLabel.setLocation(0, 0);
        backgroundLabel.setSize(screenSize);

        characterPanel.setLocation(0, 0);
        characterPanel.setSize(screenSize);

        dialoguePanel.setLocation(screenSize.getX() * 0.5f, screenSize.getY() * 0.9f);
        dialoguePanel.setSize(screenSize.getX() * 0.8f, screenSize.getY() * 0.25f);
        dialoguePanel.tick(timeMod);

        typingPanel.setLocation(screenSize.getX() * 0.5f, screenSize.getY());
        typingPanel.setSize(screenSize.getX(), screenSize.getY() * 0.25f);
        typingPanel.tick(timeMod);

        backButton.setLocation(screenSize.getX() * 0.1f, screenSize.getY() * 0.1f);
        backButton.setSize(screenSize.getX() * 0.05f,screenSize.getY() * 0.05f);
    }
}