package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StoreService;

import java.io.IOException;

@WebServlet(name = "TaskFiveServlet", value = "/task_five-servlet")
public class TaskFiveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StoreService service = new StoreService();
        req.setAttribute("order", service.createAndGetTodaySalesOrder("D12"));
        req.getRequestDispatcher("/task_five.jsp").forward(req,resp);
    }
}
