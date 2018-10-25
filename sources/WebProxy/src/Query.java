import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Query extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        UserAccess acc = new UserAccess();

        acc.setCookie("admin");
        acc.setCookie("kirill");
        acc.query("SELECT * FROM users");

        try {
            resp.getWriter().println("<?xml version=\"1.0\"?>");
            resp.getWriter().println("<spinwaves>");
            while (acc.rs.next()) {
                resp.getWriter().println("\t<"+acc.rsm.getTableName(1)+">");
                for(int j=0;j<acc.rsm.getColumnCount();j++){
                    resp.getWriter().print("\t\t<"+acc.rsm.getColumnLabel(j+1)+">");
                    resp.getWriter().print(acc.rs.getString(j+1));
                    resp.getWriter().print("</"+acc.rsm.getColumnLabel(j+1)+">\n");
                }
                resp.getWriter().println("\t</"+acc.rsm.getTableName(1)+">");
            }
            resp.getWriter().println("</spinwaves>");
        } catch (SQLException se) {
            se.printStackTrace();
        }


        acc.disconnect();


    }
}
