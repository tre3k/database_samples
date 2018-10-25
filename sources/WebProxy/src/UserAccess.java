import javax.servlet.UnavailableException;
import java.sql.*;
import java.util.Date;

public class UserAccess {

    final String JDBC_DRIVER = "org.sqlite.JDBC";
    final String DB_URL = "jdbc:sqlite:database.db";
    private Connection conn = null;

    public ResultSet rs = null;
    public ResultSetMetaData rsm = null;


    UserAccess()  throws UnavailableException {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);

        } catch (Exception e){
            e.printStackTrace();
            throw new UnavailableException(e.getMessage());
        }
    }

    public void query(String q,boolean rw){
        try {
            Statement stmt = conn.createStatement();
            if(!rw) {
                rs = stmt.executeQuery(q);
                rsm = rs.getMetaData();
            }else{
                stmt.executeUpdate(q);
            }
            stmt = null;
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    public void query(String q){
        query(q,false);
    }

    /* set the field 'cookie' to database */
    public String setCookie(String login){
        Date date = new Date();
        String CookieHash = GenerateHash.md5Hash(login+date.toString());
        query("UPDATE users SET cookie=\'"+CookieHash+"\' WHERE login=\'"+login+"\'",true);
        return CookieHash;
    }

    /* remove the field 'cookie' from the database */
    public void remCookie(String login){
        Date date = new Date();
        query("UPDATE users SET cookie=\'NULL\' WHERE login=\'"+login+"\'",true);
        return;
    }

    /* the function for checking a password */
    public boolean CheckPass(String login,String pass){
        String hash_pass = GenerateHash.md5Hash(pass);

        query("SELECT login,pass FROM users WHERE login=\'"+login+"\'");

        try {
            while (rs.next()) {
                String db_login = rs.getString("login");
                String db_hash_pass = rs.getString("pass");

                if((db_login.equals(login)) && (hash_pass.equals(db_hash_pass))) return true;
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }

    /* the function for disconnect from the database */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

}
