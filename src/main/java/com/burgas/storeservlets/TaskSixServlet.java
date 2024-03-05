package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.burgas.storeservlets.service.OrderService;

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "TaskSixServlet", value = "/task_six-servlet")
public class TaskSixServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String productName = Objects.requireNonNull(
                req.getParameter("product_name"),
                "The product name is null"
        );
        String productCount = Objects.requireNonNull(
                req.getParameter("product_count"),
                "The product count is null"
        );
        OrderService service = new OrderService();
        req.setAttribute("order numbers", service.getAndDelete(productName, Integer.parseInt(productCount)));
        req.getRequestDispatcher("/task_six.jsp").forward(req,resp);
    }
}
