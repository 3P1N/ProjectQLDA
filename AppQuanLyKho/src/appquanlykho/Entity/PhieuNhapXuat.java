package appquanlykho.Entity;

import java.sql.Date;
import java.sql.SQLException;

public class PhieuNhapXuat {
    private Integer idPhieuNhapXuat;
    private Integer idNguoiDung;
    private Date ngayTao;
    private String trangThai;
    private String loaiPhieu; // "Nhap" hoặc "Xuat"

    public PhieuNhapXuat() {
    }
    
    public static String[] getTableHeaders() {
        return new String[]{" ", "ID",  "Ngày tạo", "Trạng thái", "Loại phiếu"};
    }

    public Object[] toTableRow() throws SQLException, ClassNotFoundException, Exception {
        
        String TenLoaiPhieu;
        if(loaiPhieu.equals("Nhap")){
            TenLoaiPhieu = "Phiếu nhập";
        }
        else{
            TenLoaiPhieu = "Phiễu xuất";
        }
        
        return new Object[]{"", idPhieuNhapXuat, ngayTao, trangThai,
             TenLoaiPhieu};
    }


    public Integer getIdPhieuNhapXuat() {
        return idPhieuNhapXuat;
    }

    public void setIdPhieuNhapXuat(Integer idPhieuNhapXuat) {
        this.idPhieuNhapXuat = idPhieuNhapXuat;
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaiPhieu() {
        return loaiPhieu;
    }

    public void setLoaiPhieu(String loaiPhieu) {
        this.loaiPhieu = loaiPhieu;
    }
}
