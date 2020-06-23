/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import kmf_01.DBConnect;
import kmf_01.KMF01Lib;


public class InputPaket extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    DefaultTableModel model;
    private String id_permintaan;
    ArrayList<String> id_kota = new ArrayList<>();
    private int State = 0;
    
    /**
     * Creates new form Navbar
     */
    public InputPaket() {
        initComponents();
        
        FormLoad();
    }
    
    private void FormLoad() {
        model = new DefaultTableModel();
         
        tblPermintaanPickup.setModel(model);
        addColumn();
        loadData();
        listKota();
        ClearForm();
    }
    
    private void addColumn() {  
        model.addColumn("ID");
        model.addColumn("Nama Pengirim");
        model.addColumn("No. HP Pengirim");
        model.addColumn("Kota Asal");
        model.addColumn("Alamat Asal");
        model.addColumn("Nama Penerima");
        model.addColumn("No. HP Penerima");
        model.addColumn("Kota Tujuan");
        model.addColumn("Alamat Tujuan");
        model.addColumn("Jenis Pengiriman");
        model.addColumn("Berat Paket (kg)");
        model.addColumn("Biaya Kirim");
        model.addColumn("Tgl. Permintaan");
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
            
            txtIDPermintaan.setText(id);
        } catch(Exception e) {
            System.out.println("Terjadi error saat load permintaan pengiriman: " + e);
        }
    }
    
    private void loadData() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        model.getDataVector().removeAllElements();
        
        model.fireTableDataChanged();
        
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM PermintaanPengiriman pe JOIN Pelanggan p ON pe.id_pelanggan=p.id_pelanggan WHERE "
                    + "status_pickup LIKE 'Paket Sedang Dijemput oleh%'";
            connection.result = connection.stat.executeQuery(query);
            
            while(connection.result.next()) {
                Object[] obj = new Object[13];
                obj[0] = connection.result.getString("id_permintaanpengiriman");
                obj[1] = connection.result.getString("nama_pelanggan");
                obj[2] = connection.result.getString("nohp_pelanggan");
                obj[3] = connection.result.getString("kota_asal");
                obj[4] = connection.result.getString("alamat_asal");
                obj[5] = connection.result.getString("nama_penerima");
                obj[6] = connection.result.getString("telepon_penerima");
                obj[7] = connection.result.getString("kota_tujuan");
                obj[8] = connection.result.getString("alamat_tujuan");
                obj[9] = connection.result.getString("jenis_pengiriman");
                obj[10] = connection.result.getString("berat_paket");
                obj[11] = formatter.format(connection.result.getDouble("biaya_kirim"));
                obj[12] = connection.result.getString("tgl_permintaan");
                
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
            
        } catch(Exception e) {
            System.out.println("Terjadi error saat load data temporary shipment: " + e);
        }
    }
    
    public JPanel getPanel() {
        return InputPaket;
    }
    
    private String generateID() {
        Date date = new Date();
        String str = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        return str;
    }
    
    private void ClearForm() {
        AutoNumber();
        txtNamaPengirim.setText("");
        cmbKotaAsal.setSelectedIndex(0);
        txtAlamatAsal.setText("");
        txtHPPenerima.setText("");
        txtHPPengirim.setText("");
        txtNamaPenerima.setText("");
        cmbKotaTujuan.setSelectedIndex(0);
        txtAlamatTujuan.setText("");
        txtBeratPaket.setValue(0);
        txtBiayaPaket.setText("");
        tglmasuk.setDate(new Date());
        txtIDConnote.setText(generateID());
        txtNamaPengirim.setEnabled(true);
        cmbKotaAsal.setEnabled(true);
        txtAlamatAsal.setEnabled(true);
        txtNamaPenerima.setEnabled(true);
        cmbKotaTujuan.setEnabled(true);
        txtAlamatTujuan.setEnabled(true);
        txtBeratPaket.setEnabled(true);
        txtBiayaPaket.setEnabled(true);
        btnRefund.setEnabled(false);
        btnHitung.setEnabled(true);
        cmbJenisPengiriman.setEnabled(true);
        txtHPPenerima.setEnabled(true);
        txtHPPengirim.setEnabled(true);
        txtJenisBarang.setText("");
        
        State = 0;
    }
    
    private void listKota() {
        try {
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM KantorCabangKota";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                cmbKotaAsal.addItem(c.result.getString("kota"));
                cmbKotaTujuan.addItem(c.result.getString("kota"));
                id_kota.add(c.result.getString("id_kantorcabang"));
            }
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data list kota "  + e);
        }
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
    
    private void calculateTotalPrice() {
        double totalPrice =  getDestinationPrice(id_kota.get(cmbKotaAsal.getSelectedIndex()), id_kota.get(cmbKotaTujuan.getSelectedIndex()), (String)cmbJenisPengiriman.getSelectedItem());
        double berat = Double.parseDouble(txtBeratPaket.getValue().toString());

        txtBiayaPaket.setText(String.valueOf(berat * totalPrice)); 
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
            
        } catch(Exception e) {
            System.out.println("Terjadi error saat load pelanggan: " + e);
        }
         
         return id;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InputPaket = new javax.swing.JPanel();
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
        btnBaru = new javax.swing.JButton();
        txtNamaPengirim = new javax.swing.JTextField();
        txtNamaPenerima = new javax.swing.JTextField();
        txtAlamatAsal = new javax.swing.JTextField();
        txtAlamatTujuan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtBiayaPaket = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtIDConnote = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtJenisBarang = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tglmasuk = new com.toedter.calendar.JDateChooser();
        btnRefund = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtIDPermintaan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtHPPengirim = new javax.swing.JTextField();
        txtHPPenerima = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnHitung = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        cmbKotaAsal = new javax.swing.JComboBox<>();
        cmbKotaTujuan = new javax.swing.JComboBox<>();
        cmbJenisPengiriman = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtBeratPaket = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        Data = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPermintaanPickup = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        InputPaket.setBackground(new java.awt.Color(225, 228, 230));
        InputPaket.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        InputPaket.setPreferredSize(new java.awt.Dimension(1176, 768));
        InputPaket.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                InputPaketHierarchyChanged(evt);
            }
        });
        InputPaket.setLayout(new javax.swing.BoxLayout(InputPaket, javax.swing.BoxLayout.Y_AXIS));

        PageTitle.setBackground(new java.awt.Color(225, 228, 230));
        PageTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        PageTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Tambah Paket (Connote)");

        javax.swing.GroupLayout PageTitleLayout = new javax.swing.GroupLayout(PageTitle);
        PageTitle.setLayout(PageTitleLayout);
        PageTitleLayout.setHorizontalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(832, Short.MAX_VALUE))
        );
        PageTitleLayout.setVerticalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        InputPaket.add(PageTitle);

        Content.setBackground(new java.awt.Color(225, 228, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Content.setPreferredSize(new java.awt.Dimension(1136, 400));
        Content.setLayout(new javax.swing.BoxLayout(Content, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(520, 32767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(520, 100));

        Form.setMaximumSize(new java.awt.Dimension(510, 32767));
        Form.setPreferredSize(new java.awt.Dimension(510, 630));

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
        jLabel7.setText("Jenis Pengiriman");

        btnSimpan.setText("Simpan Paket");
        btnSimpan.setMaximumSize(new java.awt.Dimension(100, 30));
        btnSimpan.setMinimumSize(new java.awt.Dimension(0, 30));
        btnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBaru.setText("Baru");
        btnBaru.setToolTipText("");
        btnBaru.setMaximumSize(new java.awt.Dimension(73, 30));
        btnBaru.setMinimumSize(new java.awt.Dimension(0, 30));
        btnBaru.setPreferredSize(new java.awt.Dimension(100, 23));
        btnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruActionPerformed(evt);
            }
        });

        txtNamaPengirim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNamaPenerima.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtAlamatAsal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtAlamatTujuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nama Pengirim");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Nama Penerima");

        txtBiayaPaket.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Biaya Paket");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("ID Connote");

        txtIDConnote.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIDConnote.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Jenis Barang");

        txtJenisBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Tgl. Masuk");

        tglmasuk.setEnabled(false);

        btnRefund.setText("Refund Barang");
        btnRefund.setEnabled(false);
        btnRefund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefundActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("ID Permintaan Kirim");

        txtIDPermintaan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIDPermintaan.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("HP");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("HP");

        txtHPPengirim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtHPPenerima.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("KG");

        btnHitung.setText("Hitung");
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Rp.");

        cmbKotaTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKotaTujuanActionPerformed(evt);
            }
        });

        cmbJenisPengiriman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reguler", "Express" }));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Berat Paket");

        javax.swing.GroupLayout FormLayout = new javax.swing.GroupLayout(Form);
        Form.setLayout(FormLayout);
        FormLayout.setHorizontalGroup(
            FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(FormLayout.createSequentialGroup()
                            .addComponent(btnBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(59, 59, 59)
                            .addComponent(btnRefund, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FormLayout.createSequentialGroup()
                            .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)
                                .addComponent(jLabel15))
                            .addGap(12, 12, 12)
                            .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(FormLayout.createSequentialGroup()
                                    .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtNamaPenerima, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNamaPengirim, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel18))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtHPPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtHPPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(cmbKotaAsal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbKotaTujuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAlamatAsal)
                                .addComponent(txtAlamatTujuan)
                                .addComponent(txtJenisBarang)
                                .addComponent(txtIDConnote)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormLayout.createSequentialGroup()
                                    .addComponent(cmbJenisPengiriman, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel20)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtBeratPaket, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel9)
                                    .addGap(8, 8, 8))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormLayout.createSequentialGroup()
                                    .addComponent(btnHitung)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel19)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtBiayaPaket, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(tglmasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIDPermintaan)))))
                .addGap(27, 27, 27))
        );

        FormLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtHPPenerima, txtHPPengirim});

        FormLayout.setVerticalGroup(
            FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(txtIDPermintaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(txtNamaPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtHPPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtHPPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbKotaAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbKotaTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtAlamatAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtAlamatTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(cmbJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtBeratPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(txtBiayaPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHitung)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(txtIDConnote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(txtJenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(tglmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefund))
                .addContainerGap())
        );

        FormLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnRefund, btnSimpan});

        jScrollPane1.setViewportView(Form);

        Content.add(jScrollPane1);

        jPanel5.setMaximumSize(new java.awt.Dimension(10, 32767));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(10, 548));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 637, Short.MAX_VALUE)
        );

        Content.add(jPanel5);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(686, 60));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("Data Temporary Shipment");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addContainerGap(281, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 33, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        tblPermintaanPickup.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPermintaanPickup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPermintaanPickupMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPermintaanPickup);

        jPanel6.add(jScrollPane2);

        jPanel4.add(jPanel6);

        javax.swing.GroupLayout DataLayout = new javax.swing.GroupLayout(Data);
        Data.setLayout(DataLayout);
        DataLayout.setHorizontalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
        );
        DataLayout.setVerticalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );

        Content.add(Data);

        InputPaket.add(Content);

        getContentPane().add(InputPaket);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPermintaanPickupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPermintaanPickupMouseClicked
        int i = tblPermintaanPickup.getSelectedRow();
        if(i == -1) {
            return;
        }
        
        txtIDPermintaan.setText((String) model.getValueAt(i, 0));
        txtNamaPengirim.setText((String) model.getValueAt(i, 1));
        txtHPPengirim.setText((String) model.getValueAt(i, 2));
        cmbKotaAsal.setSelectedItem((String) model.getValueAt(i, 3));
        txtAlamatAsal.setText((String) model.getValueAt(i, 4));
        txtNamaPenerima.setText((String) model.getValueAt(i, 5));
        txtHPPenerima.setText((String) model.getValueAt(i, 6));
        cmbKotaTujuan.setSelectedItem((String) model.getValueAt(i, 7));
        txtAlamatTujuan.setText((String) model.getValueAt(i, 8));
        cmbJenisPengiriman.setSelectedItem((String) model.getValueAt(i, 9));
        txtBeratPaket.setValue(Integer.valueOf((String) model.getValueAt(i, 10)));
        txtBiayaPaket.setText(((String) model.getValueAt(i, 11)).substring(2));
        txtNamaPengirim.setEnabled(false);
        cmbKotaAsal.setEnabled(false);
        txtAlamatAsal.setEnabled(false);
        txtNamaPenerima.setEnabled(false);
        cmbKotaTujuan.setEnabled(false);
        txtAlamatTujuan.setEnabled(false);
        txtBeratPaket.setEnabled(false);
        txtBiayaPaket.setEnabled(false);
        btnRefund.setEnabled(true);
        btnHitung.setEnabled(false);
        cmbJenisPengiriman.setEnabled(false);
        txtHPPenerima.setEnabled(false);
        txtHPPengirim.setEnabled(false);
        
        State = 1;
    }//GEN-LAST:event_tblPermintaanPickupMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(State == 0) {
            try {
                String idPelanggan = IdPelanggan();
                String query = "INSERT INTO Pelanggan VALUES (?,?,?,?)";
                connection.pstat = connection.conn.prepareStatement(query);
                connection.pstat.setString(1, idPelanggan);
                connection.pstat.setString(2, txtNamaPengirim.getText());
                connection.pstat.setString(3, txtAlamatAsal.getText());
                connection.pstat.setString(4, txtHPPengirim.getText());
                connection.pstat.executeUpdate();
                connection.pstat.close();
                
                query = "INSERT INTO PermintaanPengiriman (id_permintaanpengiriman, kota_asal, alamat_asal, "
                        + "telepon_penerima, nama_penerima, kota_tujuan, jenis_pengiriman, alamat_tujuan, berat_paket, biaya_kirim,"
                        + " id_pelanggan, id_staff) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                connection.pstat = connection.conn.prepareStatement(query);
                connection.pstat.setString(1, txtIDPermintaan.getText());
                connection.pstat.setString(2, (String)cmbKotaAsal.getSelectedItem());
                connection.pstat.setString(3, txtAlamatAsal.getText());
                connection.pstat.setString(4, txtHPPengirim.getText());
                connection.pstat.setString(5, txtNamaPenerima.getText());
                connection.pstat.setString(6, (String)cmbKotaTujuan.getSelectedItem());
                connection.pstat.setString(7, (String)cmbJenisPengiriman.getSelectedItem());
                connection.pstat.setString(8, txtAlamatTujuan.getText());
                connection.pstat.setString(9, txtBeratPaket.getValue().toString());
                connection.pstat.setString(10, txtBiayaPaket.getText());
                connection.pstat.setString(11, idPelanggan);
                connection.pstat.setString(12, "SK001");
                connection.pstat.executeUpdate();
                connection.pstat.close();
                

            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat tambah paket permintaan : " + e);
            }
        }
        
        try {
            String query = "UPDATE PermintaanPengiriman SET status_pickup=? WHERE id_permintaanpengiriman=?";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, "Selesai");
            connection.pstat.setString(2, txtIDPermintaan.getText());
            connection.pstat.executeUpdate();
            connection.pstat.close();
                
            query = "INSERT INTO Paket (connote, jenis_barang, id_permintaanpengiriman) VALUES (?,?,?)";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, txtIDConnote.getText());
            connection.pstat.setString(2, txtJenisBarang.getText());
            connection.pstat.setString(3, txtIDPermintaan.getText());

            connection.pstat.executeUpdate();
            connection.pstat.close();
            
            KMF01Lib.UpdateHistory(txtIDConnote.getText(), "Paket siap dikirim");

        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah paket : " + e);
        }
        JOptionPane.showMessageDialog(this, "Tambah Paket berhasil");
        loadData();
        ClearForm();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        ClearForm();
    }//GEN-LAST:event_btnBaruActionPerformed

    private void btnRefundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefundActionPerformed
        try {
            String query = "UPDATE PermintaanPengiriman SET status_pickup=? WHERE id_permintaanpengiriman=?";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, "Ditolak");
            connection.pstat.setString(2, txtIDPermintaan.getText());

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat pengambilan barang : " + e);
        }
        JOptionPane.showMessageDialog(this, "Refund paket berhasil");
        loadData();
        ClearForm();
    }//GEN-LAST:event_btnRefundActionPerformed

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        calculateTotalPrice();
    }//GEN-LAST:event_btnHitungActionPerformed

    private void cmbKotaTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKotaTujuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKotaTujuanActionPerformed

    private void InputPaketHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_InputPaketHierarchyChanged
        FormLoad();
    }//GEN-LAST:event_InputPaketHierarchyChanged

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
            java.util.logging.Logger.getLogger(InputPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InputPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InputPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InputPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InputPaket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Data;
    private javax.swing.JPanel Form;
    private javax.swing.JPanel InputPaket;
    private javax.swing.JPanel PageTitle;
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnRefund;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbJenisPengiriman;
    private javax.swing.JComboBox<String> cmbKotaAsal;
    private javax.swing.JComboBox<String> cmbKotaTujuan;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPermintaanPickup;
    private com.toedter.calendar.JDateChooser tglmasuk;
    private javax.swing.JTextField txtAlamatAsal;
    private javax.swing.JTextField txtAlamatTujuan;
    private javax.swing.JSpinner txtBeratPaket;
    private javax.swing.JTextField txtBiayaPaket;
    private javax.swing.JTextField txtHPPenerima;
    private javax.swing.JTextField txtHPPengirim;
    private javax.swing.JTextField txtIDConnote;
    private javax.swing.JTextField txtIDPermintaan;
    private javax.swing.JTextField txtJenisBarang;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNamaPengirim;
    // End of variables declaration//GEN-END:variables
}
