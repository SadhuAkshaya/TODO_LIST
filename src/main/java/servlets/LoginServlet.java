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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out=response.getWriter();
    ServletContext ctxt=getServletContext();
    HttpSession session=request.getSession();
    
    String email=request.getParameter("email").trim();
    String pass=request.getParameter("pass").trim();
    if(email.length()==0 || pass.isEmpty()) {
      request.setAttribute("loginError", "Email/Pass is null");
      ctxt.getRequestDispatcher("/Login.jsp").forward(request,response);
    } else {
      ToDoDAO dao=ToDoDAOImpl.getInstance();
      int regId=dao.login(email, pass);
      System.out.println("regId : = "+regId);
      if(regId>0) {
        session.setAttribute("regId", regId);
        ctxt.getRequestDispatcher("/ViewTasks.jsp").forward(request,  response);
      } else {
        request.setAttribute("loginError", "Email/Pass is wrong");
        ctxt.getRequestDispatcher("/Login.jsp").forward(request,  response);
      }
    }
  }
}
