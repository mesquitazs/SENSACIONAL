import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora1 extends JFrame {
    private JTextField display;
    private StringBuilder entrada = new StringBuilder();
    private double resultado = 0;
    private String operacao = "";
    private boolean novaOperacao = true;

    public Calculadora1() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(240, 240, 240));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        painel.add(display, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 5, 5));
        painelBotoes.setBackground(new Color(240, 240, 240));

        String[] botoes = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "←", "%", "±"
        };

        for (String btnTexto : botoes) {
            JButton btn = new JButton(btnTexto);
            btn.setFont(new Font("Arial", Font.PLAIN, 18));
            btn.setFocusPainted(false);
            btn.addActionListener(e -> onBotaoClicado(btnTexto));
            painelBotoes.add(btn);
        }

        painel.add(painelBotoes, BorderLayout.CENTER);
        add(painel);
        setVisible(true);
    }

    private void onBotaoClicado(String btnTexto) {
        switch (btnTexto) {
            case "C":
                limpar();
                break;
            case "←":
                apagar();
                break;
            case "=":
                calcular();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                operador(btnTexto);
                break;
            case "±":
                alterarSinal();
                break;
            default:
                adicionarNumero(btnTexto);
        }
    }

    private void adicionarNumero(String num) {
        if (novaOperacao) {
            entrada.setLength(0);
            novaOperacao = false;
        }
        entrada.append(num);
        display.setText(entrada.toString());
    }

    private void operador(String op) {
        if (entrada.length() > 0) {
            resultado = Double.parseDouble(entrada.toString());
            operacao = op;
            entrada.setLength(0);
            novaOperacao = true;
        }
    }

    private void calcular() {
        if (entrada.length() > 0 && !operacao.isEmpty()) {
            double num2 = Double.parseDouble(entrada.toString());
            double res = 0;

            switch (operacao) {
                case "+": res = resultado + num2; break;
                case "-": res = resultado - num2; break;
                case "*": res = resultado * num2; break;
                case "/": res = num2 != 0 ? resultado / num2 : 0; break;
                case "%": res = resultado % num2; break;
            }

            display.setText(formatarResultado(res));
            entrada.setLength(0);
            entrada.append(formatarResultado(res));
            operacao = "";
            novaOperacao = true;
        }
    }

    private void limpar() {
        entrada.setLength(0);
        resultado = 0;
        operacao = "";
        display.setText("");
        novaOperacao = true;
    }

    private void apagar() {
        if (entrada.length() > 0) {
            entrada.deleteCharAt(entrada.length() - 1);
            display.setText(entrada.toString());
        }
    }

    private void alterarSinal() {
        if (entrada.length() > 0) {
            double num = Double.parseDouble(entrada.toString());
            entrada.setLength(0);
            entrada.append(formatarResultado(-num));
            display.setText(entrada.toString());
        }
    }

    private String formatarResultado(double num) {
        return num == (long) num ? String.format("%d", (long) num) : String.format("%.2f", num);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculadora1::new);
    }
}