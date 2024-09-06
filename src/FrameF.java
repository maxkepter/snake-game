import javax.swing.*;

public class FrameF extends JFrame {
    PanelF1 panelF1;
    PanelG1 panelG1;

    FrameF() {
        panelF1 = new PanelF1(this);
        this.setBounds(500, 150, 600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelF1);
        this.pack();
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Snake game");
        this.setVisible(true);
    }

    public void switchToPanelF1() {
        panelF1 = new PanelF1(this);
        this.setContentPane(panelF1);
        this.revalidate();
        this.repaint();
    }

    public void switchToPanelG1() {
        panelG1 = new PanelG1(this,panelF1.getmDiff()); // Truyền tham chiếu FrameF vào PanelG1
        this.setContentPane(panelG1);
        this.revalidate();
        this.repaint();
        panelG1.requestFocusInWindow(); // Đảm bảo PanelG1 có tiêu điểm ngay lập tức
    }
}
