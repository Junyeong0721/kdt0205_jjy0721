# 소스 코드
```
package JAVADB.SEC1;

import java.sql.*;
import java.util.Scanner;


public class DBstdTest {

    public EnumMenu getMenu() {
        Scanner scan = new Scanner(System.in);
        int nMenu = -1;
        while (true) {
            System.out.println("---------------------------------------------");
            System.out.println("1. 모든 학생 정보 보기");
            System.out.println("2. 학생 추가하기");
            System.out.println("3. 학생 삭제하기");
            System.out.println("4. 학생 정보 수정하기...(전화번호와 이메일 수정)");
            System.out.println("0. 종료하기...");
            System.out.println("---------------------------------------------");
            System.out.print("메뉴를 입력하시오: ");
            try {
                nMenu = Integer.parseInt(scan.nextLine());
                if (nMenu >= 0 && nMenu <= 4) break;
            } catch (Exception e) { }
            System.out.println("잘못된 선택입니다....");
        }
        switch (nMenu) {
            case 1: return EnumMenu.MENU_LIST;
            case 2: return EnumMenu.MENU_INSERT;
            case 3: return EnumMenu.MENU_DELETE;
            case 4: return EnumMenu.MENU_UPDATE;
            default: return EnumMenu.MENU_EXIT;
        }
    }

   


    public Connection makeConnection() {
        String url = "jdbc:mysql://localhost/school_db?serverTimezone=Asia/Seoul";
        String userName = "root";
        String userPass = "1234";
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("데이터베이스 연결중 ...");
            con = DriverManager.getConnection(url, userName, userPass);
            System.out.println("데이터베이스 연결 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버를 찾지 못했습니다...");
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패: " + e.getMessage());
        }
        return con;
    }

    public void doList() {
        String sql = "SELECT * FROM student";
        try (Connection con = makeConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.printf("학번: %d\t이름: %s\t전화번호: %s\t이메일: %s\n",
                        rs.getInt("stdno"), rs.getString("stdname"),
                        rs.getString("phone"), rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("조회 오류: " + e.getMessage());
        }
    }

    public void doInsert() {
        Scanner scan = new Scanner(System.in);
        System.out.print("학번: ");
        int stdno = Integer.parseInt(scan.nextLine());
        System.out.print("이름: ");
        String stdname = scan.nextLine();
        System.out.print("전화번호: ");
        String phone = scan.nextLine();
        System.out.print("이메일: ");
        String email = scan.nextLine();

        String sql = "INSERT INTO student(stdno, stdname, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection con = makeConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, stdno);
            pstmt.setString(2, stdname);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            System.out.println("학생 정보가 추가되었습니다.");
        } catch (SQLException e) {
            System.out.println("삽입 오류: " + e.getMessage());
        }
    }

    public void doDelete() {
        Scanner scan = new Scanner(System.in);
        System.out.print("삭제할 학번: ");
        int stdno = Integer.parseInt(scan.nextLine());

        String sql = "DELETE FROM student WHERE stdno = ?";
        try (Connection con = makeConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, stdno);
            int result = pstmt.executeUpdate();
            if (result > 0) System.out.println("삭제되었습니다.");
            else System.out.println("해당 학번이 존재하지 않습니다.");
        } catch (SQLException e) {
            System.out.println("삭제 오류: " + e.getMessage());
        }
    }

    public void doUpdate() {
        Scanner scan = new Scanner(System.in);
        System.out.print("수정할 학번: ");
        int stdno = Integer.parseInt(scan.nextLine());
        System.out.print("새 전화번호: ");
        String phone = scan.nextLine();
        System.out.print("새 이메일: ");
        String email = scan.nextLine();

        String sql = "UPDATE student SET phone = ?, email = ? WHERE stdno = ?";
        try (Connection con = makeConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, email);
            pstmt.setInt(3, stdno);
            int result = pstmt.executeUpdate();
            if (result > 0) System.out.println("수정되었습니다.");
            else System.out.println("해당 학번이 존재하지 않습니다.");
        } catch (SQLException e) {
            System.out.println("수정 오류: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        DBstdTest stdDemo = new DBstdTest(); // 클래스 이름에 맞게 수정
        while (true) {
            EnumMenu menu = stdDemo.getMenu();
            switch (menu) {
                case MENU_LIST:
                    System.out.println("리스트 보기 메뉴를 선택하셨습니다...");
                    stdDemo.doList();
                    break;
                case MENU_INSERT:
                    System.out.println("추가 메뉴를 선택하셨습니다...");
                    stdDemo.doInsert();
                    break;
                case MENU_DELETE:
                    System.out.println("삭제 메뉴를 선택하셨습니다...");
                    stdDemo.doDelete();
                    break;
                case MENU_UPDATE:
                    System.out.println("수정 메뉴를 선택하셨습니다...");
                    stdDemo.doUpdate();
                    break;
                default:
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
            }
        }
    }
}
```
