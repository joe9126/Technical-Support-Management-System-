
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
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Border;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
public class Mileage extends javax.swing.JFrame {

    /**
     * Creates new form Mileage
     */
   java.sql.Date servicedate;   String staffname;String printedby;        Document   receiptDoc; Image companylogo;
             String currentuser;String usertype;
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
    Home access=new Home();String directory; 
    DecimalFormat df1 =new DecimalFormat("#,###.00");
 String[] cols3={"","No.","SERVICE NO","NAME","DAY","DATE","FROM","TO","CLIENT","CONTRACT","LOCATION","KM ","KM CLAIM","PSV FARE","ACCOM","MEALS","PETTIES","LAUND.","OTHERS","STATUS","CLAIM DATE","CLAIM NO"};       
 DefaultTableModel  claimstableModel=new DefaultTableModel(cols3,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    };   
    public Mileage() {
        initComponents();
       claimsTable.getTableHeader().setReorderingAllowed(false);
           claimsTable.getColumnModel().getColumn(0).setMinWidth(2);
             claimsTable.getColumnModel().getColumn(1).setMinWidth(4);
               claimsTable.getColumnModel().getColumn(2).setMinWidth(2);
                 claimsTable.getColumnModel().getColumn(3).setMinWidth(3);
                   claimsTable.getColumnModel().getColumn(4).setMinWidth(2);
                     claimsTable.getColumnModel().getColumn(5).setMinWidth(2);
                       claimsTable.getColumnModel().getColumn(6).setMinWidth(6);
      claimsTable.getColumnModel().getColumn(7).setMinWidth(6);
             claimsTable.getColumnModel().getColumn(8).setMinWidth(2);
               claimsTable.getColumnModel().getColumn(9).setMinWidth(3);
                 claimsTable.getColumnModel().getColumn(10).setMinWidth(3);
                   claimsTable.getColumnModel().getColumn(11).setMinWidth(5);
                     claimsTable.getColumnModel().getColumn(12).setMinWidth(5);
                       claimsTable.getColumnModel().getColumn(13).setMinWidth(4); 
 claimsTable.getColumnModel().getColumn(14).setMinWidth(4);
             claimsTable.getColumnModel().getColumn(15).setMinWidth(4);
               claimsTable.getColumnModel().getColumn(16).setMinWidth(5);
               // claimsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
               
    TableColumn tc =claimsTable.getColumnModel().getColumn(0);
     tc.setCellEditor(claimsTable.getDefaultEditor(Boolean.class));
     tc.setCellRenderer(claimsTable.getDefaultRenderer(Boolean.class));             
     }

    public void getClaims(){
   System.out.println("USertype for claims is "+usertype);
       try {
 String getclaimSql;
 if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
     
 if(!searchstaffTxt.getText().equalsIgnoreCase("SEARCH STAFF NAME")&&searchstaffTxt.getText().trim().length()!=0){
 getclaimSql="SELECT SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,  " +
"        CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.ACCOMOD,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,   " +
" CLAIMS.PETTIES,CLAIMS.LAUNDRY,CLAIMS.OTHERS,CLAIMS.STATUS,CLAIMS.DATE_CLAIMED,SERVICE.LOCATION,STAFF.STAFFNO,staff.STAFFNAME,CLAIMS.CLAIMNO,CALLS.CONTRACT_NO "
         + " FROM SERVICE  " +
"  LEFT JOIN CALLS ON SERVICE.CALL_NO=CALLS.CALL_NO   " +
"          LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO  " +
"          LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO  " +
"           LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO  " +
"          LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "+
" WHERE STAFF.STAFFNO LIKE (SELECT STAFFNO FROM STAFF WHERE STAFFNAME='"+searchstaffTxt.getText()+"') AND CLAIMS.STATUS='UNCLAIMED'"
         + "   ORDER BY SERVICE.SERVICE_DATE DESC"
         ;
 
 }
 else if(!searchclaimnoTxt.getText().equalsIgnoreCase("SEARCH CLAIM NUMBER")&&searchclaimnoTxt.getText().trim().length()!=0){
  getclaimSql="SELECT SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,  " +
"        CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.ACCOMOD,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,   " +
" CLAIMS.PETTIES,CLAIMS.LAUNDRY,CLAIMS.OTHERS,CLAIMS.STATUS,CLAIMS.DATE_CLAIMED,SERVICE.LOCATION,STAFF.STAFFNO,staff.STAFFNAME,CLAIMS.CLAIMNO,CALLS.CONTRACT_NO "
          + " FROM SERVICE  " +
"  LEFT JOIN CALLS ON SERVICE.CALL_NO=CALLS.CALL_NO   " +
"          LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO  " +
"          LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO  " +
"           LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO  " +
"          LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "+
" WHERE CLAIMS.CLAIMNO ='"+searchclaimnoTxt.getText()+"'"
         + " ORDER BY SERVICE.SERVICE_DATE DESC"
         ;
     
 }
 
 else{
 getclaimSql="SELECT SERVICE.SERVICE_NO, SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,  " +
"        CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.ACCOMOD,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,   " +
" CLAIMS.PETTIES,CLAIMS.LAUNDRY,CLAIMS.OTHERS,CLAIMS.STATUS,CLAIMS.DATE_CLAIMED,SERVICE.LOCATION,STAFF.STAFFNO,staff.STAFFNAME,CLAIMS.CLAIMNO,CALLS.CONTRACT_NO "
         + "  FROM SERVICE  " +
"  LEFT JOIN CALLS ON SERVICE.CALL_NO=CALLS.CALL_NO   " +
"          LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO  " +
"          LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO  " +
"           LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO  " +
"          LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO WHERE CLAIMS.STATUS='UNCLAIMED' "+
 " ORDER BY SERVICE.SERVICE_DATE DESC";
        
 }
 
 }else{
if(servicedate!=null){
 getclaimSql="SELECT SERVICE.SERVICE_NO, SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,  " +
"        CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.ACCOMOD,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,   " +
" CLAIMS.PETTIES,CLAIMS.LAUNDRY,CLAIMS.OTHERS,CLAIMS.STATUS,CLAIMS.DATE_CLAIMED,SERVICE.LOCATION,STAFF.STAFFNO,staff.STAFFNAME,CLAIMS.CLAIMNO ,CALLS.CONTRACT_NO "
         + " FROM SERVICE  " +
"  LEFT JOIN CALLS ON SERVICE.CALL_NO=CALLS.CALL_NO   " +
"          LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO  " +
"          LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO  " +
"           LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO  " +
"          LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO   " +
"          WHERE STAFF.STAFFNO ='"+currentuser+"' AND SERVICE.SERVICE_DATE='"+servicedate+"'  AND CLAIMS.STATUS='UNCLAIMED'  "
         + "ORDER BY SERVICE.SERVICE_DATE DESC";

} 
 else if(!searchclaimnoTxt.getText().equalsIgnoreCase("SEARCH CLAIM NUMBER")&&searchclaimnoTxt.getText().trim().length()!=0){
  getclaimSql="SELECT SERVICE.SERVICE_NO,SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,  " +
"        CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.ACCOMOD,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,   " +
" CLAIMS.PETTIES,CLAIMS.LAUNDRY,CLAIMS.OTHERS,CLAIMS.STATUS,CLAIMS.DATE_CLAIMED,SERVICE.LOCATION,STAFF.STAFFNO,staff.STAFFNAME,CLAIMS.CLAIMNO,CALLS.CONTRACT_NO "
          + " FROM SERVICE  " +
"  LEFT JOIN CALLS ON SERVICE.CALL_NO=CALLS.CALL_NO   " +
"          LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO  " +
"          LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO  " +
"           LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO  " +
"          LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "+
" WHERE CLAIMS.CLAIMNO LIKE '"+searchstaffTxt.getText()+"%'"
         + "   ORDER BY SERVICE.SERVICE_DATE DESC"
         ;
     
 }
else{
 getclaimSql="SELECT SERVICE.SERVICE_NO, SERVICE.SERVICE_DATE,SERVICE.FROM2,SERVICE.TO2,CONTRACTS.CONTRACT_NO,CLIENTS.CLIENTNAME,  " +
"        CLAIMS.KM,CLAIMS.PSVFARE,CLAIMS.ACCOMOD,CLAIMS.BFAST,CLAIMS.LUNCH,CLAIMS.DINNER,   " +
" CLAIMS.PETTIES,CLAIMS.LAUNDRY,CLAIMS.OTHERS,CLAIMS.STATUS,CLAIMS.DATE_CLAIMED,SERVICE.LOCATION,STAFF.STAFFNO,staff.STAFFNAME,CLAIMS.CLAIMNO,CALLS.CONTRACT_NO "
         + "  FROM SERVICE  " +
"  LEFT JOIN CALLS ON SERVICE.CALL_NO=CALLS.CALL_NO   " +
"          LEFT JOIN CLAIMS ON SERVICE.SERVICE_NO=CLAIMS.SERVICE_NO  " +
"          LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO=CONTRACTS.CONTRACT_NO  " +
"           LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO  " +
"          LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO   " +
"          WHERE STAFF.STAFFNO ='"+currentuser+"' AND CLAIMS.STATUS='UNCLAIMED'  ORDER BY SERVICE.SERVICE_DATE DESC";

}    
 }
  int g=claimsTable.getRowCount();
    for(int f=0;f<g;f++){
    claimstableModel.removeRow(0);
    }
 System.out.println("Used query "+getclaimSql);
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,   access.username,  access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getclaimSql);
            ResultSet rst=pst.executeQuery(); int i=0;
            
