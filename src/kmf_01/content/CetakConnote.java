/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01.content;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import kmf_01.DBConnect;
import kmf_01.KMF01Lib;

/**
 *
 * @author samod
 */
public class CetakConnote extends javax.swing.JFrame {
    DBConnect connection = new DBConnect("KMF_01");
    
    /**
     * Creates new form Navbar
     */
    public CetakConnote() {
        initComponents();
    }
    
    
    public JPanel getPanel() {
        return CetakConnote;
    }
    
    private void addMetaData(Document document) {
        document.addTitle("Connote - " + txtIdConnote.getText());
        document.addSubject("Cetak Connote KMF 01");
        document.addKeywords("Cetak Connote");
        document.addAuthor("KMF 01");
        document.addCreator("KMF 01");
    }

    private void addContent(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph("PT. KMF", KMF01Lib.subFont);
        Paragraph connote = new Paragraph("No. Resi : " + txtIdConnote.getText(), KMF01Lib.smallBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        connote.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(connote);
        
        KMF01Lib.addEmptyLine(paragraph, 2);

        // add a table
        createTable(paragraph);

        paragraph.add(new Paragraph("Tgl. Kirim : " + txtTanggal.getText()));
        paragraph.add(new Paragraph("Deskripsi : " + txtJenisBarang.getText()));
        paragraph.add(new Paragraph("Berat : " + txtBerat.getText() + " Kg"));
        paragraph.add(new Paragraph("Jenis Pengiriman : " + txtJenisPengiriman.getText()));
        paragraph.add(new Paragraph("Biaya Kirim : " + txtBiayaKirim.getText()));
        
        PdfPTable table = new PdfPTable(1);

        table.setWidthPercentage(100);
        PdfPCell c1 = new PdfPCell(new Paragraph(" "));
        c1.setBorder(Rectangle.BOTTOM);
        table.addCell(c1);
        
        paragraph.add(table);
        
        document.add(paragraph);
    }

    private void createTable(Paragraph para) throws BadElementException {
        PdfPTable table = new PdfPTable(2);

        PdfPCell c1 = new PdfPCell(new Paragraph(" "));
        c1.setColspan(2);
        c1.setBorder(Rectangle.TOP);
        table.addCell(c1);
        
        table.setWidthPercentage(100);

        c1 = new PdfPCell(new Phrase("Pengirim", KMF01Lib.smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Penerima", KMF01Lib.smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        table.setHeaderRows(1);
        // Pengirim
        c1 = new PdfPCell(new Phrase(txtNamaPengirim.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        // Penerima
        c1 = new PdfPCell(new Phrase(txtNamaPenerima.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
        
        // Alamat Asal
        c1 = new PdfPCell(new Phrase(txtAlamatAsal.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        // Alamat Tujuan
        c1 = new PdfPCell(new Phrase(txtAlamatTujuan.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
        
        // Kota Asal
        c1 = new PdfPCell(new Phrase(txtKotaAsal.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        // Kota Tujuan
        c1 = new PdfPCell(new Phrase(txtKotaTujuan.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
        
        KMF01Lib.tableNewLine(table);
        
        // No Telp Pengirim
        c1 = new PdfPCell(new Phrase(txtTlpPengirim.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        // No Telp. Penerima
        c1 = new PdfPCell(new Phrase(txtTlpPenerima.getText()));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
        
        KMF01Lib.tableNewLine(table);
        KMF01Lib.tableNewLine(table);
        
        c1 = new PdfPCell(new Paragraph(" "));
        c1.setColspan(2);
        c1.setBorder(Rectangle.TOP);
        table.addCell(c1);
        
        para.add(table);
    }

    private void ClearForm() {
        txtIdCari.setText("");
        txtIdConnote.setText("");
        txtIdPelanggan.setText("");
        txtNamaPengirim.setText("");
        txtKotaAsal.setText("");
        txtTlpPengirim.setText("");
        txtAlamatAsal.setText("");
        txtNamaPenerima.setText("");
        txtTlpPenerima.setText("");
        txtKotaTujuan.setText("");
        txtAlamatTujuan.setText("");
        txtTanggal.setText("");
        txtBerat.setText("");
        txtBiayaKirim.setText("");
        txtJenisBarang.setText("");
        txtJenisPengiriman.setText("");
    }
    
    private void SaveFile(File file) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            Rectangle page = new Rectangle(700,500);
            document.setPageSize(page);
            document.open();

            addMetaData(document);
            addContent(document);
            document.close();
                
            JOptionPane.showMessageDialog(this, "Connote berhasil dicetak", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
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

        CetakConnote = new javax.swing.JPanel();
        PageTitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtIdCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        Content = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Data = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtNamaPenerima = new javax.swing.JTextField();
        txtTlpPenerima = new javax.swing.JTextField();
        txtKotaTujuan = new javax.swing.JTextField();
        txtAlamatTujuan = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtJenisBarang = new javax.swing.JTextField();
        txtBerat = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtJenisPengiriman = new javax.swing.JTextField();
        txtBiayaKirim = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtIdPelanggan = new javax.swing.JTextField();
        txtNamaPengirim = new javax.swing.JTextField();
        txtTlpPengirim = new javax.swing.JTextField();
        txtKotaAsal = new javax.swing.JTextField();
        txtAlamatAsal = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        txtIdConnote = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnCetak = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        CetakConnote.setBackground(new java.awt.Color(225, 228, 230));
        CetakConnote.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        CetakConnote.setMinimumSize(new java.awt.Dimension(0, 0));
        CetakConnote.setVerifyInputWhenFocusTarget(false);
        CetakConnote.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                CetakConnoteHierarchyChanged(evt);
            }
        });
        CetakConnote.setLayout(new javax.swing.BoxLayout(CetakConnote, javax.swing.BoxLayout.Y_AXIS));

        PageTitle.setBackground(new java.awt.Color(225, 228, 230));
        PageTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        PageTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Cetak Connote");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel22.setText("ID Connote");

        txtIdCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCariActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(txtIdCari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCari)
                .addContainerGap())
        );
        PageTitleLayout.setVerticalGroup(
            PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PageTitleLayout.createSequentialGroup()
                .addGroup(PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, PageTitleLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtIdCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PageTitleLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PageTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(jLabel1)
                                .addComponent(jLabel22))
                            .addComponent(btnCari, javax.swing.GroupLayout.Alignment.CENTER))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        CetakConnote.add(PageTitle);

        Content.setBackground(new java.awt.Color(225, 228, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Content.setPreferredSize(new java.awt.Dimension(1136, 400));
        Content.setLayout(new javax.swing.BoxLayout(Content, javax.swing.BoxLayout.LINE_AXIS));

        Data.setLayout(new javax.swing.BoxLayout(Data, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 650));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 30));
        jPanel7.setPreferredSize(new java.awt.Dimension(686, 30));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("Consignment Note");
        jPanel7.add(jLabel8, new java.awt.GridBagConstraints());

        jPanel4.add(jPanel7);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setPreferredSize(new java.awt.Dimension(1097, 500));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel9.setText("Penerima");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Nama Penerima");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Telepon");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Kota ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Alamat");

        txtNamaPenerima.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNamaPenerima.setEnabled(false);

        txtTlpPenerima.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTlpPenerima.setEnabled(false);

        txtKotaTujuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKotaTujuan.setEnabled(false);

        txtAlamatTujuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAlamatTujuan.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNamaPenerima)
                            .addComponent(txtTlpPenerima)
                            .addComponent(txtKotaTujuan)
                            .addComponent(txtAlamatTujuan))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtTlpPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtKotaTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txtAlamatTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Tanggal");

        txtTanggal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTanggal.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Jenis barang");

        txtJenisBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtJenisBarang.setEnabled(false);

        txtBerat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBerat.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Berat ");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Kg");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Jenis pengiriman");

        txtJenisPengiriman.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtJenisPengiriman.setEnabled(false);

        txtBiayaKirim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBiayaKirim.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Biaya kirim");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel21.setText("Rincian");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(96, 96, 96)
                                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel16)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel17))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(txtBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel18))
                                    .addComponent(txtJenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBiayaKirim, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel21)))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtJenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtBiayaKirim, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel15))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Pengirim");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("ID Pelanggan");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Nama Pengirim");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Telepon");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Alamat");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Kota ");

        txtIdPelanggan.setEditable(false);
        txtIdPelanggan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIdPelanggan.setEnabled(false);

        txtNamaPengirim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNamaPengirim.setEnabled(false);

        txtTlpPengirim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTlpPengirim.setEnabled(false);

        txtKotaAsal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKotaAsal.setEnabled(false);

        txtAlamatAsal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAlamatAsal.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel3)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaPengirim, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel23)
                            .addComponent(jLabel7))
                        .addGap(104, 104, 104)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAlamatAsal)
                            .addComponent(txtTlpPengirim)
                            .addComponent(txtKotaAsal))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtIdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNamaPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTlpPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKotaAsal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAlamatAsal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18))
        );

        txtIdConnote.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIdConnote.setEnabled(false);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel24.setText("ID Connote");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(44, 44, 44)
                .addComponent(txtIdConnote, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdConnote, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel10.setMinimumSize(new java.awt.Dimension(118, 45));
        jPanel10.setPreferredSize(new java.awt.Dimension(0, 45));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        btnCetak.setText("Cetak");
        btnCetak.setMaximumSize(new java.awt.Dimension(100, 35));
        btnCetak.setMinimumSize(new java.awt.Dimension(100, 35));
        btnCetak.setPreferredSize(new java.awt.Dimension(100, 35));
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        jPanel10.add(btnCetak);

        btnBatal.setText("Batal");
        btnBatal.setMaximumSize(new java.awt.Dimension(100, 35));
        btnBatal.setMinimumSize(new java.awt.Dimension(100, 35));
        btnBatal.setPreferredSize(new java.awt.Dimension(0, 35));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        jPanel10.add(btnBatal);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 76, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.add(jPanel1);

        jPanel4.add(jPanel6);

        Data.add(jPanel4);

        jScrollPane2.setViewportView(Data);

        Content.add(jScrollPane2);

        CetakConnote.add(Content);

        getContentPane().add(CetakConnote);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try{
            int count = 0;
            String sql = "SELECT connote, jenis_barang, tgl_masuk, id_permintaanpengiriman FROM Paket WHERE connote =?";
            connection.pstat = connection.conn.prepareStatement(sql);
            connection.pstat.setString(1, txtIdCari.getText());
            connection.result = connection.pstat.executeQuery();
            while(connection.result.next())
            {
                count++;
                txtIdConnote.setText(connection.result.getString("connote"));
                txtJenisBarang.setText(connection.result.getString("jenis_barang"));
                txtTanggal.setText(sdf.format(connection.result.getDate("tgl_masuk")));
            }
            connection.stat.close();
            connection.result.close();
            
            if(count > 0) {
                String sql2 = "SELECT * FROM Paket p JOIN PermintaanPengiriman pp ON p.id_permintaanpengiriman=pp.id_permintaanpengiriman WHERE p.connote=?";
                connection.pstat = connection.conn.prepareStatement(sql2);
                connection.pstat.setString(1, txtIdCari.getText());
                connection.result = connection.pstat.executeQuery();
                while(connection.result.next())
                {
                    txtKotaAsal.setText(connection.result.getString("kota_asal"));
                    txtAlamatAsal.setText(connection.result.getString("alamat_asal"));
                    txtTlpPenerima.setText(connection.result.getString("telepon_penerima"));
                    txtNamaPenerima.setText(connection.result.getString("nama_penerima"));
                    txtKotaTujuan.setText(connection.result.getString("kota_tujuan"));
                    txtAlamatTujuan.setText(connection.result.getString("alamat_tujuan"));
                    txtJenisPengiriman.setText(connection.result.getString("jenis_pengiriman"));
                    txtBerat.setText(connection.result.getString("berat_paket"));
                    txtBiayaKirim.setText(formatter.format(connection.result.getDouble("biaya_kirim")));
                    txtIdPelanggan.setText(connection.result.getString("id_pelanggan"));
                }
                connection.stat.close();
                connection.result.close();

                String sql3 = "SELECT * FROM Pelanggan p WHERE p.id_pelanggan=?";
                connection.pstat = connection.conn.prepareStatement(sql3);
                connection.pstat.setString(1, txtIdPelanggan.getText());
                connection.result = connection.pstat.executeQuery();
                while(connection.result.next())
                {
                    txtNamaPengirim.setText(connection.result.getString("nama_pelanggan"));
                    txtTlpPengirim.setText(connection.result.getString("nohp_pelanggan"));
                }


                connection.stat.close();
                connection.result.close();
                
            } else {
                JOptionPane.showMessageDialog(this, "Data paket tidak ditemukan", "Gagal",  JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e)
        {
            System.out.println("Terjadi error saat load data connote: " + e);
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtIdCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCariActionPerformed

    private void CetakConnoteHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_CetakConnoteHierarchyChanged
//        ClearForm();
    }//GEN-LAST:event_CetakConnoteHierarchyChanged

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

                SaveFile(file);
                
                ClearForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        ClearForm();
    }//GEN-LAST:event_btnBatalActionPerformed

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
            java.util.logging.Logger.getLogger(CetakConnote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CetakConnote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CetakConnote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CetakConnote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new CetakConnote().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CetakConnote;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Data;
    private javax.swing.JPanel PageTitle;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCetak;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtAlamatAsal;
    private javax.swing.JTextField txtAlamatTujuan;
    private javax.swing.JTextField txtBerat;
    private javax.swing.JTextField txtBiayaKirim;
    private javax.swing.JTextField txtIdCari;
    private javax.swing.JTextField txtIdConnote;
    private javax.swing.JTextField txtIdPelanggan;
    private javax.swing.JTextField txtJenisBarang;
    private javax.swing.JTextField txtJenisPengiriman;
    private javax.swing.JTextField txtKotaAsal;
    private javax.swing.JTextField txtKotaTujuan;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNamaPengirim;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTlpPenerima;
    private javax.swing.JTextField txtTlpPengirim;
    // End of variables declaration//GEN-END:variables
}
