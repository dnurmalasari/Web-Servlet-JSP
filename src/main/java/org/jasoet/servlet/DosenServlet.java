package org.jasoet.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasoet.dao.DosenDAO;
import org.jasoet.dao.impl.DosenDAOImpl;
import org.jasoet.db.DatabaseConnection;
import org.jasoet.model.Dosen;

/**
 * @author jasoet
 */
public class DosenServlet extends HttpServlet {

    private String list = "/WEB-INF/jsp/dosen/list.jsp";
    private String show = "/WEB-INF/jsp/dosen/show.jsp";
    private DosenDAO dosenDAO;

    public DosenServlet() {
        try {
            dosenDAO = new DosenDAOImpl(DatabaseConnection.getInstance().getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String action = request.getParameter("action");
            if (action != null) {
                if (action.equalsIgnoreCase("show")) {

                    String niy = request.getParameter("niy");

                    niy = (niy != null) ? niy : "";

                    Dosen dosen = dosenDAO.getByNiy(niy);

                    request.setAttribute("data", dosen);

                    RequestDispatcher dispacher = request.getRequestDispatcher(show);
                    dispacher.forward(request, response);

                }
            } else {
                String nama = request.getParameter("nama");
                if (nama != null) {

                    List<Dosen> data = dosenDAO.getByNama(nama);

                    request.setAttribute("data", data);

                    RequestDispatcher dispacher = request.getRequestDispatcher(list);
                    dispacher.forward(request, response);
                } else {
                    List<Dosen> data = dosenDAO.getAll();

                    request.setAttribute("data", data);

                    RequestDispatcher dispacher = request.getRequestDispatcher(list);
                    dispacher.forward(request, response);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
