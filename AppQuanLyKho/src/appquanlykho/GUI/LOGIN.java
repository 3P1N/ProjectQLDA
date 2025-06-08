package appquanlykho.GUI;

import appquanlykho.AdminGUI.AdminGUI;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.Entity.NguoiDung;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author ASUS
 */
public class LOGIN extends JFrame {

    private JTextField userField = new JTextField();
    private JPasswordField passField = new JPasswordField();
    private NguoiDung nguoiDung = null;

    public LOGIN() {
        setTitle("Đăng nhập - Công ty TNHH ABC");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 600);
        add(mainPanel);

        JLabel background = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/warehouse_11zon.jpg")));// Thay bằng hình bạn muốn
        URL imageUrl = getClass().getResource("/images/warehouse_11zon.jpg");
        System.out.println("Image URL: " + imageUrl);

        background.setBounds(0, 0, 900, 600);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(275, 100, 350, 350);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JLabel logo = new JLabel("Đăng nhập");
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(new Color(0, 102, 204));
        logo.setBounds(115, 45, 200, 40);
        loginPanel.add(logo);

        userField.setBounds(30, 115, 290, 45);
        userField.setBorder(BorderFactory.createTitledBorder("Tên đăng nhập"));
        loginPanel.add(userField);

        passField.setBounds(30, 165, 290, 40);
        passField.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        loginPanel.add(passField);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(30, 240, 290, 40);
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(e -> {
            try {
                yeuCauXacThuc();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        mainPanel.add(loginPanel);
        mainPanel.add(background);

    }

    public void yeuCauXacThuc() throws SQLException, ClassNotFoundException, Exception {
        String username = userField.getText().trim();
        String pass = new String(passField.getPassword());

        NguoiDung nd = new NguoiDung();
        nd.setTenDangNhap(username);
        nd.setMatKhau(pass);

        nguoiDung = NguoiDungDAO.LayThongTinNguoiDung(nd);

        if (nguoiDung == null) {

            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else {
            if(nguoiDung.getVaiTro().equals("Admin")){
                new AdminGUI(nguoiDung).setVisible(true);
            }
            this.dispose();
        }

    }

    public String getuser() {
        return userField.getText().trim();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            LOGIN frame = new LOGIN();
            frame.setVisible(true);
        });
    }
}
