package B9D53.qlcf.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import B9D53.qlcf.dao.UserDao;
import B9D53.qlcf.entity.User;
import B9D53.qlcf.view.LoginView;
import B9D53.qlcf.view.CafeView;

public class LoginController {
    private UserDao userDao;
    private LoginView loginView;
    private CafeView cafeView;
    
    public LoginController(LoginView view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.addLoginListener(new LoginListener());
        view.addEnterKeyListener(new EnterKeyListener());
    }
    
    public void showLoginView() {
        loginView.setVisible(true);
    }
    
    /**
     * Lớp LoginListener 
     * chứa cài đặt cho sự kiện click button "Login"
     * 
     * @author viettuts.vn
     */
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                // nếu đăng nhập thành công, mở màn hình quản lý cafe
                cafeView = new CafeView();
                CafeController cafeController = new CafeController(cafeView);
                cafeController.showCafeView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username or password is incorrect");
            }
        }
    }
    class EnterKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // Thực hiện đăng nhập khi nhấn phím "Enter"
                User user = loginView.getUser();
                if (userDao.checkUser(user)) {
                    // nếu đăng nhập thành công, mở màn hình quản lý cafe
                    cafeView = new CafeView();
                    CafeController cafeController = new CafeController(cafeView);
                    cafeController.showCafeView();
                    loginView.setVisible(false);
                } else {
                    loginView.showMessage("username or password is incorrect");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}
    }
}