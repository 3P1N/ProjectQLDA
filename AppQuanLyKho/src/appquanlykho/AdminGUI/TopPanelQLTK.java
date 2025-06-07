/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appquanlykho.AdminGUI;


import appquanlykho.Entity.NguoiDung;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelQLTK extends JPanel {

    private JButton addButton = new JButton("Thêm mới");
    private JButton filterButton = new JButton("Lọc");
    private JButton updateButton = new JButton("Sửa");
    private JButton deleteButton = new JButton("Xóa");
    private JButton refreshButton = new JButton("Refresh");

    private final JTextField idField = new JTextField("");
    private final JComboBox<String> roleComboBox;
    private final JTextField nameField = new JTextField("");


    public TopPanelQLTK() throws SQLException, ClassNotFoundException {

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        idField.setPreferredSize(new Dimension(80, 40));
        idField.setBorder(BorderFactory.createTitledBorder("ID"));
        add(idField);

        // ComboBox - Trạng thái
        
        String[] dsVaiTro = new String[]{"Tất cả", "Admin", "Quản lý", "Nhân viên nhập", "Nhân viên xuất"};
        

        roleComboBox = new JComboBox<>(dsVaiTro);
        roleComboBox.setPreferredSize(new Dimension(130, 40));
        roleComboBox.setBorder(BorderFactory.createTitledBorder("Vai trò"));
        add(roleComboBox);

        // TextField - Khách hàng
        nameField.setPreferredSize(new Dimension(120, 40));
        nameField.setBorder(BorderFactory.createTitledBorder("Họ tên"));
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

        deleteButton.setPreferredSize(new Dimension(120, 30));
        deleteButton.setBackground(new Color(200, 0, 0));
        deleteButton.setForeground(Color.white);
       
        add(deleteButton);

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

    public NguoiDung getNguoiDung() {
        NguoiDung nguoiDung = new NguoiDung();
        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            nguoiDung.setIdNguoiDung(Integer.valueOf(idText));
        } else {
            nguoiDung.setIdNguoiDung(null);
        }

        Object selected = roleComboBox.getSelectedItem();
        if(selected.equals("Tất cả")){
            nguoiDung.setVaiTro(null);
        }else{
            nguoiDung.setVaiTro(selected.toString());
        }
        

        String name = nameField.getText().trim();
        nguoiDung.setHoTen(name.isEmpty() ? null : name);
        return nguoiDung;
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
                frame.add(new TopPanelQLTK());
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelQLTK.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelQLTK.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setVisible(true);
        });
    }
}
