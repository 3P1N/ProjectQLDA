package appquanlykho.Entity;

public class ChiTietTonKho {
    private Integer idKhoHang;
    private Integer idSanPham;
    private int soLuong;

    public ChiTietTonKho() {
    }

    public ChiTietTonKho(Integer idKhoHang, Integer idSanPham, int soLuong) {
        this.idKhoHang = idKhoHang;
        this.idSanPham = idSanPham;
        this.soLuong = soLuong;
    }

    public Integer getIdKhoHang() {
        return idKhoHang;
    }

    public void setIdKhoHang(Integer idKhoHang) {
        this.idKhoHang = idKhoHang;
    }

    public Integer getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "ChiTietTonKho{" +
                "idKhoHang=" + idKhoHang +
                ", idSanPham=" + idSanPham +
                ", soLuong=" + soLuong +
                '}';
    }
}
