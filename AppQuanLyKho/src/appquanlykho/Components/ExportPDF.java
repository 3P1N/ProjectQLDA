package appquanlykho.Components;

import appquanlykho.Entity.BaoCao;
import appquanlykho.Entity.ChiTietBaoCao;
import appquanlykho.Entity.SanPham;
import appquanlykho.DAO.SanPhamDAO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportPDF {

    public static void exportBaoCao(BaoCao baoCao, List<ChiTietBaoCao> listCTBC) throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu báo cáo PDF");
        fileChooser.setSelectedFile(new File("bao_cao_ton_kho.pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // Người dùng bấm Cancel
        }

        File fileToSave = fileChooser.getSelectedFile();
        String outputPath = fileToSave.getAbsolutePath();

        // Đảm bảo đuôi .pdf
        if (!outputPath.toLowerCase().endsWith(".pdf")) {
            outputPath += ".pdf";
        }

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // Tiêu đề
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("BÁO CÁO TỒN KHO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Thông tin báo cáo
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
        document.add(new Paragraph("ID Báo cáo: " + baoCao.getIdBaoCao(), normalFont));
        document.add(new Paragraph("Người lập: " + baoCao.getIdNguoiDung(), normalFont));
        document.add(new Paragraph("Ngày tạo: " + baoCao.getNgayTao(), normalFont));
        document.add(new Paragraph("Loại báo cáo: " + baoCao.getLoaiBaoCao(), normalFont));
        document.add(Chunk.NEWLINE);

        // Bảng dữ liệu
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{1.5f, 5f, 2f});

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        table.addCell(new PdfPCell(new Phrase("Mã SP", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Số lượng", headerFont)));

        for (ChiTietBaoCao ct : listCTBC) {
            SanPham sp = new SanPham();
            sp.setIdSanPham(ct.getIdSanPham());
            SanPham fullSP = SanPhamDAO.LayThongTinSanPham(sp);

            table.addCell(String.valueOf(ct.getIdSanPham()));
            table.addCell(fullSP.getTenSanPham());
            table.addCell(String.valueOf(ct.getSoLuong()));
        }

        document.add(table);
        document.close();

        JOptionPane.showMessageDialog(null, "Xuất PDF thành công: " + outputPath);
    }
}
