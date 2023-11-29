import javax.swing.*;
import java.awt.*;

public class CashFlowMinimizer{
    static int N;
    static JTextArea outputArea;

    static int getMin(int arr[]) {
        int minInd = 0;
        for (int i = 1; i < N; i++)
            if (arr[i] < arr[minInd])
                minInd = i;
        return minInd;
    }

    static int getMax(int arr[]) {
        int maxInd = 0;
        for (int i = 1; i < N; i++)
            if (arr[i] > arr[maxInd])
                maxInd = i;
        return maxInd;
    }

    static int minOf2(int x, int y) {
        return (x < y) ? x : y;
    }

    static void minCashFlowRec(int amount[]) {
        int mxCredit = getMax(amount), mxDebit = getMin(amount);

        if (amount[mxCredit] == 0 && amount[mxDebit] == 0)
            return;

        int min = minOf2(-amount[mxDebit], amount[mxCredit]);
        amount[mxCredit] -= min;
        amount[mxDebit] += min;

        String S = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        char[] B = S.toCharArray();
        String paymentDetail = "Person " + B[mxDebit] + " pays " + min + " to Person " + B[mxCredit] + "\n";
        outputArea.append(paymentDetail);

        minCashFlowRec(amount);
    }

    static void minCashFlow(int graph[][]) {
        int amount[] = new int[N];

        for (int p = 0; p < N; p++)
            for (int i = 0; i < N; i++)
                amount[p] += (graph[i][p] - graph[p][i]);

        minCashFlowRec(amount);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cash Minimizer");
        JLabel label = new JLabel("Enter the no. of Group Members :");
        JTextField textField = new JTextField(10);
        JButton button = new JButton("Next");

        label.setBounds(10, 10, 250, 30);
        textField.setBounds(270, 10, 100, 30);
        button.setBounds(10, 50, 100, 30);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));

        frame.add(label);
        frame.add(textField);
        frame.add(button);
        frame.add(inputPanel);

        outputArea = new JTextArea(15, 30);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 90, 350, 150);
        

        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(e -> {
            String input = textField.getText();
            if (!input.isEmpty()) {
                N = Integer.parseInt(input);
                
                int graph[][] = new int[N][N];
                JTextField[][] elements = new JTextField[N][N];
                String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
                char[] A = str.toCharArray();

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (i != j) {
                            JTextField element = new JTextField(5);
                            inputPanel.add(new JLabel("Money transfer from " +A[i]+ " to " +A[j]+ " :"));
                            inputPanel.add(element);
                            elements[i][j] = element;
                        }
                    }
                }

                JButton calculateButton = new JButton("Calculate");
                inputPanel.add(calculateButton);

                calculateButton.addActionListener(ev -> {
                    frame.add(scrollPane);
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            if (i != j) {
                                graph[i][j] = Integer.parseInt(elements[i][j].getText());
                            }
                        }
                    }
                    minCashFlow(graph);
                });

                frame.validate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid matrix size!");
            }
        });
    }

    
}
