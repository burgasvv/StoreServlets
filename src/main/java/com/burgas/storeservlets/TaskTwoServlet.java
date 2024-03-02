package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StoreService;

import java.io.IOException;

@WebServlet(name = "TaskTwoServlet", value = "/task_two-servlet")
public class TaskTwoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StoreService service = new StoreService();
        req.setAttribute("order numbers", service.getOrderNumberByCondition(3000, 3));
        req.getRequestDispatcher("/task_two.jsp").forward(req,resp);
    }
}
