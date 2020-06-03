/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01;

import kmf_01.content.MasterBandara;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import kmf_01.content.*;

/**
 *
 * @author samod
 */
public class Template extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    CardLayout contentLayout, navigationLayout;
    private int width = 240;
    private boolean state = true;
    
    public Template() {
        initComponents();
        
        MasterBandara bandara = new MasterBandara();
        Dashboard dashboard = new Dashboard();
        PermintaanPickup permintaanPickup = new PermintaanPickup();
        PengambilanBarang pengambilanBarang = new PengambilanBarang();
        CetakConnote cetakConnote = new CetakConnote();
        
        Content.add("dashboard", dashboard.getPanel());
        Content.add("masterBandara", bandara.getPanel());
        Content.add("permintaanPickUp", permintaanPickup.getPanel());
        Content.add("pengambilanBarang", pengambilanBarang.getPanel());
        Content.add("cetakConnote", cetakConnote.getPanel());
        
        NavBar.add("customerServiceMenu", CS_Dept);
        NavBar.add("dispatchDepartmentMenu", Dispatch_Dept);
        
        contentLayout = (CardLayout) Content.getLayout();   // Membuat content layout, agar tampilan konten bisa diganti-ganti
        navigationLayout = (CardLayout) NavBar.getLayout(); // Membuat navigation layout, agar tampilan navigasi setiap role bisa diganti-ganti
        
        navigationLayout.show(NavBar, "customerServiceMenu");
        
        logout_Click(); //Memanggil fungsi button logout supaya bisa berfungsi
        
        toggleNav_Click(); //Untuk toggle Sidebar Navigation
        
        dashboard_Click();
        
        RegisterHRGAMenu(); // Mendaftarkan kumpulan fungsi menu HRGA
        
        RegisterCSMenu(); // Mendaftarkan kumpulan fungsi menu Customer service
        
        
        //Untuk toggle menu sidebar menjadi full lagi ketika user klik maximize
        this.addWindowStateListener(new WindowStateListener() {

            @Override
            public void windowStateChanged(WindowEvent we) {
                frame__windowStateChanged(we);
            }
            
            public void frame__windowStateChanged(WindowEvent e){
                if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH){
                    width = 240;
                    TopNav.setDividerLocation(width);
                    BottomNav.setDividerLocation(width);
                    jLabel2.setText("PT. KMF");
                    NavBrand.setMinimumSize(new Dimension(240, 60));
                    state = true;
                }
             }
        });
    }
    
    private void RegisterCSMenu() {
        homeMenuCS.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "dashboard");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeMenuCS.setBackground(new Color(43, 43, 43));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeMenuCS.setBackground(new Color(51,51,51));
            }
            
        }); 
        
        kelolaPelanggan.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "kelolaPelanggan");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kelolaPelanggan.setBackground(new Color(43, 43, 43));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelolaPelanggan.setBackground(new Color(51,51,51));
            }
            
        }); 
        
        permintaanPickupMenuCS.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "permintaanPickUp");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                permintaanPickupMenuCS.setBackground(new Color(43, 43, 43));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                permintaanPickupMenuCS.setBackground(new Color(51,51,51));
            }
        });
        
        pengambilanBarang.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "pengambilanBarang");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pengambilanBarang.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                pengambilanBarang.setBackground(new Color(51,51,51));
            }
        }); 
        
        cetakConnote.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "cetakConnote");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cetakConnote.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                cetakConnote.setBackground(new Color(51,51,51));
            }
        }); 
    }
    
    
    // Fungsi untuk mendaftarkan fungsi2 menu yang ada di CS
    // Ini nanti dipanggil di Constructor
    private void RegisterHRGAMenu() {
        homeMenuHRGA.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "dashboard");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeMenuHRGA.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeMenuHRGA.setBackground(new Color(51,51,51));
            }
            
        }); 
        
        masterBandara.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "masterBandara");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                masterBandara.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                masterBandara.setBackground(new Color(51,51,51));
            }
        }); 
    }
    
    // Untuk menampilkan halaman Home dari Customer Service
    private void dashboard_Click() {
        
    }
    
    private void masterBandara_Click() {
        
    }
    
    // Fungsi untuk button logout
    private void logout_Click() {
        btnLogout.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
//                setVisible(false);
                dispose();
//                System.exit(0);
                Login login = new Login();
                login.setVisible(true);
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(31, 125, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(36,136,255));
            }
            
        }); 
    }
    
    
    // Fungsi untuk toggle sidebar navigation
    private void toggleNav_Click() {
        toggleNav.addMouseListener(new MouseAdapter()  
        {  
            
            public void mouseClicked(MouseEvent e)  
            {  
                
                Timer timer = new Timer(1, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        resizeWidth(!state, 4);
                        if(state) {
                            if(width == 72) {
                                ((Timer)ae.getSource()).stop();
                                state = false;
                                jLabel2.setText("KMF");
                            }
                        } else {
                            if(width == 240) {
                                ((Timer)ae.getSource()).stop();
                                state = true;
                                jLabel2.setText("PT. KMF");
                            }
                        }
                    }
                });
                timer.start();
                
            }
            
            public void resizeWidth(boolean cond, int speed) {
                if(cond) {
                    width+=speed;
                    
                } else {
                    width-=speed;
                }
                BottomNav.setDividerLocation(width);
                TopNav.setDividerLocation(width);
            }
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toggleNav.setBackground(new Color(31, 125, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                toggleNav.setBackground(new Color(36,136,255));
            }
            
        }); 
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        Container = new javax.swing.JSplitPane();
        TopNav = new javax.swing.JSplitPane();
        NavBrand = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        LeftTopNav = new javax.swing.JPanel();
        toggleNav = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JLabel();
        BottomNav = new javax.swing.JSplitPane();
        NavBar = new javax.swing.JPanel();
        CS_Dept = new javax.swing.JPanel();
        homeMenuCS = new javax.swing.JLabel();
        permintaanPickupMenuCS = new javax.swing.JLabel();
        imageProfileCS = new javax.swing.JLabel();
        userNameCS = new javax.swing.JLabel();
        roleCS = new javax.swing.JLabel();
        pengambilanBarang = new javax.swing.JLabel();
        cetakConnote = new javax.swing.JLabel();
        kelolaPelanggan = new javax.swing.JLabel();
        HRGA_Dept = new javax.swing.JPanel();
        homeMenuHRGA = new javax.swing.JLabel();
        masterBandara = new javax.swing.JLabel();
        imageProfileHR = new javax.swing.JLabel();
        userNameHR = new javax.swing.JLabel();
        roleHR = new javax.swing.JLabel();
        masterDepartment = new javax.swing.JLabel();
        masterDepartment1 = new javax.swing.JLabel();
        masterDepartment2 = new javax.swing.JLabel();
        masterDepartment3 = new javax.swing.JLabel();
        masterDepartment4 = new javax.swing.JLabel();
        masterDepartment5 = new javax.swing.JLabel();
        masterDepartment6 = new javax.swing.JLabel();
        Dispatch_Dept = new javax.swing.JPanel();
        userNameDispatch = new javax.swing.JLabel();
        roleDispatch = new javax.swing.JLabel();
        imageProfileDispatch = new javax.swing.JLabel();
        homeMenuDispatch = new javax.swing.JLabel();
        pengambilanBarangMenuDispatch = new javax.swing.JLabel();
        Content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PT. KMF");
        setPreferredSize(new java.awt.Dimension(1400, 863));

        Container.setBorder(null);
        Container.setDividerLocation(60);
        Container.setDividerSize(0);
        Container.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        Container.setToolTipText("");
        Container.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Container.setPreferredSize(new java.awt.Dimension(804, 718));

        TopNav.setBorder(null);
        TopNav.setDividerLocation(240);
        TopNav.setDividerSize(0);

        NavBrand.setBackground(new java.awt.Color(22, 111, 217));
        NavBrand.setMaximumSize(new java.awt.Dimension(240, 32767));
        NavBrand.setPreferredSize(new java.awt.Dimension(240, 47));
        NavBrand.setLayout(new java.awt.GridLayout(1, 2, 50, 0));

        jLabel2.setBackground(new java.awt.Color(22, 111, 217));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PT. KMF");
        NavBrand.add(jLabel2);

        TopNav.setLeftComponent(NavBrand);

        LeftTopNav.setBackground(new java.awt.Color(36, 136, 255));
        LeftTopNav.setPreferredSize(new java.awt.Dimension(700, 52));
        LeftTopNav.setLayout(new javax.swing.BoxLayout(LeftTopNav, javax.swing.BoxLayout.LINE_AXIS));

        toggleNav.setBackground(new java.awt.Color(36, 136, 255));
        toggleNav.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toggleNav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/menu_24px.png"))); // NOI18N
        toggleNav.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        toggleNav.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toggleNav.setIconTextGap(10);
        toggleNav.setMaximumSize(new java.awt.Dimension(60, 60));
        toggleNav.setMinimumSize(new java.awt.Dimension(60, 60));
        toggleNav.setOpaque(true);
        toggleNav.setPreferredSize(new java.awt.Dimension(60, 60));
        LeftTopNav.add(toggleNav);

        jPanel1.setBackground(new java.awt.Color(36, 136, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        LeftTopNav.add(jPanel1);

        btnLogout.setBackground(new java.awt.Color(36, 136, 255));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/exit_24px.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.setIconTextGap(10);
        btnLogout.setMaximumSize(new java.awt.Dimension(33, 60));
        btnLogout.setOpaque(true);
        btnLogout.setPreferredSize(new java.awt.Dimension(120, 52));
        LeftTopNav.add(btnLogout);

        TopNav.setRightComponent(LeftTopNav);

        Container.setLeftComponent(TopNav);

        BottomNav.setBorder(null);
        BottomNav.setDividerLocation(240);
        BottomNav.setDividerSize(0);
        BottomNav.setLastDividerLocation(300);
        BottomNav.setMinimumSize(new java.awt.Dimension(148, 60));

        NavBar.setBackground(new java.awt.Color(51, 51, 51));
        NavBar.setPreferredSize(new java.awt.Dimension(98, 695));
        NavBar.setLayout(new java.awt.CardLayout());

        CS_Dept.setBackground(new java.awt.Color(51, 51, 51));

        homeMenuCS.setBackground(new java.awt.Color(51, 51, 51));
        homeMenuCS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        homeMenuCS.setForeground(new java.awt.Color(255, 255, 255));
        homeMenuCS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        homeMenuCS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/home_24px.png"))); // NOI18N
        homeMenuCS.setText("Home");
        homeMenuCS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        homeMenuCS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeMenuCS.setIconTextGap(25);
        homeMenuCS.setOpaque(true);

        permintaanPickupMenuCS.setBackground(new java.awt.Color(51, 51, 51));
        permintaanPickupMenuCS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        permintaanPickupMenuCS.setForeground(new java.awt.Color(255, 255, 255));
        permintaanPickupMenuCS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        permintaanPickupMenuCS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/phone_24px.png"))); // NOI18N
        permintaanPickupMenuCS.setText("Permintaan Pick Up");
        permintaanPickupMenuCS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        permintaanPickupMenuCS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        permintaanPickupMenuCS.setIconTextGap(25);
        permintaanPickupMenuCS.setOpaque(true);

        imageProfileCS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imageProfileCS.setForeground(new java.awt.Color(255, 255, 255));
        imageProfileCS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/male_user_50px.png"))); // NOI18N

        userNameCS.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userNameCS.setForeground(new java.awt.Color(255, 255, 255));
        userNameCS.setText("CS 1");

        roleCS.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        roleCS.setForeground(new java.awt.Color(255, 255, 255));
        roleCS.setText("Customer Service");

        pengambilanBarang.setBackground(new java.awt.Color(51, 51, 51));
        pengambilanBarang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pengambilanBarang.setForeground(new java.awt.Color(255, 255, 255));
        pengambilanBarang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pengambilanBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/location_24px.png"))); // NOI18N
        pengambilanBarang.setText("Pengambilan Barang");
        pengambilanBarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        pengambilanBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pengambilanBarang.setIconTextGap(25);
        pengambilanBarang.setOpaque(true);

        cetakConnote.setBackground(new java.awt.Color(51, 51, 51));
        cetakConnote.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cetakConnote.setForeground(new java.awt.Color(255, 255, 255));
        cetakConnote.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cetakConnote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/print_24px.png"))); // NOI18N
        cetakConnote.setText("Cetak Connote");
        cetakConnote.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        cetakConnote.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cetakConnote.setIconTextGap(25);
        cetakConnote.setOpaque(true);

        kelolaPelanggan.setBackground(new java.awt.Color(51, 51, 51));
        kelolaPelanggan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        kelolaPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        kelolaPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        kelolaPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/staff_24px.png"))); // NOI18N
        kelolaPelanggan.setText("Kelola Pelanggan");
        kelolaPelanggan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        kelolaPelanggan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kelolaPelanggan.setIconTextGap(25);
        kelolaPelanggan.setOpaque(true);

        javax.swing.GroupLayout CS_DeptLayout = new javax.swing.GroupLayout(CS_Dept);
        CS_Dept.setLayout(CS_DeptLayout);
        CS_DeptLayout.setHorizontalGroup(
            CS_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeMenuCS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(permintaanPickupMenuCS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CS_DeptLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageProfileCS)
                .addGap(18, 18, 18)
                .addGroup(CS_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameCS)
                    .addComponent(roleCS))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pengambilanBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cetakConnote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(kelolaPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CS_DeptLayout.setVerticalGroup(
            CS_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CS_DeptLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(CS_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CS_DeptLayout.createSequentialGroup()
                        .addComponent(userNameCS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleCS))
                    .addComponent(imageProfileCS))
                .addGap(36, 36, 36)
                .addComponent(homeMenuCS, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(kelolaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(permintaanPickupMenuCS, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pengambilanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cetakConnote, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(342, Short.MAX_VALUE))
        );

        NavBar.add(CS_Dept, "card2");

        HRGA_Dept.setBackground(new java.awt.Color(51, 51, 51));

        homeMenuHRGA.setBackground(new java.awt.Color(51, 51, 51));
        homeMenuHRGA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        homeMenuHRGA.setForeground(new java.awt.Color(255, 255, 255));
        homeMenuHRGA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        homeMenuHRGA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/home_24px.png"))); // NOI18N
        homeMenuHRGA.setText("Home");
        homeMenuHRGA.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        homeMenuHRGA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeMenuHRGA.setIconTextGap(25);
        homeMenuHRGA.setOpaque(true);

        masterBandara.setBackground(new java.awt.Color(51, 51, 51));
        masterBandara.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterBandara.setForeground(new java.awt.Color(255, 255, 255));
        masterBandara.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterBandara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/airport_24px.png"))); // NOI18N
        masterBandara.setText("Master Bandara");
        masterBandara.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterBandara.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterBandara.setIconTextGap(25);
        masterBandara.setOpaque(true);

        imageProfileHR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imageProfileHR.setForeground(new java.awt.Color(255, 255, 255));
        imageProfileHR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/male_user_50px.png"))); // NOI18N

        userNameHR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userNameHR.setForeground(new java.awt.Color(255, 255, 255));
        userNameHR.setText("HR GA 1");

        roleHR.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        roleHR.setForeground(new java.awt.Color(255, 255, 255));
        roleHR.setText("HR & GA Department");

        masterDepartment.setBackground(new java.awt.Color(51, 51, 51));
        masterDepartment.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterDepartment.setForeground(new java.awt.Color(255, 255, 255));
        masterDepartment.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterDepartment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/department_24px.png"))); // NOI18N
        masterDepartment.setText("Master Department");
        masterDepartment.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterDepartment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterDepartment.setIconTextGap(25);
        masterDepartment.setOpaque(true);

        masterDepartment1.setBackground(new java.awt.Color(51, 51, 51));
        masterDepartment1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterDepartment1.setForeground(new java.awt.Color(255, 255, 255));
        masterDepartment1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterDepartment1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/staff_24px.png"))); // NOI18N
        masterDepartment1.setText("Master Staff");
        masterDepartment1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterDepartment1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterDepartment1.setIconTextGap(25);
        masterDepartment1.setOpaque(true);

        masterDepartment2.setBackground(new java.awt.Color(51, 51, 51));
        masterDepartment2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterDepartment2.setForeground(new java.awt.Color(255, 255, 255));
        masterDepartment2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterDepartment2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/office_24px.png"))); // NOI18N
        masterDepartment2.setText("Master Kantor Pusat");
        masterDepartment2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterDepartment2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterDepartment2.setIconTextGap(25);
        masterDepartment2.setOpaque(true);

        masterDepartment3.setBackground(new java.awt.Color(51, 51, 51));
        masterDepartment3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterDepartment3.setForeground(new java.awt.Color(255, 255, 255));
        masterDepartment3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterDepartment3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/officebranch_24px.png"))); // NOI18N
        masterDepartment3.setText("Master Kantor Cabang");
        masterDepartment3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterDepartment3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterDepartment3.setIconTextGap(25);
        masterDepartment3.setOpaque(true);

        masterDepartment4.setBackground(new java.awt.Color(51, 51, 51));
        masterDepartment4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterDepartment4.setForeground(new java.awt.Color(255, 255, 255));
        masterDepartment4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterDepartment4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/warehouse_24px.png"))); // NOI18N
        masterDepartment4.setText("Master Manifested");
        masterDepartment4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterDepartment4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterDepartment4.setIconTextGap(25);
        masterDepartment4.setOpaque(true);

        masterDepartment5.setBackground(new java.awt.Color(51, 51, 51));
        masterDepartment5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterDepartment5.setForeground(new java.awt.Color(255, 255, 255));
        masterDepartment5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterDepartment5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/driver_24px.png"))); // NOI18N
        masterDepartment5.setText("Master Driver");
        masterDepartment5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterDepartment5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterDepartment5.setIconTextGap(25);
        masterDepartment5.setOpaque(true);

        masterDepartment6.setBackground(new java.awt.Color(51, 51, 51));
        masterDepartment6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        masterDepartment6.setForeground(new java.awt.Color(255, 255, 255));
        masterDepartment6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        masterDepartment6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/location_24px.png"))); // NOI18N
        masterDepartment6.setText("Master Destinasi");
        masterDepartment6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        masterDepartment6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masterDepartment6.setIconTextGap(25);
        masterDepartment6.setOpaque(true);

        javax.swing.GroupLayout HRGA_DeptLayout = new javax.swing.GroupLayout(HRGA_Dept);
        HRGA_Dept.setLayout(HRGA_DeptLayout);
        HRGA_DeptLayout.setHorizontalGroup(
            HRGA_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeMenuHRGA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(masterBandara, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(HRGA_DeptLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageProfileHR)
                .addGap(18, 18, 18)
                .addGroup(HRGA_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameHR)
                    .addComponent(roleHR))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(masterDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(masterDepartment1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(masterDepartment2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(masterDepartment3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(masterDepartment4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(masterDepartment5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(masterDepartment6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        HRGA_DeptLayout.setVerticalGroup(
            HRGA_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HRGA_DeptLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(HRGA_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HRGA_DeptLayout.createSequentialGroup()
                        .addComponent(userNameHR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleHR))
                    .addComponent(imageProfileHR))
                .addGap(36, 36, 36)
                .addComponent(homeMenuHRGA, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterBandara, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterDepartment1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterDepartment2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterDepartment3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterDepartment4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterDepartment5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(masterDepartment6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
        );

        NavBar.add(HRGA_Dept, "card2");

        Dispatch_Dept.setBackground(new java.awt.Color(51, 51, 51));

        userNameDispatch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userNameDispatch.setForeground(new java.awt.Color(255, 255, 255));
        userNameDispatch.setText("Dispatch User 1");

        roleDispatch.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        roleDispatch.setForeground(new java.awt.Color(255, 255, 255));
        roleDispatch.setText("Dispatch Department");

        imageProfileDispatch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imageProfileDispatch.setForeground(new java.awt.Color(255, 255, 255));
        imageProfileDispatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/male_user_50px.png"))); // NOI18N

        homeMenuDispatch.setBackground(new java.awt.Color(51, 51, 51));
        homeMenuDispatch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        homeMenuDispatch.setForeground(new java.awt.Color(255, 255, 255));
        homeMenuDispatch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        homeMenuDispatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/home_24px.png"))); // NOI18N
        homeMenuDispatch.setText("Home");
        homeMenuDispatch.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        homeMenuDispatch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeMenuDispatch.setIconTextGap(25);
        homeMenuDispatch.setOpaque(true);

        pengambilanBarangMenuDispatch.setBackground(new java.awt.Color(51, 51, 51));
        pengambilanBarangMenuDispatch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pengambilanBarangMenuDispatch.setForeground(new java.awt.Color(255, 255, 255));
        pengambilanBarangMenuDispatch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pengambilanBarangMenuDispatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/package_24px.png"))); // NOI18N
        pengambilanBarangMenuDispatch.setText("Pengambilan Barang");
        pengambilanBarangMenuDispatch.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        pengambilanBarangMenuDispatch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pengambilanBarangMenuDispatch.setIconTextGap(25);
        pengambilanBarangMenuDispatch.setOpaque(true);

        javax.swing.GroupLayout Dispatch_DeptLayout = new javax.swing.GroupLayout(Dispatch_Dept);
        Dispatch_Dept.setLayout(Dispatch_DeptLayout);
        Dispatch_DeptLayout.setHorizontalGroup(
            Dispatch_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dispatch_DeptLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(imageProfileDispatch)
                .addGap(18, 18, 18)
                .addGroup(Dispatch_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameDispatch)
                    .addComponent(roleDispatch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(homeMenuDispatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pengambilanBarangMenuDispatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Dispatch_DeptLayout.setVerticalGroup(
            Dispatch_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dispatch_DeptLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(Dispatch_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Dispatch_DeptLayout.createSequentialGroup()
                        .addComponent(userNameDispatch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleDispatch))
                    .addComponent(imageProfileDispatch))
                .addGap(36, 36, 36)
                .addComponent(homeMenuDispatch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pengambilanBarangMenuDispatch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        NavBar.add(Dispatch_Dept, "card3");

        BottomNav.setLeftComponent(NavBar);

        Content.setLayout(new java.awt.CardLayout());
        BottomNav.setRightComponent(Content);

        Container.setRightComponent(BottomNav);

        getContentPane().add(Container, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Template().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane BottomNav;
    private javax.swing.JPanel CS_Dept;
    private javax.swing.JSplitPane Container;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Dispatch_Dept;
    private javax.swing.JPanel HRGA_Dept;
    private javax.swing.JPanel LeftTopNav;
    private javax.swing.JPanel NavBar;
    private javax.swing.JPanel NavBrand;
    private javax.swing.JSplitPane TopNav;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel cetakConnote;
    private javax.swing.JLabel homeMenuCS;
    private javax.swing.JLabel homeMenuDispatch;
    private javax.swing.JLabel homeMenuHRGA;
    private javax.swing.JLabel imageProfileCS;
    private javax.swing.JLabel imageProfileDispatch;
    private javax.swing.JLabel imageProfileHR;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel kelolaPelanggan;
    private javax.swing.JLabel masterBandara;
    private javax.swing.JLabel masterDepartment;
    private javax.swing.JLabel masterDepartment1;
    private javax.swing.JLabel masterDepartment2;
    private javax.swing.JLabel masterDepartment3;
    private javax.swing.JLabel masterDepartment4;
    private javax.swing.JLabel masterDepartment5;
    private javax.swing.JLabel masterDepartment6;
    private javax.swing.JLabel pengambilanBarang;
    private javax.swing.JLabel pengambilanBarangMenuDispatch;
    private javax.swing.JLabel permintaanPickupMenuCS;
    private javax.swing.JLabel roleCS;
    private javax.swing.JLabel roleDispatch;
    private javax.swing.JLabel roleHR;
    private javax.swing.JLabel toggleNav;
    private javax.swing.JLabel userNameCS;
    private javax.swing.JLabel userNameDispatch;
    private javax.swing.JLabel userNameHR;
    // End of variables declaration//GEN-END:variables
}
