/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmf_01;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author samod
 */
public class KMF01Lib {
    public static final Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    public static final Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    public static final Font subFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    public static final Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    
    public static void MenuLabel(JLabel menu, String page, CardLayout contentLayout, JPanel Content) {
        menu.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e)  
            {  
                contentLayout.show(Content, page);
            }  
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menu.setBackground(new Color(43, 43, 43));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menu.setBackground(new Color(51,51,51));
            }
            
        }); 
    }
    
    public static void UpdateHistory(String connote, String status) {
        try {
            DBConnect connection = new DBConnect("KMF_01");
            String query = "INSERT INTO History (connote, isi_history, tgl_history) VALUES (?, ?, ?)";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, connote);
            connection.pstat.setString(2, status);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
            connection.pstat.setTimestamp(3, timestamp);

            connection.pstat.executeUpdate();
            connection.pstat.close();
        } catch(SQLException e) {
            System.out.println("Gagal update history : " + e);
        }
    }
    
    public static void UpdateStatus(String connote, String status) {
        try {
            DBConnect connection = new DBConnect("KMF_01");
            String query = "UPDATE Paket SET status_paket=? WHERE connote=?";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, status);
            connection.pstat.setString(2, connote);

            connection.pstat.executeUpdate();
            connection.pstat.close();
        } catch(SQLException e) {
            System.out.println("Gagal update status paket : " + e);
        }
    }
    
    public static void tableNewLine(PdfPTable table) {
        
        // New Empty Line
        PdfPCell c1 = new PdfPCell(new Phrase(" "));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        
        // New Empty Line
        c1 = new PdfPCell(new Phrase(" "));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
    }
    
    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
