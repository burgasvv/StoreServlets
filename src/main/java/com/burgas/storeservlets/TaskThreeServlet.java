package com.burgas.storeservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StoreService;

import java.io.IOException;

@WebServlet(name = "TaskThreeServlet", value = "/task_three-servlet")
public class TaskThreeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StoreService service = new StoreService();
        req.setAttribute("order numbers", service.getOrderNumberByProductName("Apples"));
        req.getRequestDispatcher("/task_three.jsp").forward(req,resp);
    }
}
