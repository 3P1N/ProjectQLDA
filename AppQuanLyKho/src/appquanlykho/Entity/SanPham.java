package appquanlykho.Entity;

import appquanlykho.DAO.LoaiSanPhamDAO;
import java.sql.SQLException;

public class SanPham {

    private Integer idSanPham;
    private String tenSanPham;
    private String donViTinh;
    private Integer idLoaiSanPham;

    public SanPham() {
    }

    public SanPham(Integer idSanPham, String tenSanPham, String donViTinh, Integer idLoaiSanPham) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.donViTinh = donViTinh;
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public static String[] getTableHeaders() {
        return new String[]{" ", "ID", "Tên sản phẩm", "Loại sản phẩm", "Đơn vị tính"};
    }

    public Object[] toTableRow() throws SQLException, ClassNotFoundException, Exception {
        LoaiSanPham lsp = new LoaiSanPham();
        lsp.setIdLoaiSanPham(idLoaiSanPham);
        lsp = LoaiSanPhamDAO.LayThongTinLoaiSanPham(lsp);
        String TenLoaiSP = lsp.getTenLoaiSanPham();
        return new Object[]{"", idSanPham, tenSanPham,
             TenLoaiSP, donViTinh};
    }

    public Integer getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Integer getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(Integer idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }

    @Override
    public String toString() {
        return tenSanPham;
    }
}
