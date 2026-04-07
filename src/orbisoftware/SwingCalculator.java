package orbisoftware;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Based on a Scientific Calculator by Himani Bansal, published on Jun 29, 2024

public class SwingCalculator extends JFrame implements ActionListener {

	static JTextField field;
	String str, str1, str2;
	double memory = 0.0;

	// Dark Theme Colors
	private final Color BACKGROUND_COLOR = new Color(30, 30, 30); // Dark gray
	private final Color PANEL_COLOR = new Color(40, 40, 40); // Slightly lighter panel
	private final Color TEXT_FIELD_COLOR = new Color(25, 25, 25); // Text field bg
	private final Color TEXT_COLOR = new Color(220, 220, 220); // Light gray text
	private final Color BUTTON_COLOR = new Color(55, 55, 55); // Normal buttons
	private final Color OPERATOR_COLOR = new Color(101, 152, 251); // Blue for operator buttons
	private final Color MEMORY_COLOR = new Color(248, 132, 132); // Red for memory buttons
	private final Color EQUAL_COLOR = new Color(0, 150, 100); // Green for =

	SwingCalculator() {
        super("Swing Calculator");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ignored) {}
        
		str = str1 = str2 = "";

		field = new JTextField(16);
		field.setEditable(false);
		field.setFont(new Font("Arial", Font.BOLD, 26));
		field.setHorizontalAlignment(JTextField.RIGHT);
		field.setPreferredSize(new Dimension(300, 70));
		field.setBackground(TEXT_FIELD_COLOR);
		field.setForeground(TEXT_COLOR);
		field.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Buttons
		JButton b0 = new JButton("0");
		JButton b1 = new JButton("1");
		JButton b2 = new JButton("2");
		JButton b3 = new JButton("3");
		JButton b4 = new JButton("4");
		JButton b5 = new JButton("5");
		JButton b6 = new JButton("6");
		JButton b7 = new JButton("7");
		JButton b8 = new JButton("8");
		JButton b9 = new JButton("9");

		JButton ba = new JButton("+");
		JButton bs = new JButton("-");
		JButton bd = new JButton("/");
		JButton bm = new JButton("*");
		JButton beq = new JButton("=");
		JButton bc = new JButton("C");
		JButton be = new JButton(".");
		JButton bsign = new JButton("+/-");
		JButton bpi = new JButton("π");
		JButton bsq = new JButton("√");
		JButton bpow = new JButton("xʸ");
		JButton bsqr = new JButton("x²");
		JButton binv = new JButton("1/x");
		JButton bfac = new JButton("!");
		JButton blog = new JButton("log");
		JButton bln = new JButton("ln");
		JButton bsin = new JButton("sin");
		JButton bcos = new JButton("cos");
		JButton btan = new JButton("tan");
		JButton bcsc = new JButton("csc");
		JButton bsec = new JButton("sec");
		JButton bcot = new JButton("cot");
		JButton bmod = new JButton("mod");
		
		JButton bexp = new JButton("e");

		JButton bMC = new JButton("MC");
		JButton bMR = new JButton("MR");
		JButton bm1 = new JButton("M+");
		JButton bm2 = new JButton("M-");

		JButton blshift = new JButton("<<");
		JButton brshift = new JButton(">>");

		Font buttonFont = new Font("Arial", Font.PLAIN, 18);

		JButton[] allButtons = { b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, ba, bs, bd, bm, beq, bc, be, bsign, bpi, bsq,
				bpow, bsqr, binv, bfac, blog, bln, bsin, bcos, btan, bcsc, bsec, bcot, bmod, bexp, bMC, bMR, bm1, bm2, 
				blshift, brshift };

		for (JButton btn : allButtons) {
			btn.setFont(buttonFont);
			btn.addActionListener(this);
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		}

