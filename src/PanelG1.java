import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

public class PanelG1 extends JPanel implements KeyListener, ActionListener {
    FrameF frame;   
    
    ImageIcon gback;
      
    Timer timer;
    Timer delayTimer; 

    JLabel snake;
    JLabel lScore;
    JLabel food;    
    JLabel rec1;
    JLabel rec2;
    JLabel rec3;
    JLabel rec4;
    JLabel lgback;

    ArrayList<JLabel> snakeParts;
    Random random;       
    boolean gameOver = false;    
    static int check = 0;

    int score = 0; 
    static int diff;
    int direction = KeyEvent.VK_LEFT;
    int newDirection = KeyEvent.VK_LEFT;
    PanelG1(FrameF frameF,int diff) { // Truyền tham chiếu FrameF và diff vào PanelG1
        this.frame = frameF;
        this.setBackground(new Color(44, 242, 49));        
        this.diff=diff;        
        
        random = new Random();
        snakeParts = new ArrayList<>();
        gback=new ImageIcon("R.jpg");

        lgback=new JLabel();
        lgback.setIcon(gback);
        lgback.setBounds(0, 0, 600, 400);
        lgback.setText("TEST");
        

        lScore = new JLabel();
        lScore.setText("Score: " + score);
        lScore.setBounds(20, 10, 100, 30);
        lScore.setFont(new Font("Helvetica Bold", Font.PLAIN, 20));

        rec1 = new JLabel();
        rec1.setBounds(20, 50, 10, 330);
        rec1.setBackground(Color.BLACK);
        rec1.setOpaque(true);

        rec2 = new JLabel();
        rec2.setBounds(20, 50, 565, 10);
        rec2.setBackground(Color.BLACK);
        rec2.setOpaque(true);

        rec3 = new JLabel();
        rec3.setBounds(580, 50, 10, 330);
        rec3.setBackground(Color.BLACK);
        rec3.setOpaque(true);

        rec4 = new JLabel();
        rec4.setBounds(20, 370, 565, 10);
        rec4.setBackground(Color.BLACK);
        rec4.setOpaque(true);

        food = new JLabel();
        placeFoodRandomly();
        food.setBackground(Color.RED);
        food.setOpaque(true);

        snake = new JLabel();
        snake.setBounds(300, 200, 10, 10);
        snake.setBackground(Color.BLACK);
        snake.setOpaque(true);

        snakeParts.add(snake);
        this.setLayout(null);        
        this.add(lScore);
        this.add(rec1);
        this.add(rec2);
        this.add(rec3);
        this.add(rec4);
        this.add(snake);
        this.add(food);
        if(diff>=3){
        this.add(lgback);
        this.setComponentZOrder(lgback, this.getComponentCount() - 1);
        for (JLabel part : snakeParts) {
            part.setBackground(Color.WHITE);
        }
        rec1.setBackground(new Color(133,27,13));
        rec2.setBackground(new Color(133,27,13));
        rec3.setBackground(new Color(133,27,13));
        rec4.setBackground(new Color(133,27,13));
    
        lScore.setForeground(new Color(145,20,9));

        // Phát âm thanh
        File file = new File("audio.wav");
        playSound(file,6.0f);
        }
        this.setPreferredSize(new Dimension(600, 400));



        // Thêm KeyListener vào PanelG1
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow(); // Đảm bảo PanelG1 có tiêu điểm ngay lập tức

        // Tạo Timer để di chuyển Snake liên tục
        timer = new Timer(changeSpeed(), this); 
        timer.start();

        // Tạo Timer để xử lý độ trễ
        if (diff == 3) {
            delayTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(score>=20){
                        placeFoodRandomly();
                    }                                       
                }
            });
            delayTimer.setRepeats(true); // Đặt Timer lặp lại
        }
    }
    public void setDiff(int diff){
        this.diff=diff;
    }
    public int getDiff(){
        return diff;
    }

    public int changeSpeed() {
        switch (this.getDiff()) {
            case 0:
                return 150;
            case 1:
                return 100;
            case 2:
                return 60;
            case 3:
                return 30;
            default: 
                return 30;
        }
    }
    public void playSound(File soundFile, float volume) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
    
            // Điều chỉnh âm lượng
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume); // Giảm âm lượng: âm lượng có thể là -80.0f đến 6.0f
    
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    

    private void placeFoodRandomly() {
        int x = random.nextInt(530) + 25;
        while (x % 10 != 0) {
            x = random.nextInt(530) + 25;
        }
        int y = random.nextInt(300) + 55;
        while (y % 10 != 0) {
            y = random.nextInt(300) + 55;
        }
        food.setBounds(x, y, 10, 10);
    }

    private void growSnake(int x, int y) {
        JLabel newPart = new JLabel();
        newPart.setBounds(x, y, 10, 10);
        newPart.setBackground(Color.BLACK);
        newPart.setOpaque(true);
        snakeParts.add(newPart);
        this.add(newPart);
        this.repaint();
    }

    private void checkCollision() {
        Rectangle headBounds = snake.getBounds();
        for (int i = 1; i < snakeParts.size(); i++) {
            if (headBounds.intersects(snakeParts.get(i).getBounds())) {
                gameOver = true;
                timer.stop(); // Dừng Timer khi va chạm xảy ra
                JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
                frame.switchToPanelF1(); // Quay lại PanelF1 khi trò chơi kết thúc
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // Cập nhật hướng di chuyển khi nhấn phím
        int newDir = -1;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                newDir = KeyEvent.VK_LEFT;
                break;
            case KeyEvent.VK_D:
                newDir = KeyEvent.VK_RIGHT;
                break;
            case KeyEvent.VK_W:
                newDir = KeyEvent.VK_UP;
                break;
            case KeyEvent.VK_S:
                newDir = KeyEvent.VK_DOWN;
                break;
            default:
            return;
        }

        // Ngăn chặn di chuyển ngược chiều
        if ((direction == KeyEvent.VK_LEFT && newDir != KeyEvent.VK_RIGHT) ||
            (direction == KeyEvent.VK_RIGHT && newDir != KeyEvent.VK_LEFT) ||
            (direction == KeyEvent.VK_UP && newDir != KeyEvent.VK_DOWN) ||
            (direction == KeyEvent.VK_DOWN && newDir != KeyEvent.VK_UP)) {
            newDirection = newDir;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return; // Nếu trò chơi kết thúc, không thực hiện bất kỳ hành động nào
        }

        direction = newDirection; 

        int previousX[] = new int[snakeParts.size()];
        int previousY[] = new int[snakeParts.size()];
        for (int i = 0; i < snakeParts.size(); i++) {
            previousX[i] = snakeParts.get(i).getX();
            previousY[i] = snakeParts.get(i).getY();
        }

        // Di chuyển rắn theo hướng hiện tại
        switch (direction) {
            case KeyEvent.VK_LEFT:
                snake.setLocation(snake.getX() - 10, snake.getY());
                break;
            case KeyEvent.VK_RIGHT:
                snake.setLocation(snake.getX() + 10, snake.getY());
                break;
            case KeyEvent.VK_UP:
                snake.setLocation(snake.getX(), snake.getY() - 10);
                break;
            case KeyEvent.VK_DOWN:
                snake.setLocation(snake.getX(), snake.getY() + 10);
                break;
        }

        // Cập nhật vị trí của các phần còn lại dựa trên phần trước đó
        for (int i = 1; i < snakeParts.size(); i++) {
            snakeParts.get(i).setLocation(previousX[i - 1], previousY[i - 1]);
        }

        // Kiểm tra va chạm với thức ăn
        if (snake.getBounds().intersects(food.getBounds())) {
            growSnake(previousX[snakeParts.size() - 1], previousY[snakeParts.size() - 1]);
            placeFoodRandomly();
            score++;
            lScore.setText("Score: " + score);
            if (diff == 3) {
                delayTimer.start(); // Bắt đầu Timer để thực hiện hành động với độ trễ
            }
        }

        // Kiểm tra va chạm với các rìa (rec1, rec2, rec3, rec4)
        if (snake.getBounds().intersects(rec1.getBounds()) ||
            snake.getBounds().intersects(rec2.getBounds()) ||
            snake.getBounds().intersects(rec3.getBounds()) ||
            snake.getBounds().intersects(rec4.getBounds())) {
            check++;
            if (check >= 1) {
                timer.stop(); // Dừng Timer khi va chạm xảy ra
                JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
                frame.switchToPanelF1(); // Quay lại PanelF1 khi trò chơi kết thúc
                return;
            }
        }

        checkCollision(); // Kiểm tra va chạm với cơ thể rắn
    }
}
