package B9D53.qlcf.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

import B9D53.qlcf.entity.User;

public class LoginView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel userNameLabel;
    private JLabel passwordlabel;
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JButton loginBtn;

    public LoginView() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        Font buttonFont = new Font("Arial", Font.BOLD, 13);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel titleLabel = new JLabel("QUẢN LÝ QUÁN CAFE");
        userNameLabel = new JLabel("Username");
        passwordlabel = new JLabel("Password");
        userNameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginBtn = new JButton();
        loginBtn.setFont(buttonFont);

        // Thiết lập font và màu sắc cho titleLabel
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setForeground(Color.DARK_GRAY); 

        // Thiết lập font cho userNameLabel
        userNameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        // Thiết lập màu cho userNameLabel
        userNameLabel.setForeground(Color.DARK_GRAY);

        // Thiết lập font cho passwordLabel
        passwordlabel.setFont(new Font("Serif", Font.BOLD, 18));
        // Thiết lập màu cho passwordLabel
        passwordlabel.setForeground(Color.DARK_GRAY);
        loginBtn.setText("Login");
        loginBtn.addActionListener(this);

        // Tạo spring layout
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel() {
            // Override phương thức paintComponent để vẽ hình ảnh nền
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load hình ảnh nền
                Image background = new ImageIcon("coffee2.jpg").getImage();
                // Image background = new ImageIcon(getClass().getResource("coffee2.jpeg")).getImage();
                
                // Vẽ hình ảnh nền từ góc trên bên trái của JPanel
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Đặt kích thước cho JPanel
        panel.add(titleLabel);
        panel.setPreferredSize(new Dimension(650, 500));
        panel.setLayout(layout);
        panel.add(userNameLabel);
        panel.add(passwordlabel);
        panel.add(userNameField);
        panel.add(passwordField);
        panel.add(loginBtn);

        // Thêm panel tới JFrame
        this.add(panel);
        this.pack();
        // Cài đặt các thuộc tính cho JFrame
        this.setTitle("Đăng nhập");
        this.setSize(650, 500);
        this.setResizable(false);
        // Đặt cửa sổ ở giữa màn hình
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Cài đặt vị trí các thành phần trên màn hình login
        // Đặt vị trí cho titleLabel
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 100, SpringLayout.NORTH, panel);

        // Đặt ràng buộc cho username label và text field
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, userNameField, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, userNameField, 200, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, userNameLabel, -10, SpringLayout.WEST, userNameField);
        layout.putConstraint(SpringLayout.NORTH, userNameLabel, 0, SpringLayout.NORTH, userNameField);

        // Đặt ràng buộc cho password label và text field
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordField, 0, SpringLayout.HORIZONTAL_CENTER, userNameField);
        layout.putConstraint(SpringLayout.NORTH, passwordField, 20, SpringLayout.SOUTH, userNameField);
        layout.putConstraint(SpringLayout.EAST, passwordlabel, -10, SpringLayout.WEST, passwordField);
        layout.putConstraint(SpringLayout.NORTH, passwordlabel, 0, SpringLayout.NORTH, passwordField);

        // Đặt ràng buộc cho login button
        layout.putConstraint(SpringLayout.WEST, loginBtn, 0, SpringLayout.WEST, passwordField);
        layout.putConstraint(SpringLayout.NORTH, loginBtn, 25, SpringLayout.SOUTH, passwordField);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public User getUser() {
        return new User(userNameField.getText(),
                String.copyValueOf(passwordField.getPassword()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void addLoginListener(ActionListener listener) {
        loginBtn.addActionListener(listener);
    }

    public void addEnterKeyListener(KeyListener listener) {
        userNameField.addKeyListener(listener);
        passwordField.addKeyListener(listener);
    }
}
