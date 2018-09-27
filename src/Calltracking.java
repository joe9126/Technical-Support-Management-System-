
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.tools.Executable;
import java.awt.Image;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JOE
 */

public class Calltracking extends javax.swing.JFrame {
 SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
 String currentuser;String directory,clientnames; 
 DefaultComboBoxModel  staffnamecomboModel=new DefaultComboBoxModel();
java.sql.Date callfromdate,calltodate; String staffnamesearch;
    Home access=new Home(); String staffname;
 DefaultComboBoxModel clientnamecomboModel=new DefaultComboBoxModel();   
         Document   receiptDoc;
    String[] cols3={"No.","CALL NO","CALL DATE","SERVICE NO","SERVICE DATE","FROM","TO","CLIENT","EQUIP. MODEL","SERIAL","LOCATION","FAULT","ACTION","STATUS","DONE BY"};   
 Image companylogo;
 DefaultTableModel servicetableModel=new DefaultTableModel(cols3,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    };  
    /**
     * Creates new form Calltracking
     */
    public Calltracking() {
        initComponents();
         servicerecTable.getTableHeader().setReorderingAllowed(false);
           servicerecTable.getColumnModel().getColumn(0).setPreferredWidth(1);
    }
    public void getStaffs(){
        try {
            String getcontractsql="SELECT STAFFNAME FROM STAFF ORDER BY STAFFNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            staffnamecomboModel.removeAllElements();
            while(rst.next()){
      staffnamecomboModel.addElement(rst.getString(1)); 
                i++;  
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
}
 /*      
public void getClients(){
        try {
            String getcontractsql="SELECT CLIENTNAME FROM CLIENTS WHERRE CLIENTNAME LIKE '"+clientnameTxt.getText()+"%' ORDER BY CLIENTNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            clientnamecomboModel.removeAllElements();
            while(rst.next()){
      clientnamecomboModel.addElement(rst.getString(1)); 
                i++;  
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
}*/

public void getServiceRecords(){
      
    int g=servicerecTable.getRowCount();
    for(int f=0;f<g;f++){
servicetableModel.removeRow(0);
    }
     String getcallssql;  
 if(!callsearchTxt.getText().equalsIgnoreCase("Search Call Number")){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE CALLS.CALL_NO LIKE '"+callsearchTxt.getText()+"%'"
         + " ORDER BY SERVICE.SERVICE_DATE DESC";
 } 
   else if(callfromdate!=null&&calltodate!=null&& !staffnoTxt.getText().isEmpty()&& !clientnameTxt.getText().isEmpty()){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE CALLS.CALL_DATE >='"+callfromdate+"' AND CALLS.CALL_DATE <='"+calltodate+"' AND CALLS.TECHNICIAN ='"+staffnoTxt.getText()+"'"
          + "AND CLIENTS.CLIENTNAME LIKE '"+clientnameTxt.getText()+"%' "
       
         + " ORDER BY SERVICE.SERVICE_DATE DESC";
 
 } 
 else if(clientnameTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
         + " WHERE CALLS.CALL_DATE >='"+callfromdate+"' AND CALLS.CALL_DATE <='"+calltodate+"' AND CLIENTS.CLIENTNAME LIKE '"+clientnameTxt.getText()+"%'"
      + " ORDER BY SERVICE.SERVICE_DATE DESC";
 
 }
 
  else if(callfromdate!=null&&calltodate!=null&& staffnoTxt.getText().trim().length()!=0){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE SERVICE.SERVICE_DATE >='"+callfromdate+"' AND SERVICE.SERVICE_DATE <='"+calltodate+"' AND CALLS.TECHNICIAN LIKE '"+staffnoTxt.getText()+"%' "
       
         + " ORDER BY SERVICE.SERVICE_DATE DESC";
 
 }
 
  else if(callfromdate!=null&&calltodate!=null){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE CALLS.CALL_DATE >='"+callfromdate+"' AND CALLS.CALL_DATE <='"+calltodate+"' "
       
         + " ORDER BY SERVICE.SERVICE_DATE DESC";
 
 }
else if(clientnameTxt.getText().trim().length()!=0){
   getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
         + " WHERE CLIENTS.CLIENTNAME LIKE '"+clientnameTxt.getText()+"%'"
      + " ORDER BY SERVICE.SERVICE_DATE DESC";
  }
  else if(staffnoTxt.getText().trim().length()!=0){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE CALLS.TECHNICIAN ='"+staffnoTxt.getText()+"'"
       
         + " ORDER BY SERVICE.SERVICE_DATE DESC";
 }
 else{
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME,CALLS.CALL_DATE, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                      + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
      + " ORDER BY SERVICE.SERVICE_DATE DESC";
 
 }
    try {
 
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,   access.username,  access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcallssql);
            ResultSet rst=pst.executeQuery(); int i=0;
             while(rst.next()){
 if(rst.getDate(14)!=null&&rst.getDate(3)!=null){
  servicetableModel.addRow(new Object[]{i+1,rst.getString(1),sdf.format(rst.getDate(14)),rst.getString(2),sdf.format(rst.getDate(3)),rst.getString(4),rst.getString(5),rst.getString( 13)
  ,rst.getString(7),rst.getString(8),rst.getString(9),rst.getString(10),rst.getString(11),rst.getString(12),rst.getString(15)}); 
  
 }         
   i++;  
  }
    System.out.println(i+" records found ");
             if(i<1){
 int u= servicerecTable.getRowCount();
 for(int b=0;b<u;b++){
 servicetableModel.removeRow(0);
 } 
             }
        } catch (SQLException ex) {
            Logger.getLogger(Userprofile.class.getName()).log(Level.SEVERE, null, ex);
        }  
 // callfromdate=null; calltodate=null;
   
  }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calltrackingPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        callsearchTxt = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        servicerecTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        staffnoTxt = new javax.swing.JTextField();
        staffnameTxt = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        clientnameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        calltrackingPanel.setBackground(new java.awt.Color(153, 0, 153));
        calltrackingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TRACK CALLS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        callsearchTxt.setText("SEARCH CALL NUMBER");
        callsearchTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                callsearchTxtMouseClicked(evt);
            }
        });
        callsearchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                callsearchTxtKeyReleased(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CALL RECORDS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        servicerecTable.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        servicerecTable.setModel(servicetableModel);
        servicerecTable.setFillsViewportHeight(true);
        servicerecTable.setGridColor(new java.awt.Color(153, 0, 153));
        servicerecTable.setRowHeight(25);
        servicerecTable.setRowMargin(2);
        servicerecTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        jScrollPane3.setViewportView(servicerecTable);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Service Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
        });

        jLabel1.setText("FROM:");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jLabel2.setText("TO:");

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Staff", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });

        jLabel3.setText("STAFF NAME: ");

        jLabel4.setText("STAFF NO:");

        staffnameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                staffnameTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                staffnameTxtKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(staffnameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(staffnoTxt)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(staffnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(staffnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Print Service Records", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jButton1.setText("PRINT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jButton1)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Client", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        clientnameTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientnameTxtMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clientnameTxtMouseExited(evt);
            }
        });
        clientnameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientnameTxtKeyReleased(evt);
            }
        });

        jLabel5.setText("CLIENT:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientnameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(callsearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(209, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(callsearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout calltrackingPanelLayout = new javax.swing.GroupLayout(calltrackingPanel);
        calltrackingPanel.setLayout(calltrackingPanelLayout);
        calltrackingPanelLayout.setHorizontalGroup(
            calltrackingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calltrackingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1347, Short.MAX_VALUE)
                .addContainerGap())
        );
        calltrackingPanelLayout.setVerticalGroup(
            calltrackingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(calltrackingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(calltrackingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void callsearchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_callsearchTxtMouseClicked
       callsearchTxt.setText(null); 
    }//GEN-LAST:event_callsearchTxtMouseClicked

    private void callsearchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_callsearchTxtKeyReleased
calltodate=null; callfromdate=null; staffnoTxt.setText(null); 
        getServiceRecords();
    }//GEN-LAST:event_callsearchTxtKeyReleased

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
 staffnoTxt.setText(null); staffnameTxt.setText(null); 
   callfromdate=new java.sql.Date(jXDatePicker1.getDate().getTime());
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
      calltodate=new java.sql.Date(jXDatePicker2.getDate().getTime());
      staffnoTxt.setText(null); 
       getServiceRecords();
    }//GEN-LAST:event_jXDatePicker2ActionPerformed

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
  callsearchTxt.setText("SEARCH CALL NUMBER");clientnames=null;clientnameTxt.setText(null);
 // calltodate=null;callfromdate=null;
  getStaffs();
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
     callsearchTxt.setText("SEARCH CALL NUMBER");
    }//GEN-LAST:event_jPanel3MouseEntered

    private void staffnameTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_staffnameTxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_staffnameTxtKeyTyped

    private void staffnameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_staffnameTxtKeyReleased
        staffname=staffnameTxt.getText();
      try {
    String getcontractsql;
          
  
    
    getcontractsql="SELECT STAFFNO,STAFFNAME,DEPT,POST FROM STAFF WHERE STAFFNAME LIKE '"+staffname+"%'";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
           while(rst.next()){
     staffnoTxt.setText(rst.getString(1));  
staffnamesearch=rst.getString(2);
     i++;  
        
           }
            getServiceRecords();
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_staffnameTxtKeyReleased

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
     staffnameTxt.setText(staffnamesearch); 
    }//GEN-LAST:event_jPanel4MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      int f=servicerecTable.getRowCount();
      if(f<1){
  JOptionPane.showMessageDialog(null, "No service records to print", "No Records", JOptionPane.ERROR_MESSAGE);
      }else{
              FileOutputStream filename=null; 
     receiptDoc=new Document(PageSize.A4.rotate()); 
      JFileChooser filesaver=new JFileChooser();
       filesaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int option=filesaver.showSaveDialog(calltrackingPanel);
          if(option==JFileChooser.APPROVE_OPTION){
    try {
        String   dir=filesaver.getSelectedFile().toString();
        try {
            Connection connectDb=(Connection)DriverManager.getConnection(access.server, access.username,access.dbpword);
          String sqllogo="SELECT LOGO FROM SETTINGS";
            com.mysql.jdbc.PreparedStatement pstlogo=(com.mysql.jdbc.PreparedStatement) connectDb.prepareStatement(sqllogo);
            ResultSet rst=pstlogo.executeQuery();
            
            if(rst!=null){
            while(rst.next()){
            byte[] byt = null;
            /*sacconame=rst.getString(1);
            saccoaddress=rst.getString(3);
            saccomotto=rst.getString(2);
            saccotel=rst.getString(6);
            email=rst.getString(7);
            mobile=rst.getString(8);*/
            byt=rst.getBytes(1);
            companylogo = Toolkit.getDefaultToolkit().createImage(byt);
            }
            }
            
        if(staffnoTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null)     {
                directory=dir+"/"+staffnameTxt.getText()+" SERVICE RECORDS FROM "+callfromdate+" TO "+calltodate+".pdf";
                filename = new FileOutputStream(directory);
            }
         else if(clientnameTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null)     {
                directory=   dir+"/"+clientnameTxt.getText()+" SERVICE RECORDS FROM "+callfromdate+" TO "+calltodate+".pdf";
                filename = new FileOutputStream(directory);
            }
        else if(staffnoTxt.getText().trim().length()!=0) {
                directory=dir+"/"+staffnameTxt.getText()+" SERVICE RECORDS.pdf";
                filename = new FileOutputStream(directory);
            }
            else if(clientnameTxt.getText().trim().length()!=0){
                directory=dir+"/"+clientnameTxt.getText()+" SERVICE RECORDS.pdf";
                filename = new FileOutputStream(directory);
            }
           
        else  if(callfromdate!=null&&calltodate!=null){
                directory=dir+"/TS SERVICE RECORDS FROM "+callfromdate+" TO "+calltodate+".pdf";
                filename = new FileOutputStream(directory);
            }  
            
            PdfWriter writer=PdfWriter.getInstance(receiptDoc ,filename);
            receiptDoc.open();
            Paragraph space=new Paragraph("          ");
            com.itextpdf.text.Font font1=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,22,Font.BOLD);
            com.itextpdf.text.Font font2=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,8,Font.BOLD);
            com.itextpdf.text.Font font3=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,15,Font.BOLD);
            com.itextpdf.text.Font font4=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.HELVETICA,7,Font.LAYOUT_LEFT_TO_RIGHT);
            
            String printdate=new SimpleDateFormat("dd MMM, yyyy").format(Calendar.getInstance().getTime());
            Paragraph date=new Paragraph( "  Printed: "+printdate,font4);
            date.setAlignment(Paragraph.ALIGN_RIGHT);
            receiptDoc.add(date);
            
          com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(  companylogo , null); 
