
package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.ChiTietKiemKe;
import appquanlykho.Entity.PhieuKiemKe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PhieuKiemKeDAO {
    public static void TaoPhieuKiemKe(PhieuKiemKe phieuKiemKe, List<ChiTietKiemKe> list) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionUtils.getMyConnection();
            conn.setAutoCommit(false);

            // 1. Lấy ID tiếp theo từ Sequence
            stmt = conn.prepareStatement("SELECT SEQ_PhieuKiemKe.NEXTVAL FROM dual");
            rs = stmt.executeQuery();
            int newId = -1;
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            phieuKiemKe.setIdPhieuKiemKe(newId);

            // 2. Insert Phiếu nhập xuất
            String sql = "INSERT INTO PhieuKiemKe(ID_PhieuKiemKe, ID_NguoiDung) "
                    + "VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newId);
            stmt.setInt(2, phieuKiemKe.getIdNguoiDung());
            
            stmt.executeUpdate();

            // 3. Gán ID vào từng chi tiết và insert
            for (ChiTietKiemKe ctkk : list) {
                ctkk.setIdPhieuKiemKe(newId);
            }
            ChiTietKiemKeDAO.ThemDSChiTietKiemKe(conn, list);

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Tạo phiếu thất bại", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    
}
