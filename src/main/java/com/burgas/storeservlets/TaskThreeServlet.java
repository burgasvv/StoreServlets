package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.burgas.storeservlets.service.OrderService;

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "TaskThreeServlet", value = "/task_three-servlet")
public class TaskThreeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String productName = Objects.requireNonNull(
                req.getParameter("product_name"),
                "Product name is null"
        );
        OrderService service = new OrderService();
        req.setAttribute("order numbers", service.getByProductName(productName));
        req.getRequestDispatcher("/task/task_three.jsp").forward(req,resp);
    }
}
