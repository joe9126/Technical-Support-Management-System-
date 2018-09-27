
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.table.DatePickerCellEditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JOE
 */
public class PM_Schedule extends javax.swing.JFrame {
  Home access=new Home();  String client,descrip;String clientNo,currentuser;
  String serviceperiodID;   Document   receiptDoc;
  SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
  DefaultComboBoxModel clientnameComboModel=new DefaultComboBoxModel();  
     JComboBox techCombo; JXDatePicker datepicker; 
  DefaultComboBoxModel descriptioncomboModel=new DefaultComboBoxModel();  
  SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
  String servicedate; java.sql.Date serviceFrom, serviceTo;
    String staffname;String printedby;       Image companylogo;
 String usertype;String directory;
    String[] cols ={"","NO.","MODEL","DESCRIPTION","SERIAL NO","LOCATION","SERVICE DATE","TIME","TECHNICIAN"," TECH PHONE"};
  DefaultTableModel   scheduletableModel=new DefaultTableModel(cols ,0){
   @Override
    public boolean isCellEditable(int row,int column){
        return column == 0||column==6||column==7||column==8;
   }
    };
 DefaultComboBoxModel techcomboModel=new DefaultComboBoxModel();
   JComboBox timeCombo;
    public PM_Schedule() {
        initComponents();
   
        
TableColumn column = scheduleTable.getColumnModel().getColumn(6);
column.setCellEditor(new DatePickerCellEditor());

//column.setCellRenderer(scheduleTable.getDefaultRenderer(LocalDateTime.class));

String[] timecol={"12:00 AM","12:30 AM","01:00 AM","01:30 AM","02:00 AM","02:30 AM","03:00 AM","03:30 AM","04:00 AM","04:30 AM","05:00 AM",
"05:30 AM","06:00 AM","06:30 AM","07:00 AM","07:30 AM","08:00 AM","08:30 AM","09:00 AM","09:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM"
,"12:30 PM","01:00 PM","01:30 PM","02:00 PM","02:30 PM","03:00 PM","03:30 PM","04:00 PM","04:30 PM","05:00 PM","05:30 PM","06:00 PM"
,"06:30 PM","07:00 PM","07:30 PM","08:00 PM","09:00 PM","09:30 PM","10:00 PM","10:30 PM","11:00 PM","11:30 PM"};
  timeCombo=new JComboBox(timecol);  
 scheduleTable.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(timeCombo)); 
 
 
    descriptioncomboModel.addElement("--Select Contract--");
  scheduleTable.getColumnModel().getColumn(0).setPreferredWidth(1);  
   scheduleTable.getColumnModel().getColumn(1).setPreferredWidth(1);  
   TableColumn tc =scheduleTable.getColumnModel().getColumn(0);
   
     tc.setCellEditor(scheduleTable.getDefaultEditor(Boolean.class));
     tc.setCellRenderer(scheduleTable.getDefaultRenderer(Boolean.class));
     
          techCombo=new JComboBox(techcomboModel);
          datepicker=new JXDatePicker();
          techCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
           String techname=""+ techCombo.getSelectedItem();
           System.out.println("Selected tech :"+techname); 
        int activerow=  scheduleTable.getSelectedRow();
         try {
            String getcontractsql="SELECT phone FROM STAFF WHERE STAFFNAME='"+techname+"'";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
             while(rst.next()){
               
       scheduleTable.setValueAt(rst.getString(1),activerow, 9); 
        System.out.println("Selected tech phone :"+rst.getString(1)); 
          i++;  
            }
        }  catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }      
            }
        }); 
      
        scheduleTable.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(techCombo)); 
     //    scheduleTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(panel)); 
        scheduleTable.setColumnSelectionAllowed(true);
         scheduleTable.getTableHeader().setReorderingAllowed(false);
         
         
         scheduletableModel.addRow(new Object[]{false,"1","","","","","",""});
    }
    
