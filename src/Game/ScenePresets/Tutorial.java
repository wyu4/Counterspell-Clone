package Game.ScenePresets;

import Game.Scene;

public class Tutorial extends Scene {
    private static final String G1 = "QUESTIONING1";
    private static final String G2 = "QUESTIONING2";
    private static final String G3 = "QUESTIONING3";
    private String currentPlaying;

    public Tutorial() {
        super();
        currentPlaying = "";

        super.addDialogue("???", "Welcome.");
        super.addDialogue("???", "Commencing cloning protocol…");
        super.addDialogue("???", "DATE: 21XX - 12 - 24");
        super.addDialogue("???", "SUBJECT: ANNE");
        super.addDialogue("???", "TEST #: 1056");
        super.addDialogue("???", "Loading...");
        super.addDialogue("???", "Complete.");
        super.addDialogue("???", "Please answer the following questions to the best of your ability:");
        super.addDialogue("???", "Question 1: ___________________________");
        super.addDialogue("", "", G1);
        super.addDialogue("???", "Question 2: ___________________________");
        super.addDialogue("???", "", G2);
        super.addDialogue("???", "Final question: ___________________________");
        super.addDialogue("???", "", G3);
        super.addDialogue("???", "Loading...");
        super.addDialogue("???", "Data Successfully Collected!");
        super.addDialogue("???", "Commencing Clone Generation Protocol…");
        super.addDialogue("???", "Reducing clone statistics…");
        super.addDialogue("???", "Generating body…");


        processInputs(true, false);
        requestNextDialogue();
        System.out.println("Scene1 created");
    }

    @Override
    public void tick(float timeMod) {
        super.tick(timeMod);

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
                setTypePrompt("Hi");
//                setTypePrompt("Adaptive radiation is the evolutionary process through which a single ancestral species diversifies into multiple distinct species, each adapted to exploit different ecological niches. This occurs rapidly, often in response to an environment with diverse resources or a lack of competition.");
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
                setTypePrompt("CRISPR-Cas9 is a revolutionary gene-editing tool adapted from the immune system of prokaryotes. It enables precise modifications to DNA sequences.");
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
                setTypePrompt("The human body maintains a stable internal temperature (~37C) through homeostasis, even in changing external conditions.");
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
        }
    }
}