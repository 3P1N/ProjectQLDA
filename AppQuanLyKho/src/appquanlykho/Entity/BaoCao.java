package appquanlykho.Entity;

import java.util.Date;

public class BaoCao {
    private Integer idBaoCao;
    private Integer idNguoiDung;
    private String loaiBaoCao; // ví dụ: "Tuan", "Thang", "Nam"
    private Date ngayTao;

    // Constructors
    public BaoCao() {}

    public BaoCao(Integer idBaoCao, Integer idNguoiDung, String loaiBaoCao, Date ngayTao) {
        this.idBaoCao = idBaoCao;
        this.idNguoiDung = idNguoiDung;
        this.loaiBaoCao = loaiBaoCao;
        this.ngayTao = ngayTao;
    }

    // Getters and Setters
    public Integer getIdBaoCao() {
        return idBaoCao;
    }

    public void setIdBaoCao(Integer idBaoCao) {
        this.idBaoCao = idBaoCao;
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getLoaiBaoCao() {
        return loaiBaoCao;
    }

    public void setLoaiBaoCao(String loaiBaoCao) {
        this.loaiBaoCao = loaiBaoCao;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
}
