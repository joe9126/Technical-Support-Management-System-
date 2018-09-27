
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JOE
 */
public class Mileagereport extends javax.swing.JFrame {
  NumberFormat formatter=NumberFormat.getNumberInstance(Locale.UK); 
   DefaultComboBoxModel techcomboModel=new DefaultComboBoxModel();
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
    Home access=new Home(); double currentcyclevalue=0;
    String descrip,client; java.sql.Date serviceFrom,serviceTo; 
 DefaultComboBoxModel  descriptioncomboModel=new DefaultComboBoxModel();
DefaultComboBoxModel clientcomboModel=new DefaultComboBoxModel();
DefaultComboBoxModel clientcomboModel1=new DefaultComboBoxModel();
DefaultComboBoxModel contractcomboModel1 =new DefaultComboBoxModel();
String technician;
  String[] cols3={"No.","CALL NO","SERVICE NO","SERVICE DATE","CLIENT","LOCATION","DONE BY","TOTAL MILEAGE"};  
DefaultTableModel    reportstableModel =new DefaultTableModel(cols3,0){
         @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    };
         
    public Mileagereport() {
        initComponents();
       reportsTable.getTableHeader().setReorderingAllowed(false);
          reportsTable.getColumnModel().getColumn(0).setPreferredWidth(1);
 reportsTable.getColumnModel().getColumn(1).setPreferredWidth(2);
    }

