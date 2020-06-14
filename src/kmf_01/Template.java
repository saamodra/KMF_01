/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01;

import kmf_01.content.PengambilanBarang;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import kmf_01.content.*;

/**
 *
 * @author samod
 */
public class Template extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    DefaultTableModel model;
    private String id_permintaan;
    
    /**
     * Creates new form Dashboard
     */
    CardLayout contentLayout, navigationLayout;
    private int width = 240;
    private boolean state = true;
    
    public Template() {
        initComponents();
        
        Dashboard dashboard = new Dashboard();
        PermintaanPickup permintaanPickup = new PermintaanPickup();
        InputPaket inputPaket = new InputPaket();
        CetakTemporaryShipment cetakTemporaryShipment = new CetakTemporaryShipment();
        PengambilanBarang pengambilanBarang = new PengambilanBarang();
        CetakConnote cetakConnote = new CetakConnote();
        CargoManifest cargoManifest = new CargoManifest();
        PenerimaanCargo penerimaanCargo = new PenerimaanCargo();
        PengirimanCargo pengirimanCargo = new PengirimanCargo();
        PendataanBarangMasuk pendataanBarangMasuk = new PendataanBarangMasuk();
        CetakSMU cetakSMU = new CetakSMU();
        LihatPaket lihatPaket = new LihatPaket();
        
        Content.add("dashboard", dashboard.getPanel());
        Content.add("permintaanPickUp", permintaanPickup.getPanel());
        Content.add("pengambilanBarang", pengambilanBarang.getPanel());
        Content.add("cetakConnote", cetakConnote.getPanel());
        Content.add("tambahPaket", inputPaket.getPanel());
        Content.add("cetakTemporaryShipment", cetakTemporaryShipment.getPanel());
        Content.add("cargoManifest", cargoManifest.getPanel());
        Content.add("penerimaanCargo", penerimaanCargo.getPanel());
        Content.add("pendataanBarangMasuk", pendataanBarangMasuk.getPanel());
        Content.add("lihatPaket", lihatPaket.getPanel());
        
        NavBar.add("Customer Service", CS_Dept);
        NavBar.add("Dispatch", Dispatch_Dept);
        NavBar.add("Staff Kantor Cabang", StaffKantor);
        NavBar.add("National Transport", National_Transport_Dept);
        
        
        contentLayout = (CardLayout) Content.getLayout();   // Membuat content layout, agar tampilan konten bisa diganti-ganti
        navigationLayout = (CardLayout) NavBar.getLayout(); // Membuat navigation layout, agar tampilan navigasi setiap role bisa diganti-ganti
        
        setNavigation("Samodra", "Customer Service");
        logout_Click(); //Memanggil fungsi button logout supaya bisa berfungsi
        
        toggleNav_Click(); //Untuk toggle Sidebar Navigation
        
        
        RegisterCSMenu(); // Mendaftarkan kumpulan fungsi menu Customer service
        
        RegisterDispatchMenu();
        
        RegisterStaffKantorMenu();
        
        RegisterNTDMenu();
        
        
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
    
    public void setNavigation(String username, String navName) {
        userNameCS.setText(username);
        userNameDispatch.setText(username);
        userNameStaffKantor.setText(username);
        navigationLayout.show(NavBar, navName);
    }
    
    public void setContent(String navName) {
        navigationLayout.show(NavBar, navName);
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
        
        lihatPaketMenu.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "lihatPaket");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lihatPaketMenu.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                lihatPaketMenu.setBackground(new Color(51,51,51));
            }
        }); 
        
        
    }
    
    private void RegisterDispatchMenu() {
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
    }
    
    private void RegisterNTDMenu() {
        penerimaanCargoMenu.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "penerimaanCargo");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                penerimaanCargoMenu.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                penerimaanCargoMenu.setBackground(new Color(51,51,51));
            }
        }); 
        
    }
    
    private void RegisterStaffKantorMenu() {
        temporaryShipmentMenu.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "cetakTemporaryShipment");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                temporaryShipmentMenu.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                temporaryShipmentMenu.setBackground(new Color(51,51,51));
            }
        }); 
        
        tambahPaketMenu.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "tambahPaket");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tambahPaketMenu.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                tambahPaketMenu.setBackground(new Color(51,51,51));
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
        
        cargoManifestMenu.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "cargoManifest");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cargoManifestMenu.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                cargoManifestMenu.setBackground(new Color(51,51,51));
            }
        }); 
        
        pendataanBarangMasukMenu.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "pendataanBarangMasuk");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pendataanBarangMasukMenu.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                pendataanBarangMasukMenu.setBackground(new Color(51,51,51));
            }
        }); 
        
        lihatPaketSKMenu.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
               contentLayout.show(Content, "lihatPaket");
            }  
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lihatPaketSKMenu.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                lihatPaketSKMenu.setBackground(new Color(51,51,51));
            }
        }); 
    }
    
    
    // Fungsi untuk button logout
    private void logout_Click() {
        btnLogout.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent e)  
            {  
                dispose();
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
        lihatPaketMenu = new javax.swing.JLabel();
        Dispatch_Dept = new javax.swing.JPanel();
        userNameDispatch = new javax.swing.JLabel();
        roleDispatch = new javax.swing.JLabel();
        imageProfileDispatch = new javax.swing.JLabel();
        homeMenuDispatch = new javax.swing.JLabel();
        pengambilanBarang = new javax.swing.JLabel();
        StaffKantor = new javax.swing.JPanel();
        userNameStaffKantor = new javax.swing.JLabel();
        roleStaffKantor = new javax.swing.JLabel();
        imageProfileDispatch1 = new javax.swing.JLabel();
        homeMenuStaffKantor = new javax.swing.JLabel();
        temporaryShipmentMenu = new javax.swing.JLabel();
        cetakConnote = new javax.swing.JLabel();
        tambahPaketMenu = new javax.swing.JLabel();
        cargoManifestMenu = new javax.swing.JLabel();
        pendataanBarangMasukMenu = new javax.swing.JLabel();
        pengirimanBarangMenu = new javax.swing.JLabel();
        lihatPaketSKMenu = new javax.swing.JLabel();
        National_Transport_Dept = new javax.swing.JPanel();
        homeMenuCS1 = new javax.swing.JLabel();
        cetakSMUMenu = new javax.swing.JLabel();
        imageProfileCS1 = new javax.swing.JLabel();
        userNameCS1 = new javax.swing.JLabel();
        roleCS1 = new javax.swing.JLabel();
        pengirimanCargoMenu = new javax.swing.JLabel();
        penerimaanCargoMenu = new javax.swing.JLabel();
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

        lihatPaketMenu.setBackground(new java.awt.Color(51, 51, 51));
        lihatPaketMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lihatPaketMenu.setForeground(new java.awt.Color(255, 255, 255));
        lihatPaketMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lihatPaketMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/package_24px.png"))); // NOI18N
        lihatPaketMenu.setText("Lihat Daftar Paket");
        lihatPaketMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        lihatPaketMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lihatPaketMenu.setIconTextGap(25);
        lihatPaketMenu.setOpaque(true);

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
            .addComponent(lihatPaketMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(permintaanPickupMenuCS, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lihatPaketMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(432, Short.MAX_VALUE))
        );

        NavBar.add(CS_Dept, "card2");

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
            .addComponent(pengambilanBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(pengambilanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        NavBar.add(Dispatch_Dept, "card3");

        StaffKantor.setBackground(new java.awt.Color(51, 51, 51));

        userNameStaffKantor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userNameStaffKantor.setForeground(new java.awt.Color(255, 255, 255));
        userNameStaffKantor.setText("Staff Kantor Cabang 1");

        roleStaffKantor.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        roleStaffKantor.setForeground(new java.awt.Color(255, 255, 255));
        roleStaffKantor.setText("Staff Kantor Cabang");

        imageProfileDispatch1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imageProfileDispatch1.setForeground(new java.awt.Color(255, 255, 255));
        imageProfileDispatch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/male_user_50px.png"))); // NOI18N

        homeMenuStaffKantor.setBackground(new java.awt.Color(51, 51, 51));
        homeMenuStaffKantor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        homeMenuStaffKantor.setForeground(new java.awt.Color(255, 255, 255));
        homeMenuStaffKantor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        homeMenuStaffKantor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/home_24px.png"))); // NOI18N
        homeMenuStaffKantor.setText("Home");
        homeMenuStaffKantor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        homeMenuStaffKantor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeMenuStaffKantor.setIconTextGap(25);
        homeMenuStaffKantor.setOpaque(true);

        temporaryShipmentMenu.setBackground(new java.awt.Color(51, 51, 51));
        temporaryShipmentMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        temporaryShipmentMenu.setForeground(new java.awt.Color(255, 255, 255));
        temporaryShipmentMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        temporaryShipmentMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/note_24px.png"))); // NOI18N
        temporaryShipmentMenu.setText("Temporary Shipment");
        temporaryShipmentMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        temporaryShipmentMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        temporaryShipmentMenu.setIconTextGap(25);
        temporaryShipmentMenu.setOpaque(true);

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

        tambahPaketMenu.setBackground(new java.awt.Color(51, 51, 51));
        tambahPaketMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tambahPaketMenu.setForeground(new java.awt.Color(255, 255, 255));
        tambahPaketMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tambahPaketMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/package_24px.png"))); // NOI18N
        tambahPaketMenu.setText("Tambah Paket");
        tambahPaketMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        tambahPaketMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tambahPaketMenu.setIconTextGap(25);
        tambahPaketMenu.setOpaque(true);

        cargoManifestMenu.setBackground(new java.awt.Color(51, 51, 51));
        cargoManifestMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cargoManifestMenu.setForeground(new java.awt.Color(255, 255, 255));
        cargoManifestMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cargoManifestMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/shipping_container_24px.png"))); // NOI18N
        cargoManifestMenu.setText("Cargo Manifest");
        cargoManifestMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        cargoManifestMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cargoManifestMenu.setIconTextGap(25);
        cargoManifestMenu.setOpaque(true);

        pendataanBarangMasukMenu.setBackground(new java.awt.Color(51, 51, 51));
        pendataanBarangMasukMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pendataanBarangMasukMenu.setForeground(new java.awt.Color(255, 255, 255));
        pendataanBarangMasukMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pendataanBarangMasukMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/down_left_24px.png"))); // NOI18N
        pendataanBarangMasukMenu.setText("Pendataan Barang Masuk");
        pendataanBarangMasukMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        pendataanBarangMasukMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pendataanBarangMasukMenu.setIconTextGap(25);
        pendataanBarangMasukMenu.setOpaque(true);

        pengirimanBarangMenu.setBackground(new java.awt.Color(51, 51, 51));
        pengirimanBarangMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pengirimanBarangMenu.setForeground(new java.awt.Color(255, 255, 255));
        pengirimanBarangMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pengirimanBarangMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/up_right_24px.png"))); // NOI18N
        pengirimanBarangMenu.setText("Pengiriman Barang");
        pengirimanBarangMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        pengirimanBarangMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pengirimanBarangMenu.setIconTextGap(25);
        pengirimanBarangMenu.setOpaque(true);

        lihatPaketSKMenu.setBackground(new java.awt.Color(51, 51, 51));
        lihatPaketSKMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lihatPaketSKMenu.setForeground(new java.awt.Color(255, 255, 255));
        lihatPaketSKMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lihatPaketSKMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/package_24px.png"))); // NOI18N
        lihatPaketSKMenu.setText("Lihat Daftar Paket");
        lihatPaketSKMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        lihatPaketSKMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lihatPaketSKMenu.setIconTextGap(25);
        lihatPaketSKMenu.setOpaque(true);

        javax.swing.GroupLayout StaffKantorLayout = new javax.swing.GroupLayout(StaffKantor);
        StaffKantor.setLayout(StaffKantorLayout);
        StaffKantorLayout.setHorizontalGroup(
            StaffKantorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StaffKantorLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(imageProfileDispatch1)
                .addGap(18, 18, 18)
                .addGroup(StaffKantorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameStaffKantor)
                    .addComponent(roleStaffKantor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(homeMenuStaffKantor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(temporaryShipmentMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cetakConnote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tambahPaketMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cargoManifestMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pendataanBarangMasukMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pengirimanBarangMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lihatPaketSKMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        StaffKantorLayout.setVerticalGroup(
            StaffKantorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StaffKantorLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(StaffKantorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StaffKantorLayout.createSequentialGroup()
                        .addComponent(userNameStaffKantor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleStaffKantor))
                    .addComponent(imageProfileDispatch1))
                .addGap(36, 36, 36)
                .addComponent(homeMenuStaffKantor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(temporaryShipmentMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tambahPaketMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cetakConnote, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cargoManifestMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pendataanBarangMasukMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pengirimanBarangMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lihatPaketSKMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        NavBar.add(StaffKantor, "card3");

        National_Transport_Dept.setBackground(new java.awt.Color(51, 51, 51));

        homeMenuCS1.setBackground(new java.awt.Color(51, 51, 51));
        homeMenuCS1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        homeMenuCS1.setForeground(new java.awt.Color(255, 255, 255));
        homeMenuCS1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        homeMenuCS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/home_24px.png"))); // NOI18N
        homeMenuCS1.setText("Home");
        homeMenuCS1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        homeMenuCS1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeMenuCS1.setIconTextGap(25);
        homeMenuCS1.setOpaque(true);

        cetakSMUMenu.setBackground(new java.awt.Color(51, 51, 51));
        cetakSMUMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cetakSMUMenu.setForeground(new java.awt.Color(255, 255, 255));
        cetakSMUMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cetakSMUMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/print_24px.png"))); // NOI18N
        cetakSMUMenu.setText("Cetak SMU");
        cetakSMUMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        cetakSMUMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cetakSMUMenu.setIconTextGap(25);
        cetakSMUMenu.setOpaque(true);

        imageProfileCS1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imageProfileCS1.setForeground(new java.awt.Color(255, 255, 255));
        imageProfileCS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/male_user_50px.png"))); // NOI18N

        userNameCS1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userNameCS1.setForeground(new java.awt.Color(255, 255, 255));
        userNameCS1.setText("NTD 1");

        roleCS1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        roleCS1.setForeground(new java.awt.Color(255, 255, 255));
        roleCS1.setText("National Transport Dept.");

        pengirimanCargoMenu.setBackground(new java.awt.Color(51, 51, 51));
        pengirimanCargoMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pengirimanCargoMenu.setForeground(new java.awt.Color(255, 255, 255));
        pengirimanCargoMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pengirimanCargoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/up_right_24px.png"))); // NOI18N
        pengirimanCargoMenu.setText("Pengiriman Cargo");
        pengirimanCargoMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        pengirimanCargoMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pengirimanCargoMenu.setIconTextGap(25);
        pengirimanCargoMenu.setOpaque(true);

        penerimaanCargoMenu.setBackground(new java.awt.Color(51, 51, 51));
        penerimaanCargoMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        penerimaanCargoMenu.setForeground(new java.awt.Color(255, 255, 255));
        penerimaanCargoMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        penerimaanCargoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kmf_01/images/down_left_24px.png"))); // NOI18N
        penerimaanCargoMenu.setText("Penerimaan Cargo");
        penerimaanCargoMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        penerimaanCargoMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penerimaanCargoMenu.setIconTextGap(25);
        penerimaanCargoMenu.setOpaque(true);

        javax.swing.GroupLayout National_Transport_DeptLayout = new javax.swing.GroupLayout(National_Transport_Dept);
        National_Transport_Dept.setLayout(National_Transport_DeptLayout);
        National_Transport_DeptLayout.setHorizontalGroup(
            National_Transport_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeMenuCS1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cetakSMUMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(National_Transport_DeptLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageProfileCS1)
                .addGap(18, 18, 18)
                .addGroup(National_Transport_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameCS1)
                    .addComponent(roleCS1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pengirimanCargoMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(penerimaanCargoMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        National_Transport_DeptLayout.setVerticalGroup(
            National_Transport_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(National_Transport_DeptLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(National_Transport_DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(National_Transport_DeptLayout.createSequentialGroup()
                        .addComponent(userNameCS1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleCS1))
                    .addComponent(imageProfileCS1))
                .addGap(36, 36, 36)
                .addComponent(homeMenuCS1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cetakSMUMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pengirimanCargoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(penerimaanCargoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(387, Short.MAX_VALUE))
        );

        NavBar.add(National_Transport_Dept, "card2");

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
                if ("Windows".equals(info.getName())) {
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
    private javax.swing.JPanel LeftTopNav;
    private javax.swing.JPanel National_Transport_Dept;
    private javax.swing.JPanel NavBar;
    private javax.swing.JPanel NavBrand;
    private javax.swing.JPanel StaffKantor;
    private javax.swing.JSplitPane TopNav;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel cargoManifestMenu;
    private javax.swing.JLabel cetakConnote;
    private javax.swing.JLabel cetakSMUMenu;
    private javax.swing.JLabel homeMenuCS;
    private javax.swing.JLabel homeMenuCS1;
    private javax.swing.JLabel homeMenuDispatch;
    private javax.swing.JLabel homeMenuStaffKantor;
    private javax.swing.JLabel imageProfileCS;
    private javax.swing.JLabel imageProfileCS1;
    private javax.swing.JLabel imageProfileDispatch;
    private javax.swing.JLabel imageProfileDispatch1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lihatPaketMenu;
    private javax.swing.JLabel lihatPaketSKMenu;
    private javax.swing.JLabel pendataanBarangMasukMenu;
    private javax.swing.JLabel penerimaanCargoMenu;
    private javax.swing.JLabel pengambilanBarang;
    private javax.swing.JLabel pengirimanBarangMenu;
    private javax.swing.JLabel pengirimanCargoMenu;
    private javax.swing.JLabel permintaanPickupMenuCS;
    private javax.swing.JLabel roleCS;
    private javax.swing.JLabel roleCS1;
    private javax.swing.JLabel roleDispatch;
    private javax.swing.JLabel roleStaffKantor;
    private javax.swing.JLabel tambahPaketMenu;
    private javax.swing.JLabel temporaryShipmentMenu;
    private javax.swing.JLabel toggleNav;
    private javax.swing.JLabel userNameCS;
    private javax.swing.JLabel userNameCS1;
    private javax.swing.JLabel userNameDispatch;
    private javax.swing.JLabel userNameStaffKantor;
    // End of variables declaration//GEN-END:variables
}
