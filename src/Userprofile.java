
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
public class Userprofile extends javax.swing.JFrame {

    /**
     * Creates new form Userprofile
     */
    String currentowner;  String usertype;
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
    Home access=new Home();
    
String[] cols3={"No.","CALL NO","SERVICE NO","SERVICE DATE","FROM","TO","CLIENT","EQUIP. MODEL","SERIAL","LOCATION" ,"ACTION","STATUS","DONE BY"};   
 
 DefaultTableModel servicetableModel=new DefaultTableModel(cols3,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    };   
 String[] cols2={"No.","TECHNICIAN","DATE OPENED","CALL NO","ACTIVITY","CLIENT"," CONTRACT NO","STATUS"};       
 DefaultTableModel callstableModel=new DefaultTableModel(cols2,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    };
    public Userprofile() {
        initComponents();
         staffcallsTable.getTableHeader().setReorderingAllowed(false);
           staffcallsTable.getColumnModel().getColumn(0).setPreferredWidth(1);
           
           servicerecTable.getTableHeader().setReorderingAllowed(false);
           servicerecTable.getColumnModel().getColumn(0).setPreferredWidth(1);
   // getUser(); 
  //getstaffCalls();
    }
   
 public String checkusertype(String usertype){
    try {
         Class.forName("com.mysql.jdbc.Driver");
         Connection connectDb=(Connection)DriverManager.getConnection(access.server, access.username,access.dbpword);
         String sqllogo="SELECT TYPE FROM USERS WHERE OWNER='"+currentowner+"'";
         com.mysql.jdbc.PreparedStatement pstret=(com.mysql.jdbc.PreparedStatement)connectDb.prepareStatement(sqllogo);
 ResultSet stret=pstret.executeQuery();
while(stret.next()){
 usertype=stret.getString(1); 
}
     } catch (ClassNotFoundException | SQLException ex) {
         Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
     }
return usertype;
 } 

