/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import kmf_01.DBConnect;
import kmf_01.KMF01Lib;

/**
 *
 * @author samod
 */
public final class PengirimanCargo extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    DefaultTableModel modelcargo, modeldetail;
    ArrayList<String> bandara = new ArrayList<>();
    int changed = 0;
    
    /**
     * Creates new form Navbar
     */
    public PengirimanCargo() {
        initComponents();
        
        FormLoad();
    }
    
    private void FormLoad() {
        modelcargo = new DefaultTableModel();
        modeldetail = new DefaultTableModel();

        
        tblDaftarCargo.setModel(modelcargo);
        tblDetailCargo.setModel(modeldetail);
        addColumnDaftar();
        addColumnDetail();
        loadBandara();
    }
    
    private void addColumnDaftar() {  
        modelcargo.addColumn("ID Cargo");
        modelcargo.addColumn("Tgl. Kirim");
        modelcargo.addColumn("Bandara Tujuan");
    }
    
    private void addColumnDetail() {  
        modeldetail.addColumn("Connote");
        modeldetail.addColumn("Jenis Barang");
        modeldetail.addColumn("Berat Paket");
        modeldetail.addColumn("Nama Penerima");
    }
    
    private void Clear() {
        txtIDCargo.setText("");
        modelcargo.getDataVector().removeAllElements();
        modelcargo.fireTableDataChanged();
        modeldetail.getDataVector().removeAllElements();
        modeldetail.fireTableDataChanged();
        btnKonfirmasi.setEnabled(false);
        
    }
    
    private void loadDetail(String id_cargo) {
        try {
            modeldetail.getDataVector().removeAllElements();
            modeldetail.fireTableDataChanged();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM DetilCargoManifest d JOIN Paket p ON d.connote = p.connote "
                    + "JOIN PermintaanPengiriman pe ON p.id_permintaanpengiriman = pe.id_permintaanpengiriman "
                    + "WHERE id_cargo='" + id_cargo + "'";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[4];
                obj[0] = r.getString("connote");
                obj[1] = r.getString("jenis_barang");
                obj[2] = r.getString("berat_paket");
                obj[3] = r.getString("nama_penerima");
                
                modeldetail.addRow(obj);
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data barang "  + e);
        }
        
    }
    
    private void loadCargo(String id_bandara) {
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        try {
            modeldetail.getDataVector().removeAllElements();
            modeldetail.fireTableDataChanged();
            
            modelcargo.getDataVector().removeAllElements();
            modelcargo.fireTableDataChanged();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT c.*, ba.nama_bandara as 'bandaraAsal', bt.nama_bandara as 'bandaraTujuan' FROM CargoManifest c "
                    + "JOIN Bandara ba ON ba.id_bandara = c.bandara_asal JOIN Bandara bt ON bt.id_bandara = c.bandara_tujuan "
                    + "WHERE ba.id_bandara='" + id_bandara + "' AND status_cargo='Cargo telah diterima di bandara kota asal'";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[4];
                obj[0] = r.getString("id_cargo");
                obj[1] = f.format(r.getDate("tgl_kirim"));
                obj[2] = r.getString("bandaraTujuan");
                
                modelcargo.addRow(obj);
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data barang "  + e);
        }
        
        tblDaftarCargo.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=tblDaftarCargo.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(15);    
        colModel.getColumn(1).setPreferredWidth(30);
    }
    
    private void loadBandara() {
        try {
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Bandara";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                ResultSet r = c.result;
                cmbBandara.addItem(r.getString("nama_bandara"));
                bandara.add(r.getString("id_bandara"));
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat load data bandara: " + e);
        }
    }
    
    public JPanel getPanel() {
        return PengirimanCargo;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PengirimanCargo = new javax.swing.JPanel();
        PageTitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Content = new javax.swing.JPanel();
        Form = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbBandara = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDaftarCargo = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Data = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIDCargo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetailCargo = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnKonfirmasi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        PengirimanCargo.setBackground(new java.awt.Color(225, 228, 230));
        PengirimanCargo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        PengirimanCargo.setPreferredSize(new java.awt.Dimension(1176, 768));
        PengirimanCargo.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                PengirimanCargoHierarchyChanged(evt);
            }
        });
        PengirimanCargo.setLayout(new javax.swing.BoxLayout(PengirimanCargo, javax.swing.BoxLayout.Y_AXIS));

        PageTitle.setBackground(new java.awt.Color(225, 228, 230));
        PageTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        PageTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Pengiriman Cargo");

        javax.swing.GroupLayout PageTitleLayout = new javax.swing.GroupLayout(PageTitle);
        PageTitle.setLayout(PageTitleLayout);
        PageTitleLayout.setHorizontalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(999, Short.MAX_VALUE))
        );
        PageTitleLayout.setVerticalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        PengirimanCargo.add(PageTitle);

        Content.setBackground(new java.awt.Color(225, 228, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Content.setPreferredSize(new java.awt.Dimension(1136, 400));
        Content.setLayout(new javax.swing.BoxLayout(Content, javax.swing.BoxLayout.LINE_AXIS));

        Form.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Form.setMaximumSize(new java.awt.Dimension(40, 32767));
        Form.setPreferredSize(new java.awt.Dimension(500, 548));
        Form.setLayout(new javax.swing.BoxLayout(Form, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 90));
        jPanel2.setPreferredSize(new java.awt.Dimension(460, 90));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Bandara Asal");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("Daftar Cargo");

        cmbBandara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBandaraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(58, 58, 58)
                .addComponent(cmbBandara, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 338, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbBandara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        Form.add(jPanel2);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        tblDaftarCargo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDaftarCargo.getTableHeader().setResizingAllowed(false);
        tblDaftarCargo.getTableHeader().setReorderingAllowed(false);
        tblDaftarCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDaftarCargoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDaftarCargo);

        jPanel3.add(jScrollPane1);

        Form.add(jPanel3);

        jPanel8.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel8.setPreferredSize(new java.awt.Dimension(460, 70));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 64, Short.MAX_VALUE)
        );

        Form.add(jPanel8);

        Content.add(Form);

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
            .addGap(0, 592, Short.MAX_VALUE)
        );

        Content.add(jPanel5);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(686, 60));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("Detail Cargo");

        jLabel9.setText("ID Cargo Manifest");

        txtIDCargo.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 298, Short.MAX_VALUE)
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
                .addGap(0, 31, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        tblDetailCargo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetailCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetailCargoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDetailCargo);

        jPanel6.add(jScrollPane2);

        jPanel4.add(jPanel6);

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel1.setPreferredSize(new java.awt.Dimension(546, 70));

        btnKonfirmasi.setText("Kirim Cargo");
        btnKonfirmasi.setEnabled(false);
        btnKonfirmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirmasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 550, Short.MAX_VALUE)
                .addComponent(btnKonfirmasi))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 37, Short.MAX_VALUE)
                .addComponent(btnKonfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.add(jPanel1);

        javax.swing.GroupLayout DataLayout = new javax.swing.GroupLayout(Data);
        Data.setLayout(DataLayout);
        DataLayout.setHorizontalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
        );
        DataLayout.setVerticalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
        );

        Content.add(Data);

        PengirimanCargo.add(Content);

        getContentPane().add(PengirimanCargo);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblDetailCargoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailCargoMouseClicked
        
    }//GEN-LAST:event_tblDetailCargoMouseClicked

    private void btnKonfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiActionPerformed
        try {
            
            String query = "UPDATE CargoManifest SET status_cargo=? WHERE id_cargo=?";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, "Cargo sedang dikirim menuju bandara kota tujuan");
            connection.pstat.setString(2, txtIDCargo.getText());
            
            connection.pstat.executeUpdate();
            connection.pstat.close();
            
            for(int i = 0; i < modeldetail.getRowCount(); i++) {
                KMF01Lib.UpdateStatus((String)tblDetailCargo.getValueAt(i, 0), "Paket sedang dikirim menuju bandara kota tujuan");
                KMF01Lib.UpdateHistory((String)tblDetailCargo.getValueAt(i, 0), "Paket sedang dikirim menuju bandara kota tujuan");
            }
            
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat konfirmasi cargo manifest: " + e);
        }
        
        JOptionPane.showMessageDialog(this, "Konfirmasi penerimaan Cargo Manifest berhasil");
        Clear();
    }//GEN-LAST:event_btnKonfirmasiActionPerformed

    private void tblDaftarCargoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDaftarCargoMouseClicked
        int i = tblDaftarCargo.getSelectedRow();
        if(i == -1) {
            return;
        }
        
        String id_cargo = (String) modelcargo.getValueAt(i, 0);
        loadDetail(id_cargo);
        txtIDCargo.setText(id_cargo);
        btnKonfirmasi.setEnabled(true);
    }//GEN-LAST:event_tblDaftarCargoMouseClicked

    private void cmbBandaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBandaraActionPerformed
        if(changed != 0) {
            int i = cmbBandara.getSelectedIndex();
            loadCargo(bandara.get(i));
            btnKonfirmasi.setEnabled(false);
        }
        changed++;
    }//GEN-LAST:event_cmbBandaraActionPerformed

    private void PengirimanCargoHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_PengirimanCargoHierarchyChanged
        FormLoad();
    }//GEN-LAST:event_PengirimanCargoHierarchyChanged

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
            java.util.logging.Logger.getLogger(PengirimanCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PengirimanCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PengirimanCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PengirimanCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PengirimanCargo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Data;
    private javax.swing.JPanel Form;
    private javax.swing.JPanel PageTitle;
    private javax.swing.JPanel PengirimanCargo;
    private javax.swing.JButton btnKonfirmasi;
    private javax.swing.JComboBox<String> cmbBandara;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDaftarCargo;
    private javax.swing.JTable tblDetailCargo;
    private javax.swing.JTextField txtIDCargo;
    // End of variables declaration//GEN-END:variables
}
