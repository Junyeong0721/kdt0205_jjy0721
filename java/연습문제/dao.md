## 소스 코드

# DPHelper
```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String db_hostname = "localhost";
    private static final int db_portnumber = 3306;
    private static final String db_database = "school";
    private static final String db_charset = "utf8";
    private static final String db_username = "root";
    private static final String db_password = "1234";

    private Connection conn = null;

    // ------------ 싱글톤 객체 시작 --------------
    private static DBHelper current;

    public static DBHelper getInstance() {
        if (current == null) {
            current = new DBHelper();
        }
        return current;
    }

    public static void freeInstance() {
        current = null;
    }

    private DBHelper() { }
    // ------------ 싱글톤 객체 끝 --------------

    /** 데이터베이스에 접속 후, 접속 객체 return */
    public Connection open() {
        if (conn == null) {
            String urlFormat = "jdbc:mysql://%s:%d/%s?&characterEncoding=%s";
            String url = String.format(urlFormat, db_hostname, db_portnumber, db_database, db_charset);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, db_username, db_password);
                System.out.println("=== DATABASE Connect Success ===");
            } catch (ClassNotFoundException e) {
                System.out.println("=== DATABASE Connect Fail ===");
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println("=== DATABASE Connect Fail ===");
                System.out.println(e.getMessage());
            }
        }
        return conn;
    }

    /** 데이터베이스 접속 해제 */
    public void close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("=== DATABASE Disconnect Success ===");
            } catch (Exception e) {
                System.out.println("=== DATABASE Disconnect Fail ===");
                System.out.println(e.getMessage());
            }
            conn = null;
        }
    }
}
```

# Student
```
public class Student {
    private int stdno;
    private String stdname;
    private String phone;
    private String email;

    public Student(int stdno, String stdname, String phone, String email) {
        super();
        this.stdno = stdno;
        this.stdname = stdname;
        this.phone = phone;
        this.email = email;
    }

    public int getStdno() {
        return stdno;
    }

    public void setStdno(int stdno) {
        this.stdno = stdno;
    }

    public String getStdname() {
        return stdname;
    }

    public void setStdname(String stdname) {
        this.stdname = stdname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student [stdno=" + stdno + ", stdname=" + stdname + ", phone=" + phone + ", email=" + email + "]";
    }
}
```

# StudentDao
```
import java.util.List;

public interface StudentDao {
    public int insert(Student params);
    public int delete(int params);
    public int update(Student params);
    public Student selectOne(int params);
    public List<Student> select();
}
```

# StudentDaoImpl
```
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    /** 데이터 베이스 접속 객체 */
    private Connection conn;

    /** 생성자를 통해서 데이터베이스 접속 객체를 전달 받는다. */
    public StudentDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int insert(Student params) {
        int result = 0;
        String sql = "INSERT INTO student (stdname, phone, email) VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, params.getStdname());
            pstmt.setString(2, params.getPhone());
            pstmt.setString(3, params.getEmail());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("MySQL SQL Fail : " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        }
        return result;
    }

    @Override
    public int delete(int params) {
        int result = 0;
        String sql = "DELETE FROM student WHERE stdno=?";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, params);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("MySQL SQL Fail : " + e.getMessage());
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        }

        return result;
    }

    @Override
    public int update(Student params) {
        int result = 0;
        String sql = "UPDATE student SET phone=?, email=? WHERE stdno=?";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, params.getPhone());
            pstmt.setString(2, params.getEmail());
            pstmt.setInt(3, params.getStdno());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("MySQL SQL Fail : " + e.getMessage());
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        }

        return result;
    }

    @Override
    public Student selectOne(int params) {
        Student result = null;
        String sql = "SELECT stdno, stdname, phone, email FROM student WHERE stdno=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, params);
            rs = pstmt.executeQuery();

            boolean first = rs.next();
            if (first) {
                int stdno = rs.getInt("stdno");
                String stdname = rs.getString("stdname");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                result = new Student(stdno, stdname, phone, email);
            } else {
                System.out.println("조회 결과가 없습니다.");
            }
        } catch (SQLException e) {
            System.out.println("MySQL SQL Fail : " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        }

        return result;
    }

    @Override
    public List<Student> select() {
        List<Student> result = null;
        String sql = "SELECT stdno, stdname, phone, email FROM student";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            result = new ArrayList<Student>();

            while (rs.next()) {
                int stdno = rs.getInt("stdno");
                String stdname = rs.getString("stdname");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                Student item = new Student(stdno, stdname, phone, email);
                result.add(item);
            }
        } catch (SQLException e) {
            System.out.println("MySQL SQL Fail : " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        }

        return result;
    }
}
```
# main01
```
import java.sql.Connection;

public class Main01 {
    public static void main(String[] args) {
        Connection conn = DBHelper.getInstance().open();
        if (conn == null) {
            System.out.println("데이터베이스 접속 실패");
            return;
        }

        Student model = new Student(0, "홍길동", "010-1234-5678", "hong@school.com");

        StudentDao dao = new StudentDaoImpl(conn);
        int result = dao.insert(model);

        System.out.println(result + "번 데이터 저장됨");

        DBHelper.getInstance().close();
    }
}
```

# main02
```
import java.sql.Connection;

public class Main02 {
    public static void main(String[] args) {
        Connection conn = DBHelper.getInstance().open();
        if (conn == null) {
            System.out.println("데이터베이스 접속 실패");
            return;
        }

        int target = 1;

        StudentDao dao = new StudentDaoImpl(conn);
        int result = dao.delete(target);

        System.out.println(result + "개의 데이터 삭제됨");

        DBHelper.getInstance().close();
    }
}
```

#main 03

```
import java.sql.Connection;

public class Main03 {
    public static void main(String[] args) {
        Connection conn = DBHelper.getInstance().open();
        if (conn == null) {
            System.out.println("데이터베이스 접속 실패");
            return;
        }

        Student model = new Student(1, null, "010-7777-8888", "newmail@school.com");

        StudentDao dao = new StudentDaoImpl(conn);
        int result = dao.update(model);

        System.out.println(result + "개의 데이터 수정됨");

        DBHelper.getInstance().close();
    }
}
```

#DaoDemo
```
import java.sql.Connection;
import java.util.List;

public class DaoDemo1 {
    public static void main(String[] args) {
        Connection conn = DBHelper.getInstance().open();
        if (conn == null) {
            System.out.println("데이터베이스 접속 실패");
            return;
        }

        StudentDao dao = new StudentDaoImpl(conn);
        List<Student> result = dao.select();

        if (result == null) {
            System.out.println("조회결과 없음");
        } else {
            for (int i = 0; i < result.size(); i++) {
                Student item = result.get(i);
                System.out.println(item.toString());
            }
        }

        DBHelper.getInstance().close();
    }
}
```
