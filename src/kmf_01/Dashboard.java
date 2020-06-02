/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01;

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

/**
 *
 * @author samod
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    CardLayout contentLayout, navigationLayout;
    private int width = 240;
    private boolean state = true;
    
    public Dashboard() {
        initComponents();
        
        Content.add("dashboard", Dashboard);
        Content.add("permintaan_pickup", permintaanPickUpPage);
        
        NavBar.add("customerServiceMenu", CustomerService);
        NavBar.add("dispatchDepartmentMenu", DispatchDepartment);
        
        contentLayout = (CardLayout) Content.getLayout();   // Membuat content layout, agar tampilan konten bisa diganti-ganti
        navigationLayout = (CardLayout) NavBar.getLayout(); // Membuat navigation layout, agar tampilan navigasi setiap role bisa diganti-ganti
        
        
        logout_Click(); //Memanggil fungsi button logout supaya bisa berfungsi
        
        toggleNav_Click(); //Untuk toggle Sidebar Navigation
        
        dashboard_Click();
        
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
    
    
    // Fungsi untuk mendaftarkan fungsi2 menu yang ada di CS
    // Ini nanti dipanggil di Constructor
    private void RegisterCSMenu() {
    }
    
    // Untuk menampilkan halaman Home dari Customer Service
    private void dashboard_Click() {
        homeMenuCS.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "dashboard");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeMenuCS.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeMenuCS.setBackground(new Color(51,51,51));
            }
            
        }); 
    }
    
    
    // Fungsi untuk button logout
    private void logout_Click() {
        btnLogout.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               // Close this window, and show the login page
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
        CustomerService = new javax.swing.JPanel();
        homeMenuCS = new javax.swing.JLabel();
        permintaanPickupMenuCS = new javax.swing.JLabel();
        imageProfileCS = new javax.swing.JLabel();
        userNameCS = new javax.swing.JLabel();
        roleCS = new javax.swing.JLabel();
        DispatchDepartment = new javax.swing.JPanel();
        userNameDispatch = new javax.swing.JLabel();
        roleDispatch = new javax.swing.JLabel();
        imageProfileDispatch = new javax.swing.JLabel();
        homeMenuDispatch = new javax.swing.JLabel();
        pengambilanBarangMenuDispatch = new javax.swing.JLabel();
        Content = new javax.swing.JPanel();
        Dashboard = new javax.swing.JPanel();
        DashboardTitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ReportSummary = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        DContent = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        permintaanPickUpPage = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

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

        CustomerService.setBackground(new java.awt.Color(51, 51, 51));

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

        javax.swing.GroupLayout CustomerServiceLayout = new javax.swing.GroupLayout(CustomerService);
        CustomerService.setLayout(CustomerServiceLayout);
        CustomerServiceLayout.setHorizontalGroup(
            CustomerServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeMenuCS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(permintaanPickupMenuCS, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addGroup(CustomerServiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageProfileCS)
                .addGap(18, 18, 18)
                .addGroup(CustomerServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameCS)
                    .addComponent(roleCS))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CustomerServiceLayout.setVerticalGroup(
            CustomerServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerServiceLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(CustomerServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CustomerServiceLayout.createSequentialGroup()
                        .addComponent(userNameCS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleCS))
                    .addComponent(imageProfileCS))
                .addGap(36, 36, 36)
                .addComponent(homeMenuCS, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(permintaanPickupMenuCS, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(432, Short.MAX_VALUE))
        );

        NavBar.add(CustomerService, "card2");

        DispatchDepartment.setBackground(new java.awt.Color(51, 51, 51));

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

        javax.swing.GroupLayout DispatchDepartmentLayout = new javax.swing.GroupLayout(DispatchDepartment);
        DispatchDepartment.setLayout(DispatchDepartmentLayout);
        DispatchDepartmentLayout.setHorizontalGroup(
            DispatchDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DispatchDepartmentLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(imageProfileDispatch)
                .addGap(18, 18, 18)
                .addGroup(DispatchDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameDispatch)
                    .addComponent(roleDispatch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(homeMenuDispatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pengambilanBarangMenuDispatch, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        DispatchDepartmentLayout.setVerticalGroup(
            DispatchDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DispatchDepartmentLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(DispatchDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DispatchDepartmentLayout.createSequentialGroup()
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

        NavBar.add(DispatchDepartment, "card3");

        BottomNav.setLeftComponent(NavBar);

        Content.setLayout(new java.awt.CardLayout());

        Dashboard.setBackground(new java.awt.Color(225, 228, 230));
        Dashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        Dashboard.setLayout(new javax.swing.BoxLayout(Dashboard, javax.swing.BoxLayout.Y_AXIS));

        DashboardTitle.setBackground(new java.awt.Color(225, 228, 230));
        DashboardTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        DashboardTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Dashboard");

        javax.swing.GroupLayout DashboardTitleLayout = new javax.swing.GroupLayout(DashboardTitle);
        DashboardTitle.setLayout(DashboardTitleLayout);
        DashboardTitleLayout.setHorizontalGroup(
            DashboardTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(379, Short.MAX_VALUE))
        );
        DashboardTitleLayout.setVerticalGroup(
            DashboardTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        Dashboard.add(DashboardTitle);

        ReportSummary.setBackground(new java.awt.Color(225, 228, 230));
        ReportSummary.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ReportSummary.setMaximumSize(new java.awt.Dimension(32767, 104));
        ReportSummary.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBackground(new java.awt.Color(225, 228, 230));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBackground(new java.awt.Color(23, 162, 184));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4);

        ReportSummary.add(jPanel3);

        jPanel5.setBackground(new java.awt.Color(225, 228, 230));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jPanel10.setBackground(new java.awt.Color(40, 167, 69));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel10);

        ReportSummary.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(225, 228, 230));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 193, 7));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel2);

        ReportSummary.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(225, 228, 230));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jPanel9.setBackground(new java.awt.Color(220, 53, 69));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel9);

        ReportSummary.add(jPanel7);

        Dashboard.add(ReportSummary);

        DContent.setBackground(new java.awt.Color(225, 228, 230));
        DContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        DContent.setPreferredSize(new java.awt.Dimension(1136, 400));
        DContent.setLayout(new javax.swing.BoxLayout(DContent, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 409, Short.MAX_VALUE)
        );

        DContent.add(jPanel11);

        Dashboard.add(DContent);

        Content.add(Dashboard, "card2");

        permintaanPickUpPage.setBackground(new java.awt.Color(238, 245, 248));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Permintaan Pick Up");

        javax.swing.GroupLayout permintaanPickUpPageLayout = new javax.swing.GroupLayout(permintaanPickUpPage);
        permintaanPickUpPage.setLayout(permintaanPickUpPageLayout);
        permintaanPickUpPageLayout.setHorizontalGroup(
            permintaanPickUpPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(permintaanPickUpPageLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addContainerGap(361, Short.MAX_VALUE))
        );
        permintaanPickUpPageLayout.setVerticalGroup(
            permintaanPickUpPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(permintaanPickUpPageLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addContainerGap(609, Short.MAX_VALUE))
        );

        Content.add(permintaanPickUpPage, "card3");

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane BottomNav;
    private javax.swing.JSplitPane Container;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel CustomerService;
    private javax.swing.JPanel DContent;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel DashboardTitle;
    private javax.swing.JPanel DispatchDepartment;
    private javax.swing.JPanel LeftTopNav;
    private javax.swing.JPanel NavBar;
    private javax.swing.JPanel NavBrand;
    private javax.swing.JPanel ReportSummary;
    private javax.swing.JSplitPane TopNav;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel homeMenuCS;
    private javax.swing.JLabel homeMenuDispatch;
    private javax.swing.JLabel imageProfileCS;
    private javax.swing.JLabel imageProfileDispatch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel pengambilanBarangMenuDispatch;
    private javax.swing.JPanel permintaanPickUpPage;
    private javax.swing.JLabel permintaanPickupMenuCS;
    private javax.swing.JLabel roleCS;
    private javax.swing.JLabel roleDispatch;
    private javax.swing.JLabel toggleNav;
    private javax.swing.JLabel userNameCS;
    private javax.swing.JLabel userNameDispatch;
    // End of variables declaration//GEN-END:variables
}