    public void getSettings(){
     try {
         Class.forName("com.mysql.jdbc.Driver");
         Connection connectDb=(Connection)DriverManager.getConnection(access.server, access.username,access.dbpword);
         String sqllogo="SELECT * FROM SETTINGS";
         com.mysql.jdbc.PreparedStatement pstret=(com.mysql.jdbc.PreparedStatement)connectDb.prepareStatement(sqllogo);
 ResultSet stret=pstret.executeQuery();
 int k=0;
 if(stret!=null){
while(stret.next()){
 
byte[] ibytes =stret.getBytes(5); 

Image imagelog = getToolkit().createImage(ibytes);
ImageIcon icon=new ImageIcon( imagelog.getScaledInstance(280, 100, Image.SCALE_DEFAULT));
   logoLabel.setIcon(icon);

}
 }      
     } catch (ClassNotFoundException | SQLException ex) {
         Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
       
  public void getServiceRecords(){
    
    int g=servicerecTable.getRowCount();
    for(int f=0;f<g;f++){
servicetableModel.removeRow(0);
    }
    String usertype="";
     try {
 
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,   access.username,  access.dbpword);
     String sqllogo="SELECT TYPE FROM USERS WHERE OWNER='"+currentowner+"'";
         com.mysql.jdbc.PreparedStatement pstret=(com.mysql.jdbc.PreparedStatement)connectDb.prepareStatement(sqllogo);
 ResultSet stret=pstret.executeQuery();
while(stret.next()){
 usertype=stret.getString(1); 
}
    System.out.println("Current owner "+currentowner+" USertype "+usertype);
     String getservicessql="";  String getcalls="";
 /*if(!searchstaffTxt.getText().equalsIgnoreCase("Search Staff")){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM,SERVICE.TO,CONTRACTS.CONTRACT_NO,SERVICE.EQUIP_MODEL,SERVICE.SERIAL,"
         + "SERVICE.LOCATION,SERVICE.FAULT,SERVICE.ACTION,SERVICE.STATUS, CLIENTS.CLIENTNAME, STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO"
          + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE STAFF.STAFFNAME LIKE '"+searchstaffTxt.getText()+"%'"
         + " ORDER BY SERVICE.SERVICE_DATE DESC";
  
  getcalls="SELECT CALLS.CALL_NO,CALLS.CALL_DATE,CLIENTS.CLIENTNAME, CALLS.CONTRACT_NO, CALLS.STATUS,STAFF.STAFFNAME FROM CALLS "
                    + "LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO "
                    + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
        + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
        + "WHERE STAFF.STAFFNAME LIKE '"+searchstaffTxt.getText()+"%' ORDER BY "
                    + "CALLS.CALL_DATE DESC";
 } else*/
 
 {
     
 if(usertype.equalsIgnoreCase("USER")){
if(!searchcallnoTxt.getText().equalsIgnoreCase("SEARCH CALL NUMBER")){
  getservicessql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CALLS.TO_DO  FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
             + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE CALLS.CALL_NO LIKE '"+searchcallnoTxt.getText()+"%'"
               + " ORDER BY SERVICE.SERVICE_DATE DESC";
}else{
 getservicessql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CALLS.TO_DO  FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
             + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
          + "WHERE CALLS.TECHNICIAN ='"+currentowner+"'"
               + " ORDER BY SERVICE.SERVICE_DATE DESC";
 
}
 
 }
 else if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
  if(!searchstaffTxt.getText().equalsIgnoreCase("SEARCH STAFF"))  {
  getservicessql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CALLS.TO_DO  FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
             + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
           + "WHERE STAFF.STAFFNAME like '"+searchstaffTxt.getText()+"%'"
           + " ORDER BY SERVICE.SERVICE_DATE DESC";

  }
  else{
  getservicessql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CALLS.TO_DO FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
             + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
           + " ORDER BY SERVICE.SERVICE_DATE DESC";
  }
    
 }
   
 }  
       PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getservicessql);
            ResultSet rst=pst.executeQuery(); int i=0;
             while(rst.next()){
                 java.sql.Date servdate=rst.getDate(3);
        if(servdate==null){
        
        } 
        else{
  if(rst.getString(12)==null){
   String getclient="SELECT SUPPLY_REQUESTS.CSRNO, CLIENTS.CLIENTNAME FROM SUPPLY_REQUESTS LEFT JOIN CLIENTS "
         + "ON SUPPLY_REQUESTS.CLIENTNO=CLIENTS.CLIENT_NO WHERE SUPPLY_REQUESTS.CSRNO='"+rst.getString(6)+"'";
    PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(getclient);
   ResultSet rst2=pst2.executeQuery();
   
   while(rst2.next()){
servicetableModel.addRow(new Object[]{i+1,rst.getString(1),rst.getString(2),sdf.format(rst.getDate(3)),rst.getString(4),rst.getString(5),rst2.getString(2)
  ,rst.getString(7),rst.getString(8),rst.getString(9),rst.getString(10),rst.getString(11),rst.getString(13)}); 
   }
  }
  else{
 servicetableModel.addRow(new Object[]{i+1,rst.getString(1),rst.getString(2),sdf.format(rst.getDate(3)),rst.getString(4),rst.getString(5),rst.getString(12)
  ,rst.getString(7),rst.getString(8),rst.getString(9),rst.getString(10),rst.getString(11),rst.getString(13)}); 
  }
        }    
    i++;  
        }
    
             if(i<1){
//JOptionPane.showMessageDialog(null,"no record found" );
             }
        } catch (SQLException ex) {
            Logger.getLogger(Userprofile.class.getName()).log(Level.SEVERE, null, ex);
        }  
  
   
  }
    
