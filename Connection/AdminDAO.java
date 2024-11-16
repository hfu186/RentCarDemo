package Connection;

import java.sql.*;

public class AdminDAO {

    private Connection conn;

    public AdminDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rent_car", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tổng số xe trong kho
    public int getTotalCars() {
        String query = "SELECT COUNT(*) AS TotalCars FROM VEHICLE";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("TotalCars");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy tổng số khách hàng
    public int getTotalUsers() {
        String query = "SELECT COUNT(*) AS TotalUsers FROM USER";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("TotalUsers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy tổng số lượt booking
    public int getTotalBookings() {
        String query = "SELECT COUNT(*) AS TotalBookings FROM HISTORY_BOOKING";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("TotalBookings");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy tổng doanh thu đã kiếm được
    public double getTotalRevenue() {
        String query = "SELECT SUM(price) AS TotalRevenue FROM HISTORY_BOOKING WHERE Payment_status = 'Paid'";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble("TotalRevenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
