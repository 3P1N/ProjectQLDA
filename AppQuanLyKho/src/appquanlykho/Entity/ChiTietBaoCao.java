package appquanlykho.Entity;

import appquanlykho.DAO.SanPhamDAO;
import java.sql.SQLException;

public class ChiTietBaoCao {
    private Integer idBaoCao;
    private Integer idSanPham;
    private Integer soLuong;

    // Constructors
    public ChiTietBaoCao() {}

    public ChiTietBaoCao(Integer idBaoCao, Integer idSanPham, Integer soLuong) {
        this.idBaoCao = idBaoCao;
        this.idSanPham = idSanPham;
        this.soLuong = soLuong;
    }

    public static String[] getTableHeaders() {
        return new String[]{" ", "ID sản phẩm", "Tên sản phẩm", "Số lượng"};
    }

    public Object[] toTableRow() throws  ClassNotFoundException, Exception {
        SanPham sp = new SanPham();
        sp.setIdLoaiSanPham(idSanPham);
        sp = SanPhamDAO.LayThongTinSanPham(sp);
        
        return new Object[]{"", idSanPham,
             sp.getTenSanPham(), soLuong};
    }
    
    // Getters and Setters
    public Integer getIdBaoCao() {
        return idBaoCao;
    }

    public void setIdBaoCao(Integer idBaoCao) {
        this.idBaoCao = idBaoCao;
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
    static public void main(String[] args){
        
    }
}
