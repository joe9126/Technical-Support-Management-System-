
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerDateModel;
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
public class Calls extends javax.swing.JFrame {

    /**
     * Creates new form Calls
     */
       String technician,dbserialno,staff,usertype;int techindex;
       String techemail;
       String staffname,selectedtown, callreference="";
       java.sql.Date servicedate1; int servicedays; Calendar c=Calendar.getInstance();
Home access=new Home();  String callnumber;
 DefaultComboBoxModel  descriptioncomboModel=new DefaultComboBoxModel();
DefaultComboBoxModel clientcomboModel=new DefaultComboBoxModel();
DefaultComboBoxModel clientcomboModel1=new DefaultComboBoxModel();
DefaultComboBoxModel contractcomboModel1 =new DefaultComboBoxModel();

DefaultComboBoxModel towncomboboxModel=new DefaultComboBoxModel();

DefaultComboBoxModel equipcomboModel=new DefaultComboBoxModel();
DefaultComboBoxModel serialcomboModel=new DefaultComboBoxModel();
String selectedserialno;  String reportstatus=null;
SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
   int starthour, endhour;  int startMinute, endMinute;
    String serviceDate,    status,client; Date calldate; String existcallno;
  DecimalFormat df1=new DecimalFormat("#,###.00");     String  selectedcall; String transport,descrip;
   String callstatus=""; String service_no;
   String usercallreference="";
 DefaultComboBoxModel technamecomboModel=new DefaultComboBoxModel();  
   String[] cols3={"NO.","STAFF NO","TECHNICIAN"}; 
    String[] cols4={"NO.","RECOMMENDATIONS"}; 
    String[] cols5={"NO.","FINDINGS"}; 
 