             int firstkm=0,firstkmrate=0,extrakmrate=0; 
  double kmtrav=0; double kmclaim=0,totalkmclaim=0;  String stafflevel;
  double extrakm=0,extrakmclaim=0; double totalclaim=0;
   String getsettings;   PreparedStatement pst4;   ResultSet rst4;
             while(rst.next()){
   java.sql.Date day=rst.getDate(2);
     staffname=rst.getString(20);  stafflevel=rst.getString(19); kmtrav=rst.getDouble(7);
     int mealstotal;
if(day==null){}
else{
System.out.println("SQL db date :"+day); 
      Calendar calendar=Calendar.getInstance();
      calendar.setTime(day); 
   int dayname1=  calendar.get(Calendar.DAY_OF_WEEK);
   System.out.println("Day number "+dayname1);
   String dayname=" ";
    
    switch (dayname1){
        case 1: dayname="Sunday";break;
         case 2: dayname="Monday"; break; case 3: dayname="Tuesday"; break; case 4: dayname="Wednesday"; break; case 5: dayname="Thursday";break; 
          case 6: dayname="Friday"; break; case 7: dayname="Saturday"; break; default: break;
    }
         
 if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
 currentuser=rst.getString(19);
  }
 
 getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL=(SELECT STAFFLEVEL FROM STAFF WHERE STAFFNO= '"+currentuser+"')";   
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
    
    System.out.println("Dinner "+rst.getInt(12)+" Lunch "+rst.getInt(11)+" Breakfast "+rst.getInt(10)+" ");
    mealstotal= rst.getInt(10)+rst.getInt(11)+rst.getInt(12);
  System.out.println("Service client: "+rst.getString(6));
   System.out.println("Service contract: "+rst.getString(22));
 if(rst.getString(6)!=null){
 totalclaim=totalkmclaim+mealstotal+rst.getInt(13)+rst.getInt(14)+rst.getInt(15)+rst.getInt(9)+rst.getInt(8);
if(totalclaim>0){
 claimstableModel.addRow(new Object[]{false,i+1,rst.getString(1),staffname,dayname,sdf.format(rst.getDate(2)),rst.getString(3),rst.getString(4),rst.getString(6),rst.getString(22),rst.getString(18)
  ,rst.getDouble(7),(totalkmclaim),rst.getInt(8),rst.getInt(9),mealstotal,rst.getInt(13),rst.getInt(14),rst.getInt(15),rst.getString(16),rst.getString(17),rst.getString(21)}); 
  } 
 }
 else{
 String getclient="SELECT SUPPLY_REQUESTS.CSRNO, CLIENTS.CLIENTNAME FROM SUPPLY_REQUESTS LEFT JOIN CLIENTS "
         + "ON SUPPLY_REQUESTS.CLIENTNO=CLIENTS.CLIENT_NO WHERE SUPPLY_REQUESTS.CSRNO='"+rst.getString(22)+"'";
  PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(getclient);
   ResultSet rst2=pst2.executeQuery();
   while(rst2.next()){
       totalclaim=totalkmclaim+mealstotal+rst.getInt(13)+rst.getInt(14)+rst.getInt(15)+rst.getInt(9)+rst.getInt(8);
 if(totalclaim>0){
       claimstableModel.addRow(new Object[]{false,i+1,rst.getString(1),staffname,dayname,sdf.format(rst.getDate(2)),rst.getString(3),rst.getString(4),rst2.getString(2),rst.getString(22),rst.getString(18)
  ,rst.getDouble(7),(totalkmclaim),rst.getInt(8),rst.getInt(9),mealstotal,rst.getInt(13),rst.getInt(14),rst.getInt(15),rst.getString(16),rst.getString(17),rst.getString(21)}); 
  }
   }
 }
 i++; 
 
}
         }
             
