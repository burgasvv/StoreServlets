package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.burgas.storeservlets.service.OrderService;

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "TaskOneServlet", value = "/task_one-servlet")
public class TaskOneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderNumber = Objects.requireNonNull(
                req.getParameter("order_number"),
                "The order number is null"
        );
        OrderService service = new OrderService();
        req.setAttribute("info", service.getInfo(orderNumber));
        req.getRequestDispatcher("/task/task_one.jsp").forward(req,resp);
    }
}
