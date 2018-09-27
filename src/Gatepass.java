
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
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JOE
 */
public class Gatepass extends javax.swing.JFrame {
    int equipNum;
    String currentuser,clientname; Home access=new Home();   String broughtby, broughtby2,clientno;
 DefaultComboBoxModel  techniciancomboModel=new DefaultComboBoxModel();
 SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
  String[] cols3={"No.","MODEL","DESCRIPTION","SERIAL NO.","FAULT","WARRANTY STATUS"};   
    Document   receiptDoc;
  DefaultTableModel equiptableModel=new DefaultTableModel(cols3,0);
  
   String[] cols2={"No.","GIN NO","DATE IN","BROUGHT BY","PHONE","CLIENT","MODEL","DESCRIPTION","SERIAL NO.","FAULT","WARRANTY","DATE OUT","TAKEN BY"};   
   DefaultTableModel  gatepasstableModel=new DefaultTableModel(cols2,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    }; 
  
    java.sql.Date servicedate;   String staffname;String printedby;       Image companylogo;
 String usertype;String directory;
  String idno,phone;
  /* {
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    }; */
 
 /**
     * Creates new form Gatepass
     */
    public Gatepass() {
        initComponents();
    techniciancomboModel.addElement("--Select Technician--");
  int b=   equipsTable.getRowCount();
     equiptableModel.addRow(new Object[]{b+1,"","","","",""});
 //equipsTable.editCellAt(0, 1);
  equipsTable.setSurrendersFocusOnKeystroke(true);   
        
//        equipsTable.getEditorComponent().requestFocus();
        
  equipsTable.getColumnModel().getColumn(0).setMinWidth(1); 
  equipsTable.getColumnModel().getColumn(1).setMinWidth(4); 
  equipsTable.getColumnModel().getColumn(2).setMinWidth(4); 
  equipsTable.getColumnModel().getColumn(3).setMinWidth(4); 
  equipsTable.getColumnModel().getColumn(4).setMinWidth(7); 
  equipsTable.getColumnModel().getColumn(5).setMinWidth(4); 
 
   
        String cols[]={"ACTIVE","EXPIRED","NONE"};
     JComboBox generateBox=new JComboBox(cols);
        equipsTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(generateBox)); 
        equipsTable.setColumnSelectionAllowed(true);
         equipsTable.getTableHeader().setReorderingAllowed(false);
         
gatepassTable.getTableHeader().setReorderingAllowed(false); 
         InputMap im=equipsTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
        KeyStroke enterkey=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
        im.put(enterkey, enterkey);
    
