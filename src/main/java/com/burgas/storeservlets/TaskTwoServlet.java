package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.burgas.storeservlets.service.OrderService;

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "TaskTwoServlet", value = "/task_two-servlet")
public class TaskTwoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String price = Objects.requireNonNull(
                req.getParameter("price"),
                "The price is null"
        );
        String productCount = Objects.requireNonNull(
                req.getParameter("product _count"),
                "The product count is null"
        );
        OrderService service = new OrderService();
        req.setAttribute("order numbers", service.getBy(Integer.parseInt(price), Integer.parseInt(productCount)));
        req.getRequestDispatcher("/task_two.jsp").forward(req,resp);
    }
}
