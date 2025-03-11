package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import beans.Task;
import dao.ToDoDAO;
import dao.ToDoDAOImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out=response.getWriter();
    HttpSession session=request.getSession();
    ServletContext context=getServletContext();
    
    String taskName=request.getParameter("taskName").trim();
    String taskDate=request.getParameter("taskDate").trim();    
    int taskStatus=Integer.parseInt(request.getParameter("taskStatus"));
    System.out.println(" taskStatus "+taskStatus);
    int regId=((Integer)session.getAttribute("regId")).intValue();
    
    Task task=new Task(0,taskName,taskDate,taskStatus,regId);
    
    ToDoDAO dao=ToDoDAOImpl.getInstance();
    boolean flag=dao.addTask(task, regId);
    
    if(flag) {
      context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
    } else {
      out.println("Tx Failed, Task not added");
    }    
  }
}
