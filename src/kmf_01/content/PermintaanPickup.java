/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import java.awt.FlowLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import kmf_01.DBConnect;
import kmf_01.KMFSession;

public class PermintaanPickup extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    ArrayList<String> id_kota = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    boolean foundPelanggan = false;
    String idpelanggan;
    /**
     * Creates new form PermintaanPickup
     */
    public PermintaanPickup() {
        initComponents();
        
        FormLoad();
    }
    
    private void FormLoad() {
        
        AutoNumber();
        listKota();
        txtTglPermintaan.setText(dateFormat.format(new java.util.Date()));
    }
        
    
    public JPanel getPanel() {
        return PermintaanPickup;
    }
    
    private void listKota() {
        try {
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM KantorCabangKota";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                cmbAsal.addItem(c.result.getString("kota"));
                cmbTujuan.addItem(c.result.getString("kota"));
                id_kota.add(c.result.getString("id_kantorcabang"));
            }
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data list kota "  + e);
        }
    }
    
    
    private void AutoNumber() {
        try {
            String id = "";
            int idBuku, countRow = 0;
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM PermintaanPengiriman";
            connection.result = connection.stat.executeQuery(query);
            
            while(connection.result.next()) {
                id = connection.result.getString("id_permintaanpengiriman");
                countRow++;
            }
            
            if(countRow > 0) {
                idBuku = Integer.parseInt(id.substring(2)) + 1;
                id = String.format ("PP%03d", idBuku);
            } else {
                id = "PP001";
            }
            connection.stat.close();
            connection.result.close();
            
            txtID.setText(id);
        } catch(Exception e) {
            System.out.println("Terjadi error saat load permintaan pengiriman: " + e);
        }
    }
    
    
    private String IdPelanggan() {
        String id = "";
        try {
            int idBuku, countRow = 0;
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM Pelanggan";
            connection.result = connection.stat.executeQuery(query);
            
            while(connection.result.next()) {
                id = connection.result.getString("id_pelanggan");
                countRow++;
            }
            
            if(countRow > 0) {
                idBuku = Integer.parseInt(id.substring(2)) + 1;
                id = String.format ("PG%03d", idBuku);
            } else {
                id = "PG001";
            }
            connection.stat.close();
            connection.result.close();
            
            txtID.setText(id);
        } catch(Exception e) {
            System.out.println("Terjadi error saat load pelanggan: " + e);
        }
         
         return id;
    }
    
    private boolean pelangganIsUnique(String nama, String hp) {
        boolean result = false;
        try {
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Pelanggan WHERE nama_pelanggan = '" + nama + "' AND nohp_pelanggan = '" + hp + "'";
            c.result = c.stat.executeQuery(sql);
            int countRow = 0;
            
            while(c.result.next()) {
                countRow++;
            }
            
            if(countRow == 0) {
                result = true;
            } 
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data Destinasi "  + e);
        }
        
        return result;
    }
    
    private double getDestinationPrice(String k1, String k2, String jenis) {
        double price=0;
        
        try {
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Destinasi WHERE ((kota_1='" + k1 + "' AND kota_2='" + k2 + "') OR "
                    + "(kota_1='" + k2 + "' AND kota_2='" + k1 + "')) AND jenis_pengiriman='" + jenis + "'";
            c.result = c.stat.executeQuery(sql);
            
            
            while(c.result.next()) {
                price = Double.parseDouble(c.result.getString("harga"));
            }
            c.stat.close();
            c.result.close();
        } catch(Exception e) {
            System.out.println("Terjadi error saat load data Destinasi "  + e);
        }
        
        return price;
    }
    
    private boolean validateAll() {
        return !(txtNamaPelanggan.getText().equals("") || txtNamaPenerima.getText().equals("") || txtNoTelpPelanggan.getText().equals("")
                || txtNoTelpPenerima.getText().equals("") || txtAlamatAsal.getText().equals("") || txtAlamatTujuan.getText().equals("")
                || txtBerat.getValue().toString().equals("0"));
        
    }
    
    private void calculateTotalPrice() {
        double totalPrice =  getDestinationPrice(id_kota.get(cmbAsal.getSelectedIndex()), id_kota.get(cmbTujuan.getSelectedIndex()), (String)cmbJenisPengiriman.getSelectedItem());
        double berat = Double.parseDouble(txtBerat.getValue().toString());

        txtBiaya.setText(String.valueOf(berat * totalPrice)); 
    }
    
    private void ClearForm() {
        FormLoad();
        newPengirim();
        cmbAsal.setSelectedIndex(0);
        cmbTujuan.setSelectedIndex(0);
        cmbJenisPengiriman.setSelectedIndex(0);
        txtNamaPenerima.setText("");
        txtAlamatAsal.setText("");
        txtAlamatTujuan.setText("");
        txtNoTelpPenerima.setText("");
        txtBerat.setValue(0);
        txtBiaya.setText("");
    }
    
    private void newPengirim() {
        txtNamaPelanggan.setEnabled(true);
        txtNoTelpPelanggan.setEnabled(true);
        txtNamaPelanggan.setText("");
        txtNoTelpPelanggan.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PermintaanPickup = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtTglPermintaan = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNamaPelanggan = new javax.swing.JTextField();
        cmbAsal = new javax.swing.JComboBox<>();
        txtAlamatAsal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNoTelpPelanggan = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnBaru = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNamaPenerima = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbTujuan = new javax.swing.JComboBox<>();
        txtAlamatTujuan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNoTelpPenerima = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbJenisPengiriman = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtBiaya = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtBerat = new javax.swing.JSpinner();
        jPanel11 = new javax.swing.JPanel();
        btnRequest = new javax.swing.JButton();
        btnCek = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        PermintaanPickup.setBackground(new java.awt.Color(225, 228, 230));
        PermintaanPickup.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 50, 50, 50));
        PermintaanPickup.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                PermintaanPickupHierarchyChanged(evt);
            }
        });
        PermintaanPickup.setLayout(new javax.swing.BoxLayout(PermintaanPickup, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 100));
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel3.setPreferredSize(new java.awt.Dimension(806, 80));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Permintaan Pengiriman");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 404, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 403, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel8.setMaximumSize(new java.awt.Dimension(32767, 70));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("ID Permintaan Pickup");

        txtID.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtID.setEnabled(false);

        txtTglPermintaan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtTglPermintaan.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel16.setText("Tgl. Permintaan");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(33, 33, 33)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(33, 33, 33)
                .addComponent(txtTglPermintaan, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txtTglPermintaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel6.setMaximumSize(new java.awt.Dimension(32767, 220));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pengirim"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Nama Pelanggan");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Kota Asal");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Alamat Asal");

        txtNamaPelanggan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        cmbAsal.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cmbAsal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAsalItemStateChanged(evt);
            }
        });
        cmbAsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAsalActionPerformed(evt);
            }
        });

        txtAlamatAsal.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel13.setText("No. Telepon");

        txtNoTelpPelanggan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnBaru.setText("Baru");
        btnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel13))
                .addGap(42, 42, 42)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cmbAsal, 0, 233, Short.MAX_VALUE)
                    .addComponent(txtAlamatAsal)
                    .addComponent(txtNoTelpPelanggan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBaru)))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNoTelpPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmbAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtAlamatAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBaru, btnCari, txtNamaPelanggan});

        jPanel6.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Penerima"));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setText("Nama Penerima");

        txtNamaPenerima.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("Kota Tujuan");

        cmbTujuan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cmbTujuan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTujuanItemStateChanged(evt);
            }
        });
        cmbTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTujuanActionPerformed(evt);
            }
        });

        txtAlamatTujuan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Alamat Tujuan");

        txtNoTelpPenerima.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel14.setText("No. Telepon");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNamaPenerima)
                    .addComponent(txtNoTelpPenerima)
                    .addComponent(cmbTujuan, 0, 276, Short.MAX_VALUE)
                    .addComponent(txtAlamatTujuan))
                .addGap(69, 69, 69))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtNoTelpPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtAlamatTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel5);

        jPanel2.add(jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail Paket"));
        jPanel9.setMaximumSize(new java.awt.Dimension(32767, 80));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Berat Paket");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("KG");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setText("Jenis Pengiriman");

        cmbJenisPengiriman.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cmbJenisPengiriman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reguler", "Express" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setText("Biaya Pengiriman");

        txtBiaya.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtBiaya.setText("0");
        txtBiaya.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel15.setText("Rp.");

        txtBerat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBerat.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtBeratStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel8)
                .addGap(32, 32, 32)
                .addComponent(txtBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(79, 79, 79)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(cmbJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(43, 43, 43)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(cmbJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtBerat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel9);

        jPanel2.add(jPanel7);

        btnRequest.setText("Request");
        btnRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestActionPerformed(evt);
            }
        });

        btnCek.setText("Cek Harga");
        btnCek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(894, Short.MAX_VALUE)
                .addComponent(btnCek)
                .addGap(18, 18, 18)
                .addComponent(btnRequest)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCek, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel11);

        jPanel1.add(jPanel2);

        PermintaanPickup.add(jPanel1);

        getContentPane().add(PermintaanPickup);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTujuanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTujuanItemStateChanged
        
    }//GEN-LAST:event_cmbTujuanItemStateChanged

    private void txtBeratStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtBeratStateChanged
        
    }//GEN-LAST:event_txtBeratStateChanged

    private void cmbAsalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAsalItemStateChanged

    }//GEN-LAST:event_cmbAsalItemStateChanged

    private void cmbAsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAsalActionPerformed

    }//GEN-LAST:event_cmbAsalActionPerformed

    private void cmbTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTujuanActionPerformed
        
    }//GEN-LAST:event_cmbTujuanActionPerformed

    private void btnCekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekActionPerformed
        calculateTotalPrice();
    }//GEN-LAST:event_btnCekActionPerformed

    private void btnRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestActionPerformed
        if(!validateAll()) {
            JOptionPane.showMessageDialog(this, "Mohon isi data dengan lengkap", "Gagal", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String query;
                
                if(pelangganIsUnique(txtNamaPelanggan.getText(), txtNoTelpPelanggan.getText())) {
                    idpelanggan = IdPelanggan();
                    query = "INSERT INTO Pelanggan VALUES (?,?,?,?)";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idpelanggan);
                    connection.pstat.setString(2, txtNamaPelanggan.getText());
                    connection.pstat.setString(3, txtAlamatAsal.getText());
                    connection.pstat.setString(4, txtNoTelpPelanggan.getText());
                    connection.pstat.executeUpdate();
                    connection.pstat.close();
                }
                
                query = "INSERT INTO PermintaanPengiriman (id_permintaanpengiriman, kota_asal, alamat_asal, "
                        + "telepon_penerima, nama_penerima, kota_tujuan, jenis_pengiriman, alamat_tujuan, berat_paket, biaya_kirim,"
                        + " id_pelanggan, id_staff) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                connection.pstat = connection.conn.prepareStatement(query);
                connection.pstat.setString(1, txtID.getText());
                connection.pstat.setString(2, (String)cmbAsal.getSelectedItem());
                connection.pstat.setString(3, txtAlamatAsal.getText());
                connection.pstat.setString(4, txtNoTelpPenerima.getText());
                connection.pstat.setString(5, txtNamaPenerima.getText());
                connection.pstat.setString(6, (String)cmbTujuan.getSelectedItem());
                connection.pstat.setString(7, (String)cmbJenisPengiriman.getSelectedItem());
                connection.pstat.setString(8, txtAlamatTujuan.getText());
                connection.pstat.setString(9, txtBerat.getValue().toString());
                connection.pstat.setString(10, txtBiaya.getText());
                connection.pstat.setString(11, idpelanggan);
                connection.pstat.setString(12, KMFSession.getId_user());
                
                
                connection.pstat.executeUpdate();
                connection.pstat.close();

            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat tambah permintaan pickup : " + e);
            }
            ClearForm();
            JOptionPane.showMessageDialog(this, "Tambah Permintaan Pickup berhasil");
        }
    }//GEN-LAST:event_btnRequestActionPerformed

    private void PermintaanPickupHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_PermintaanPickupHierarchyChanged
        ClearForm();
    }//GEN-LAST:event_PermintaanPickupHierarchyChanged

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        JDialog d = new JDialog(this , "Data Pelanggan", true);  
        d.setLayout( new FlowLayout() );  
        LihatPelanggan lPelanggan = new LihatPelanggan(d);
        d.add(lPelanggan.getPanel());
        d.setResizable(false);
        d.setSize(600,370);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
        
        idpelanggan = lPelanggan.getPelanggan()[0];
        txtNamaPelanggan.setText(lPelanggan.getPelanggan()[1]);
        txtNoTelpPelanggan.setText(lPelanggan.getPelanggan()[2]);
        txtNamaPelanggan.setEnabled(false);
        txtNoTelpPelanggan.setEnabled(false);
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        
        newPengirim();
    }//GEN-LAST:event_btnBaruActionPerformed

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
            java.util.logging.Logger.getLogger(PermintaanPickup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PermintaanPickup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PermintaanPickup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PermintaanPickup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PermintaanPickup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PermintaanPickup;
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCek;
    private javax.swing.JButton btnRequest;
    private javax.swing.JComboBox<String> cmbAsal;
    private javax.swing.JComboBox<String> cmbJenisPengiriman;
    private javax.swing.JComboBox<String> cmbTujuan;
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
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField txtAlamatAsal;
    private javax.swing.JTextField txtAlamatTujuan;
    private javax.swing.JSpinner txtBerat;
    private javax.swing.JTextField txtBiaya;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNoTelpPelanggan;
    private javax.swing.JTextField txtNoTelpPenerima;
    private javax.swing.JTextField txtTglPermintaan;
    // End of variables declaration//GEN-END:variables
}
