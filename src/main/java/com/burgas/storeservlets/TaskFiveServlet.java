package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.burgas.storeservlets.service.OrderService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet(name = "TaskFiveServlet", value = "/task_five-servlet")
public class TaskFiveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderNumber = Objects.requireNonNull(
                req.getParameter("order_number"),
                "The order number is null"
        );

        OrderService service = new OrderService();

        try {
            req.setAttribute("order", service.createAndGet(orderNumber));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/task/task_five.jsp").forward(req,resp);
    }
}
