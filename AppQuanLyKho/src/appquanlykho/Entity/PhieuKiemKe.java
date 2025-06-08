package appquanlykho.Entity;

import java.sql.Date;

public class PhieuKiemKe {
    private int idPhieuKiemKe;
    private int idNguoiDung;
    private Date ngayTao;

    public PhieuKiemKe() {}

    public PhieuKiemKe(int idPhieuKiemKe, int idNguoiDung, Date ngayTao) {
        this.idPhieuKiemKe = idPhieuKiemKe;
        this.idNguoiDung = idNguoiDung;
        this.ngayTao = ngayTao;
    }

    public int getIdPhieuKiemKe() {
        return idPhieuKiemKe;
    }

    public void setIdPhieuKiemKe(int idPhieuKiemKe) {
        this.idPhieuKiemKe = idPhieuKiemKe;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(int idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
}
