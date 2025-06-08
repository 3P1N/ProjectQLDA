/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appquanlykho.AdminGUI;

import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.GUI.LOGIN;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AdminGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private NguoiDung nguoiDung;

    public AdminGUI(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException, Exception {

        this.nguoiDung = nguoiDung;

        setTitle("3P1N - Nhân viên kho");
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo menu
        AdminSidebar sidebar = new AdminSidebar(nguoiDung);
        add(sidebar, BorderLayout.WEST);

        // Panel trung tâm hiển thị nội dung
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Thêm các trang nội dung
        contentPanel.add(new QuanLyTaiKhoanPanel(), "Quản lý tài khoản");

        add(contentPanel, BorderLayout.CENTER);

        sidebar.addMenuClickListener((selectedName) -> {
            if (selectedName.equals("Đăng xuất")) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc chắn muốn đăng xuất không?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();

                    SwingUtilities.invokeLater(() -> new LOGIN().setVisible(true));
                }
            } else {
                cardLayout.show(contentPanel, selectedName);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            try {
                
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setIdNguoiDung(1);
                nguoiDung = NguoiDungDAO.LayThongTinNguoiDung(nguoiDung);
                new AdminGUI(nguoiDung).setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

    }
}
