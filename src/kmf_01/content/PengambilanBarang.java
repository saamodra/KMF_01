/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import kmf_01.DBConnect;

/**
 *
 * @author samod
 */
public class PengambilanBarang extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    DefaultTableModel model;
    ArrayList<String> kc = new ArrayList<>();
    private String id_permintaan;
    /**
     * Creates new form Navbar
     */
    public PengambilanBarang() {
        initComponents();
        
        FormLoad();
    }
    
    private void FormLoad() {
        model = new DefaultTableModel();
        
        tblPermintaanPickup.setModel(model);
        addColumn();
        loadData();
        tampilKantorCabang();
    }
    
    private void addColumn() {  
        model.addColumn("ID");
        model.addColumn("Nama Pengirim");
        model.addColumn("Kota Asal");
        model.addColumn("Alamat Asal");
        model.addColumn("Nama Penerima");
        model.addColumn("Kota Tujuan");
        model.addColumn("Alamat Tujuan");
        model.addColumn("Berat Paket (kg)");
        model.addColumn("Biaya Kirim");
        model.addColumn("Tgl. Permintaan");
    }
    
    private void loadData() {
        model.getDataVector().removeAllElements();
        
        model.fireTableDataChanged();
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM PermintaanPengiriman pe JOIN Pelanggan p ON pe.id_pelanggan=p.id_pelanggan WHERE status_pickup='Diminta'";
            connection.result = connection.stat.executeQuery(query);
            
            while(connection.result.next()) {
                Object[] obj = new Object[10];
                obj[0] = connection.result.getString("id_permintaanpengiriman");
                obj[1] = connection.result.getString("nama_pelanggan");
                obj[2] = connection.result.getString("kota_asal");
                obj[3] = connection.result.getString("alamat_asal");
                obj[4] = connection.result.getString("nama_penerima");
                obj[5] = connection.result.getString("kota_tujuan");
                obj[6] = connection.result.getString("alamat_tujuan");
                obj[7] = connection.result.getString("berat_paket");
                obj[8] = formatter.format(connection.result.getDouble("biaya_kirim"));
                obj[9] = connection.result.getString("tgl_permintaan");
                
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
            
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data permintaan pengiriman: " + e);
        }
    }
    
    private void tampilKantorCabang() {
        try {
            cmbKantorCabang.removeAllItems();
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT id_kantorcabang, nama_kantorcabang FROM KantorCabangKota";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                cmbKantorCabang.addItem(c.result.getString("nama_kantorcabang"));
                kc.add(c.result.getString("id_kantorcabang"));
            }
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data kantor cabang "  + e);
        }
    }
    
    public JPanel getPanel() {
        return PengambilanBarang;
    }
    
    public void ClearForm() {
        id_permintaan = "";
        txtNamaPengirim.setText("");
        txtKotaAsal.setText("");
        txtAlamatAsal.setText("");
        txtNamaPenerima.setText("");
        txtKotaTujuan.setText("");
        txtAlamatTujuan.setText("");
        txtBeratPaket.setText("");
        txtBiayaPaket.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PengambilanBarang = new javax.swing.JPanel();
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
        btnKirim = new javax.swing.JButton();
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
        cmbKantorCabang = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        Data = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPermintaanPickup = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PengambilanBarang.setBackground(new java.awt.Color(225, 228, 230));
        PengambilanBarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        PengambilanBarang.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                PengambilanBarangHierarchyChanged(evt);
            }
        });
        PengambilanBarang.setLayout(new javax.swing.BoxLayout(PengambilanBarang, javax.swing.BoxLayout.Y_AXIS));

        PageTitle.setBackground(new java.awt.Color(225, 228, 230));
        PageTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        PageTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Pengambilan Barang");

        javax.swing.GroupLayout PageTitleLayout = new javax.swing.GroupLayout(PageTitle);
        PageTitle.setLayout(PageTitleLayout);
        PageTitleLayout.setHorizontalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(937, Short.MAX_VALUE))
        );
        PageTitleLayout.setVerticalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        PengambilanBarang.add(PageTitle);

        Content.setBackground(new java.awt.Color(225, 228, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Content.setPreferredSize(new java.awt.Dimension(1136, 400));
        Content.setLayout(new javax.swing.BoxLayout(Content, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(430, 32767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(430, 100));

        Form.setMaximumSize(new java.awt.Dimension(400, 32767));
        Form.setPreferredSize(new java.awt.Dimension(400, 548));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("Form Pengambilan Barang");

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

        btnKirim.setText("Kirim");
        btnKirim.setMaximumSize(new java.awt.Dimension(100, 30));
        btnKirim.setMinimumSize(new java.awt.Dimension(0, 30));
        btnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        btnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.setMaximumSize(new java.awt.Dimension(73, 30));
        btnBatal.setMinimumSize(new java.awt.Dimension(0, 30));
        btnBatal.setPreferredSize(new java.awt.Dimension(100, 23));

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

        txtBiayaPaket.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBiayaPaket.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Biaya Paket");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Kantor Cabang");

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
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(23, 23, 23)
                        .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormLayout.createSequentialGroup()
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnKirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNamaPengirim)
                            .addComponent(txtNamaPenerima)
                            .addComponent(txtKotaAsal)
                            .addComponent(txtKotaTujuan)
                            .addComponent(txtAlamatAsal)
                            .addComponent(txtAlamatTujuan)
                            .addComponent(txtBeratPaket)
                            .addComponent(txtBiayaPaket)
                            .addComponent(cmbKantorCabang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(34, 34, 34))
        );
        FormLayout.setVerticalGroup(
            FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(40, 40, 40)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNamaPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
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
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtBiayaPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cmbKantorCabang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );

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
            .addGap(0, 591, Short.MAX_VALUE)
        );

        Content.add(jPanel5);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(686, 60));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("Data Permintaan Pickup");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addContainerGap(447, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 33, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));
        jPanel4.add(jPanel6);

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
        jScrollPane3.setViewportView(tblPermintaanPickup);

        jPanel4.add(jScrollPane3);

        javax.swing.GroupLayout DataLayout = new javax.swing.GroupLayout(Data);
        Data.setLayout(DataLayout);
        DataLayout.setHorizontalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
        );
        DataLayout.setVerticalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );

        Content.add(Data);

        PengambilanBarang.add(Content);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PengambilanBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PengambilanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimActionPerformed
        try {
            String query = "UPDATE PermintaanPengiriman SET status_pickup=? WHERE id_permintaanpengiriman=?";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, "Permintaan Pickup");
            connection.pstat.setString(2, id_permintaan);

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch(Exception e) {
            System.out.println("Terjadi error pada saat pengambilan barang : " + e);
        }
        JOptionPane.showMessageDialog(this, "Pengambilan barang berhasil diproses");
        loadData();
        ClearForm();
    }//GEN-LAST:event_btnKirimActionPerformed

    private void PengambilanBarangHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_PengambilanBarangHierarchyChanged
        FormLoad();
    }//GEN-LAST:event_PengambilanBarangHierarchyChanged

    private void tblPermintaanPickupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPermintaanPickupMouseClicked
        int i = tblPermintaanPickup.getSelectedRow();
        if(i == -1) {
            return;
        }
        
        id_permintaan = (String) model.getValueAt(i, 0);
        txtNamaPengirim.setText((String) model.getValueAt(i, 1));
        txtKotaAsal.setText((String) model.getValueAt(i, 2));
        txtAlamatAsal.setText((String) model.getValueAt(i, 3));
        txtNamaPenerima.setText((String) model.getValueAt(i, 4));
        txtKotaTujuan.setText((String) model.getValueAt(i, 5));
        txtAlamatTujuan.setText((String) model.getValueAt(i, 6));
        txtBeratPaket.setText((String) model.getValueAt(i, 7));
        txtBiayaPaket.setText((String) model.getValueAt(i, 8));
    }//GEN-LAST:event_tblPermintaanPickupMouseClicked

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
            java.util.logging.Logger.getLogger(PengambilanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PengambilanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PengambilanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PengambilanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PengambilanBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Data;
    private javax.swing.JPanel Form;
    private javax.swing.JPanel PageTitle;
    private javax.swing.JPanel PengambilanBarang;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnKirim;
    private javax.swing.JComboBox<String> cmbKantorCabang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblPermintaanPickup;
    private javax.swing.JTextField txtAlamatAsal;
    private javax.swing.JTextField txtAlamatTujuan;
    private javax.swing.JTextField txtBeratPaket;
    private javax.swing.JTextField txtBiayaPaket;
    private javax.swing.JTextField txtKotaAsal;
    private javax.swing.JTextField txtKotaTujuan;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNamaPengirim;
    // End of variables declaration//GEN-END:variables
}
