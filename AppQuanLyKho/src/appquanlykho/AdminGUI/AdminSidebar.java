package appquanlykho.AdminGUI;

import appquanlykho.Components.Sidebar;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.Entity.NguoiDung;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author HP
 */
public class AdminSidebar extends Sidebar {

    private static final List<String> items = Arrays.asList("Trang chủ", "Quản lý tài khoản", "Thông tin cá nhân", "Đăng xuất");
    private static final List<String> icons = Arrays.asList("home.png", "customer.png", "customer.png", "logout.png");

    public AdminSidebar(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException {
        super(items, icons, nguoiDung);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test MenuBar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700); // Kích thước của JFrame
            frame.setLocationRelativeTo(null);

            AdminSidebar sidebar = null;

            NguoiDung nd = new NguoiDung();
            nd.setIdNguoiDung(1);
            try {
                nd = NguoiDungDAO.LayThongTinNguoiDung(nd);
                sidebar = new AdminSidebar(nd);
            } catch (SQLException ex) {
                Logger.getLogger(AdminSidebar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminSidebar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(AdminSidebar.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setLayout(new BorderLayout());
            frame.add(sidebar, BorderLayout.WEST);  // Menu sẽ được đặt ở bên trái

            frame.setVisible(true);
        });
    }

}
