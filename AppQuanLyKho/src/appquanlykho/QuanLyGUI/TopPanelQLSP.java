/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appquanlykho.QuanLyGUI;

import appquanlykho.AdminGUI.*;
import appquanlykho.DAO.KhoHangDAO;
import appquanlykho.DAO.LoaiSanPhamDAO;
import appquanlykho.Entity.KhoHang;
import appquanlykho.Entity.LoaiSanPham;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.SanPham;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelQLSP extends JPanel {

    private JButton addButton = new JButton("Thêm mới");
    private JButton filterButton = new JButton("Lọc");
    private JButton updateButton = new JButton("Sửa");
    private JButton deleteButton = new JButton("Xóa");
    private JButton refreshButton = new JButton("Refresh");

    private final JTextField idField = new JTextField("");
    private final JComboBox<LoaiSanPham> cbLoaiSP;
    private final JTextField nameField = new JTextField("");

    public TopPanelQLSP() throws SQLException, ClassNotFoundException {

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        idField.setPreferredSize(new Dimension(80, 40));
        idField.setBorder(BorderFactory.createTitledBorder("ID"));
        add(idField);

        // ComboBox - Trạng thái
        cbLoaiSP = new JComboBox<>();
        java.util.List<LoaiSanPham> dsLoaiSP = null;
        try {
            dsLoaiSP = LoaiSanPhamDAO.LayDSLoaiSanPham(new LoaiSanPham());
        } catch (Exception ex) {
            Logger.getLogger(TopPanelQLSP.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbLoaiSP.addItem(new LoaiSanPham(null, "Tất cả"));

        for (LoaiSanPham loaisp : dsLoaiSP) {
            cbLoaiSP.addItem(loaisp);
        }

        cbLoaiSP.setPreferredSize(new Dimension(130, 40));
        cbLoaiSP.setBorder(BorderFactory.createTitledBorder("Loại sản phẩm"));
        add(cbLoaiSP);

        // TextField - Khách hàng
        nameField.setPreferredSize(new Dimension(120, 40));
        nameField.setBorder(BorderFactory.createTitledBorder("Tên sản phẩm"));
        add(nameField);

        // Button - Lọc (màu xanh đậm)
        filterButton.setPreferredSize(new Dimension(60, 30));
        filterButton.setBackground(new Color(0, 136, 153));
        filterButton.setForeground(Color.WHITE);

        add(filterButton);

        // Button - Thêm mới (màu xanh lá)
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);

        add(addButton);


        updateButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setBackground(new Color(0, 153, 76));
        updateButton.setForeground(Color.WHITE);
        add(updateButton);

        refreshButton.setPreferredSize(new Dimension(150, 30));
        refreshButton.setBackground(new Color(0, 136, 153));
        refreshButton.setForeground(Color.WHITE);
        add(refreshButton);

    }

    public JButton getRefreshButton() {
        return this.refreshButton;
    }

    public JButton getaddButton() {
        return this.addButton;
    }

    public JButton getupdateButton() {
        return this.updateButton;
    }

    public JButton getfilterButton() {
        return this.filterButton;
    }

    public JButton getDeleteButton() {
        return this.deleteButton;
    }

    public SanPham getSanPham() {
        SanPham sanPham = new SanPham();
        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            sanPham.setIdSanPham(Integer.valueOf(idText));
        } else {
            sanPham.setIdSanPham(null);
        }

        LoaiSanPham loai = (LoaiSanPham) cbLoaiSP.getSelectedItem();
        if (loai.getTenLoaiSanPham().equals("Tất cả")) {
            sanPham.setIdLoaiSanPham(null);
        } else {
            sanPham.setIdLoaiSanPham(loai.getIdLoaiSanPham());
        }

        String name = nameField.getText().trim();
        sanPham.setTenSanPham(name.isEmpty() ? null : name);
        return sanPham;
    }

    static public void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lọc đơn hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 120);
            frame.setLocationRelativeTo(null);

            try {
                frame.add(new TopPanelQLSP());
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelQLSP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelQLSP.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setVisible(true);
        });
    }
}
