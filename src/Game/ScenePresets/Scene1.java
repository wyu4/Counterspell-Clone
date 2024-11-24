package Game.ScenePresets;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateImageIcon;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.VideoPanel;
import Game.Scene;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Scene1 extends Scene {
    private static final String INTRO = "INTRO";
    private static final String MC1 = "MC1";
    private static final String MC2 = "MC2";
    private static final String MC3 = "MC3";
    private static final String END = "END";

    private static final String PROMPT_1 = "Explain the process of adaptive radiation and describe its significance in evolutionary biology. Using examples from at least three different ecosystems (e.g., Galapagos Islands, African savannah, and coral reefs), discuss how species within these ecosystems have undergone adaptive radiation to occupy various ecological niches.";
    private static final String PROMPT_2 = "Describe the process of gene editing using CRISPR-Cas9 and its applications in modern biology. Discuss the mechanism by which the CRISPR-Cas9 system identifies and modifies target DNA sequences, and explain how this system has been adapted from its natural role in prokaryotic immunity.";
    private static final String PROMPT_3 = "Explain how the human body maintains homeostasis in response to changes in external temperature.";

    private String currentPlaying;
    private final AccurateLabel anne, clone;
    private VideoPanel intro;
    private MediaPlayer introPlayer;

    public Scene1() {
        super();

        Media introMedia = ResourcesManager.getAsMedia(ResourceEnum.Scene1_Intro_mp4, "mp4");
//        Media introMedia = new Media(new File("Resources/Scene1Intro.mp4").toURI().toString());

        if (introMedia != null) {
            introPlayer = new MediaPlayer(introMedia);
            introPlayer.setRate(0.5f);
            intro = new VideoPanel(introPlayer);
            intro.setVisible(false);
            addCharacter(intro);
        }

        setCharacterBackground(new Color(255, 255, 255));

        anne = new AccurateLabel("Anne");
        clone = new AccurateLabel("Clone");

        AccurateImageIcon anneImg = new AccurateImageIcon(ResourcesManager.getAsBufferedImage(ResourceEnum.Anne_Sprite_png));
        AccurateImageIcon cloneImg = new AccurateImageIcon(ResourcesManager.getAsBufferedImage(ResourceEnum.Clone_Sprite_png));
        anneImg.setMode(AccurateImageIcon.PaintMode.RATIO);
        cloneImg.setMode(AccurateImageIcon.PaintMode.RATIO);

        anne.setIcon(anneImg);
        clone.setIcon(cloneImg);

        anne.setAnchorPoint(0.5f, 1f);
        clone.setAnchorPoint(0.5f, 1f);

        addCharacter(anne);
        addCharacter(clone);

        addCharacter("Anne", anneImg);
        addCharacter("Clone", cloneImg);

        currentPlaying = "";

        super.addDialogue("", "", INTRO);
        super.addDialogue("Anne", "", MC1);
        super.addDialogue("Clone", ".....");
        super.addDialogue("Anne", "", MC2);
        super.addDialogue("Clone", ".....");
        super.addDialogue("Anne", "", MC3);
        super.addDialogue("Clone", ".....");
        super.addDialogue("", "Anne gets up and leaves the room silently, leaving the clone alone in the room.");
        super.addDialogue("", "", "END");
        processInputs(true, false);
        requestNextDialogue();
        revalidate();
        System.out.println("Scene1 created");
    }

    @Override
    public void tick(float timeMod) {
        super.tick(timeMod);
        FloatCoordinate screenSize = getCharacterPanelSize();

        anne.setLocation(screenSize.getX() * 0.25f, screenSize.getY());
        anne.setSize(screenSize);

        clone.setLocation(screenSize.getX() * 0.75f, screenSize.getY());
        clone.setSize(anne.getAccurateSize());

        if (intro != null) {
            intro.setLocation(0, 0);
            intro.setSize(screenSize.toDimension());
            if (intro.isVisible()) {
                intro.tick(timeMod);
            }
        }

        String cue = getCurrentCue();
        if (cue == null) {
            return;
        }
        if (currentPlaying.equals(cue)) {
            return;
        }
        currentPlaying = cue;
        switch (cue) {
            case INTRO: {
                if (intro != null) {
                    anne.setVisible(false);
                    clone.setVisible(false);
                    intro.setVisible(true);
                    introPlayer.play();
                    setDialoguePanelShowing(false);
                    setTypingPanelShowing(false);
                    processInputs(false, false);
                    introPlayer.setOnEndOfMedia(() -> {
                        introPlayer.stop();
                        intro.setVisible(false);
                        processInputs(true, false);
                        setDialoguePanelShowing(true);
                        setTypingPanelShowing(false);
                        anne.setVisible(true);
                        clone.setVisible(true);
                        requestNextDialogue();
                    });
                } else {
                    requestNextDialogue();
                }
                break;
            }
            case MC1: {
                setDialoguePanelShowing(false);
                setTypingPanelShowing(true);
//                setTypePrompt("Hi");
                setTypePrompt(PROMPT_1);
                processInputs(false, true);
                setTypingPanelOnEmpty(
                        () -> {
                            processInputs(true, false);
                            setDialoguePanelShowing(true);
                            setTypingPanelShowing(false);
                            requestNextDialogue();
                        }
                );
                break;
            }
            case MC2: {
                setDialoguePanelShowing(false);
                setTypingPanelShowing(true);
//                setTypePrompt("Hi");
                setTypePrompt(PROMPT_2);
                processInputs(false, true);
                setTypingPanelOnEmpty(
                        () -> {
                            processInputs(true, false);
                            setDialoguePanelShowing(true);
                            setTypingPanelShowing(false);
                            requestNextDialogue();
                        }
                );
                break;
            }
            case MC3: {
                setDialoguePanelShowing(false);
                setTypingPanelShowing(true);
//                setTypePrompt("Hi");
                setTypePrompt(PROMPT_3);
                processInputs(false, true);
                setTypingPanelOnEmpty(
                        () -> {
                            processInputs(true, false);
                            setDialoguePanelShowing(true);
                            setTypingPanelShowing(false);
                            requestNextDialogue();
                        }
                );
                break;
            }
            case END: {
                processInputs(false, false);
                runOnEnd();
                break;
            }
        }
    }
}
