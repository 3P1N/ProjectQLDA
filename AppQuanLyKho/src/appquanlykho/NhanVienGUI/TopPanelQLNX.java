/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appquanlykho.NhanVienGUI;



import appquanlykho.Entity.LoaiSanPham;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.PhieuNhapXuat;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelQLNX extends JPanel {

    private JButton addButton = new JButton("Thêm mới");
    private JButton filterButton = new JButton("Lọc");
    private JButton refreshButton = new JButton("Refresh");

    private final JTextField idField = new JTextField("");
    private final JComboBox<String> cbTrangThai;
    private final JTextField nameField = new JTextField("");
    private NguoiDung nguoiDung;
    
    public TopPanelQLNX(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException {
        this.nguoiDung = nguoiDung;
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        idField.setPreferredSize(new Dimension(80, 40));
        idField.setBorder(BorderFactory.createTitledBorder("ID"));
        add(idField);

        // ComboBox - Trạng thái
        String[] dsTrangThai = new String[]{"Tất cả", "Chờ duyệt", "Đã duyệt", "Đã hủy"};
        cbTrangThai = new JComboBox<>(dsTrangThai);
        
        cbTrangThai.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        add(cbTrangThai);

       

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



    public JButton getfilterButton() {
        return this.filterButton;
    }

    

    public PhieuNhapXuat getPhieuNhapXuat() {
        PhieuNhapXuat phieuNhapXuat = new PhieuNhapXuat();
        
        phieuNhapXuat.setIdNguoiDung(nguoiDung.getIdNguoiDung());
        
        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            phieuNhapXuat.setIdPhieuNhapXuat(Integer.valueOf(idText));
        } else {
            phieuNhapXuat.setIdPhieuNhapXuat(null);
        }

        String trangThai = (String) cbTrangThai.getSelectedItem();
        if(!trangThai.equals("Tất cả")){
            phieuNhapXuat.setTrangThai(trangThai);
        }

        return phieuNhapXuat;
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
            NguoiDung nd = new NguoiDung();
            try {
                frame.add(new TopPanelQLNX(nd));
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelQLNX.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelQLNX.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setVisible(true);
        });
    }
}
