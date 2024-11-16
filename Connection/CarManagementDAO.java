package Connection;

import Model.Cars;
import Connection.connect;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CarManagementDAO {

    private List<Cars> c;

    public CarManagementDAO() {
        this.c = new ArrayList<Cars>();
    }

    public List<Cars> getCars() {
        return c;
    }

    public void getCar(List<Cars> c) {
        this.c = c;
    }

    public void setCarsData() {
        String sql = " select * from VEHICLE";
        try {
            Connection conn = connect.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                int cid = rs.getInt(1);
                String res_no = rs.getString(2);
                String model = rs.getString(3);
                String status = rs.getString(6);
                String type = rs.getString(5);
                int driver_id = rs.getInt(7);
                int price = rs.getInt(8);

                c.add(new Cars(cid, res_no, model, status, type, driver_id, price));
            }
            for (Cars cars : c) {
                System.out.println(cars);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean deleteCarById(String carId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String url = "jdbc:mysql://localhost:3306/rent_car";
            String user = "root";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM VEHICLE WHERE car_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, carId);  


            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Nếu có lỗi xảy ra, trả về false
        } finally {
            try {
                // Đảm bảo đóng kết nối và PreparedStatement sau khi sử dụng
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateCar(int carId, String regNo, String model, String type, String status, int driverId, int price) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Cấu hình kết nối cơ sở dữ liệu
            String url = "jdbc:mysql://localhost:3306/rent_car";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);

            // Câu lệnh SQL để cập nhật dữ liệu
            String sql = "{CALL UpdateVehicle(?, ?, ?, ?, ?, ?, ?)}";
            pstmt = conn.prepareStatement(sql);

            // Thiết lập giá trị cho các tham số
            pstmt.setInt(1, carId);
            pstmt.setString(2, regNo);
            pstmt.setString(3, model);
            pstmt.setString(4, type);
            pstmt.setString(5, status);
            pstmt.setInt(6, driverId);
            pstmt.setInt(7, price);

            // Thực thi câu lệnh SQL
            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0;  // Trả về true nếu có ít nhất một dòng được cập nhật
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            // Đóng kết nối
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