int j=claimsTable.getRowCount(); Double kmclaims,fare,accom,meals,petties,laund,other,total, grandtotal=0.0;
    for(int f=0;f<j;f++){
 kmclaims=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 12)));
 fare=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 13)));
 accom=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 14)));
 meals=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 15)));
 petties=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 16)));
 laund=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 17)));
 other=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 18)));
 total=kmclaims+fare+accom+meals+petties+laund+other;
 grandtotal=grandtotal+total;
    }
    claimvalueTxt.setText("KES. "+df1.format(grandtotal)); 
             if(i<1){
//JOptionPane.showMessageDialog(null,"no record found" );
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

        mileagePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        claimsTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        searchstaffPanel = new javax.swing.JPanel();
        searchstaffTxt = new javax.swing.JTextField();
        searchdatePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        servicedatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        searchdateTxt = new javax.swing.JTextField();
        searchclaimnoTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        claimvalueTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mileagePanel.setBackground(new java.awt.Color(153, 0, 153));
        mileagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MILEAGE CLAIMS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "STAFF CLAIMS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 153))); // NOI18N

        claimsTable.setModel(claimstableModel);
        claimsTable.setFillsViewportHeight(true);
        claimsTable.setGridColor(new java.awt.Color(153, 0, 153));
        claimsTable.setRowHeight(25);
        claimsTable.setRowMargin(2);
        claimsTable.setSelectionBackground(new java.awt.Color(153, 0, 153));
        claimsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                claimsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(claimsTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton2.setText("PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        searchstaffPanel.setBackground(new java.awt.Color(153, 0, 153));
        searchstaffPanel.setLayout(new java.awt.CardLayout());

        searchstaffTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchstaffTxt.setForeground(new java.awt.Color(153, 153, 153));
        searchstaffTxt.setText("SEARCH STAFF NAME");
        searchstaffTxt.setCaretColor(new java.awt.Color(204, 0, 204));
        searchstaffTxt.setSelectionColor(new java.awt.Color(153, 0, 153));
        searchstaffTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchstaffTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchstaffTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchstaffTxtMouseExited(evt);
            }
        });
        searchstaffTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchstaffTxtKeyReleased(evt);
            }
        });
        searchstaffPanel.add(searchstaffTxt, "card2");

        searchdatePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Search Service Date:");

        servicedatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servicedatePicker1ActionPerformed(evt);
            }
        });

        searchdateTxt.setEditable(false);
        searchdateTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        searchdateTxt.setForeground(new java.awt.Color(153, 0, 153));

        javax.swing.GroupLayout searchdatePanelLayout = new javax.swing.GroupLayout(searchdatePanel);
        searchdatePanel.setLayout(searchdatePanelLayout);
        searchdatePanelLayout.setHorizontalGroup(
            searchdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
            .addGroup(searchdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(searchdatePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(servicedatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(searchdateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        searchdatePanelLayout.setVerticalGroup(
            searchdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(searchdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(searchdatePanelLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addGroup(searchdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(servicedatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchdateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        searchstaffPanel.add(searchdatePanel, "card3");

        searchclaimnoTxt.setForeground(new java.awt.Color(153, 153, 153));
        searchclaimnoTxt.setText("Search Claim Number");
        searchclaimnoTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchclaimnoTxtMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchclaimnoTxtMouseExited(evt);
            }
        });
        searchclaimnoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchclaimnoTxtActionPerformed(evt);
            }
        });
        searchclaimnoTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchclaimnoTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchclaimnoTxtKeyTyped(evt);
            }
        });

        jButton1.setText("REMOVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("TOTAL CLAIM:");

        claimvalueTxt.setEditable(false);
        claimvalueTxt.setBackground(new java.awt.Color(0, 0, 0));
        claimvalueTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        claimvalueTxt.setForeground(new java.awt.Color(0, 255, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(searchstaffPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(claimvalueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(160, 160, 160)
                        .addComponent(searchclaimnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(34, 34, 34)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchstaffPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchclaimnoTxt)
                        .addComponent(jButton2)
                        .addComponent(jButton1)
                        .addComponent(jLabel1)
                        .addComponent(claimvalueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mileagePanelLayout = new javax.swing.GroupLayout(mileagePanel);
        mileagePanel.setLayout(mileagePanelLayout);
        mileagePanelLayout.setHorizontalGroup(
            mileagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mileagePanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );
        mileagePanelLayout.setVerticalGroup(
            mileagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mileagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mileagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchstaffTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchstaffTxtKeyReleased
     getClaims();
    }//GEN-LAST:event_searchstaffTxtKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
  int year=LocalDate.now().getYear();   
  
 String claimno= "TS"+year+"/"+loannumber+"" ;
      int rows=claimsTable.getRowCount();
      if(rows<1){
      JOptionPane.showMessageDialog(null, "No mileage records to print!","No Records",JOptionPane.ERROR_MESSAGE);
      }else{
              FileOutputStream filename=null; 
     receiptDoc=new Document(PageSize.A4.rotate()); 
      JFileChooser filesaver=new JFileChooser();
       filesaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int option=filesaver.showSaveDialog(mileagePanel);
          if(option==JFileChooser.APPROVE_OPTION){
    try {
        String   dir=filesaver.getSelectedFile().toString();
        String staffname1=claimsTable.getValueAt(0, 3).toString();
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
          String printdate=new SimpleDateFormat("dd MMM, yyyy").format(Calendar.getInstance().getTime()); 
            String printdate2=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());  
        if(!searchstaffTxt.getText().equalsIgnoreCase("SEARCH STAFF NAME")&&searchstaffTxt.getText().trim().length()!=0)     { 
                directory=dir+"/"+searchstaffTxt.getText()+" MILEAGE CLAIM PRINTED "+printdate+" .pdf";
                filename = new FileOutputStream(directory);
            }
        else if(!searchclaimnoTxt.getText().equalsIgnoreCase("SEARCH CLAIM NUMBER")&&searchclaimnoTxt.getText().trim().length()!=0){
      directory=dir+"/"+staffname1+"_MILEAGE_CLAIM_PRINTED_"+printdate+"_.pdf";
              filename = new FileOutputStream(directory);   claimno=searchclaimnoTxt.getText();
        }
         else if(servicedate!=null)     {
                directory=   dir+"/"+staffname+" MILEAGE CLAIM  PRINTED "+printdate+" .pdf";
                filename = new FileOutputStream(directory);
            }
        else   {
                directory=dir+"/ GENERAL MILEAGE PRINTED "+printdate+" .pdf";
                filename = new FileOutputStream(directory);
            }
           
            
            PdfWriter writer=PdfWriter.getInstance(receiptDoc ,filename);
            receiptDoc.open();
            Paragraph space=new Paragraph("          ");
            com.itextpdf.text.Font font1=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,22,Font.BOLD);
            com.itextpdf.text.Font font2=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,10,Font.BOLD);
            com.itextpdf.text.Font font3=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
            com.itextpdf.text.Font font4=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.HELVETICA,7,Font.LAYOUT_LEFT_TO_RIGHT);
            com.itextpdf.text.Font font5=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.HELVETICA,13,Font.LAYOUT_LEFT_TO_RIGHT);
             com.itextpdf.text.Font font6=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.HELVETICA,5,Font.LAYOUT_LEFT_TO_RIGHT);
             Paragraph user=new Paragraph("Printed By: "+printedby,font4);
            user.setAlignment(Paragraph.ALIGN_RIGHT);
          //  receiptDoc.add(user);
            Paragraph date=new Paragraph( "  Printed: "+printdate+" By: "+printedby,font4);
            date.setAlignment(Paragraph.ALIGN_RIGHT);
            receiptDoc.add(date);
            
          com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(  companylogo , null); 
image.setAbsolutePosition(40, 500);
         image.scaleToFit(280,100);
 writer.getDirectContent().addImage(image);  receiptDoc.add(space);  // receiptDoc.add(space); 
  
            //Paragraph schname=new Paragraph( sacconame,font4); schname.setAlignment(Paragraph.ALIGN_CENTER);
              String separator="____________________________________________________________________________________________________";
            Paragraph separatorlable=new Paragraph(separator,font2);
               separatorlable.setAlignment(Paragraph.ALIGN_CENTER);
              //  receiptDoc.add(separatorlable);
   String separator2=" ________________________________________________________________________________________________________________________________________________________";
            Paragraph separatorlable3=new Paragraph(separator2,font2);
               separatorlable.setAlignment(Paragraph.ALIGN_CENTER);
               
            Paragraph receiptlabel=new Paragraph("TECHNICAL SERVICE DEPARTMENT     ",font1);
            receiptlabel.setAlignment(Paragraph.ALIGN_CENTER);  receiptDoc.add(receiptlabel);
             receiptDoc.add(separatorlable3); 
        //receiptDoc.add(space);
         
   float[] colswidth;      com.itextpdf.text.pdf.PdfPTable subjreportTable = null;
   
   float[] colswidth2=new float[]{10f,10f,10f};
   PdfPTable colstable=new PdfPTable(colswidth2);
     colstable .setWidthPercentage(80);
     
   PdfPCell cell1=new PdfPCell(new Phrase("Full Name: "+staffname ,font5));cell1.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell1); 
    
     PdfPCell cell2=new PdfPCell(new Phrase("Claim Date: "+printdate,font5));cell2.setBorder(Rectangle.NO_BORDER);  
     colstable.addCell(cell2); 
    
   
     PdfPCell cell3=new PdfPCell(new Phrase("Claim Number: "+claimno,font5));cell3.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell3); 
   
       Paragraph receiptlabel2=new Paragraph("  MILEAGE EXPENSES CLAIM FORM  ",font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);  //receiptDoc.add(space);
                receiptDoc.add(colstable); receiptDoc.add(space);
                
        colswidth = new float[]{4f,4f,3f,3f,10f,7f,6f,3f,5f,3f,4f,4f,4f,4f,5f,5f };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
          subjreportTable .setWidthPercentage(108);       
                
        if(!searchstaffTxt.getText().equalsIgnoreCase("SEARCH STAFF NAME")&&searchstaffTxt.getText().trim().length()!=0){
        //  receiptDoc.add(separatorlable3);  //receiptDoc.add(space); receiptDoc.add(space);
                
               Paragraph receiptlabel3=new Paragraph(" STAFF NO:"+currentuser+"    NAME: "+staffname,font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
               // receiptDoc.add(receiptlabel3);
                 // receiptDoc.add(separatorlable3); receiptDoc.add(space); //receiptDoc.add(space);
                  
     
                
            }
           else if(servicedate!=null) {
              receiptDoc.add(separatorlable3); // receiptDoc.add(space); //receiptDoc.add(space);
                
               Paragraph receiptlabel3=new Paragraph(" STAFF NO:"+currentuser+"    NAME: "+staffname,font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
               // receiptDoc.add(receiptlabel3);
                
              Paragraph receiptlabel4=new Paragraph("SERVICE DATE:  "+sdf.format(servicedate)+"  ",font3);
                receiptlabel4.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel4);                
                  receiptDoc.add(separatorlable3); receiptDoc.add(space); //receiptDoc.add(space);
              }
            else if(servicedate==null&& !usertype.equalsIgnoreCase("ADMINISTRATOR")) {
             receiptDoc.add(separatorlable3);receiptDoc.add(space);
               Paragraph receiptlabel3=new Paragraph(" STAFF NO:"+currentuser+"    NAME: "+staffname,font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
               // receiptDoc.add(receiptlabel3);      
       
             }
            else  if(!searchclaimnoTxt.getText().equalsIgnoreCase("SEARCH CLAIM NUMBER")&&searchclaimnoTxt.getText().trim().length()!=0){
             Paragraph receiptlabel3=new Paragraph(" STAFF NO:"+currentuser+"    NAME: "+staffname,font3);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER);
               // receiptDoc.add(receiptlabel3);
                 // receiptDoc.add(separatorlable3); receiptDoc.add(space); //receiptDoc.add(space);
              }
            
         /*   else if(usertype.equalsIgnoreCase("ADMINISTRATOR")){
              receiptDoc.add(separatorlable3);      
                
    colswidth = new float[]{4f,4f,3f,3f,10f,9f,3f,4f,3f,4f,4f,4f,4f,4f,4f };
    
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
                subjreportTable .setWidthPercentage(108);  
            }*/
           //   receiptDoc.add(space); receiptDoc.add(space);
          
           Paragraph Paras=new Paragraph("Day",font2);
            Paras.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras1=new Paragraph("Date",font2);
            Paras1.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras2=new Paragraph("From",font2);
            Paras2.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras21=new Paragraph("To",font2);
            Paras21.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras33=new Paragraph("Client",font2);
            Paras33.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras330=new Paragraph("Contract",font2);
            Paras330.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras34=new Paragraph("Location",font2);
            Paras34.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras35=new Paragraph("Km",font2);
            Paras35.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras36=new Paragraph("Km claim",font2);
            Paras36.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras42=new Paragraph("Fare",font2);
            Paras42.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras40=new Paragraph("Accomod",font2);
            Paras40.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras37=new Paragraph("Meals",font2);
            Paras37.getFont().setStyle(Font.BOLD);
            /*
            Paragraph Paras38=new Paragraph("Lunch",font2);
            Paras38.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras39=new Paragraph("Dinner",font2);
            Paras39.getFont().setStyle(Font.BOLD);
            */
            Paragraph Paras41=new Paragraph("Petties",font2);
            Paras41.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras46=new Paragraph("Laund.",font2);
            Paras46.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras43=new Paragraph("Others",font2);
            Paras43.getFont().setStyle(Font.BOLD);
            
             Paragraph Paras44=new Paragraph("Total",font2);
            Paras44.getFont().setStyle(Font.BOLD);
            
              Paragraph Paras45=new Paragraph("Staff",font2);
            Paras45.getFont().setStyle(Font.BOLD);
            
          //
          subjreportTable.addCell(Paras);
           subjreportTable.addCell(Paras1);
           subjreportTable.addCell(Paras2);
            subjreportTable.addCell(Paras21);  
            subjreportTable.addCell(Paras33);
            subjreportTable.addCell(Paras330);
            subjreportTable.addCell(Paras34);
            subjreportTable.addCell(Paras35);
            subjreportTable.addCell(Paras36);
            subjreportTable.addCell(Paras42);
             subjreportTable.addCell(Paras40); 
             subjreportTable.addCell(Paras37);
           // subjreportTable.addCell(Paras38);subjreportTable.addCell(Paras39);
            subjreportTable.addCell(Paras41);subjreportTable.addCell(Paras46); subjreportTable.addCell(Paras43);
            subjreportTable.addCell(Paras44);
     /*       
         if(clientnameTxt.getText().trim().length()!=0&&callfromdate!=null&&calltodate!=null)     {
              subjreportTable.addCell(Paras43);        
            }
 else if(clientnameTxt.getText().trim().length()!=0){
           subjreportTable.addCell(Paras43);    
            }
   else if(callfromdate!=null&&calltodate!=null&&staffnoTxt.getText().isEmpty()){
               subjreportTable.addCell(Paras43);    
            }
            */
     
     String updateclaimsql; PreparedStatement claimpst;
            String day, sdate,service2date,from,to,client,contractno,location,fare,accomod,meals,petties, laundry,others ;
            double km,kmclaim;
            double totals; double grandtotal=0; String serviceno;
            int n=claimsTable.getRowCount();
            for(int z=0;z<n;z++){
                serviceno= String.valueOf(claimsTable.getValueAt(z , 2));
                day= String.valueOf(claimsTable.getValueAt(z , 4));
                service2date=String.valueOf(claimsTable.getValueAt(z ,5));
                from=String.valueOf(claimsTable.getValueAt(z ,6));
                to= (String.valueOf(claimsTable.getValueAt(z, 7)));
                client=String.valueOf(claimsTable.getValueAt(z ,8));
                contractno=String.valueOf(claimsTable.getValueAt(z ,9));
               
                location=String.valueOf(claimsTable.getValueAt(z ,10));
                km=Double.parseDouble(String.valueOf(claimsTable.getValueAt(z ,11)));
                kmclaim=Double.parseDouble(String.valueOf(claimsTable.getValueAt(z ,12)));
                fare=String.valueOf(claimsTable.getValueAt(z ,13));
                accomod=String.valueOf(claimsTable.getValueAt(z ,14));
                meals=String.valueOf(claimsTable.getValueAt(z ,15));
                petties=String.valueOf(claimsTable.getValueAt(z ,16));
                laundry=String.valueOf(claimsTable.getValueAt(z ,17));
               others  =String.valueOf(claimsTable.getValueAt(z ,18));
              totals= (kmclaim)+Double.valueOf(fare)+Double.valueOf(accomod)+Double.valueOf(meals)
                      +Double.valueOf(petties)+Double.valueOf(laundry)+Double.valueOf(others);
              grandtotal=grandtotal+totals;
              
 if(!searchstaffTxt.getText().equalsIgnoreCase("SEARCH STAFF NAME")&&searchstaffTxt.getText().trim().length()!=0){
    updateclaimsql="UPDATE CLAIMS SET STATUS='CLAIMED', DATE_CLAIMED='"+printdate2+"', CLAIMNO='"+claimno+"' WHERE SERVICE_NO='"+serviceno+"'";  
      claimpst=(com.mysql.jdbc.PreparedStatement) connectDb.prepareStatement(updateclaimsql);
      claimpst.executeUpdate();
 }else  if(!searchclaimnoTxt.getText().equalsIgnoreCase("SEARCH CLAIM NUMBER")&&searchclaimnoTxt.getText().trim().length()!=0){
 updateclaimsql="UPDATE CLAIMS SET STATUS='CLAIMED', DATE_CLAIMED='"+printdate2+"' WHERE SERVICE_NO='"+serviceno+"'";  
      claimpst=(com.mysql.jdbc.PreparedStatement) connectDb.prepareStatement(updateclaimsql);
      claimpst.executeUpdate();
 
 }
 if(usertype.equalsIgnoreCase("USER")){
 updateclaimsql="UPDATE CLAIMS SET STATUS='CLAIMED', DATE_CLAIMED='"+printdate2+"', CLAIMNO='"+claimno+"' WHERE SERVICE_NO='"+serviceno+"'";  
      claimpst=(com.mysql.jdbc.PreparedStatement) connectDb.prepareStatement(updateclaimsql);
      claimpst.executeUpdate();
 }
  
      
               /* Paragraph Paras789=new Paragraph(String.valueOf(z+1));
                Paras789.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                */
                Paragraph Paras11=new Paragraph(day,font4);
                Paras11.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras211=new Paragraph(service2date,font4);
                Paras211.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras2111=new Paragraph(from,font4);
                Paras2111.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras331=new Paragraph(to,font4);
                Paras331.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras341=new Paragraph(client,font4);
                Paras341.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras3421=new Paragraph(contractno,font4);
                Paras3421.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                
                Paragraph Paras351=new Paragraph(location,font4);
                Paras351.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras361=new Paragraph(String.valueOf(km),font4);
                Paras361.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras371=new Paragraph(String.valueOf((int)Math.round(kmclaim)),font4);
                Paras371.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras381=new Paragraph((fare),font4);
                Paras381.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras391=new Paragraph(df1.format(Integer.parseInt(accomod)),font4);
                Paras391.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras401=new Paragraph(df1.format(Integer.parseInt(meals)),font4);
                Paras401.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras411=new Paragraph(df1.format(Integer.parseInt(petties)),font4);
                Paras411.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras421=new Paragraph(df1.format(Integer.parseInt(laundry)),font4);
                Paras421.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras422=new Paragraph(df1.format(Integer.parseInt(others)),font4);
                Paras422.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras423=new Paragraph(String.valueOf(df1.format(totals)),font4);
                Paras423.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
               // subjreportTable.addCell(Paras789);
                subjreportTable.addCell(Paras11);subjreportTable.addCell(Paras211);
                subjreportTable.addCell(Paras2111);   subjreportTable.addCell(Paras331);subjreportTable.addCell(Paras341);subjreportTable.addCell(Paras3421);
                subjreportTable.addCell(Paras351);subjreportTable.addCell(Paras361);subjreportTable.addCell(Paras371);
                subjreportTable.addCell(Paras381);subjreportTable.addCell(Paras391);subjreportTable.addCell(Paras401);
                subjreportTable.addCell(Paras411);subjreportTable.addCell(Paras421); subjreportTable.addCell(Paras422);
                subjreportTable.addCell(Paras423);
 
  }
             PdfPCell cell5=new PdfPCell(new Phrase("  " ,font2));cell5.setBorder(Rectangle.NO_BORDER);  
             cell5.setColspan(14); 
   subjreportTable.addCell(cell5);  
      
              Paragraph Paras48=new Paragraph("Total KES.",font2);
            Paras48.getFont().setStyle(Font.BOLD); 
      subjreportTable.addCell(Paras48);  
      
   Paragraph Paras49=new Paragraph(""+df1.format(grandtotal),font2);
            Paras49.getFont().setStyle(Font.BOLD);    
      subjreportTable.addCell(Paras49); 
  
     EnglishNumberToWords   obj = new EnglishNumberToWords  ();
         System.out.println("*** " + obj.convert((int) grandtotal));
 
   PdfPCell cell53=new PdfPCell(new Phrase("  " ,font2));cell53.setBorder(Rectangle.NO_BORDER);  
 cell53.setColspan(9); 
 subjreportTable.addCell(cell53); 
             
 PdfPCell cell456=new PdfPCell(new Phrase("Amount in Words ",font2));cell456.setColspan(3); 
  subjreportTable.addCell(cell456); 
  
 Paragraph t1=new Paragraph("    "+obj.convert((int) grandtotal)+".",font2);
t1.setAlignment(Paragraph.ALIGN_RIGHT); 
 PdfPCell cell457=new PdfPCell(  );cell457.setColspan(4); 
 cell457.addElement(t1); 
 subjreportTable.addCell(cell457); 
 
 receiptDoc.add( subjreportTable);      
 
     
 float[] colswidth5=new float[]{9f,5f,5f,5f,5f,5f,5f,5f,5f};     
 PdfPTable allowanceguideTable=new PdfPTable(colswidth5);
 allowanceguideTable.setWidthPercentage(70); 
 allowanceguideTable.setHorizontalAlignment(Element.ALIGN_LEFT);    
    
 Paragraph x1=new Paragraph(" Receipt State   " ,font2);
  Paragraph x0=new Paragraph("Town" ,font2);
  Paragraph x2=new Paragraph(" B/Fast   " ,font2);
   Paragraph x3=new Paragraph(" Lunch   " ,font2);
    Paragraph x4=new Paragraph(" Dinner   " ,font2);
     Paragraph x5=new Paragraph(" Accomod. " ,font2);
      Paragraph x6=new Paragraph(" Petties   " ,font2);
       Paragraph x7=new Paragraph(" Laund/Wk   " ,font2);
          Paragraph x8=new Paragraph("Others" ,font2);
  allowanceguideTable.addCell(x1);  allowanceguideTable.addCell(x0); allowanceguideTable.addCell(x2);
   allowanceguideTable.addCell(x3); allowanceguideTable.addCell(x4);
    allowanceguideTable.addCell(x5); allowanceguideTable.addCell(x6);
     allowanceguideTable.addCell(x7); allowanceguideTable.addCell(x8);
     
     
     
  int bfrecept=0, bfnorecept=0, lunchrecept=0, lunchnorecept=0, dinnerrecept=0, dinnernorecept=0, accomodrecept=0,accomodnorecept=0,
          laundry2=0,petties2=0,others2=0;       
 int firstkm,firstkmrate,extrakmrate; String getsettings=""; String town;
  
 if(!searchclaimnoTxt.getText().equalsIgnoreCase("SEARCH CLAIM NUMBER")&&searchclaimnoTxt.getText().trim().length()!=0){
  getsettings=" SELECT * FROM MILEAGESETTINGS WHERE LOWER(STAFFLEVEL)= (SELECT STAFFLEVEL FROM STAFF WHERE LOWER(STAFFNAME)=lower('"+staffname1+"'))";  
 }
 else  if( usertype.equalsIgnoreCase("ADMINISTRATOR")){
  getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL=(SELECT STAFFLEVEL FROM STAFF WHERE STAFFNAME='"+searchstaffTxt.getText()+"')";  
 }

 else if(!usertype.equalsIgnoreCase("ADMINISTRATOR")){
  getsettings=" SELECT * FROM MILEAGESETTINGS WHERE STAFFLEVEL= (SELECT STAFFLEVEL FROM STAFF WHERE STAFFNO='"+currentuser +"')";
 }
 System.out.println("QUERY USED "+getsettings);
    PreparedStatement pst4=(PreparedStatement)connectDb.prepareStatement(getsettings);
    ResultSet rst4=pst4.executeQuery();  
    Paragraph z0,z1,z2,z3,z4,z5,z6,z7,z8,z9;
 Paragraph w0,w1,w2,w3,w4,w5,w6,w7,w8,w9;

    while(rst4.next()){
        town=rst4.getString(2);
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
        laundry2=  rst4.getInt(14);
         petties2=  rst4.getInt(15 );
         others2=rst4.getInt(16);
  
  z1=new Paragraph(" Without Receipts   " ,font6); 
   z0=new Paragraph(town ,font6); 
  z2=new Paragraph(String.valueOf(bfnorecept) ,font6);
   z3=new Paragraph(String.valueOf(lunchnorecept) ,font6);
  z4=new Paragraph(String.valueOf(dinnernorecept) ,font6);
  z5=new Paragraph(String.valueOf(accomodnorecept) ,font6);
    z8=new Paragraph(String.valueOf(petties2) ,font6);
  z6=new Paragraph(String.valueOf("") ,font6);
   z9=new Paragraph(String.valueOf(others2) ,font6);
  z7=new Paragraph(String.valueOf("") ,font6);

 
  
allowanceguideTable.addCell( z1);     allowanceguideTable.addCell(z0);   allowanceguideTable.addCell(z2); 
  allowanceguideTable.addCell(z3);  allowanceguideTable.addCell(z4); 
  allowanceguideTable.addCell(z5); 
   allowanceguideTable.addCell(z8);  allowanceguideTable.addCell(z6); 
    allowanceguideTable.addCell(z9);  //allowanceguideTable.addCell(String.valueOf(" ")); 
    /*
    allowanceguideTable.addCell(String.valueOf(firstkmrate)); allowanceguideTable.addCell(String.valueOf(extrakmrate)); */
    
     w1=new Paragraph(" With Receipts   " ,font6); 
        w0=new Paragraph(town ,font6); 
  w2=new Paragraph(String.valueOf(bfrecept) ,font6);
   w3=new Paragraph(String.valueOf(lunchrecept) ,font6);
  w4=new Paragraph(String.valueOf(dinnerrecept) ,font6);
  w5=new Paragraph(String.valueOf(accomodrecept) ,font6);
   w7=new Paragraph(String.valueOf("") ,font6);
  w6=new Paragraph(String.valueOf(laundry2) ,font6);
  w8=new Paragraph(String.valueOf("") ,font6);
 // w9=new Paragraph(String.valueOf("") ,font2);
  
     allowanceguideTable.addCell(w1);    allowanceguideTable.addCell(w0); allowanceguideTable.addCell(w2); 
  allowanceguideTable.addCell(w3);  allowanceguideTable.addCell(w4); 
    allowanceguideTable.addCell(w5); 
   allowanceguideTable.addCell(w7);  allowanceguideTable.addCell(w6); 
    allowanceguideTable.addCell(w8);
    }
  // receiptDoc.add(space);  
   
     Paragraph guideheader=new Paragraph("Staff Allowance Guideline " ,font2);
    receiptDoc.add(guideheader);  receiptDoc.add(allowanceguideTable); //receiptDoc.add(space);
      
    
    float[] colswidth6=new float[]{15f,15f,15f};     
 PdfPTable signatureTable=new PdfPTable(colswidth6);
 signatureTable.setWidthPercentage(108); 
 signatureTable.setHorizontalAlignment(Element.ALIGN_LEFT);  

 
    Paragraph sign1=new Paragraph("Staff Sign.:__________________ Date:__________________" ,font4);
   sign1.setAlignment(Paragraph.ALIGN_LEFT);
    PdfPCell cell23=new PdfPCell(sign1);cell23.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell23); 
   
     /*Paragraph sign2=new Paragraph(" " ,font4);
     sign2.setAlignment(Paragraph.ALIGN_LEFT);
    PdfPCell cell24=new PdfPCell(sign2);cell24.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell24); */
   
   /* PdfPCell cell29=new PdfPCell(new Phrase(" "));cell29.setBorder(Rectangle.NO_BORDER); 
   signatureTable.addCell(cell29); */
   
  // signatureTable.addCell(cell29);     signatureTable.addCell(cell29);    signatureTable.addCell(cell29); 
   
    Paragraph sign3=new Paragraph("F.S.M Sign.:__________________ Date:__________________" ,font4);
   sign3.setAlignment(Paragraph.ALIGN_LEFT);
    PdfPCell cell25=new PdfPCell(sign3);cell25.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell25); 
   
    /* Paragraph sign4=new Paragraph(" " ,font4);
     sign4.setAlignment(Paragraph.ALIGN_LEFT);
      PdfPCell cell89=new PdfPCell(sign4);cell89.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell89);*/
     

  Paragraph sign5=new Paragraph("G.M Sign.:__________________ Date:__________________" ,font4);
   sign5.setAlignment(Paragraph.ALIGN_LEFT); 
    PdfPCell cell27=new PdfPCell(sign5);cell27.setBorder(Rectangle.NO_BORDER);
    signatureTable.addCell(cell27); 
    receiptDoc.add(space); 
   receiptDoc.add(signatureTable); 
    
 receiptDoc.close();
            
            
        }   catch (SQLException | DocumentException | FileNotFoundException ex) {
            Logger.getLogger(Calltracking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Executable.openDocument(directory);
        
        java.io.File fileout;
        fileout = new File(directory);
       com.lowagie.tools.Executable.printDocument(fileout);
       getClaims();
    }   catch (IOException ex) {
              Logger.getLogger(Calltracking.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          }
      }
//  bnnbnb      
  String updateclaimstatus="";      
        
      
    }//GEN-LAST:event_jButton2ActionPerformed
