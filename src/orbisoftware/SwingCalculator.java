package orbisoftware;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class SwingCalculator extends JFrame {
	
	SwingCalculator() {
		super("Swing Calculator");
        setMinimumSize(new Dimension(440, 705));
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ignored) {}
        
        SharedClasses.ProgrammerCalculator.frameResized(screenWidth);
        performContentSetup();
        
        setVisible(true);
	}
	
	private void performContentSetup() {
		
        SharedClasses.cards.add(SharedClasses.ScientificCalculator.setTheContentPane(), "one");
        SharedClasses.cards.add(SharedClasses.ProgrammerCalculator.setTheContentPane(), "two");
        
        setContentPane(SharedClasses.cards);
        
        CardLayout cl = (CardLayout) SharedClasses.cards.getLayout();
        
        if (SharedClasses.activeCard == 0)
        	cl.show(SharedClasses.cards, "one");
        else if (SharedClasses.activeCard == 1)
        	cl.show(SharedClasses.cards, "two");
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SharedClasses.SwingCalculator = new SwingCalculator();
			}
		});
	}
}
