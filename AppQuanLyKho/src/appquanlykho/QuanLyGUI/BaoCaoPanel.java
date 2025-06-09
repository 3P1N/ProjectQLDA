package appquanlykho.QuanLyGUI;

import appquanlykho.Components.EmailSender;
import appquanlykho.Components.ExportPDF;
import appquanlykho.Components.MyTable;
import appquanlykho.DAO.BaoCaoDAO;
import appquanlykho.DAO.ChiTietTonKhoDAO;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.DAO.SanPhamDAO;
import appquanlykho.Entity.BaoCao;
import appquanlykho.Entity.ChiTietBaoCao;
import appquanlykho.Entity.ChiTietTonKho;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.SanPham;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaoCaoPanel extends JPanel {

    private MyTable myTable;
    private JButton btnThemSanPham, btnLuuBaoCao, btnGuiBaoCao;
    private JLabel lblNgayTao;

    private JTextField txtNgayTao;
    private JComboBox<SanPham> cbSanPham;
    private List<ChiTietBaoCao> listCTBC;
    private BaoCao baoCao;
    private NguoiDung nguoiDung;

    public BaoCaoPanel(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
        baoCao = new BaoCao();
        baoCao.setIdNguoiDung(nguoiDung.getIdNguoiDung());
        setLayout(new BorderLayout());
        listCTBC = new ArrayList<>();
        initUI();
        loadSanPham();
    }

    public void initUI() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNgayTao = new JLabel("Ngày tạo:");
        txtNgayTao = new JTextField(10);
        txtNgayTao.setText(java.time.LocalDate.now().toString());
        txtNgayTao.setFocusable(false);

        btnThemSanPham = new JButton("Thêm sản phẩm");
        btnLuuBaoCao = new JButton("Tạo báo cáo");
        btnGuiBaoCao = new JButton("Gửi báo cáo");

        cbSanPham = new JComboBox<>();
        topPanel.add(lblNgayTao);
        topPanel.add(txtNgayTao);
        topPanel.add(cbSanPham);
        topPanel.add(btnThemSanPham);
        topPanel.add(btnLuuBaoCao);
        topPanel.add(btnGuiBaoCao);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = ChiTietBaoCao.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        myTable = new MyTable(columns, data);

        add(new JScrollPane(myTable), BorderLayout.CENTER);
        btnThemSanPham.addActionListener(this::themSanPhamVaoBang);
        btnLuuBaoCao.addActionListener(this::taoBaoCao);
        btnGuiBaoCao.addActionListener(this::guiBaoCao);

    }

    private void guiBaoCao(ActionEvent e) {
        EmailSender.sendFileByEmail();
    }

    private void taoBaoCao(ActionEvent e) {
        if (listCTBC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong báo cáo.");
            return;
        }
        try {
            BaoCaoDAO.TaoBaoCao(baoCao, listCTBC);
            ExportPDF.exportBaoCao(baoCao, listCTBC);

            resetForm();
        } catch (Exception ex) {
            Logger.getLogger(BaoCaoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetForm() throws Exception {
        listCTBC.clear();
        txtNgayTao.setText(java.time.LocalDate.now().toString());
        HienThiCTBC();
    }

    private void loadSanPham() {
        try {
            List<SanPham> ds = SanPhamDAO.LayDSSanPham(new SanPham());
            for (SanPham sp : ds) {
                cbSanPham.addItem(sp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm: " + e.getMessage());
        }
    }

    public void themSanPhamVaoBang(ActionEvent e) {
        SanPham sp = (SanPham) cbSanPham.getSelectedItem();
        for (ChiTietBaoCao ctbc : listCTBC) {
            if (Objects.equals(ctbc.getIdSanPham(), sp.getIdSanPham())) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã được thêm!");
                return;
            }
        }

        ChiTietTonKho cttk = new ChiTietTonKho();
        cttk.setIdKhoHang(nguoiDung.getIdKhoHang());
        cttk.setIdSanPham(sp.getIdSanPham());

        try {
            cttk = ChiTietTonKhoDAO.LayThongTinTonKho(cttk);
            ChiTietBaoCao ctbc = new ChiTietBaoCao();
            ctbc.setIdSanPham(sp.getIdSanPham());
            ctbc.setSoLuong(cttk.getSoLuong());
            listCTBC.add(ctbc);
            HienThiCTBC();
        } catch (Exception ex) {
            Logger.getLogger(BaoCaoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void HienThiCTBC() throws Exception {
        String[] columns = ChiTietBaoCao.getTableHeaders();
        Object[][] data = new Object[listCTBC.size()][columns.length];
        for (int i = 0; i < listCTBC.size(); i++) {
            data[i] = listCTBC.get(i).toTableRow();
        }

        myTable.table.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tạo báo cáo tồn kho");
            NguoiDung nd = new NguoiDung();
            nd.setIdNguoiDung(3);
            try {
                frame.setContentPane(new BaoCaoPanel(NguoiDungDAO.LayThongTinNguoiDung(nd)));
            } catch (Exception ex) {
                Logger.getLogger(BaoCaoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