public void getstaffCalls(){
    int g=staffcallsTable.getRowCount();
    for(int f=0;f<g;f++){
callstableModel.removeRow(0);
    }
    String usertype="";
    try {
       String getcallssql="";
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,   access.username,  access.dbpword);
     String sqllogo="SELECT TYPE FROM USERS WHERE OWNER='"+currentowner+"'";
         com.mysql.jdbc.PreparedStatement pstret=(com.mysql.jdbc.PreparedStatement)connectDb.prepareStatement(sqllogo);
 ResultSet stret=pstret.executeQuery();
while(stret.next()){
 usertype=stret.getString(1); 
}
  System.out.println("CURRENT OWNER "+currentowner+" USertype is "+usertype);
 if(usertype.equalsIgnoreCase("USER")){
  if(!searchcallnoTxt.getText().equalsIgnoreCase("SEARCH CALL NUMBER")){
  getcallssql="SELECT CALLS.CALL_NO,CALLS.CALL_DATE,CLIENTS.CLIENTNAME, CALLS.CONTRACT_NO, CALLS.STATUS,STAFF.STAFFNAME,CALLS.TO_DO FROM CALLS "
                    + "LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO "
                    + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
        + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
        + "WHERE CALLS.CALL_NO LIKE '"+searchcallnoTxt.getText()+"%' AND CALLS.TECHNICIAN='"+currentowner+"' ORDER BY "
                    + "CALLS.CALL_DATE DESC";
  }   
  else{ 
getcallssql="SELECT CALLS.CALL_NO,CALLS.CALL_DATE,CLIENTS.CLIENTNAME, CALLS.CONTRACT_NO, CALLS.STATUS,STAFF.STAFFNAME,CALLS.TO_DO FROM CALLS "
                    + "LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO "
                    + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
        + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
        + "WHERE CALLS.TECHNICIAN ='"+staffnoTxt.getText()+"' ORDER BY "
                    + "CALLS.CALL_DATE DESC";
  }
 }
 else if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
     
      if(!searchstaffTxt.getText().equalsIgnoreCase("SEARCH STAFF")){
  getcallssql="SELECT CALLS.CALL_NO,CALLS.CALL_DATE,CLIENTS.CLIENTNAME, CALLS.CONTRACT_NO, CALLS.STATUS,STAFF.STAFFNAME,CALLS.TO_DO FROM CALLS " +
" LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO " +
"                    LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO " +
"        LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO " +
"      WHERE CALLS.TECHNICIAN LIKE (SELECT STAFFNO FROM STAFF WHERE STAFF.STAFFNAME = '"+searchstaffTxt.getText()+"') ORDER BY " +
"                   CALLS.CALL_DATE DESC";
  }
      else{
      getcallssql="SELECT CALLS.CALL_NO,CALLS.CALL_DATE,CLIENTS.CLIENTNAME, CALLS.CONTRACT_NO, CALLS.STATUS,STAFF.STAFFNAME,CALLS.TO_DO FROM CALLS "
                    + "LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO "
              + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                    + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO ORDER BY "
                    + "CALLS.CALL_DATE DESC";
      
      }  
 }
            
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcallssql);
            ResultSet rst=pst.executeQuery(); int i=0;
             while(rst.next()){
  System.out.println("client name for calls "+rst.getString(3));
  
  if(rst.getString(3)==null){
  String getclient="SELECT SUPPLY_REQUESTS.CSRNO, CLIENTS.CLIENTNAME FROM SUPPLY_REQUESTS LEFT JOIN CLIENTS "
         + "ON SUPPLY_REQUESTS.CLIENTNO=CLIENTS.CLIENT_NO WHERE SUPPLY_REQUESTS.CSRNO='"+rst.getString(4)+"'";
  PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(getclient);
   ResultSet rst2=pst2.executeQuery();
   
   while(rst2.next()){
callstableModel.addRow(new Object[]{i+1,rst.getString(6),sdf.format(rst.getDate(2)),rst.getString(1),rst.getString(7),rst2.getString(2),rst.getString(4),rst.getString(5)}); 
 }
  }else{               
  callstableModel.addRow(new Object[]{i+1,rst.getString(6),sdf.format(rst.getDate(2)),rst.getString(1),rst.getString(7),rst.getString(3),rst.getString(4),rst.getString(5)}); 
  }
  i++;  
            }
             if(i<1){
//JOptionPane.showMessageDialog(null,"no record found" );
             }
        }
        catch (SQLException ex) {
            Logger.getLogger(Userprofile.class.getName()).log(Level.SEVERE, null, ex);
        }

}