      /*  TableColumn tc =equipsTable.getColumnModel().getColumn(0);
     tc.setCellEditor(equipsTable.getDefaultEditor(Boolean.class));
     tc.setCellRenderer(equipsTable.getDefaultRenderer(Boolean.class));*/
    }
    
 public void getEquipment(){
        try {
   String getequipsql="SELECT GATEPASS.GINNO, GATEPASS.DATEIN,GATEPASS.BROUGHTBY, GATEPASS.PHONE,CLIENTS.CLIENTNAME,GATEPASS.MODEL,GATEPASS.DESCRIPTION, "
                    + "GATEPASS.SERIAL_NO,GATEPASS.FAULT,GATEPASS.WARRANTY,GATEPASS.DATEOUT,GATEPASS.TAKENBY FROM GATEPASS "
                    + "LEFT JOIN CLIENTS ON GATEPASS.CLIENTNO=CLIENTS.CLIENT_NO ORDER BY GATEPASS.DATEIN ASC";
      int row=gatepassTable.getRowCount(); 
      for(int g=0;g<row;g++){
      gatepasstableModel.removeRow(0);
      }
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getequipsql);
            ResultSet rst=pst.executeQuery(); int i=0; equipNum=0;
            while(rst.next()){
                gatepasstableModel.addRow(new Object[]{equipNum+1,rst.getString(1),sdf.format(rst.getDate(2)),rst.getString(3),rst.getString(4),rst.getString(5),
                rst.getString(6),rst.getString(7),rst.getString(8),rst.getString(9),rst.getString(10),rst.getString(11),rst.getString(12)}); 
               equipNum++;  
            }
        } catch (SQLException ex) {
            Logger.getLogger(Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }
 }   
    
    
    public boolean vaidCheck()
{
if(equipsTable.getCellEditor()!=null){
equipsTable.getCellEditor().stopCellEditing();
}
for(int i=0;i< equipsTable.getRowCount();i++)
{
for (int j=0;j<equipsTable.getColumnCount();j++)
{
    try{
 String om=equipsTable.getValueAt(i,j).toString();
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gatepassPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        clientnameTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        contpersonTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        phoneTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        broughtbyholderPanel = new javax.swing.JPanel();
        clientPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        customernameTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        idnoTxt = new javax.swing.JTextField();
        technicianPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        technicianTxt = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        equipsTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        dateinTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        gatepassnoTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        receivedbyTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        gatepassTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        gatepassPanel.setBackground(new java.awt.Color(153, 0, 153));
        gatepassPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EQUIPMENT GATEPASS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CLIENT DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel1.setText("CLIENT NAME:");

        clientnameTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clientnameTxtMouseExited(evt);
            }
        });
        clientnameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientnameTxtKeyReleased(evt);
            }
        });

        jLabel2.setText("CONTACT PERSON:");

        jLabel3.setText("PHONE NO:");

        jLabel4.setText("EMAIL:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clientnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(contpersonTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(clientnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contpersonTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BROUGHT BY", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel5.setText("BROUGHT BY:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Client", "Technician" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        broughtbyholderPanel.setBackground(new java.awt.Color(255, 255, 255));
        broughtbyholderPanel.setLayout(new java.awt.CardLayout());

        clientPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("NAME:");

        jLabel7.setText("PHONE:");

        jLabel8.setText("ID NO:");

        javax.swing.GroupLayout clientPanelLayout = new javax.swing.GroupLayout(clientPanel);
        clientPanel.setLayout(clientPanelLayout);
        clientPanelLayout.setHorizontalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(67, 67, 67)
                        .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                            .addComponent(customernameTxt)))
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(72, 72, 72)
                        .addComponent(idnoTxt)))
                .addContainerGap())
        );
        clientPanelLayout.setVerticalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(customernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(idnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        broughtbyholderPanel.add(clientPanel, "card2");

        technicianPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("NAME:");

        jComboBox2.setModel(techniciancomboModel);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        technicianTxt.setEditable(false);

        javax.swing.GroupLayout technicianPanelLayout = new javax.swing.GroupLayout(technicianPanel);
        technicianPanel.setLayout(technicianPanelLayout);
        technicianPanelLayout.setHorizontalGroup(
            technicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(technicianPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(77, 77, 77)
                .addGroup(technicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(technicianTxt)
                    .addComponent(jComboBox2, 0, 194, Short.MAX_VALUE))
                .addContainerGap())
        );
        technicianPanelLayout.setVerticalGroup(
            technicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(technicianPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(technicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addComponent(technicianTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        broughtbyholderPanel.add(technicianPanel, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5)
                        .addGap(44, 44, 44)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(broughtbyholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(broughtbyholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GATEPASS IN DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Equipment Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        equipsTable.setFont(new java.awt.Font("Trebuchet MS", 0, 13)); // NOI18N
        equipsTable.setModel(equiptableModel);
        equipsTable.setCellSelectionEnabled(true);
        equipsTable.setFillsViewportHeight(true);
        equipsTable.setGridColor(new java.awt.Color(153, 0, 153));
        equipsTable.setRowHeight(30);
        equipsTable.setRowMargin(2);
        equipsTable.setSelectionBackground(new java.awt.Color(153, 0, 153));
        equipsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                equipsTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(equipsTable);

        jLabel10.setText("DATE IN:");

        dateinTxt.setEditable(false);

        jLabel11.setText("GATEPASS IN NO:");

        gatepassnoTxt.setEditable(false);

        jLabel12.setText("RECEIVED BY:");

        receivedbyTxt.setEditable(false);

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

        jButton3.setText("PRINT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateinTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gatepassnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(receivedbyTxt)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(53, 53, 53)
                .addComponent(jButton2)
                .addGap(52, 52, 52)
                .addComponent(jButton3)
                .addGap(34, 34, 34))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(dateinTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(gatepassnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(receivedbyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(21, 21, 21))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EQUIPMENT IN STORE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        gatepassTable.setModel(gatepasstableModel);
        gatepassTable.setFillsViewportHeight(true);
        gatepassTable.setGridColor(new java.awt.Color(153, 0, 153));
        gatepassTable.setRowHeight(25);
        gatepassTable.setRowMargin(2);
        gatepassTable.setSelectionBackground(new java.awt.Color(153, 0, 153));
        gatepassTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gatepassTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(gatepassTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout gatepassPanelLayout = new javax.swing.GroupLayout(gatepassPanel);
        gatepassPanel.setLayout(gatepassPanelLayout);
        gatepassPanelLayout.setHorizontalGroup(
            gatepassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gatepassPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        gatepassPanelLayout.setVerticalGroup(
            gatepassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gatepassPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(gatepassPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
  broughtby=""+jComboBox1.getSelectedItem();
   if(broughtby.equalsIgnoreCase("Technician")){
   broughtbyholderPanel.removeAll();
   broughtbyholderPanel.repaint();
   broughtbyholderPanel.revalidate();
   broughtbyholderPanel.add(technicianPanel);
   
   getTechnicians();
   
   }else{
     broughtbyholderPanel.removeAll();
   broughtbyholderPanel.repaint();
   broughtbyholderPanel.revalidate();
    broughtbyholderPanel.add(clientPanel);
   }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void equipsTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_equipsTableKeyPressed
       if(evt.getKeyCode()==10){
       int rowindex=equipsTable.getSelectedRow();
       int colindex=equipsTable.getSelectedColumn();
       equipsTable.editCellAt(rowindex+1, 2);
       evt.consume();
//     studregTable.setColumnSelectionInterval(rowindex, colindex+1);
  if(vaidCheck())
{
int row=equipsTable.getRowCount();
  equiptableModel.addRow(new Object[]{ row+1,"","","","","" });
   equipsTable.editCellAt(rowindex+1, 1);
}
else
{
JOptionPane.showMessageDialog(null,"Please fill in all the empty fields !","Alert",JOptionPane.WARNING_MESSAGE);
}
      // studregTable.setSurrendersFocusOnKeystroke(true);
//        studregTable.getEditorComponent().requestFocus();
       }
    }//GEN-LAST:event_equipsTableKeyPressed
public void getGIN(){
    int year=LocalDate.now().getYear();    
          Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
  gatepassnoTxt.setText(String.valueOf("GIN"+year+"-"+loannumber)); 
   LocalDate todaydate=LocalDate.now();
 String  callreqDate=(DateTimeFormatter.ofPattern("dd MMM,yyyy").format(todaydate));  
  dateinTxt.setText(callreqDate);      
   receivedbyTxt.setText(currentuser);

}
    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
     
    }//GEN-LAST:event_jPanel4MouseEntered

    private void clientnameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientnameTxtKeyReleased
    try {
            String getcontractsql="SELECT  CLIENTNAME, CONT_PERSON, MOBILE,EMAIL,CLIENT_NO  FROM CLIENTS  WHERE  CLIENTNAME like " +
" '"+ clientnameTxt.getText()+"%' ORDER BY CLIENTNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
          while(rst.next()){
 clientname=rst.getString(1);
 contpersonTxt.setText(rst.getString(2)); 
 phoneTxt.setText(rst.getString(3));
 emailTxt.setText( (rst.getString(4))); 
 clientno=rst.getString(5);
      i++;  
            }
          if(i<1){
              clientname=null;
  contpersonTxt.setText(null); 
 phoneTxt.setText(null);
 emailTxt.setText(null);   
 
  Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
   clientno=   String.valueOf(loannumber);    
          }
     }  catch (SQLException ex) {
            Logger.getLogger(Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_clientnameTxtKeyReleased

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
     String technician=""+jComboBox2.getSelectedItem();
        if(!technician.equalsIgnoreCase("--Select Technician--")){
        technicianTxt.setText(technician); 
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void clientnameTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientnameTxtMouseExited
       clientnameTxt.setText(clientname);
    }//GEN-LAST:event_clientnameTxtMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(clientnameTxt.getText().isEmpty()){
JOptionPane.showMessageDialog(null, "Please enter client name!","Client name required",JOptionPane.WARNING_MESSAGE);
}else if(contpersonTxt.getText().isEmpty()){
JOptionPane.showMessageDialog(null, "Please enter contact person!","Contact person required",JOptionPane.WARNING_MESSAGE);
}
else if(vaidCheck()){
 if(broughtby==null){
 JOptionPane.showMessageDialog(null, "Please select Brought by!","Brought By required",JOptionPane.WARNING_MESSAGE);
 }else if(broughtby.equalsIgnoreCase("Technician")){
 if(technicianTxt.getText().isEmpty()){
 JOptionPane.showMessageDialog(null, "Please select technican name!","Technician name required",JOptionPane.WARNING_MESSAGE);
  }else{
 saveGatepass();
 }
 }
 else  if(broughtby.equalsIgnoreCase("Client")){
 if(customernameTxt.getText().isEmpty()){
  JOptionPane.showMessageDialog(null, "Please customer name!","Customer name required",JOptionPane.WARNING_MESSAGE);
  }else if(idnoTxt.getText().isEmpty()){
    JOptionPane.showMessageDialog(null, "Please enter customer ID number!","ID Number required",JOptionPane.WARNING_MESSAGE);
   }
 else if(phoneTxt.getText().isEmpty()){
    JOptionPane.showMessageDialog(null, "Please enter customer phone number!","Phone Number required",JOptionPane.WARNING_MESSAGE);
   }else{
 saveGatepass();
 }
 }
    

}else{
JOptionPane.showMessageDialog(null, "Please enter equipment details in the table!","Equipment details required",JOptionPane.WARNING_MESSAGE);

}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      printGatepass();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void gatepassTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gatepassTableMouseClicked
        try {
            int row=gatepassTable.getSelectedRow();
            String getGIN=String.valueOf(gatepassTable.getValueAt(row, 1));
        int r=equipsTable.getRowCount();
        for(int f=0;f<r;f++){
        equiptableModel.removeRow(0);
        }
            String getequipdetails="SELECT MODEL, DESCRIPTION,SERIAL_NO,FAULT,WARRANTY, DATEIN,GINNO,RECEIVEDBY,BROUGHTBY,IDNO,PHONE,CLIENTS.CLIENTNAME,"
                    + "CLIENTS.CONT_PERSON,CLIENTS.MOBILE,CLIENTS.EMAIL FROM GATEPASS "
                    + "LEFT JOIN CLIENTS ON GATEPASS.CLIENTNO=CLIENTS.CLIENT_NO "
                    + "WHERE GINNO='"+getGIN+"'";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getequipdetails);
       ResultSet rst=pst.executeQuery(); int h=0;
       while(rst.next()){
       equiptableModel.addRow(new Object[]{h+1,rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5)});
    customernameTxt.setText(rst.getString(9)); idnoTxt.setText(rst.getString(10));  jTextField6.setText(rst.getString(11)); 
    idno=rst.getString(10); phone=rst.getString(11); broughtby2=rst.getString(9);
    dateinTxt.setText(sdf.format(rst.getDate(6))); gatepassnoTxt.setText(rst.getString(7)); receivedbyTxt.setText(rst.getString(8));
       clientnameTxt.setText(rst.getString(12)); contpersonTxt.setText(rst.getString(13)); phoneTxt.setText(rst.getString(14));
     emailTxt.setText(rst.getString(15));
       h++; }
   
       
        } catch (SQLException ex) {
            Logger.getLogger(Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_gatepassTableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
   
    }//GEN-LAST:event_jButton2ActionPerformed
public void saveGatepass(){

int choice=JOptionPane.showConfirmDialog(null, "Do you want save Gatepass In details for "+clientnameTxt.getText()+"?","Confirm",JOptionPane.YES_NO_OPTION);
if(choice==JOptionPane.YES_OPTION){
  try {
            String savegatepasssql="INSERT INTO GATEPASS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(savegatepasssql);
    String serial, model, description, warranty, fault;  
     LocalDate todaydate=LocalDate.now();
 String  callreqDate=(DateTimeFormatter.ofPattern("dd MMM,yyyy").format(todaydate));  
 
 java.util.Date today1=Date.valueOf(todaydate);
 java.sql.Date today=new java.sql.Date(today1.getTime()); 
       int rows=equipsTable.getRowCount(); 
  if(broughtby.equalsIgnoreCase("Technician")){
  broughtby2=technicianTxt.getText();
  idno="N/A"; phone="N/A";
  }else{broughtby2=customernameTxt.getText();
  idno=idnoTxt.getText();phone=jTextField6.getText();
  }     
       int i=0;
       for(int b=0;b<rows;b++){
    serial=String.valueOf(equipsTable.getValueAt(b, 3));
    model=String.valueOf(equipsTable.getValueAt(b, 1));
    description=String.valueOf(equipsTable.getValueAt(b, 2));
    warranty=String.valueOf(equipsTable.getValueAt(b, 5));
    fault=String.valueOf(equipsTable.getValueAt(b, 4));
  
    pst.setInt(1, (equipNum+(b+1)) ); pst.setString(2, serial);   pst.setString(3, model); pst.setString(4, description); pst.setString(5, fault);
    pst.setString(6, warranty); pst.setDate(7, today); pst.setString(8, gatepassnoTxt.getText()); pst.setString(9, receivedbyTxt.getText());
     pst.setString(10, clientno); pst.setString(11, broughtby2); pst.setString(12, idno); pst.setString(13, phone);
      pst.setString(14, "N/A"); pst.setString(15, "N/A"); //pst.setString(15, "N/A");
    i=pst.executeUpdate();
       }
         if(i>0){ 
             getEquipment();
      JOptionPane.showMessageDialog(null, "Gatepass In for "+clientnameTxt.getText()+" equipment saved successfully!","Successful",JOptionPane.INFORMATION_MESSAGE);
       printGatepass();  
         }
     }  catch (SQLException ex) {
            Logger.getLogger(Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
    
    
    public void getTechnicians(){
  try {
            String getcontractsql="SELECT STAFFNAME   FROM STAFF   ORDER BY STAFFNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
        techniciancomboModel.removeAllElements();
        techniciancomboModel.addElement("--Select Technician--");
            while(rst.next()){
 techniciancomboModel.addElement(rst.getString(1));
    i++;  
            }
          if(i<1){  }
     }  catch (SQLException ex) {
            Logger.getLogger(Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    
  public void printGatepass(){
   
              FileOutputStream filename=null; 
     receiptDoc=new Document(PageSize.A4 ); 
      JFileChooser filesaver=new JFileChooser();
       filesaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int option=filesaver.showSaveDialog(gatepassPanel);
          
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
          String printdate=new SimpleDateFormat("dd MMM, yyyy").format(Calendar.getInstance().getTime()); 
          
       directory=dir+"/"+clientnameTxt.getText()+" GATEPASS IN NOTE "+printdate+" .pdf";
                filename = new FileOutputStream(directory);
            
   PdfWriter writer=PdfWriter.getInstance(receiptDoc ,filename);
            receiptDoc.open();
            Paragraph space=new Paragraph("          ");
            com.itextpdf.text.Font font1=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,22,Font.BOLD);
            com.itextpdf.text.Font font2=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,8,Font.BOLD);
            com.itextpdf.text.Font font3=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
            com.itextpdf.text.Font font4=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.HELVETICA,8,Font.LAYOUT_LEFT_TO_RIGHT);
             com.itextpdf.text.Font font5=new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.COURIER,9,Font.LAYOUT_LEFT_TO_RIGHT);
            
            
            Paragraph date=new Paragraph( "  Printed: "+printdate,font4);
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
            String separator2="  ___________________________________________________________________________________________________________";
            Paragraph separatorlable3=new Paragraph(separator2,font2);
               separatorlable3.setAlignment(Paragraph.ALIGN_CENTER);
               
            Paragraph receiptlabel=new Paragraph("TECHNICAL SERVICE DEPARTMENT     ",font1);
            receiptlabel.setAlignment(Paragraph.ALIGN_CENTER);  receiptDoc.add(receiptlabel);
             receiptDoc.add(separatorlable3); 
         receiptDoc.add(space);
         
     Paragraph receiptlabel2=new Paragraph("  EQUIPMENT-IN GATEPASS",font3);
                receiptlabel2.setAlignment(Paragraph.ALIGN_CENTER);
                receiptDoc.add(receiptlabel2);  
       receiptDoc.add(separatorlable3);  receiptDoc.add(space);
          /*       float[] colswidth4=new float[]{5f,9f};
   PdfPTable colstable2=new PdfPTable(colswidth4);
     colstable2 .setWidthPercentage(50);//colstable.setHorizontalAlignment();
      colstable2.setHorizontalAlignment(Element.ALIGN_RIGHT);  */ 
      
   float[] colswidth;      com.itextpdf.text.pdf.PdfPTable subjreportTable = null;
 
   float[] colswidth2=new float[]{5f,9f,6f,5f,8f};
   PdfPTable colstable=new PdfPTable(colswidth2);
     colstable .setWidthPercentage(80);//colstable.setHorizontalAlignment();
      colstable.setHorizontalAlignment(Element.ALIGN_CENTER); 
      
         PdfPCell cell243=new PdfPCell(new Phrase(" "  ,font4));cell243.setBorder(Rectangle.NO_BORDER);  
 
      
   PdfPCell cell1=new PdfPCell(new Phrase("Client Name: "  ,font2));//cell1.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell1); 
   
     PdfPCell cell3=new PdfPCell(new Phrase( clientnameTxt.getText() ,font4));//cell3.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell3);   colstable.addCell(cell243 ); 
   
  
   
    PdfPCell cell=new PdfPCell(new Phrase("Date In: "  ,font2));//cell.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell ); 
     PdfPCell cell23=new PdfPCell(new Phrase(printdate  ,font4));//cell23.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell23 ); 
    
   PdfPCell cell2=new PdfPCell(new Phrase("Contact Person: " ,font2));//cell2.setBorder(Rectangle.NO_BORDER);  
     colstable.addCell(cell2);  
     
      PdfPCell cell5=new PdfPCell(new Phrase(" "+contpersonTxt.getText(),font4));//cell5.setBorder(Rectangle.NO_BORDER);  
     colstable.addCell(cell5);  colstable.addCell(cell243 ); 
     
       PdfPCell cell34=new PdfPCell(new Phrase("Brought By: "  ,font2));//cell34.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell34 ); 
 
  PdfPCell cell43=new PdfPCell(new Phrase(broughtby2  ,font4));//cell43.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell43 );
     
    PdfPCell cell6=new PdfPCell(new Phrase("Phone: " ,font2));//cell6.setBorder(Rectangle.NO_BORDER);  
     colstable.addCell(cell6);  
     
      PdfPCell cell7=new PdfPCell(new Phrase(" "+phoneTxt.getText(),font4));//cell7.setBorder(Rectangle.NO_BORDER);  
     colstable.addCell(cell7);   colstable.addCell(cell243 ); 
     
      PdfPCell cell54=new PdfPCell(new Phrase("ID No: "  ,font2));//cell54.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell54 ); 
 
  PdfPCell cell253=new PdfPCell(new Phrase(idno  ,font4));//cell43.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell253 );
     
      PdfPCell cell8=new PdfPCell(new Phrase("Email: " ,font2));//cell8.setBorder(Rectangle.NO_BORDER);  
     colstable.addCell(cell8);  
     
       PdfPCell cell9=new PdfPCell(new Phrase(" "+emailTxt.getText(),font4));//cell9.setBorder(Rectangle.NO_BORDER);  
     colstable.addCell(cell9);   colstable.addCell(cell243 ); 
     
      PdfPCell cell541=new PdfPCell(new Phrase("Phone: "  ,font2));//cell541.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell541 ); 
 
  PdfPCell cell251=new PdfPCell(new Phrase(phone  ,font4));//cell251.setBorder(Rectangle.NO_BORDER);  
   colstable.addCell(cell251 );
   
   receiptDoc.add(colstable);    // receiptDoc.add(colstable2); 
   
   
/* Paragraph cell3=new Paragraph(new Phrase("CONTRACT DESCRIPTION:"+ descriptionTxt.getText()+" ",font2)); 
 cell3.setAlignment(Paragraph.ALIGN_CENTER);
   receiptDoc.add(cell3); */
       
  /*Paragraph receiptlabel3=new Paragraph("VALID FROM: "+validfromTxt.getText()+"    TO: "+toTxt.getText(),font2);
                receiptlabel3.setAlignment(Paragraph.ALIGN_CENTER); receiptDoc.add(receiptlabel3);*/
     //receiptDoc.add(separatorlable3); 
     receiptDoc.add(space); //receiptDoc.add(space);
                  
      colswidth = new float[]{2f,6f,7f,6f,10f,5f  };
       subjreportTable=new  com.itextpdf.text.pdf.PdfPTable(colswidth);
          subjreportTable .setWidthPercentage(90);  
 
           Paragraph Paras=new Paragraph("No",font2);
            Paras.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras1=new Paragraph("Model",font2);
            Paras1.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras2=new Paragraph("Description",font2);
            Paras2.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras21=new Paragraph("Serial No.",font2);
            Paras21.getFont().setStyle(Font.BOLD);
            
            Paragraph Paras33=new Paragraph("Fault Reported",font2);
            Paras33.getFont().setStyle(Font.BOLD);
          Paragraph Paras37=new Paragraph("Warranty",font2);
            Paras37.getFont().setStyle(Font.BOLD);
           
          
           subjreportTable.addCell(Paras);subjreportTable.addCell(Paras1);subjreportTable.addCell(Paras2);
            subjreportTable.addCell(Paras21);   subjreportTable.addCell(Paras33);  subjreportTable.addCell(Paras37); 
   
            String model, description,serial,  fault,warranty ;
            int n=equipsTable.getRowCount();
            for(int z=0;z<n;z++){
                
                model= String.valueOf(equipsTable.getValueAt(z , 1));
                description=String.valueOf(equipsTable.getValueAt(z ,2));
                serial=String.valueOf(equipsTable.getValueAt(z ,3));
                fault= (String.valueOf(equipsTable.getValueAt(z, 4)));
               warranty=(String.valueOf(equipsTable.getValueAt(z, 5)));
               
              Paragraph Paras11=new Paragraph(String.valueOf(z+1),font5);
                Paras11.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras211=new Paragraph(model,font5);
                Paras211.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras2111=new Paragraph(description,font5);
                Paras2111.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras331=new Paragraph(serial,font5);
                Paras331.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                Paragraph Paras341=new Paragraph(fault,font5);
                Paras341.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 Paragraph Paras342=new Paragraph(warranty,font5);
                Paras342.getFont().setStyle(Font.LAYOUT_LEFT_TO_RIGHT);
                
                 subjreportTable.addCell(Paras11);subjreportTable.addCell(Paras211);
                subjreportTable.addCell(Paras2111);   subjreportTable.addCell(Paras331);subjreportTable.addCell(Paras341);
                subjreportTable.addCell(Paras342);
             }
             
   PdfPCell cell78=new PdfPCell(new Phrase("  " ,font5));cell78.setBorder(Rectangle.NO_BORDER);  
   subjreportTable.addCell(cell78);  subjreportTable.addCell(cell78);  subjreportTable.addCell(cell78);  subjreportTable.addCell(cell78); 
    subjreportTable.addCell(cell78);       subjreportTable.addCell(cell78);  
     
    Paragraph sign1=new Paragraph("Equipment Details" ,font3);
   sign1.setAlignment(Paragraph.ALIGN_CENTER);receiptDoc.add( sign1);
   receiptDoc.add(separatorlable3); 
     receiptDoc.add( space);
     receiptDoc.add( subjreportTable);
     receiptDoc.add( space);
     
    float[] colswidth6=new float[]{4f,8f,15f};     
 PdfPTable signatureTable=new PdfPTable(colswidth6);
 signatureTable.setWidthPercentage(108); 
 signatureTable.setHorizontalAlignment(Element.ALIGN_LEFT);  

 /*
    Paragraph sign1=new Paragraph("Staff Signature:________________________" ,font4);
   sign1.setAlignment(Paragraph.ALIGN_LEFT);
    PdfPCell cell23=new PdfPCell(sign1);cell23.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell23); 
   
     Paragraph sign2=new Paragraph(" Date:__________________" ,font4);
     sign2.setAlignment(Paragraph.ALIGN_LEFT);
    PdfPCell cell24=new PdfPCell(sign2);cell24.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell24); */
   
 PdfPCell cell29=new PdfPCell(new Phrase(" "));cell29.setBorder(Rectangle.NO_BORDER); 

   
   Paragraph sign3=new Paragraph("Received By: " ,font2);
   sign3.setAlignment(Paragraph.ALIGN_LEFT);
    PdfPCell cell25=new PdfPCell(sign3);cell25.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell25); 
   
      Paragraph sign31=new Paragraph(currentuser ,font4);
   sign31.setAlignment(Paragraph.ALIGN_LEFT);
    PdfPCell cell234=new PdfPCell(sign31);cell234.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell234); 
   
     Paragraph sign4=new Paragraph(" Sign:__________________" ,font2);
     sign4.setAlignment(Paragraph.ALIGN_LEFT);
      PdfPCell cell89=new PdfPCell(sign4);cell89.setBorder(Rectangle.NO_BORDER);
   signatureTable.addCell(cell89);
     
   signatureTable.addCell(cell29);    signatureTable.addCell(cell29);    signatureTable.addCell(cell29); 
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
    
   receiptDoc.add(signatureTable);  receiptDoc.add(space);
   
    Paragraph NOTE2=new Paragraph("Company Stamp:_______________________",font2);
            NOTE2.setAlignment(Paragraph.ALIGN_LEFT);
     receiptDoc.add(NOTE2); receiptDoc.add(space);
   
   receiptDoc.add(space);
  
   receiptDoc.add(space);receiptDoc.add(space); receiptDoc.add(space);receiptDoc.add(space);
    receiptDoc.add(space);receiptDoc.add(space); receiptDoc.add(space);receiptDoc.add(space);
   
   Paragraph NOTE=new Paragraph("Please bring this Gatepass Note when collecting your equipment. Call __________________ for enquiries. ",font4);
            NOTE.setAlignment(Paragraph.ALIGN_CENTER);
     receiptDoc.add(NOTE); receiptDoc.add(space);
     
   Paragraph user=new Paragraph("Printed By: "+currentuser,font4);
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
    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel broughtbyholderPanel;
    private javax.swing.JPanel clientPanel;
    private javax.swing.JTextField clientnameTxt;
    private javax.swing.JTextField contpersonTxt;
    private javax.swing.JTextField customernameTxt;
    private javax.swing.JTextField dateinTxt;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JTable equipsTable;
    public javax.swing.JPanel gatepassPanel;
    private javax.swing.JTable gatepassTable;
    private javax.swing.JTextField gatepassnoTxt;
    private javax.swing.JTextField idnoTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField phoneTxt;
    private javax.swing.JTextField receivedbyTxt;
    private javax.swing.JPanel technicianPanel;
    private javax.swing.JTextField technicianTxt;
    // End of variables declaration//GEN-END:variables
}
