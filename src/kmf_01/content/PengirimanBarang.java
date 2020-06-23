/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import kmf_01.DBConnect;
import kmf_01.KMF01Lib;
import kmf_01.KMFSession;

/**
 *
 * @author HP
 */
public class PengirimanBarang extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    DefaultTableModel model;
    private ArrayList<String> driver = new ArrayList<>();
    
    public PengirimanBarang() {
        initComponents();
        FormLoad();
    }
    
    private void FormLoad() {
        ClearForm();
        
        model = new DefaultTableModel();
         
        tblPenerimaanBarang.setModel(model);
        addColumn();
        loadData("");
        tampilDriver();
    }
    
    private void addColumn() {
        model.addColumn("Connote");
        model.addColumn("Jenis Barang");
        model.addColumn("Berat Paket");
        model.addColumn("Tgl. Masuk");
        model.addColumn("Jenis Pengiriman");
        model.addColumn("Biaya Kirim");
        model.addColumn("Status Paket");
        model.addColumn("Nama Pengirim");
        model.addColumn("Kota Asal");
        model.addColumn("Alamat Asal");
        model.addColumn("No. HP Pengirim");
        model.addColumn("Nama Penerima");
        model.addColumn("Kota Tujuan");
        model.addColumn("Alamat Tujuan");
        model.addColumn("No. HP Penerima");
    }
    
    private void loadData(String cari) 
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            
            String sql = "SELECT * FROM Paket p JOIN PermintaanPengiriman pp ON p.id_permintaanpengiriman = pp.id_permintaanpengiriman "
                    + "JOIN Pelanggan pe ON pp.id_pelanggan = pe.id_pelanggan WHERE kota_tujuan='" + KMFSession.getKota() + "' "
                    + "AND status_paket LIKE 'Paket telah diterima di kantor cabang tujuan%' AND (connote LIKE '%" + cari + "%' OR "
                    + "jenis_barang LIKE '%" + cari + "%' OR kota_asal LIKE '%" + cari + "%' OR alamat_asal LIKE '%" + cari + "%' "
                    + "OR nama_pelanggan LIKE '%" + cari + "%' OR kota_tujuan LIKE '%" + cari + "%' OR alamat_tujuan LIKE '%" + cari + "%' "
                    + "OR nama_penerima LIKE '%" + cari + "%')";
            
            c.result = c.stat.executeQuery(sql);

            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[15];
                obj[0] = r.getString("connote");
                obj[1] = r.getString("jenis_barang");
                obj[2] = r.getString("berat_paket") + " Kg";
                obj[3] = r.getString("jenis_pengiriman");
                obj[4] = formatter.format(r.getDouble("biaya_kirim"));
                obj[5] = r.getString("nama_pelanggan");
                obj[6] = r.getString("kota_asal");
                obj[7] = r.getString("alamat_asal");
                obj[8] = r.getString("nohp_pelanggan");
                obj[9] = r.getString("nama_penerima");
                obj[10] = r.getString("kota_tujuan");
                obj[11] = r.getString("alamat_tujuan");
                obj[12] = r.getString("telepon_penerima");
                
                model.addRow(obj);
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data paket "  + e);
        }  
    }
    
    private void tampilDriver() {
        try {
            cmbDriver.removeAllItems();
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Driver WHERE "
                    + "kota='" + KMFSession.getKota() + "'";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                cmbDriver.addItem(c.result.getString("nama_driver"));
                driver.add(c.result.getString("id_driver"));
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data driver "  + e);
        }
    }
    
    
    public JPanel getPanel() {
        return PengirimanBarang;
    }
    
    private void ClearForm() {
        txtIdConnote.setText("");
        txtJenisBarang.setText("");
        txtJenisPengiriman.setText("");
        txtNamaPengirim.setText("");
        txtKotaAsal.setText("");
        txtAlamatAsal.setText("");
        txtNamaPenerima.setText("");
        txtKotaTujuan.setText("");
        txtAlamatTujuan.setText("");
        txtBeratPaket.setText("");
        txtBiayaPaket.setText("");
        txtTlpPengirim.setText("");
        txtTlpPenerima.setText("");
        
        txtIdConnote.setEnabled(false);
        txtJenisBarang.setEnabled(false);
        txtJenisPengiriman.setEnabled(false);
        txtNamaPengirim.setEnabled(false);
        txtKotaAsal.setEnabled(false);
        txtAlamatAsal.setEnabled(false);
        txtNamaPenerima.setEnabled(false);
        txtKotaTujuan.setEnabled(false);
        txtAlamatTujuan.setEnabled(false);
        txtBeratPaket.setEnabled(false);
        txtBiayaPaket.setEnabled(false);
        txtTlpPengirim.setEnabled(false);
        txtTlpPenerima.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PengirimanBarang = new javax.swing.JPanel();
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
        btnSimpan = new javax.swing.JButton();
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
        txtBiayaPaket = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbDriver = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtIdConnote = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtJenisBarang = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtJenisPengiriman = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTlpPengirim = new javax.swing.JTextField();
        txtTlpPenerima = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPenerimaanBarang = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PengirimanBarang.setBackground(new java.awt.Color(225, 228, 230));
        PengirimanBarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        PengirimanBarang.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                PengirimanBarangHierarchyChanged(evt);
            }
        });
        PengirimanBarang.setLayout(new javax.swing.BoxLayout(PengirimanBarang, javax.swing.BoxLayout.Y_AXIS));

        PageTitle.setBackground(new java.awt.Color(225, 228, 230));
        PageTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        PageTitle.setPreferredSize(new java.awt.Dimension(1136, 70));
        PageTitle.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                PageTitleHierarchyChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Pengiriman Barang");

        javax.swing.GroupLayout PageTitleLayout = new javax.swing.GroupLayout(PageTitle);
        PageTitle.setLayout(PageTitleLayout);
        PageTitleLayout.setHorizontalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(973, Short.MAX_VALUE))
        );
        PageTitleLayout.setVerticalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        PengirimanBarang.add(PageTitle);

        Content.setBackground(new java.awt.Color(225, 228, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Content.setPreferredSize(new java.awt.Dimension(1136, 400));
        Content.setLayout(new javax.swing.BoxLayout(Content, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(440, 32767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(440, 100));

        Form.setMaximumSize(new java.awt.Dimension(380, 32767));
        Form.setPreferredSize(new java.awt.Dimension(380, 548));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("Form Pengiriman Barang");

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

        btnSimpan.setText("Simpan");
        btnSimpan.setMaximumSize(new java.awt.Dimension(100, 30));
        btnSimpan.setMinimumSize(new java.awt.Dimension(0, 30));
        btnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

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
        txtAlamatAsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatAsalActionPerformed(evt);
            }
        });

        txtAlamatTujuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAlamatTujuan.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nama Pengirim");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Nama Penerima");

        txtBeratPaket.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBeratPaket.setEnabled(false);

        txtBiayaPaket.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBiayaPaket.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Biaya Kirim");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Driver");

        cmbDriver.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDriverActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("ID Connote");

        txtIdConnote.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Jenis Barang");

        txtJenisBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Jenis Pengiriman");

        txtJenisPengiriman.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Telepon Pengirim");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Telepon Penerima");

        txtTlpPengirim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtTlpPenerima.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout FormLayout = new javax.swing.GroupLayout(Form);
        Form.setLayout(FormLayout);
        FormLayout.setHorizontalGroup(
            FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
            .addGroup(FormLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(FormLayout.createSequentialGroup()
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel13)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14)
                            .addComponent(jLabel10))
                        .addGap(30, 30, 30)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTlpPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTlpPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtJenisPengiriman, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addComponent(txtAlamatAsal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtAlamatTujuan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtJenisBarang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtKotaAsal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtKotaTujuan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtIdConnote, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtNamaPengirim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtNamaPenerima, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                .addComponent(txtBiayaPaket, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtBeratPaket, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        FormLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbDriver, txtAlamatAsal, txtAlamatTujuan, txtIdConnote, txtJenisBarang, txtJenisPengiriman, txtKotaAsal, txtKotaTujuan, txtNamaPenerima, txtNamaPengirim, txtTlpPenerima, txtTlpPengirim});

        FormLayout.setVerticalGroup(
            FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(25, 25, 25)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(txtIdConnote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(txtNamaPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtKotaAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txtKotaTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtAlamatAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtAlamatTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(txtJenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel16)
                    .addComponent(txtJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtBeratPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtBiayaPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(txtTlpPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(txtTlpPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(cmbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        FormLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbDriver, txtBeratPaket, txtBiayaPaket, txtJenisPengiriman, txtKotaTujuan, txtTlpPenerima, txtTlpPengirim});

        FormLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtAlamatTujuan, txtJenisBarang});

        FormLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtIdConnote, txtNamaPengirim});

        jScrollPane1.setViewportView(Form);

        Content.add(jScrollPane1);

        jPanel1.setBackground(new java.awt.Color(225, 228, 230));
        jPanel1.setMaximumSize(new java.awt.Dimension(10, 32767));
        jPanel1.setPreferredSize(new java.awt.Dimension(10, 591));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );

        Content.add(jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));
        jPanel4.add(jPanel6);

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(686, 60));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("Data Penerimaan Barang");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(438, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(30, 30, 30))
        );

        jPanel4.add(jPanel7);

        tblPenerimaanBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPenerimaanBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenerimaanBarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPenerimaanBarang);

        jPanel4.add(jScrollPane2);

        Content.add(jPanel4);

        PengirimanBarang.add(Content);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PengirimanBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PengirimanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPenerimaanBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenerimaanBarangMouseClicked
        int i = tblPenerimaanBarang.getSelectedRow();
        if(i == -1) {
            return;
        }
        
        txtIdConnote.setText((String) model.getValueAt(i, 0));
        txtJenisBarang.setText((String) model.getValueAt(i, 1));
        txtBeratPaket.setText((String) model.getValueAt(i, 2));
        txtJenisPengiriman.setText((String) model.getValueAt(i, 3));
        txtBiayaPaket.setText((String) model.getValueAt(i, 4));
        txtNamaPengirim.setText((String) model.getValueAt(i, 5));
        txtKotaAsal.setText((String) model.getValueAt(i, 6));
        txtAlamatAsal.setText((String) model.getValueAt(i, 7));
        txtTlpPengirim.setText((String) model.getValueAt(i, 8));
        txtNamaPenerima.setText((String) model.getValueAt(i, 9));
        txtKotaTujuan.setText((String) model.getValueAt(i, 10));
        txtAlamatTujuan.setText((String) model.getValueAt(i, 11));
        txtTlpPenerima.setText((String) model.getValueAt(i, 12));
    }//GEN-LAST:event_tblPenerimaanBarangMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        int i = cmbDriver.getSelectedIndex();
        String status = "Paket sedang diantar ke alamat penerima oleh " + cmbDriver.getSelectedItem() + " [" + driver.get(i) + "]";
        
        KMF01Lib.UpdateStatus(txtIdConnote.getText(), status);
        KMF01Lib.UpdateHistory(txtIdConnote.getText(), status);
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
        loadData("");
        ClearForm();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        ClearForm();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void cmbDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDriverActionPerformed
        
    }//GEN-LAST:event_cmbDriverActionPerformed

    private void PageTitleHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_PageTitleHierarchyChanged
        
    }//GEN-LAST:event_PageTitleHierarchyChanged

    private void PengirimanBarangHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_PengirimanBarangHierarchyChanged
        FormLoad();
    }//GEN-LAST:event_PengirimanBarangHierarchyChanged

    private void txtAlamatAsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatAsalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatAsalActionPerformed

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
            java.util.logging.Logger.getLogger(PengirimanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PengirimanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PengirimanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PengirimanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new PengirimanBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Form;
    private javax.swing.JPanel PageTitle;
    private javax.swing.JPanel PengirimanBarang;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbDriver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPenerimaanBarang;
    private javax.swing.JTextField txtAlamatAsal;
    private javax.swing.JTextField txtAlamatTujuan;
    private javax.swing.JTextField txtBeratPaket;
    private javax.swing.JTextField txtBiayaPaket;
    private javax.swing.JTextField txtIdConnote;
    private javax.swing.JTextField txtJenisBarang;
    private javax.swing.JTextField txtJenisPengiriman;
    private javax.swing.JTextField txtKotaAsal;
    private javax.swing.JTextField txtKotaTujuan;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNamaPengirim;
    private javax.swing.JTextField txtTlpPenerima;
    private javax.swing.JTextField txtTlpPengirim;
    // End of variables declaration//GEN-END:variables
}