private static final String[] specialNames = {
        ""," thousand"," million"," billion"," trillion",
        " quadrillion", " quintillion"
    };
    
    private static final String[] tensNames = {
        "",   " ten",  " twenty",  " thirty",
        " forty",  " fifty",  " sixty",   " seventy",  " eighty",  " ninety"
    };
    
    private static final String[] numNames = {
        "",  " one",  " two",  " three",   " four",   " five",   " six",
        " seven",  " eight",  " nine",   " ten",  " eleven",  " twelve",
        " thirteen",  " fourteen",   " fifteen",   " sixteen",  " seventeen",   " eighteen",   " nineteen"
    };
    
      private String convertLessThanOneThousand(int number) {
        String current;
        
        if (number % 100 < 20){
            current = numNames[number % 100];
            number /= 100;
        }
        else {
            current = numNames[number % 10];
            number /= 10;
            
            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " hundred" + current;
    }
      
       public String convert(int number) {

        if (number == 0) { return "zero"; }
        
        String prefix = "";
        
        if (number < 0) {
            number = -number;
            prefix = "negative";
        }
        
        String current = "";
        int place = 0;
        
        do {
            int n = number % 1000;
            if (n != 0){
                String s = convertLessThanOneThousand(n);
                current = s + specialNames[place] + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);
        
        return (prefix + current).trim();
    }
    private void searchstaffTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchstaffTxtMouseClicked
    searchstaffTxt.setText(null);
     searchstaffTxt.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_searchstaffTxtMouseClicked

    private void servicedatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servicedatePicker1ActionPerformed
        servicedate=new java.sql.Date(servicedatePicker1.getDate().getTime());
  searchdateTxt.setText(sdf.format(servicedate));
  getClaims();
    }//GEN-LAST:event_servicedatePicker1ActionPerformed

    private void searchclaimnoTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchclaimnoTxtMouseClicked
        searchclaimnoTxt.setText(null);
     searchclaimnoTxt.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_searchclaimnoTxtMouseClicked

    private void searchclaimnoTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchclaimnoTxtMouseExited
      if(searchclaimnoTxt.getText().isEmpty()){
       searchclaimnoTxt.setText("Search Claim Number");
      }
    }//GEN-LAST:event_searchclaimnoTxtMouseExited

    private void searchstaffTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchstaffTxtMouseEntered
    
        searchstaffTxt.setForeground(Color.magenta);
    }//GEN-LAST:event_searchstaffTxtMouseEntered

    private void searchstaffTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchstaffTxtMouseExited
     searchstaffTxt.setForeground(Color.LIGHT_GRAY);
  if( searchstaffTxt.getText().isEmpty()){
    searchstaffTxt.setText("Search Staff Name");
  }
    }//GEN-LAST:event_searchstaffTxtMouseExited

    private void searchclaimnoTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchclaimnoTxtKeyTyped
    
    }//GEN-LAST:event_searchclaimnoTxtKeyTyped

    private void searchclaimnoTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchclaimnoTxtKeyReleased
        getClaims();
    }//GEN-LAST:event_searchclaimnoTxtKeyReleased

    private void claimsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_claimsTableMouseClicked
     // int row=claimsTable.getSelectedRow();
      
    }//GEN-LAST:event_claimsTableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 TableModel model1=claimsTable.getModel(); String selectedClaims=null;
      for(int i=0; i<model1.getRowCount();i++){
       if((Boolean) model1.getValueAt(i,0)){
     selectedClaims=claimsTable.getModel().getValueAt(i,0).toString();
       }
     }
  if(selectedClaims==null){
  JOptionPane.showMessageDialog(rootPane,"Please select claims from the list below to remove!","No Selection",JOptionPane.WARNING_MESSAGE);
      }else{    
        int choice=JOptionPane.showConfirmDialog(null, "Do you want to remove selected claims?","Confirm",JOptionPane.YES_NO_OPTION);
if(choice==JOptionPane.YES_OPTION){
 TableModel model2=claimsTable.getModel();  String unlockclaimsql;
    for(int i=0; i<model2.getRowCount();i++){
                        if((Boolean) model2.getValueAt(i,0)){
      claimstableModel.removeRow(i);
  } 
    } 
    
   int j=claimsTable.getRowCount(); Double kmclaims,fare,accom,meals,petties,laund,other,total, grandtotal=0.0;
    for(int f=0;f<j;f++){
 kmclaims=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 12)));
 fare=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 13)));
 accom=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 14)));
 meals=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 15)));
 petties=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 16)));
 laund=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 17)));
 other=Double.parseDouble(String.valueOf(claimsTable.getValueAt(f, 18)));
 total=kmclaims+fare+accom+meals+petties+laund+other;
 grandtotal=grandtotal+total;
    }
    claimvalueTxt.setText("KES. "+df1.format(grandtotal)); 
}
  }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchclaimnoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchclaimnoTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchclaimnoTxtActionPerformed

    /**
     * @param args the command line arguments
     */
    
    

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable claimsTable;
    private javax.swing.JTextField claimvalueTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JPanel mileagePanel;
    private javax.swing.JTextField searchclaimnoTxt;
    public javax.swing.JPanel searchdatePanel;
    private javax.swing.JTextField searchdateTxt;
    public javax.swing.JPanel searchstaffPanel;
    public javax.swing.JTextField searchstaffTxt;
    public org.jdesktop.swingx.JXDatePicker servicedatePicker1;
    // End of variables declaration//GEN-END:variables
}
