package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/add")
public class addstudent extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/db";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "MOHANSAI2006";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String branch = request.getParameter("branch");

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Student</title>");
        out.println("</head>");
        out.println("<body>");

        try {

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL
            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            // SQL query
            String sql =
                    "INSERT INTO student(name, branch) VALUES (?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, branch);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                out.println("<h2>Student Added Successfully</h2>");

                out.println("<p>Name: "
                        + name + "</p>");

                out.println("<p>Branch: "
                        + branch + "</p>");

            } else {

                out.println("<h2>Student Not Added</h2>");
            }

            ps.close();
            con.close();

        } catch (ClassNotFoundException e) {

            out.println("<h2>MySQL Driver Not Found</h2>");

            e.printStackTrace(out);

        } catch (SQLException e) {

            out.println("<h2>Database Connection Error</h2>");

            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace(out);
        }

        out.println("<br>");
        out.println("<a href='login.html'>Back</a>");

        out.println("</body>");
        out.println("</html>");
    }
}
