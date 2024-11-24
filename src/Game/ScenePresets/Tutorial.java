package Game.ScenePresets;

import Game.Scene;

public class Tutorial extends Scene {
    private static final String G1 = "QUESTIONING1";
    private static final String G2 = "QUESTIONING_DONE1";
    private String currentPlaying;

    public Tutorial() {
        super();
        currentPlaying = "";

        super.addDialogue("???", "Welcome.");
        super.addDialogue("???", "Commencing cloning protocol…");
        super.addDialogue("???", "DATE: 21XX - 12 - 24");
        super.addDialogue("???", "SUBJECT: MC NAME");
        super.addDialogue("???", "TEST #: 1056");
        super.addDialogue("???", "Loading...");
        super.addDialogue("???", "Complete.");
        super.addDialogue("???", "Please answer the following questions to the best of your ability:");
        super.addDialogue("", "", G1);
        super.addDialogue("???", "Loading…", G2);
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
                System.out.println("Switching!");
                setDialoguePanelShowing(false);
                setTypingPanelShowing(true);
                setTypePrompt("Hello Test");
//                setTypePrompt("Adaptive radiation is the evolutionary process through which a single ancestral species diversifies into multiple distinct species, each adapted to exploit different ecological niches. This occurs rapidly, often in response to an environment with diverse resources or a lack of competition.");
                processInputs(false, true);
                setTypingPanelOnEmpty(
                        () -> {
                            System.out.println("Player completed first tutorial.");
                            requestNextDialogue();
                        }
                );
                break;
            }
            case G2: {
                System.out.println("G2!");
                processInputs(true, false);
                setDialoguePanelShowing(true);
                setTypingPanelShowing(false);
                break;
            }
        }
    }
}
