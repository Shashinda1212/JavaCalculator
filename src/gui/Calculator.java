package gui;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame {

    public Calculator() {

        init();
    }

    private double firstNumber;
    private String operator;
    private boolean isOperator;

    private void init() {

        this.setTitle("My Calculator");
        this.setSize(new Dimension(330, 450));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel displayPanel = new JPanel();
        BorderLayout bl = new BorderLayout();
        displayPanel.setLayout(bl);

        JPanel buttonPanel = new JPanel();
        GridLayout gl = new GridLayout(6, 4, 4, 4);
        buttonPanel.setLayout(gl);

        JTextField displayField = new JTextField();
        displayField.setPreferredSize(new Dimension(40, 73));
        displayField.setFont(new Font("Arial", Font.BOLD, 35));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBorder(null);
        displayField.setEditable(false);
        displayField.setText("0");

        JLabel labelText = new JLabel("Standard Calculator");

        displayPanel.add(labelText, BorderLayout.NORTH);
        displayPanel.add(displayField);

        String[] btnLabels = {
            "CE", "C", "<", "%",
            "1/x", "x^2", "√", "/",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        for (String label : btnLabels) {

            JButton button = new JButton(label);

            if (label == "=") {

                button.setBackground(new Color(71, 177, 234));
            }

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String buttonText = e.getActionCommand();
                    String currentText = displayField.getText();

                    switch (buttonText) {

                        case ("CE"):
                            displayField.setText("0");
                            operator = null;
                            isOperator = false;
                            firstNumber = 0;
                            break;

                        case ("C"):
                            displayField.setText("0");
                            operator = null;
                            isOperator = false;
                            firstNumber = 0;
                            break;

                        case ("<"):
                            if (!currentText.equals("0") && currentText.length() > 1) {

                                displayField.setText(currentText.substring(0, currentText.length() - 1));

                            } else {

                                displayField.setText("0");
                            }
                            break;

                        case ("1/x"):
                            double currentTextValue = Double.parseDouble(currentText);
                            double result;
                            if (currentTextValue != 0) {
                                result = 1 / currentTextValue;
                                displayField.setText(formatNumber(result));
                            } else {

                                displayField.setText("Error");
                            }
                            break;

                        case ("x^2"):
                            double sqr = Math.pow(Double.parseDouble(currentText), 2);
                            displayField.setText(formatNumber(sqr));
                            break;

                        case ("√"):
                            double sqrt = Math.sqrt(Double.parseDouble(currentText));
                            displayField.setText(formatNumber(sqrt));
                            break;

                        case ("/"):
                        case ("+"):
                        case ("-"):
                        case ("x"):
                        case ("%"):
                            if (isOperator == false) {
                                firstNumber = Double.parseDouble(currentText);
                                operator = label;
                                isOperator = true;
                                displayField.setText("");
                            } else {
                                displayField.setText("Error");
                            }
                            break;

                        case ("."):
                            if (!currentText.contains(".")) {
                                displayField.setText(currentText + ".");
                            }
                            break;

                        case ("="):
                            if (isOperator == true) {
                                double secondNumber = Double.parseDouble(displayField.getText());
                                double finalResult = 0;

                                switch (operator) {
                                                                    
                                    case ("/"):
                                        if (secondNumber == 0) {
                                            displayField.setText("Error");
                                            return;
                                        } else {
                                            finalResult = firstNumber / secondNumber;
                                        }
                                        break;

                                    case ("x"):
                                        finalResult = firstNumber * secondNumber;
                                        break;

                                    case ("+"):
                                        finalResult = firstNumber + secondNumber;
                                        break;

                                    case ("-"):
                                        finalResult = firstNumber - secondNumber;
                                        break;

                                    case ("%"):
                                        finalResult = firstNumber % secondNumber;
                                        break;
                                }
                                displayField.setText(formatNumber(finalResult));

                            }

                            isOperator = false;
                            operator = null;
                            firstNumber = 0;
                            break;

                        default:
                            if ("0123456789".contains(buttonText)) {

                                if (!currentText.equals("Error")) {

                                    if (currentText.equals("0")) {

                                        displayField.setText(buttonText);
                                    } else {

                                        displayField.setText(currentText + buttonText);
                                    }
                                }
                            }
                            break;
                    }
                }
            });

            buttonPanel.add(button);
        }

        this.setLayout(new BorderLayout());
        this.add(displayPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
        appIcon.applyIcon(this);

    }

    private String formatNumber(double num) {
        if (num == (int) num) {
            return String.valueOf((int) num);
        } else {
            DecimalFormat dcm = new DecimalFormat("#.##");
            return String.valueOf(dcm.format(num));
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new Calculator().setVisible(true);
    }
}

class appIcon {

    private static Image icon;

    private static void util() {

        try {

            URL iconPath = appIcon.class.getResource("/gui/calculator-icon-1.png");
            ImageIcon imageicon = new ImageIcon(iconPath);
            appIcon.icon = imageicon.getImage();
        } catch (NullPointerException e) {

            e.printStackTrace();
        }

    }

    public static void applyIcon(JFrame frame) {

        if (frame != null) {
            util();
            frame.setIconImage(icon);
        }
    }
}
