package appquanlykho.QuanLyGUI;

import appquanlykho.NhanVienGUI.*;
import appquanlykho.Components.MyTable;
import appquanlykho.DAO.ChiTietNhapXuatDAO;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.DAO.PhieuNhapXuatDAO;
import appquanlykho.Entity.SanPham;
import appquanlykho.DAO.SanPhamDAO;
import appquanlykho.Entity.ChiTietNhapXuat;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.PhieuNhapXuat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietPhieuNhapXuatFrame extends JFrame {

    private MyTable table;
    private JButton btnHuy;
    private List<ChiTietNhapXuat> listCTNX;
 
    private PhieuNhapXuat phieuNhapXuat;

    public ChiTietPhieuNhapXuatFrame(PhieuNhapXuat phieuNhapXuat) {
        
        ChiTietNhapXuat ctnx = new ChiTietNhapXuat();
        this.phieuNhapXuat = phieuNhapXuat;
        ctnx.setIdPhieuNhapXuat(phieuNhapXuat.getIdPhieuNhapXuat());
        try {
            listCTNX = ChiTietNhapXuatDAO.LayDSChiTietNhapXuat(ctnx);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChiTietPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (phieuNhapXuat.getLoaiPhieu().equals("Nhap")) {
            setTitle("Piếu nhập");
            
        } else {
            setTitle("Phiếu xuất");    

        }
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
       
        listCTNX = new ArrayList<>();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
       

        

        // Table
//        tblModel = new DefaultTableModel(new String[]{"ID", "Tên sản phẩm", "Số lượng", "Đơn vị"}, 0);
//        tblChiTiet = new JTable(tblModel);
        String[] columns = ChiTietNhapXuat.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        table = new MyTable(columns, data);
        JScrollPane scrollPane = new JScrollPane(table);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnHuy = new JButton("Hủy");
        
        bottomPanel.add(btnHuy);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Sự kiện
        
        btnHuy.addActionListener(e -> dispose());
        
        try {
            HienThiCTNX();
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void HienThiCTNX() throws Exception {

        String[] columns = NguoiDung.getTableHeaders();
        Object[][] data = new Object[listCTNX.size()][columns.length];

        for (int i = 0; i < listCTNX.size(); i++) {
            data[i] = listCTNX.get(i).toTableRow();
        }

        table.setTableData(data);
    }

    

    public static void main(String[] args) {
        
        try {
           
            PhieuNhapXuat pnx = new PhieuNhapXuat();
            pnx.setIdPhieuNhapXuat(2);
            SwingUtilities.invokeLater(() -> {
                try {
                    new ChiTietPhieuNhapXuatFrame(PhieuNhapXuatDAO.LayThongTinPhieuNhapXuat(pnx)).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(ChiTietPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ChiTietPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