public void getClients(){
        try {
            String getcontractsql="SELECT CLIENTNAME FROM CLIENTS ORDER BY CLIENTNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            clientnameComboModel.removeAllElements();
              clientnameComboModel.addElement("--Select Client--"); 
             while(rst.next()){
       clientnameComboModel.addElement(rst.getString(1)); 
      //  clientcomboModel1.addElement(rst.getString(1)); 
                i++;  
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
}   

 public void getContracts(){
     try {
            String getcontractsql="SELECT CONTRACTS.CONT_DESCRIP, CONTRACTS.CLIENT_NO FROM CONTRACTS " +
"                  LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO WHERE CLIENTS.CLIENT_NO=(SELECT CLIENT_NO FROM CLIENTS WHERE " +
"                   CLIENTNAME='"+client+"') ORDER BY CLIENTS.CLIENTNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            descriptioncomboModel.removeAllElements();
           descriptioncomboModel.addElement("-Select Contract-"); 
           
            while(rst.next()){
    descriptioncomboModel.addElement(rst.getString(1)); 
    clientNo=rst.getString(2);
    //contractcomboModel1.addElement(rst.getString(1)); 
             i++;  
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
   } 
  public void getTechnicians(){
  try {
            String getcontractsql="SELECT STAFFNAME   FROM STAFF   ORDER BY STAFFNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
        techcomboModel.removeAllElements();
        techcomboModel.addElement("-Select Technician-");
            while(rst.next()){
 techcomboModel.addElement(rst.getString(1));
    i++;  
            }
          if(i<1){  }
     }  catch (SQLException ex) {
            Logger.getLogger(Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }

}
  
  public void getEquipment(){
        try {
     String getequipsql;  
 if(contractnumTxt.getText().isEmpty()){
 getequipsql="SELECT EQUIPMENT.MODEL,EQUIPMENT.DESCRIPTION,EQUIPMENT.SERIAL_NO, EQUIPMENT.LOCATION FROM equipment "
           + " LEFT JOIN CONTRACTS ON EQUIPMENT.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                    + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO ORDER BY EQUIPMENT.LOCATION ASC";
 } else{
 getequipsql="SELECT EQUIPMENT.MODEL,EQUIPMENT.DESCRIPTION,EQUIPMENT.SERIAL_NO, EQUIPMENT.LOCATION FROM equipment "
           + " LEFT JOIN CONTRACTS ON EQUIPMENT.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                    + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO WHERE EQUIPMENT.CONTRACT_NO='"+contractnumTxt.getText()+"' ORDER BY EQUIPMENT.LOCATION ASC";
 }   

      int row=scheduleTable.getRowCount(); 
      for(int g=0;g<row;g++){
      scheduletableModel.removeRow(0);
      }
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getequipsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            while(rst.next()){
 scheduletableModel.addRow(new Object[]{false,i+1,rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),
                "","",""}); 
                i++;  
            }
        } catch (SQLException ex) {
            Logger.getLogger(Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }
 } 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pmschedulePanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        contractnumTxt = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jTextField2 = new javax.swing.JTextField();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jTextField3 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        scheduleTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pmschedulePanel.setBackground(new java.awt.Color(153, 0, 153));
        pmschedulePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PM SCHEDULE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Client & Contract ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel1.setText("CLIENT:");

        jComboBox1.setModel(clientnameComboModel);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("CONTRACT:");

        jComboBox2.setModel(descriptioncomboModel);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setText("CONTRACT NO:");

        contractnumTxt.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contractnumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(contractnumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Service Period", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 0, 204))); // NOI18N

        jLabel4.setText("SERVICE FROM:");

        jLabel5.setText("SERVICE TO:");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jTextField2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operations", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("DELETE");

        jButton4.setText("PRINT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Service Schedule", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 153))); // NOI18N

        scheduleTable.setModel(scheduletableModel);
        scheduleTable.setFillsViewportHeight(true);
        scheduleTable.setGridColor(new java.awt.Color(153, 0, 153));
        scheduleTable.setRowHeight(25);
        scheduleTable.setRowMargin(2);
        scheduleTable.setSelectionBackground(new java.awt.Color(153, 0, 153));
        scheduleTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                scheduleTableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(scheduleTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 942, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pmschedulePanelLayout = new javax.swing.GroupLayout(pmschedulePanel);
        pmschedulePanel.setLayout(pmschedulePanelLayout);
        pmschedulePanelLayout.setHorizontalGroup(
            pmschedulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmschedulePanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pmschedulePanelLayout.setVerticalGroup(
            pmschedulePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmschedulePanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 35, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(pmschedulePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
      client=""+jComboBox1.getSelectedItem();
      getContracts();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
       descrip=""+jComboBox2.getSelectedItem();
        try {
            String getcontractsql="SELECT CONTRACTS.CONTRACT_NO, CONTRACTS.CONT_DESCRIP FROM CONTRACTS "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO = CLIENTS.CLIENT_NO WHERE CONTRACTS.CONT_DESCRIP ='"+descrip+"' AND "
            + " CLIENTS.CLIENTNAME='"+client+"'";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            while(rst.next()){
                contractnumTxt.setText(rst.getString(1));
                getEquipment();  //getSchedule();
                i++;
            }
      connectDb.close();  }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void scheduleTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_scheduleTableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_scheduleTableKeyPressed

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
 serviceFrom=new java.sql.Date(jXDatePicker1.getDate().getTime()); 
 jTextField2.setText(sdf.format(serviceFrom)); 
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
   serviceTo=new java.sql.Date(jXDatePicker2.getDate().getTime()); 
          jTextField3.setText(sdf.format(serviceTo));
          getSchedule();
    }//GEN-LAST:event_jXDatePicker2ActionPerformed
public void getSchedule(){
      try {
     String checkschedule;
    
 if(serviceTo!=null){
  checkschedule="SELECT CLIENTSCHEDULE.CLIENT_NO,CLIENTSCHEDULE.CONTRACT_NO, SERVICESCHEDULE.SERVICEPERIOD_ID, EQUIPMENT.MODEL,EQUIPMENT.DESCRIPTION,"
          + "EQUIPMENT.SERIAL_NO,EQUIPMENT.LOCATION,SERVICESCHEDULE.SERVICE_DATE,SERVICESCHEDULE.TIME,SERVICESCHEDULE.TECH,SERVICESCHEDULE.PHONE  "
          + "FROM CLIENTSCHEDULE LEFT JOIN SERVICESCHEDULE ON CLIENTSCHEDULE.SERVICEPERIOD_ID= SERVICESCHEDULE.SERVICEPERIOD_ID "
          + "LEFT JOIN EQUIPMENT ON SERVICESCHEDULE.EQUIP_SERIAL= EQUIPMENT.SERIAL_NO WHERE "
          + "  CLIENTSCHEDULE.CONTRACT_NO='"+contractnumTxt.getText()+"' AND CLIENTSCHEDULE.SERVICE_FROM >='"+serviceFrom+"' "
          + "AND CLIENTSCHEDULE.SERVICE_TO <='"+serviceTo+"' ORDER BY SERVICESCHEDULE.SERVICE_DATE DESC ";
 }else{
  checkschedule="SELECT CLIENTSCHEDULE.CLIENT_NO,CLIENTSCHEDULE.CONTRACT_NO, SERVICESCHEDULE.SERVICEPERIOD_ID, EQUIPMENT.MODEL,EQUIPMENT.DESCRIPTION,"
          + "EQUIPMENT.SERIAL_NO,EQUIPMENT.LOCATION,SERVICESCHEDULE.SERVICE_DATE,SERVICESCHEDULE.TIME,SERVICESCHEDULE.TECH,SERVICESCHEDULE.PHONE  "
          + "FROM CLIENTSCHEDULE LEFT JOIN SERVICESCHEDULE ON CLIENTSCHEDULE.SERVICEPERIOD_ID= SERVICESCHEDULE.SERVICEPERIOD_ID "
          + "LEFT JOIN EQUIPMENT ON SERVICESCHEDULE.EQUIP_SERIAL= EQUIPMENT.SERIAL_NO WHERE "
          + "  CLIENTSCHEDULE.CONTRACT_NO='"+contractnumTxt.getText()+"' ORDER BY SERVICESCHEDULE.SERVICE_DATE DESC ";
 
 }
 
          Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
          PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(checkschedule);
          ResultSet rst=pst.executeQuery(); int i=0;
             int rows=scheduleTable.getRowCount();
    for(int g=0;g<rows;g++){
    scheduletableModel.removeRow(0);
    }         
          while(rst.next()){
 serviceperiodID=   rst.getString(3);          
    scheduletableModel.addRow(new Object[]{false,i+1,rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8),
    rst.getString(9),rst.getString(10),rst.getString(11)}); 
             i++;
          }
   if(i<1){
     getEquipment();
    }       
      } catch (SQLException ex) {
          Logger.getLogger(PM_Schedule.class.getName()).log(Level.SEVERE, null, ex);
      }
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      int rows=scheduleTable.getRowCount();
        if(rows<1){
 JOptionPane.showMessageDialog(null, "Please select Client and contract !","Equipment Required",JOptionPane.WARNING_MESSAGE);
        }
        else if(client==null||client.equalsIgnoreCase("--Select Client--")){
JOptionPane.showMessageDialog(null, "Please select Client !","Client Required",JOptionPane.WARNING_MESSAGE);        
        } 
        else if(descrip==null||descrip.equalsIgnoreCase("--Select Contract--")){
  JOptionPane.showMessageDialog(null, "Please select  contract !","Contract Required",JOptionPane.WARNING_MESSAGE);       
        } 
        
        else if(serviceFrom==null){
 JOptionPane.showMessageDialog(null, "Please select  Service From Period !","Service From Required",JOptionPane.WARNING_MESSAGE);          
        }  
        else if(serviceTo==null){
 JOptionPane.showMessageDialog(null, "Please select  Service To Period !","Service To Required",JOptionPane.WARNING_MESSAGE);          
        }
        else if(vaidCheck())   {
  int choice=JOptionPane.showConfirmDialog(null, "Do you want to save "+client+" Service schedule for "+sdf.format(serviceFrom)+" To "+sdf.format(serviceTo)+" Period?","Confirm",JOptionPane.YES_OPTION);
if(choice==JOptionPane.YES_OPTION){
      try {
          String checkschedule="SELECT SERVICEPERIOD_ID FROM CLIENTSCHEDULE WHERE SERVICE_FROM ='"+serviceFrom+"' AND SERVICE_TO ='"+serviceTo+"'";
          Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
          PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(checkschedule);
          ResultSet rst=pst.executeQuery(); int i=0;
          while(rst.next()){
             i++;
          }
if(i>0){
JOptionPane.showMessageDialog(null, client+" Service schedule for "+sdf.format(serviceFrom)+" To "+sdf.format(serviceTo)+" already exists!","Duplicate Schedule",JOptionPane.WARNING_MESSAGE);
}         
else{
String saveclientschedule="INSERT INTO CLIENTSCHEDULE VALUES(?,?,?,?,?)";
  PreparedStatement pst1=(PreparedStatement)connectDb.prepareStatement(saveclientschedule);
pst1.setString(1, clientNo); 
pst1.setString(2, contractnumTxt.getText()); 
int year=LocalDate.now().getYear();    
 Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
 pst1.setString(3, "PM"+year+"/"+loannumber);
  
java.sql.Date  sqlFrom=new java.sql.Date(serviceFrom.getTime()); 
  pst1.setDate(4, sqlFrom); 
  
  java.sql.Date  sqlTo=new java.sql.Date(serviceTo.getTime()); 
  pst1.setDate(5, sqlTo); 
pst1.executeUpdate();
 int g=0;
int r=scheduleTable.getRowCount();
for(int h=0;h<r;h++){
String saveserviceschedule="INSERT INTO SERVICESCHEDULE VALUES(?,?,?,?,?,?)";
  PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(saveserviceschedule);
   pst2.setString(1, "PM"+year+"/"+loannumber); 
   pst2.setString(2, String.valueOf(scheduleTable.getValueAt(h, 4)));
   pst2.setString(3, String.valueOf(scheduleTable.getValueAt(h, 6))); 
      pst2.setString(4, String.valueOf(scheduleTable.getValueAt(h, 7)));
   pst2.setString(5, String.valueOf(scheduleTable.getValueAt(h, 8)));
     pst2.setString(6, String.valueOf(scheduleTable.getValueAt(h, 9)));
 g=pst2.executeUpdate();
 
}
if(g>0){
JOptionPane.showMessageDialog(null, client+" Service schedule for "+sdf.format(serviceFrom)+" To "+sdf.format(serviceTo)+" saved successfully!","Schedule Created",JOptionPane.INFORMATION_MESSAGE);
} 
}
      } catch (SQLException ex) {
          Logger.getLogger(PM_Schedule.class.getName()).log(Level.SEVERE, null, ex);
      }
}        
        }    
        
        else{
  JOptionPane.showMessageDialog(null, "Please fill in all empty fields in schedule table!","All Fields Required",JOptionPane.WARNING_MESSAGE);
        }       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
   if(vaidCheck()){
       try {
 String checkschedule="SELECT SERVICEPERIOD_ID FROM CLIENTSCHEDULE WHERE SERVICE_FROM ='"+serviceFrom+"' AND SERVICE_TO ='"+serviceTo+"'";
  Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
  PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(checkschedule);
 ResultSet rst=pst.executeQuery(); int i=0;
 while(rst.next()){
               i++;
   }
           if(i>0){
   int choice= JOptionPane.showConfirmDialog(null, "Do you want to save changes to "+client+" Service schedule for "+sdf.format(serviceFrom)+" To "+sdf.format(serviceTo)+"?","Confirm",JOptionPane.YES_NO_OPTION);
if(choice==JOptionPane.YES_OPTION){
int r=scheduleTable.getRowCount(); int g=0;
for(int h=0;h<r;h++){
String saveserviceschedule="UPDATE SERVICESCHEDULE SET SERVICE_DATE=?, TIME=?,TECH=?, PHONE=? WHERE SERVICEPERIOD_ID='"+serviceperiodID+"'";
  PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(saveserviceschedule);
   pst2.setString(1, String.valueOf(scheduleTable.getValueAt(h, 6)));
   pst2.setString(2, String.valueOf(scheduleTable.getValueAt(h, 7))); 
      pst2.setString(3, String.valueOf(scheduleTable.getValueAt(h, 8)));
   pst2.setString(4, String.valueOf(scheduleTable.getValueAt(h, 9)));
  g=pst2.executeUpdate();
 }     
if(g>0){
JOptionPane.showMessageDialog(null, client+" Service schedule for "+sdf.format(serviceFrom)+" To "+sdf.format(serviceTo)+" updated successfully!","Schedule Updated",JOptionPane.INFORMATION_MESSAGE);
printSchedule();
}          
}      }  
       
       } catch (SQLException ex) {
           Logger.getLogger(PM_Schedule.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   else{
   JOptionPane.showMessageDialog(null, "Please fill in all empty fields in schedule table!","All Fields Required",JOptionPane.WARNING_MESSAGE); 
   }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
 if(client==null){
  JOptionPane.showMessageDialog(null, "Please select client!","Client Required",JOptionPane.WARNING_MESSAGE); 
 } else if(contractnumTxt.getText().isEmpty()){
  JOptionPane.showMessageDialog(null, "Please select contract!","Contract Required",JOptionPane.WARNING_MESSAGE); 
 } else if(vaidCheck()){
  printSchedule();
 } else{
    JOptionPane.showMessageDialog(null, "Please fill in all empty fields in schedule table!","All Fields Required",JOptionPane.WARNING_MESSAGE); 
 }

    }//GEN-LAST:event_jButton4ActionPerformed
 
public void printSchedule(){
    int r= scheduleTable.getRowCount();
    if(r<1){
JOptionPane.showMessageDialog(null, "Please select equipment from the Equipment in Workshop list!","No Equipment Selected",JOptionPane.WARNING_MESSAGE);
    }else{
     FileOutputStream filename=null; 
     receiptDoc=new Document(PageSize.A4 ); 
      JFileChooser filesaver=new JFileChooser();
       filesaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int option=filesaver.showSaveDialog(jPanel2); 
          
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
            byt=rst.getBytes(1);
            companylogo = Toolkit.getDefaultToolkit().createImage(byt);
            }
            }
          String printdate=new SimpleDateFormat("dd MMM, yyyy").format(Calendar.getInstance().getTime()); 
          
       directory=dir+"/"+client+" SERVICE SCHEDULE FOR "+sdf.format(serviceFrom)+" TO "+sdf.format(serviceFrom)+" .pdf";
                filename = new FileOutputStream(directory);
            
   PdfWriter writer=PdfWriter.getInstance(receiptDoc ,filename);
            receiptDoc.open();
            Paragraph space=new Paragraph("          ");
            com.itextpdf.text.Font font1=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,22,Font.BOLD);
            com.itextpdf.text.Font font2=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,8,Font.BOLD);
            com.itextpdf.text.Font font3=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
            com.itextpdf.text.Font font4=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.HELVETICA,8,Font.LAYOUT_LEFT_TO_RIGHT);
             com.itextpdf.text.Font font5=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.COURIER,9,Font.LAYOUT_LEFT_TO_RIGHT);
             
              com.itextpdf.text.Font font6=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,16,Font.BOLD); 
           com.itextpdf.text.Font font7=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,16,Font.PLAIN);   
           
            Paragraph date=new Paragraph( "  Generated: "+printdate,font4);
            date.setAlignment(Paragraph.ALIGN_RIGHT);
            receiptDoc.add(date);
            
          com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(  companylogo , null); 
image.setAbsolutePosition(05, 780);
         image.scaleToFit(150,50);
 writer.getDirectContent().addImage(image);  receiptDoc.add(space);   receiptDoc.add(space); 
  
            //Paragraph schname=new Paragraph( sacconame,font4); schname.setAlignment(Paragraph.ALIGN_CENTER);
              String separator="______________________________________________________________________________";
            Paragraph separatorlable=new Paragraph(separator,font2);
               separatorlable.setAlignment(Paragraph.ALIGN_CENTER);
              //  receiptDoc.add(separatorlable);
 String separator2="  __________________________________________________________________________________________________________________________________";
            Paragraph separatorlable3=new Paragraph(separator2,font2);
               separatorlable3.setAlignment(Paragraph.ALIGN_CENTER);
               
            Paragraph receiptlabel=new Paragraph("TECHNICAL SERVICE DEPARTMENT     ",font1);
            receiptlabel.setAlignment(Paragraph.ALIGN_CENTER);  receiptDoc.add(receiptlabel);
             receiptDoc.add(separatorlable3); 
         receiptDoc.add(space);
         
     Paragraph receiptlabel2=new Paragraph("  PREVENTIVE MAINTENANCE SCHEDULE",font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);  
       receiptDoc.add(separatorlable3);  receiptDoc.add(space);
          /*       float[] colswidth4=new float[]{5f,9f};
   PdfPTable colstable2=new PdfPTable(colswidth4);
     colstable2 .setWidthPercentage(50);//colstable.setHorizontalAlignment();
      colstable2.setHorizontalAlignment(Element.ALIGN_RIGHT);  */ 
      
   float[] colswidth;      com.itextpdf.text.pdf.PdfPTable subjreportTable = null;
 
   float[] colswidth2=new float[]{5f,15f};
   PdfPTable colstable=new PdfPTable(colswidth2);
     colstable .setWidthPercentage(100);//colstable.setHorizontalAlignment();
      colstable.setHorizontalAlignment(Element.ALIGN_CENTER); 
      
        // PdfPCell cell243=new PdfPCell(new Phrase(" "  ,font4));cell243.setBorder(Rectangle.NO_BORDER);  
 
     Paragraph paras11=new Paragraph("Client: ",font6);paras11.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT); 
     paras11.setAlignment(Paragraph.ALIGN_CENTER); 
      PdfPCell cell1=new PdfPCell(paras11); cell1.setBorder(Rectangle.NO_BORDER);  
  colstable.addCell(cell1); 
   
    Paragraph paras1=new Paragraph(client,font7); 
       paras1.setAlignment(Paragraph.ALIGN_CENTER);  paras1.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);   
  PdfPCell cell3=new PdfPCell(paras1);cell3.setBorder(Rectangle.NO_BORDER); 
 colstable.addCell(cell3); 
   
  
   Paragraph paras12=new Paragraph("Contract No: ",font6);paras12.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT);   
          paras12.setAlignment(Paragraph.ALIGN_CENTER);   
  PdfPCell cell=new PdfPCell(paras12);cell.setBorder(Rectangle.NO_BORDER);  
  colstable.addCell(cell ); 
   
  Paragraph paras13=new Paragraph(contractnumTxt.getText(),font7);paras13.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT); 
         paras13.setAlignment(Paragraph.ALIGN_CENTER);   
    PdfPCell cell23=new PdfPCell(paras13);cell23.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell23 ); 
    
   Paragraph paras14=new Paragraph("Description: " ,font6);paras14.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT);  
        paras14.setAlignment(Paragraph.ALIGN_CENTER);   
  PdfPCell cell2=new PdfPCell(paras14);cell2.setBorder(Rectangle.NO_BORDER);  
    colstable.addCell(cell2);  
 
      Paragraph paras15=new Paragraph(descrip,font7);paras15.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT); 
           paras15.setAlignment(Paragraph.ALIGN_CENTER);   
 PdfPCell cell5=new PdfPCell(paras15);cell5.setBorder(Rectangle.NO_BORDER);  
  colstable.addCell(cell5);   
     
      Paragraph paras16=new Paragraph("Service Period:  " ,font6);paras16.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT);
    paras16.setAlignment(Paragraph.ALIGN_CENTER);  
    PdfPCell cell34=new PdfPCell(paras16);cell34.setBorder(Rectangle.NO_BORDER);  
 colstable.addCell(cell34 ); 
      
 Paragraph paras17=new Paragraph(sdf.format(serviceFrom)+"  --   "+sdf.format(serviceTo),font7);paras17.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);  
      paras17.setAlignment(Paragraph.ALIGN_CENTER);  
   PdfPCell cell71=new PdfPCell(paras17);cell71.setBorder(Rectangle.NO_BORDER);  
    colstable.addCell(cell71); 
 receiptDoc.add(colstable); 
    receiptDoc.add(separatorlable3);     
     receiptDoc.add(space); //receiptDoc.add(space);
                  
      colswidth = new float[]{2f,5f,5f,5f,7f,10f ,5f ,8f ,8f  };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
          subjreportTable .setWidthPercentage(110);  
 
           Paragraph Paras=new Paragraph("No",font2);
            Paras.getFont().setStyle(Font.BOLD);
            
    Paragraph Paras1=new Paragraph("Model",font2);
            Paras1.getFont().setStyle(Font.BOLD);
            
              Paragraph Paras12=new Paragraph("Description",font2);
            Paras12.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras21=new Paragraph("Serial No.",font2);
            Paras21.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras22=new Paragraph("Location",font2);
            Paras22.getFont().setStyle(Font.BOLD);
            
      Paragraph Paras11=new Paragraph("Service Date",font2);
            Paras11.getFont().setStyle(Font.BOLD);
            
        Paragraph Paras131=new Paragraph("Time",font2);
            Paras131.getFont().setStyle(Font.BOLD); 
            
          Paragraph Paras121=new Paragraph("Technician",font2);
            Paras121.getFont().setStyle(Font.BOLD); 
            
          Paragraph Paras141=new Paragraph("Phone",font2);
            Paras141.getFont().setStyle(Font.BOLD); 
            
  subjreportTable.addCell(Paras);   subjreportTable.addCell(Paras1);
  subjreportTable.addCell(Paras12);  subjreportTable.addCell(Paras21);  
  subjreportTable.addCell(Paras22);subjreportTable.addCell(Paras11); 
   subjreportTable.addCell(Paras131);subjreportTable.addCell(Paras121);   
   subjreportTable.addCell(Paras141);  
          
 String model, description,serial,location,servdate,servetime,assigned,phone,status,  fault,warranty ;
  int n=scheduleTable.getRowCount();
    for(int z=0;z<n;z++){
   model= String.valueOf(scheduleTable.getValueAt(z , 2));
  description=String.valueOf(scheduleTable.getValueAt(z ,3));
     serial=String.valueOf(scheduleTable.getValueAt(z ,4)); 
    location= (String.valueOf(scheduleTable.getValueAt(z, 5)));
  servdate= String.valueOf(scheduleTable.getValueAt(z , 6));
  servetime  = String.valueOf(scheduleTable.getValueAt(z , 7));
  assigned = String.valueOf(scheduleTable.getValueAt(z ,8));
  phone = String.valueOf(scheduleTable.getValueAt(z , 9));
              
             
  Paragraph Paras110=new Paragraph(String.valueOf(z+1),font5);
 Paras11.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
 
           Paragraph Paras211=new Paragraph(model,font5);
                Paras211.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras2111=new Paragraph(description,font5);
                Paras2111.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras330=new Paragraph(serial,font5);
                Paras330.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras341=new Paragraph(location,font5);
                Paras341.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras311=new Paragraph(servdate,font5);
                Paras311.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
         Paragraph Paras3111=new Paragraph(servetime,font5);
       Paras3111.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT); 
       
      Paragraph Paras3112=new Paragraph(assigned,font5);
       Paras3112.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);           
  
        Paragraph Paras3113=new Paragraph(phone,font5);
       Paras3113.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);     
                
          subjreportTable.addCell(Paras110);subjreportTable.addCell(Paras211);
            subjreportTable.addCell(Paras2111); subjreportTable.addCell(Paras330);
         subjreportTable.addCell(Paras341);      subjreportTable.addCell(Paras311);
               subjreportTable.addCell(Paras3111);      subjreportTable.addCell(Paras3112);
                     subjreportTable.addCell(Paras3113);
             }
 receiptDoc.add( space);
   
      receiptDoc.add(subjreportTable);
   // receiptDoc.add(separatorlable3); 
    receiptDoc.add( space);
    
     float[] colswidth6=new float[]{4f,8f,15f};     
 PdfPTable signatureTable=new PdfPTable(colswidth6);
 signatureTable.setWidthPercentage(108); 
 signatureTable.setHorizontalAlignment(Element.ALIGN_LEFT);  

  PdfPCell cell29=new PdfPCell(new Phrase(" "));cell29.setBorder(Rectangle.NO_BORDER); 

    Paragraph sign5=new Paragraph("Authorised Sign: " ,font2);
   sign5.setAlignment(Paragraph.ALIGN_LEFT); 
    PdfPCell cell27=new PdfPCell(sign5);cell27.setBorder(Rectangle.NO_BORDER);
    signatureTable.addCell(cell27); 
    
     Paragraph sign56=new Paragraph(" ________________________ " ,font2);
   sign56.setAlignment(Paragraph.ALIGN_LEFT); 
    PdfPCell cell289=new PdfPCell(sign56);cell289.setBorder(Rectangle.NO_BORDER);
    signatureTable.addCell(cell289); 
    
      Paragraph sign562=new Paragraph(" Date:__________________" ,font2);
   sign562.setAlignment(Paragraph.ALIGN_LEFT); 
    PdfPCell cell283=new PdfPCell(sign562);cell283.setBorder(Rectangle.NO_BORDER);
    signatureTable.addCell(cell283); 
     signatureTable.addCell(cell29);    signatureTable.addCell(cell29);    signatureTable.addCell(cell29); 
    
  
   receiptDoc.add(signatureTable);  receiptDoc.add(space); receiptDoc.add(space);receiptDoc.add(space);
   
    Paragraph NOTE2=new Paragraph(" Stamp: ",font2);
            NOTE2.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE2); //receiptDoc.add(space);
   
  // receiptDoc.add(space);
  
   receiptDoc.add(space);receiptDoc.add(space); //receiptDoc.add(space);receiptDoc.add(space);
  //  receiptDoc.add(space);//receiptDoc.add(space); receiptDoc.add(space);receiptDoc.add(space);
   
   Paragraph NOTE=new Paragraph(" Please call 4455000 or email service@symphony.co.ke for enquiries. System Generated By: "+currentuser,font4);
            NOTE.setAlignment(Paragraph.ALIGN_CENTER);
     receiptDoc.add(NOTE); //receiptDoc.add(space);
     
  /* Paragraph user=new Paragraph(,font4);
            user.setAlignment(Paragraph.ALIGN_CENTER);
            receiptDoc.add(user);*/
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
    
    
    
    
    public boolean vaidCheck()
{
if(scheduleTable.getCellEditor()!=null){
scheduleTable.getCellEditor().stopCellEditing();
}
for(int i=0;i< scheduleTable.getRowCount();i++)
{
for (int j=0;j<scheduleTable.getColumnCount();j++)
{
    try{
 String om=scheduleTable.getValueAt(i,j).toString();
System.out.println("Value ="+om+ " and Length :"+om.length());
if(om.trim().length()==0)
{
return false;
}   
    } catch(NullPointerException ex){
   // JOptionPane.showMessageDialog(null, "Cell is empty !");
    }
}
}
return true;
}    
    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField contractnumTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    public javax.swing.JPanel pmschedulePanel;
    private javax.swing.JTable scheduleTable;
    // End of variables declaration//GEN-END:variables
}
