package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StoreService;

import java.io.IOException;

@WebServlet(name = "TaskSixServlet", value = "/task_six-servlet")
public class TaskSixServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StoreService service = new StoreService();
        req.setAttribute("order numbers", service.getOrderNumbersAndDelete("Potatoes", 25));
        req.getRequestDispatcher("/task_six.jsp").forward(req,resp);
    }
}
