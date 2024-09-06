import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;


public class Option extends JFrame implements ActionListener {
    JComboBox<String> combo;
    JLabel label;
    private static int chooseDiff=1;
    private final Object lock = new Object();

    Option() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Difficult");
        this.setResizable(false);
        this.setBounds(700, 350, 200, 100);
        this.setLayout(new FlowLayout());

        String[] diff = {"Easy", "Medium", "Hard", "Địa Ngục"};
        label = new JLabel("     Difficult :");
        combo = new JComboBox<>(diff);

        combo.addActionListener(this);
        this.add(label);
        this.add(combo);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == combo) {
            this.setChooseDiff(combo.getSelectedIndex());
            if (combo.getSelectedIndex() == 3) {
                int response = JOptionPane.showConfirmDialog(
                    null,
                    "Đây là chế độ cực kỳ nguy hiểm, bạn có chắc chứ?",
                    "Cảnh Báo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    dispose(); 
                }
            } else {
                dispose(); 
            }
        }
    }

    public void setChooseDiff(int chooseDiff) {
        this.chooseDiff = chooseDiff;
    }

    public int getChooseDiff() {
        return chooseDiff;
    }

}
