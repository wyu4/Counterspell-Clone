package Game;

import DataTypes.FloatCoordinate;
import GUIClasses.AccurateUIComponents.AccurateImageIcon;
import GUIClasses.AccurateUIComponents.AccurateLabel;
import GUIClasses.AccurateUIComponents.AccuratePanel;
import ResourceClasses.ResourceEnum;
import ResourceClasses.ResourcesManager;

import java.awt.Color;

public class SplashScreen extends AccuratePanel {
    private final AccurateLabel logoLabel;
    private final AccurateImageIcon logo;
    private boolean state;

    public SplashScreen() {
        super("SplashScreen");
        state = true;

        setBackground(new Color(0, 0, 0, 0));

        logo = new AccurateImageIcon(ResourcesManager.getAsBufferedImage(ResourceEnum.Counterspell_png));
        logo.setMode(AccurateImageIcon.PaintMode.RATIO);
        logo.setAlpha(0);

        logoLabel = new AccurateLabel("SplashScreenLogo");
        logoLabel.setIcon(logo);
        logoLabel.setAnchorPoint(0.5f, 0.5f);

        add(logoLabel);
    }

    public void setShouldShow(boolean state) {
        this.state = state;
    }

    public void tick(double timeMod) {
        FloatCoordinate screenSize = getAccurateSize();
        logoLabel.setLocation(screenSize.multiply(0.5f));
        logoLabel.setSize(screenSize.multiply(0.25f));

        float alpha = logo.getAlpha();
        if (state) {
            alpha = (float) Math.clamp(alpha + (0.01f * timeMod), 0f, 1f);
        } else {
            alpha = (float) Math.clamp(alpha - (0.01f * timeMod), 0f, 1f);
        }
        logo.setAlpha(alpha);
    }
}
