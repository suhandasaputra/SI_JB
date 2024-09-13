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
public class PicServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PicServlet.class);
    DatabaseProcess dp = new DatabaseProcess();

    public PicServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("ini req message : " + "get_pic");
            HashMap result = dp.getAllPic();
            response.setContentType("application/json");
            response.getWriter().print(JsonProcess.generateJson(result));
            System.out.println("ini resp message : " + JsonProcess.generateJson(result));
        } catch (ParseException ex) {
            Logger.getLogger(PicServlet.class.getName()).log(Level.SEVERE, null, ex);
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
                    String ktp_num = input.get("ktp_num").toString();
                    String pic_name = input.get("pic_name").toString();
                    String pic_phone = input.get("pic_phone").toString();
                    String pic_email = input.get("pic_email").toString();
                    String pic_address = input.get("pic_address").toString();
                    String pic_born_place = input.get("pic_born_place").toString();
                    String pic_born_date = input.get("pic_born_date").toString();
                    hasil = dp.addPic(ktp_num, pic_name, pic_phone, pic_email, pic_address, pic_born_place, pic_born_date);
                    break;
                }
                case "update": {
                    String ktp_num = input.get("ktp_num").toString();
                    String pic_name = input.get("pic_name").toString();
                    String pic_phone = input.get("pic_phone").toString();
                    String pic_email = input.get("pic_email").toString();
                    String pic_address = input.get("pic_address").toString();
                    String pic_born_place = input.get("pic_born_place").toString();
                    String pic_born_date = input.get("pic_born_date").toString();
                    hasil = dp.updatePic(ktp_num, pic_name, pic_phone, pic_email, pic_address, pic_born_place, pic_born_date);
                    break;
                }
                case "delete": {
                    String ktp_num = input.get("ktp_num").toString();
                    hasil = dp.deletePic(ktp_num);
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
