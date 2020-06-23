/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import kmf_01.DBConnect;
import kmf_01.KMF01Lib;


public class CargoManifest extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    DefaultTableModel model;
    ArrayList<String> bandara = new ArrayList<>();
    ArrayList<String> kota = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    
    private String bandara_asal = "", bandara_tujuan = "";
    private int no_Cargo = 0;
    /**
     * Creates new form Navbar
     */
    public CargoManifest() {
        initComponents();
        FormLoad();
    }
    
    private void FormLoad() {
        model = new DefaultTableModel();
         
        AutoNumber();
        tblCargo.setModel(model);
        addColumn();
        tampilBandara();
    }
    
    private void addColumn() {  
        model.addColumn("No.");
        model.addColumn("Connote");
        model.addColumn("Nama Pengirim");
        model.addColumn("Kota Asal");
        model.addColumn("Alamat Asal");
        model.addColumn("Nama Penerima");
        model.addColumn("Kota Tujuan");
        model.addColumn("Alamat Tujuan");
        model.addColumn("Berat Paket (kg)");
        model.addColumn("Jenis Pengiriman");
        
        
    }
    
    private void AutoNumber() {
        try {
            String id = "";
            int idBuku, countRow = 0;
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM CargoManifest";
            connection.result = connection.stat.executeQuery(query);
            
            while(connection.result.next()) {
                id = connection.result.getString("id_cargo");
                countRow++;
            }
            
            if(countRow > 0) {
                idBuku = Integer.parseInt(id.substring(2)) + 1;
                id = String.format ("CM%03d", idBuku);
            } else {
                id = "CM001";
            }
            connection.stat.close();
            connection.result.close();
            
            txtIDCargo.setText(id);
        } catch(Exception e) {
            System.out.println("Terjadi error saat load data buku: " + e);
        }
    }
    
    private void tampilBandara() {
        try {
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Bandara";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                bandara.add(c.result.getString("id_bandara"));
                cmbBandaraAsal.addItem(c.result.getString("nama_bandara"));
                cmbBandaraTujuan.addItem(c.result.getString("nama_bandara"));
                kota.add(c.result.getString("kota"));
            }
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data barang "  + e);
        }
    }
    
    public JPanel getPanel() {
        return CargoManifest;
    }
    
    public void ClearForm() {
        
        txtConnote.setEnabled(true);
        txtConnote.setText("");
        txtNamaPengirim.setText("");
        txtKotaAsal.setText("");
        txtAlamatAsal.setText("");
        txtNamaPenerima.setText("");
        txtKotaTujuan.setText("");
        txtAlamatTujuan.setText("");
        txtBeratPaket.setText("");
        txtJenis.setText("");
    }
    
    private void addCargo() {
        int iAsal = cmbBandaraAsal.getSelectedIndex();
        int iTujuan = cmbBandaraTujuan.getSelectedIndex();
        no_Cargo++;
        bandara_asal = bandara.get(iAsal);
        bandara_tujuan = bandara.get(iTujuan);

        Object obj[] = new Object[10];
        obj[0] = no_Cargo;
        obj[1] = txtConnote.getText();
        obj[2] = txtNamaPengirim.getText();
        obj[3] = txtKotaAsal.getText();
        obj[4] = txtAlamatAsal.getText();
        obj[5] = txtNamaPenerima.getText();
        obj[6] = txtKotaTujuan.getText();
        obj[7] = txtAlamatTujuan.getText();
        obj[8] = txtBeratPaket.getText();
        obj[9] = txtJenis.getText();
        
        model.addRow(obj);
    }
    
    private void insertDetail(String idcargo, String connote) {
        try {
            String query = "INSERT INTO DetilCargoManifest VALUES (?, ?)";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, idcargo);
            connection.pstat.setString(2, connote);

            connection.pstat.executeUpdate();
            connection.pstat.close();
            
            query = "UPDATE Paket SET status_paket=? WHERE connote=?";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, "Paket sedang menuju bandara kota asal");
            connection.pstat.setString(2, connote);

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat insert data detil cargo : " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CargoManifest = new javax.swing.JPanel();
        PageTitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Content = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Form = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        txtNamaPengirim = new javax.swing.JTextField();
        txtNamaPenerima = new javax.swing.JTextField();
        txtKotaAsal = new javax.swing.JTextField();
        txtKotaTujuan = new javax.swing.JTextField();
        txtAlamatAsal = new javax.swing.JTextField();
        txtAlamatTujuan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtBeratPaket = new javax.swing.JTextField();
        txtJenis = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtConnote = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cmbBandaraAsal = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cmbBandaraTujuan = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        Data = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIDCargo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCargo = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnKosongkan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        CargoManifest.setBackground(new java.awt.Color(225, 228, 230));
        CargoManifest.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        CargoManifest.setPreferredSize(new java.awt.Dimension(1176, 768));
        CargoManifest.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                CargoManifestHierarchyChanged(evt);
            }
        });
        CargoManifest.setLayout(new javax.swing.BoxLayout(CargoManifest, javax.swing.BoxLayout.Y_AXIS));

        PageTitle.setBackground(new java.awt.Color(225, 228, 230));
        PageTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        PageTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Cargo Manifest");

        javax.swing.GroupLayout PageTitleLayout = new javax.swing.GroupLayout(PageTitle);
        PageTitle.setLayout(PageTitleLayout);
        PageTitleLayout.setHorizontalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(940, Short.MAX_VALUE))
        );
        PageTitleLayout.setVerticalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        CargoManifest.add(PageTitle);

        Content.setBackground(new java.awt.Color(225, 228, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Content.setPreferredSize(new java.awt.Dimension(1136, 400));
        Content.setLayout(new javax.swing.BoxLayout(Content, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(400, 32767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 100));

        Form.setMaximumSize(new java.awt.Dimension(40, 32767));
        Form.setPreferredSize(new java.awt.Dimension(375, 600));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("Form Paket");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Kota Asal");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Kota Tujuan");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Alamat Asal");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Alamat Tujuan");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Berat Paket");

        btnBatal.setText("Batal");
        btnBatal.setMaximumSize(new java.awt.Dimension(73, 30));
        btnBatal.setMinimumSize(new java.awt.Dimension(0, 30));
        btnBatal.setPreferredSize(new java.awt.Dimension(100, 23));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        txtNamaPengirim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNamaPengirim.setEnabled(false);

        txtNamaPenerima.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNamaPenerima.setEnabled(false);

        txtKotaAsal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKotaAsal.setEnabled(false);

        txtKotaTujuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKotaTujuan.setEnabled(false);

        txtAlamatAsal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAlamatAsal.setEnabled(false);

        txtAlamatTujuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAlamatTujuan.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nama Pengirim");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Nama Penerima");

        txtBeratPaket.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBeratPaket.setEnabled(false);

        txtJenis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtJenis.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Jenis Pengiriman");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Connote");

        txtConnote.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtConnote.setEnabled(false);

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Bandara Asal");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jLabel13.setText("Kg");

        cmbBandaraAsal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Bandara Tujuan");

        cmbBandaraTujuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout FormLayout = new javax.swing.GroupLayout(Form);
        Form.setLayout(FormLayout);
        FormLayout.setHorizontalGroup(
            FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(FormLayout.createSequentialGroup()
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel3)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(23, 23, 23)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(FormLayout.createSequentialGroup()
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNamaPengirim)
                            .addComponent(txtNamaPenerima)
                            .addComponent(txtKotaAsal)
                            .addComponent(txtKotaTujuan)
                            .addComponent(txtAlamatAsal)
                            .addComponent(txtAlamatTujuan)
                            .addComponent(txtJenis)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormLayout.createSequentialGroup()
                                .addComponent(txtConnote)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCari))
                            .addGroup(FormLayout.createSequentialGroup()
                                .addComponent(txtBeratPaket, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel13))
                            .addComponent(cmbBandaraAsal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbBandaraTujuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        FormLayout.setVerticalGroup(
            FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormLayout.createSequentialGroup()
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FormLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtConnote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCari))
                        .addGap(24, 24, 24)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtNamaPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKotaAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtKotaTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtAlamatAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtAlamatTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtBeratPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cmbBandaraAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(cmbBandaraTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambah)))
                    .addGroup(FormLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FormLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBatal, btnTambah});

        jScrollPane1.setViewportView(Form);

        Content.add(jScrollPane1);

        jPanel5.setMaximumSize(new java.awt.Dimension(10, 32767));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(10, 548));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 656, Short.MAX_VALUE)
        );

        Content.add(jPanel5);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(686, 60));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("Data Cargo Manifest");

        jLabel9.setText("ID Cargo Manifest");

        txtIDCargo.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIDCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(txtIDCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7);

        jPanel6.setPreferredSize(new java.awt.Dimension(452, 700));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        tblCargo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblCargo);

        jPanel6.add(jScrollPane3);

        jPanel4.add(jPanel6);

        btnSimpan.setText("Simpan Cargo Manifest");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnKosongkan.setText("Kosongkan Cargo");
        btnKosongkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKosongkanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 368, Short.MAX_VALUE)
                .addComponent(btnKosongkan)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKosongkan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        jPanel4.add(jPanel1);

        javax.swing.GroupLayout DataLayout = new javax.swing.GroupLayout(Data);
        Data.setLayout(DataLayout);
        DataLayout.setHorizontalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );
        DataLayout.setVerticalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Content.add(Data);

        CargoManifest.add(Content);

        getContentPane().add(CargoManifest);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        JDialog d = new JDialog(this , "Data Paket", true);  
        d.setLayout( new FlowLayout() );  
        DialogLihatPaket dialogPaket = new DialogLihatPaket(d);
        d.add(dialogPaket.getPanel());
        d.setResizable(false);
        d.setSize(925, 422);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
        
        txtConnote.setText(dialogPaket.getPaket()[0]);
        txtNamaPengirim.setText(dialogPaket.getPaket()[1]);
        txtNamaPenerima.setText(dialogPaket.getPaket()[2]);
        txtKotaAsal.setText(dialogPaket.getPaket()[3]);
        txtKotaTujuan.setText(dialogPaket.getPaket()[4]);
        txtAlamatAsal.setText(dialogPaket.getPaket()[5]);
        txtAlamatTujuan.setText(dialogPaket.getPaket()[6]);
        txtBeratPaket.setText(dialogPaket.getPaket()[7]);
        txtJenis.setText(dialogPaket.getPaket()[8]);
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        ClearForm();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        boolean checkDuplicate = false;
        
        for(int i = 0; i < model.getRowCount(); i++) {
            if(txtConnote.getText().equals((String) model.getValueAt(i, 1))) {
                checkDuplicate = true;
            }
        }
        
        if(txtConnote.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Data paket belum dipilih", "Gagal", JOptionPane.ERROR_MESSAGE);
        } else if(checkDuplicate) {
            JOptionPane.showMessageDialog(this, "Paket telah ditambahkan di cargo", "Gagal", JOptionPane.ERROR_MESSAGE);
        } else {
            if(bandara_asal.equals("") || bandara_tujuan.equals("")) {
                addCargo();
            } else {
                if(bandara_tujuan.equals(bandara.get(cmbBandaraTujuan.getSelectedIndex()))) {
                    addCargo();
                    
                } else {
                    JOptionPane.showMessageDialog(Content, "Data paket harus dengan kota tujuan yg sama", "Gagal", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnKosongkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKosongkanActionPerformed
        DefaultTableModel dm = (DefaultTableModel)tblCargo.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        
        no_Cargo = 0;
        bandara_asal = "";
        bandara_tujuan = "";
    }//GEN-LAST:event_btnKosongkanActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
            String query = "INSERT INTO CargoManifest VALUES (?,?,?,?,?)";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, txtIDCargo.getText());
            connection.pstat.setString(2, formatter.format(new Date()));
            connection.pstat.setString(3, "Cargo sedang dikirim menuju bandara kota asal");
            connection.pstat.setString(4, bandara_asal);
            connection.pstat.setString(5, bandara_tujuan);
            
            connection.pstat.executeUpdate();
            connection.pstat.close();
            
            for (int i = 0; i < tblCargo.getRowCount(); i++) {  // Loop through the rows
                insertDetail(txtIDCargo.getText(), (String)tblCargo.getValueAt(i, 1));
                KMF01Lib.UpdateHistory((String)tblCargo.getValueAt(i, 1), "Paket sedang dikirim menuju bandara kota asal");
            }

        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat penambahan cargo manifest: " + e);
        }
        JOptionPane.showMessageDialog(this, "Penambahan Cargo Manifest berhasil");
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        AutoNumber();
        ClearForm();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void CargoManifestHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_CargoManifestHierarchyChanged
        FormLoad();
    }//GEN-LAST:event_CargoManifestHierarchyChanged

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
            java.util.logging.Logger.getLogger(CargoManifest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CargoManifest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CargoManifest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CargoManifest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CargoManifest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CargoManifest;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Data;
    private javax.swing.JPanel Form;
    private javax.swing.JPanel PageTitle;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnKosongkan;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbBandaraAsal;
    private javax.swing.JComboBox<String> cmbBandaraTujuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblCargo;
    private javax.swing.JTextField txtAlamatAsal;
    private javax.swing.JTextField txtAlamatTujuan;
    private javax.swing.JTextField txtBeratPaket;
    private javax.swing.JTextField txtConnote;
    private javax.swing.JTextField txtIDCargo;
    private javax.swing.JTextField txtJenis;
    private javax.swing.JTextField txtKotaAsal;
    private javax.swing.JTextField txtKotaTujuan;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNamaPengirim;
    // End of variables declaration//GEN-END:variables
}
