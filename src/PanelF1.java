import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class PanelF1 extends JPanel implements ActionListener{
    JLabel label1;
    JLabel label2;
    ImageIcon background;
    JButton button1;
    JButton button2;
    JButton button3;
    FrameF frame;
    PanelG1 panelG1;
    Option option;
    static int mDiff=1;
    PanelF1(FrameF frameF) {
        this.frame=frameF;
        background=new ImageIcon("6.jpg");
        label1=new JLabel();
        label1.setIcon(background);
        label1.setPreferredSize(new Dimension(background.getIconWidth(), background.getIconHeight()));
        label1.setText("Snake Game");
        label1.setFont(new Font("MV Boli",Font.PLAIN,40));
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.TOP);
        label1.setIconTextGap(-70);
        
        button1=new JButton();
        button1.setBounds(225,150,150,50);
        button1.addActionListener(this);
        button1.setText("Start Game");
        button1.setBackground(new Color(70,170,80));
        button1.setForeground(Color.BLACK);

        button2=new JButton();
        button2.setBounds(225,270,150,50);
        button2.setText("Quit");
        button2.addActionListener(this);
        button2.setBackground(new Color(70,170,80));
        button2.setForeground(Color.BLACK);

        button3=new JButton();
        button3.setBounds(225,210,150,50);
        button3.setText("Game Option");
        button3.setBackground(new Color(70,170,80));
        button3.setForeground(Color.BLACK);
        button3.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(label1, BorderLayout.CENTER);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(label1);
        this.setPreferredSize(new Dimension(600,400));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
        // Tạo panelG1 và thiết lập giá trị diff trước khi thay đổi nội dung của frame
        panelG1 = new PanelG1(frame,getmDiff());        
        frame.setContentPane(panelG1);
        frame.revalidate();
        frame.repaint();
        
        panelG1.requestFocusInWindow(); //ensure panelG1 focus when add in frame
    }
    if (e.getSource() == button2) {
        frame.dispose();
    }
    if (e.getSource() == button3) {
        try {
            option = new Option();
            // Sử dụng một Listener để nhận giá trị ngay khi cửa sổ Option đóng
            option.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent we) {
                    int newDiff = option.getChooseDiff();
                    setmDiff(newDiff);
                }
            });
        } catch (Exception ex) {
            System.out.println("Failed to create Option object.");
        }
    }
}

    public void setmDiff(int mDiff){
        this.mDiff=mDiff;
    }
    public int getmDiff(){
        return mDiff;
    }

}
