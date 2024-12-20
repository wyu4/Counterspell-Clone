package Game.ScenePresets;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import Game.Scene;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import javax.swing.SwingConstants;
import java.awt.Color;

public class Tutorial extends Scene {
    private static final String G1 = "QUESTIONING1";
    private static final String G2 = "QUESTIONING2";
    private static final String G3 = "QUESTIONING3";
    private static final String END_CUTSCENE = "END_CUTSCENE";
    private static final String END = "END";

    private static final String PROMPT_1 = "Adaptive radiation is the evolutionary process through which a single ancestral species diversifies into multiple distinct species, each adapted to exploit different ecological niches. This occurs rapidly, often in response to an environment with diverse resources or a lack of competition.";
    private static final String PROMPT_2 = "The human body maintains a stable internal temperature (~37C) through homeostasis, even in changing external conditions.";
    private static final String PROMPT_3 = "CRISPR-Cas9 is a revolutionary gene-editing tool adapted from the immune system of prokaryotes. It enables precise modifications to DNA sequences.";

    private String currentPlaying;
    private final FadeToWhiteTitleScene outro;

    public Tutorial() {
        super();
        currentPlaying = "";
        outro = new FadeToWhiteTitleScene();
        outro.onEnd(
                () -> {
                    processInputs(true, false);
                    requestNextDialogue();
                }
        );

        add(outro);
        setDialoguePanelLocked(false);

        super.addDialogue("", "Welcome.");
        super.addDialogue("", "Commencing cloning protocol…");
        super.addDialogue("", "DATE: 21XX - 12 - 24");
        super.addDialogue("", "SUBJECT: ANNE");
        super.addDialogue("", "TEST #: 1056");
        super.addDialogue("", "Loading...");
        super.addDialogue("", "Complete.");
        super.addDialogue("", "Please answer the following questions to the best of your ability:");
        super.addDialogue("", "Question 1: ___________________________");
        super.addDialogue("", "", G1);
//        super.addDialogue("You", PROMPT_1);
        super.addDialogue("", "Question 2: ___________________________");
        super.addDialogue("", "", G2);
//        super.addDialogue("You", PROMPT_2);
        super.addDialogue("", "Final question: ___________________________");
        super.addDialogue("", "", G3);
//        super.addDialogue("You", PROMPT_3);
        super.addDialogue("", "Loading...");
        super.addDialogue("", "Data Successfully Collected!");
        super.addDialogue("", "Commencing Clone Generation Protocol…");
        super.addDialogue("", "Reducing clone statistics…");
        super.addDialogue("", "Generating body…");
        super.addDialogue("", "", END_CUTSCENE);
        super.addDialogue("", "", END);

        processInputs(true, false);
        requestNextDialogue();
        System.out.println("Tutorial created");
    }

    @Override
    public void tick(float timeMod) {
        super.tick(timeMod);

        outro.setLocation(0, 0);
        outro.setSize(getAccurateSize());
        outro.tick(timeMod);

        setDialoguePanelLocation(getAccurateSize().multiply(0.5f));

        String cue = getCurrentCue();
        if (cue == null) {
            return;
        }
        if (currentPlaying.equals(cue)) {
            return;
        }
        currentPlaying = cue;
        switch (cue) {
            case G1: {
                setDialoguePanelShowing(false);
                setTypingPanelShowing(true);
//                setTypePrompt("Hi");
                setTypePrompt(PROMPT_1);
                processInputs(false, true);
                setTypingPanelOnEmpty(
                        () -> {
                            System.out.println("Player completed first tutorial.");
                            processInputs(true, false);
                            setDialoguePanelShowing(true);
                            setTypingPanelShowing(false);
                            requestNextDialogue();
                        }
                );
                break;
            }
            case G2: {
                setDialoguePanelShowing(false);
                setTypingPanelShowing(true);
                setTypePrompt(PROMPT_3);
                processInputs(false, true);
                setTypingPanelOnEmpty(
                        () -> {
                            System.out.println("Player completed second tutorial.");
                            processInputs(true, false);
                            setDialoguePanelShowing(true);
                            setTypingPanelShowing(false);
                            requestNextDialogue();
                        }
                );
                break;
            }
            case G3: {
                setDialoguePanelShowing(false);
                setTypingPanelShowing(true);
                setTypePrompt(PROMPT_2);
                processInputs(false, true);
                setTypingPanelOnEmpty(
                        () -> {
                            System.out.println("Player completed third tutorial.");
                            processInputs(true, false);
                            setDialoguePanelShowing(true);
                            setTypingPanelShowing(false);
                            requestNextDialogue();
                        }
                );
                break;
            }
            case END_CUTSCENE: {
                processInputs(false, false);
                setDialoguePanelShowing(false);
                setTypingPanelShowing(false);
                outro.start();
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

class FadeToWhiteTitleScene extends AccuratePanel {
    private Runnable onEnd;
    private final AccurateLabel titleLabel;
    private float titleFontSize;
    private boolean triggered;
    private Long stallStart;

    public FadeToWhiteTitleScene() {
        super("FadeToWhite");

        titleFontSize = 0f;
        triggered = false;

        setBackground(new Color(0, 0, 0));

        titleLabel = new AccurateLabel("Title");
        titleLabel.setText("CLONE");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 0, 0));
        titleLabel.setAnchorPoint(0.5f, 0.5f);

        add(titleLabel);
    }

    public void start() {
        triggered = true;
    }

    public void onEnd(Runnable task) {
        onEnd = task;
    }

    public void tick(float timeMod) {
        if (!triggered) {
            return;
        }

        FloatCoordinate screenSize = getAccurateSize();

        Color oldColor = getBackground();
        System.out.println(oldColor);
        setBackground(
                new Color(
                       Math.clamp((oldColor.getRed()/255f) + (0.0005f*timeMod), 0, 1f),
                       Math.clamp((oldColor.getGreen()/255f) + (0.0005f*timeMod), 0, 1f),
                       Math.clamp((oldColor.getBlue()/255f) + (0.0005f*timeMod), 0, 1f)
                )
        );
        if (oldColor.getRed() >= 255 && oldColor.getGreen() >= 255 && oldColor.getBlue() >= 255) {
            if (stallStart == null) {
                stallStart = System.currentTimeMillis();
            } else {
                if (System.currentTimeMillis() - stallStart >= 4000) {
                    triggered = false;
                    onEnd.run();
                }
            }

        }

        titleLabel.setLocation(screenSize.multiply(0.5f));
        titleLabel.setSize(screenSize.getX(), screenSize.getY()*0.25f);

        float newTitleFontSize = titleLabel.getHeight()*0.9f;
        if (titleFontSize != newTitleFontSize) {
            titleFontSize = newTitleFontSize;
            titleLabel.setFont(ResourcesManager.getAsFont(ResourceEnum.BungeeSpice_ttf).deriveFont(newTitleFontSize));
        }
    }
}