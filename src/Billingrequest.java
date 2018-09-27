
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Billingrequest extends javax.swing.JFrame {
 NumberFormat formatter=NumberFormat.getNumberInstance(Locale.UK); 
   Home access=new Home(); String contractnumber;String refno;
        String referenceitem; Image companylogo;
         String CLIENTNAME,soldby,clientsname,currentuser,directory;
   String currency; java.sql.Date billfromDate,billtoDate;
     SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
   DecimalFormat df1=new DecimalFormat("#,###.00");
     Document  receiptDoc;
    double totalbilled;
     String[] cols2={"NO","REQUEST NO","CLIENT","CONTRACT NO.","DESCRIPTION","BILL FROM","BILL TO","AMOUNT BILLED"};
  DefaultTableModel contractbilltableModel=new DefaultTableModel(cols2,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }  }; 
  String billingitem;
   String[] cols ={"NO","REQUEST NO","CLIENT","CSR NO.","DESCRIPTION","VALUE","VAT","TOTAL","STATUS"};
  DefaultTableModel csrrequesttableModel=new DefaultTableModel(cols,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }  }; 
  
    public Billingrequest() {
        initComponents();
         csrrequestsTable.getTableHeader().setReorderingAllowed(false);
           csrrequestsTable.getColumnModel().getColumn(0).setPreferredWidth(1);
           
          contractsbillTable.getTableHeader().setReorderingAllowed(false);
           contractsbillTable.getColumnModel().getColumn(0).setPreferredWidth(1);
             
    }
  public void getContracts(){
     try {
         String getcontractsql="SELECT CONTRACTS.CLIENT_NO,BILLINGREQUESTS.REQNO,CLIENTS.clientname,CONTRACTS.CONTRACT_NO, CONTRACTS.CONT_DESCRIP," +
                 "BILLINGREQUESTS.BILLFROM,BILLINGREQUESTS.BILLTO, CONTRACTS.CURRENCY,BILLINGREQUESTS.BILLAMOUNT FROM BILLINGREQUESTS " +
                 "LEFT JOIN CONTRACTS ON BILLINGREQUESTS.REFNO =CONTRACTS.CONTRACT_NO"
                 + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO  ORDER BY BILLINGREQUESTS.REQNO ASC ";
         Connection connectDb = (Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
                 ResultSet rst=pst.executeQuery(); int i=0; double total=0;
    int rows=contractsbillTable.getRowCount();
                for(int g=0;g<rows;g++){
        contractbilltableModel.removeRow(0);
                }
        while(rst.next()){
  if(rst.getString(2)==null){
  
  } else{         
if(!rst.getString(6).equalsIgnoreCase("N/A")&&!rst.getString(7).equalsIgnoreCase("N/A")){
  contractbilltableModel.addRow(new Object[]{i+1,rst.getString(2),rst.getString(3),rst.getString(4), rst.getString(5),sdf.format(rst.getDate(6)),sdf.format(rst.getDate(7)),
 (rst.getString(8))+". "+ formatter.format(rst.getDouble(9))});
                    i++;}
  } }
                
     } catch (SQLException ex) {
         Logger.getLogger(Billingrequest.class.getName()).log(Level.SEVERE, null, ex);
     }
                
  
  }  
    
 public void getCSR(){
        try {
  
  String getcsrsql="SELECT SUPPLY_REQUESTS.CLIENTNO,BILLINGREQUESTS.REQNO,CLIENTS.clientname,SUPPLY_REQUESTS.CSRNO, SUPPLY_REQUESTS.DESCRIPTION," +
"SUPPLY_REQUESTS.CURRENCY,SUPPLY_REQUESTS.CSRVALUE,SUPPLY_REQUESTS.VAT,BILLINGREQUESTS.STATUS  FROM SUPPLY_REQUESTS " +
"left JOIN CLIENTS ON SUPPLY_REQUESTS.CLIENTNO=CLIENTS.CLIENT_NO "
          + " LEFT JOIN BILLINGREQUESTS ON SUPPLY_REQUESTS.CSRNO= BILLINGREQUESTS.REFNO ORDER BY SUPPLY_REQUESTS.CSRNO DESC ";
            try (Connection connectDb = (Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword)) {
                PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcsrsql);
                ResultSet rst=pst.executeQuery(); int i=0; double total=0;
                String billingreq="NA"; String billstatus="NOT BILLED";
                int rows=csrrequestsTable.getRowCount();
                for(int g=0;g<rows;g++){
                    csrrequesttableModel.removeRow(0);
                }
                while(rst.next()){
                    total=((rst.getDouble(8)+100)*rst.getDouble(7))/100;
              if(rst.getString(2) !=null){
              billingreq=rst.getString(2); billstatus="BILLED";
              }
 csrrequesttableModel.addRow(new Object[]{i+1,billingreq,rst.getString(3),rst.getString(4), (rst.getString(5)),(rst.getString(6))+". "+
                            formatter.format(rst.getDouble(7)),rst.getDouble(8),rst.getString(6)+". "+formatter.format(total),billstatus});
                    i++;
                }
          connectDb.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Supplyrequest.class.getName()).log(Level.SEVERE, null, ex);
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

        billrequestPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        refnoTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        clientnameTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        addressTxt = new javax.swing.JTextField();
        townTxt = new javax.swing.JTextField();
        contactTxt = new javax.swing.JTextField();
        phoneTxt = new javax.swing.JTextField();
        detailsholderPanel = new javax.swing.JPanel();
        csrholderPanel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        csrnoTxt = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        csrdescripTxt = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        csrdateTxt = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        ponumTxt = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        podateTxt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        csrvalueTxt = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        billamountTxt = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        amountviewTxt = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        vatTxt2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        totalbilableTxt = new javax.swing.JTextField();
        contractholderPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        contractnoTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        contdescripTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        startTxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        endTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        contvalueTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        billfromTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        billtoTxt = new javax.swing.JTextField();
        periodTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        contbillamountTxt = new javax.swing.JTextField();
        contamountviewTxt = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        vatTxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        totalbilledTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        billingtableholderPanel = new javax.swing.JPanel();
        csrrequestsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        csrrequestsTable = new javax.swing.JTable();
        contractrequestsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        contractsbillTable = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        requestnoTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        billrequestPanel.setBackground(new java.awt.Color(153, 0, 153));
        billrequestPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BILLING REQUESTS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Client Details ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel1.setText("REF No:");

        refnoTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        refnoTxt.setText("Enter CSR No. or Contract No.");
        refnoTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refnoTxtMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refnoTxtMouseExited(evt);
            }
        });
        refnoTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                refnoTxtKeyReleased(evt);
            }
        });

        jLabel2.setText("CLIENT NAME:");

        clientnameTxt.setEditable(false);

        jLabel3.setText("P.O. BOX:");

        jLabel4.setText("CONTACT:");

        jLabel5.setText("TOWN:");

        jLabel6.setText("PHONE:");

        addressTxt.setEditable(false);

        townTxt.setEditable(false);

        contactTxt.setEditable(false);

        phoneTxt.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(townTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(refnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(clientnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(townTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(contactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        detailsholderPanel.setBackground(new java.awt.Color(255, 255, 255));
        detailsholderPanel.setLayout(new java.awt.CardLayout());

        csrholderPanel.setBackground(new java.awt.Color(255, 255, 255));
        csrholderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Supply Request Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 102))); // NOI18N

        jLabel17.setText("CSR NO:");

        csrnoTxt.setEditable(false);

        jLabel18.setText("DESCRIPTION:");

        csrdescripTxt.setEditable(false);

        jLabel19.setText("CSR DATE:");

        csrdateTxt.setEditable(false);
        csrdateTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csrdateTxtActionPerformed(evt);
            }
        });

        jLabel20.setText("PO NO:");

        ponumTxt.setEditable(false);

        jLabel21.setText("PO DATE:");

        podateTxt.setEditable(false);

        jLabel22.setText("CSR VALUE (VAT Excl):");

        csrvalueTxt.setEditable(false);
        csrvalueTxt.setBackground(new java.awt.Color(0, 0, 0));
        csrvalueTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        csrvalueTxt.setForeground(new java.awt.Color(51, 255, 0));
        csrvalueTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csrvalueTxtActionPerformed(evt);
            }
        });

        jLabel23.setText("BILLING AMOUNT:");

        billamountTxt.setBackground(new java.awt.Color(0, 0, 0));
        billamountTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        billamountTxt.setForeground(new java.awt.Color(51, 255, 0));
        billamountTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billamountTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billamountTxtKeyTyped(evt);
            }
        });

        jLabel24.setText("AMOUNT BILLED:");

        amountviewTxt.setEditable(false);
        amountviewTxt.setBackground(new java.awt.Color(0, 0, 0));
        amountviewTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        amountviewTxt.setForeground(new java.awt.Color(51, 255, 51));

        jLabel26.setText("VAT %:");

        vatTxt2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        vatTxt2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vatTxt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vatTxt2ActionPerformed(evt);
            }
        });

        jLabel27.setText("CSR VALUE (VAT Incl):");

        totalbilableTxt.setBackground(new java.awt.Color(0, 0, 0));
        totalbilableTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalbilableTxt.setForeground(new java.awt.Color(102, 255, 51));

        javax.swing.GroupLayout csrholderPanelLayout = new javax.swing.GroupLayout(csrholderPanel);
        csrholderPanel.setLayout(csrholderPanelLayout);
        csrholderPanelLayout.setHorizontalGroup(
            csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(csrholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(csrdescripTxt)
                    .addComponent(csrnoTxt)
                    .addComponent(csrdateTxt)
                    .addComponent(ponumTxt)
                    .addComponent(podateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addGap(173, 173, 173)
                .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(csrholderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(totalbilableTxt))
                    .addGroup(csrholderPanelLayout.createSequentialGroup()
                        .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(csrvalueTxt)
                            .addComponent(billamountTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                            .addComponent(amountviewTxt)
                            .addComponent(vatTxt2))))
                .addGap(79, 79, 79))
        );
        csrholderPanelLayout.setVerticalGroup(
            csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(csrholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(csrnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(csrvalueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(csrholderPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(csrdescripTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(csrdateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(ponumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(csrholderPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(billamountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(31, 31, 31)
                        .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(vatTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalbilableTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(csrholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(podateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(amountviewTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        detailsholderPanel.add(csrholderPanel, "card2");

        contractholderPanel.setBackground(new java.awt.Color(255, 255, 255));
        contractholderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contract Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel7.setText("CONTRACT NO:");

        contractnoTxt.setEditable(false);

        jLabel8.setText("DESCRIPTION:");

        contdescripTxt.setEditable(false);

        jLabel9.setText("START:");

        startTxt.setEditable(false);

        jLabel10.setText("END:");

        endTxt.setEditable(false);

        jLabel11.setText("VALUE (VAT Incl.):");

        contvalueTxt.setEditable(false);
        contvalueTxt.setBackground(new java.awt.Color(0, 0, 0));
        contvalueTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        contvalueTxt.setForeground(new java.awt.Color(102, 255, 0));

        jLabel12.setText("BILLING FROM:");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        billfromTxt.setEditable(false);

        jLabel13.setText("BILLING TO:");

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        billtoTxt.setEditable(false);

        periodTxt.setEditable(false);

        jLabel14.setText("PERIOD:");

        jLabel15.setText("BILLING AMOUNT:");

        contbillamountTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contbillamountTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contbillamountTxtKeyTyped(evt);
            }
        });

        contamountviewTxt.setEditable(false);
        contamountviewTxt.setBackground(new java.awt.Color(0, 0, 0));
        contamountviewTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        contamountviewTxt.setForeground(new java.awt.Color(102, 255, 0));

        jLabel25.setText("VAT %:");

        vatTxt.setText("16.0");
        vatTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vatTxtActionPerformed(evt);
            }
        });
        vatTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vatTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                vatTxtKeyTyped(evt);
            }
        });

        jLabel16.setText("TOTAL BILLED:");

        totalbilledTxt.setEditable(false);
        totalbilledTxt.setBackground(new java.awt.Color(0, 0, 0));
        totalbilledTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalbilledTxt.setForeground(new java.awt.Color(51, 255, 51));

        javax.swing.GroupLayout contractholderPanelLayout = new javax.swing.GroupLayout(contractholderPanel);
        contractholderPanel.setLayout(contractholderPanelLayout);
        contractholderPanelLayout.setHorizontalGroup(
            contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractholderPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(contractholderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contvalueTxt))
                    .addGroup(contractholderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contractnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contractholderPanelLayout.createSequentialGroup()
                        .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contdescripTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contractholderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(billfromTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contractholderPanelLayout.createSequentialGroup()
                        .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(33, 33, 33)
                        .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contractholderPanelLayout.createSequentialGroup()
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(billtoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(periodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contractholderPanelLayout.createSequentialGroup()
                        .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contractholderPanelLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(contbillamountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(contractholderPanelLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(vatTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(contamountviewTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(totalbilledTxt))))
                .addGap(80, 80, 80))
        );
        contractholderPanelLayout.setVerticalGroup(
            contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(contractnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billfromTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(contdescripTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billtoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(startTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(periodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(29, 29, 29)
                .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(endTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(contbillamountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contamountviewTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(vatTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(totalbilledTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25))
                    .addGroup(contractholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(contvalueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        detailsholderPanel.add(contractholderPanel, "card3");

        jButton1.setText("CREATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("UPDATE");

        jButton3.setText("DELETE");

        billingtableholderPanel.setBackground(new java.awt.Color(255, 255, 255));
        billingtableholderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BILLING REQUESTS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N
        billingtableholderPanel.setLayout(new java.awt.CardLayout());

        csrrequestsPanel.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        csrrequestsTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        csrrequestsTable.setModel(csrrequesttableModel);
        csrrequestsTable.setFillsViewportHeight(true);
        csrrequestsTable.setGridColor(new java.awt.Color(204, 0, 204));
        csrrequestsTable.setRowHeight(25);
        csrrequestsTable.setRowMargin(2);
        csrrequestsTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        csrrequestsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                csrrequestsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(csrrequestsTable);

        javax.swing.GroupLayout csrrequestsPanelLayout = new javax.swing.GroupLayout(csrrequestsPanel);
        csrrequestsPanel.setLayout(csrrequestsPanelLayout);
        csrrequestsPanelLayout.setHorizontalGroup(
            csrrequestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1281, Short.MAX_VALUE)
        );
        csrrequestsPanelLayout.setVerticalGroup(
            csrrequestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );

        billingtableholderPanel.add(csrrequestsPanel, "card3");

        contractrequestsPanel.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setForeground(new java.awt.Color(204, 0, 153));

        contractsbillTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        contractsbillTable.setModel(contractbilltableModel);
        contractsbillTable.setFillsViewportHeight(true);
        contractsbillTable.setGridColor(new java.awt.Color(204, 0, 204));
        contractsbillTable.setRowHeight(25);
        contractsbillTable.setRowMargin(2);
        contractsbillTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        contractsbillTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contractsbillTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(contractsbillTable);

        javax.swing.GroupLayout contractrequestsPanelLayout = new javax.swing.GroupLayout(contractrequestsPanel);
        contractrequestsPanel.setLayout(contractrequestsPanelLayout);
        contractrequestsPanelLayout.setHorizontalGroup(
            contractrequestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractrequestsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
                .addContainerGap())
        );
        contractrequestsPanelLayout.setVerticalGroup(
            contractrequestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );

        billingtableholderPanel.add(contractrequestsPanel, "card2");

        jButton4.setText("PRINT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel28.setText("REQUEST NO:");

        requestnoTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(billingtableholderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addComponent(requestnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(29, 29, 29)
                                .addComponent(jButton4)
                                .addGap(25, 25, 25))
                            .addComponent(detailsholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 891, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(detailsholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28)
                                .addComponent(requestnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton2)
                                .addComponent(jButton3)
                                .addComponent(jButton4))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billingtableholderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout billrequestPanelLayout = new javax.swing.GroupLayout(billrequestPanel);
        billrequestPanel.setLayout(billrequestPanelLayout);
        billrequestPanelLayout.setHorizontalGroup(
            billrequestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billrequestPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        billrequestPanelLayout.setVerticalGroup(
            billrequestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(billrequestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(billrequestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refnoTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_refnoTxtKeyReleased
   referenceitem=refnoTxt.getText();
        getSearchitem(); countRequests();
    }//GEN-LAST:event_refnoTxtKeyReleased
public void getSearchitem(){
    try {
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
     String getcsrsql="SELECT CLIENTS.CLIENT_NO,CLIENTS.CLIENTNAME,CLIENTS.pobox,CLIENTS.TOWN,CLIENTS.CONT_PERSON,"
             + "CLIENTS.MOBILE, SUPPLY_REQUESTS.CSRNO,SUPPLY_REQUESTS.DESCRIPTION,SUPPLY_REQUESTS.CSRDATE,SUPPLY_REQUESTS.PONUM,"
             + "SUPPLY_REQUESTS.PODATE,SUPPLY_REQUESTS.CURRENCY,SUPPLY_REQUESTS.CSRVALUE,SUPPLY_REQUESTS.INVOICE_NO,SUPPLY_REQUESTS.VAT FROM "
             + "CLIENTS LEFT JOIN SUPPLY_REQUESTS ON CLIENTS.CLIENT_NO = SUPPLY_REQUESTS.CLIENTNO WHERE"
           + " SUPPLY_REQUESTS.CSRNO LIKE '"+referenceitem+"%' ORDER BY SUPPLY_REQUESTS.CSRNO ASC";
     PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcsrsql);
               ResultSet rst=pst.executeQuery(); int i=0; int j=0;
             while(rst.next()){
                 detailsholderPanel.removeAll();
                 detailsholderPanel.repaint();
                 detailsholderPanel.revalidate();
                 detailsholderPanel.add(csrholderPanel);
                 
                 billingtableholderPanel.removeAll();
                  billingtableholderPanel.repaint();
                 billingtableholderPanel.revalidate();
                 billingtableholderPanel.add(csrrequestsPanel);
            //refnoTxt.setText(rst.getString(7));
            clientnameTxt.setText(rst.getString(2));addressTxt.setText(rst.getString(3));townTxt.setText(rst.getString(4));
            contactTxt.setText(rst.getString(5)); phoneTxt.setText(rst.getString(6));
            csrnoTxt.setText(rst.getString(7)); csrdescripTxt.setText(rst.getString(8)); csrdateTxt.setText(sdf.format(rst.getDate(9)));
            ponumTxt.setText(rst.getString(10)); podateTxt.setText(sdf.format(rst.getDate(11))); csrvalueTxt.setText(rst.getString(12)+". "+formatter.format(rst.getDouble(13)) );
currency=rst.getString(12);  contractnumber=rst.getString(7);  billamountTxt.setText(String.valueOf((rst.getDouble(13))));
vatTxt2.setText(String.valueOf(rst.getDouble(15)));
totalbilableTxt.setText(currency+". "+String.valueOf(formatter.format(rst.getDouble(13)*(1+(rst.getDouble(15)/100))))); 
                      getCSR(); billingitem="CSR";
                      i++;
               }
             System.out.println("CSR found "+i);
               if(i<1){
      csrnoTxt.setText(null); csrdescripTxt.setText(null); csrdateTxt.setText(null);
            ponumTxt.setText(null); podateTxt.setText(null); csrvalueTxt.setText(null);
    String getContract="SELECT CLIENTS.CLIENT_NO,CLIENTS.CLIENTNAME,CLIENTS.pobox,CLIENTS.TOWN,CLIENTS.CONT_PERSON,"
             + "CLIENTS.MOBILE, CONTRACTS.CONTRACT_NO,CONTRACTS.CONT_DESCRIP,CONTRACTS.START,CONTRACTS.END,"
             + "CONTRACTS.CURRENCY,CONTRACTS.VALUE FROM "
             + "CLIENTS LEFT JOIN CONTRACTS ON CLIENTS.CLIENT_NO = CONTRACTS.CLIENT_NO WHERE"
           + " CONTRACTS.CONTRACT_NO LIKE '"+refnoTxt.getText()+"%' ORDER BY CLIENTS.CLIENTNAME ASC";
  PreparedStatement pst1=(PreparedStatement)connectDb.prepareStatement(getContract);
               ResultSet rst1=pst1.executeQuery(); 
             while(rst1.next()){
                 detailsholderPanel.removeAll();
                 detailsholderPanel.repaint();
                 detailsholderPanel.revalidate();
                 detailsholderPanel.add(contractholderPanel);
                 
                  billingtableholderPanel.removeAll();
                  billingtableholderPanel.repaint();
                 billingtableholderPanel.revalidate();
                 billingtableholderPanel.add(contractrequestsPanel);
                 
          contractnumber=rst1.getString(7);       
           // refnoTxt.setText(rst.getString(7));     
            clientnameTxt.setText(rst1.getString(2));addressTxt.setText(rst1.getString(3));townTxt.setText(rst1.getString(4));
            contactTxt.setText(rst1.getString(5)); phoneTxt.setText(rst1.getString(6));
            contractnoTxt.setText(rst1.getString(7)); contdescripTxt.setText(rst1.getString(8)); startTxt.setText(sdf.format(rst1.getDate(9)));
            endTxt.setText(rst1.getString(10)); contvalueTxt.setText(rst1.getString(11)+". "+df1.format(rst1.getDouble(12)) );
            currency=rst1.getString(11);
           getContracts(); 
            j++; billingitem="CONTRACT";
               }
              System.out.println("contract found "+j);
               }
             if(j<1){
             contractnoTxt.setText(null); contdescripTxt.setText(null); startTxt.setText(null);
            endTxt.setText(null);contvalueTxt.setText(null);   
           //   JOptionPane.showMessageDialog(null, "No Supply Request or Contract number found for Ref No. "+refnoTxt.getText()+"!", "Not Found", JOptionPane.WARNING_MESSAGE);
             }
             if(i<1&j<1){
             clientnameTxt.setText(null);addressTxt.setText(null);townTxt.setText(null);
            contactTxt.setText(null); phoneTxt.setText(null);
             }
    
                  connectDb.close();     
           } catch (SQLException ex) {
               Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
           }
}
    private void csrdateTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csrdateTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_csrdateTxtActionPerformed

    private void refnoTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refnoTxtMouseClicked
    refnoTxt.setText(null);
    }//GEN-LAST:event_refnoTxtMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if(!csrnoTxt.getText().isEmpty()){
        refno=csrnoTxt.getText();
    if(billamountTxt.getText().isEmpty()){
   JOptionPane.showMessageDialog(null, "Please enter a CSR Billing amount!","Billing Amount Required",JOptionPane.WARNING_MESSAGE);  
    }
   
    else{
    int choice=JOptionPane.showConfirmDialog(null, "Do you want to create billing request of "+billamountTxt.getText()+" for Ref No. "+csrnoTxt.getText()+"?","Confirm",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_NO_OPTION){
 createRequest();
  } 
    }
    }
    else if(!contractnoTxt.getText().isEmpty()){
        refno=contractnoTxt.getText();
     if(contbillamountTxt.getText().isEmpty()){
   JOptionPane.showMessageDialog(null, "Please enter a Contract Billing amount!","Billing Amount Required",JOptionPane.WARNING_MESSAGE);  
    }
      else if(vatTxt.getText().isEmpty()){
     JOptionPane.showMessageDialog(null, "Please enter VAT percentage!","VAT Percentage Required",JOptionPane.WARNING_MESSAGE);
    }else if(billfromDate==null){
 JOptionPane.showMessageDialog(null, "Please select the Bill from date!","Bill From Date Required",JOptionPane.WARNING_MESSAGE);
    }
    else if(billtoDate==null){
 JOptionPane.showMessageDialog(null, "Please select the Bill to date!","Bill To Date Required",JOptionPane.WARNING_MESSAGE);
    }
     else{
    int choice=JOptionPane.showConfirmDialog(null, "Do you want to create billing request of "+totalbilledTxt.getText()+" for Ref No. "+csrnoTxt.getText()+"?","Confirm",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_NO_OPTION){
  createRequest();
  }
    }
    }
    else{
    JOptionPane.showMessageDialog(null, "Please enter a valid reference number!","Ref Number Required",JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

public void countRequests(){
     try {
         int year=LocalDate.now().getYear();
         String getreqcount="SELECT * FROM BILLINGREQUESTS";
         Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
         PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(getreqcount);
         ResultSet rst2=pst2.executeQuery(); int reqcount=0;
         while(rst2.next()){
             reqcount++; 
             requestnoTxt.setText("REQ-"+year+"-"+(reqcount+1)); 
         }
     } catch (SQLException ex) {
         Logger.getLogger(Billingrequest.class.getName()).log(Level.SEVERE, null, ex);
     }
}
    public void createRequest(){
     try {
         String createbillingsql="SELECT REQNO, STATUS FROM BILLINGREQUESTS WHERE REFNO='"+refno+"' ";
         Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
    PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(createbillingsql);
               ResultSet rst=pst.executeQuery(); int i=0; String status="";
     while(rst.next()){
     status=rst.getString(2);
     i++;
     }  
/*if(status.equalsIgnoreCase("BILLED")){
JOptionPane.showMessageDialog(this, "Item of Reference number "+refno+" is already billed!","Item Billed",JOptionPane.WARNING_MESSAGE);
}else*/
{
          
String creatreqsql="INSERT INTO BILLINGREQUESTS VALUES(?,?,?,?,?,?)";
PreparedStatement pst3=(PreparedStatement)connectDb.prepareStatement(creatreqsql);
 pst3.setString(1, requestnoTxt.getText()); 
 pst3.setString(2, refno);
 if(!csrnoTxt.getText().isEmpty()){
  pst3.setString(3, "N/A");
  pst3.setString(4, "N/A");
   pst3.setDouble(5, Double.parseDouble(billamountTxt.getText())); 
 }else if(!contractnoTxt.getText().isEmpty()){
  pst3.setString(3, String.valueOf(billfromDate)); 
  pst3.setString(4, String.valueOf(billtoDate)); 
  pst3.setDouble(5, Double.parseDouble(contbillamountTxt.getText()));  
 }
   pst3.setString(6, "BILLED");
 int f= pst3.executeUpdate();
   
 if(f>0){
    getCSR(); getContracts();
 JOptionPane.showMessageDialog(this, "Billing request for Item Reference number "+refno+" created successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
 printRequest();
 }
}     
     } catch (SQLException ex) {
         Logger.getLogger(Billingrequest.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    
    private void billamountTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billamountTxtKeyReleased
     if(Double.parseDouble(billamountTxt.getText())>0){double vat=0;
     if(!vatTxt2.getText().isEmpty()){ 
         vat=Double.valueOf(vatTxt2.getText());
     }
     
     amountviewTxt.setText(currency+" "+df1.format(Double.parseDouble(billamountTxt.getText())*((100+vat)/100)));
     }
    }//GEN-LAST:event_billamountTxtKeyReleased

    private void contbillamountTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contbillamountTxtKeyReleased
     totalbilled=0.0; totalbilledTxt.setText(null);
        if(Double.parseDouble(contbillamountTxt.getText())>0){
   totalbilledTxt.setText(currency+" "+df1.format(Double.parseDouble(contbillamountTxt.getText())*((100+Double.parseDouble(vatTxt.getText()))/100)));          
     contamountviewTxt.setText(currency+" "+df1.format(Double.parseDouble(contbillamountTxt.getText())));
     }
    }//GEN-LAST:event_contbillamountTxtKeyReleased

    private void contbillamountTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contbillamountTxtKeyTyped
        char c=evt.getKeyChar();
        if((c>='0')&&(c<='9')){
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_contbillamountTxtKeyTyped

    private void billamountTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billamountTxtKeyTyped
         char c=evt.getKeyChar();
        if((c>='0')&&(c<='9')){
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_billamountTxtKeyTyped

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
      billfromDate= new java.sql.Date(jXDatePicker1.getDate().getTime());
      billfromTxt.setText(sdf.format(billfromDate));
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
       billtoDate=new java.sql.Date(jXDatePicker2.getDate().getTime());
       if(billtoDate.before(billfromDate)){
       JOptionPane.showMessageDialog(null, "Please select a valid Bill To Date!","Invalid To Date",JOptionPane.WARNING_MESSAGE);
       }else
        billtoTxt.setText(sdf.format(billtoDate)); 
       getContractlength();       
    }//GEN-LAST:event_jXDatePicker2ActionPerformed
public void getContractlength(){
 long daysold=(jXDatePicker2.getDate().getTime()-jXDatePicker1.getDate().getTime())/(1000*60*60*24);
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
    private void vatTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vatTxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_vatTxtKeyTyped

    private void vatTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vatTxtKeyReleased
     if(!contbillamountTxt.getText().isEmpty()){
  totalbilled=((Double.parseDouble(vatTxt.getText())+100)*Double.parseDouble(contbillamountTxt.getText()))/100;
     totalbilledTxt.setText(currency+". "+df1.format(totalbilled));
     }
    }//GEN-LAST:event_vatTxtKeyReleased

    private void refnoTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refnoTxtMouseExited
     if(contractnumber!=null){
      refnoTxt.setText( contractnumber ); 
     }
       
    }//GEN-LAST:event_refnoTxtMouseExited

    private void csrrequestsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_csrrequestsTableMouseClicked
   int rows=csrrequestsTable.getRowCount();countRequests();
     int i=csrrequestsTable.getSelectedRow();
    if(rows>0){
      requestnoTxt.setText(String.valueOf(csrrequestsTable.getValueAt(i, 1)));
   
     }
    referenceitem=csrrequestsTable.getValueAt(i, 3).toString();
    getSearchitem(); 
    
    }//GEN-LAST:event_csrrequestsTableMouseClicked

    private void csrvalueTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csrvalueTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_csrvalueTxtActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      printRequest();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void contractsbillTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contractsbillTableMouseClicked
     int rows=contractsbillTable.getRowCount();
     Date  localdate;
     if(rows>0){
         try {
             int i=contractsbillTable.getSelectedRow();
             requestnoTxt.setText(String.valueOf(contractsbillTable.getValueAt(i, 1)));
             billfromTxt.setText(String.valueOf(contractsbillTable.getValueAt(i, 5)));
             billtoTxt.setText(String.valueOf(contractsbillTable.getValueAt(i, 6)));
             contamountviewTxt.setText(String.valueOf(contractsbillTable.getValueAt(i, 7)));
             String createbillingsql="SELECT BILLAMOUNT FROM BILLINGREQUESTS WHERE REQNO='"+requestnoTxt.getText()+"' ";
             Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
             PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(createbillingsql);
             ResultSet rst=pst.executeQuery(); int j=0; String status="";
             while(rst.next()){
         contbillamountTxt.setText(String.valueOf(rst.getDouble(1)));  
                 j++;
             }
 totalbilledTxt.setText(currency+" "+formatter.format((((Double.parseDouble(vatTxt.getText())+100)/100)*Double.parseDouble(contbillamountTxt.getText()))));
         } catch (SQLException ex) {
             Logger.getLogger(Billingrequest.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
    }//GEN-LAST:event_contractsbillTableMouseClicked

    private void vatTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vatTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vatTxtActionPerformed

    private void vatTxt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vatTxt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vatTxt2ActionPerformed

   public void printRequest(){
  /*
       if(requestnoTxt.getText().isEmpty()){
 JOptionPane.showMessageDialog(null, "Please create a new Billing Request or select one from list below!","Request No Required",JOptionPane.WARNING_MESSAGE);
    }
    else if(contbillamountTxt.getText().isEmpty()){
    JOptionPane.showMessageDialog(null, "Please enter amount to be billed!","Billing Amount Required",JOptionPane.WARNING_MESSAGE);
   } 
    
    else  if(vatTxt.getText().isEmpty()){
 JOptionPane.showMessageDialog(null, "Please enter VAT percentage!","VAT Percentage Required",JOptionPane.WARNING_MESSAGE);  
   }  
    else
    */
       try {
         String createbillingsql="SELECT REQNO, STATUS FROM BILLINGREQUESTS WHERE REQNO='"+requestnoTxt.getText()+"' ";
         Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
         PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(createbillingsql);
         ResultSet rst=pst.executeQuery(); int i=0; String status="";
         while(rst.next()){
             status=rst.getString(2);
             i++;
         }
    if(i<1){
    JOptionPane.showMessageDialog(this,"Billing Request number  "+requestnoTxt.getText()+" does not exist!","Request Does not Exist",JOptionPane.WARNING_MESSAGE);
    }   
    else{
    FileOutputStream filename=null; 
   receiptDoc=new Document(PageSize.A4 ); 
      JFileChooser filesaver=new JFileChooser();
       filesaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int option=filesaver.showSaveDialog(jPanel2); 
    
        String   dir=filesaver.getSelectedFile().toString();
          String sqllogo="SELECT LOGO FROM SETTINGS";
            com.mysql.jdbc.PreparedStatement pstlogo=(com.mysql.jdbc.PreparedStatement) connectDb.prepareStatement(sqllogo);
            ResultSet rst3=pstlogo.executeQuery();
            
            if(rst3!=null){
            while(rst3.next()){
            byte[] byt = null;
            byt=rst3.getBytes(1);
            companylogo = Toolkit.getDefaultToolkit().createImage(byt);
            }
            }
          String printdate=new SimpleDateFormat("dd MMM, yyyy").format(Calendar.getInstance().getTime()); 
  directory=dir+"/Bill Request No "+requestnoTxt.getText()+" "+clientnameTxt.getText()+".pdf";
    filename = new FileOutputStream(directory);
   PdfWriter writer=PdfWriter.getInstance(receiptDoc ,filename);
            receiptDoc.open();
            Paragraph space=new Paragraph("          ");
            com.itextpdf.text.Font font1=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,22,Font.BOLD);
            com.itextpdf.text.Font font2=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,8,Font.BOLD);
            com.itextpdf.text.Font font3=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
            com.itextpdf.text.Font font4=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.HELVETICA,8,Font.LAYOUT_LEFT_TO_RIGHT);
             com.itextpdf.text.Font font5=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.COURIER,9,Font.LAYOUT_LEFT_TO_RIGHT);
             
      com.itextpdf.text.Font font6=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.COURIER,12,Font.LAYOUT_LEFT_TO_RIGHT); 
      com.itextpdf.text.Font font7=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);   
          com.itextpdf.text.Font font8=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,9,Font.BOLD);      
           
            
com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(companylogo , null); 
image.setAbsolutePosition(05, 780);
         image.scaleToFit(150,50);
 writer.getDirectContent().addImage(image);  receiptDoc.add(space);  // receiptDoc.add(space); 
  
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
        // receiptDoc.add(space);
         
     Paragraph receiptlabel2=new Paragraph("BILLING REQUEST",font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
  receiptDoc.add(receiptlabel2);      //   receiptDoc.add(separatorlable3);
 // receiptDoc.add(space);
  
   Paragraph date=new Paragraph( "DATE: "+printdate,font6);
 date.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT); 
   date.setAlignment(Paragraph.ALIGN_LEFT);
           receiptDoc.add(date);
            
   Paragraph paras1345=new Paragraph("REQ. NO: "+requestnoTxt.getText(),font6);
   paras1345.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT); 
 paras1345.setAlignment(Paragraph.ALIGN_LEFT);
   receiptDoc.add(paras1345); receiptDoc.add(space);
   
   
          float[] colswidth4=new float[]{15f,9f,9f};
   PdfPTable colstable2=new PdfPTable(colswidth4);
     colstable2 .setWidthPercentage(100);//colstable.setHorizontalAlignment();
      colstable2.setHorizontalAlignment(Element.ALIGN_CENTER);  
   
      // Paragraph paras102=new Paragraph("CUSTOMER NAME & ADDRESS"+"",font6);  
 PdfPCell cell320=new PdfPCell( new Phrase("CUSTOMER NAME & ADDRESS"+"",font6));    cell320.setBorder(Rectangle.NO_BORDER);
  colstable2.addCell(cell320);  
  
   //Paragraph paras103=new Paragraph("ORDER NO"+"",font6);  
 PdfPCell cell321=new PdfPCell( new Phrase("REFERENCE ITEM"+"",font6));    cell321.setBorder(Rectangle.NO_BORDER);
  colstable2.addCell(cell321);  
  
  //  Paragraph paras104=new Paragraph("DATED"+"",font6);  
 PdfPCell cell322=new PdfPCell( new Phrase(" "+"",font6));    cell322.setBorder(Rectangle.NO_BORDER);
  colstable2.addCell(cell322);  
  
   PdfPCell cell323=new PdfPCell( new Phrase(" "));    cell323.setBorder(Rectangle.NO_BORDER);
   cell323.setColspan(3); colstable2.addCell(cell323);  
   
   Paragraph paras1=new Paragraph(clientnameTxt.getText()+",",font6);  
  paras1.setAlignment(Paragraph.ALIGN_CENTER);  paras1.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);   
  PdfPCell cell31=new PdfPCell(paras1);
  cell31.setBorder(Rectangle.NO_BORDER); 
 colstable2.addCell(cell31); 
 
 
 if(billingitem.equalsIgnoreCase("CSR")){
   Paragraph paras12=new Paragraph(" "+csrnoTxt.getText(),font6);paras12.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT); 
     paras12.setAlignment(Paragraph.ALIGN_CENTER); 
      PdfPCell cell2=new PdfPCell(paras12); cell2.setBorder(Rectangle.NO_BORDER);  
  colstable2.addCell(cell2); 
 }
 else{
   Paragraph paras12=new Paragraph(" "+contractnoTxt.getText(),font6);paras12.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT); 
     paras12.setAlignment(Paragraph.ALIGN_CENTER); 
      PdfPCell cell2=new PdfPCell(paras12); cell2.setBorder(Rectangle.NO_BORDER);  
  colstable2.addCell(cell2); 
 }
 
    Paragraph paras13=new Paragraph("",font6);paras13.getFont().setStyle(Font.LAYOUT_RIGHT_TO_LEFT); 
     paras13.setAlignment(Paragraph.ALIGN_CENTER); 
      PdfPCell cell3=new PdfPCell(paras13); cell3.setBorder(Rectangle.NO_BORDER);  
  colstable2.addCell(cell3); 
  
  
 Paragraph paras131=new Paragraph("P.O. BOX "+addressTxt.getText()+",",font6);paras131.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT); 
   paras131.setAlignment(Paragraph.ALIGN_CENTER);   
    PdfPCell cell23=new PdfPCell(paras131);cell23.setBorder(Rectangle.NO_BORDER);  
   colstable2.addCell(cell23 ); 
   
    PdfPCell cell324=new PdfPCell( new Phrase(" "));    cell324.setBorder(Rectangle.NO_BORDER);
   cell324.setColspan(2); colstable2.addCell(cell324); 

   Paragraph paras15=new Paragraph(townTxt.getText()+".",font6);paras15.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT); 
           paras15.setAlignment(Paragraph.ALIGN_CENTER);   
 PdfPCell cell5=new PdfPCell(paras15);cell5.setBorder(Rectangle.NO_BORDER);  
  colstable2.addCell(cell5); 
  
   colstable2.addCell(cell324); 
 
   receiptDoc.add(colstable2);  //receiptDoc.add(space);
 
 
   float[] colswidth;      com.itextpdf.text.pdf.PdfPTable subjreportTable = null;
  float[] colswidth2=new float[]{15f};
   PdfPTable colstable=new PdfPTable(colswidth2);
     colstable .setWidthPercentage(100);//colstable.setHorizontalAlignment();
      colstable.setHorizontalAlignment(Element.ALIGN_CENTER); 
      
  receiptDoc.add(space); //receiptDoc.add(space); 
  
  Paragraph paras145=new Paragraph("Please raise an invoice for the below listed items with indicated values:",font6);
paras145.setAlignment(Paragraph.ALIGN_LEFT);   receiptDoc.add(paras145);  receiptDoc.add(space);
  double vat = 0;
if(billingitem.equalsIgnoreCase("CSR")){
  //receiptDoc.add(separatorlable3);  
       colswidth = new float[]{2f,4f,9f,3f,4f,4f  };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
          subjreportTable .setWidthPercentage(100);  
 
           Paragraph Paras=new Paragraph("No",font2);
            Paras.getFont().setStyle(Font.BOLD);
            
    Paragraph Paras1=new Paragraph("PART NO.",font2);
            Paras1.getFont().setStyle(Font.BOLD);
            
              Paragraph Paras12=new Paragraph("DESCRIPTION",font2);
            Paras12.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras21=new Paragraph("QTY.",font2);
            Paras21.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras22=new Paragraph("UNIT COST",font2);
            Paras22.getFont().setStyle(Font.BOLD);
            
      Paragraph Paras11=new Paragraph("TOTAL",font2);
            Paras11.getFont().setStyle(Font.BOLD);
   subjreportTable.addCell(Paras);   subjreportTable.addCell(Paras1);
  subjreportTable.addCell(Paras12);  subjreportTable.addCell(Paras21);  
  subjreportTable.addCell(Paras22);subjreportTable.addCell(Paras11); 
   
 String PARTNO, description,qty ;
 double unitcost,subcost, totalexcluvat=0; String currency = null;

 
 String getCSritemsql="SELECT SUPPLY_REQUESTS.CSRNO, CSRITEMS.PARTNO,CSRITEMS.DESCRIPTION, CSRITEMS.QTY,CSRITEMS.UNITCOST,SUPPLY_REQUESTS.currency,"
         + "  SUPPLY_REQUESTS.VAT FROM SUPPLY_REQUESTS "
         + "LEFT JOIN CSRITEMS ON CSRITEMS.CSRNO=SUPPLY_REQUESTS.CSRNO WHERE SUPPLY_REQUESTS.CSRNO='"+csrnoTxt.getText()+"'";
 com.mysql.jdbc.PreparedStatement pst4=(com.mysql.jdbc.PreparedStatement) connectDb.prepareStatement(getCSritemsql);
            ResultSet rst4=pst4.executeQuery();  int g=0;
    PdfPCell cellspanner=new PdfPCell();
 cellspanner.setColspan(4); //cellspanner.setBorder(Rectangle.NO_BORDER);  
while(rst4.next()){
  currency=rst4.getString(6);
    subcost=rst4.getDouble(4)* rst4.getDouble(5);
      totalexcluvat=totalexcluvat+subcost;
      vat=rst4.getDouble(7);
Paragraph Paras110=new Paragraph(String.valueOf(g+1),font5);
Paras11.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
 
          Paragraph Paras211=new Paragraph(rst4.getString(2),font5);
          Paras211.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
             Paragraph Paras2111=new Paragraph(rst4.getString(3),font5);
                Paras2111.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras330=new Paragraph( String.valueOf(rst4.getDouble(4)),font5);
                Paras330.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras341=new Paragraph(rst4.getString(6)+". "+String.valueOf(formatter.format(rst4.getDouble(5))),font5);
                Paras341.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras311=new Paragraph(rst4.getString(6)+". "+String.valueOf(formatter.format(subcost)),font5);
                Paras311.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
          subjreportTable.addCell(Paras110);subjreportTable.addCell(Paras211);
            subjreportTable.addCell(Paras2111); subjreportTable.addCell(Paras330);
         subjreportTable.addCell(Paras341);      subjreportTable.addCell(Paras311);
            }
   PdfPCell cellspanner2=new PdfPCell();
 cellspanner2.setColspan(6);  subjreportTable.addCell(cellspanner2);   
 
   subjreportTable.addCell(cellspanner); 
     Paragraph Paras311=new Paragraph("Sub Total "+". ",font8);
    Paras311.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras311);  
       
    Paragraph Paras312=new Paragraph(currency+". "+formatter.format(totalexcluvat),font8);
    Paras312.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras312);   
       
   subjreportTable.addCell(cellspanner);  // subjreportTable.addCell(space);   subjreportTable.addCell(space); 
  // subjreportTable.addCell(space);
   
    Paragraph Paras313=new Paragraph("VAT "+vat+" %",font8);
    Paras313.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras313);  
       
  double vatamount=  totalexcluvat*(Double.parseDouble(String.valueOf(vat))/100);
       Paragraph Paras314=new Paragraph(currency+". "+formatter.format(vatamount)+" ",font8);
    Paras314.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras314); 
   subjreportTable.addCell(cellspanner);  //subjreportTable.addCell(space);    subjreportTable.addCell(space);   subjreportTable.addCell(space); 
  
  
     Paragraph Paras315=new Paragraph("Billing Total: ",font8);
    Paras315.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras315); 
       
        Paragraph Paras316=new Paragraph(currency +". "+(formatter.format(totalexcluvat+vatamount)),font8);
    Paras316.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras316);
       receiptDoc.add(subjreportTable);
   }else{

  colswidth = new float[]{9f,10f,6f,6f,7f  };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
          subjreportTable .setWidthPercentage(100);  
          
  Paragraph Paras=new Paragraph("CONTRACT No",font2);
  Paras.getFont().setStyle(Font.BOLD); subjreportTable.addCell(Paras);
            
  Paragraph Paras12=new Paragraph("DESCRIPTION",font2);
  Paras12.getFont().setStyle(Font.BOLD); subjreportTable.addCell(Paras12);
  
 Paragraph Paras1=new Paragraph("BILL FROM",font2);
 Paras1.getFont().setStyle(Font.BOLD); subjreportTable.addCell(Paras1);
            
  Paragraph Paras21=new Paragraph("BILL TO",font2);
   Paras21.getFont().setStyle(Font.BOLD); subjreportTable.addCell(Paras21);
            
  Paragraph Paras22=new Paragraph("AMOUNT",font2);
    Paras22.getFont().setStyle(Font.BOLD);   subjreportTable.addCell(Paras22);      

Paragraph Paras110=new Paragraph(contractnoTxt.getText(),font5);
Paras110.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT); subjreportTable.addCell(Paras110);
 
Paragraph Paras211=new Paragraph(contdescripTxt.getText(),font5);
 Paras211.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);subjreportTable.addCell(Paras211);
         
   Paragraph Paras2111=new Paragraph((billfromTxt.getText()),font5);
   Paras2111.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT); subjreportTable.addCell(Paras2111);
                
  Paragraph Paras330=new Paragraph(( billtoTxt.getText()),font5);
   Paras330.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);subjreportTable.addCell(Paras330);
                
   Paragraph Paras341=new Paragraph(contamountviewTxt.getText(),font5);
  Paras341.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);subjreportTable.addCell(Paras341);
                
 PdfPCell cellspanner2=new PdfPCell();
 cellspanner2.setColspan(5);  subjreportTable.addCell(cellspanner2);   
 
     PdfPCell cellspanner=new PdfPCell();
 cellspanner.setColspan(3);cellspanner.setBorder(Rectangle.NO_BORDER); 
   subjreportTable.addCell(cellspanner);
   
     Paragraph Paras311=new Paragraph("Sub Total "+". ",font8);
    Paras311.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras311);  
       
    Paragraph Paras312=new Paragraph(contamountviewTxt.getText(),font8);
    Paras312.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras312);   
     
     subjreportTable.addCell(cellspanner);   
   Paragraph Paras313=new Paragraph("VAT "+vatTxt.getText()+" %",font8); 
    Paras313.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras313);  
       
  double vatamount=  Double.parseDouble(String.valueOf(contbillamountTxt.getText()))*(Double.parseDouble(String.valueOf(vatTxt.getText()))/100);
       Paragraph Paras314=new Paragraph(currency+". "+formatter.format(vatamount)+" ",font8);
      Paras314.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras314); 
       
     //subjreportTable.addCell(space);    subjreportTable.addCell(space);   subjreportTable.addCell(space); 
  
   subjreportTable.addCell(cellspanner);
     Paragraph Paras315=new Paragraph("Billing Total: ",font8);
    Paras315.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras315); 
       
   Paragraph Paras316=new Paragraph(currency +". "+(formatter.format(Double.parseDouble(String.valueOf(contbillamountTxt.getText()))+vatamount)),font8);
    Paras316.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
       subjreportTable.addCell(Paras316);
       
       receiptDoc.add(subjreportTable);      
       }
 
  receiptDoc.add( space);
   receiptDoc.add( space);
    
   Paragraph NOTE3=new Paragraph(" REQUESTED BY ",font2);
            NOTE3.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE3);    receiptDoc.add( space);
 Paragraph cell281=new Paragraph(new Phrase("Name:______________________________ Signature:_________________________ Date:_________________________ ",font2) ); 
   receiptDoc.add(cell281);

    receiptDoc.add(space); //receiptDoc.add(space);receiptDoc.add(space);
   
    Paragraph NOTE=new Paragraph(" NOTE: ",font2);
            NOTE.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE);
     
      Paragraph NOTE4=new Paragraph("1.Service or delivery for above listed items has been completed.",font4);
     NOTE4.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE4); 
     
        Paragraph NOTE41=new Paragraph("2.Approval is therefore given by above signed to proceed with invoicing.",font4);
     NOTE41.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE41); 
     
      Paragraph NOTE11=new Paragraph("BILLING TERMS: ",font2);
            NOTE11.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE11);
     
    Paragraph NOTE2=new Paragraph("PRICE: As listed above in "+currency+". Grand Total is inclusive of "+vat+" % VAT. ",font4);
     NOTE2.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE2); 
     
   Paragraph NOTE5=new Paragraph("PAYMENT: Within 30 days after service delivery and upon receipt of invoice.",font4);
     NOTE5.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE5); 
    
     receiptDoc.add(space);  receiptDoc.add(space);
  
 //receiptDoc.add(space); receiptDoc.add(space);receiptDoc.add(space);
   
   receiptDoc.add(space);
     
   Paragraph user=new Paragraph("TECHNICAL SERVICE DEPARTMENT                        System Generated By: "+currentuser+" on "+printdate,font4);
            user.setAlignment(Paragraph.ALIGN_CENTER);
            receiptDoc.add(user);
            receiptDoc.close();
          Executable.openDocument(directory);     
        }      
        }   catch (SQLException | DocumentException | FileNotFoundException ex) {
            Logger.getLogger(Calltracking.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
              Logger.getLogger(Calltracking.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTxt;
    private javax.swing.JTextField amountviewTxt;
    private javax.swing.JTextField billamountTxt;
    private javax.swing.JTextField billfromTxt;
    private javax.swing.JPanel billingtableholderPanel;
    public javax.swing.JPanel billrequestPanel;
    private javax.swing.JTextField billtoTxt;
    private javax.swing.JTextField clientnameTxt;
    private javax.swing.JTextField contactTxt;
    private javax.swing.JTextField contamountviewTxt;
    private javax.swing.JTextField contbillamountTxt;
    private javax.swing.JTextField contdescripTxt;
    private javax.swing.JPanel contractholderPanel;
    private javax.swing.JTextField contractnoTxt;
    private javax.swing.JPanel contractrequestsPanel;
    private javax.swing.JTable contractsbillTable;
    private javax.swing.JTextField contvalueTxt;
    private javax.swing.JTextField csrdateTxt;
    private javax.swing.JTextField csrdescripTxt;
    private javax.swing.JPanel csrholderPanel;
    private javax.swing.JTextField csrnoTxt;
    private javax.swing.JPanel csrrequestsPanel;
    private javax.swing.JTable csrrequestsTable;
    private javax.swing.JTextField csrvalueTxt;
    private javax.swing.JPanel detailsholderPanel;
    private javax.swing.JTextField endTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private javax.swing.JTextField periodTxt;
    private javax.swing.JTextField phoneTxt;
    private javax.swing.JTextField podateTxt;
    private javax.swing.JTextField ponumTxt;
    private javax.swing.JTextField refnoTxt;
    private javax.swing.JTextField requestnoTxt;
    private javax.swing.JTextField startTxt;
    private javax.swing.JTextField totalbilableTxt;
    private javax.swing.JTextField totalbilledTxt;
    private javax.swing.JTextField townTxt;
    private javax.swing.JTextField vatTxt;
    private javax.swing.JTextField vatTxt2;
    // End of variables declaration//GEN-END:variables
}
