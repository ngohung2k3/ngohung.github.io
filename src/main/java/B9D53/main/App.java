package B9D53.main;

import java.awt.EventQueue;

import B9D53.qlcf.controller.LoginController;
import B9D53.qlcf.view.LoginView;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginView view = new LoginView();
            LoginController controller = new LoginController(view);
            // hiển thị màn hình login
            controller.showLoginView();
        });
    }
}