image.setAbsolutePosition(40, 500);
         image.scaleToFit(200,80);
 writer.getDirectContent().addImage(image);  receiptDoc.add(space);   receiptDoc.add(space); 
  
            //Paragraph schname=new Paragraph( sacconame,font4); schname.setAlignment(Paragraph.ALIGN_CENTER);
              String separator="____________________________________________________________________________________________________";
            Paragraph separatorlable=new Paragraph(separator,font2);
               separatorlable.setAlignment(Paragraph.ALIGN_CENTER);
              //  receiptDoc.add(separatorlable);
   String separator2="___________________________________________________________________________________________________________________________________________________________________________________________";
            Paragraph separatorlable3=new Paragraph(separator2,font2);
               separatorlable.setAlignment(Paragraph.ALIGN_CENTER);
               
            Paragraph receiptlabel=new Paragraph("TECHNICAL SERVICE DEPARTMENT     ",font1);
            receiptlabel.setAlignment(Paragraph.ALIGN_CENTER);  receiptDoc.add(receiptlabel);
             receiptDoc.add(separatorlable3); 
         receiptDoc.add(space);
         
   float[] colswidth;      com.itextpdf.text.pdf.PdfPTable subjreportTable = null;
   
     
            
           if(staffnoTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null)     {
              
            Paragraph receiptlabel2=new Paragraph(" STAFF SERVICE REPORT FROM:  "+sdf.format(callfromdate)+"   TO:   "+sdf.format(calltodate),font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);      receiptDoc.add(separatorlable3);   
               Paragraph receiptlabel3=new Paragraph(" STAFF NO:"+staffnoTxt.getText()+"    NAME: "+staffnameTxt.getText(),font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel3);
                  receiptDoc.add(separatorlable3);
                  
                   colswidth = new float[]{2f,4f,4f,5f,5f,4f,4f,7f,6f,6f,5f,5f,8f,4f };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
          subjreportTable .setWidthPercentage(108);  
                
            }else if(clientnameTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null)     {
      Paragraph receiptlabel21=new Paragraph(" CLIENT SERVICE REPORT   ",font3);
                receiptlabel21.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel21);  
                
                Paragraph receiptlabel3=new Paragraph( " CLIENT NAME: "+clientnameTxt.getText(),font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel3);      // receiptDoc.add(separatorlable3);
                Paragraph receiptlabel2=new Paragraph("  FROM:  "+sdf.format(callfromdate)+"   TO:   "+sdf.format(calltodate),font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);      receiptDoc.add(separatorlable3);
                
          colswidth = new float[]{2f,4f,4f,5f,5f,4f,4f,7f,6f,6f,5f,5f,8f,4f,6f };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
         subjreportTable .setWidthPercentage(108);        
            }
            else if(staffnoTxt.getText().trim().length()!=0) {
                 Paragraph receiptlabel2=new Paragraph("  GENERAL STAFF SERVICE REPORT  ",font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);   receiptDoc.add(separatorlable3);
                
                Paragraph receiptlabel3=new Paragraph(" STAFF NO:"+staffnoTxt.getText()+"    NAME: "+staffnameTxt.getText(),font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel3);      receiptDoc.add(separatorlable3);
             
                colswidth = new float[]{2f,4f,4f,5f,5f,4f,4f,7f,6f,6f,5f,5f,8f,4f };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
              subjreportTable .setWidthPercentage(108);  
             }
            else if(clientnameTxt.getText().trim().length()!=0){
          Paragraph receiptlabel2=new Paragraph("  GENERAL CLIENT SERVICE REPORT  ",font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);    receiptDoc.add(separatorlable3);      
                
                Paragraph receiptlabel3=new Paragraph( " CLIENT NAME: "+clientnameTxt.getText(),font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel3);       receiptDoc.add(separatorlable3);
               
                   
                    colswidth = new float[]{2f,4f,4f,5f,5f,4f,4f,7f,6f,6f,5f,5f,8f,4f,6f };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
                subjreportTable .setWidthPercentage(108);  
            }
            else  if(callfromdate!=null&&calltodate!=null){
                 Paragraph receiptlabel45=new Paragraph("  GENERAL SERVICE REPORT  ",font3);
                receiptlabel45.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel45);    receiptDoc.add(separatorlable3);  
                
                Paragraph receiptlabel2=new Paragraph("  FROM:  "+sdf.format(callfromdate)+"   TO:   "+sdf.format(calltodate),font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);      receiptDoc.add(separatorlable3);
                
                 colswidth = new float[]{2f,4f,4f,5f,5f,4f,4f,7f,6f,6f,5f,5f,8f,4f,6f };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
    subjreportTable .setWidthPercentage(108);       
            }
             receiptDoc.add(space); receiptDoc.add(space);
          
           Paragraph Paras=new Paragraph("No.",font2);
            Paras.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras1=new Paragraph("Call No.",font2);
            Paras1.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras2=new Paragraph("Call Date",font2);
            Paras2.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras21=new Paragraph("Serv. No",font2);
            Paras21.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras33=new Paragraph("Serv. Date",font2);
            Paras33.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras34=new Paragraph("From",font2);
            Paras34.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras35=new Paragraph("To",font2);
            Paras35.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras36=new Paragraph("CLIENT",font2);
            Paras36.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras37=new Paragraph("EQUIP MODEL",font2);
            Paras37.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras38=new Paragraph("SERIAL#",font2);
            Paras38.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras39=new Paragraph("LOCATION",font2);
            Paras39.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras40=new Paragraph("FAULT",font2);
            Paras40.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras41=new Paragraph("ACTION",font2);
            Paras41.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras42=new Paragraph("STATUS",font2);
            Paras42.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras43=new Paragraph("DONE BY",font2);
            Paras43.getFont().setStyle(Font.BOLD);
            
            
            subjreportTable.addCell(Paras);subjreportTable.addCell(Paras1);subjreportTable.addCell(Paras2);
            subjreportTable.addCell(Paras21);   subjreportTable.addCell(Paras33);subjreportTable.addCell(Paras34);
            subjreportTable.addCell(Paras35);subjreportTable.addCell(Paras36);subjreportTable.addCell(Paras37);
            subjreportTable.addCell(Paras38);subjreportTable.addCell(Paras39);subjreportTable.addCell(Paras40);
            subjreportTable.addCell(Paras41);subjreportTable.addCell(Paras42);
            
            
         if(clientnameTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null&&staffnoTxt.getText().isEmpty())     {
              subjreportTable.addCell(Paras43);        
            }
 else if(clientnameTxt.getText().trim().length()!=0&&staffnoTxt.getText().isEmpty()){
           subjreportTable.addCell(Paras43);    
            }
   else if(callfromdate!=null&&calltodate!=null&&staffnoTxt.getText().isEmpty()){
               subjreportTable.addCell(Paras43);    
            }
            
            String callno, calldate, serviceno, servicedate,from,to,client,equipmodel,serial,location,fault,action,status,doneby;
            int n=servicerecTable.getRowCount();
            for(int z=0;z<n;z++){
                
                callno= String.valueOf(servicerecTable.getValueAt(z , 1));
                calldate=String.valueOf(servicerecTable.getValueAt(z ,2));
                serviceno= (String.valueOf(servicerecTable.getValueAt(z, 3)));
                servicedate=String.valueOf(servicerecTable.getValueAt(z ,4));
                from=String.valueOf(servicerecTable.getValueAt(z ,5));
                to=String.valueOf(servicerecTable.getValueAt(z ,6));
                client=String.valueOf(servicerecTable.getValueAt(z ,7));
                equipmodel=String.valueOf(servicerecTable.getValueAt(z ,8));
                serial=String.valueOf(servicerecTable.getValueAt(z ,9));
                location=String.valueOf(servicerecTable.getValueAt(z ,10));
                fault=String.valueOf(servicerecTable.getValueAt(z ,11));
                action=String.valueOf(servicerecTable.getValueAt(z ,12));
                status=String.valueOf(servicerecTable.getValueAt(z ,13));
                
              doneby  =String.valueOf(servicerecTable.getValueAt(z ,14));
                
                Paragraph Paras789=new Paragraph(String.valueOf(z+1),font4);
                Paras789.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras11=new Paragraph(callno,font4);
                Paras11.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras211=new Paragraph(calldate,font4);
                Paras211.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras2111=new Paragraph(serviceno,font4);
                Paras2111.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras331=new Paragraph(servicedate,font4);
                Paras331.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras341=new Paragraph(from,font4);
                Paras341.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras351=new Paragraph(to,font4);
                Paras351.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras361=new Paragraph(client,font4);
                Paras361.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras371=new Paragraph(equipmodel,font4);
                Paras371.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras381=new Paragraph(serial,font4);
                Paras381.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras391=new Paragraph(location,font4);
                Paras391.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras401=new Paragraph(fault,font4);
                Paras401.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras411=new Paragraph(action,font4);
                Paras411.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras421=new Paragraph(status,font4);
                Paras421.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras422=new Paragraph(doneby,font4);
                Paras422.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                subjreportTable.addCell(Paras789);subjreportTable.addCell(Paras11);subjreportTable.addCell(Paras211);
                subjreportTable.addCell(Paras2111);   subjreportTable.addCell(Paras331);subjreportTable.addCell(Paras341);
                subjreportTable.addCell(Paras351);subjreportTable.addCell(Paras361);subjreportTable.addCell(Paras371);
                subjreportTable.addCell(Paras381);subjreportTable.addCell(Paras391);subjreportTable.addCell(Paras401);
                subjreportTable.addCell(Paras411);subjreportTable.addCell(Paras421);
                
      if(clientnameTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null&&staffnoTxt.getText().isEmpty())     {
              subjreportTable.addCell(Paras422);        
            }
 else if(clientnameTxt.getText().trim().length()!=0&&staffnoTxt.getText().isEmpty()){
           subjreportTable.addCell(Paras422);    
            }
   else if(callfromdate!=null&&calltodate!=null&&staffnoTxt.getText().isEmpty()){
               subjreportTable.addCell(Paras422);    
            }
         
            }
            receiptDoc.add( subjreportTable);
            receiptDoc.add( space);
            
            Paragraph user=new Paragraph("Printed By:"+currentuser,font4);
            user.setAlignment(Paragraph.ALIGN_RIGHT);
            receiptDoc.add(user);
            receiptDoc.close();
            
            
        }   catch (SQLException | DocumentException | FileNotFoundException ex) {
            Logger.getLogger(Calltracking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Executable.openDocument(directory);
        
    }   catch (IOException ex) {
              Logger.getLogger(Calltracking.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          }
      }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void clientnameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientnameTxtKeyReleased
     getServiceRecords();
    }//GEN-LAST:event_clientnameTxtKeyReleased

    private void clientnameTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientnameTxtMouseClicked
  callsearchTxt.setText("SEARCH CALL NUMBER");
 // staffnameTxt.setText(null);
 // staffnoTxt.setText(null);
  //callfromdate=null; calltodate=null;staffnamesearch=null;
    }//GEN-LAST:event_clientnameTxtMouseClicked

    private void clientnameTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientnameTxtMouseExited
     try {
       
         String getclient="SELECT CLIENTNAME FROM CLIENTS WHERE CLIENTNAME LIKE '"+clientnameTxt.getText()+"%'";
         Connection connectDb=(Connection)DriverManager.getConnection(access.server, access.username,access.dbpword);
         com.mysql.jdbc.PreparedStatement pstlogo=(com.mysql.jdbc.PreparedStatement) connectDb.prepareStatement(getclient);
         ResultSet rst=pstlogo.executeQuery();
         while(rst.next()){
               clientnames=rst.getString(1);
      clientnameTxt.setText(clientnames); 
         }
     } catch (SQLException ex) {
         Logger.getLogger(Calltracking.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//GEN-LAST:event_clientnameTxtMouseExited

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField callsearchTxt;
    public javax.swing.JPanel calltrackingPanel;
    private javax.swing.JTextField clientnameTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JTable servicerecTable;
    private javax.swing.JTextField staffnameTxt;
    private javax.swing.JTextField staffnoTxt;
    // End of variables declaration//GEN-END:variables
}
