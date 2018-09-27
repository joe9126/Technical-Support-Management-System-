/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JOE
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Contracts extends javax.swing.JFrame {
    Home access=new Home();
String toDate, fromDate, address,town, contperson, mobile, CLIENTNAME,currency,billschedule,pmschedule;
     SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
   String[] cols={"NO","CONTRACT No.","CLIENT","DESCRIPTION","START","END ","VALUE ","PM","BILLING"};
   DecimalFormat df1=new DecimalFormat("#,###.00");
  DefaultTableModel contractstableModel=new DefaultTableModel(cols,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }  };
          int selectedcontract;
 String clientsname;     java.sql.Date startdateadded;
    String contractnum=null;
   /**
     * Creates new form Contracts
     */
    public Contracts() {
        initComponents();
         contractsTable.getTableHeader().setReorderingAllowed(false);
           contractsTable.getColumnModel().getColumn(0).setPreferredWidth(1);
    }
public void  getContracts(){
      try {
         int r= contractsTable.getRowCount();
         for(int g=0;g<r;g++){
          contractstableModel.removeRow(0);
         }
  String getcontractsql=""; 
 if(contractnum!=null){
  getcontractsql="SELECT CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,CONTRACTS.CONT_DESCRIP, CONTRACTS.START,CONTRACTS.END,"
         + "CONTRACTS.CURRENCY,CONTRACTS.VALUE,"
                  + "CONTRACTS.PM_SCHEDULE, CONTRACTS.BILL_SCHEDULE FROM CONTRACTS"
         + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO where CONTRACTS.CONTRACT_NO='"+contractnum+"' ORDER BY CLIENTS.CLIENTNAME ASC";
  }
 else if(searchclientTxt.getText().equalsIgnoreCase("Search client name")||searchclientTxt.getText().isEmpty()){
 getcontractsql="SELECT CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,CONTRACTS.CONT_DESCRIP, CONTRACTS.START,CONTRACTS.END,"
         + "CONTRACTS.CURRENCY,CONTRACTS.VALUE,"
                  + "CONTRACTS.PM_SCHEDULE, CONTRACTS.BILL_SCHEDULE FROM CONTRACTS"
         + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO ORDER BY CLIENTS.CLIENTNAME ASC";
  } 

 else if(!searchclientTxt.getText().equalsIgnoreCase("Search client name")||!searchclientTxt.getText().isEmpty()){
   getcontractsql="SELECT CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,CONTRACTS.CONT_DESCRIP, CONTRACTS.START,CONTRACTS.END,"
         + "CONTRACTS.CURRENCY,CONTRACTS.VALUE,"
                  + "CONTRACTS.PM_SCHEDULE, CONTRACTS.BILL_SCHEDULE FROM CONTRACTS"
         + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO WHERE CLIENTS.CLIENTNAME LIKE '"+searchclientTxt.getText()+"%' "
           + "ORDER BY CLIENTS.CLIENTNAME ASC";
  
  }
          Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
          PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
          ResultSet rst=pst.executeQuery(); int i=0;
          while(rst.next()){
         contractstableModel.addRow(new Object[]{i+1,rst.getString(1),rst.getString(2),rst.getString(3),sdf.format(rst.getDate(4)),sdf.format(rst.getDate(5)),
         rst.getString(6)+" "+df1.format(rst.getDouble(7)),rst.getString(8),rst.getString(9)}); 
        i++; 
 
        if(contractnum!=null){
    contractnumTxt.setText(rst.getString(1)); 
currencyTxt.setText(rst.getString(6));  descripTxt.setText(rst.getString(3)); 
valueTxt.setText(String.valueOf(rst.getDouble(7)));  contvalueTxt.setText(df1.format(rst.getDouble(7))); 
startdateTxt.setText(sdf.format(rst.getDate(4)));  pmscheduleTxt.setText(rst.getString(8)); 
enddateTxt.setText(sdf.format(rst.getDate(5)));  billingscheduleTxt.setText(rst.getString(9)); 
 
long daysold=(rst.getDate(5).getTime()-rst.getDate(4).getTime())/(1000*60*60*24);
 int years=(int)daysold/365;
 int months=((int)daysold%365)/31;
 int days= months%31;
 if(years>0&&months>0){
 periodTxt.setText(years+" Years,"+months+" Months");
 }
 else if(years>0){
 periodTxt.setText(years+" Years");
 } else if(months>0&& days>0){
 periodTxt.setText(months+" Months,"+days+" Days");
 }else if(months>0){
 periodTxt.setText(months+" Months");
 }
 else if(days>0){
 periodTxt.setText(days+" Days");
 }      
        }
          }
          

 

      } catch (SQLException ex) {
          Logger.getLogger(Contracts.class.getName()).log(Level.SEVERE, null, ex);
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

        contractsPanel = new javax.swing.JPanel();
        scrollholderPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        contractnumTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        descripTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        startdateTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        currencyCombo = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        valueTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        enddateTxt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        pmscheduleCombo = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        billingscheduleCombo = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        contvalueTxt = new javax.swing.JTextField();
        startdatePicker = new org.jdesktop.swingx.JXDatePicker();
        enddatePicker = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        periodTxt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        currencyTxt = new javax.swing.JTextField();
        pmscheduleTxt = new javax.swing.JTextField();
        billingscheduleTxt = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cleintnameTxt = new javax.swing.JTextField();
        cleintnoTxt = new javax.swing.JTextField();
        addressTxt = new javax.swing.JTextField();
        cityTxt = new javax.swing.JTextField();
        contactpersonTxt = new javax.swing.JTextField();
        telephoneTxt = new javax.swing.JTextField();
        emailTxt = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        searchclientTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        contractsTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        fromdateTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        todateTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contractsPanel.setBackground(new java.awt.Color(153, 0, 153));
        contractsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONTRACTS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        scrollholderPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " CONTRACT DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("CONTRACT NO:");

        contractnumTxt.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("DESCRIPTION:");

        descripTxt.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("START DATE:");

        startdateTxt.setEditable(false);
        startdateTxt.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("CURRENCY:");

        currencyCombo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        currencyCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "KES", "USD", "EUR" }));
        currencyCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currencyComboActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("TOTAL VALUE:");

        valueTxt.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        valueTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                valueTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                valueTxtKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("END DATE:");

        enddateTxt.setEditable(false);
        enddateTxt.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        enddateTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enddateTxtActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("PM SCHEDULE:");

        pmscheduleCombo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pmscheduleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ONE-OFF", "MONTHLY", "QUARTERLY", "SEMI-ANNUALLY", "ANNUALLY", "N/A" }));
        pmscheduleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmscheduleComboActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("BILLING CYCLE:");

        billingscheduleCombo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        billingscheduleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MONTHLY", "QUARTERLY", "SEMI-ANNUALLY", "ANNUALLY", "ONCE", "N/A" }));
        billingscheduleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billingscheduleComboActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setText("DELETE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setText("SAVE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        contvalueTxt.setEditable(false);
        contvalueTxt.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N

        startdatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startdatePickerActionPerformed(evt);
            }
        });

        enddatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enddatePickerActionPerformed(evt);
            }
        });

        jLabel3.setText("PERIOD COVERED:");

        periodTxt.setEditable(false);
        periodTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        periodTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        currencyTxt.setEditable(false);
        currencyTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        pmscheduleTxt.setEditable(false);

        billingscheduleTxt.setEditable(false);

        jButton5.setText("CLEAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(periodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(descripTxt)
                                    .addComponent(contractnumTxt)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(enddatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(startdatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(startdateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                            .addComponent(enddateTxt))))))
                        .addGap(42, 42, 42)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(27, 27, 27)
                                .addComponent(billingscheduleCombo, 0, 1, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(29, 29, 29)
                                .addComponent(pmscheduleCombo, 0, 1, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(valueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(currencyCombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(contvalueTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(currencyTxt)
                            .addComponent(pmscheduleTxt)
                            .addComponent(billingscheduleTxt)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)))
                .addGap(60, 60, 60))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(contractnumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(currencyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(currencyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(descripTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(valueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(contvalueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(startdateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(startdatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(pmscheduleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pmscheduleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(enddateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enddatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(billingscheduleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(billingscheduleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 74, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(periodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jButton4)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton5))))
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CLIENT DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(204, 0, 204))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("CLIENT NAME:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("CLIENT NO:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("PO BOX:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("CITY:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("CONTACT P.");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("TELEPHONE:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("EMAIL:");

        cleintnameTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cleintnameTxtMouseExited(evt);
            }
        });
        cleintnameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cleintnameTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cleintnameTxtKeyTyped(evt);
            }
        });

        cleintnoTxt.setEditable(false);
        cleintnoTxt.setText(" ");

        addressTxt.setEditable(false);
        addressTxt.setText(" ");

        cityTxt.setEditable(false);
        cityTxt.setText(" ");
        cityTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityTxtActionPerformed(evt);
            }
        });

        contactpersonTxt.setEditable(false);
        contactpersonTxt.setText(" ");

        telephoneTxt.setEditable(false);
        telephoneTxt.setText(" ");

        emailTxt.setEditable(false);
        emailTxt.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(51, 51, 51)
                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(telephoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(19, 19, 19)
                        .addComponent(contactpersonTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cleintnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addGap(19, 19, 19)
                            .addComponent(cleintnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cleintnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cleintnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(contactpersonTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telephoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONTRACTS LIST", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        searchclientTxt.setText("Search Client Name");
        searchclientTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchclientTxtMouseClicked(evt);
            }
        });
        searchclientTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchclientTxtKeyReleased(evt);
            }
        });

        jButton1.setText("PRINT");

        contractsTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        contractsTable.setModel(contractstableModel);
        contractsTable.setFillsViewportHeight(true);
        contractsTable.setGridColor(new java.awt.Color(153, 0, 153));
        contractsTable.setRowHeight(30);
        contractsTable.setRowMargin(2);
        contractsTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        contractsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contractsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(contractsTable);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Contract Period", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel1.setText("FROM:");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        fromdateTxt.setEditable(false);

        jLabel2.setText("TO:");

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        todateTxt.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(fromdateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(todateTxt))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(fromdateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(todateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchclientTxt))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(23, 23, 23)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(searchclientTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout scrollholderPanelLayout = new javax.swing.GroupLayout(scrollholderPanel);
        scrollholderPanel.setLayout(scrollholderPanelLayout);
        scrollholderPanelLayout.setHorizontalGroup(
            scrollholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scrollholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scrollholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(scrollholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(scrollholderPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(910, Short.MAX_VALUE)))
        );
        scrollholderPanelLayout.setVerticalGroup(
            scrollholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scrollholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(scrollholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(scrollholderPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(402, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout contractsPanelLayout = new javax.swing.GroupLayout(contractsPanel);
        contractsPanel.setLayout(contractsPanelLayout);
        contractsPanelLayout.setHorizontalGroup(
            contractsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contractsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        contractsPanelLayout.setVerticalGroup(
            contractsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractsPanelLayout.createSequentialGroup()
                .addComponent(scrollholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contractsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contractsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void currencyComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currencyComboActionPerformed
        currency=""+currencyCombo.getSelectedItem();
        currencyTxt.setText(currency);
    }//GEN-LAST:event_currencyComboActionPerformed

    private void valueTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_valueTxtKeyReleased
        if(valueTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(valueTxt.getText());
            if(value>0){
                contvalueTxt.setText(currencyCombo.getSelectedItem()+" "+df1.format(value));
            }
        }
    }//GEN-LAST:event_valueTxtKeyReleased

    private void valueTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_valueTxtKeyTyped
        char c=evt.getKeyChar();
        if(!((c>='0')&&(c<='9') )){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_valueTxtKeyTyped

    private void enddateTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enddateTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enddateTxtActionPerformed

    private void pmscheduleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmscheduleComboActionPerformed
        pmschedule=""+pmscheduleCombo.getSelectedItem();
        pmscheduleTxt.setText(pmschedule);
    }//GEN-LAST:event_pmscheduleComboActionPerformed

    private void billingscheduleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billingscheduleComboActionPerformed
        billschedule=""+billingscheduleCombo.getSelectedItem();
        billingscheduleTxt.setText(billschedule);
    }//GEN-LAST:event_billingscheduleComboActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(cleintnameTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter client name!","Client Number Required",JOptionPane.WARNING_MESSAGE);
        }else{
            if(contractnumTxt.getText().isEmpty()){
               JOptionPane.showMessageDialog(null,"Please enter contract number!","Contract Number Required",JOptionPane.WARNING_MESSAGE);
            }else if(descripTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Please enter contract description!","Contract Description Required",JOptionPane.WARNING_MESSAGE);
            }else if(fromDate==null){
                JOptionPane.showMessageDialog(null,"Please select contract start date!","Start Date Required",JOptionPane.WARNING_MESSAGE);
            }else  if(toDate==null){
                JOptionPane.showMessageDialog(null,"Please select contract end date!","End Date Required",JOptionPane.WARNING_MESSAGE);
            }else if(currency==null){
                JOptionPane.showMessageDialog(null,"Please select vaule currency!","Currency Required",JOptionPane.WARNING_MESSAGE);
            }
            else if(valueTxt.getText().trim().length()==0){
                JOptionPane.showMessageDialog(null,"Please enter contract vaule !","Contract Value Required",JOptionPane.WARNING_MESSAGE);
            }else if(pmschedule==null){
                JOptionPane.showMessageDialog(null,"Please select PM Schedule !","PM Schedule Required",JOptionPane.WARNING_MESSAGE);
            }else if(billschedule==null){
                JOptionPane.showMessageDialog(null,"Please select Billing Schedule !","Billing Schedule Required",JOptionPane.WARNING_MESSAGE);
            }else{
                int choice=JOptionPane.showConfirmDialog(null, "Do you want to save contract number "+contractnumTxt.getText()+" for "+cleintnameTxt.getText()+" ?"
                    ,"Confirm",JOptionPane.YES_NO_OPTION);

                if(choice==JOptionPane.YES_OPTION){
                    try {
                        String getusersql="SELECT CONT_DESCRIP FROM CONTRACTS WHERE CONTRACT_NO='"+contractnumTxt.getText()+"'";
                        Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
                        PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getusersql);
                        ResultSet rst=pst.executeQuery(); int i=0;
                        while(rst.next()){
                            i++;
                        }
                        if(i>0){
                            JOptionPane.showMessageDialog(null,"Contract Number must be unique!","Contract Number Required",JOptionPane.WARNING_MESSAGE);
                        }else{
        String insertsql="INSERT INTO CONTRACTS VALUES(?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pst3=(PreparedStatement)connectDb.prepareStatement(insertsql);
                            pst3.setString(1, contractnumTxt.getText()); pst3.setString(2, cleintnoTxt.getText());
                            pst3.setString(3, descripTxt.getText( )); pst3.setDate(4, new java.sql.Date(startdatePicker.getDate().getTime()));
                            pst3.setDate(5, new java.sql.Date(enddatePicker.getDate().getTime())); pst3.setString(6, currency);
                            pst3.setDouble(7, Double.parseDouble(valueTxt.getText())); pst3.setString(8, pmschedule);
                            pst3.setString(9, billschedule);

                            int p= pst3.executeUpdate();
                            if(p>0){
                                JOptionPane.showMessageDialog(null,"Contract Number "+contractnumTxt.getText()+" details saved successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                        String insertsql="";
                    } catch (SQLException ex) {
                  Logger.getLogger(Contracts.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cityTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityTxtActionPerformed

    private void startdatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startdatePickerActionPerformed
       SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
    fromDate=sdf.format(startdatePicker.getDate());
      System.out.println("to date: "+fromDate);
      startdateTxt.setText(fromDate);
    }//GEN-LAST:event_startdatePickerActionPerformed

    private void enddatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enddatePickerActionPerformed
      if(enddatePicker.getDate().before(startdatePicker.getDate())){
   JOptionPane.showMessageDialog(null,"Please select a valid Contract End Date!","Invalid Date",JOptionPane.WARNING_MESSAGE);
      }else{
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
toDate=sdf.format( enddatePicker.getDate());
     // System.out.println("to date: "+toDate);
    enddateTxt.setText(toDate);
 /*  
 java.util.Date date=java.sql.Date.valueOf(String.valueOf(lastrepay));
  System.out.println(date);
  Calendar c=new GregorianCalendar();
  c.setTime(date);
  c.add(Calendar.DATE, 31);
  java.util.Date nextrepayment=c.getTime();
  System.out.println("Next repay "+sdf.format(nextrepayment));
  
  Date today=java.sql.Date.valueOf(LocalDate.now());
  */
getContractlength();
   // int difference=enddatePicker.getDate().
    
      }
    }//GEN-LAST:event_enddatePickerActionPerformed

    private void cleintnameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cleintnameTxtKeyReleased

    }//GEN-LAST:event_cleintnameTxtKeyReleased

    private void cleintnameTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cleintnameTxtMouseExited
     cleintnameTxt.setText(clientsname);
    }//GEN-LAST:event_cleintnameTxtMouseExited

    private void cleintnameTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cleintnameTxtKeyTyped
      getClients();
    }//GEN-LAST:event_cleintnameTxtKeyTyped

    private void searchclientTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchclientTxtKeyReleased
     getContracts();
    }//GEN-LAST:event_searchclientTxtKeyReleased
public void getContractlength(){
 long daysold=(enddatePicker.getDate().getTime()-startdatePicker.getDate().getTime())/(1000*60*60*24);
 int years=(int)daysold/365;
 int months=((int)daysold%365)/31;
 int days= months%31;
 if(years>0&&months>0){
 periodTxt.setText(years+" Years,"+months+" Months");
 }
 else if(years>0){
 periodTxt.setText(years+" Years");
 } else if(months>0&& days>0){
 periodTxt.setText(months+" Months,"+days+" Days");
 }else if(months>0){
 periodTxt.setText(months+" Months");
 }
 else if(days>0){
 periodTxt.setText(days+" Days");
 }
}
    private void searchclientTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchclientTxtMouseClicked
       searchclientTxt.setText(null);
    }//GEN-LAST:event_searchclientTxtMouseClicked

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
      fromdateTxt.setText(sdf.format(jXDatePicker1.getDate())); 
   startdateadded=new java.sql.Date(jXDatePicker1.getDate().getTime());
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
     todateTxt.setText(sdf.format(jXDatePicker2.getDate())); 
 java.sql.Date enddateadded=new java.sql.Date(jXDatePicker2.getDate().getTime());
    
   try {
        String  getcontractsql="SELECT CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,CONTRACTS.CONT_DESCRIP, CONTRACTS.START,CONTRACTS.END,"
         + "CONTRACTS.CURRENCY,CONTRACTS.VALUE,"
                  + "CONTRACTS.PM_SCHEDULE, CONTRACTS.BILL_SCHEDULE FROM CONTRACTS"
         + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO WHERE  CONTRACTS.START >= '"+ startdateadded+"' "
                + "and CONTRACTS.END <='"+enddateadded+"'"
           + "ORDER BY CONTRACTS.END ASC";
  
  int r= contractsTable.getRowCount();
         for(int g=0;g<r;g++){
          contractstableModel.removeRow(0);
         }
          Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
          PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
          ResultSet rst=pst.executeQuery(); int i=0;
          while(rst.next()){
         contractstableModel.addRow(new Object[]{i+1,rst.getString(1),rst.getString(2),rst.getString(3),sdf.format(rst.getDate(4)),sdf.format(rst.getDate(5)),
         rst.getString(6)+" "+df1.format(rst.getDouble(7)),rst.getString(8),rst.getString(9)}); 
        i++;  }
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }//GEN-LAST:event_jXDatePicker2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
  if(contractnumTxt.getText().isEmpty()){
  JOptionPane.showMessageDialog(null, "Please enter the contract number or select a contract from the Contracts List!","Contract Number Required",JOptionPane.WARNING_MESSAGE);
  }
  else{     
        int choice=JOptionPane.showConfirmDialog(null, "Do you want to DELETE contract number "+contractnumTxt.getText()+" for "+cleintnameTxt.getText()+" ?"
                    ,"Confirm",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_OPTION){
        try {
            String deltesql="DELETE FROM CONTRACTS WHERE CONTRACT_NO ='"+contractnumTxt.getText()+"'";
   Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
  PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(deltesql);
    int g=pst.executeUpdate();
    if(g>0){
  JOptionPane.showMessageDialog(null, "Contract Number "+contractnumTxt.getText()+" for "+cleintnameTxt.getText()+" deleted successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
    clearTxt();
    }
        } catch (SQLException ex) {
            Logger.getLogger(Contracts.class.getName()).log(Level.SEVERE, null, ex);
        }
  
  }
  }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void contractsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contractsTableMouseClicked
      selectedcontract=contractsTable.getSelectedRow();
    contractnum=String.valueOf(contractsTable.getValueAt(selectedcontract, 1));
    getClients(); 
   getContracts();
  
    }//GEN-LAST:event_contractsTableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         if(contractnumTxt.getText().isEmpty()){
               JOptionPane.showMessageDialog(null,"Please enter contract number!","Contract Number Required",JOptionPane.WARNING_MESSAGE);
            }else if(descripTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Please enter contract description!","Contract Description Required",JOptionPane.WARNING_MESSAGE);
            }  else if(currencyTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Please select vaule currency!","Currency Required",JOptionPane.WARNING_MESSAGE);
            }
            else if(valueTxt.getText().trim().length()==0){
                JOptionPane.showMessageDialog(null,"Please enter contract vaule !","Contract Value Required",JOptionPane.WARNING_MESSAGE);
            }else if(pmscheduleTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Please select PM Schedule !","PM Schedule Required",JOptionPane.WARNING_MESSAGE);
            }else if(billingscheduleTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Please select Billing Schedule !","Billing Schedule Required",JOptionPane.WARNING_MESSAGE);
            }else{
                int choice=JOptionPane.showConfirmDialog(null, "Do you want to update contract number "+contractnumTxt.getText()+" for "+cleintnameTxt.getText()+" ?"
                    ,"Confirm",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_OPTION){
                    try {
  String updatesql;
   Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
  PreparedStatement pst;
                        if(fromDate!=null&&toDate==null){
      updatesql="UPDATE CONTRACTS SET CONT_DESCRIP=?, START=?, CURRENCY=?,VALUE=?, PM_SCHEDULE=?, BILL_SCHEDULE=? WHERE CONTRACT_NO='"+contractnumTxt.getText()+"'";  
     pst=(PreparedStatement)connectDb.prepareStatement(updatesql);
        pst.setString(1,descripTxt.getText()); 
        pst.setDate(2,new java.sql.Date(startdatePicker.getDate().getTime()));
        pst.setString(3,currencyTxt.getText()) ;
  pst.setString(4,valueTxt.getText() );
  pst.setString(5,pmscheduleTxt.getText());
  pst.setString( 6,billingscheduleTxt.getText());   
                        }
                        else if(toDate!=null&& fromDate==null){
     updatesql="UPDATE CONTRACTS SET CONT_DESCRIP=?, END=?,CURRENCY=?, VALUE=?, PM_SCHEDULE=?, BILL_SCHEDULE=? WHERE CONTRACT_NO='"+contractnumTxt.getText()+"'";   
     pst=(PreparedStatement)connectDb.prepareStatement(updatesql);
    pst.setString(1,descripTxt.getText()); 
    pst.setDate(2,new java.sql.Date(enddatePicker.getDate().getTime()));
 pst.setString(3,currencyTxt.getText()) ;
  pst.setString(4,valueTxt.getText() );
  pst.setString(5,pmscheduleTxt.getText());
  pst.setString( 6,billingscheduleTxt.getText());  
                        }
                  else if(toDate!=null&& fromDate!=null){
    updatesql="UPDATE CONTRACTS SET CONT_DESCRIP=?, START=?, END=?,CURRENCY=?, VALUE=?, PM_SCHEDULE=?, BILL_SCHEDULE=? WHERE CONTRACT_NO='"+contractnumTxt.getText()+"'";   
    pst=(PreparedStatement)connectDb.prepareStatement(updatesql);
             pst.setString(1,descripTxt.getText());  
        pst.setDate(2,new java.sql.Date(startdatePicker.getDate().getTime()));
          pst.setDate(3,new java.sql.Date(enddatePicker.getDate().getTime()));
    pst.setString(4,currencyTxt.getText()) ;
  pst.setString(5,valueTxt.getText() ); 
  pst.setString(6,pmscheduleTxt.getText());
  pst.setString( 7,billingscheduleTxt.getText());   
                  } else{
   updatesql="UPDATE CONTRACTS SET CONT_DESCRIP=?, CURRENCY=?, VALUE=?, PM_SCHEDULE=?, BILL_SCHEDULE=? WHERE CONTRACT_NO='"+contractnumTxt.getText()+"'";   
  pst=(PreparedStatement)connectDb.prepareStatement(updatesql);                     
 pst.setString(1,descripTxt.getText());  pst.setString(2,currencyTxt.getText()) ;
  pst.setString(3,valueTxt.getText() ); pst.setString(4,pmscheduleTxt.getText());
  pst.setString( 5,billingscheduleTxt.getText());            
                  } 
                       
  int f=pst.executeUpdate();          
  
  if(f>0){
   getContracts();   
  JOptionPane.showMessageDialog(null, "Contract Number "+contractnumTxt.getText()+" for "+cleintnameTxt.getText()+" updated successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
 clearTxt();
  }
  
                    } catch (SQLException ex) {
                        Logger.getLogger(Contracts.class.getName()).log(Level.SEVERE, null, ex);
                    }
  }              
            }
          
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
clearTxt();
    }//GEN-LAST:event_jButton5ActionPerformed
   public void clearTxt(){
    int r= contractsTable.getRowCount();
         for(int g=0;g<r;g++){
          contractstableModel.removeRow(0);
         }
contractnumTxt.setText(null);   descripTxt.setText(null); 
 currencyTxt.setText(null); 
 valueTxt.setText(null); 
 pmscheduleTxt.setText(null); 
 billingscheduleTxt.setText(null); fromDate=null; toDate=null; contvalueTxt.setText(null);
 startdateTxt.setText(null);  enddateTxt.setText(null);  periodTxt .setText(null); 
  cleintnoTxt.setText(null);  addressTxt.setText(null); 
     cityTxt.setText(null);  contactpersonTxt.setText(null);  
     telephoneTxt.setText(null);  emailTxt.setText(null);    cleintnameTxt.setText(null);
contractnum=null; getContracts();
    } 
   
   
public void getClients(){
        try {
            String getcontractsql="SELECT CLIENTNAME,CLIENT_NO,POBOX,TOWN,CONT_PERSON,MOBILE, EMAIL FROM CLIENTS WHERE CLIENTNAME LIKE "
                    + " '"+ cleintnameTxt.getText()+"%' ORDER BY CLIENTNAME DESC";
      if(contractnum!=null){
       getcontractsql="SELECT CLIENTS.CLIENTNAME,CLIENTS.CLIENT_NO,CLIENTS.POBOX,CLIENTS.TOWN,CLIENTS.CONT_PERSON,"
               + "CLIENTS.MOBILE, CLIENTS.EMAIL,CONTRACTS.CONTRACT_NO FROM CLIENTS LEFT JOIN CONTRACTS ON "
               + "CLIENTS.CLIENT_NO=CONTRACTS.CLIENT_NO WHERE CONTRACTS.CONTRACT_NO="
                    + " '"+contractnum+"' ";      
      }      
      
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
        // getContracts();
            while(rst.next()){
                
              if(contractnum!=null){
             cleintnameTxt .setText(rst.getString(1));
              }  
                clientsname=rst.getString(1);
     cleintnoTxt.setText(rst.getString(2));  addressTxt.setText(rst.getString(3)); 
     cityTxt.setText(rst.getString(4));  contactpersonTxt.setText(rst.getString(5));  
     telephoneTxt.setText(rst.getString(6));  emailTxt.setText(rst.getString(7)); 
                i++;  
            }if(i<1){
          cleintnoTxt.setText(null);  addressTxt.setText(null); 
     cityTxt.setText(null);  contactpersonTxt.setText(null);  
     telephoneTxt.setText(null);  emailTxt.setText(null);    
      }
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTxt;
    private javax.swing.JComboBox<String> billingscheduleCombo;
    private javax.swing.JTextField billingscheduleTxt;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JTextField cleintnameTxt;
    private javax.swing.JTextField cleintnoTxt;
    private javax.swing.JTextField contactpersonTxt;
    private javax.swing.JTextField contractnumTxt;
    public javax.swing.JPanel contractsPanel;
    private javax.swing.JTable contractsTable;
    private javax.swing.JTextField contvalueTxt;
    private javax.swing.JComboBox<String> currencyCombo;
    private javax.swing.JTextField currencyTxt;
    private javax.swing.JTextField descripTxt;
    private javax.swing.JTextField emailTxt;
    private org.jdesktop.swingx.JXDatePicker enddatePicker;
    private javax.swing.JTextField enddateTxt;
    private javax.swing.JTextField fromdateTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JTextField periodTxt;
    private javax.swing.JComboBox<String> pmscheduleCombo;
    private javax.swing.JTextField pmscheduleTxt;
    private javax.swing.JPanel scrollholderPanel;
    private javax.swing.JTextField searchclientTxt;
    private org.jdesktop.swingx.JXDatePicker startdatePicker;
    private javax.swing.JTextField startdateTxt;
    private javax.swing.JTextField telephoneTxt;
    private javax.swing.JTextField todateTxt;
    private javax.swing.JTextField valueTxt;
    // End of variables declaration//GEN-END:variables
}