		// Apply dark theme colors to buttons
		for (JButton btn : allButtons) {
			String text = btn.getText();
			if (text.equals("=")) {
				btn.setBackground(EQUAL_COLOR);
				btn.setForeground(Color.WHITE);
			} else if ("+-*/".contains(text)) {
				btn.setBackground(OPERATOR_COLOR);
				btn.setForeground(Color.WHITE);
			} else if ("C ! π ln log sin cos tan Exp +/-".contains(text)) {
				btn.setBackground(BUTTON_COLOR);
				btn.setForeground(TEXT_COLOR);
			} else if ("MC MR M+ M-".contains(text)) {
				btn.setBackground(MEMORY_COLOR);
				btn.setForeground(TEXT_COLOR);
			} else {
				btn.setBackground(BUTTON_COLOR);
				btn.setForeground(TEXT_COLOR);
			}
		}

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(10, 4, 8, 8));

	       // Layout
        p.add(bMR); p.add(bMC); p.add(bm1); p.add(bm2);
        p.add(b7);  p.add(b8);  p.add(b9);  p.add(bd);
        p.add(b4);  p.add(b5);  p.add(b6);  p.add(bm);
        p.add(b1);  p.add(b2);  p.add(b3);  p.add(bs);
        p.add(b0);  p.add(be);  p.add(bsign); p.add(ba);
        p.add(bsin);p.add(bcos);p.add(btan); p.add(bfac);
        p.add(bcsc);p.add(bsec);p.add(bcot); p.add(bmod);
        p.add(bsq); p.add(bsqr); p.add(bpow); p.add(binv);
        p.add(blshift); p.add(brshift);  p.add(bexp); p.add(bpi);
        p.add(bln); p.add(blog); p.add(bc); p.add(beq);

		// Apply dark theme to panel and frame
		p.setBackground(PANEL_COLOR);
		setBackground(BACKGROUND_COLOR);
		
		JPanel appPanel = new JPanel();

		appPanel.setLayout(new BorderLayout());
		appPanel.add(field, BorderLayout.NORTH);
		appPanel.add(p, BorderLayout.CENTER);
		
		appPanel.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createRaisedBevelBorder(),
			    BorderFactory.createLineBorder(Color.black, 5)
			));
		
		setContentPane(appPanel);
		
		setSize(440, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// The rest of your methods remain exactly the same
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		boolean isOneOverX = false;

		if ((s.length() > 1) && (s.charAt(0) == '1') && (s.charAt(1) == '/') && (s.charAt(2) == 'x'))
			isOneOverX = true;

		if (((s.charAt(0) >= '0' && s.charAt(0) <= '9') || s.equals(".")) && !isOneOverX) {
			if (!str1.equals("")) {
				str2 = str2 + s;
			} else {
				str = str + s;
			}
			field.setText(str + str1 + str2);
		} else if (s.equals("C")) {
			str = str1 = str2 = "";
			field.setText("");
		} else if (s.equals("=") || isUnaryOperation(str1)) {
			calculateResult(s);
		} else if (s.equals("MC") || s.equals("MR") || s.equals("M+") || s.equals("M-")) {
			handleMemory(s);
		} else if (isBinaryOperator(s)) {
			if (str1.equals("") || str2.equals("")) {
				str1 = s;
			} else {
				calculateResult("=");
				str1 = s;
				str2 = "";
			}
			field.setText(str + str1 + str2);
		} else if (s.equals("+/-")) {
			toggleSign();
		} else if (s.equals("π")) {
			insertPi();
		} else if (s.equals("e")) {
			insertExp();
		} else {
			str1 = s;
			actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "="));
		}
	}

	private boolean isUnaryOperation(String op) {
		return op.equals("√") || op.equals("1/x") || op.equals("log") || op.equals("ln") || op.equals("sin")
				|| op.equals("cos") || op.equals("tan") || op.equals("Exp") || op.equals("!") || op.equals("x²");
	}

	private boolean isBinaryOperator(String op) {
		return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("xʸ") || op.equals("mod")
				|| op.equals("<<") || op.equals(">>");
	}

	private void calculateResult(String command) {
		double te = 0;
		try {
			double num1 = str.isEmpty() ? 0 : Double.parseDouble(str);
			double num2 = str2.isEmpty() ? 0 : Double.parseDouble(str2);

			switch (str1) {
			case "+":
				te = num1 + num2;
				break;
			case "-":
				te = num1 - num2;
				break;
			case "*":
				te = num1 * num2;
				break;
			case "/":
				te = num1 / num2;
				break;
			case "xʸ":
				te = Math.pow(num1, num2);
				break;
			case "mod":
				te = num1 % num2;
				break;
			case "<<":
				te = (long) num1 << (long) num2;
				break;
			case ">>":
				te = (long) num1 >> (long) num2;
				break;

			case "√":
				te = Math.sqrt(num1);
				break;
			case "x²":
				te = num1 * num1;
				break;
			case "1/x":
				te = 1 / num1;
				break;
			case "log":
				te = Math.log10(num1);
				break;
			case "ln":
				te = Math.log(num1);
				break;
			case "sin":
				te = Math.sin(Math.toRadians(num1));
				break;
			case "cos":
				te = Math.cos(Math.toRadians(num1));
				break;
			case "tan":
				te = Math.tan(Math.toRadians(num1));
				break;
			case "csc":
				te = 1.0 / Math.sin(Math.toRadians(num1));
				break;
			case "sec":
				te = 1.0 / Math.cos(Math.toRadians(num1));
				break;
			case "cot":
				te = 1.0 / Math.tan(Math.toRadians(num1));
				break;
			case "!":
				int value = (int) num1;
				if (value < 0 || value > 20) {
					field.setText("Error");
					str = str1 = str2 = "";
					return;
				}
				long fact = factorial(value);
				field.setText(str + "! = " + fact);
				str = String.valueOf(fact);
				str1 = str2 = "";
				return;
			}

			field.setText(str + str1 + str2 + " = " + te);
			str = String.valueOf(te);
			str1 = str2 = "";

		} catch (Exception ex) {
			field.setText("Error");
			str = str1 = str2 = "";
		}
	}

	private void toggleSign() {
		if (!str2.equals("")) {
			str2 = str2.startsWith("-") ? str2.substring(1) : "-" + str2;
		} else if (!str.equals("")) {
			str = str.startsWith("-") ? str.substring(1) : "-" + str;
		}
		field.setText(str + str1 + str2);
	}

	private void insertPi() {
		String piStr = String.valueOf(Math.PI);
		if (!str1.equals("")) {
			str2 = piStr;
		} else {
			str = piStr;
		}
		field.setText(str + str1 + str2);
	}
	
	private void insertExp() {
		String expStr = String.valueOf(Math.E);
		if (!str1.equals("")) {
			str2 = expStr;
		} else {
			str = expStr;
		}
		field.setText(str + str1 + str2);
	}

	private void handleMemory(String op) {
		try {
			String displayText = field.getText().replaceAll("[^0-9.-]", "");
			double current = displayText.isEmpty() ? 0 : Double.parseDouble(displayText);

			switch (op) {
			case "MC":
				memory = 0;
				break;
			case "MR":
				str = String.valueOf(memory);
				str1 = str2 = "";
				field.setText(str);
				return;
			case "M+":
				memory += current;
				break;
			case "M-":
				memory -= current;
				break;
			}
		} catch (Exception ignored) {
		}
	}

	public static long factorial(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Negative factorial");
		long result = 1;
		for (int i = 1; i <= n; i++)
			result *= i;
		return result;
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingCalculator();
			}
		});
	}
}