public void getUser(){
 try {
          Connection connectDb=(Connection)DriverManager.getConnection(access.server, access.username,access.dbpword);
String checksql="SELECT  STAFFNO, STAFFNAME,  DEPT, POST,PASSPORT FROM STAFF WHERE STAFFNO ='"+ currentowner+"'"; 
          PreparedStatement pst3=(PreparedStatement)connectDb.prepareStatement(checksql);
          ResultSet rst3=pst3.executeQuery(); int y3=0;
          while(rst3.next()){
     staffnoTxt.setText(rst3.getString(1));
  nameTxt.setText(rst3.getString(2)); 
   deptTxt.setText(rst3.getString(3));
   postTxt.setText(rst3.getString(4)); 
   byte[] ibytes =rst3.getBytes(5); 

Image imagelog = getToolkit().createImage(ibytes);
ImageIcon icon=new ImageIcon( imagelog.getScaledInstance(150, 150, Image.SCALE_DEFAULT));
   userLabel.setIcon(icon);
         }} catch (SQLException ex) {
            Logger.getLogger(Userprofile.class.getName()).log(Level.SEVERE, null, ex);
        }
//System.out.println("USER OWNER IS "+access.owner);

}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userprofilePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        staffnoTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        deptTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        postTxt = new javax.swing.JTextField();
        callsholderPanel = new javax.swing.JPanel();
        adminscallPanel = new javax.swing.JPanel();
        techniciancallsPanel = new javax.swing.JPanel();
        servicerecordPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        servicerecTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        staffcallsTable = new javax.swing.JTable();
        searchPanel = new javax.swing.JPanel();
        searchstaffTxt = new javax.swing.JTextField();
        searchcallnoTxt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        currentuserTxt = new javax.swing.JLabel();
        usertypeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userprofilePanel.setBackground(new java.awt.Color(153, 0, 153));
        userprofilePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "USER PROFILE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "My User Profile", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 0, 102))); // NOI18N

        userLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/newmember.JPG"))); // NOI18N

        jLabel2.setText("STAFF NUMBER:");

        staffnoTxt.setEditable(false);

        jLabel3.setText("NAME:");

        deptTxt.setEditable(false);

        jLabel4.setText("DEPT:");

        nameTxt.setEditable(false);

        jLabel5.setText("POST");

        postTxt.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deptTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(staffnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(postTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(staffnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(deptTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(postTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        callsholderPanel.setBackground(new java.awt.Color(255, 255, 255));
        callsholderPanel.setLayout(new java.awt.CardLayout());

        adminscallPanel.setBackground(new java.awt.Color(255, 255, 255));
        adminscallPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Calls List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 153))); // NOI18N
        adminscallPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminscallPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout adminscallPanelLayout = new javax.swing.GroupLayout(adminscallPanel);
        adminscallPanel.setLayout(adminscallPanelLayout);
        adminscallPanelLayout.setHorizontalGroup(
            adminscallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 923, Short.MAX_VALUE)
        );
        adminscallPanelLayout.setVerticalGroup(
            adminscallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        callsholderPanel.add(adminscallPanel, "card3");

        techniciancallsPanel.setBackground(new java.awt.Color(255, 255, 255));
        techniciancallsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "STAFF CALLS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        javax.swing.GroupLayout techniciancallsPanelLayout = new javax.swing.GroupLayout(techniciancallsPanel);
        techniciancallsPanel.setLayout(techniciancallsPanelLayout);
        techniciancallsPanelLayout.setHorizontalGroup(
            techniciancallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 923, Short.MAX_VALUE)
        );
        techniciancallsPanelLayout.setVerticalGroup(
            techniciancallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        callsholderPanel.add(techniciancallsPanel, "card2");

        servicerecordPanel.setBackground(new java.awt.Color(255, 255, 255));
        servicerecordPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Service Records", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 14), new java.awt.Color(204, 0, 204))); // NOI18N

        servicerecTable.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        servicerecTable.setModel(servicetableModel);
        servicerecTable.setFillsViewportHeight(true);
        servicerecTable.setGridColor(new java.awt.Color(153, 0, 153));
        servicerecTable.setRowHeight(25);
        servicerecTable.setRowMargin(2);
        servicerecTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        jScrollPane3.setViewportView(servicerecTable);

        javax.swing.GroupLayout servicerecordPanelLayout = new javax.swing.GroupLayout(servicerecordPanel);
        servicerecordPanel.setLayout(servicerecordPanelLayout);
        servicerecordPanelLayout.setHorizontalGroup(
            servicerecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1264, Short.MAX_VALUE)
        );
        servicerecordPanelLayout.setVerticalGroup(
            servicerecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicerecordPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        staffcallsTable.setModel(callstableModel);
        staffcallsTable.setFillsViewportHeight(true);
        staffcallsTable.setGridColor(new java.awt.Color(204, 0, 153));
        staffcallsTable.setRowHeight(25);
        staffcallsTable.setRowMargin(5);
        staffcallsTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        staffcallsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffcallsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(staffcallsTable);

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchPanel.setLayout(new java.awt.CardLayout());

        searchstaffTxt.setForeground(new java.awt.Color(153, 0, 153));
        searchstaffTxt.setText("Search Staff");
        searchstaffTxt.setToolTipText("ENTER STAFF NAME");
        searchstaffTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchstaffTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchstaffTxtMouseEntered(evt);
            }
        });
        searchstaffTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchstaffTxtKeyReleased(evt);
            }
        });
        searchPanel.add(searchstaffTxt, "card2");

        searchcallnoTxt.setForeground(new java.awt.Color(153, 0, 153));
        searchcallnoTxt.setText("Search Call Number");
        searchcallnoTxt.setToolTipText("ENTER CALL NUMBER");
        searchcallnoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchcallnoTxtActionPerformed(evt);
            }
        });
        searchcallnoTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchcallnoTxtKeyReleased(evt);
            }
        });
        searchPanel.add(searchcallnoTxt, "card3");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servicerecordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(406, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(callsholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(405, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(servicerecordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(callsholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(384, Short.MAX_VALUE)))
        );

        jScrollPane2.setViewportView(jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        logoLabel.setBackground(new java.awt.Color(255, 255, 255));

        dateLabel.setBackground(new java.awt.Color(255, 255, 255));
        dateLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(153, 0, 153));
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateLabel.setText("Date|");

        currentuserTxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        currentuserTxt.setForeground(new java.awt.Color(153, 0, 153));
        currentuserTxt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        currentuserTxt.setText("Current User");

        usertypeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usertypeLabel.setForeground(new java.awt.Color(153, 0, 153));
        usertypeLabel.setText(",Usertype");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 431, Short.MAX_VALUE)
                .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(currentuserTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(usertypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentuserTxt)
                    .addComponent(usertypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout userprofilePanelLayout = new javax.swing.GroupLayout(userprofilePanel);
        userprofilePanel.setLayout(userprofilePanelLayout);
        userprofilePanelLayout.setHorizontalGroup(
            userprofilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        userprofilePanelLayout.setVerticalGroup(
            userprofilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userprofilePanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userprofilePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(userprofilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchstaffTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchstaffTxtKeyReleased
       getstaffCalls();  getServiceRecords();
    }//GEN-LAST:event_searchstaffTxtKeyReleased

    private void searchstaffTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchstaffTxtMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_searchstaffTxtMouseEntered

    private void searchstaffTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchstaffTxtMouseClicked
    searchstaffTxt.setText(null); 
    }//GEN-LAST:event_searchstaffTxtMouseClicked

    private void searchcallnoTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchcallnoTxtKeyReleased
   getstaffCalls(); getServiceRecords();
    }//GEN-LAST:event_searchcallnoTxtKeyReleased

    private void adminscallPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminscallPanelMouseClicked
 
    }//GEN-LAST:event_adminscallPanelMouseClicked

    private void staffcallsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffcallsTableMouseClicked
        try {
            int row=staffcallsTable.getSelectedRow(); String callno="";
       if(row>0){
         callno=String.valueOf(staffcallsTable.getValueAt(row, 3));
       }
            int g=servicerecTable.getRowCount();
            for(int f=0;f<g;f++){
                servicetableModel.removeRow(0);
            };
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,   access.username,  access.dbpword);
            String  getservicessql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
                    + "SERVICE.SERIAL,SERVICE.LOCATION,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CALLS.TO_DO  FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                    + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                    + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                    + "WHERE CALLS.CALL_NO = '"+callno+"'"
                    + " ORDER BY SERVICE.SERVICE_DATE DESC";
            
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getservicessql);
            ResultSet rst=pst.executeQuery(); int i=0;
            while(rst.next()){
                java.sql.Date servdate=rst.getDate(3);
                if(servdate==null){
                    
                }
                else{
    servicetableModel.addRow(new Object[]{i+1,rst.getString(1),rst.getString(2),sdf.format(rst.getDate(3)),rst.getString(4),rst.getString(5),rst.getString(12)
             ,rst.getString(7),rst.getString(8),rst.getString(9),rst.getString(10),rst.getString(11),rst.getString(13)});
                }
                i++;  
            }
        } catch (SQLException ex) {
            Logger.getLogger(Userprofile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_staffcallsTableMouseClicked

    private void searchcallnoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchcallnoTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchcallnoTxtActionPerformed

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adminscallPanel;
    private javax.swing.JPanel callsholderPanel;
    public javax.swing.JLabel currentuserTxt;
    public javax.swing.JLabel dateLabel;
    private javax.swing.JTextField deptTxt;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel logoLabel;
    public javax.swing.JTextField nameTxt;
    private javax.swing.JTextField postTxt;
    public javax.swing.JPanel searchPanel;
    public javax.swing.JTextField searchcallnoTxt;
    public javax.swing.JTextField searchstaffTxt;
    private javax.swing.JTable servicerecTable;
    private javax.swing.JPanel servicerecordPanel;
    private javax.swing.JTable staffcallsTable;
    private javax.swing.JTextField staffnoTxt;
    private javax.swing.JPanel techniciancallsPanel;
    private javax.swing.JLabel userLabel;
    public javax.swing.JPanel userprofilePanel;
    public javax.swing.JLabel usertypeLabel;
    // End of variables declaration//GEN-END:variables
}
