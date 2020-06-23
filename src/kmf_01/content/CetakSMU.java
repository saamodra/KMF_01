/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import kmf_01.DBConnect;
import kmf_01.KMF01Lib;


public class CetakSMU extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");

    
    /**
     * Creates new form Navbar
     */
    public CetakSMU() {
        initComponents();
        
    }
    
    private void loadData(String id_cargo) {
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        
        try {
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT c.*, ba.nama_bandara AS 'namabandaraasal', ba.kota AS 'kota_asal', bt.nama_bandara AS 'namabandaratujuan', "
                    + "bt.kota AS 'kota_tujuan' FROM CargoManifest c JOIN bandara ba ON ba.id_bandara = c.bandara_asal "
                    + "JOIN bandara bt ON bt.id_bandara = c.bandara_tujuan WHERE id_cargo = '" + id_cargo + "' "
                    + "AND status_cargo='Cargo telah diterima di bandara kota asal'";
            
            c.result = c.stat.executeQuery(sql);
            int count = 0;
            
            while(c.result.next()) {
                ResultSet r = c.result;
                lblBandaraAsal.setText(r.getString("namabandaraasal"));
                lblBandaraTujuan.setText(r.getString("namabandaratujuan"));
                lblRuteAsal.setText("Dari : " + r.getString("kota_asal"));
                lblRuteTujuan.setText("Tujuan : " + r.getString("kota_tujuan"));
                lblTgl.setText("Dibuat pada (tanggal) : " + f.format(new Date()));
                lblDi.setText("Di (tempat) : " + r.getString("kota_asal"));
                
                count++;
            }
            c.stat.close();
            c.result.close();
            
            if(count == 0) {
                JOptionPane.showMessageDialog(Content, "Cargo tidak ditemukan!");
            } else {
                JOptionPane.showMessageDialog(Content, "Cargo ditemukan");
                btnCetak.setEnabled(true);
                lblNoCargo.setText(id_cargo);
                
                c.stat = c.conn.createStatement();
                sql = "SELECT COUNT(*) as 'jumlah_barang' FROM DetilCargoManifest WHERE id_cargo = '" + id_cargo + "'";
            
                c.result = c.stat.executeQuery(sql);

                while(c.result.next()) {
                    ResultSet r = c.result;
                    lblJumlahBarang.setText(r.getString("jumlah_barang"));
                }
                c.stat.close();
                c.result.close();
                
                c.stat = c.conn.createStatement();
                sql = "SELECT SUM(pp.berat_paket) as 'total_berat' FROM DetilCargoManifest d JOIN Paket p ON d.connote = p.connote "
                        + "JOIN PermintaanPengiriman pp ON p.id_permintaanpengiriman = pp.id_permintaanpengiriman "
                        + "WHERE id_cargo = '" + id_cargo + "'";
            
                c.result = c.stat.executeQuery(sql);

                while(c.result.next()) {
                    ResultSet r = c.result;
                    lblBeratKotor.setText(r.getString("total_berat"));
                }
                c.stat.close();
                c.result.close();
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data paket "  + e);
        }
    }
    
    public JPanel getPanel() {
        return Panel;
    }
    
    private void addMetaData(Document document) {
        document.addTitle("SMU - " + txtIDCargo.getText());
        document.addSubject("SMU KMF 01");
        document.addKeywords("SMU");
        document.addAuthor("KMF 01");
        document.addCreator("KMF 01");
    }

    private void addContent(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph("SURAT MUATAN UDARA", KMF01Lib.subFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell c1 = new PdfPCell(new Paragraph(" "));
        c1.setColspan(2);
        c1.setBorder(Rectangle.TOP);
        table.addCell(c1);
        
        paragraph.add(table);
        
        KMF01Lib.addEmptyLine(paragraph, 1);

        // add a table
        createTable(paragraph);
        
        table = new PdfPTable(1);
        table.setWidthPercentage(100);
        c1 = new PdfPCell(new Paragraph(" "));
        c1.setBorder(Rectangle.BOTTOM);
        table.addCell(c1);
        
        paragraph.add(table);
        
        document.add(paragraph);
    }

    private void createTable(Paragraph para) throws BadElementException {
        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(100);

        PdfPCell c1 = new PdfPCell(new Phrase("Bandara (Dari Lapangan Udara)", KMF01Lib.smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Tujuan (Ke Lapangan Udara)", KMF01Lib.smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase(lblBandaraAsal.getText()));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(lblBandaraTujuan.getText()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        KMF01Lib.tableNewLine(table);
        
        para.add(table);
        
        table = new PdfPTable(3);
        table.setWidthPercentage(100);

        // No. Cargo
        c1 = new PdfPCell(new Phrase("Nomor Cargo", KMF01Lib.smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(10);
        c1.setPaddingBottom(15);
        table.addCell(c1);
        
        // Jumlah Barang
        c1 = new PdfPCell(new Phrase("Jumlah Barang", KMF01Lib.smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(10);
        c1.setPaddingBottom(15);
        table.addCell(c1);
        
        // Berat Kotor dalam Kg
        c1 = new PdfPCell(new Phrase("Berat Kotor dalam Kg", KMF01Lib.smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(10);
        c1.setPaddingBottom(15);
        table.addCell(c1);
        
        // No. Cargo
        c1 = new PdfPCell(new Phrase(txtIDCargo.getText()));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(20);
        c1.setPaddingBottom(25);
        table.addCell(c1);
        
        // Jumlah Barang
        c1 = new PdfPCell(new Phrase(lblJumlahBarang.getText()));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(20);
        c1.setPaddingBottom(25);
        table.addCell(c1);
        
        // Berat Kotor dalam Kg
        c1 = new PdfPCell(new Phrase(lblBeratKotor.getText()));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(20);
        c1.setPaddingBottom(25);
        table.addCell(c1);
        
        KMF01Lib.tableNewLine(table);
        
        para.add(table);
        
        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        KMF01Lib.tableNewLine(table);
        c1 = new PdfPCell(new Phrase("Rute Penerbangan", KMF01Lib.smallBold));
        c1.setColspan(3);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase(lblRuteAsal.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(lblTgl.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase(lblRuteTujuan.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(lblDi.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        para.add(table);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        Panel = new javax.swing.JPanel();
        PageTitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIDCargo = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        Content = new javax.swing.JPanel();
        Data = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblBandaraAsal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblBandaraTujuan = new javax.swing.JLabel();
        Header = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblNoCargo = new javax.swing.JLabel();
        lblJumlahBarang = new javax.swing.JLabel();
        lblBeratKotor = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblRuteAsal = new javax.swing.JLabel();
        lblRuteTujuan = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTgl = new javax.swing.JLabel();
        lblDi = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnCetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel.setBackground(new java.awt.Color(225, 228, 230));
        Panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        PageTitle.setBackground(new java.awt.Color(225, 228, 230));
        PageTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        PageTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Cetak Surat Muatan Udara");

        txtIDCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDCargoKeyPressed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PageTitleLayout = new javax.swing.GroupLayout(PageTitle);
        PageTitle.setLayout(PageTitleLayout);
        PageTitleLayout.setHorizontalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 634, Short.MAX_VALUE)
                .addComponent(txtIDCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCari)
                .addGap(21, 21, 21))
        );
        PageTitleLayout.setVerticalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIDCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        Panel.add(PageTitle);

        Content.setBackground(new java.awt.Color(225, 228, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Content.setPreferredSize(new java.awt.Dimension(1136, 400));
        Content.setLayout(new javax.swing.BoxLayout(Content, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel5.setMinimumSize(new java.awt.Dimension(100, 0));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Surat Muatan Udara");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 554, 9, 528);
        jPanel5.add(jLabel2, gridBagConstraints);

        jPanel4.add(jPanel5);

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel7.setPreferredSize(new java.awt.Dimension(686, 60));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jPanel8.setLayout(new java.awt.GridLayout(2, 0));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Berangkat (Dari Lapangan Udara)");
        jPanel8.add(jLabel8);

        lblBandaraAsal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel8.add(lblBandaraAsal);

        jPanel7.add(jPanel8);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel3);

        jPanel1.setMaximumSize(new java.awt.Dimension(300, 32476));
        jPanel1.setPreferredSize(new java.awt.Dimension(185, 10));
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Tujuan (Dari Lapangan Udara)");
        jPanel1.add(jLabel9);

        lblBandaraTujuan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblBandaraTujuan);

        jPanel7.add(jPanel1);

        jPanel4.add(jPanel7);

        Header.setMaximumSize(new java.awt.Dimension(32767, 50));
        Header.setPreferredSize(new java.awt.Dimension(1116, 50));
        Header.setLayout(new java.awt.GridLayout(1, 3));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Nomor Cargo");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Header.add(jLabel12);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Jumlah Barang");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Header.add(jLabel11);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Berat Kotor dalam Kg");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Header.add(jLabel10);

        jPanel4.add(Header);

        jPanel6.setMaximumSize(new java.awt.Dimension(32767, 100));
        jPanel6.setPreferredSize(new java.awt.Dimension(108, 100));
        jPanel6.setLayout(new java.awt.GridLayout(1, 3));

        lblNoCargo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNoCargo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNoCargo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(lblNoCargo);

        lblJumlahBarang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblJumlahBarang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJumlahBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(lblJumlahBarang);

        lblBeratKotor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblBeratKotor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBeratKotor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(lblBeratKotor);

        jPanel4.add(jPanel6);

        jPanel9.setMaximumSize(new java.awt.Dimension(32767, 120));
        jPanel9.setLayout(new java.awt.GridLayout(1, 2));

        jPanel11.setMaximumSize(new java.awt.Dimension(32767, 200));
        jPanel11.setPreferredSize(new java.awt.Dimension(558, 200));
        jPanel11.setLayout(new java.awt.GridLayout(3, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Rute Penerbangan");
        jPanel11.add(jLabel4);

        lblRuteAsal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRuteAsal.setText("Dari");
        jPanel11.add(lblRuteAsal);

        lblRuteTujuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRuteTujuan.setText("Ke");
        jPanel11.add(lblRuteTujuan);

        jPanel9.add(jPanel11);

        jPanel10.setLayout(new java.awt.GridLayout(3, 0));
        jPanel10.add(jLabel5);

        lblTgl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTgl.setText("Dibuat pada (tanggal)");
        jPanel10.add(lblTgl);

        lblDi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDi.setText("Di (tempat)");
        jPanel10.add(lblDi);

        jPanel9.add(jPanel10);

        jPanel4.add(jPanel9);

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel2.setPreferredSize(new java.awt.Dimension(1116, 80));

        btnCetak.setText("Cetak SMU");
        btnCetak.setEnabled(false);
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(966, Short.MAX_VALUE)
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2);

        javax.swing.GroupLayout DataLayout = new javax.swing.GroupLayout(Data);
        Data.setLayout(DataLayout);
        DataLayout.setHorizontalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DataLayout.setVerticalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Content.add(Data);

        Panel.add(Content);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 1236, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();

            FileFilter filter = new FileNameExtensionFilter("PDF File", "pdf");
            chooser.setFileFilter(filter);
            int returnVal1= chooser.showSaveDialog(this);
            if (returnVal1 == JFileChooser.APPROVE_OPTION)
            {
                File file = chooser.getSelectedFile();
                if(!file.getPath().toLowerCase().endsWith(".pdf"))
                {
                    file = new File(file + ".pdf");
                }

                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                    Rectangle page = new Rectangle(700,400);
                    document.setPageSize(page);
                    document.open();

                    addMetaData(document);
                    addContent(document);
                    document.close();
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
                
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        loadData(txtIDCargo.getText());
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtIDCargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDCargoKeyPressed
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            loadData(txtIDCargo.getText());
        }
    }//GEN-LAST:event_txtIDCargoKeyPressed

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
            java.util.logging.Logger.getLogger(CetakSMU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CetakSMU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CetakSMU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CetakSMU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CetakSMU().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Data;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel PageTitle;
    private javax.swing.JPanel Panel;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCetak;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblBandaraAsal;
    private javax.swing.JLabel lblBandaraTujuan;
    private javax.swing.JLabel lblBeratKotor;
    private javax.swing.JLabel lblDi;
    private javax.swing.JLabel lblJumlahBarang;
    private javax.swing.JLabel lblNoCargo;
    private javax.swing.JLabel lblRuteAsal;
    private javax.swing.JLabel lblRuteTujuan;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JTextField txtIDCargo;
    // End of variables declaration//GEN-END:variables
}
