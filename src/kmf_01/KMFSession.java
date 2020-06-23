/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01;

import javax.swing.JFrame;

/**
 *
 * @author samod
 */
public class KMFSession {
    private static String id_user;
    private static String username;
    private static String nama_staff;
    private static String kota;
    private static String role;
    private static Object form;


    public static String getId_user() {
        return id_user;
    }

    public static void setId_user(String id_user) {
        KMFSession.id_user = id_user;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        KMFSession.username = username;
    }

    public static String getNama_staff() {
        return nama_staff;
    }

    public static void setNama_staff(String nama_staff) {
        KMFSession.nama_staff = nama_staff;
    }

    public static String getKota() {
        return kota;
    }

    public static void setKota(String kota) {
        KMFSession.kota = kota;
    }
    
    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        KMFSession.role = role;
    }

    public static Object getForm() {
        return form;
    }

    public static void setForm(Object form) {
        KMFSession.form = form;
    }
    
    
}
