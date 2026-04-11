package orbisoftware;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class SharedClasses {

	public static SwingCalculator SwingCalculator;
	public static JPanel cards = new JPanel(new CardLayout());
	public static ScientificCalulator ScientificCalculator = new ScientificCalulator();
	public static ProgrammerCalculator ProgrammerCalculator = new ProgrammerCalculator();
}
