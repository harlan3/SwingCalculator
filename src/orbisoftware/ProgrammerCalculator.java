package orbisoftware;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ProgrammerCalculator extends JPanel {

    private enum BaseMode {
        DEC(10), HEX(16), OCT(8), BIN(2);

        final int radix;

        BaseMode(int radix) {
            this.radix = radix;
        }
    }

    private final JTextField displayField = new JTextField();
    private final JLabel decValueLabel = new JLabel("0");
    private final JLabel hexValueLabel = new JLabel("0");
    private final JLabel octValueLabel = new JLabel("0");
    private final JLabel binValueLabel = new JLabel("0");

    private BaseMode currentBase = BaseMode.DEC;

    private final Map<String, JButton> buttonMap = new HashMap<>();

	// Dark Theme Colors
	private final Color BACKGROUND_COLOR = new Color(30, 30, 30); // Dark gray
	private final Color PANEL_COLOR = new Color(40, 40, 40); // Slightly lighter panel
	private final Color TEXT_COLOR = new Color(220, 220, 220); // Light gray text
	private final Color BUTTON_COLOR = new Color(55, 55, 55); // Normal buttons
	private final Color OPERATOR_COLOR = new Color(101, 152, 251); // Blue for operator buttons
	private final Color EQUAL_COLOR = new Color(0, 150, 100); // Green for =
	
	private int appWidth = 380;
			
    public ProgrammerCalculator() {

    }
    
    public JPanel setTheContentPane() {
    	
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    	
    	mainPanel.add(buildTopPanel());
    	mainPanel.add(buildButtonPanel());

        updateBaseButtons();
        updateConversionsFromDisplay();
        
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setForeground(PANEL_COLOR);

        return mainPanel;
    }
    
    public void frameResized(int width) {
    	
    	appWidth = width - 60;
    }

    private JPanel buildTopPanel() {
	
    	JPanel top = new JPanel(new BorderLayout(10, 10));

        displayField.setFont(new Font("Arial", Font.BOLD, 26));
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        displayField.setPreferredSize(new Dimension(300, 60));
        displayField.addActionListener(e -> evaluateExpression());
        displayField.setBackground(BACKGROUND_COLOR);
        displayField.setForeground(PANEL_COLOR);
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateConversionsFromDisplay();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateConversionsFromDisplay();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateConversionsFromDisplay();
            }
        });
        top.setBackground(BACKGROUND_COLOR);
        top.setForeground(PANEL_COLOR);
        
    	JPanel baseValuePane = buildBaseValuePanel();

    	JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    	bottomPanel.add(baseValuePane);

    	top.add(displayField, BorderLayout.CENTER);
    	top.add(bottomPanel, BorderLayout.SOUTH);

        return top;
    }
    
    private JPanel buildBaseValuePanel() {
    	
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        displayField.setBackground(PANEL_COLOR);
        displayField.setForeground(TEXT_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(PANEL_COLOR);
        panel.setForeground(TEXT_COLOR);
        
        Font valueFont = new Font(Font.MONOSPACED, Font.PLAIN, 16);

        JLabel[] valueLabels = {decValueLabel, hexValueLabel, octValueLabel, binValueLabel};
        String[] names = {"DEC", "HEX", "OCT", "BIN"};

        Dimension valueSize = new Dimension(appWidth, 22);

        gbc.insets = new Insets(2, 4, 2, 4);
        gbc.gridy = 0;

        for (int i = 0; i < valueLabels.length; i++) {
            JLabel valueLabel = valueLabels[i];
            valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            valueLabel.setFont(valueFont);
            valueLabel.setPreferredSize(valueSize);
            valueLabel.setMinimumSize(valueSize);
            valueLabel.setBackground(PANEL_COLOR);
            valueLabel.setForeground(TEXT_COLOR);

            gbc.gridx = 0;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.NONE;
            panel.add(valueLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.0;
            gbc.anchor = GridBagConstraints.WEST;
            JLabel myLabel = new JLabel(names[i]);
            myLabel.setBackground(PANEL_COLOR);
            myLabel.setForeground(TEXT_COLOR);
            panel.add(myLabel, gbc);

            gbc.gridy++;
        }

        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 5, 4, 4));
        panel.setBorder(new EmptyBorder(10, 8, 8, 8));
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        panel.setBackground(PANEL_COLOR);
        
        String[][] rows = {
                {"AND", "OR", "NOT", "DEC", "HEX"},
                {"NAND", "NOR", "XOR", "OCT", "BIN"},
                {"A", "<<", ">>", "CL", "⌫"},
                {"B", "(", ")", "%", "÷"},
                {"C", "7", "8", "9", "×"},
                {"D", "4", "5", "6", "−"},
                {"E", "1", "2", "3", "+"},
                {"F", "+/-", "0", "S", "="}
        };

        for (String[] row : rows) {
            for (String text : row) {
                JButton button = createButton(text);
                button.setFont(buttonFont);
                button.setBackground(BUTTON_COLOR);
                button.setForeground(TEXT_COLOR);
                if (text.equals("=")) {
                    button.setBackground(EQUAL_COLOR);
                    button.setForeground(TEXT_COLOR);
                }
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                panel.add(button);
            }
        }

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        button.setMargin(new Insets(8, 8, 8, 8));
        buttonMap.put(text, button);

        switch (text) {
            case "=" -> button.addActionListener(e -> evaluateExpression());
            case "CL" -> button.addActionListener(e -> displayField.setText(""));
            case "⌫" -> button.addActionListener(e -> backspace());
            case "DEC" -> button.addActionListener(e -> setBase(BaseMode.DEC));
            case "HEX" -> button.addActionListener(e -> setBase(BaseMode.HEX));
            case "OCT" -> button.addActionListener(e -> setBase(BaseMode.OCT));
            case "BIN" -> button.addActionListener(e -> setBase(BaseMode.BIN));
            case "NOT" -> button.addActionListener(e -> appendToken("~"));
            case "AND" -> button.addActionListener(e -> appendToken("&"));
            case "OR" -> button.addActionListener(e -> appendToken("|"));
            case "XOR" -> button.addActionListener(e -> appendToken("^"));
            case "NAND" -> button.addActionListener(e -> appendToken(" NAND "));
            case "NOR" -> button.addActionListener(e -> appendToken(" NOR "));
            case "<<" -> button.addActionListener(e -> appendToken(" << "));
            case ">>" -> button.addActionListener(e -> appendToken(" >> "));
            case "÷" -> button.addActionListener(e -> appendToken(" / "));
            case "×" -> button.addActionListener(e -> appendToken(" * "));
            case "−" -> button.addActionListener(e -> appendToken(" - "));
            case "+" -> button.addActionListener(e -> appendToken(" + "));
            case "%" -> button.addActionListener(e -> appendToken(" % "));
            case "(" -> button.addActionListener(e -> appendToken("("));
            case ")" -> button.addActionListener(e -> appendToken(")"));
            case "+/-" -> button.addActionListener(e -> toggleSign());
            case "S" -> button.addActionListener(e -> switchToScientific());
            default -> button.addActionListener(e -> appendToken(text));
        }

        return button;
    }

    private void switchToScientific() {

        CardLayout cl = (CardLayout) SharedClasses.cards.getLayout();
        cl.show(SharedClasses.cards, "one");
        SharedClasses.activeCard = 0;
    }
    
    private void appendToken(String token) {
        displayField.setText(displayField.getText() + token);
    }

    private void backspace() {
        String text = displayField.getText();
        if (!text.isEmpty()) {
            displayField.setText(text.substring(0, text.length() - 1));
        }
    }

    private void toggleSign() {
        String text = displayField.getText().trim();
        if (text.isEmpty()) {
            displayField.setText("-");
            return;
        }

        int lastSpace = Math.max(text.lastIndexOf(' '), Math.max(text.lastIndexOf('('), text.lastIndexOf(')')));
        String prefix = "";
        String token = text;

        if (lastSpace >= 0) {
            prefix = text.substring(0, lastSpace + 1);
            token = text.substring(lastSpace + 1);
        }

        if (token.startsWith("-")) {
            token = token.substring(1);
        } else if (!token.isEmpty()) {
            token = "-" + token;
        }

        displayField.setText(prefix + token);
    }

    private void setBase(BaseMode newBase) {
        try {
            long value = evaluate(displayField.getText(), currentBase);
            currentBase = newBase;
            displayField.setText(formatValue(value, currentBase));
        } catch (Exception ignored) {
            currentBase = newBase;
        }

        updateBaseButtons();
        updateConversionsFromDisplay();
    }

    private void updateBaseButtons() {
        enableDigitsForBase();
        highlightBaseButton("DEC", currentBase == BaseMode.DEC);
        highlightBaseButton("HEX", currentBase == BaseMode.HEX);
        highlightBaseButton("OCT", currentBase == BaseMode.OCT);
        highlightBaseButton("BIN", currentBase == BaseMode.BIN);
    }

    private void highlightBaseButton(String key, boolean active) {
        JButton button = buttonMap.get(key);
        if (button != null) {
            if (active) {
                button.setBackground(OPERATOR_COLOR);
                button.setForeground(TEXT_COLOR);
                button.setOpaque(true);
            } else {
                button.setBackground(BUTTON_COLOR);
                button.setForeground(TEXT_COLOR);
                button.setOpaque(true);
            }
        }
    }

    private void enableDigitsForBase() {
        Set<String> enabled = switch (currentBase) {
            case BIN -> Set.of("0", "1");
            case OCT -> Set.of("0", "1", "2", "3", "4", "5", "6", "7");
            case DEC -> Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
            case HEX -> Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F");
        };

        for (String key : List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")) {
            JButton button = buttonMap.get(key);
            if (button != null) {
                button.setEnabled(enabled.contains(key));
            }
        }
    }

    private void evaluateExpression() {
        try {
            long result = evaluate(displayField.getText(), currentBase);
            displayField.setText(formatValue(result, currentBase));
            updateConversions(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid expression:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateConversionsFromDisplay() {
        try {
            long value = evaluate(displayField.getText(), currentBase);
            updateConversions(value);
        } catch (Exception ex) {
            decValueLabel.setText("0");
            hexValueLabel.setText("0");
            octValueLabel.setText("0");
            binValueLabel.setText("0");
        }
    }

    private void updateConversions(long value) {
        decValueLabel.setText(Long.toString(value));
        hexValueLabel.setText(Long.toHexString(value).toUpperCase());
        octValueLabel.setText(Long.toOctalString(value));
        binValueLabel.setText(Long.toBinaryString(value));
    }

    private String formatValue(long value, BaseMode base) {
        return switch (base) {
            case DEC -> Long.toString(value);
            case HEX -> Long.toHexString(value).toUpperCase();
            case OCT -> Long.toOctalString(value);
            case BIN -> Long.toBinaryString(value);
        };
    }

    private long evaluate(String expression, BaseMode base) {
        if (expression == null || expression.trim().isEmpty()) {
            return 0;
        }
        List<String> tokens = tokenize(expression);
        List<String> postfix = toPostfix(tokens);
        return evalPostfix(postfix, base);
    }

    private List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        int i = 0;

        while (i < expr.length()) {
            char ch = expr.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            if (ch == '(' || ch == ')' || ch == '%' || ch == '^' || ch == '~') {
                tokens.add(String.valueOf(ch));
                i++;
                continue;
            }

            if (ch == '+' || ch == '*' || ch == '/' || ch == '&' || ch == '|') {
                tokens.add(String.valueOf(ch));
                i++;
                continue;
            }

            if (ch == '-') {
                String prev = tokens.isEmpty() ? null : tokens.get(tokens.size() - 1);
                boolean unary = prev == null || isOperator(prev) || "(".equals(prev);
                if (unary) {
                    int j = i + 1;
                    while (j < expr.length() && Character.isWhitespace(expr.charAt(j))) {
                        j++;
                    }
                    if (j < expr.length() && isNumberChar(expr.charAt(j))) {
                        StringBuilder sb = new StringBuilder("-");
                        i = j;
                        while (i < expr.length() && isNumberChar(expr.charAt(i))) {
                            sb.append(expr.charAt(i));
                            i++;
                        }
                        tokens.add(sb.toString());
                    } else {
                        tokens.add("u-");
                        i++;
                    }
                } else {
                    tokens.add("-");
                    i++;
                }
                continue;
            }

            if (ch == '<' || ch == '>') {
                if (i + 1 < expr.length() && expr.charAt(i + 1) == ch) {
                    tokens.add("" + ch + ch);
                    i += 2;
                    continue;
                }
                throw new IllegalArgumentException("Invalid shift operator near: " + expr.substring(i));
            }

            if (Character.isLetter(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isLetterOrDigit(expr.charAt(i)))) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                tokens.add(sb.toString().toUpperCase());
                continue;
            }

            if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && isNumberChar(expr.charAt(i))) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                tokens.add(sb.toString().toUpperCase());
                continue;
            }

            if (ch == '.') {
                throw new IllegalArgumentException("Floating point values are not supported.");
            }

            throw new IllegalArgumentException("Unexpected character: " + ch);
        }

        return tokens;
    }

    private boolean isNumberChar(char ch) {
        return Character.isDigit(ch) || Character.isLetter(ch);
    }

    private boolean isOperator(String token) {
        return Set.of("+", "-", "*", "/", "%", "&", "|", "^", "<<", ">>", "~", "u-", "NAND", "NOR").contains(token);
    }

    private int precedence(String op) {
        return switch (op) {
            case "~", "u-" -> 7;
            case "*", "/", "%" -> 6;
            case "+", "-" -> 5;
            case "<<", ">>" -> 4;
            case "&", "NAND" -> 3;
            case "^", "XOR" -> 2;
            case "|", "NOR" -> 1;
            default -> 0;
        };
    }

    private boolean rightAssociative(String op) {
        return "~".equals(op) || "u-".equals(op);
    }

    private List<String> toPostfix(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> ops = new ArrayDeque<>();

        for (String token : tokens) {
            if (isNumberToken(token)) {
                output.add(token);
            } else if ("(".equals(token)) {
                ops.push(token);
            } else if (")".equals(token)) {
                while (!ops.isEmpty() && !"(".equals(ops.peek())) {
                    output.add(ops.pop());
                }
                if (ops.isEmpty() || !"(".equals(ops.peek())) {
                    throw new IllegalArgumentException("Mismatched parentheses.");
                }
                ops.pop();
            } else if (isOperator(token)) {
                while (!ops.isEmpty() && isOperator(ops.peek())) {
                    String top = ops.peek();
                    if ((rightAssociative(token) && precedence(token) < precedence(top))
                            || (!rightAssociative(token) && precedence(token) <= precedence(top))) {
                        output.add(ops.pop());
                    } else {
                        break;
                    }
                }
                ops.push(token);
            } else {
                throw new IllegalArgumentException("Unknown token: " + token);
            }
        }

        while (!ops.isEmpty()) {
            String op = ops.pop();
            if ("(".equals(op) || ")".equals(op)) {
                throw new IllegalArgumentException("Mismatched parentheses.");
            }
            output.add(op);
        }

        return output;
    }

    private boolean isNumberToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        if ("-".equals(token)) {
            return false;
        }
        if (token.startsWith("-") && token.length() > 1) {
            token = token.substring(1);
        }
        for (char ch : token.toCharArray()) {
            if (!Character.isDigit(ch) && !(ch >= 'A' && ch <= 'F')) {
                return false;
            }
        }
        return true;
    }

    private long evalPostfix(List<String> postfix, BaseMode base) {
        Deque<Long> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (isNumberToken(token)) {
                stack.push(parseNumber(token, base.radix));
                continue;
            }

            switch (token) {
                case "~" -> {
                    long a = pop(stack);
                    stack.push(~a);
                }
                case "u-" -> {
                    long a = pop(stack);
                    stack.push(-a);
                }
                case "+" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a + b);
                }
                case "-" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a - b);
                }
                case "*" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a * b);
                }
                case "/" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero.");
                    }
                    stack.push(a / b);
                }
                case "%" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero.");
                    }
                    stack.push(a % b);
                }
                case "&" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a & b);
                }
                case "|" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a | b);
                }
                case "^", "XOR" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a ^ b);
                }
                case "NAND" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(~(a & b));
                }
                case "NOR" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(~(a | b));
                }
                case "<<" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a << b);
                }
                case ">>" -> {
                    long b = pop(stack);
                    long a = pop(stack);
                    stack.push(a >> b);
                }
                default -> throw new IllegalArgumentException("Unsupported operator: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Malformed expression.");
        }

        return stack.pop();
    }

    private long pop(Deque<Long> stack) {
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("Malformed expression.");
        }
        return stack.pop();
    }

    private long parseNumber(String token, int radix) {
        boolean negative = token.startsWith("-");
        String body = negative ? token.substring(1) : token;

        if (body.isEmpty()) {
            throw new IllegalArgumentException("Invalid number.");
        }

        for (char ch : body.toUpperCase().toCharArray()) {
            int digit = Character.digit(ch, radix);
            if (digit < 0) {
                throw new IllegalArgumentException(
                        "Digit '" + ch + "' is not valid for base " + radix + "."
                );
            }
        }

        long value = Long.parseLong(body, radix);
        return negative ? -value : value;
    }
}