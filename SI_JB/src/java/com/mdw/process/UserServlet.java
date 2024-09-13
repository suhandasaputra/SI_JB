/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mdw.process;

import com.function.JsonProcess;
import com.mdw.db.DatabaseProcess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author suhanda
 */
public class UserServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(UserServlet.class);
    DatabaseProcess dp = new DatabaseProcess();

    public UserServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("ini req message : " + "get_user");
            HashMap result = dp.getAllUser();
            response.setContentType("application/json");
            response.getWriter().print(JsonProcess.generateJson(result));
            System.out.println("ini resp message : " + JsonProcess.generateJson(result));
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        HashMap input = new HashMap();
        HashMap hasil = new HashMap();
        String inputString = "";
        String line = "";

        while ((line = in.readLine()) != null) {
            inputString += line;
        }
        input = JsonProcess.decodeJson(inputString);

        System.out.println("ini req message : " + input);
        String req_type = input.get("req_type").toString();
        if (null != req_type) {
            switch (req_type) {
                case "add": {
                    String user_name = input.get("user_name").toString();
                    String user_phone = input.get("user_phone").toString();
                    String user_email = input.get("user_email").toString();
                    String password = input.get("password").toString();
                    hasil = dp.addUser(user_name, user_phone, user_email, password);
                    break;
                }
                case "update": {
                    String user_name = input.get("user_name").toString();
                    String user_phone = input.get("user_phone").toString();
                    String user_email = input.get("user_email").toString();
                    String user_id = input.get("user_id").toString();
                    hasil = dp.updateUser(user_name, user_phone, user_email, user_id);
                    break;
                }
                case "delete": {
                    String user_id = input.get("user_id").toString();
                    hasil = dp.deleteUser(user_id);
                    break;
                }
                default:
                    break;
            }
        }
        response.setContentType("application/json");
        response.getWriter().print(JsonProcess.generateJson(hasil));
        System.out.println("ini resp message : " + hasil);

        in = null;
        input = null;
        hasil = null;
    }
}
