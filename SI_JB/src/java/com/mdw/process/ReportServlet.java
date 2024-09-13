/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mdw.process;

import com.mdw.db.DatabaseProcess;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author suhanda
 */
public class ReportServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ReportServlet.class);
    DatabaseProcess dp = new DatabaseProcess();

    public ReportServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            ArrayList<Model_Report> listTransaction = new ArrayList<>();
//            listTransaction = dp.getAllTrx();
//            Gson gson = new Gson();
//            JsonElement element = gson.toJsonTree(listTransaction);
//            JsonArray jsonArray = element.getAsJsonArray();
//
//            response.setContentType("application/json");
//            response.getWriter().print(jsonArray);
//        } catch (ParseException ex) {
//            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
