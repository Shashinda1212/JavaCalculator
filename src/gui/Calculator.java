package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.DocFlavor;
import javax.swing.JButton;
import javax.swing.JFrame;
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

        this.setTitle("Java Calculator");
        this.setSize(new Dimension(350, 400));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel displayPanel = new JPanel();
        BorderLayout bl = new BorderLayout();
        displayPanel.setLayout(bl);

        JPanel buttonPanel = new JPanel();
        GridLayout gl = new GridLayout(6, 4, 2, 2);
        buttonPanel.setLayout(gl);

        JTextField displayField = new JTextField();
        displayField.setPreferredSize(new Dimension(40, 73));
        displayField.setFont(new Font("Arial", Font.BOLD, 35));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBorder(null);
        displayField.setEditable(false);
        displayField.setText("0");

        displayPanel.add(displayField, BorderLayout.CENTER);

        String[] btnLabels = {
            "%", "CE", "C", "<",
            "1/x", "x^2", "√", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        for (String label : btnLabels) {

            JButton button = new JButton(label);

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
                            if (!currentText.equals("0") && currentText.length() > 0) {

                                displayField.setText(currentText.substring(0, currentText.length() - 1));
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
                        case ("*"):
                        case ("%"):
                            if (isOperator == false) {
                                firstNumber = Double.parseDouble(currentText);
                                operator = label;
                                isOperator = true;
                                displayField.setText("");
                            }else{
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

                                    case ("*"):
                                        finalResult = firstNumber * secondNumber;
                                        break;

                                    case ("+"):
                                        finalResult = firstNumber + secondNumber;
                                        break;

                                    case ("-"):
                                        finalResult = firstNumber - secondNumber;
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

                                if(!currentText.equals("Error")){
                                
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
    }

    private String formatNumber(double num) {
        if (num == (int) num) {
            return String.valueOf((int) num);
        } else {
            return String.valueOf(num);
        }
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new Calculator().setVisible(true);
    }
}
