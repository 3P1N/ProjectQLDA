package appquanlykho.Entity;

public class KhoHang {
    private Integer idKhoHang;
    private String tenKhoHang;
    private Integer soLuongToiDa;
    private Integer soLuongTonKho;

    public KhoHang() {
    }

    public KhoHang(int idKhoHang, String tenKhoHang, Integer soLuongToiDa, Integer soLuongTonKho) {
        this.idKhoHang = idKhoHang;
        this.tenKhoHang = tenKhoHang;
        this.soLuongToiDa = soLuongToiDa;
        this.soLuongTonKho = soLuongTonKho;
    }

    public Integer getIdKhoHang() {
        return idKhoHang;
    }

    public void setIdKhoHang(Integer idKhoHang) {
        this.idKhoHang = idKhoHang;
    }

    public String getTenKhoHang() {
        return tenKhoHang;
    }

    public void setTenKhoHang(String tenKhoHang) {
        this.tenKhoHang = tenKhoHang;
    }

    public Integer getSoLuongToiDa() {
        return soLuongToiDa;
    }

    public void setSoLuongToiDa(Integer soLuongToiDa) {
        this.soLuongToiDa = soLuongToiDa;
    }

    public Integer getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public void setSoLuongTonKho(Integer soLuongTonKho) {
        this.soLuongTonKho = soLuongTonKho;
    }
}
