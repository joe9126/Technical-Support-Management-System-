
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
public class Createmileage extends javax.swing.JFrame {
    String currentowner; String usertype;  String town; String contracttable;
      String breakfastselected,     lunchselected,     dinnerselected, accomodselected;
    Home access=new Home();  String callnumber;
     DefaultComboBoxModel  descriptioncomboModel=new DefaultComboBoxModel();
DefaultComboBoxModel clientcomboModel=new DefaultComboBoxModel();
SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
   int starthour, endhour;  int startMinute, endMinute;
    String serviceDate,    status,client; Date calldate; String existcallno;
  DecimalFormat df1=new DecimalFormat("#,###.00");     String  selectedcall; String transport,descrip;
   String callstatus=""; String service_no;
   
  String[] cols3={"","No.","CALL NO","SERVICE NO","SERVICE DATE","FROM","TO","CLIENT","EQUIP. MODEL","SERIAL","LOCATION","TOWN","FAULT","ACTION","STATUS","DONE BY","STATUS","CLAIM NO"};  
 
 DefaultTableModel servicerectableModel=new DefaultTableModel(cols3,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    };   
 
    public Createmileage() {
        initComponents();
          servicerecTable.getTableHeader().setReorderingAllowed(false);
           servicerecTable.getColumnModel().getColumn(0).setPreferredWidth(1);
           servicerecTable.getColumnModel().getColumn(1).setPreferredWidth(2);
           
           // servicerecTable.getColumnModel().getColumn(5).setPreferredWidth(5);
             //servicerecTable.getColumnModel().getColumn(6).setPreferredWidth(5);
           
             TableColumn tc =servicerecTable.getColumnModel().getColumn(0);
     tc.setCellEditor(servicerecTable.getDefaultEditor(Boolean.class));
     tc.setCellRenderer(servicerecTable.getDefaultRenderer(Boolean.class));
     
    }
  public void getServiceRecords(){
      
    int g=servicerecTable.getRowCount();
    for(int f=0;f<g;f++){
servicerectableModel.removeRow(0);
    }
    
String checkcontract="SELECT CONTRACT_NO, CONT_DESCRIP FROM CONTRACTS WHERE CONTRACT_NO='";    
    
     String getcallssql = null;  
 if(!callsearchTxt.getText().equalsIgnoreCase("Search Call Number")){
  getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME,STAFF.STAFFNAME,CLAIMS.STATUS,SERVICE.TOWN,CLAIMS.CLAIMNO "
          + " FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
            + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
          + "WHERE CALLS.CALL_NO LIKE '"+callsearchTxt.getText()+"%'"
         + " ORDER BY SERVICE.SERVICE_DATE DESC";

 }
 else{
   if(usertype.equalsIgnoreCase("USER")){
 getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CALLS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CLAIMS.STATUS,SERVICE.TOWN "
            + ",CLAIMS.CLAIMNO  FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
             + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
             + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
            + "where CALLS.TECHNICIAN ='"+currentowner+"'"
           + " ORDER BY SERVICE.SERVICE_DATE DESC";
 }
 else if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
    
    if(!searchstaffnameTxt.getText().equalsIgnoreCase("Search Staff Name")){
    getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CALLS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CLAIMS.STATUS,SERVICE.TOWN "
            + ",CLAIMS.CLAIMNO  FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
             + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
             + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
            + "where STAFF.STAFFNAME LIKE '"+searchstaffnameTxt.getText()+"%'"
           + " ORDER BY SERVICE.SERVICE_DATE DESC";
    
    } 
    else{ 
     getcallssql="SELECT CALLS.CALL_NO,SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO, SERVICE.EQUIP_MODEL,"
          + "SERVICE.SERIAL,SERVICE.LOCATION,CALLS.TO_DO,SERVICE.ACTION,SERVICE.STATUS,CLIENTS.CLIENTNAME, STAFF.STAFFNAME,CLAIMS.STATUS,SERVICE.TOWN "
             + ",CLAIMS.CLAIMNO FROM CALLS "
                    + "LEFT JOIN SERVICE ON CALLS.CALL_NO = SERVICE.CALL_NO "
                    + " LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO "
             + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO = CLIENTS.CLIENT_NO "
            + " LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
             + "LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO "
           + " ORDER BY SERVICE.SERVICE_DATE DESC";
    }
 }
 }  
    try {
 
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,   access.username,  access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcallssql);
            ResultSet rst=pst.executeQuery(); int i=0; String client=null;
             while(rst.next()){
 java.sql.Date date1=rst.getDate(3);
 if(date1==null){

 }else{
 if(rst.getString(13)!=null){
 servicerectableModel.addRow(new Object[]{false,i+1,rst.getString(1),rst.getString(2),sdf.format(rst.getDate(3)),rst.getString(4),rst.getString(5),rst.getString( 13)
  ,rst.getString(7),rst.getString(8),rst.getString(9),rst.getString(16),rst.getString(10),rst.getString(11),rst.getString(12),rst.getString(14),rst.getString(15),rst.getString(17)}); 
client=rst.getString(13);
 }    
 
 System.out.println("Service client: "+rst.getString(13));
  System.out.println("Service CONTRACT: "+rst.getString(6));
 
 if(rst.getString(13)==null){
 String getclient="SELECT SUPPLY_REQUESTS.CSRNO, CLIENTS.CLIENTNAME FROM SUPPLY_REQUESTS LEFT JOIN CLIENTS "
         + "ON SUPPLY_REQUESTS.CLIENTNO=CLIENTS.CLIENT_NO WHERE SUPPLY_REQUESTS.CSRNO='"+rst.getString(6)+"'";
  PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(getclient);
   ResultSet rst2=pst2.executeQuery();
   
  while(rst2.next()){
    servicerectableModel.addRow(new Object[]{false,i+1,rst.getString(1),rst.getString(2),sdf.format(rst.getDate(3)),rst.getString(4),rst.getString(5),rst2.getString( 2)
  ,rst.getString(7),rst.getString(8),rst.getString(9),rst.getString(16),rst.getString(10),rst.getString(11),rst.getString(12),rst.getString(14),rst.getString(15),rst.getString(17)}); 
  
   System.out.println("SERVICE CSR CLIENT: "+rst2.getString(2));
   }
  
 }

 }
 i++;         }
             if(i<1){
                 

             }
        } catch (SQLException ex) {
            Logger.getLogger(Userprofile.class.getName()).log(Level.SEVERE, null, ex);
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

        mileagecreationPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        transportPanel = new javax.swing.JPanel();
        nofarePanel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        privatePanel = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        kmTxt = new javax.swing.JTextField();
        publicPanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        psvfareTxt = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        bfastTxt = new javax.swing.JTextField();
        bfastTxt2 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        accomTxt = new javax.swing.JTextField();
        accomTxt2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        laundTxt = new javax.swing.JTextField();
        laundTxt2 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        pettiesTxt2 = new javax.swing.JTextField();
        pettiesTxt = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        othersTxt = new javax.swing.JTextField();
        othersTxt2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        servicenoTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lunchcombo = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        lunchTxt = new javax.swing.JTextField();
        lunchTxt2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dinnercomboBox = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        dinnerTxt = new javax.swing.JTextField();
        dinnerTxt2 = new javax.swing.JTextField();
        servicerecordPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        servicerecTable = new javax.swing.JTable();
        callsearchTxt = new javax.swing.JTextField();
        unlockclaimPanel = new javax.swing.JPanel();
        unlockholderPanel = new javax.swing.JPanel();
        searchstaffnameTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mileagecreationPanel.setBackground(new java.awt.Color(153, 0, 153));
        mileagecreationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CLAIM CREATION", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MILEAGE CLAIM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(204, 0, 204))); // NOI18N
        jPanel5.setToolTipText("SELECT SERVICE NUMBER FROM LIST BELOW");

        jLabel18.setText("TRANSPORT:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMPANY PROVIDED", "PRIVATE", "PUBLIC" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        transportPanel.setBackground(new java.awt.Color(255, 255, 255));
        transportPanel.setLayout(new java.awt.CardLayout());

        nofarePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setText("TRANS. CLAIM:");

        jTextField3.setEditable(false);
        jTextField3.setText("0");
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout nofarePanelLayout = new javax.swing.GroupLayout(nofarePanel);
        nofarePanel.setLayout(nofarePanelLayout);
        nofarePanelLayout.setHorizontalGroup(
            nofarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nofarePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );
        nofarePanelLayout.setVerticalGroup(
            nofarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nofarePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nofarePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transportPanel.add(nofarePanel, "card4");

        privatePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setText("KILOMETERS:");

        kmTxt.setText("0");

        javax.swing.GroupLayout privatePanelLayout = new javax.swing.GroupLayout(privatePanel);
        privatePanel.setLayout(privatePanelLayout);
        privatePanelLayout.setHorizontalGroup(
            privatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(privatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kmTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        privatePanelLayout.setVerticalGroup(
            privatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(privatePanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(privatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(kmTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transportPanel.add(privatePanel, "card2");

        publicPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setText("PSV FARE:");

        psvfareTxt.setText("0");

        javax.swing.GroupLayout publicPanelLayout = new javax.swing.GroupLayout(publicPanel);
        publicPanel.setLayout(publicPanelLayout);
        publicPanelLayout.setHorizontalGroup(
            publicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(publicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(psvfareTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        publicPanelLayout.setVerticalGroup(
            publicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(publicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(publicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(psvfareTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transportPanel.add(publicPanel, "card3");

        jLabel20.setText("BREAKFAST");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Receipted", "No Receipt" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel22.setText("KES:");

        bfastTxt.setText("0");
        bfastTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bfastTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                bfastTxtKeyTyped(evt);
            }
        });

        bfastTxt2.setEditable(false);
        bfastTxt2.setText("0.00");

        jLabel24.setText("ACCOMOD:");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Receipted", "No Receipt" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel25.setText("KES:");

        accomTxt.setText("0");
        accomTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                accomTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                accomTxtKeyTyped(evt);
            }
        });

        accomTxt2.setEditable(false);
        accomTxt2.setText("0.00");

        jLabel26.setText("Laundry KES:");

        laundTxt.setText("0");
        laundTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                laundTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                laundTxtKeyTyped(evt);
            }
        });

        laundTxt2.setEditable(false);
        laundTxt2.setText("0.00");

        jLabel28.setText("PETTIES KES:");

        pettiesTxt2.setEditable(false);
        pettiesTxt2.setText("0.00");

        pettiesTxt.setText("0");
        pettiesTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pettiesTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pettiesTxtKeyTyped(evt);
            }
        });

        jLabel29.setText("OTHERS KES:");

        othersTxt.setText("0");
        othersTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                othersTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                othersTxtKeyTyped(evt);
            }
        });

        othersTxt2.setEditable(false);
        othersTxt2.setText("0.00");

        jButton3.setText("CREATE ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("SERVICE NO:");

        servicenoTxt.setEditable(false);

        jLabel2.setText("LUNCH:");

        lunchcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Receipted", "No Receipt" }));
        lunchcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunchcomboActionPerformed(evt);
            }
        });

        jLabel30.setText("KES:");

        lunchTxt.setText("0");
        lunchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lunchTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lunchTxtKeyTyped(evt);
            }
        });

        lunchTxt2.setEditable(false);
        lunchTxt2.setText("0.00");

        jLabel3.setText("DINNER:");

        dinnercomboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Receipted", "No Receipt" }));
        dinnercomboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dinnercomboBoxActionPerformed(evt);
            }
        });

        jLabel31.setText("KES:");

        dinnerTxt.setText("0");
        dinnerTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dinnerTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dinnerTxtKeyTyped(evt);
            }
        });

        dinnerTxt2.setEditable(false);
        dinnerTxt2.setText("0.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transportPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(servicenoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel22)
                                .addGap(52, 52, 52)
                                .addComponent(bfastTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bfastTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(dinnerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(dinnerTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dinnercomboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(lunchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lunchTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lunchcombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(76, 76, 76)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(33, 33, 33)
                        .addComponent(pettiesTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pettiesTxt2))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(laundTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(laundTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(accomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(accomTxt2))
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(53, 53, 53)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(41, 41, 41)
                        .addComponent(othersTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(othersTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(lunchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lunchTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(bfastTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bfastTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel29)
                                        .addComponent(othersTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(othersTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel24)
                                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(accomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(accomTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26)
                                    .addComponent(laundTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(laundTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28)
                                    .addComponent(pettiesTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pettiesTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel1)
                                            .addComponent(servicenoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2)
                                            .addComponent(lunchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(97, 97, 97))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel18)
                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(transportPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(dinnercomboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel31)
                                            .addComponent(dinnerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dinnerTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 25, Short.MAX_VALUE))))
        );

        servicerecordPanel.setBackground(new java.awt.Color(255, 255, 255));
        servicerecordPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Service Records", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 14), new java.awt.Color(204, 0, 204))); // NOI18N

        servicerecTable.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        servicerecTable.setModel(servicerectableModel);
        servicerecTable.setFillsViewportHeight(true);
        servicerecTable.setGridColor(new java.awt.Color(153, 0, 153));
        servicerecTable.setRowHeight(25);
        servicerecTable.setRowMargin(2);
        servicerecTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        servicerecTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                servicerecTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(servicerecTable);

        callsearchTxt.setText("Search Call Number");
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

        unlockclaimPanel.setBackground(new java.awt.Color(255, 255, 255));
        unlockclaimPanel.setLayout(new java.awt.CardLayout());

        unlockholderPanel.setBackground(new java.awt.Color(255, 255, 255));

        searchstaffnameTxt.setText("Search Staff Name");
        searchstaffnameTxt.setToolTipText("ENTER TECHNICIAN NAME");
        searchstaffnameTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchstaffnameTxtMouseClicked(evt);
            }
        });
        searchstaffnameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchstaffnameTxtKeyReleased(evt);
            }
        });

        jButton1.setText("UNLOCK");
        jButton1.setToolTipText("SELECT CLAIMED MILEAGES TO UNLOCK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout unlockholderPanelLayout = new javax.swing.GroupLayout(unlockholderPanel);
        unlockholderPanel.setLayout(unlockholderPanelLayout);
        unlockholderPanelLayout.setHorizontalGroup(
            unlockholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unlockholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchstaffnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        unlockholderPanelLayout.setVerticalGroup(
            unlockholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unlockholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(unlockholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchstaffnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        unlockclaimPanel.add(unlockholderPanel, "card2");

        javax.swing.GroupLayout servicerecordPanelLayout = new javax.swing.GroupLayout(servicerecordPanel);
        servicerecordPanel.setLayout(servicerecordPanelLayout);
        servicerecordPanelLayout.setHorizontalGroup(
            servicerecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicerecordPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(servicerecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
                    .addGroup(servicerecordPanelLayout.createSequentialGroup()
                        .addComponent(unlockclaimPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(callsearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        servicerecordPanelLayout.setVerticalGroup(
            servicerecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, servicerecordPanelLayout.createSequentialGroup()
                .addGroup(servicerecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(callsearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unlockclaimPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(servicerecordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(servicerecordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout mileagecreationPanelLayout = new javax.swing.GroupLayout(mileagecreationPanel);
        mileagecreationPanel.setLayout(mileagecreationPanelLayout);
        mileagecreationPanelLayout.setHorizontalGroup(
            mileagecreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        mileagecreationPanelLayout.setVerticalGroup(
            mileagecreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mileagecreationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mileagecreationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        transport=""+jComboBox1.getSelectedItem();
        if(transport.equalsIgnoreCase("private")){
            transportPanel.removeAll();
            transportPanel.repaint();
            transportPanel.revalidate();
            transportPanel.add(privatePanel);
        }else  if(transport.equalsIgnoreCase("PUBLIC")){
            transportPanel.removeAll();
            transportPanel.repaint();
            transportPanel.revalidate();
            transportPanel.add(publicPanel);
 
        }else{
            transportPanel.removeAll();
            transportPanel.repaint();
            transportPanel.revalidate();
            transportPanel.add(nofarePanel);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void bfastTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bfastTxtKeyReleased
        if(bfastTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(bfastTxt.getText());
            if(value>0){
                bfastTxt2.setText( df1.format(value));
            }
        }
    }//GEN-LAST:event_bfastTxtKeyReleased

    private void bfastTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bfastTxtKeyTyped
        char c=evt.getKeyChar();
        if((c>='0')&&(c<='9')){
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_bfastTxtKeyTyped

    private void accomTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accomTxtKeyReleased
        if(accomTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(accomTxt.getText());
            if(value>0){
                accomTxt2.setText( df1.format(value));
            }
        }
    }//GEN-LAST:event_accomTxtKeyReleased

    private void accomTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accomTxtKeyTyped
        char c=evt.getKeyChar();
        if(!((c>='0')&&(c<='9') )){
            getToolkit().beep();
            evt.consume();}
    }//GEN-LAST:event_accomTxtKeyTyped

    private void laundTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_laundTxtKeyReleased
        if(laundTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(laundTxt.getText());
            if(value>0){
                laundTxt2.setText( df1.format(value));
            }
        }
    }//GEN-LAST:event_laundTxtKeyReleased

    private void laundTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_laundTxtKeyTyped
        char c=evt.getKeyChar();
        if(!((c>='0')&&(c<='9') )){
            getToolkit().beep();
            evt.consume();}
    }//GEN-LAST:event_laundTxtKeyTyped

    private void pettiesTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pettiesTxtKeyReleased
        if(pettiesTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(pettiesTxt.getText());
            if(value>0){
                pettiesTxt2.setText( df1.format(value));
            }
        }
    }//GEN-LAST:event_pettiesTxtKeyReleased

    private void pettiesTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pettiesTxtKeyTyped
        char c=evt.getKeyChar();
        if(!((c>='0')&&(c<='9')) ){
            getToolkit().beep();
            evt.consume();}
    }//GEN-LAST:event_pettiesTxtKeyTyped

    private void othersTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_othersTxtKeyReleased
        if(othersTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(othersTxt.getText());
            if(value>0){
                othersTxt2.setText( df1.format(value));
            }
        }
    }//GEN-LAST:event_othersTxtKeyReleased

    private void othersTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_othersTxtKeyTyped
        char c=evt.getKeyChar();
        if(!((c>='0')&&(c<='9') )){
            getToolkit().beep();
            evt.consume();}
    }//GEN-LAST:event_othersTxtKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    if(servicenoTxt.getText().isEmpty()){
 JOptionPane.showMessageDialog(null, "Please select Service from the Service Record List below !","Service Number Required",JOptionPane.WARNING_MESSAGE);
        } 
      
        else {
     
       try {
        Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
//                        java.sql.Date sqltoday=new java.sql.Date(calldate.getTime());
    String checkclaimsql="SELECT SERVICE_NO,STATUS, DATE_CLAIMED FROM CLAIMS WHERE SERVICE_NO='"+servicenoTxt.getText()+"'";                     
     PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(checkclaimsql);  
     ResultSet rst=pst2.executeQuery(); String dateclaimed=""; int u=0;String claimstatus="";
     while(rst.next()){
  claimstatus= rst.getString(2);  u++;
     }
     System.out.println(" SERVICE CLAIM EXISTENCE "+u);
    
         
  if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
   if(claimstatus.equalsIgnoreCase("CLAIMED"))  {  
   JOptionPane.showMessageDialog(null," Claim for Service Number "+servicenoTxt.getText()+" already created! Unlock to make changes","Claim Exists",JOptionPane.ERROR_MESSAGE);
   }
    else{
    createClaim();     
     }
  }
  else{
  if( claimstatus.equalsIgnoreCase("UNCLAIMED")||claimstatus.equalsIgnoreCase("NOT CREATED")){
   createClaim();     
  } else{
 JOptionPane.showMessageDialog(null," Service Number "+servicenoTxt.getText()+" already claimed! Request Admin to unlock","Claim Printed",JOptionPane.ERROR_MESSAGE);
   }    
  }       
     
    
       } catch (SQLException ex) {
                        //JOptionPane.showMessageDialog(null, "Call number must be unique!","Call Number Exists",JOptionPane.WARNING_MESSAGE);
                        Logger.getLogger(Createmileage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                

            
        }
    }//GEN-LAST:event_jButton3ActionPerformed
public void createClaim(){

   if(transport==null){
            JOptionPane.showMessageDialog(null, "Please select Transport !"," Transport Required",JOptionPane.WARNING_MESSAGE);
        }else    if(transport.equalsIgnoreCase("PRIVATE")&&kmTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter kilometers travelled !"," Kilometers Required",JOptionPane.WARNING_MESSAGE);
        }else  if(transport.equalsIgnoreCase("PUBLIC")&&psvfareTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter PSV Fare utilised !"," PSV Fare Required",JOptionPane.WARNING_MESSAGE);
        }else{
       try {
           int bfrecept=0, bfnorecept=0, lunchrecept=0, lunchnorecept=0, dinnerrecept=0, dinnernorecept=0, accomodrecept=0,accomodnorecept=0,laundry=0,petties=0,others=0;
           int firstkm,firstkmrate,extrakmrate;
             int row=servicerecTable.getSelectedRow();
           town=String.valueOf(servicerectableModel.getValueAt(row, 11));
           String doneby=String.valueOf(servicerectableModel.getValueAt(row, 15));
           
      //   JOptionPane.showMessageDialog(null, "Town "+town);
           Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            String getsettings;
     if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
  getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL=(SELECT STAFFLEVEL FROM STAFF WHERE STAFFNAME='"+doneby+"') "
                   + "AND TOWN='"+town+"'";
     }else{
       getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL=(SELECT STAFFLEVEL FROM STAFF WHERE STAFFNO='"+currentowner+"') "
                   + "AND TOWN='"+town+"'";
     }    
     
           PreparedStatement pst4=(PreparedStatement)connectDb.prepareStatement(getsettings);
           ResultSet rst4=pst4.executeQuery(); int k=0;
           while(rst4.next()){
               firstkm=rst4.getInt(3);
               firstkmrate=rst4.getInt(4);
               extrakmrate=rst4.getInt(5);
               accomodrecept= rst4.getInt(6);
               accomodnorecept= rst4.getInt( 7 );
               bfrecept= rst4.getInt(8);
               bfnorecept= rst4.getInt(9);
               lunchrecept=   rst4.getInt( 10);
               lunchnorecept=  rst4.getInt(11);
               dinnerrecept= rst4.getInt(12);
               dinnernorecept=  rst4.getInt(13);
               laundry=  rst4.getInt(14);
               petties=  rst4.getInt(15 );
               others=rst4.getInt(16);
               k++;
           }     
  if(k<1){
     if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
         
  getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL=(SELECT STAFFLEVEL FROM STAFF WHERE STAFFNAME='"+doneby+"') "
                   + "AND TOWN='"+town+"'";
     }else{
       getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL=(SELECT STAFFLEVEL FROM STAFF WHERE STAFFNO='"+currentowner+"') "
                   + "AND TOWN='"+town+"'";
     } 
     pst4=(PreparedStatement)connectDb.prepareStatement(getsettings);
      rst4=pst4.executeQuery();  int t=0;
           while(rst4.next()){
               firstkm=rst4.getInt(3);
               firstkmrate=rst4.getInt(4);
               extrakmrate=rst4.getInt(5);
               accomodrecept= rst4.getInt(6);
               accomodnorecept= rst4.getInt( 7 );
               bfrecept= rst4.getInt(8);
               bfnorecept= rst4.getInt(9);
               lunchrecept=   rst4.getInt( 10);
               lunchnorecept=  rst4.getInt(11);
               dinnerrecept= rst4.getInt(12);
               dinnernorecept=  rst4.getInt(13);
               laundry=  rst4.getInt(14);
               petties=  rst4.getInt(15 );
               others=rst4.getInt(16);
               t++;
           }
   if(t<1){
       
       getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL=(SELECT STAFFLEVEL FROM STAFF WHERE STAFFNO='"+currentowner+"') "
                   + "AND TOWN='OTHERS'";
    pst4=(PreparedStatement)connectDb.prepareStatement(getsettings);
      rst4=pst4.executeQuery();  
           while(rst4.next()){
               firstkm=rst4.getInt(3);
               firstkmrate=rst4.getInt(4);
               extrakmrate=rst4.getInt(5);
               accomodrecept= rst4.getInt(6);
               accomodnorecept= rst4.getInt( 7 );
               bfrecept= rst4.getInt(8);
               bfnorecept= rst4.getInt(9);
               lunchrecept=   rst4.getInt( 10);
               lunchnorecept=  rst4.getInt(11);
               dinnerrecept= rst4.getInt(12);
               dinnernorecept=  rst4.getInt(13);
               laundry=  rst4.getInt(14);
               petties=  rst4.getInt(15 );
               others=rst4.getInt(16);
               t++;
           }
   
   }
  
  }         
           if(Integer.parseInt(bfastTxt.getText())>0){
 if(breakfastselected==null){
     JOptionPane.showMessageDialog(null, "Please specify if Breakfast is receipted or not! ","Breakfast Receipt Status Required",JOptionPane.WARNING_MESSAGE);
   }    
    else   if(breakfastselected.equalsIgnoreCase("No receipt")){
                   if(Integer.parseInt(bfastTxt.getText())>bfnorecept){
                       JOptionPane.showMessageDialog(null, " Breakfast without receipt is limited to KES. "+df1.format(bfnorecept)+"!","BreakFast Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       bfastTxt.setText("0"); bfastTxt2.setText("KES. 0.00");
                   }}
               
               else{
                   if(Integer.parseInt(bfastTxt.getText())>bfrecept){
                       JOptionPane.showMessageDialog(null, " Breakfast with receipt is limited to KES. "+df1.format(bfrecept)+"!","BreakFast Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       bfastTxt.setText("0"); bfastTxt2.setText("KES. 0.00");
                   }
               }
           }   if(Integer.parseInt(lunchTxt.getText())>0) {
           if(lunchselected==null){
     JOptionPane.showMessageDialog(null, "Please specify if Lunch is receipted or not! ","Lunch Receipt Status Required",JOptionPane.WARNING_MESSAGE);
           }    
              else if(lunchselected.equalsIgnoreCase("No receipt")){
                   if(Integer.parseInt(lunchTxt.getText())>lunchnorecept){
                       JOptionPane.showMessageDialog(null, " Lunch without receipt is limited to KES. "+df1.format(lunchnorecept)+"!","Lunch Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       lunchTxt.setText("0"); lunchTxt2.setText("KES. 0.00");
                   }
               }else{
                   if(Integer.parseInt(lunchTxt.getText())>lunchrecept){
                       JOptionPane.showMessageDialog(null, " Lunch with receipt is limited to KES. "+df1.format(bfrecept)+"!","Lunch Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       lunchTxt.setText("0"); lunchTxt2.setText("KES. 0.00");
                   }
               }
           } 
           
           if(Integer.parseInt(dinnerTxt.getText())>0){
           if(dinnerselected==null){
     JOptionPane.showMessageDialog(null, "Please specify if Dinner is receipted or not! ","Dinner Receipt Status Required",JOptionPane.WARNING_MESSAGE);
           }    
              else        
               if(dinnerselected.equalsIgnoreCase("No receipt")){
                   if(Integer.parseInt(dinnerTxt.getText())>dinnernorecept){
                       JOptionPane.showMessageDialog(null, " Dinner without receipt is limited to KES. "+df1.format(dinnernorecept)+"!","Dinner Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       dinnerTxt.setText("0"); dinnerTxt2.setText("KES. 0.00");
                   }
               }else{
                   if(Integer.parseInt(dinnerTxt.getText())>dinnerrecept){
                       JOptionPane.showMessageDialog(null, " Dinner with receipt is limited to KES. "+df1.format(dinnerrecept)+"!","DInner Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       dinnerTxt.setText("0"); dinnerTxt2.setText("KES. 0.00");
                   }
               }
           } 
           
   if(Integer.parseInt(accomTxt.getText())>0){
 if(accomodselected==null){
     JOptionPane.showMessageDialog(null, "Please specify if Accomodation is receipted or not! ","Accomodation Receipt Status Required",JOptionPane.WARNING_MESSAGE);
           }    
    else            
       if(accomodselected.equalsIgnoreCase("No receipt")){
                   if(Integer.parseInt(accomTxt.getText())>accomodnorecept){
                       JOptionPane.showMessageDialog(null, " Accomodation without receipt is limited to KES. "+df1.format(accomodnorecept)+"!","Accomodation Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       accomTxt.setText("0"); accomTxt2.setText("KES. 0.00");
                   }
               }else{
                   if(Integer.parseInt(accomTxt.getText())>accomodrecept){
                       JOptionPane.showMessageDialog(null, " Dinner with receipt is limited to KES. "+df1.format(accomodrecept)+"!","Accomodation Limit Exceeded",JOptionPane.WARNING_MESSAGE);
                       accomTxt.setText("0"); accomTxt2.setText("KES. 0.00");
                   }
               }
           }  if(Integer.parseInt(laundTxt.getText())>laundry){
               JOptionPane.showMessageDialog(null, " Laundry   is limited to KES. "+df1.format(laundry)+" per week!","Laundry Limit Exceeded",JOptionPane.WARNING_MESSAGE);
               laundTxt.setText("0"); laundTxt2.setText("KES. 0.00");
           }      if(Integer.parseInt(pettiesTxt.getText())>petties){
               JOptionPane.showMessageDialog(null, " Petties are limited to KES. "+df1.format(petties)+" !","Petties Limit Exceeded",JOptionPane.WARNING_MESSAGE);
               pettiesTxt.setText("0"); pettiesTxt2.setText("KES. 0.00");
           }      if(Integer.parseInt(othersTxt.getText())>others){
               JOptionPane.showMessageDialog(null, " Others Expenditures are limited to KES. "+df1.format(others)+" !","Others Expenditures Limit Exceeded",JOptionPane.WARNING_MESSAGE);
               othersTxt.setText("0"); othersTxt2.setText("KES. 0.00");
           }     
     
  String checkclaimsql="SELECT SERVICE_NO,STATUS,DATE_CLAIMED FROM CLAIMS WHERE SERVICE_NO='"+servicenoTxt.getText()+"'";                     
     PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(checkclaimsql);  
     ResultSet rst=pst2.executeQuery(); String dateclaimed=""; int u=0;String claimstatus="";
     while(rst.next()){
  claimstatus= rst.getString(2);  u++;
     }         
           if(u>0){
               int choice=JOptionPane.showConfirmDialog(null, "Do you want to create new mileage claim?","Confirm",JOptionPane.YES_NO_OPTION);
               if(choice==JOptionPane.YES_OPTION){
       if(claimstatus.equalsIgnoreCase("CLAIMED")||claimstatus.equalsIgnoreCase("UNCLAIMED")){
        String updateclaimsql="UPDATE CLAIMS SET KM=?,PSVFARE=?,BFAST=?,LUNCH=?,DINNER=?,ACCOMOD=?"
                           + ",LAUNDRY=?,PETTIES=?,OTHERS=? WHERE SERVICE_NO='"+service_no+"'";
        PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(updateclaimsql);
                   // pst.setString(1,callnumber);
                   //  pst.setString(2,service_no);
                   pst.setDouble(1,Double.parseDouble(kmTxt.getText()));
                   pst.setInt(2, Integer.parseInt(psvfareTxt.getText()));
                   pst.setInt(3, Integer.parseInt(bfastTxt.getText()));
                   pst.setInt(4, Integer.parseInt(lunchTxt.getText())); pst.setInt(5, Integer.parseInt(dinnerTxt.getText()));
                   pst.setInt(6, Integer.parseInt(accomTxt.getText()));   pst.setInt(7, Integer.parseInt(laundTxt.getText()));
                   pst.setInt(8, Integer.parseInt(pettiesTxt.getText()));   pst.setInt(9, Integer.parseInt(othersTxt.getText()));
                     int p=pst.executeUpdate();
                              if(p>0){
           JOptionPane.showMessageDialog(null, "Claim created successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
                       servicenoTxt.setText(null);       getServiceRecords();
                   }
       }
       else{
                   String updateclaimsql="UPDATE CLAIMS SET KM=?,PSVFARE=?,BFAST=?,LUNCH=?,DINNER=?,ACCOMOD=?"
                           + ",LAUNDRY=?,PETTIES=?,OTHERS=?,STATUS=?,DATE_CLAIMED=?,CLAIMNO='NA' WHERE SERVICE_NO='"+service_no+"'";
                
  PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(updateclaimsql);
                   // pst.setString(1,callnumber);
                   //  pst.setString(2,service_no);
                   pst.setDouble(1,Double.parseDouble(kmTxt.getText()));
                   pst.setInt(2, Integer.parseInt(psvfareTxt.getText()));
                   pst.setInt(3, Integer.parseInt(bfastTxt.getText()));
                   pst.setInt(4, Integer.parseInt(lunchTxt.getText())); pst.setInt(5, Integer.parseInt(dinnerTxt.getText()));
                   pst.setInt(6, Integer.parseInt(accomTxt.getText()));   pst.setInt(7, Integer.parseInt(laundTxt.getText()));
                   pst.setInt(8, Integer.parseInt(pettiesTxt.getText()));   pst.setInt(9, Integer.parseInt(othersTxt.getText()));
                   pst.setString(10, "UNCLAIMED"); pst.setString(11, "N/A");
                   int p=pst.executeUpdate();
                   if(p>0){
           JOptionPane.showMessageDialog(null, "Claim created successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
                       servicenoTxt.setText(null);       getServiceRecords();
                   }
               }
               }
           }
           else{
               int choice=JOptionPane.showConfirmDialog(null, "Do you want to create new mileage claim?","Confirm",JOptionPane.YES_NO_OPTION);
               if(choice==JOptionPane.YES_OPTION){
                   String updateclaimsql="INSERT INTO CLAIMS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                   //    String newcallsql="INSERT INTO CLAIMS VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                   PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(updateclaimsql);
                   pst.setString(1,callnumber);
                   pst.setString(2,service_no);
                   pst.setDouble(3,Double.parseDouble(kmTxt.getText()));
                   pst.setInt(4, Integer.parseInt(psvfareTxt.getText()));
                   pst.setInt(5, Integer.parseInt(bfastTxt.getText()));
                   pst.setInt(6, Integer.parseInt(lunchTxt.getText()));
                   pst.setInt(7, Integer.parseInt(dinnerTxt.getText()));
                   pst.setInt(8, Integer.parseInt(accomTxt.getText()));
                   pst.setInt(9, Integer.parseInt(laundTxt.getText()));
                   pst.setInt(10, Integer.parseInt(pettiesTxt.getText()));
                   pst.setInt(11, Integer.parseInt(othersTxt.getText()));
                   pst.setString(12, "UNCLAIMED");
                   pst.setString(13, "N/A");
                    pst.setString(14, "N/A");
                   int p=pst.executeUpdate();
                   if(p>0){
                       JOptionPane.showMessageDialog(null, "Claim created successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
                       servicenoTxt.setText(null);       getServiceRecords();
                   }
               }
               
               
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(Createmileage.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
     

}
    private void callsearchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_callsearchTxtKeyReleased
      getServiceRecords(); 
    }//GEN-LAST:event_callsearchTxtKeyReleased

    private void callsearchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_callsearchTxtMouseClicked
searchstaffnameTxt.setText("Search Staff Name");       callsearchTxt.setText(null); 
    }//GEN-LAST:event_callsearchTxtMouseClicked

    private void servicerecTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servicerecTableMouseClicked
int clickedcolumn=servicerecTable.getSelectedColumn();
int rows=servicerecTable.getSelectedRow();
String selectedservice=String.valueOf(servicerecTable.getValueAt(rows, 3));
if(clickedcolumn==0)
{}
else{
        try {
        Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
   int row=servicerecTable.getSelectedRow();
  service_no=String.valueOf(servicerectableModel.getValueAt(row, 3));
  
 town=String.valueOf(servicerectableModel.getValueAt(row, 11));
 
  String selectedtech=String.valueOf(servicerectableModel.getValueAt(row, 15));
   if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
  String getstaffno="SELECT STAFFNO FROM STAFF WHERE STAFFNAME='"+selectedtech+"'";
   PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(getstaffno);  
    ResultSet rst=pst2.executeQuery(); String dateclaimed=""; int u=0;String claimstatus="";
     while(rst.next()){
  currentowner=rst.getString(1);
         u++;
     }
   }
  System.out.println("Staff no "+currentowner);
  
   System.out.println("SERVICE NO "+selectedservice);
   
     callnumber=String.valueOf(servicerectableModel.getValueAt(row, 2)); String status="";
    String checkclaimsql="SELECT SERVICE_NO,STATUS, DATE_CLAIMED FROM CLAIMS WHERE SERVICE_NO='"+selectedservice+"'";                     
     PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(checkclaimsql);  
     ResultSet rst=pst2.executeQuery(); String dateclaimed=""; int u=0;String claimstatus="";
     while(rst.next()){
  status=rst.getString(2);
         u++;
     }
    
       System.out.println("CLAIM STATUS "+status);
    if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
  if(status.equalsIgnoreCase("UNCLAIMED")||status.equalsIgnoreCase("CLAIMED")){
          servicenoTxt.setText(null);         
  int choice= JOptionPane.showConfirmDialog(null," Claim for this Service already created. Do you want edit? ","Claim Exists",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_OPTION){
  servicenoTxt.setText(service_no); 
  getClaim();
  }
     }else{
  servicenoTxt.setText(service_no);  getClaim();
 //JOptionPane.showMessageDialog(null, "This service has been claimed. Unlock to edit! ","Service Claimed",JOptionPane.INFORMATION_MESSAGE);
 
  }
    }  
   else{ 
       if (status.equalsIgnoreCase("CLAIMED")){
   servicenoTxt.setText(null);    
   JOptionPane.showMessageDialog(null, "You are not authorised to edit this claim. Request access from administrator ","Access Denied",JOptionPane.ERROR_MESSAGE);
    }else if(status.equalsIgnoreCase("UNCLAIMED")){
   int choice= JOptionPane.showConfirmDialog(null," Claim for this Service already created. Do you want edit? ","Claim Exists",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_OPTION){
  servicenoTxt.setText(service_no);
  getClaim();
  }
       }else{
    servicenoTxt.setText(service_no);
    getClaim();
    }
     }
 
    }   catch (SQLException ex) {
            Logger.getLogger(Createmileage.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    }//GEN-LAST:event_servicerecTableMouseClicked
public void getClaim(){
        try {
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            String getclaimsql="SELECT * FROM CLAIMS WHERE SERVICE_NO='"+servicenoTxt.getText()+"'";
            PreparedStatement pst4=(PreparedStatement)connectDb.prepareStatement(getclaimsql);
            ResultSet rst4=pst4.executeQuery();
            while(rst4.next()){
              //  servicenoTxt.setText(service_no);
               System.out.println("KM travelled "+rst4.getDouble(3));
             if(rst4.getDouble(3)>0.0)
                {
                    transportPanel.removeAll();
                    transportPanel.repaint();
                    transportPanel.revalidate();
                    transportPanel.add(privatePanel);
                    kmTxt.setText(String.valueOf(rst4.getDouble(3)));
                }
                
                System.out.println("psv fare "+rst4.getInt(4));   
                
             if(rst4.getInt(4)>0)
                {
                    transportPanel.removeAll();
                    transportPanel.repaint();
                    transportPanel.revalidate();
                    transportPanel.add(publicPanel);
                    psvfareTxt.setText(String.valueOf(rst4.getInt(4)));
                }
                
                if(rst4.getInt(4)==0&&rst4.getDouble(3)==0){
                    transportPanel.removeAll();
                    transportPanel.repaint();
                    transportPanel.revalidate();
                    transportPanel.add(nofarePanel);
                    jTextField3.setText("0");
                }
                bfastTxt.setText(String.valueOf(rst4.getInt(5)));
                lunchTxt.setText(String.valueOf(rst4.getInt(6)));
                dinnerTxt.setText(String.valueOf(rst4.getInt(7)));
                accomTxt.setText(String.valueOf(rst4.getInt(8)));
                laundTxt.setText(String.valueOf(rst4.getInt(9)));
                pettiesTxt.setText(String.valueOf(rst4.getInt(10)));
                othersTxt.setText(String.valueOf(rst4.getInt(11)));
            } } catch (SQLException ex) {
            Logger.getLogger(Createmileage.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        char c=evt.getKeyChar();
        if((c>='0')&&(c<='9')){
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void lunchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lunchTxtKeyReleased
         if(lunchTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(lunchTxt.getText());
            if(value>0){
                lunchTxt2.setText( df1.format(value));
            }
        }
    }//GEN-LAST:event_lunchTxtKeyReleased

    private void lunchTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lunchTxtKeyTyped
        char c=evt.getKeyChar();
        if((c>='0')&&(c<='9')){
           
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_lunchTxtKeyTyped

    private void dinnerTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dinnerTxtKeyReleased
       if(dinnerTxt.getText().trim().length()!=0){
            int value=Integer.parseInt(dinnerTxt.getText());
            if(value>0){
                dinnerTxt2.setText( df1.format(value));
            }
        }
    }//GEN-LAST:event_dinnerTxtKeyReleased

    private void dinnerTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dinnerTxtKeyTyped
         char c=evt.getKeyChar();
        if((c>='0')&&(c<='9')){
           
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_dinnerTxtKeyTyped

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
    breakfastselected=""+jComboBox4.getSelectedItem();
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void lunchcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunchcomboActionPerformed
     lunchselected=""+lunchcombo.getSelectedItem();
    }//GEN-LAST:event_lunchcomboActionPerformed

    private void dinnercomboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dinnercomboBoxActionPerformed
      dinnerselected=""+dinnercomboBox.getSelectedItem();
    }//GEN-LAST:event_dinnercomboBoxActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
    accomodselected=""+jComboBox5.getSelectedItem();
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void searchstaffnameTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchstaffnameTxtMouseClicked
callsearchTxt.setText("Search Call Number");    searchstaffnameTxt.setText(null); 
    }//GEN-LAST:event_searchstaffnameTxtMouseClicked

    private void searchstaffnameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchstaffnameTxtKeyReleased
           getServiceRecords(); 
    }//GEN-LAST:event_searchstaffnameTxtKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   TableModel model1=servicerecTable.getModel(); String delselectedColumns1=null;
      for(int i=0; i<model1.getRowCount();i++){
       if((Boolean) model1.getValueAt(i,0)){
     delselectedColumns1=servicerecTable.getModel().getValueAt(i,0).toString();
       }
     }
   if(delselectedColumns1==null){
  JOptionPane.showMessageDialog(rootPane,"Please select claims from the service records below !","Alert",JOptionPane.WARNING_MESSAGE);
      }else{
       try {
           Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
           PreparedStatement pstdel;
           int choice=JOptionPane.showConfirmDialog(rootPane, "Do you want to unlock selected claim(s) ?","Confirm",JOptionPane.YES_NO_OPTION);
           if(choice==JOptionPane.YES_OPTION){
                int k=0;
    TableModel model2=servicerecTable.getModel();  String unlockclaimsql;
    for(int i=0; i<model2.getRowCount();i++){
                        if((Boolean) model2.getValueAt(i,0)){
                            delselectedColumns1=servicerecTable.getModel().getValueAt(i,3).toString();
   unlockclaimsql="UPDATE CLAIMS SET  STATUS='UNCLAIMED' WHERE SERVICE_NO='"+delselectedColumns1+"'";   
     pstdel=(PreparedStatement)connectDb.prepareStatement(unlockclaimsql);
     k= pstdel.executeUpdate();
   } 
    }            
   if(k>0){
        getServiceRecords();
  JOptionPane.showMessageDialog(rootPane, "Selected claim(s) unclocked successfully !","Success",JOptionPane.INFORMATION_MESSAGE);
    }              
                         
           }  } catch (SQLException ex) {
           Logger.getLogger(Createmileage.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accomTxt;
    private javax.swing.JTextField accomTxt2;
    private javax.swing.JTextField bfastTxt;
    private javax.swing.JTextField bfastTxt2;
    private javax.swing.JTextField callsearchTxt;
    private javax.swing.JTextField dinnerTxt;
    private javax.swing.JTextField dinnerTxt2;
    private javax.swing.JComboBox<String> dinnercomboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField kmTxt;
    private javax.swing.JTextField laundTxt;
    private javax.swing.JTextField laundTxt2;
    private javax.swing.JTextField lunchTxt;
    private javax.swing.JTextField lunchTxt2;
    private javax.swing.JComboBox<String> lunchcombo;
    public javax.swing.JPanel mileagecreationPanel;
    private javax.swing.JPanel nofarePanel;
    private javax.swing.JTextField othersTxt;
    private javax.swing.JTextField othersTxt2;
    private javax.swing.JTextField pettiesTxt;
    private javax.swing.JTextField pettiesTxt2;
    private javax.swing.JPanel privatePanel;
    private javax.swing.JTextField psvfareTxt;
    private javax.swing.JPanel publicPanel;
    private javax.swing.JTextField searchstaffnameTxt;
    private javax.swing.JTextField servicenoTxt;
    private javax.swing.JTable servicerecTable;
    private javax.swing.JPanel servicerecordPanel;
    private javax.swing.JPanel transportPanel;
    public javax.swing.JPanel unlockclaimPanel;
    public javax.swing.JPanel unlockholderPanel;
    // End of variables declaration//GEN-END:variables
}
