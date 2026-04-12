package orbisoftware;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        
        performContentSetup();
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (SharedClasses.activeCard == 1) {          	
                	SharedClasses.ProgrammerCalculator.frameResized(getWidth());
                	SharedClasses.cards.removeAll();
                    performContentSetup();
                }
            }
        });
        
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
