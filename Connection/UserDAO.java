package Connection;

import Connection.connect;
import java.sql.*;

public class UserDAO {

    public int Usr_id;
    public String UName;
    public String Contact_no;
    public String Gender;
    public String Address;

    public UserDAO(int Usr_id, String UName, String Contact_no, String Gender, String Address) {
        this.Usr_id = Usr_id;
        this.UName = UName;
        this.Contact_no = Contact_no;
        this.Gender = Gender;
        this.Address = Address;
    }

    public UserDAO() {
    }

    public int getUsr_id() {
        return Usr_id;
    }

    public String getUName() {
        return UName;
    }

    public String getContact_no() {
        return Contact_no;
    }

    public String getGender() {
        return Gender;
    }

    public String getAddress() {
        return Address;
    }

    public boolean registerUser(String usrName, String usrPass, String name, String contactNo, String gender, String address) {
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            // Kết nối với cơ sở dữ liệu
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rent_car", "root", "");
            
            if (conn != null) {
                String sql = "{CALL CreateUserAccount(?, ?, ?, ?, ?, ?)}";
                stmt = conn.prepareCall(sql);

                // Thiết lập các tham số đầu vào cho thủ tục
                stmt.setString(1, usrName);
                stmt.setString(2, usrPass);
                stmt.setString(3, name);
                stmt.setString(4, contactNo);
                stmt.setString(5, gender);
                stmt.setString(6, address);

                // Thực thi thủ tục
                stmt.execute();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng CallableStatement và Connection
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
 
}
