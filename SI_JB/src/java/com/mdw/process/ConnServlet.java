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
public class ConnServlet extends HttpServlet {

    static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ConnServlet.class);
    DatabaseProcess dp = new DatabaseProcess();

    public ConnServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("ini req message : " + "get_customer");
            HashMap result = dp.getCustomer();

            response.setContentType("application/json");
            response.getWriter().print(JsonProcess.generateJson(result));
            System.out.println("ini resp message : " + JsonProcess.generateJson(result));
        } catch (ParseException ex) {
            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);
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
                    String email = input.get("email").toString();
                    String name = input.get("name").toString();
                    String phonenumber = input.get("phonenumber").toString();
                    String address_ktp = input.get("address_ktp").toString();
                    String address_stay = input.get("address_stay").toString();
                    String birth_place = input.get("birth_place").toString();
                    String birth_day = input.get("birth_day").toString();
                    String gender = input.get("gender").toString();
                    String marital_status = input.get("marital_status").toString();
                    String children = input.get("children").toString();
                    String couple_ktp_num = input.get("couple_ktp_num").toString();
                    String couple_address = input.get("couple_address").toString();
                    String couple_name = input.get("couple_name").toString();
                    String couple_phone = input.get("couple_phone").toString();
                    String related_person_categorys = input.get("related_person_categorys").toString();
                    String related_person_name = input.get("related_person_name").toString();
                    String related_person_address = input.get("related_person_address").toString();
                    String related_person_phone = input.get("related_person_phone").toString();
                    String job_category = input.get("job_category").toString();
                    String company_name = input.get("company_name").toString();
                    String company_address = input.get("company_address").toString();
                    String company_phone = input.get("company_phone").toString();

                    hasil = dp.addCus(ktp_num, email, name, phonenumber, address_ktp, address_stay, birth_place, birth_day, gender, marital_status, children, couple_ktp_num, couple_name, couple_phone, couple_address, related_person_categorys, related_person_name, related_person_address, related_person_phone,
                            job_category, company_name, company_address, company_phone);
                    break;
                }
                case "update": {
                    String ktp_num = input.get("ktp_num").toString();
                    String email = input.get("email").toString();
                    String name = input.get("name").toString();
                    String phonenumber = input.get("phonenumber").toString();
                    String address_ktp = input.get("address_ktp").toString();
                    String address_stay = input.get("address_stay").toString();
                    String birth_place = input.get("birth_place").toString();
                    String birth_day = input.get("birth_day").toString();
                    String gender = input.get("gender").toString();
                    String marital_status = input.get("marital_status").toString();
                    String children = input.get("children").toString();
                    String couple_ktp_num = input.get("couple_ktp_num").toString();
                    String couple_address = input.get("couple_address").toString();
                    String couple_name = input.get("couple_name").toString();
                    String couple_phone = input.get("couple_phone").toString();
                    String related_person_categorys = input.get("related_person_categorys").toString();
                    String related_person_name = input.get("related_person_name").toString();
                    String related_person_address = input.get("related_person_address").toString();
                    String related_person_phone = input.get("related_person_phone").toString();
                    String job_category = input.get("job_category").toString();
                    String company_name = input.get("company_name").toString();
                    String company_address = input.get("company_address").toString();
                    String company_phone = input.get("company_phone").toString();

                    hasil = dp.updateCus(
                            ktp_num, email, name, phonenumber,
                            address_ktp, address_stay, birth_place,
                            birth_day, gender, marital_status,
                            children, couple_ktp_num, couple_name,
                            couple_phone, couple_address, related_person_categorys,
                            related_person_name, related_person_address, related_person_phone,
                            job_category, company_name, company_address,
                            company_phone);
                    break;
                }
                case "delete": {
                    String ktp_num = input.get("ktp_num").toString();
                    hasil = dp.deleteCus(ktp_num);
                    break;
                }
                default:
                    break;
            }
        }
        response.setContentType(
                "application/json");
        response.getWriter()
                .print(JsonProcess.generateJson(hasil));
        System.out.println(
                "ini resp message : " + hasil);
        in = null;
        input = null;
        hasil = null;
    }
}
