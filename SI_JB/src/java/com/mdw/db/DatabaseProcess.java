/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mdw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseProcess {

    private void clearStatment(PreparedStatement stat) {
        if (stat != null) {
            try {
                stat.clearBatch();
                stat.clearParameters();
                stat.close();
                stat = null;
            } catch (SQLException ex) {
            }
        }
    }

    private void clearDBConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
            }
        }
    }

    private void clearResultset(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
            }
        }
    }

    private void clearAllConnStatRS(Connection conn, PreparedStatement stat, ResultSet rs) {
        clearResultset(rs);
        clearStatment(stat);
        clearDBConnection(conn);
    }

    //proses login
    public HashMap validate(String user, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        String present = "0";
        String pass = "0";

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from fds_user where user_id=?");
            stat.setString(1, user);
            rs = stat.executeQuery();
            while (rs.next()) {
                present = "1";
                result.put("user_status", rs.getString("user_status"));
                result.put("status_login", rs.getString("status_login"));
                stat = conn.prepareStatement("select * from fds_user where user_id=? and password=?");
                stat.setString(1, user);
                stat.setString(2, password);
                rs = stat.executeQuery();
                while (rs.next()) {
                    pass = "1";
                }
                result.put("pass", pass);
            }
            result.put("present", present);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

//    public ArrayList<Model_Product> getAllProduct() throws ParseException {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        ArrayList<Model_Product> listProduct = new ArrayList<>();
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("Select * from trancode");
//            rs = stat.executeQuery();
//            while (rs.next()) {
//                Model_Product reportyabes = new Model_Product();
//                reportyabes.setTrancodeid(rs.getString("trancodeid"));
//                reportyabes.setBankcode(rs.getString("bankcode"));
//                reportyabes.setTrancodename(rs.getString("trancodename"));
//                reportyabes.setTcbiller(rs.getString("tcbiller"));
//                reportyabes.setFeebeli(rs.getString("feebeli"));
//                reportyabes.setFeejual(rs.getString("feejual"));
//                reportyabes.setFeebpd(rs.getString("feebpd"));
//                reportyabes.setFeebank(rs.getString("feebank"));
//                reportyabes.setFeeskema(rs.getString("feeskema"));
//                listProduct.add(reportyabes);
//            }
//        } catch (SQLException e) {
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return listProduct;
//    }
//    public String addProduct(Model_Product pro) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        String status;
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            boolean status1 = false;
//            stat = conn.prepareStatement("select * from trancode where trancodeid=?");
//            stat.setString(1, pro.getTrancodeid());
//            rs = stat.executeQuery();
//            status1 = rs.next();
//            if (status1 == true) {
//                return status = "01";
//            } else {
//                stat = conn.prepareStatement("INSERT INTO trancode(trancodeid, bankcode, trancodename, tcbiller, feebeli, feejual, feebpd, feebank, feeskema) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
//                stat.setString(1, pro.getTrancodeid());
//                stat.setString(2, pro.getBankcode());
//                stat.setString(3, pro.getTrancodename());
//                stat.setString(4, pro.getTcbiller());
//                stat.setInt(5, Integer.valueOf(pro.getFeebeli()));
//                stat.setInt(6, Integer.valueOf(pro.getFeejual()));
//                stat.setInt(7, Integer.valueOf(pro.getFeebpd()));
//                stat.setInt(8, Integer.valueOf(pro.getFeebank()));
//                stat.setString(9, pro.getFeeskema());
//                stat.executeUpdate();
//            }
//        } catch (SQLException e) {
//            return status = e.getMessage();
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return status = "00";
//    }
//    public Model_Product getproductDetail(String trancodeid) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        Model_Product mp = new Model_Product();
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("select * from trancode where trancodeid=?");
//            stat.setString(1, trancodeid);
//            rs = stat.executeQuery();
//            if (rs.next()) {
//                mp.setTrancodeid(rs.getString("trancodeid"));
//                mp.setBankcode(rs.getString("bankcode"));
//                mp.setTrancodename(rs.getString("trancodename"));
//                mp.setTcbiller(rs.getString("tcbiller"));
//                mp.setFeebeli(rs.getString("feebeli"));
//                mp.setFeejual(rs.getString("feejual"));
//                mp.setFeebpd(rs.getString("feebpd"));
//                mp.setFeebank(rs.getString("feebank"));
//                mp.setFeeskema(rs.getString("feeskema"));
//            }
//        } catch (SQLException e) {
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return mp;
//    }
//    public String deleteproduct(String trancodeid) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        String status = "01";
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("delete from trancode where trancodeid=?");
//            stat.setString(1, trancodeid);
//            stat.executeUpdate();
//            status = "00";
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return status;
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return status;
//    }
//    public String updateProduk(String trancodeid, String bankcode, String trancodename, String tcbiller, String feebeli, String feejual, String feebpd, String feebank, String feeskema) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        String status = "01";
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("UPDATE trancode SET bankcode=?, trancodename=?, tcbiller=?, feebeli=?, feejual=?, feebpd=?, feebank=?, feeskema=? WHERE trancodeid=?");
//            stat.setString(1, bankcode);
//            stat.setString(2, trancodename);
//            stat.setString(3, tcbiller);
//            stat.setInt(4, Integer.valueOf(feebeli));
//            stat.setInt(5, Integer.valueOf(feejual));
//            stat.setInt(6, Integer.valueOf(feebpd));
//            stat.setInt(7, Integer.valueOf(feebank));
//            stat.setString(8, feeskema);
//            stat.setString(9, trancodeid);
//            stat.executeUpdate();
//            status = "00";
//            stat.clearParameters();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return status = e.getMessage();
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return status;
//    }
//    public ArrayList<Model_Report> getAllTrx() throws ParseException {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        ArrayList<Model_Report> listTrx = new ArrayList<>();
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("select * from (\n"
//                    + "select requesttime,responsetime,noref,cardno,bankcode,fromaccount,toaccount,amount,proccode,transactionid,productcode,rcinternal,custno,mti,terminalid,merchanttype,responsecode from tempmsg \n"
//                    + "union all \n"
//                    + "select requesttime,responsetime,noref,cardno,bankcode,fromaccount,toaccount,amount,proccode,transactionid,productcode,rcinternal,custno,mti,terminalid,merchanttype,responsecode from tempmsg_backup order by requesttime desc\n"
//                    + ") AS tempmsg");
//            rs = stat.executeQuery();
//            while (rs.next()) {
//                Model_Report reportyabes = new Model_Report();
//                reportyabes.setRequesttime(rs.getString("requesttime"));
//                reportyabes.setMti(rs.getString("mti"));
//                reportyabes.setNoref(rs.getString("noref"));
//                reportyabes.setCardno(rs.getString("cardno"));
//                reportyabes.setBankcode(rs.getString("bankcode"));
//                reportyabes.setFromaccount(rs.getString("fromaccount"));
//                reportyabes.setToaccount(rs.getString("toaccount"));
//                reportyabes.setAmount(rs.getString("amount"));
//                reportyabes.setProccode(rs.getString("proccode"));
//                reportyabes.setProductcode(rs.getString("productcode"));
//                reportyabes.setResponsecode(rs.getString("responsecode"));
//                listTrx.add(reportyabes);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return listTrx;
//    }
    public HashMap getAllUser() throws ParseException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from fds_user where user_status = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("user_id", rs.getString("user_id"));
                ab.put("user_name", rs.getString("user_name"));
                ab.put("user_phone", rs.getString("user_phone"));
                ab.put("user_email", rs.getString("user_email"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap addUser(String user_name, String user_phone, String user_email, String password) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();

            stat = conn.prepareStatement("INSERT INTO fds_user(user_id, user_name, user_phone, user_email, password) VALUES (?, ?, ?, ?, ?)");
            stat.setString(1, user_email);
            stat.setString(2, user_name);
            stat.setString(3, user_phone);
            stat.setString(4, user_email);
            stat.setString(5, password);
            stat.executeUpdate();
            result.put("resp_code", "00");
            result.put("resp_desc", "success");

        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("error_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap deleteUser(String user_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update fds_user set user_status=? WHERE user_id=?");
            stat.setString(1, "0");
            stat.setString(2, user_id);
            stat.executeUpdate();
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
            stat.clearParameters();
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());

        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap updateUser(String user_name, String user_phone, String user_email, String user_id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update fds_user set user_name=?, user_phone=?, user_email=? WHERE user_id=?");
            stat.setString(1, user_name);
            stat.setString(2, user_phone);
            stat.setString(3, user_email);
            stat.setString(4, user_id);
            stat.executeUpdate();
            stat.clearParameters();
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getCustomer() throws ParseException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from customer where status_active = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();

                ab.put("ktp_num", rs.getString("ktp_num"));
                ab.put("email", rs.getString("email"));
                ab.put("name", rs.getString("name"));
                ab.put("phonenumber", rs.getString("phonenumber"));
                ab.put("address_ktp", rs.getString("address_ktp"));
                ab.put("address_stay", rs.getString("address_stay"));
                ab.put("birth_place", rs.getString("birth_place"));
                ab.put("birth_day", rs.getString("birth_day"));
                ab.put("gender", rs.getString("gender"));
                ab.put("marital_status", rs.getString("marital_status"));
                ab.put("children", rs.getString("children"));
                ab.put("couple_ktp_num", rs.getString("couple_ktp_num"));
                ab.put("couple_name", rs.getString("couple_name"));
                ab.put("couple_phone", rs.getString("couple_phone"));
                ab.put("couple_address", rs.getString("couple_address"));
                ab.put("related_person_categorys", rs.getString("related_person_categorys"));
                ab.put("related_person_name", rs.getString("related_person_name"));
                ab.put("related_person_address", rs.getString("related_person_address"));
                ab.put("related_person_phone", rs.getString("related_person_phone"));
                ab.put("job_category", rs.getString("job_category"));
                ab.put("company_name", rs.getString("company_name"));
                ab.put("company_address", rs.getString("company_address"));
                ab.put("company_phone", rs.getString("company_phone"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap addCus(String ktp_num, String email, String name, String phonenumber, String address_ktp, String address_stay,
            String birth_place, String birth_day, String gender, String marital_status, String children, String couple_ktp_num,
            String couple_name, String couple_phone, String couple_address, String related_person_categorys, String related_person_name, String related_person_address, String related_person_phone,
            String job_category, String company_name, String company_address, String company_phone) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO customer (ktp_num, email, name, phonenumber, address_ktp, address_stay, birth_place, birth_day, gender, marital_status, "
                    + "children, couple_ktp_num, couple_name, couple_phone, couple_address, related_person_categorys, related_person_name, related_person_address, related_person_phone, "
                    + "job_category, company_name, company_address, company_phone) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stat.setString(1, ktp_num);
            stat.setString(2, email);
            stat.setString(3, name);
            stat.setString(4, phonenumber);
            stat.setString(5, address_ktp);
            stat.setString(6, address_stay);
            stat.setString(7, birth_place);
            stat.setString(8, birth_day);
            stat.setString(9, gender);
            stat.setString(10, marital_status);
            stat.setString(11, children);
            stat.setString(12, couple_ktp_num);
            stat.setString(13, couple_name);
            stat.setString(14, couple_phone);
            stat.setString(15, couple_address);
            stat.setString(16, related_person_categorys);
            stat.setString(17, related_person_name);
            stat.setString(18, related_person_address);
            stat.setString(19, related_person_phone);
            stat.setString(20, job_category);
            stat.setString(21, company_name);
            stat.setString(22, company_address);
            stat.setString(23, company_phone);
            stat.executeUpdate();

            result.put("resp_code", "00");
            result.put("resp_desc", "success");

        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap deleteCus(String ktp_num) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update customer set status_active = '0' where ktp_num=?");
            stat.setString(1, ktp_num);
            stat.executeUpdate();
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
            stat.clearParameters();
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap updateCus(
            String ktp_num, String email, String name,
            String phonenumber, String address_ktp, String address_stay,
            String birth_place, String birth_day, String gender,
            String marital_status, String children, String couple_ktp_num,
            String couple_name, String couple_phone, String couple_address,
            String related_person_categorys, String related_person_name, String related_person_address,
            String related_person_phone, String job_category, String company_name,
            String company_address, String company_phone) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update customer set email=?, name=?, phonenumber=?, address_ktp=?, address_stay=?, birth_place=?, birth_day=?, gender=?, marital_status=?, children=?, couple_ktp_num=?,"
                    + " couple_name=?, couple_phone=?, couple_address=?, related_person_categorys=?, related_person_name=?, related_person_address=?, related_person_phone=?, job_category=?, company_name=?, company_address=?,"
                    + " company_phone=? WHERE ktp_num=?");
            stat.setString(1, email);
            stat.setString(2, name);
            stat.setString(3, phonenumber);
            stat.setString(4, address_ktp);
            stat.setString(5, address_stay);
            stat.setString(6, birth_place);
            stat.setString(7, birth_day);
            stat.setString(8, gender);
            stat.setString(9, marital_status);
            stat.setString(10, children);
            stat.setString(11, couple_ktp_num);
            stat.setString(12, couple_name);
            stat.setString(13, couple_phone);
            stat.setString(14, couple_address);
            stat.setString(15, related_person_categorys);
            stat.setString(16, related_person_name);
            stat.setString(17, related_person_address);
            stat.setString(18, related_person_phone);
            stat.setString(19, job_category);
            stat.setString(20, company_name);
            stat.setString(21, company_address);
            stat.setString(22, company_phone);
            stat.setString(23, ktp_num);
            stat.executeUpdate();
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());            
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap getAllPic() throws ParseException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = new HashMap();
        List list = new ArrayList();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from pic where pic_status = '1'");
            rs = stat.executeQuery();
            while (rs.next()) {
                HashMap ab = new HashMap();
                ab.put("ktp_num", rs.getString("ktp_num"));
                ab.put("pic_name", rs.getString("pic_name"));
                ab.put("pic_phone", rs.getString("pic_phone"));
                ab.put("pic_email", rs.getString("pic_email"));
                ab.put("pic_address", rs.getString("pic_address"));
                ab.put("pic_born_place", rs.getString("pic_born_place"));
                ab.put("pic_born_date", rs.getString("pic_born_date"));
                ab.put("register_date", rs.getString("register_date"));
                list.add(ab);
                ab = null;
            }
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
            result.put("list", list);
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());            
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap addPic(String ktp_num, String pic_name, String pic_phone, String pic_email, String pic_address, String pic_born_place, String pic_born_date) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();            
                stat = conn.prepareStatement("INSERT INTO pic(ktp_num, pic_name, pic_phone, pic_email, pic_address, pic_born_place, pic_born_date) VALUES (?, ?, ?, ?, ?, ?, ?)");
                stat.setString(1, ktp_num);
                stat.setString(2, pic_name);
                stat.setString(3, pic_phone);
                stat.setString(4, pic_email);
                stat.setString(5, pic_address);
                stat.setString(6, pic_born_place);
                stat.setString(7, pic_born_date);
                stat.executeUpdate();
                result.put("resp_code", "00");
                result.put("resp_desc", "success");
            
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("error_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());     
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap deletePic(String ktp_num) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update pic set pic_status=? WHERE ktp_num=?");
            stat.setString(1, "0");
            stat.setString(2, ktp_num);
            stat.executeUpdate();
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
            stat.clearParameters();
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());     
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }

    public HashMap updatePic(String ktp_num, String pic_name, String pic_phone, String pic_email, String pic_address, String pic_born_place, String pic_born_date) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        HashMap result = null;
        result = new HashMap();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("update pic set pic_name=?, pic_phone=?, pic_email=?, pic_address=?, pic_born_place=?, pic_born_date=? WHERE ktp_num=?");
            stat.setString(1, pic_name);
            stat.setString(2, pic_phone);
            stat.setString(3, pic_email);
            stat.setString(4, pic_address);
            stat.setString(5, pic_born_place);
            stat.setString(6, pic_born_date);
            stat.setString(7, ktp_num);
            stat.executeUpdate();
            stat.clearParameters();
            result.put("resp_code", "00");
            result.put("resp_desc", "success");
        } catch (SQLException e) {
            result.put("resp_code", "01");
            result.put("resp_desc", e.getMessage());
            System.out.println("error : " + e.getMessage());  
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return result;
    }
}