 DefaultTableModel   techtableModel=new DefaultTableModel(cols3,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 0;
   }
    };
 
  DefaultTableModel recommendtableModel=new DefaultTableModel(cols4,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 1;
   }
    };
 
  DefaultTableModel    findingstableModel=new DefaultTableModel(cols5,0){
   @Override
   public boolean isCellEditable(int row,int column){
        return column == 1;
   }
    };

    public Calls() {
        initComponents();
         towncomboboxModel.addElement("--Select Town--");
      techTable.getTableHeader().setReorderingAllowed(false);
           techTable.getColumnModel().getColumn(0).setPreferredWidth(5);
           
        clientcomboModel.addElement("--Select Client--");
        descriptioncomboModel.addElement("--Select Description--");
        contractcomboModel1.addElement("--Select Contract--");
        
        
        
   findingsTable.getTableHeader().setReorderingAllowed(false);
    findingsTable.getColumnModel().getColumn(0).setPreferredWidth(1);
     recommendationsTable.getTableHeader().setReorderingAllowed(false);
    recommendationsTable.getColumnModel().getColumn(0).setPreferredWidth(1);
    
            int year=LocalDate.now().getYear();    
          Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
  admincallnumberTxt.setText(String.valueOf(""+year+"/"+loannumber)); 
  usercallnumberTxt.setText(String.valueOf(""+year+"/"+loannumber)); 
 // staffnameTxt.setText(staffname);
   LocalDate todaydate=LocalDate.now();
 String  callreqDate=(DateTimeFormatter.ofPattern("dd MMM,yyyy").format(todaydate));  
  calldateTxt.setText(callreqDate);  
  calldateTxt1.setText(callreqDate);  
  /*
JSpinner.DateEditor timeEditor=new JSpinner.DateEditor(startMinspinner,"HH:mm");
        startMinspinner.setEditor(timeEditor);
        startMinspinner.setValue(new java.util.Date()); */
     //   startMinspinner.setVisible(true); 
      getClients();getTowns();
    }
    public void getTowns(){
      try {
     String getcontractsql="SELECT TOWN FROM MILEAGESETTINGS GROUP BY TOWN ORDER BY TOWN ASC";
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
               PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
               ResultSet rst=pst.executeQuery(); int i=0;
          towncomboboxModel.removeAllElements();
           towncomboboxModel.addElement("--Select Town--");
               while(rst.next()){
          towncomboboxModel.addElement(rst.getString("town"));
                   i++;
               }} catch (SQLException ex) {
               Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
           }
    
    }
 public void getEquipment(){
 
  try {
     String getcontractsql="SELECT * FROM EQUIPMENT where CONTRACT_NO='"+contractnumTxt.getText()+"' ORDER BY MODEL ASC";
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
               PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
               ResultSet rst=pst.executeQuery(); int i=0;
          serialcomboModel.removeAllElements();
               while(rst.next()){
          serialcomboModel.addElement(rst.getString("SERIAL_NO"));
                   i++;
               }} catch (SQLException ex) {
               Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
           }
 }  
    
   public void getContracts(){
     try {
            String getcontractsql="SELECT CONTRACTS.CONT_DESCRIP, CONTRACTS.CLIENT_NO FROM CONTRACTS " +
"                  LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO WHERE CLIENTS.CLIENT_NO=(SELECT CLIENT_NO FROM CLIENTS WHERE\n" +
"                   CLIENTNAME='"+client+"') ORDER BY CLIENTS.CLIENTNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            descriptioncomboModel.removeAllElements();
           descriptioncomboModel.addElement("--Select Contract--"); 
           
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
public void registerService() {
     try {

   Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);        
//java.sql.Date sqltoday=new java.sql.Date(calldate.getTime());
  LocalDate localDate = LocalDate.now();
   String todayDate=(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));  
   java.util.Date date=Date.valueOf(todayDate);
java.sql.Date  sqltoday=new java.sql.Date(date.getTime()); 
  /*
     if(usertype.equalsIgnoreCase("USER")){
     servicedate1=sqltoday;
     }     
          */
 Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
  if(usertype.equalsIgnoreCase("Administrator")){
   if(selectedcall.equalsIgnoreCase("existing call")){
                            callnumber=existingcallnoTxt.getText();
                         }else{
            callnumber=admincallnumberTxt.getText();
            
           }
  }
  else{
   if(selectedcall.equalsIgnoreCase("existing call")){
     callnumber=existingcallnoTxt.getText();
                         }else{
            callnumber=usercallnumberTxt.getText();
            }
  }
      service_no=callnumber+"-"+loannumber;
  
         String checkcallsql = null;
PreparedStatement pst1;    ResultSet rst1;
   int t=techTable.getRowCount();int y=0;
 if(t>0){  
  for(int f=0;f<t;f++){
  if(t>1){
   checkcallsql="SELECT CALL_NO, STATUS FROM CALLS WHERE CALL_NO='"+callnumber+"-"+(f+1)+"' ";
  }else{
     checkcallsql="SELECT CALL_NO, STATUS FROM CALLS WHERE CALL_NO='"+callnumber+"' ";
  }
   
  }
   }
 
 else{
  checkcallsql="SELECT CALL_NO, STATUS FROM CALLS WHERE CALL_NO='"+callnumber+"' ";
 }
 
 pst1=(PreparedStatement)connectDb.prepareStatement(checkcallsql);
      rst1=pst1.executeQuery();  
            while(rst1.next()){
                callstatus=rst1.getString(2);
                y++; 
            }
            
    if(y>0){           
  if(callstatus.equalsIgnoreCase("CLOSED")){
    JOptionPane.showMessageDialog(null, "Call No. "+callnumber+" is closed. Open New Call","Call Closed",JOptionPane.WARNING_MESSAGE); 
  }
  else{
   if(starthour<1){
                JOptionPane.showMessageDialog(null, "Please select Start Hour!","Start Hour required",JOptionPane.WARNING_MESSAGE);
            }else if(endhour<1){
                JOptionPane.showMessageDialog(null, "Please select End Hour!","End Hour required",JOptionPane.WARNING_MESSAGE);
            }else if(startMinute<0||startMinute>59){
                JOptionPane.showMessageDialog(null, "Please enter valid Start Minute!","Start Minute required",JOptionPane.WARNING_MESSAGE);
            }
            else if(endMinute<0||endMinute>59){
                JOptionPane.showMessageDialog(null, "Please enter valid End Minute!","End Minute required",JOptionPane.WARNING_MESSAGE);
            }
            else if(endhour<starthour){
                JOptionPane.showMessageDialog(null, "Please select valid End Hour!","End Hour required",JOptionPane.WARNING_MESSAGE);
            }
            else if(selectedserialno==null){
                JOptionPane.showMessageDialog(null, "Please enter serial number!","Serial Number required",JOptionPane.WARNING_MESSAGE);
            }
            else if(equipmodelTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Equipment model!","Equipment Model required",JOptionPane.WARNING_MESSAGE);
            }
            else if(equipdescriptionTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Equipment Description!","Equipment Description required",JOptionPane.WARNING_MESSAGE);
            } 
            else if(locationTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Service Location!","Service Location required",JOptionPane.WARNING_MESSAGE);
            }
           else if( selectedtown==null||selectedtown.equalsIgnoreCase("--Select Town--")){
                JOptionPane.showMessageDialog(null, "Please select Service Town/City!","Service Town required",JOptionPane.WARNING_MESSAGE);
            } 
            else if(actiontakentxtArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Action Taken!","Service Location required",JOptionPane.WARNING_MESSAGE);
            }else if(status==null){
                JOptionPane.showMessageDialog(null, "Please select Service Status!","Service Status required",JOptionPane.WARNING_MESSAGE);
            }
          
       else{
   if(dbserialno==null){
        int choice2=JOptionPane.showConfirmDialog(null,"Serial Number "+serialnoTxt.getText()+" is not covered under contract no. "+contractnumTxt.getText()+". Proceed?","Confirm",JOptionPane.YES_NO_OPTION);
    if(choice2==JOptionPane.YES_OPTION){
  int choice=JOptionPane.showConfirmDialog(null, "Do you want to save service details for call no. "+callnumber+"?","Confirm",JOptionPane.YES_NO_OPTION);
   if(choice==JOptionPane.YES_OPTION){
       serviceEntry(); 
  } 
            }  
}  
   else{
 int choice=JOptionPane.showConfirmDialog(null, "Do you want to save service details for call no. "+callnumber+"?","Confirm",JOptionPane.YES_NO_OPTION);
   if(choice==JOptionPane.YES_OPTION){
       serviceEntry();
       
       int g=techTable.getRowCount();
   for(int b=0;b<g;b++){
   techtableModel.removeRow(0);
   }
  } 
   }
    } 
    }
   }  
    else{
   JOptionPane.showMessageDialog(null, "Call number "+callnumber+" does not exist. Please open call  first!","Invalid Call Number",JOptionPane.ERROR_MESSAGE);
}
   System.out.println("used query "+checkcallsql);  }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

public void serviceEntry(){
           try {
       boolean findingcheck, recommendcheck; servicedays=Integer.parseInt(noofdaysTxt.getText());
  findingcheck=findingsCheckbox.isSelected();
  recommendcheck=recommendationsCheckbox.isSelected();
  
               String newcallsql;    PreparedStatement pst;
               int b=techTable.getRowCount();
               int p=0;
               Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
               if(b>0){
             for(int j=0;j<servicedays;j++){ 
              
               if(servicedays>1){
            
               c.setTime(servicedate1);
                c.add(Calendar.DATE, j); 
               }
                 
                   for(int v=0;v<b;v++){
                       newcallsql="INSERT INTO SERVICE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                       pst =(PreparedStatement)connectDb.prepareStatement(newcallsql);
                       
                       if(b>1){
                           pst.setString(1, callnumber+"-"+(v+1));
                           if(servicedays>1){ pst.setString(2, service_no+j+"-"+(v+1));}
                           else{ pst.setString(2, service_no+"-"+(v+1));}
                          
                       }else{
                           pst.setString(1, callnumber);
                            if(servicedays>1){ pst.setString(2, service_no+j);}
                           else{ pst.setString(2, service_no);}
                         
                       }
                       if(servicedays>1){
                        pst.setDate(3,new java.sql.Date(c.getTimeInMillis()));
                        System.out.println("next service date:"+new java.sql.Date(c.getTimeInMillis()) );
                       }else{
                       pst.setDate(3,servicedate1);
                       }
                       if(startMinute==0){
                           pst.setString(4, starthour+":00");
                       }else if(startMinute<10){
                           pst.setString(4, starthour+":0"+startMinute);
                       }
                       else{
                           pst.setString(4, starthour+":"+startMinute);
                       }
                       
                       if(endMinute==0){
                           pst.setString(5, endhour+":00");
                       }else if(endMinute<10){
                           pst.setString(5, endhour+":0"+endMinute);
                       }
                       else{
                           pst.setString(5, endhour+":"+endMinute);
                       }
                       
                       pst.setString(6,locationTxt.getText());  pst.setString(7,selectedtown);
                       pst.setString(8, equipdescriptionTxt.getText());
                       pst.setString(9, equipmodelTxt.getText());pst.setString(10, serialnoTxt.getText());
                       pst.setString(11, actiontakentxtArea.getText());pst.setString(12, status);
                     
                      
    if(findingcheck==true&&recommendcheck==true){
         saveserviceReport(service_no);
        System.out.println("report status :"+reportstatus);
    if(reportstatus.equalsIgnoreCase("success")||reportstatus.equalsIgnoreCase("no report")){
            p=pst.executeUpdate();
                  }
    }else{
      p=pst.executeUpdate();
    }
          }
             }       
              
               
               
               
               }
               
               else{
                   
                   newcallsql="INSERT INTO SERVICE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                   pst =(PreparedStatement)connectDb.prepareStatement(newcallsql);
              for(int j=0;j<servicedays;j++){ 
              
               if(servicedays>1){
            
               c.setTime(servicedate1);
                c.add(Calendar.DATE, j); 
               } 
               pst.setString(1, callnumber);
               
                    if(servicedays>1){
                   pst.setString(2, service_no+j);
                   pst.setDate(3,(new java.sql.Date(c.getTimeInMillis()))); 
                    }else{
                    pst.setString(2, service_no);
                     pst.setDate(3,servicedate1); 
                    }
                   
                   if(startMinute==0){
                       pst.setString(4, starthour+":00");
                   }else if(startMinute<10){
                       pst.setString(4, starthour+":0"+startMinute);
                   }
                   else{
                       pst.setString(4, starthour+":"+startMinute);
                   }
                   
                   if(endMinute==0){
                       pst.setString(5, endhour+":00");
                   }else if(endMinute<10){
                       pst.setString(5, endhour+":0"+endMinute);
                   }
                   else{
                       pst.setString(5, endhour+":"+endMinute);
                   }
                   
                   pst.setString(6,locationTxt.getText());    pst.setString(7,selectedtown);
                   pst.setString(8, equipdescriptionTxt.getText());
                   pst.setString(9, equipmodelTxt.getText());pst.setString(10, serialnoTxt.getText());
                   pst.setString(11, actiontakentxtArea.getText());pst.setString(12, status);
                   
 
  
    if(findingcheck==true&&recommendcheck==true){
    saveserviceReport(service_no);
     System.out.println("report status :"+reportstatus);
    if(reportstatus.equalsIgnoreCase("success")||reportstatus.equalsIgnoreCase("no report")){
                   p=pst.executeUpdate();
                  }
    }
    else{
      p=pst.executeUpdate();
    }
           
              }  
               }
               
               if(p>0){
                   String updatecallstatus;
                   PreparedStatement pst56 ;
                   
                   if(b>0){
                       for(int v=0;v<b;v++){
                           if(b>1){
                               updatecallstatus="UPDATE CALLS SET STATUS='"+status+"' WHERE CALL_NO='"+callnumber+"-"+(v+1)+"'";
                               pst56=(PreparedStatement)connectDb.prepareStatement(updatecallstatus);
                           }
                           else{
                               updatecallstatus="UPDATE CALLS SET STATUS='"+status+"' WHERE CALL_NO='"+callnumber+"'";
                               pst56=(PreparedStatement)connectDb.prepareStatement(updatecallstatus);
                               
                           }
                           pst56.executeUpdate();
                           
                       }
                   }else{
                       
                       updatecallstatus="UPDATE CALLS SET STATUS='"+status+"' WHERE CALL_NO='"+callnumber+"'";
                       pst56=(PreparedStatement)connectDb.prepareStatement(updatecallstatus);
                       pst56.executeUpdate();
                       
                   }
                   System.out.println("SERVICE NO :"+service_no);
                   System.out.println("CALL NO :"+callnumber);
             
                 
                   String createclaimsql="INSERT INTO CLAIMS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                   PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(createclaimsql);
                   int g=0;
                    for(int h=0;h<servicedays;h++)  {
                 
       // JOptionPane.showMessageDialog(this, "no of days for service: "+servicedays);
                   if(b>0){
                       
                       for(int v=0;v<b;v++){
                           if(b>1){
                               pst2.setString(1, callnumber+"-"+(v+1));
                               if(servicedays>1){
                               pst2.setString(2, service_no+h+"-"+(v+1));
                               }else{
                                pst2.setString(2, service_no+"-"+(v+1));
                               }
                           }else{
                               pst2.setString(1, callnumber);
                                if(servicedays>1){
                               pst2.setString(2, service_no+h);
                                }else{
                            pst2.setString(2, service_no);     
                                }
                           }
                           
                           pst2.setDouble(3,0);
                           pst2.setInt(4, 0);
                           pst2.setInt(5,0);
                           pst2.setInt(6, 0);
                           pst2.setInt(7, 0);
                           pst2.setInt(8,  0);
                           pst2.setInt(9, 0);
                           pst2.setInt(10, 0);
                           pst2.setInt(11, 0);
                           pst2.setString(12, "NO CLAIM");
                           pst2.setString(13, "N/A");
                           pst2.setString(14, "N/A");
                           g=      pst2.executeUpdate();
                       }
                   }else{
                       
                       pst2.setString(1, callnumber);
                       if(servicedays>1){
                        pst2.setString(2, service_no+h);
                       }else{
                       pst2.setString(2, service_no);
                       }
                       pst2.setDouble(3,0);
                       pst2.setInt(4, 0);
                       pst2.setInt(5,0);
                       pst2.setInt(6, 0);
                       pst2.setInt(7, 0);
                       pst2.setInt(8,  0);
                       pst2.setInt(9, 0);
                       pst2.setInt(10, 0);
                       pst2.setInt(11, 0);
                       pst2.setString(12, "NOT CREATED");
                       pst2.setString(13, "N/A");
                       pst2.setString(14, "N/A");
                        g=pst2.executeUpdate();
                   }
                   }  
         System.out.println("value of g: "+g);
                   if(g>0){
                
                  JOptionPane.showMessageDialog(null, "Service details saved  successfully!","Successfull",JOptionPane.INFORMATION_MESSAGE);
                       endhour=0; endMinute =0; starthour =0;startMinute=0; callnumber =null; service_no=null;
                       locationTxt.setText(null);
                       // faulttypeTxt.setText(null);
                       equipmodelTxt.setText(null);
                       serialnoTxt.setText(null);
                       actiontakentxtArea.setText(null);
                       calldateTxt.setText(null);   admincallnumberTxt.setText(null);  //technameTxt.setText(null);
                       contractnumTxt.setText(null);
                       
                   }
               }
               
               
           } catch (SQLException ex) {
               Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
           }
      

}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        callsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        actiontakentxtArea = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        starthourCombo = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        startMinspinner = new javax.swing.JSpinner(new SpinnerDateModel());
        endhourCombo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        endMinspinner = new javax.swing.JSpinner();
        serialnoholderPanel = new javax.swing.JPanel();
        serialnoTxt = new javax.swing.JTextField();
        locationTxt = new javax.swing.JTextField();
        equipmodelTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        equipdescriptionTxt = new javax.swing.JTextField();
        servicedatePanel = new javax.swing.JPanel();
        servicedateTxt = new javax.swing.JTextField();
        servicedatePicker = new org.jdesktop.swingx.JXDatePicker();
        savebtnholderPanel = new javax.swing.JPanel();
        adminsaveBtn = new javax.swing.JButton();
        usersaveBtn = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        townComboBox = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        findingsTable = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        recommendationsTable = new javax.swing.JTable();
        findingsCheckbox = new javax.swing.JCheckBox();
        recommendationsCheckbox = new javax.swing.JCheckBox();
        jLabel37 = new javax.swing.JLabel();
        noofdaysTxt = new javax.swing.JTextField();
        callholderPanel = new javax.swing.JPanel();
        callchangerPanel = new javax.swing.JPanel();
        necallsholderPanel = new javax.swing.JPanel();
        adminnewcallsPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        clientsCombo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        contractnumTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        calldateTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        admincallnumberTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        calldescriptionTxtarea = new javax.swing.JTextArea();
        techniciansPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        techTable = new javax.swing.JTable();
        technameTxt = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        refComboBox = new javax.swing.JComboBox<>();
        callrefTxt = new javax.swing.JTextField();
        usersnewcallPanel = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        clientsCombo1 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        contractCombo = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        contractnumTxt1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        calldateTxt1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        usercallnumberTxt = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        staffnameTxt = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        calldescriptionTxtarea1 = new javax.swing.JTextArea();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        userreferenceCombo = new javax.swing.JComboBox<>();
        usercallrefTxt = new javax.swing.JTextField();
        existingcallPanel = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        existingcallnoTxt = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        callopendateTxt = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        technicianTxt = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        clientnameTxt = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        contractnoTxt = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        callstatusTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        callsPanel.setBackground(new java.awt.Color(153, 0, 153));
        callsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CALLS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Job Card", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(204, 0, 204))); // NOI18N
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SERVICE DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 51, 255))); // NOI18N
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
        });

        jLabel7.setText("START DATE:");

        jLabel8.setText("FROM:");

        jLabel9.setText("TO:");

        jLabel10.setText("LOCATION");

        jLabel12.setText("ACTION TAKEN:");

        actiontakentxtArea.setColumns(20);
        actiontakentxtArea.setRows(5);
        jScrollPane2.setViewportView(actiontakentxtArea);

        jLabel13.setText("EQUIPMENT MODEL:");

        jLabel14.setText("SERIAL NO:");

        jLabel15.setText("STATUS:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CLOSED", "TO CONTINUE", "AWAITING PARTS", "NO ACCESS ", "NO WORK" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        starthourCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00" }));
        starthourCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starthourComboActionPerformed(evt);
            }
        });

        jLabel16.setText(":");

        startMinspinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        startMinspinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                startMinspinnerStateChanged(evt);
            }
        });

        endhourCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00" }));
        endhourCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endhourComboActionPerformed(evt);
            }
        });

        jLabel17.setText(":");

        endMinspinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        endMinspinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                endMinspinnerStateChanged(evt);
            }
        });

        serialnoholderPanel.setBackground(new java.awt.Color(153, 0, 153));
        serialnoholderPanel.setLayout(new java.awt.CardLayout());

        serialnoTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                serialnoTxtMouseExited(evt);
            }
        });
        serialnoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serialnoTxtActionPerformed(evt);
            }
        });
        serialnoTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                serialnoTxtKeyReleased(evt);
            }
        });
        serialnoholderPanel.add(serialnoTxt, "card3");

        jLabel11.setText("DESCRIPTION:");

        equipdescriptionTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipdescriptionTxtActionPerformed(evt);
            }
        });

        servicedatePanel.setBackground(new java.awt.Color(153, 0, 153));
        servicedatePanel.setLayout(new java.awt.CardLayout());

        servicedateTxt.setEditable(false);
        servicedateTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servicedateTxtActionPerformed(evt);
            }
        });
        servicedatePanel.add(servicedateTxt, "card2");

        servicedatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servicedatePickerActionPerformed(evt);
            }
        });
        servicedatePanel.add(servicedatePicker, "card3");

        savebtnholderPanel.setLayout(new java.awt.CardLayout());

        adminsaveBtn.setText("Save");
        adminsaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminsaveBtnActionPerformed(evt);
            }
        });
        savebtnholderPanel.add(adminsaveBtn, "card2");

        usersaveBtn.setText("Save");
        usersaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersaveBtnActionPerformed(evt);
            }
        });
        savebtnholderPanel.add(usersaveBtn, "card3");

        jLabel26.setText("TOWN:");

        townComboBox.setModel(towncomboboxModel);
        townComboBox.setToolTipText("SELECT SERVICE TOWN FOR MILEAGE PURPOSE");
        townComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                townComboBoxMouseEntered(evt);
            }
        });
        townComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                townComboBoxActionPerformed(evt);
            }
        });

        jLabel29.setText("FINDINGS:");

        findingsTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        findingsTable.setForeground(new java.awt.Color(153, 0, 153));
        findingsTable.setModel(findingstableModel);
        findingsTable.setCellSelectionEnabled(true);
        findingsTable.setFillsViewportHeight(true);
        findingsTable.setGridColor(new java.awt.Color(153, 0, 153));
        findingsTable.setSelectionBackground(new java.awt.Color(204, 0, 204));
        findingsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                findingsTableKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(findingsTable);

        jLabel30.setText("RECOMMENDATIONS:");

        recommendationsTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        recommendationsTable.setForeground(new java.awt.Color(153, 0, 153));
        recommendationsTable.setModel(recommendtableModel);
        recommendationsTable.setCellSelectionEnabled(true);
        recommendationsTable.setFillsViewportHeight(true);
        recommendationsTable.setGridColor(new java.awt.Color(153, 0, 153));
        recommendationsTable.setSelectionBackground(new java.awt.Color(153, 0, 153));
        recommendationsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                recommendationsTableKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(recommendationsTable);

        findingsCheckbox.setBackground(new java.awt.Color(255, 255, 255));
        findingsCheckbox.setText("ANY FINDINGS?");
        findingsCheckbox.setToolTipText("SELECTING THIS MEANS THERE ARE FINDINGS");
        findingsCheckbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                findingsCheckboxMouseExited(evt);
            }
        });
        findingsCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findingsCheckboxActionPerformed(evt);
            }
        });

        recommendationsCheckbox.setBackground(new java.awt.Color(255, 255, 255));
        recommendationsCheckbox.setText("ANY RECOMMENDATIONS?");
        recommendationsCheckbox.setToolTipText("SELECTING THIS MEANS THERE ARE RECOMMENDATIONS");
        recommendationsCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recommendationsCheckboxActionPerformed(evt);
            }
        });

        jLabel37.setText("No. OF DAYS:");

        noofdaysTxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        noofdaysTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        noofdaysTxt.setText("1");
        noofdaysTxt.setToolTipText("Enter number of days of service (Optional)");
        noofdaysTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                noofdaysTxtMouseExited(evt);
            }
        });
        noofdaysTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noofdaysTxtActionPerformed(evt);
            }
        });
        noofdaysTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                noofdaysTxtKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(11, Short.MAX_VALUE)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(savebtnholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel29))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane6)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(64, 64, 64)
                                                        .addComponent(findingsCheckbox))
                                                    .addComponent(recommendationsCheckbox))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jScrollPane2)))
                                .addGap(6, 6, 6))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(38, 38, 38)
                                        .addComponent(locationTxt))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel37)
                                                .addGap(36, 36, 36)))
                                        .addComponent(equipmodelTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(49, 49, 49)
                                            .addComponent(starthourCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(startMinspinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(32, 32, 32)
                                            .addComponent(jLabel9)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(endhourCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(endMinspinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(31, 31, 31)
                                                .addComponent(equipdescriptionTxt))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel26)
                                                .addGap(68, 68, 68)
                                                .addComponent(townComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(serialnoholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(49, 49, 49))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(noofdaysTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(38, 38, 38)
                                        .addComponent(servicedatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 450, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel9)
                        .addComponent(jLabel16)
                        .addComponent(startMinspinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(endhourCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(endMinspinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(starthourCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(servicedatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noofdaysTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(serialnoholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel37))))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(equipdescriptionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(equipmodelTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(townComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(locationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addComponent(findingsCheckbox))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(recommendationsCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(76, 76, 76))
                    .addComponent(savebtnholderPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        callholderPanel.setBackground(new java.awt.Color(255, 255, 255));
        callholderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CALL OPENING", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 0, 204))); // NOI18N

        callchangerPanel.setBackground(new java.awt.Color(153, 0, 153));
        callchangerPanel.setLayout(new java.awt.CardLayout());

        necallsholderPanel.setLayout(new java.awt.CardLayout());

        adminnewcallsPanel.setBackground(new java.awt.Color(255, 255, 255));
        adminnewcallsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NEW CALL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 0, 204))); // NOI18N
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

        jLabel6.setText("DESCRIPTION:");

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

        jLabel1.setText("OPEN DATE:");

        calldateTxt.setEditable(false);
        calldateTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setText("CALL NO:");

        admincallnumberTxt.setEditable(false);
        admincallnumberTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setText("TECHNICIAN:");

        jButton2.setText("Open Call");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel18.setText("TO DO:");

        calldescriptionTxtarea.setColumns(20);
        calldescriptionTxtarea.setRows(5);
        calldescriptionTxtarea.setToolTipText("ENTER CALL DESCRIPTION ");
        jScrollPane3.setViewportView(calldescriptionTxtarea);

        techTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        techTable.setModel(techtableModel);
        techTable.setFillsViewportHeight(true);
        techTable.setGridColor(new java.awt.Color(153, 0, 153));
        techTable.setRowHeight(25);
        techTable.setRowMargin(2);
        techTable.setSelectionBackground(new java.awt.Color(153, 0, 153));
        techTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                techTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(techTable);

        javax.swing.GroupLayout techniciansPanelLayout = new javax.swing.GroupLayout(techniciansPanel);
        techniciansPanel.setLayout(techniciansPanelLayout);
        techniciansPanelLayout.setHorizontalGroup(
            techniciansPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, techniciansPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        techniciansPanelLayout.setVerticalGroup(
            techniciansPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, techniciansPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        technameTxt.setModel(technamecomboModel);
        technameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technameTxtActionPerformed(evt);
            }
        });

        jLabel27.setText("REFERENCE:");

        refComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONTRACT", "CSR" }));
        refComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refComboBoxActionPerformed(evt);
            }
        });

        callrefTxt.setEditable(false);

        javax.swing.GroupLayout adminnewcallsPanelLayout = new javax.swing.GroupLayout(adminnewcallsPanel);
        adminnewcallsPanel.setLayout(adminnewcallsPanelLayout);
        adminnewcallsPanelLayout.setHorizontalGroup(
            adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4))
                            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(contractnumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(24, 24, 24)
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(admincallnumberTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(calldateTxt)
                            .addComponent(technameTxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                        .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(techniciansPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel27))
                                .addGap(50, 50, 50)
                                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(adminnewcallsPanelLayout.createSequentialGroup()
                                        .addComponent(refComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(callrefTxt)
                                        .addGap(4, 4, 4))
                                    .addComponent(clientsCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        adminnewcallsPanelLayout.setVerticalGroup(
            adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminnewcallsPanelLayout.createSequentialGroup()
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calldateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(admincallnumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(technameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(techniciansPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(clientsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(refComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(callrefTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contractnumTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(27, 27, 27)
                .addGroup(adminnewcallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addComponent(jButton2))
        );

        necallsholderPanel.add(adminnewcallsPanel, "card2");

        usersnewcallPanel.setBackground(new java.awt.Color(255, 255, 255));
        usersnewcallPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Call", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 153))); // NOI18N

        jLabel19.setText("CLIENT:");

        clientsCombo1.setModel(clientcomboModel);
        clientsCombo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clientsCombo1MouseEntered(evt);
            }
        });
        clientsCombo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientsCombo1ActionPerformed(evt);
            }
        });

        jLabel20.setText("CONTRACT:");

        contractCombo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        contractCombo.setModel(contractcomboModel1);
        contractCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractComboActionPerformed(evt);
            }
        });

        jLabel21.setText("CONTRACT NO:");

        contractnumTxt1.setEditable(false);
        contractnumTxt1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel22.setText("OPEN DATE:");

        calldateTxt1.setEditable(false);
        calldateTxt1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel23.setText("CALL NO:");

        usercallnumberTxt.setEditable(false);
        usercallnumberTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel24.setText("TECHNICIAN:");

        jButton3.setText("Open Call");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        staffnameTxt.setEditable(false);

        calldescriptionTxtarea1.setColumns(20);
        calldescriptionTxtarea1.setRows(5);
        jScrollPane5.setViewportView(calldescriptionTxtarea1);

        jLabel25.setText("TO DO:");

        jLabel28.setText("REFERENCE:");

        userreferenceCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECT REF.--", "CONTRACT", "CSR" }));
        userreferenceCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userreferenceComboActionPerformed(evt);
            }
        });

        usercallrefTxt.setEditable(false);

        javax.swing.GroupLayout usersnewcallPanelLayout = new javax.swing.GroupLayout(usersnewcallPanel);
        usersnewcallPanel.setLayout(usersnewcallPanelLayout);
        usersnewcallPanelLayout.setHorizontalGroup(
            usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usersnewcallPanelLayout.createSequentialGroup()
                        .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, usersnewcallPanelLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(contractnumTxt1))
                            .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addGap(18, 18, 18)
                                        .addComponent(staffnameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                    .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                                        .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22)
                                            .addComponent(jLabel23))
                                        .addGap(24, 24, 24)
                                        .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(usercallnumberTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                            .addComponent(calldateTxt1, javax.swing.GroupLayout.Alignment.LEADING)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, usersnewcallPanelLayout.createSequentialGroup()
                                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20))
                                .addGap(25, 25, 25)
                                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contractCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clientsCombo1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                                        .addComponent(userreferenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(usercallrefTxt)))))
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usersnewcallPanelLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane5)
                        .addContainerGap())
                    .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                        .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usersnewcallPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton3))
                            .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        usersnewcallPanelLayout.setVerticalGroup(
            usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calldateTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(28, 28, 28)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usercallnumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(staffnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(clientsCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(userreferenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(usercallrefTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(contractCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contractnumTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(46, 46, 46)
                .addGroup(usersnewcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(usersnewcallPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel25)))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap())
        );

        necallsholderPanel.add(usersnewcallPanel, "card3");

        callchangerPanel.add(necallsholderPanel, "card4");

        existingcallPanel.setBackground(new java.awt.Color(255, 255, 255));
        existingcallPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPDATE CALL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 0, 204))); // NOI18N

        jLabel31.setText("CALL NUMBER:");

        existingcallnoTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                existingcallnoTxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                existingcallnoTxtKeyTyped(evt);
            }
        });

        jLabel32.setText("OPEN DATE:");

        callopendateTxt.setEditable(false);

        jLabel33.setText("TECHNICIAN:");

        technicianTxt.setEditable(false);

        jLabel34.setText("CLIENT:");

        clientnameTxt.setEditable(false);

        jLabel35.setText("CONTRACT NO:");

        contractnoTxt.setEditable(false);

        jLabel36.setText("STATUS");

        callstatusTxt.setEditable(false);

        javax.swing.GroupLayout existingcallPanelLayout = new javax.swing.GroupLayout(existingcallPanel);
        existingcallPanel.setLayout(existingcallPanelLayout);
        existingcallPanelLayout.setHorizontalGroup(
            existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(existingcallPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(existingcallPanelLayout.createSequentialGroup()
                        .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addGap(27, 27, 27)
                        .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clientnameTxt)
                            .addComponent(technicianTxt)
                            .addComponent(callopendateTxt)
                            .addComponent(existingcallnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(existingcallPanelLayout.createSequentialGroup()
                        .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addGap(22, 22, 22)
                        .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contractnoTxt)
                            .addComponent(callstatusTxt))))
                .addGap(37, 37, 37))
        );
        existingcallPanelLayout.setVerticalGroup(
            existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(existingcallPanelLayout.createSequentialGroup()
                .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(existingcallnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(callopendateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(technicianTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(clientnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(contractnoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(existingcallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(callstatusTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(291, Short.MAX_VALUE))
        );

        callchangerPanel.add(existingcallPanel, "card3");

        javax.swing.GroupLayout callholderPanelLayout = new javax.swing.GroupLayout(callholderPanel);
        callholderPanel.setLayout(callholderPanelLayout);
        callholderPanelLayout.setHorizontalGroup(
            callholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(callholderPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(callchangerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        callholderPanelLayout.setVerticalGroup(
            callholderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, callholderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(callchangerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(callholderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(callholderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(498, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout callsPanelLayout = new javax.swing.GroupLayout(callsPanel);
        callsPanel.setLayout(callsPanelLayout);
        callsPanelLayout.setHorizontalGroup(
            callsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(callsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1239, Short.MAX_VALUE)
                .addContainerGap())
        );
        callsPanelLayout.setVerticalGroup(
            callsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(callsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(callsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(callsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        status=""+ jComboBox2.getSelectedItem();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void starthourComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starthourComboActionPerformed
        starthour=Integer.parseInt(""+starthourCombo.getSelectedItem());
    }//GEN-LAST:event_starthourComboActionPerformed

    private void startMinspinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_startMinspinnerStateChanged
        startMinute=Integer.parseInt(""+startMinspinner.getModel().getValue());
    }//GEN-LAST:event_startMinspinnerStateChanged

    private void endhourComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endhourComboActionPerformed
        endhour=Integer.parseInt(""+endhourCombo.getSelectedItem());
    }//GEN-LAST:event_endhourComboActionPerformed

    private void endMinspinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_endMinspinnerStateChanged
        endMinute=Integer.parseInt(""+endMinspinner.getModel().getValue());
    }//GEN-LAST:event_endMinspinnerStateChanged

    private void adminsaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminsaveBtnActionPerformed
 
            registerService();
    }//GEN-LAST:event_adminsaveBtnActionPerformed

    private void clientsComboMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientsComboMouseEntered
       

    }//GEN-LAST:event_clientsComboMouseEntered

    private void clientsComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientsComboActionPerformed
        client=""+clientsCombo.getSelectedItem();
      
    }//GEN-LAST:event_clientsComboActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        descrip=""+jComboBox3.getSelectedItem();
           String getcontractsql;
        try {
    if(callreference.equalsIgnoreCase("CONTRACT")){
      getcontractsql="SELECT CONTRACTS.CONTRACT_NO, CONTRACTS.CONT_DESCRIP FROM CONTRACTS "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO = CLIENTS.CLIENT_NO WHERE CONTRACTS.CONT_DESCRIP ='"+descrip+"' AND "
            + " CLIENTS.CLIENTNAME='"+client+"'";
    }else{
      getcontractsql="SELECT supply_requests.CSRNO, supply_requests.DESCRIPTION FROM supply_requests "
            + "LEFT JOIN CLIENTS ON supply_requests.CLIENTNO = CLIENTS.CLIENT_NO WHERE supply_requests.DESCRIPTION ='"+descrip+"' AND "
            + " CLIENTS.CLIENTNAME='"+client+"'";
    }    
            
       
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            while(rst.next()){
                contractnumTxt.setText(rst.getString(1));
                getEquipment();
                i++;
            }
      connectDb.close();  }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 int g=techTable.getRowCount();
 
    if(client==null){
            JOptionPane.showMessageDialog(null, "Please select Client!","Client required",JOptionPane.WARNING_MESSAGE);
        }else if(contractnumTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please select Contract description!","Description required",JOptionPane.WARNING_MESSAGE);
        }
   else if(calldescriptionTxtarea.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter Call description!","To Do required",JOptionPane.WARNING_MESSAGE);
        }   
   else if(g<0){
  JOptionPane.showMessageDialog(null, "Please select at least one technician!","To Do required",JOptionPane.WARNING_MESSAGE);
   }  
        else{
            int choice=JOptionPane.showConfirmDialog(null, "Do you want to create new call for "+client+" under "+descrip+"?","Confirm",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_OPTION){
                try {
  // java.sql.Date sqltoday=new java.sql.Date(java.util.Date.valueOf(LocalDate.now())); 
 LocalDate localDate = LocalDate.now();
 java.util.Date date=Date.valueOf(localDate);
 java.sql.Date sqltoday=new java.sql.Date(date.getTime());
 String newcallsql;
  Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
 PreparedStatement pst;    int p=0;
 for(int v=0;v<g;v++){
  newcallsql="INSERT INTO CALLS VALUES(?,?,?,?,?,?)";
  pst=(PreparedStatement)connectDb.prepareStatement(newcallsql); 
technician=String.valueOf(techTable.getValueAt(v, 1));
 pst.setDate(1,sqltoday); 
if(g>1){
pst.setString(2, admincallnumberTxt.getText()+"-"+(v+1));
} else{
pst.setString(2, admincallnumberTxt.getText());
}
  pst.setString(3,technician); 
 pst.setString(4, contractnumTxt.getText());
 pst.setString(5,"PENDING");
  pst.setString(6,calldescriptionTxtarea.getText());
   p=pst.executeUpdate();
   
  
   
    // Recipient's email ID needs to be mentioned.
      String to = techemail;

      // Sender's email ID needs to be mentioned
      String from = "symphonyhelpdesk@symphony.co.ke";

      // Assuming you are sending email from localhost
      String host = access.server;

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("smtp.gmail.com", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("CALL #"+admincallnumberTxt.getText()+calldescriptionTxtarea.getText()+" FOR "+client);

         // Now set the actual message
         String messagebody="This is a notification for a call opened with details below:\n"
                 + "Call Date: Time: \n"
                 + "Client: Contract: \n"
                 + "Service Description: \n"
                 + "Service Eng: \n"
                 + "An email with a job card will be sent when service is completed.";
         message.setText(messagebody);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
 }
   if(p>0){
   JOptionPane.showMessageDialog(null, "New Call for "+client+" opened successfully. Email notification has been sent to technicians!","Call Created",JOptionPane.INFORMATION_MESSAGE);
                    }
            connectDb.close();    } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Call number must be unique!","Call Number Exists",JOptionPane.WARNING_MESSAGE);
                    Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void adminnewcallsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminnewcallsPanelMouseEntered

    }//GEN-LAST:event_adminnewcallsPanelMouseEntered

    private void existingcallnoTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_existingcallnoTxtKeyReleased
        String clientname=null;
        try {
            String getcontractsql="SELECT CALLS.CALL_NO,CALLS.CALL_DATE, CALLS.TECHNICIAN, CALLS.CONTRACT_NO, "
                    + "CLIENTS.CLIENTNAME, CALLS.STATUS, STAFF.STAFFNAME FROM CALLS "
            + "LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO "
            + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO "
                    + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                    + "WHERE CALLS.CALL_NO = '"+existingcallnoTxt.getText()+"'";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0; int j=0;
            while(rst.next()){
                existcallno=rst.getString(1);
                callopendateTxt.setText(sdf.format(rst.getDate(2)));
                 contractnoTxt.setText(rst.getString(4));
                clientnameTxt.setText(rst.getString(5));
                clientname=rst.getString(5);
                callstatusTxt.setText(rst.getString(6)); 
                 technicianTxt.setText(rst.getString(7)); 
                i++;
            }
            System.out.println("Client name on call update status is "+i);
                 System.out.println("Client name on call update  is "+clientname);
           if(clientname==null){
          getcontractsql="SELECT CALLS.CALL_NO,CALLS.CALL_DATE, CALLS.TECHNICIAN, CALLS.CONTRACT_NO, "
                    + "CLIENTS.CLIENTNAME, CALLS.STATUS, STAFF.STAFFNAME FROM CALLS "
            + "LEFT JOIN SUPPLY_REQUESTS ON CALLS.CONTRACT_NO = SUPPLY_REQUESTS.CSRNO "
            + " LEFT JOIN CLIENTS ON SUPPLY_REQUESTS.CLIENTNO=CLIENTS.CLIENT_NO "
                    + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                    + "WHERE CALLS.CALL_NO = '"+existingcallnoTxt.getText()+"'";      
      pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
      ResultSet   rst2=pst.executeQuery();       
                 
while(rst2.next()){
                existcallno=rst2.getString(1);
                callopendateTxt.setText(sdf.format(rst2.getDate(2)));
                contractnoTxt.setText(rst2.getString(4));
                clientnameTxt.setText(rst2.getString(5));
                callstatusTxt.setText(rst2.getString(6)); 
                 technicianTxt.setText(rst2.getString(7)); 
                j++;
            }
if(j<1){
callopendateTxt.setText(null);
                technicianTxt.setText(null); contractnoTxt.setText(null);
                clientnameTxt.setText(null);
}
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_existingcallnoTxtKeyReleased

    private void existingcallnoTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_existingcallnoTxtKeyTyped

    }//GEN-LAST:event_existingcallnoTxtKeyTyped

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered

    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
   LocalDate todaydate=LocalDate.now();
 String  callreqDate=(DateTimeFormatter.ofPattern("dd MMM,yyyy").format(todaydate));  
 servicedateTxt.setText(callreqDate); 

    }//GEN-LAST:event_jPanel4MouseEntered

    private void serialnoTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serialnoTxtKeyReleased
  selectedserialno=serialnoTxt.getText();
   try {
     String getcontractsql="SELECT SERIAL_NO, MODEL,LOCATION,DESCRIPTION FROM EQUIPMENT where SERIAL_NO LIKE '"+selectedserialno+"%' ";
    Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
               PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
               ResultSet rst=pst.executeQuery(); int i=0;
             while(rst.next()){
          dbserialno=rst.getString("SERIAL_NO");
          equipdescriptionTxt.setText(rst.getString("DESCRIPTION"));
          locationTxt.setText(rst.getString("LOCATION"));
         equipmodelTxt.setText(rst.getString("model"));
                   i++;
               }
   if(i<1){
   dbserialno=null;
          equipdescriptionTxt.setText(null);
          locationTxt.setText(null);
         equipmodelTxt.setText(null);
   }
   } catch (SQLException ex) {
               Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
           }
  
    }//GEN-LAST:event_serialnoTxtKeyReleased

    private void serialnoTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_serialnoTxtMouseExited
if(dbserialno!=null){
 serialnoTxt.setText(dbserialno);  
}
       
    }//GEN-LAST:event_serialnoTxtMouseExited

    private void servicedatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servicedatePickerActionPerformed
      servicedate1= new java.sql.Date(servicedatePicker.getDate().getTime());
    }//GEN-LAST:event_servicedatePickerActionPerformed

    private void technameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technameTxtActionPerformed
      staff=""+technameTxt.getSelectedItem();
   techindex= technameTxt.getSelectedIndex();

      System.out.println("Selected technician "+staff);
       try {
               String technsql="SELECT STAFFNO, STAFFNAME FROM STAFF where STAFFNAME='"+staff+"' ORDER BY STAFFNAME asc";
               Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
               PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(technsql);
               ResultSet rst=pst.executeQuery(); int i=0;
               String staffnum;
               int g=techTable.getRowCount();
               while(rst.next()){
           if(g>0){
               
  for(int y=0;y<g;y++){
        staffnum=String.valueOf(techTable.getValueAt(y, 0));
        technician=rst.getString(1);
      if(!staffnum.equalsIgnoreCase(technician)){
    //   JOptionPane.showMessageDialog(null,rst.getString(2)+ " already added to list!", "Duplicate entry", JOptionPane.ERROR_MESSAGE);
     // break;
      techtableModel.addRow(new Object[]{g+1,rst.getString(1),rst.getString(2)});  break;
    }
      else  {
   techtableModel.addRow(new Object[]{g+1,rst.getString(1),rst.getString(2)});  break;
      }   
             }
           }
           else{
        techtableModel.addRow(new Object[]{g+1,rst.getString(1),rst.getString(2)}); 
           }
         
                   i++;
               }
               if(i<1){
               staff=null;
               technician=null;
               }
      if(staff!=null&&!staff.equalsIgnoreCase("--Select Technician--")){
       technameTxt.removeItemAt(techindex); 
      } 
       
       }
   
       catch (SQLException ex) {
               Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
           }
    }//GEN-LAST:event_technameTxtActionPerformed

    private void techTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_techTableMouseClicked
   int rows=techTable.getRowCount();
   if(rows>0){
    int choice=JOptionPane.showConfirmDialog(null, "Remove selected technician?","Confirm",JOptionPane.YES_NO_OPTION);
     if(choice==JOptionPane.YES_OPTION){
    technamecomboModel.addElement(techTable.getValueAt(techTable.getSelectedRow(), 2));         
     techtableModel.removeRow(techTable.getSelectedRow()); 
      }
   }
       
    }//GEN-LAST:event_techTableMouseClicked

    private void clientsCombo1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientsCombo1MouseEntered
     // getClients();
    }//GEN-LAST:event_clientsCombo1MouseEntered

    private void clientsCombo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientsCombo1ActionPerformed
       client=""+clientsCombo1.getSelectedItem();
       
     //   JOptionPane.showMessageDialog(null, "Selected client "+client);
    }//GEN-LAST:event_clientsCombo1ActionPerformed

    private void contractComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractComboActionPerformed
    descrip=""+contractCombo.getSelectedItem();   String getcontractsql;
        try {
        if(usercallreference.equalsIgnoreCase("CONTRACT")){  getcontractsql="SELECT CONTRACTS.CONTRACT_NO, CONTRACTS.CONT_DESCRIP FROM CONTRACTS "
            + "LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO = CLIENTS.CLIENT_NO WHERE CONTRACTS.CONT_DESCRIP ='"+descrip+"' AND "
            + " CLIENTS.CLIENTNAME='"+client+"'";
        }else{
          getcontractsql="SELECT supply_requests.CSRNO, supply_requests.DESCRIPTION FROM supply_requests "
            + "LEFT JOIN CLIENTS ON supply_requests.CLIENTNO = CLIENTS.CLIENT_NO WHERE supply_requests.DESCRIPTION ='"+descrip+"' AND "
            + " CLIENTS.CLIENTNAME='"+client+"'";
        }
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            while(rst.next()){
                contractnumTxt1.setText(rst.getString(1));
                getEquipment();
                i++;
            }
            
        }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_contractComboActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      openuserCall();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void usersaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersaveBtnActionPerformed
    registerService();//   enteruserService();
    }//GEN-LAST:event_usersaveBtnActionPerformed

    private void townComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_townComboBoxActionPerformed
     selectedtown=""+townComboBox.getSelectedItem();
    }//GEN-LAST:event_townComboBoxActionPerformed

    private void townComboBoxMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_townComboBoxMouseEntered
      
    }//GEN-LAST:event_townComboBoxMouseEntered

    private void refComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refComboBoxActionPerformed
      callreference=""+refComboBox.getSelectedItem();
      callrefTxt.setText(callreference); 
      getReferences();
    }//GEN-LAST:event_refComboBoxActionPerformed

    private void userreferenceComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userreferenceComboActionPerformed
      usercallreference=""+userreferenceCombo.getSelectedItem();
   usercallrefTxt.setText(usercallreference); 
   
getuserReferences();
    }//GEN-LAST:event_userreferenceComboActionPerformed

    private void findingsTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findingsTableKeyPressed
   if(findingsTable.getCellEditor()!=null){
findingsTable.getCellEditor().stopCellEditing();
}
        if(evt.getKeyCode()==10){
      int rows=findingsTable.getRowCount();
      if(validCheck())
      {
      findingstableModel.addRow(new Object[]{(rows+1+".")," "}); 
      }
    }
    }//GEN-LAST:event_findingsTableKeyPressed

    private void recommendationsTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recommendationsTableKeyPressed
        if(recommendationsTable.getCellEditor()!=null){
recommendationsTable.getCellEditor().stopCellEditing();
}
        if(evt.getKeyCode()==10){
      int rows=recommendationsTable.getRowCount();
      if(validCheck2())
      {
    recommendtableModel.addRow(new Object[]{(rows+1+".")," "}); 
      }
    }
    }//GEN-LAST:event_recommendationsTableKeyPressed

    private void findingsCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findingsCheckboxActionPerformed
         boolean checked = findingsCheckbox.isSelected();
         int rows=findingsTable.getRowCount();
