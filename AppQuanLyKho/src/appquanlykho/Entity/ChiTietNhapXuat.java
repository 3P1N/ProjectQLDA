package appquanlykho.Entity;

import appquanlykho.DAO.SanPhamDAO;
import java.sql.SQLException;

public class ChiTietNhapXuat {
    private Integer idPhieuNhapXuat;
    private Integer idSanPham;
    private Integer soLuong;

    public ChiTietNhapXuat() {
    }

    public static String[] getTableHeaders() {
        return new String[]{" ","ID Sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn vị tính"};
    }

    public Object[] toTableRow() throws SQLException, ClassNotFoundException, Exception {
        SanPham sp = new SanPham();
        sp.setIdSanPham(idSanPham);
        sp = SanPhamDAO.LayThongTinSanPham(sp);
        String TenSP = sp.getTenSanPham();
        return new Object[]{"", idSanPham, TenSP,
             soLuong, sp.getDonViTinh()};
    }
    
    public Integer getIdPhieuNhapXuat() {
        return idPhieuNhapXuat;
    }

    public void setIdPhieuNhapXuat(Integer idPhieuNhapXuat) {
        this.idPhieuNhapXuat = idPhieuNhapXuat;
    }

    public Integer getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
