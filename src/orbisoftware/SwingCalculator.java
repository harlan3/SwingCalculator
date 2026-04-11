package orbisoftware;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class SwingCalculator extends JFrame {
	
	SwingCalculator() {
		
		super("Swing Calculator");
        setMinimumSize(new Dimension(440, 705));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ignored) {}
        
        SharedClasses.cards.add(SharedClasses.ScientificCalculator.setTheContentPane(), "one");
        SharedClasses.cards.add(SharedClasses.ProgrammerCalculator.setTheContentPane(), "two");
        
        setContentPane(SharedClasses.cards);
        
        CardLayout cl = (CardLayout) SharedClasses.cards.getLayout();
        cl.show(SharedClasses.cards, "one");
        
        setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SharedClasses.SwingCalculator = new SwingCalculator();
			}
		});
	}
}