   public void getTechnicians(){
  try {
            String getcontractsql="SELECT STAFFNAME   FROM STAFF WHERE POST='TECHNICIAN'  ORDER BY STAFFNAME ASC";
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contractreportPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        adminnewcallsPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        clientsCombo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        contractnumTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        startTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        endTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        valueTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        billingTxt = new javax.swing.JTextField();
        pmcycleTxt = new javax.swing.JTextField();
        currentcycleTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cyclevalueTxt = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        servicefromTxt = new javax.swing.JTextField();
        servicetoTxt = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        grandclaimTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        cyclevalueagainstclaimTxt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contractreportPanel.setBackground(new java.awt.Color(153, 0, 153));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        adminnewcallsPanel.setBackground(new java.awt.Color(255, 255, 255));
        adminnewcallsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONTRACT DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 0, 204))); // NOI18N
        adminnewcallsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminnewcallsPanelMouseEntered(evt);
            }
        });

        jLabel4.setText("CLIENT:");

        clientsCombo.setModel(clientcomboModel);
        clientsCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clientsComboMouseEntered(evt);
            }
        });
        clientsCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientsComboActionPerformed(evt);
            }
        });

        jLabel6.setText("CONTRACT:");

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox3.setModel(descriptioncomboModel);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel5.setText("CONTRACT NO:");

        contractnumTxt.setEditable(false);
        contractnumTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel7.setText("START DATE:");

        startTxt.setEditable(false);
        startTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel8.setText("EXPIRY DATE:");

        endTxt.setEditable(false);
        endTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setText("CONTRACT VALUE:");

        valueTxt.setEditable(false);
        valueTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setText("BILLING CYCLE:");

        jLabel10.setText("PM CYCLE:");

        jLabel11.setText(" CURRENT CYCLE:");

        billingTxt.setEditable(false);
        billingTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        pmcycleTxt.setEditable(false);
        pmcycleTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        currentcycleTxt.setEditable(false);
        currentcycleTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel12.setText(" CYCLE VALUE:");

        cyclevalueTxt.setEditable(false);
        cyclevalueTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout adminnewcallsPanelLayout = new javax.swing.GroupLayout(adminnewcallsPanel);
        adminnewcallsPanel.setLayout(adminnewcallsPanelLayout);
        adminnewcallsPanelLayout.setHorizontalGroup(
            adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))
                        .addGap(27, 27, 27)
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(startTxt)
                                .addComponent(jComboBox3, 0, 200, Short.MAX_VALUE)
                                .addComponent(contractnumTxt)
                                .addComponent(clientsCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(endTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cyclevalueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pmcycleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(billingTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(valueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                    .addContainerGap(465, Short.MAX_VALUE)
                    .addComponent(currentcycleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        adminnewcallsPanelLayout.setVerticalGroup(
            adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(clientsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(billingTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(contractnumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(pmcycleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(valueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(startTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(endTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cyclevalueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                    .addGap(137, 137, 137)
                    .addComponent(currentcycleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(83, Short.MAX_VALUE)))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SERVICE PERIOD", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 0, 204))); // NOI18N

        jLabel1.setText("SERVICE  FROM:");

        jLabel2.setText("SERVICE TO:");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        servicefromTxt.setEditable(false);

        servicetoTxt.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(28, 28, 28)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(servicefromTxt)
                    .addComponent(servicetoTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servicefromTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servicetoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("TECHNICIAN"));

        jComboBox1.setModel(techcomboModel);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel15.setText("STAFF NAME:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(45, 45, 45)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operations", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 0, 204))); // NOI18N

        jLabel13.setText("TOTAL CLAIM:");

        grandclaimTxt.setEditable(false);

        jButton1.setText("PRINT");

        jLabel14.setText("CLAIMS/CYCLE VALUE:");

        cyclevalueagainstclaimTxt.setEditable(false);

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grandclaimTxt))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cyclevalueagainstclaimTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(grandclaimTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cyclevalueagainstclaimTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminnewcallsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adminnewcallsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SERVICES & MILEAGES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 0, 204))); // NOI18N

        reportsTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reportsTable.setModel(reportstableModel);
        reportsTable.setFillsViewportHeight(true);
        reportsTable.setGridColor(new java.awt.Color(204, 0, 204));
        reportsTable.setRowHeight(30);
        reportsTable.setRowMargin(2);
        reportsTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        jScrollPane1.setViewportView(reportsTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1276, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout contractreportPanelLayout = new javax.swing.GroupLayout(contractreportPanel);
        contractreportPanel.setLayout(contractreportPanelLayout);
        contractreportPanelLayout.setHorizontalGroup(
            contractreportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contractreportPanelLayout.setVerticalGroup(
            contractreportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractreportPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contractreportPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contractreportPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clientsComboMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientsComboMouseEntered
        getClients();
    }//GEN-LAST:event_clientsComboMouseEntered

    private void clientsComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientsComboActionPerformed
        client=""+clientsCombo.getSelectedItem();
        getContracts();
    }//GEN-LAST:event_clientsComboActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        descrip=""+jComboBox3.getSelectedItem();
        try {
            String getcontractsql="SELECT CONTRACTS.CONTRACT_NO,CONTRACTS.CONT_DESCRIP,CONTRACTS.START, CONTRACTS.END, CONTRACTS.CURRENCY,"
                    + "CONTRACTS.VALUE,CONTRACTS.PM_SCHEDULE,CONTRACTS.BILL_SCHEDULE FROM CONTRACTS "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO = CLIENTS.CLIENT_NO WHERE CONTRACTS.CONT_DESCRIP ='"+descrip+"' AND "
            + " CLIENTS.CLIENTNAME='"+client+"'";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            while(rst.next()){
                contractnumTxt.setText(rst.getString(1));
                startTxt.setText(sdf.format(rst.getDate(3)));
                endTxt.setText(sdf.format(rst.getDate(4)));
                valueTxt.setText(rst.getString(5)+" "+formatter.format(rst.getDouble(6))); 
                 pmcycleTxt.setText(rst.getString(7));
                billingTxt.setText(rst.getString(8));
          
       int currentmonth=LocalDate.now().getMonthValue();
       String   currentcycle="NONE";
    if(rst.getString(8).equalsIgnoreCase("QUARTERLY")){
       if(currentmonth<=3){
       currentcycle="QUARTER 1";
       }else if(currentmonth<=6&&currentmonth>=4){
       currentcycle="QUARTER 2";
       }else if(currentmonth<=9&&currentmonth>=7){
       currentcycle="QUARTER 3";
       }else if(currentmonth<=12&&currentmonth>=10){
       currentcycle="QUARTER 4";
       }
      currentcyclevalue=   rst.getDouble(6)/4;
     }
    else  if(rst.getString(8).equalsIgnoreCase("SEMI-ANNUALLY")){
      if(currentmonth<=6){
       currentcycle="FIRST HALF";
       }else if(currentmonth<=12&&currentmonth>=7){
       currentcycle="SECOND HALF";
        }
    currentcyclevalue=   rst.getDouble(6)/2;     }
    else if(rst.getString(8).equalsIgnoreCase("ANNUALLY")){
        currentcycle="YEAR 1";
          currentcyclevalue=   rst.getDouble(6);
       }
    
     currentcycleTxt.setText(currentcycle);
     
  cyclevalueTxt.setText(rst.getString(5)+" "+formatter.format(currentcyclevalue));
  getReport();
                i++;
            }
            connectDb.close();  }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void adminnewcallsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminnewcallsPanelMouseEntered

    }//GEN-LAST:event_adminnewcallsPanelMouseEntered

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
   serviceFrom=new java.sql.Date(jXDatePicker2.getDate().getTime());
   servicefromTxt.setText(sdf.format(serviceFrom)); 
    }//GEN-LAST:event_jXDatePicker2ActionPerformed

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
if(contractnumTxt.getText().isEmpty()){
JOptionPane.showMessageDialog(null, "Please select the Client and contract!","Contract Required", JOptionPane.WARNING_MESSAGE);
}
      else  if(serviceFrom==null){
JOptionPane.showMessageDialog(null, "Please select the Service From Date!","Service From Required", JOptionPane.WARNING_MESSAGE);
}
else{    serviceTo=new java.sql.Date(jXDatePicker1.getDate().getTime());
   servicetoTxt.setText(sdf.format(serviceTo));    
   getReport();
}
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
       technician=""+jComboBox1.getSelectedItem();
     getReport();    
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    technician=null; serviceFrom=null;serviceTo=null;contractnumTxt=null; startTxt=null;
    endTxt=null; valueTxt=null; billingTxt=null; pmcycleTxt=null;  currentcycleTxt=null; cyclevalueTxt =null;
    grandclaimTxt=null;  cyclevalueagainstclaimTxt =null;
            descrip=null; client=null;
    }//GEN-LAST:event_jButton2ActionPerformed
    
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
           
           contractcomboModel1.removeAllElements();
            contractcomboModel1.addElement("--Select Contract--"); 
            while(rst.next()){
    descriptioncomboModel.addElement(rst.getString(1)); 
    contractcomboModel1.addElement(rst.getString(1)); 
             i++;  
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
   } 
    
public void getClients(){
        try {
            String getcontractsql="SELECT CLIENTNAME FROM CLIENTS ORDER BY CLIENTNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            clientcomboModel.removeAllElements();
              clientcomboModel.addElement("--Select Client--"); 
              
                 clientcomboModel1.removeAllElements();
              clientcomboModel1.addElement("--Select Client--"); 
            while(rst.next()){
       clientcomboModel.addElement(rst.getString(1)); 
        clientcomboModel1.addElement(rst.getString(1)); 
                i++;  
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
}
 public void getReport(){
      try {
          String reportsql;
if(serviceTo!=null&!contractnumTxt.getText().isEmpty()&&technician!=null&& !technician.equalsIgnoreCase("-Select Technician-")){
    reportsql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,CONTRACTS.CONTRACT_NO, "
                  + "CLIENTS.CLIENTNAME,SERVICE.LOCATION,STAFF.STAFFNAME,CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,CLAIMS.ACCOMOD,"
                  + "CLAIMS.LAUNDRY,CLAIMS.PETTIES,CLAIMS.OTHERS FROM CALLS "
                  + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                  + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                  + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                  + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                  + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
                  + "WHERE CALLS.CONTRACT_NO ='"+contractnumTxt.getText()+"' AND  STAFF.STAFFNAME='"+technician+"' AND SERVICE.SERVICE_DATE>='"+serviceFrom+"' AND SERVICE.SERVICE_DATE<='"+serviceTo+"'"
                  + " ORDER BY SERVICE.SERVICE_DATE DESC";  
     
     }          
else if( !contractnumTxt.getText().isEmpty() && technician!=null && !technician.equalsIgnoreCase("-Select Technician-")){
    reportsql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,CONTRACTS.CONTRACT_NO, "
                  + "CLIENTS.CLIENTNAME,SERVICE.LOCATION,STAFF.STAFFNAME,CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,CLAIMS.ACCOMOD,"
                  + "CLAIMS.LAUNDRY,CLAIMS.PETTIES,CLAIMS.OTHERS FROM CALLS "
                  + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                  + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                  + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                  + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                  + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
                  + "WHERE CALLS.CONTRACT_NO ='"+contractnumTxt.getText()+"' AND  STAFF.STAFFNAME='"+technician+"'"
                  + " ORDER BY SERVICE.SERVICE_DATE DESC";  
     
     }           
else if(serviceTo!=null&!contractnumTxt.getText().isEmpty()){
    reportsql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,CONTRACTS.CONTRACT_NO, "
                  + "CLIENTS.CLIENTNAME,SERVICE.LOCATION,STAFF.STAFFNAME,CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,CLAIMS.ACCOMOD,"
                  + "CLAIMS.LAUNDRY,CLAIMS.PETTIES,CLAIMS.OTHERS FROM CALLS "
                  + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                  + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                  + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                  + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                  + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
                  + "WHERE CALLS.CONTRACT_NO ='"+contractnumTxt.getText()+"' AND SERVICE.SERVICE_DATE>='"+serviceFrom+"' AND SERVICE.SERVICE_DATE<='"+serviceTo+"'"
                  + " ORDER BY SERVICE.SERVICE_DATE DESC";  
     
     }
          else if(!contractnumTxt.getText().isEmpty()){
          reportsql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,CONTRACTS.CONTRACT_NO, "
                  + "CLIENTS.CLIENTNAME,SERVICE.LOCATION,STAFF.STAFFNAME,CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,CLAIMS.ACCOMOD,"
                  + "CLAIMS.LAUNDRY,CLAIMS.PETTIES,CLAIMS.OTHERS FROM CALLS "
                  + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                  + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                  + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                  + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                  + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
                  + "WHERE CALLS.CONTRACT_NO ='"+contractnumTxt.getText()+"'"
                  + " ORDER BY SERVICE.SERVICE_DATE DESC";
     }

     else{
      reportsql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,CONTRACTS.CONTRACT_NO, "
                  + "CLIENTS.CLIENTNAME,SERVICE.LOCATION,STAFF.STAFFNAME,CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,CLAIMS.ACCOMOD,"
                  + "CLAIMS.LAUNDRY,CLAIMS.PETTIES,CLAIMS.OTHERS FROM CALLS "
                  + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                  + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
                  + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                  + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                  + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
                   + " ORDER BY SERVICE.SERVICE_DATE DESC";
     }
          Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
          PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(reportsql);
          System.out.println(" used query "+reportsql);
          PreparedStatement pst4; ResultSet rst4; double firstkm=0,firstkmrate=0,extrakmrate=0,kmclaim,kmtrav=0,totalkmclaim=0,extrakm,extrakmclaim,totalclaim; 
          ResultSet rst=pst.executeQuery();int i=0;   String  getsettings; String staffname; double grandclaims=0;
    int r=reportsTable.getRowCount();
    for(int f=0;f<r;f++){
    reportstableModel.removeRow(0);
    }
  
    while(rst.next()){
      staffname=rst.getString(7);
        kmtrav=  rst.getDouble(8);
  
   getsettings=" SELECT * FROM MILEAGESETTINGS";   
  pst4=(PreparedStatement)connectDb.prepareStatement(getsettings);
   rst4=pst4.executeQuery();
    while(rst4.next()){
        firstkm=rst4.getInt(3);
        firstkmrate=rst4.getInt(4);
        extrakmrate=rst4.getInt(5);
    }
    
    if(kmtrav>=0&&kmtrav<=firstkm){
    kmclaim=kmtrav*firstkmrate;
    totalkmclaim=kmclaim;
    }else if(kmtrav>firstkm){
       kmclaim= firstkm*firstkmrate;
        extrakm=kmtrav-firstkm;
    extrakmclaim=extrakm*extrakmrate;
    totalkmclaim=kmclaim+extrakmclaim;
    }
    totalclaim=totalkmclaim+rst.getDouble(9)+rst.getDouble(10)+rst.getDouble(11)+rst.getDouble(12)+rst.getDouble(13)+rst.getDouble(14)+rst.getDouble(15)+rst.getDouble(16);
 grandclaims=grandclaims+totalclaim;
 
 if(rst.getDate(3)==null){
 grandclaimTxt.setText("KES. "+formatter.format(grandclaims)); 
    reportstableModel.addRow(new Object[]{i+1,rst.getString(1),rst.getString(2)," ",rst.getString(5),rst.getString(6),
   rst.getString(7),"KES. "+formatter.format(totalclaim)}); 
 }
 else{
 grandclaimTxt.setText("KES. "+formatter.format(grandclaims)); 
    reportstableModel.addRow(new Object[]{i+1,rst.getString(1),rst.getString(2),sdf.format(rst.getDate(3)),rst.getString(5),rst.getString(6),
   rst.getString(7),"KES. "+formatter.format(totalclaim)}); 
 }
    if(currentcyclevalue>0){
   System.out.println(" total claim "+totalclaim+" current cycle value "+currentcyclevalue);
     double percentage= (grandclaims/currentcyclevalue)*100;
    cyclevalueagainstclaimTxt.setText(formatter.format(percentage)+" %");
    }
   
  i++;
    }       
      } catch (SQLException ex) {
          Logger.getLogger(Mileagereport.class.getName()).log(Level.SEVERE, null, ex);
      }
 
 }   

 public void printReport(){
 
 }
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
            java.util.logging.Logger.getLogger(Mileagereport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mileagereport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mileagereport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mileagereport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mileagereport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel adminnewcallsPanel;
    private javax.swing.JTextField billingTxt;
    private javax.swing.JComboBox<String> clientsCombo;
    private javax.swing.JTextField contractnumTxt;
    public javax.swing.JPanel contractreportPanel;
    private javax.swing.JTextField currentcycleTxt;
    private javax.swing.JTextField cyclevalueTxt;
    private javax.swing.JTextField cyclevalueagainstclaimTxt;
    private javax.swing.JTextField endTxt;
    private javax.swing.JTextField grandclaimTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JTextField pmcycleTxt;
    private javax.swing.JTable reportsTable;
    private javax.swing.JTextField servicefromTxt;
    private javax.swing.JTextField servicetoTxt;
    private javax.swing.JTextField startTxt;
    private javax.swing.JTextField valueTxt;
    // End of variables declaration//GEN-END:variables
}
