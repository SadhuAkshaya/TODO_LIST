package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import dao.ToDoDAO;
import dao.ToDoDAOImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/MarkTaskCompletedServlet")
public class MarkTaskCompletedServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out=response.getWriter();
    HttpSession session=request.getSession();
    ServletContext context=getServletContext();
    
    int regId=Integer.parseInt(request.getParameter("regId"));
    int taskId=Integer.parseInt(request.getParameter("taskId"));
    
    ToDoDAO dao=ToDoDAOImpl.getInstance();
    boolean flag=dao.markTaskCompleted(regId, taskId);
    if(flag)
      context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
    else
      out.println("TX Failed, Task not marked");
  }

}