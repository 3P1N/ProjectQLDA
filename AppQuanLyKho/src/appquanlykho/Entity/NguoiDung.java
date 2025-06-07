
package appquanlykho.Entity;

import appquanlykho.DAO.KhoHangDAO;
import java.sql.SQLException;


public class NguoiDung {
    private Integer idNguoiDung;
    private String tenDangNhap;
    private String matKhau;
    private String hoTen;
    private int trangThaiXoa;
    private String vaiTro;
    private Integer idKhoHang; // Cho phép null nếu không bắt buộc

    public NguoiDung() {
    }

    public NguoiDung(int idNguoiDung, String tenDangNhap, String matKhau, String hoTen, int trangThaiXoa, String vaiTro, Integer idKhoHang) {
        this.idNguoiDung = idNguoiDung;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.trangThaiXoa = trangThaiXoa;
        this.vaiTro = vaiTro;
        this.idKhoHang = idKhoHang;
    }
    
     public static String[] getTableHeaders() {
        return new String[]{" ", "ID", "Tên đăng nhập",  "Mật khẩu" , "Họ tên", "Vai trò", "Kho làm việc", "Trạng thái xóa"};
    }
     
    public Object[] toTableRow() throws SQLException, ClassNotFoundException, Exception {
        KhoHang kho = new KhoHang();
        kho.setIdKhoHang(idKhoHang);
        kho = KhoHangDAO.LayThongTinKhoHang(kho);
        String TenKho = kho.getTenKhoHang();
        return new Object[]{"",idNguoiDung, tenDangNhap
                , matKhau, hoTen, vaiTro, TenKho, trangThaiXoa};
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Integer getIdKhoHang() {
        return idKhoHang;
    }

    public void setIdKhoHang(Integer idKhoHang) {
        this.idKhoHang = idKhoHang;
    }
}

