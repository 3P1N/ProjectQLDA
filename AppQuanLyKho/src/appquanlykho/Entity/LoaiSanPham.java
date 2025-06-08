package appquanlykho.Entity;

public class LoaiSanPham {
    private Integer idLoaiSanPham;
    private String tenLoaiSanPham;

    public LoaiSanPham() {
    }

    public LoaiSanPham(Integer idLoaiSanPham, String tenLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public Integer getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(Integer idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    @Override
    public String toString() {
        return tenLoaiSanPham;
    }
}
