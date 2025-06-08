package appquanlykho.Entity;

import appquanlykho.DAO.SanPhamDAO;

public class ChiTietKiemKe {
    private Integer idPhieuKiemKe;
    private Integer idSanPham;
    private int soLuongThucTe;
    private int soLuongHeThong;
    private int chenhLech;

    public ChiTietKiemKe() {}

    public ChiTietKiemKe(Integer idPhieuKiemKe, Integer idSanPham, int soLuongThucTe, int soLuongHeThong) {
        this.idPhieuKiemKe = idPhieuKiemKe;
        this.idSanPham = idSanPham;
        this.soLuongThucTe = soLuongThucTe;
        this.soLuongHeThong = soLuongHeThong;
        this.chenhLech = soLuongThucTe - soLuongHeThong;
    }

    public static String[] getTableHeaders() {
        return new String[]{" ", "Tên sản phẩm", "Số lượng hệ thống", "Số lượng thực tế", "Chênh lệch"};
    }

    public Object[] toTableRow() throws ClassNotFoundException, Exception {
        SanPham sp = new SanPham();
        sp.setIdSanPham(idSanPham);
        sp = SanPhamDAO.LayThongTinSanPham(sp);
        return new Object[]{"", sp.getTenSanPham(), soLuongHeThong,
             soLuongThucTe, chenhLech};
    }

    
    public Integer getIdPhieuKiemKe() {
        return idPhieuKiemKe;
    }

    public void setIdPhieuKiemKe(Integer idPhieuKiemKe) {
        this.idPhieuKiemKe = idPhieuKiemKe;
    }

    public Integer getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getSoLuongThucTe() {
        return soLuongThucTe;
    }

    public void setSoLuongThucTe(int soLuongThucTe) {
        this.soLuongThucTe = soLuongThucTe;
        this.chenhLech = this.soLuongThucTe - this.soLuongHeThong;
    }

    public int getSoLuongHeThong() {
        return soLuongHeThong;
    }

    public void setSoLuongHeThong(int soLuongHeThong) {
        this.soLuongHeThong = soLuongHeThong;
        this.chenhLech = this.soLuongThucTe - this.soLuongHeThong;
    }

    public int getChenhLech() {
        return chenhLech;
    }

    public void setChenhLech(int chenhLech) {
        this.chenhLech = chenhLech;
    }
}
