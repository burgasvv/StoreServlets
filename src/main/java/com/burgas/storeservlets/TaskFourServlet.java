package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StoreService;

import java.io.IOException;

@WebServlet(name = "TaskFourServlet", value = "/task_four-servlet")
public class TaskFourServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StoreService service = new StoreService();
        req.setAttribute("order numbers",service.getOrderNumberByProductNameAndDate("Lemonade"));
        req.getRequestDispatcher("/task_four.jsp").forward(req,resp);
    }
}