if(checked==true) {
if(rows<1){
findingstableModel.addRow(new Object[]{"1."," "}); 
}     
} else {
 for(int g=0;g<rows;g++){
 findingstableModel.removeRow(0); 
 }
}
    }//GEN-LAST:event_findingsCheckboxActionPerformed

    private void findingsCheckboxMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_findingsCheckboxMouseExited
    
    }//GEN-LAST:event_findingsCheckboxMouseExited

    private void recommendationsCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recommendationsCheckboxActionPerformed
      boolean checked = recommendationsCheckbox.isSelected();
         int rows=recommendationsTable.getRowCount();
if(checked==true) {
if(rows<1){
recommendtableModel.addRow(new Object[]{"1."," "}); 
}     
} else {
 for(int g=0;g<rows;g++){
 recommendtableModel.removeRow(0); 
 }
}
    }//GEN-LAST:event_recommendationsCheckboxActionPerformed

    private void servicedateTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servicedateTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_servicedateTxtActionPerformed

    private void equipdescriptionTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipdescriptionTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_equipdescriptionTxtActionPerformed

    private void serialnoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serialnoTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serialnoTxtActionPerformed

    private void noofdaysTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noofdaysTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noofdaysTxtActionPerformed

    private void noofdaysTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noofdaysTxtMouseExited
    if(noofdaysTxt.getText().isEmpty()){
    noofdaysTxt.setText("1"); 
    }
    else{
        int days=Integer.parseInt(noofdaysTxt.getText());
        if(days<1){
         noofdaysTxt.setText("1");
        }
                }
    }//GEN-LAST:event_noofdaysTxtMouseExited

    private void noofdaysTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noofdaysTxtKeyTyped
              char c=evt.getKeyChar();
        if(!((c>='0')&&(c<='9') )){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_noofdaysTxtKeyTyped

  public void saveserviceReport(String servicenumber){
  // JOptionPane.showMessageDialog(this, "service reports being saved. service number is "+servicenumber);
  boolean findingschecked = findingsCheckbox.isSelected();
   int rows1=findingsTable.getRowCount();
  System.out.println("findings check state "+findingschecked);
 boolean recommendchecked = recommendationsCheckbox.isSelected();
   int rows=recommendationsTable.getRowCount(); int v=0;
   
    System.out.println("recommend check state "+recommendchecked);
   try {
Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
   if(findingschecked==true) {
 if(validCheck()==true)
{
    String technsql="INSERT INTO SERVICE_FINDINGS VALUES(?,?)";
    PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(technsql);
    for(int f=0;f<rows1;f++){
        pst.setString(1,servicenumber);
        pst.setString(2,String.valueOf(findingsTable.getValueAt(f,1)) );
        v=pst.executeUpdate();
    }
    if(v>0){
        for(int f=0;f<rows1;f++){
            findingstableModel.removeRow(0);
            reportstatus="success";
        }
    } else{
   reportstatus="no reports";
   }
}
  else{
    JOptionPane.showMessageDialog(null, "Findings checkbox selected, please enter findings!","Findings Required",JOptionPane.WARNING_MESSAGE);
} 
   } 
  
 if(recommendchecked==true){
 if(validCheck2())
 {
     int p=0;
   String technsql="INSERT INTO SERVICE_RECOMMENDATIONS VALUES(?,?)";
   PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(technsql);
     for(int f=0;f<rows;f++){
   pst.setString(1,servicenumber);
   pst.setString(2,String.valueOf(recommendationsTable.getValueAt(f,1)) ); 
 p=  pst.executeUpdate();
     }  
   if(p>0){
   //JOptionPane.showMessageDialog(null, "Findings and recommendations saved successfully!");
       for(int f=0;f<rows;f++){
  recommendtableModel.removeRow(0); reportstatus="success";
     } 
   }
   else{
   reportstatus="no reports";
   }
}
  else{
JOptionPane.showMessageDialog(null, "Recommendations checkbox selected, please enter recommendations!","Recommendations Required",JOptionPane.WARNING_MESSAGE);
} 
 }  
   
}  
   catch (SQLException ex) {
        Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
    }
  
} 
  
  
    
       public boolean validCheck()
{
if(findingsTable.getCellEditor()!=null){
findingsTable.getCellEditor().stopCellEditing();
}
for(int i=0;i< findingsTable.getRowCount();i++)
{
for (int j=0;j<findingsTable.getColumnCount()-1;j++)
{
    try{
 String om=findingsTable.getValueAt(i,j).toString();
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
 
       
           public boolean validCheck2()
{
if(recommendationsTable.getCellEditor()!=null){
recommendationsTable.getCellEditor().stopCellEditing();
}
for(int i=0;i< recommendationsTable.getRowCount();i++)
{
for (int j=0;j<recommendationsTable.getColumnCount()-1;j++)
{
    try{
 String om=recommendationsTable.getValueAt(i,j).toString();
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
    
    public void getReferences(){
if(callreference.equalsIgnoreCase("CONTRACT")){
  getContracts();
}
else{
getCSRs();
}
}

public void getuserReferences(){
if(usercallreference.equalsIgnoreCase("CONTRACT")){
  getContracts();
}
else{
getCSRs();
}
}

public void getCSRs(){
  try {
      int currentyear=LocalDate.now().getYear();
            String getcontractsql="SELECT SUPPLY_REQUESTS.DESCRIPTION, SUPPLY_REQUESTS.CLIENTNO FROM SUPPLY_REQUESTS " +
"                  LEFT JOIN CLIENTS ON SUPPLY_REQUESTS.CLIENTNO=CLIENTS.CLIENT_NO WHERE CLIENTS.CLIENT_NO=(SELECT CLIENT_NO FROM CLIENTS WHERE\n" +
"                   CLIENTNAME='"+client+"') AND SUPPLY_REQUESTS.CSRDATE >='"+currentyear+"-01-01' ORDER BY CLIENTS.CLIENTNAME ASC";
            Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
            PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcontractsql);
            ResultSet rst=pst.executeQuery(); int i=0;
            descriptioncomboModel.removeAllElements();
           descriptioncomboModel.addElement("--Select Contract--"); 
           
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
    
    public void      enteruserService(){

 try {

   Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);        
//java.sql.Date sqltoday=new java.sql.Date(calldate.getTime());

  LocalDate localDate = LocalDate.now();
   String todayDate=(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));  
   java.util.Date date=Date.valueOf(todayDate);
java.sql.Date  sqltoday=new java.sql.Date(date.getTime()); 
   servicedate1=sqltoday;
    
          
 Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
     if(selectedcall.equalsIgnoreCase("existing call")){
                            callnumber=existingcallnoTxt.getText();
                         }else{
                            callnumber=usercallnumberTxt.getText();
           }
         service_no=callnumber+"-"+loannumber;
  
         String checkcallsql; PreparedStatement pst1;    ResultSet rst1;
 int y=0;
  checkcallsql="SELECT CALL_NO, STATUS FROM CALLS WHERE CALL_NO='"+callnumber+"' ";
  
   pst1=(PreparedStatement)connectDb.prepareStatement(checkcallsql);
      rst1=pst1.executeQuery();  
            while(rst1.next()){
                callstatus=rst1.getString(2);
                y++; 
            }
  
    if(y>0){           
  if(callstatus.equalsIgnoreCase("CLOSED")){
    JOptionPane.showMessageDialog(null, "Call No. "+callnumber+" is closed. Open New Call","Call Closed",JOptionPane.WARNING_MESSAGE); 
  }
  else{
   if(starthour<1){
                JOptionPane.showMessageDialog(null, "Please select Start Hour!","Start Hour required",JOptionPane.WARNING_MESSAGE);
            }else if(endhour<1){
                JOptionPane.showMessageDialog(null, "Please select End Hour!","End Hour required",JOptionPane.WARNING_MESSAGE);
            }else if(startMinute<0||startMinute>59){
                JOptionPane.showMessageDialog(null, "Please enter valid Start Minute!","Start Minute required",JOptionPane.WARNING_MESSAGE);
            }
            else if(endMinute<0||endMinute>59){
                JOptionPane.showMessageDialog(null, "Please enter valid End Minute!","End Minute required",JOptionPane.WARNING_MESSAGE);
            }
            else if(endhour<starthour){
                JOptionPane.showMessageDialog(null, "Please select valid End Hour!","End Hour required",JOptionPane.WARNING_MESSAGE);
            }
            else if(selectedserialno==null){
                JOptionPane.showMessageDialog(null, "Please enter serial number!","Serial Number required",JOptionPane.WARNING_MESSAGE);
            }
            else if(equipmodelTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Equipment model!","Equipment Model required",JOptionPane.WARNING_MESSAGE);
            }
            else if(equipdescriptionTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Equipment Description!","Equipment Description required",JOptionPane.WARNING_MESSAGE);
            } 
            else if(locationTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Service Location!","Service Location required",JOptionPane.WARNING_MESSAGE);
            }else if(actiontakentxtArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter Action Taken!","Service Location required",JOptionPane.WARNING_MESSAGE);
            }else if(status==null){
                JOptionPane.showMessageDialog(null, "Please select Service Status!","Service Status required",JOptionPane.WARNING_MESSAGE);
            }
       else{
   if(dbserialno==null){
        int choice2=JOptionPane.showConfirmDialog(null,"Serial Number "+serialnoTxt.getText()+" is not covered under contract no. "+contractnumTxt1.getText()+". Proceed?","Confirm",JOptionPane.YES_NO_OPTION);
    if(choice2==JOptionPane.YES_OPTION){
  int choice=JOptionPane.showConfirmDialog(null, "Do you want to save service details for call no. "+callnumber+"?","Confirm",JOptionPane.YES_NO_OPTION);
   if(choice==JOptionPane.YES_OPTION){
   String newcallsql;    PreparedStatement pst;  
   int p=0;
 
newcallsql="INSERT INTO SERVICE VALUES(?,?,?,?,?,?,?,?,?,?,?)";
pst =(PreparedStatement)connectDb.prepareStatement(newcallsql);
pst.setString(1, callnumber);  
 pst.setString(2, service_no); 
 pst.setDate(3,servicedate1);
       pst.setString(4, starthour+":"+startMinute);  pst.setString(5, endhour+":"+endMinute);
      pst.setString(6,locationTxt.getText()); pst.setString(7, equipdescriptionTxt.getText());
      pst.setString(8, equipmodelTxt.getText());pst.setString(9, serialnoTxt.getText());
      pst.setString(10, actiontakentxtArea.getText());pst.setString(11, status);
  p=pst.executeUpdate();
  
 if(p>0){
 String updatecallstatus;   
     PreparedStatement pst56 ;
updatecallstatus="UPDATE CALLS SET STATUS='"+status+"' WHERE CALL_NO='"+callnumber+"'";    
 pst56=(PreparedStatement)connectDb.prepareStatement(updatecallstatus);
  pst56.executeUpdate();
    
    System.out.println("SERVICE NO :"+service_no);
         System.out.println("CALL NO :"+callnumber);
         
      String createclaimsql="INSERT INTO CLAIMS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
       PreparedStatement pst2=(PreparedStatement)connectDb.prepareStatement(createclaimsql);
              int g=0;
pst2.setString(1, callnumber);  
 pst2.setString(2, service_no); 
   pst2.setDouble(3,0);
   pst2.setInt(4, 0);  
     pst2.setInt(5,0);
   pst2.setInt(6, 0);
  pst2.setInt(7, 0);
  pst2.setInt(8,  0);  
   pst2.setInt(9, 0);
  pst2.setInt(10, 0);  
  pst2.setInt(11, 0);
  pst2.setString(12, "NOT CREATED"); 
  pst2.setString(13, "N/A");
   g=      pst2.executeUpdate(); 
       
  if(g>0){
      JOptionPane.showMessageDialog(null, "Service Details saved  successfully!","Successfull",JOptionPane.INFORMATION_MESSAGE);
  endhour=0; endMinute =0; starthour =0;startMinute=0; callnumber =null; service_no=null;
  locationTxt.setText(null); 
 // faulttypeTxt.setText(null); 
 equipmodelTxt.setText(null); 
 serialnoTxt.setText(null); 
  actiontakentxtArea.setText(null); 
  calldateTxt1.setText(null);   usercallnumberTxt.setText(null);  //technameTxt.setText(null);  
  contractnumTxt1.setText(null); calldescriptionTxtarea1.setText(null);
  
     }                       
 
   }
      } 
            }  
}    
    } 
    }
 
        }  
    else{
   JOptionPane.showMessageDialog(null, "Call number "+callnumber+" does not exist. Please open call  first!","Invalid Call Number",JOptionPane.ERROR_MESSAGE);
}
     }
        catch (SQLException ex) {
            Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
        }
}


  public void   openuserCall(){

    if(client==null){
            JOptionPane.showMessageDialog(null, "Please select Client!","Client required",JOptionPane.WARNING_MESSAGE);
        }else if(contractnumTxt1.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please select Contract!","Description required",JOptionPane.WARNING_MESSAGE);
        }
   else if(calldescriptionTxtarea1.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter Call description!","To Do required",JOptionPane.WARNING_MESSAGE);
        }   
   else{
            int choice=JOptionPane.showConfirmDialog(null, "Do you want to create new call for "+client+" under "+descrip+"?","Confirm",JOptionPane.YES_NO_OPTION);
  if(choice==JOptionPane.YES_OPTION){
                try {
  // java.sql.Date sqltoday=new java.sql.Date(java.util.Date.valueOf(LocalDate.now())); 
 LocalDate localDate = LocalDate.now();
 java.util.Date date=Date.valueOf(localDate);
 java.sql.Date sqltoday=new java.sql.Date(date.getTime());
 String newcallsql;
  Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
 PreparedStatement pst;    int p=0;
{
  newcallsql="INSERT INTO CALLS VALUES(?,?,?,?,?,?)";
  pst=(PreparedStatement)connectDb.prepareStatement(newcallsql); 
  pst.setDate(1,sqltoday); 
pst.setString(2, usercallnumberTxt.getText());
 pst.setString(3,technician); 
 pst.setString(4, contractnumTxt1.getText());
 pst.setString(5,"PENDING");
  pst.setString(6,calldescriptionTxtarea1.getText());
   p=pst.executeUpdate();
 }
   if(p>0){
   JOptionPane.showMessageDialog(null, "New Call for "+client+" opened successfully!","Call Created",JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Call number must be unique!","Call Number Exists",JOptionPane.WARNING_MESSAGE);
                    Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }}
  
  }  
    /**
     * @param args the command line arguments
     */
 public void getTechnician(){
           try {
               String technsql="SELECT STAFFNO, STAFFNAME,EMAIL FROM STAFF ORDER BY STAFFNAME ASC";
               Connection connectDb=(Connection)DriverManager.getConnection( access.server,  access.username, access.dbpword);
               PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(technsql);
               ResultSet rst=pst.executeQuery(); int i=0;
               technamecomboModel.removeAllElements();
               technamecomboModel.addElement("--Select Technician--");
               while(rst.next()){
                   technamecomboModel.addElement(rst.getString(2));
                  technician=rst.getString(1);
                  staff=rst.getString(2);
                  techemail=rst.getString(3);
                   i++;
               }
               if(i<1){
               staff=null;
               technician=null;
               }
           } catch (SQLException ex) {
               Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
           }
  
 }
 
  public void sendEmail(String recipient1){
   // Recipient's email ID needs to be mentioned.
      String to = recipient1;

      // Sender's email ID needs to be mentioned
      String from = "symphonyhelpdesk@symphony.co.ke";

      // Assuming you are sending email from localhost
      String host = access.server;

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("smtp.gmail.com", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("This is the Subject Line!");

         // Now set the actual message
         message.setText("This is actual message");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea actiontakentxtArea;
    public javax.swing.JTextField admincallnumberTxt;
    public javax.swing.JPanel adminnewcallsPanel;
    public javax.swing.JButton adminsaveBtn;
    public javax.swing.JPanel callchangerPanel;
    public javax.swing.JTextField calldateTxt;
    public javax.swing.JTextField calldateTxt1;
    private javax.swing.JTextArea calldescriptionTxtarea;
    private javax.swing.JTextArea calldescriptionTxtarea1;
    private javax.swing.JPanel callholderPanel;
    private javax.swing.JTextField callopendateTxt;
    private javax.swing.JTextField callrefTxt;
    public javax.swing.JPanel callsPanel;
    private javax.swing.JTextField callstatusTxt;
    private javax.swing.JTextField clientnameTxt;
    private javax.swing.JComboBox<String> clientsCombo;
    private javax.swing.JComboBox<String> clientsCombo1;
    private javax.swing.JComboBox<String> contractCombo;
    private javax.swing.JTextField contractnoTxt;
    private javax.swing.JTextField contractnumTxt;
    private javax.swing.JTextField contractnumTxt1;
    private javax.swing.JSpinner endMinspinner;
    private javax.swing.JComboBox<String> endhourCombo;
    private javax.swing.JTextField equipdescriptionTxt;
    private javax.swing.JTextField equipmodelTxt;
    public javax.swing.JPanel existingcallPanel;
    private javax.swing.JTextField existingcallnoTxt;
    private javax.swing.JCheckBox findingsCheckbox;
    private javax.swing.JTable findingsTable;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField locationTxt;
    public javax.swing.JPanel necallsholderPanel;
    private javax.swing.JTextField noofdaysTxt;
    private javax.swing.JCheckBox recommendationsCheckbox;
    private javax.swing.JTable recommendationsTable;
    private javax.swing.JComboBox<String> refComboBox;
    public javax.swing.JPanel savebtnholderPanel;
    private javax.swing.JTextField serialnoTxt;
    private javax.swing.JPanel serialnoholderPanel;
    public javax.swing.JPanel servicedatePanel;
    public org.jdesktop.swingx.JXDatePicker servicedatePicker;
    public javax.swing.JTextField servicedateTxt;
    public javax.swing.JTextField staffnameTxt;
    private javax.swing.JSpinner startMinspinner;
    private javax.swing.JComboBox<String> starthourCombo;
    private javax.swing.JTable techTable;
    private javax.swing.JComboBox<String> technameTxt;
    private javax.swing.JTextField technicianTxt;
    private javax.swing.JPanel techniciansPanel;
    private javax.swing.JComboBox<String> townComboBox;
    public javax.swing.JTextField usercallnumberTxt;
    private javax.swing.JTextField usercallrefTxt;
    private javax.swing.JComboBox<String> userreferenceCombo;
    public javax.swing.JButton usersaveBtn;
    public javax.swing.JPanel usersnewcallPanel;
    // End of variables declaration//GEN-END:variables
}
