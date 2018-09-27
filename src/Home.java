
import java.awt.Dimension;
import java.awt.Image;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.security.MessageDigest;
import java.time.temporal.ChronoUnit;
import javax.swing.DefaultComboBoxModel;


public final class Home extends javax.swing.JFrame implements WindowListener{
int trialcounter=0;
 //String server="jdbc:mysql://support.symphony.co.ke:3306/symphonyts"; String dbpword="janito12345678";
//String server="jdbc:mysql://41.215.129.162:3306/symphonyts"; String dbpword="janito12345678";
String server="jdbc:mysql://10.1.101.40:3306/symphonyts"; String dbpword="janito12345678";
 //String server="jdbc:mysql://127.0.0.1:3306/supportmaster"; String dbpword="287885299126"; 
String sessionID;  
String dbname="symphonyts"; String username="root";//
 String username2, pword, owner, SACCONAME, USERTYPE;
String logintime,logouttime; String currentowner;
   public  JPanel homepanel3=new JPanel();
   java.sql.Date deadline; String staffname;
   SimpleDateFormat stf=new SimpleDateFormat("HH:MM:ss");
   
   DefaultComboBoxModel usernamecomboModel =new DefaultComboBoxModel();
   String selectedusername=null;
    public Home() {
        initComponents();
      usernamecomboModel.addElement("--Select Username--"); 
      getUsernames();
          try{ 
         Class.forName("com.mysql.jdbc.Driver");
         Connection connectDb=(Connection)DriverManager.getConnection( server,  username, dbpword);
     } catch (ClassNotFoundException | SQLException ex) {
          
           System.out.println("Please check your connection");
      server="jdbc:mysql://41.215.129.162:3306/supportmaster";    
           getIPConnection(server);
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int)screensize.getWidth();
        int height=(int)screensize.getHeight();
        setSize(width-50,height-50);
        setLocationRelativeTo(null);
     menuBar.setVisible(false); 
       setExtendedState(JFrame.MAXIMIZED_BOTH);
     JFrame.setDefaultLookAndFeelDecorated(true);
  ImageIcon img = new ImageIcon(Home.class.getResource("icons/symphonyicon.jpg"));   
 setIconImage(img.getImage());
 
 pack();
  setScreensize(); 
  //setMaximizedBounds(new Rectangle(0, 0));
  
            addWindowStateListener(new WindowStateListener() {
                @Override
                public void windowStateChanged(final WindowEvent e) {
                    if (e.getNewState() == MAXIMIZED_BOTH) {
                        setExtendedState(NORMAL);
                       
                    }
                }
            });
   setLocationRelativeTo(null);
   getSettings();
    }
    
    public void getUsernames(){
    try{ 
         Class.forName("com.mysql.jdbc.Driver");
 Connection connectDb=(Connection)DriverManager.getConnection( server,  username, dbpword);
  String getusers="SELECT USERNAME FROM USERS ORDER BY USERNAME ASC";
  PreparedStatement pst=connectDb.prepareStatement(getusers);
  ResultSet rst=pst.executeQuery();
  usernamecomboModel.removeAllElements();usernamecomboModel.addElement("--Select Username--");
  while(rst.next()){
  usernamecomboModel.addElement(rst.getString(1));
  }
       } catch (ClassNotFoundException | SQLException ex) {
          
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void setScreensize(){
     Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
int screendiff=screensize.width-screensize.height;

 if(screendiff>=598){
      //JOptionPane.showMessageDialog(null, "Screen width: "+screensize.width+" screen height: "+screensize.height);
 setSize(screensize.width,screensize.height);
 System.out.println("Screen height:"+screensize.height);
System.out.println("Screen height:"+screensize.width);
 }else{
 setSize(screensize.width,screensize.height-150);
 //JOptionPane.showMessageDialog(null, "Screen width: "+screensize.width+" screen height: "+screensize.height);
 System.out.println("Screen height:"+screensize.height);
System.out.println("Screen height:"+screensize.width);
 }

    }
    
public void getIPConnection(String servername){
 try{ 
         Class.forName("com.mysql.jdbc.Driver");
         Connection connectDb=(Connection)DriverManager.getConnection( servername,  username, dbpword);
 server="jdbc:mysql://41.215.129.162:3306/symphonyts";   
       } catch (ClassNotFoundException | SQLException ex) {
           System.out.println("Please check your connection");
       server="jdbc:mysql://10.1.101.40:3306/symphonyts";     
           getLocalConnection(server);
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

}

public void getLocalConnection(String servername){
 try{ 
         Class.forName("com.mysql.jdbc.Driver");
         Connection connectDb=(Connection)DriverManager.getConnection( servername,  username, dbpword);
      server="jdbc:mysql://10.1.101.40:3306/symphonyts"; 
       } catch (ClassNotFoundException | SQLException ex) {
     JOptionPane.showMessageDialog(null,"Connection to server failed! Please check your connection","No Connection",JOptionPane.ERROR_MESSAGE);
      // System.exit(0);
             Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

}

   public void getSettings(){
     try {
         Class.forName("com.mysql.jdbc.Driver");
         Connection connectDb=(Connection)DriverManager.getConnection( server,  username, dbpword);
         String sqllogo="SELECT * FROM SETTINGS";
         com.mysql.jdbc.PreparedStatement pstret=(com.mysql.jdbc.PreparedStatement)connectDb.prepareStatement(sqllogo);
 ResultSet stret=pstret.executeQuery();
 int k=0;
 if(stret!=null){
while(stret.next()){
 setTitle(stret.getString(1)+" CMS");
byte[] ibytes =stret.getBytes(5); 

Image imagelog = getToolkit().createImage(ibytes);
ImageIcon icon=new ImageIcon( imagelog.getScaledInstance(300, 100, Image.SCALE_DEFAULT));
 cologoLabel.setIcon(icon);

}
 }      
     } catch (ClassNotFoundException | SQLException ex) {
         Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
   
     public void checkCallstatus(){
    try {
          String getcallsql="";
          System.out.println("USERTYPE "+USERTYPE+" OWNER "+owner);


  if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")) {       
  getcallsql="SELECT CALLS.CALL_NO, CALLS.CALL_DATE, CALLSTATUS.TIMEOPENED,CLIENTS.CLIENTNAME, CALLS.TO_DO,STAFF.STAFFNAME FROM CALLS "
                + " LEFT JOIN CALLSTATUS ON CALLS.CALL_NO=CALLSTATUS.CALL_NO "
                + "LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO "
                + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO  "
                + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                + "WHERE CALLSTATUS.STATUS='NO UPDATE'"
                + " ORDER BY CALLS.CALL_DATE AND CALLSTATUS.TIMEOPENED DESC";
  }
  else if(USERTYPE.equalsIgnoreCase("user")) {
  getcallsql="SELECT CALLS.CALL_NO, CALLS.CALL_DATE, CALLSTATUS.TIMEOPENED,CLIENTS.CLIENTNAME, CALLS.TO_DO,STAFF.STAFFNAME FROM CALLS "
                    + " LEFT JOIN CALLSTATUS ON CALLS.CALL_NO=CALLSTATUS.CALL_NO "
                    + "LEFT JOIN CONTRACTS ON CALLS.CONTRACT_NO = CONTRACTS.CONTRACT_NO "
                    + " LEFT JOIN CLIENTS ON CONTRACTS.CLIENT_NO=CLIENTS.CLIENT_NO  "
                    + "LEFT JOIN STAFF ON CALLS.TECHNICIAN=STAFF.STAFFNO "
                    + "WHERE CALLS.TECHNICIAN='"+owner+"' AND CALLSTATUS.STATUS='NO UPDATE'"
                    + " ORDER BY CALLS.CALL_DATE AND CALLSTATUS.TIMEOPENED DESC"; 
  
   }
        Connection connectDb=(Connection)DriverManager.getConnection( server,  username, dbpword);
        PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getcallsql);
        ResultSet rst=pst.executeQuery(); int i=0;
        java.sql.Time timenow=java.sql.Time.valueOf(LocalTime.now());long daysdiff=0; long hoursdiff=0;
        java.sql.Date today=java.sql.Date.valueOf(LocalDate.now());
        while(rst.next())  {
            if(rst.getTime(3)!=null){
                daysdiff=ChronoUnit.DAYS.between(LocalDate.parse(rst.getDate(2).toString()), LocalDate.parse(today.toString())) ;
                hoursdiff=ChronoUnit.HOURS.between(LocalTime.parse(rst.getTime(3).toString()), LocalTime.parse(timenow.toString())) ;
             }
            i++;
        }
        if(daysdiff>=1||hoursdiff>=2){
 Callnotification dialog = new Callnotification(new javax.swing.JFrame(), true);
 dialog.getCalls(getcallsql,USERTYPE); 
dialog.setVisible(true);
 dialog.usertype=USERTYPE;
 dialog.owner=owner;
                }
    } catch (SQLException ex) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
    }
  
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
  public void setName(){
  try {
       
    String getusersql="SELECT NAME FROM SETTINGS";    
 
    Connection connectDb=(Connection)DriverManager.getConnection( server,  username, dbpword);
        PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getusersql); 
        ResultSet rst=pst.executeQuery(); int i=1;
        while(rst.next()){
      SACCONAME=rst.getString(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
    }
       setTitle(" | "+SACCONAME+"  ");
  
  } 
  
 public   String md5( String pwordsource ) {
    try {
        MessageDigest md = MessageDigest.getInstance( "MD5" );
        byte[] bytes = md.digest( pwordsource.getBytes("UTF-8") );
        return getString( bytes );
    } catch( Exception e )  {
        e.printStackTrace();
        return null;
    }
}

  
  private static String getString( byte[] bytes ) 
{
  StringBuffer sb = new StringBuffer();
  for( int i=0; i<bytes.length; i++ )     
  {
     byte b = bytes[ i ];
     String hex = Integer.toHexString((int) 0x00FF & b);
     if (hex.length() == 1) 
     {
        sb.append("0");
     }
     sb.append( hex );
  }
  return sb.toString();
}


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        loginPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        pwordTxt = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cologoLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        contractsmenuBtn = new javax.swing.JButton();
        callsmenuBtn = new javax.swing.JButton();
        callsmenuBtn1 = new javax.swing.JButton();
        contractsmenuBtn1 = new javax.swing.JButton();
        contractsmenuBtn2 = new javax.swing.JButton();
        profielmenuBtn2 = new javax.swing.JButton();
        callsmenuBtn3 = new javax.swing.JButton();
        callsmenuBtn4 = new javax.swing.JButton();
        contractsmenuBtn3 = new javax.swing.JButton();
        callsmenuBtn5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        loggedinuserLabel = new javax.swing.JLabel();
        usertypeLabel = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        BGlABEL = new javax.swing.JLabel();
        userdashboardPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        cologo = new javax.swing.JLabel();
        bgLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        jMenu7 = new javax.swing.JMenu();
        homeMenu = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        newcontractMenu = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        groupsMenu = new javax.swing.JMenu();
        opencallsMenu = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        logoutMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1000, 800));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new java.awt.BorderLayout());

        homePanel.setBackground(new java.awt.Color(0, 0, 0));
        homePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        homePanel.setOpaque(false);
        homePanel.setLayout(new java.awt.CardLayout());

        loginPanel.setBackground(new java.awt.Color(0, 0, 0));
        loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 204), new java.awt.Color(0, 153, 204), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 204, 204))));
        loginPanel.setOpaque(false);
        loginPanel.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 204), new java.awt.Color(0, 102, 102), null, null), "Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 204, 204))); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setText("Username:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(70, 60, 74, 17);

        usernameTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        usernameTxt.setForeground(new java.awt.Color(153, 0, 153));
        usernameTxt.setModel(usernamecomboModel);
        usernameTxt.setToolTipText("Select username");
        usernameTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usernameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTxtActionPerformed(evt);
            }
        });
        jPanel1.add(usernameTxt);
        usernameTxt.setBounds(160, 60, 210, 20);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("Password:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(76, 89, 72, 17);

        pwordTxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pwordTxt.setForeground(new java.awt.Color(153, 0, 153));
        pwordTxt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pwordTxt.setCaretColor(new java.awt.Color(0, 153, 255));
        pwordTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwordTxtActionPerformed(evt);
            }
        });
        jPanel1.add(pwordTxt);
        pwordTxt.setBounds(160, 87, 210, 21);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(166, 126, 72, 23);

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(277, 126, 77, 23);

        loginPanel.add(jPanel1);
        jPanel1.setBounds(466, 414, 440, 200);

        jPanel3.setOpaque(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setOpaque(false);

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Supportmaster.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Technical Support Management System ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(428, 428, 428))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel5)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        loginPanel.add(jPanel3);
        jPanel3.setBounds(270, 10, 960, 390);
        loginPanel.add(cologoLabel);
        cologoLabel.setBounds(30, 20, 360, 140);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bgimage2.png"))); // NOI18N
        loginPanel.add(jLabel1);
        jLabel1.setBounds(0, 0, 1360, 820);

        homePanel.add(loginPanel, "card3");

        dashboardPanel.setBackground(new java.awt.Color(255, 255, 255));
        dashboardPanel.setLayout(null);

        contractsmenuBtn.setBackground(new java.awt.Color(255, 255, 255));
        contractsmenuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/stock.png"))); // NOI18N
        contractsmenuBtn.setOpaque(false);
        contractsmenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractsmenuBtnActionPerformed(evt);
            }
        });
        dashboardPanel.add(contractsmenuBtn);
        contractsmenuBtn.setBounds(420, 310, 140, 120);

        callsmenuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/logout.png"))); // NOI18N
        callsmenuBtn.setOpaque(false);
        callsmenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callsmenuBtnActionPerformed(evt);
            }
        });
        dashboardPanel.add(callsmenuBtn);
        callsmenuBtn.setBounds(1080, 310, 140, 120);

        callsmenuBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/call.png"))); // NOI18N
        callsmenuBtn1.setOpaque(false);
        callsmenuBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callsmenuBtn1ActionPerformed(evt);
            }
        });
        dashboardPanel.add(callsmenuBtn1);
        callsmenuBtn1.setBounds(420, 120, 140, 120);

        contractsmenuBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/claim.png"))); // NOI18N
        contractsmenuBtn1.setOpaque(false);
        contractsmenuBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractsmenuBtn1ActionPerformed(evt);
            }
        });
        dashboardPanel.add(contractsmenuBtn1);
        contractsmenuBtn1.setBounds(650, 120, 140, 120);

        contractsmenuBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/contracts.png"))); // NOI18N
        contractsmenuBtn2.setOpaque(false);
        contractsmenuBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractsmenuBtn2ActionPerformed(evt);
            }
        });
        dashboardPanel.add(contractsmenuBtn2);
        contractsmenuBtn2.setBounds(1080, 120, 140, 120);

        profielmenuBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/myprofile1.png"))); // NOI18N
        profielmenuBtn2.setOpaque(false);
        profielmenuBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profielmenuBtn2ActionPerformed(evt);
            }
        });
        dashboardPanel.add(profielmenuBtn2);
        profielmenuBtn2.setBounds(180, 120, 140, 120);

        callsmenuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/gatepass.png"))); // NOI18N
        callsmenuBtn3.setOpaque(false);
        callsmenuBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callsmenuBtn3ActionPerformed(evt);
            }
        });
        dashboardPanel.add(callsmenuBtn3);
        callsmenuBtn3.setBounds(180, 310, 140, 120);

        callsmenuBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/staff.png"))); // NOI18N
        callsmenuBtn4.setOpaque(false);
        callsmenuBtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callsmenuBtn4ActionPerformed(evt);
            }
        });
        dashboardPanel.add(callsmenuBtn4);
        callsmenuBtn4.setBounds(650, 310, 140, 120);

        contractsmenuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/clients.png"))); // NOI18N
        contractsmenuBtn3.setOpaque(false);
        contractsmenuBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractsmenuBtn3ActionPerformed(evt);
            }
        });
        dashboardPanel.add(contractsmenuBtn3);
        contractsmenuBtn3.setBounds(870, 120, 140, 120);

        callsmenuBtn5.setBackground(new java.awt.Color(153, 0, 153));
        callsmenuBtn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/users.png"))); // NOI18N
        callsmenuBtn5.setToolTipText("");
        callsmenuBtn5.setOpaque(false);
        callsmenuBtn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callsmenuBtn5ActionPerformed(evt);
            }
        });
        dashboardPanel.add(callsmenuBtn5);
        callsmenuBtn5.setBounds(870, 310, 140, 120);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userman2.png"))); // NOI18N
        dashboardPanel.add(jLabel4);
        jLabel4.setBounds(970, 10, 40, 40);

        loggedinuserLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        loggedinuserLabel.setForeground(new java.awt.Color(255, 255, 255));
        loggedinuserLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loggedinuserLabel.setText("User logged in");
        dashboardPanel.add(loggedinuserLabel);
        loggedinuserLabel.setBounds(980, 20, 140, 17);

        usertypeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usertypeLabel.setForeground(new java.awt.Color(255, 255, 255));
        usertypeLabel.setText("| Usertype");
        dashboardPanel.add(usertypeLabel);
        usertypeLabel.setBounds(1130, 20, 140, 17);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout2.jpg"))); // NOI18N
        jButton11.setToolTipText("Click to log Out");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        dashboardPanel.add(jButton11);
        jButton11.setBounds(1270, 10, 30, 30);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/Supportmaster.png"))); // NOI18N
        dashboardPanel.add(jLabel6);
        jLabel6.setBounds(470, 520, 570, 140);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PARTS STOCK");
        dashboardPanel.add(jLabel7);
        jLabel7.setBounds(440, 440, 100, 17);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("STAFF");
        dashboardPanel.add(jLabel8);
        jLabel8.setBounds(700, 440, 60, 17);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("USER ACCOUNTS");
        dashboardPanel.add(jLabel12);
        jLabel12.setBounds(880, 440, 130, 17);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("LOG OUT");
        dashboardPanel.add(jLabel18);
        jLabel18.setBounds(1120, 440, 90, 17);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("CONTRACTS");
        dashboardPanel.add(jLabel20);
        jLabel20.setBounds(1110, 240, 90, 17);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("CLIENTS");
        dashboardPanel.add(jLabel21);
        jLabel21.setBounds(900, 240, 90, 17);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("CLAIMS");
        dashboardPanel.add(jLabel22);
        jLabel22.setBounds(690, 237, 90, 30);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("CALLS");
        dashboardPanel.add(jLabel23);
        jLabel23.setBounds(460, 240, 50, 17);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("PROFILE");
        dashboardPanel.add(jLabel24);
        jLabel24.setBounds(220, 240, 70, 17);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("GATEPASS");
        dashboardPanel.add(jLabel25);
        jLabel25.setBounds(210, 440, 80, 17);

        BGlABEL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bgimage2.png"))); // NOI18N
        dashboardPanel.add(BGlABEL);
        BGlABEL.setBounds(0, 0, 1370, 700);

        homePanel.add(dashboardPanel, "card3");

        userdashboardPanel.setLayout(null);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/userman2.png"))); // NOI18N
        userdashboardPanel.add(jLabel9);
        jLabel9.setBounds(920, 10, 40, 40);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("User logged in");
        userdashboardPanel.add(jLabel10);
        jLabel10.setBounds(970, 20, 150, 17);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("| Usertype");
        userdashboardPanel.add(jLabel11);
        jLabel11.setBounds(1120, 20, 140, 17);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/call.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        userdashboardPanel.add(jButton3);
        jButton3.setBounds(580, 100, 160, 120);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("New Call");
        userdashboardPanel.add(jLabel13);
        jLabel13.setBounds(620, 230, 80, 14);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Update Call");
        userdashboardPanel.add(jLabel14);
        jLabel14.setBounds(900, 230, 80, 20);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/callupdate.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        userdashboardPanel.add(jButton4);
        jButton4.setBounds(860, 100, 160, 120);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/claim.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        userdashboardPanel.add(jButton5);
        jButton5.setBounds(310, 310, 160, 120);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Create Claim");
        userdashboardPanel.add(jLabel15);
        jLabel15.setBounds(340, 440, 110, 14);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Print Claim");
        userdashboardPanel.add(jLabel16);
        jLabel16.setBounds(630, 440, 110, 14);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/print.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        userdashboardPanel.add(jButton6);
        jButton6.setBounds(590, 310, 160, 120);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/myprofile1.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        userdashboardPanel.add(jButton7);
        jButton7.setBounds(310, 100, 160, 120);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("My Profile");
        userdashboardPanel.add(jLabel17);
        jLabel17.setBounds(350, 230, 80, 14);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/PasswordChange.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        userdashboardPanel.add(jButton9);
        jButton9.setBounds(870, 310, 160, 120);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Change Password");
        userdashboardPanel.add(jLabel19);
        jLabel19.setBounds(880, 440, 140, 14);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout2.jpg"))); // NOI18N
        jButton10.setToolTipText("Click to log Out");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        userdashboardPanel.add(jButton10);
        jButton10.setBounds(1270, 10, 30, 30);

        cologo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dicons/Supportmaster.png"))); // NOI18N
        cologo.setText("jLabel6");
        userdashboardPanel.add(cologo);
        cologo.setBounds(450, 550, 580, 120);

        bgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bgimage2.png"))); // NOI18N
        userdashboardPanel.add(bgLabel);
        bgLabel.setBounds(0, 4, 1370, 820);

        homePanel.add(userdashboardPanel, "card4");

        jPanel2.add(homePanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        menuBar.setBackground(new java.awt.Color(255, 255, 255));

        jMenu7.setText("DASHBOARD");
        jMenu7.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu7MenuSelected(evt);
            }
        });
        jMenu7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu7ActionPerformed(evt);
            }
        });
        menuBar.add(jMenu7);

        homeMenu.setText("PROFILE");
        homeMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMenuMouseClicked(evt);
            }
        });
        homeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeMenuActionPerformed(evt);
            }
        });
        menuBar.add(homeMenu);

        jMenu3.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.setText("CLIENTS");

        jMenuItem3.setText("New Client");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseClicked(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Clients List");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        menuBar.add(jMenu3);

        jMenu4.setBackground(new java.awt.Color(255, 255, 255));
        jMenu4.setText("CONTRACTS");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });

        jMenuItem18.setText("Supply Request");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem18);

        jMenuItem22.setText("Billing Requests");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem22);

        newcontractMenu.setText(" Contracts");
        newcontractMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newcontractMenuMouseClicked(evt);
            }
        });
        newcontractMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newcontractMenuActionPerformed(evt);
            }
        });
        jMenu4.add(newcontractMenu);

        jMenuItem19.setText("Contract Report");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem19);

        jMenuItem17.setText("PM Schedules");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem17);

        jMenuItem15.setText("Contract Equipment");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        menuBar.add(jMenu4);

        groupsMenu.setBackground(new java.awt.Color(255, 255, 255));
        groupsMenu.setText("CALLS");
        groupsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsMenuActionPerformed(evt);
            }
        });

        opencallsMenu.setBackground(new java.awt.Color(255, 255, 255));
        opencallsMenu.setText("New Call");
        opencallsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opencallsMenuActionPerformed(evt);
            }
        });
        groupsMenu.add(opencallsMenu);

        jMenuItem5.setText("Update Call");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        groupsMenu.add(jMenuItem5);

        jMenuItem14.setText("Manage Service");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        groupsMenu.add(jMenuItem14);

        jMenuItem8.setText("Track");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        groupsMenu.add(jMenuItem8);

        menuBar.add(groupsMenu);

        jMenu2.setText("CLAIMS");

        jMenuItem6.setText("Create");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem13.setText("Print");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        menuBar.add(jMenu2);

        jMenu1.setText("GATEPASS");

        jMenuItem11.setText("IN");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem11);

        jMenuItem12.setText("OUT");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem12);

        menuBar.add(jMenu1);

        jMenu6.setText("PART STOCK");
        jMenu6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu6ActionPerformed(evt);
            }
        });

        jMenuItem20.setText("STOCK IN");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem20);

        jMenuItem21.setText("STOCK OUT");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem21);

        menuBar.add(jMenu6);

        jMenu5.setText("SETTINGS");

        jMenuItem10.setText("Staff ");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem10);

        jMenuItem2.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem2.setText("User Accounts");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        jMenuItem7.setText("Mileage Settings");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem9.setText("System");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuItem1.setText("Change Password");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        jMenuItem16.setText("User Log");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        menuBar.add(jMenu5);

        logoutMenu.setText("LOG OUT");
        logoutMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMenuMouseClicked(evt);
            }
        });
        logoutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuActionPerformed(evt);
            }
        });
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opencallsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opencallsMenuActionPerformed
Calls nj=new Calls();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(nj.callsPanel); 
 
   nj.getTechnician();
   
     int year=LocalDate.now().getYear();    
          Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
   nj.admincallnumberTxt.setText(String.valueOf(""+year+"/"+loannumber)); 
   LocalDate todaydate=LocalDate.now();
 String  callreqDate=(DateTimeFormatter.ofPattern("dd MMM,yyyy").format(todaydate));  
   nj.calldateTxt.setText(callreqDate);    
  
   nj.selectedcall="New call";
   nj.technician=owner;  
  nj.staffnameTxt.setText(staffname);  
      nj.usertype=USERTYPE;
     nj.staffname=staffname;
     
  if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
  nj.callchangerPanel.removeAll();
       nj.callchangerPanel.repaint();
      nj.callchangerPanel.revalidate();
      nj.callchangerPanel.add(nj.necallsholderPanel);
               
    nj.necallsholderPanel.removeAll();    
       nj.necallsholderPanel.repaint();
       nj.necallsholderPanel.revalidate();
      nj.necallsholderPanel .add(nj.adminnewcallsPanel);
    
  nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker); 
  
    nj.savebtnholderPanel.removeAll();
  nj.savebtnholderPanel.repaint();
  nj.savebtnholderPanel.revalidate();
   nj.savebtnholderPanel.add(nj.adminsaveBtn); 
  }else{
     
        nj.callchangerPanel.removeAll();
       nj.callchangerPanel.repaint();
      nj.callchangerPanel.revalidate();
      nj.callchangerPanel.add(nj.necallsholderPanel);
      
        nj.necallsholderPanel.removeAll();    
       nj.necallsholderPanel.repaint();
       nj.necallsholderPanel.revalidate();
      nj.necallsholderPanel .add(nj.usersnewcallPanel);
      
   nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker);
  
  nj.savebtnholderPanel.removeAll();
  nj.savebtnholderPanel.repaint();
  nj.savebtnholderPanel.revalidate();
   nj.savebtnholderPanel.add(nj.usersaveBtn);
  }    
    }//GEN-LAST:event_opencallsMenuActionPerformed

    private void newcontractMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newcontractMenuActionPerformed
 if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
        Contracts cn=new Contracts();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(cn.contractsPanel);
  cn. getContracts();
     //   lr.currentuser=currentowner;
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_newcontractMenuActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
if(USERTYPE.equalsIgnoreCase("Administrator")){
 Settings st= new Settings();
   st.getSettings(); 
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(st.jPanel1);
st.getSettings();
}
else{
    
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
}
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
if(USERTYPE.equalsIgnoreCase("Administrator")){
      Users us=new Users();
   us.getUsers();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(us.useraccountsPanel);
}
else{JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);}
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void logoutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuActionPerformed
   
    }//GEN-LAST:event_logoutMenuActionPerformed

    private void homeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeMenuActionPerformed
Userprofile nf=new Userprofile();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(nf.userprofilePanel);
  nf. getSettings();
  
    }//GEN-LAST:event_homeMenuActionPerformed
public void Login(){
   // LocalTimestamp timestamp=LocalTimestamp.
        LocalTime localTime=LocalTime.now();
    Time date=Time.valueOf(localTime); 
      System.out.println("Login time "+stf.format(date)); 
        logintime=  String.valueOf(date); 
        System.out.println("sql DATE "+logintime);
        java.sql.Date year= java.sql.Date.valueOf(LocalDate.now());    
          Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(100000-1)+1;
   sessionID= String.valueOf("LOG"+year+"/"+loannumber);

   if(selectedusername.equalsIgnoreCase("--Select Username--")){
            JOptionPane.showMessageDialog(null, "Please select or enter Username ! ", "Username Required", JOptionPane.WARNING_MESSAGE);
        }else if(pwordTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter Password!","Passowrd Required",JOptionPane.WARNING_MESSAGE);
        }else{
           
      try {
    String accountstatus=""; 
          Connection connectDb=(Connection)DriverManager.getConnection(server, username,dbpword);
String checksql="SELECT USERS.USERNAME, USERS.PASSWORD, USERS.OWNER, USERS.TYPE, STAFF.STAFFNAME FROM USERS "
        + "LEFT JOIN STAFF ON USERS.OWNER=STAFF.STAFFNO WHERE USERS.USERNAME='"+selectedusername+"'"; 
          PreparedStatement pst3=(PreparedStatement)connectDb.prepareStatement(checksql);
          ResultSet rst3=pst3.executeQuery(); int y3=0;
          while(rst3.next()){
        username2=rst3.getString(1);
        pword=rst3.getString(2);
        owner=rst3.getString(3);
        USERTYPE=rst3.getString(4);
        staffname=rst3.getString(5);
         }
 System.out.println("Username "+username2+" pword "+pword+" owner "+owner);
 
String checkstatus="SELECT STATUS, TYPE FROM USERS WHERE OWNER='"+owner+"'"; 
 PreparedStatement pst6=(PreparedStatement)connectDb.prepareStatement(checkstatus);
          ResultSet rst6=pst6.executeQuery(); int x=0;
          while(rst6.next()){
          accountstatus=rst6.getString(1);
          }
  if(accountstatus.equalsIgnoreCase("LOCKED")){
  JOptionPane.showMessageDialog(null, "Account is locked! Please contact administrator for assistance!","Account Locked",JOptionPane.WARNING_MESSAGE);
 }else  if(accountstatus.equalsIgnoreCase("UNLOCKED")){   
System.out.println(" login trials "+trialcounter);
 if(trialcounter>3){
 JOptionPane.showMessageDialog(null, "You have tried 3 unsuccessful logins! Account locked, contact administrator!");
 }
 else{
  if(selectedusername.equals(username2)){
  if((md5(pwordTxt.getText())).equals((pword))){
      trialcounter=0;
   String storesession="INSERT INTO SESSION VALUES(?,?,?)";
  PreparedStatement pst45=(PreparedStatement)connectDb.prepareStatement(storesession);
   pst45.setString(1, owner); 
     pst45.setString(2, username2); 
       pst45.setString(3, pword);     
  pst45.executeUpdate();
  System.out.println("Home owner is "+owner);
  
  String registerlogin="INSERT INTO USERLOG VALUES(?,?,?,?,?,?,?)";
   PreparedStatement pst47=(PreparedStatement)connectDb.prepareStatement(registerlogin);
   pst47.setString(1, sessionID); 
   pst47.setString(2, owner); 
     pst47.setString(3, staffname); 
     LocalDate localDate = LocalDate.now();
 java.util.Date date3=Date.valueOf(localDate);
 java.sql.Date sqltoday=new java.sql.Date(date3.getTime());
       pst47.setDate(4, sqltoday);  
        pst47.setString(5, logintime); 
    pst47.setString(6, "ACTIVE"); 
     pst47.setString(7, "ACTIVE"); 
    pst47.executeUpdate();
    
 JOptionPane.showMessageDialog(null,staffname+" Welcome!","Welcome",JOptionPane.INFORMATION_MESSAGE);
//loadDashboard();
  selectedusername=null;
     pwordTxt.setText(null);
 
     Userprofile nf=new Userprofile();
      nf.searchPanel.removeAll();
 nf.searchPanel.repaint();
   nf.searchPanel.revalidate();
   // homePanel.add(nf.userprofilePanel);
  nf. getSettings();
    nf.currentowner=owner;
    LocalDate today=LocalDate.now();
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMM, yyyy");
     java.util.Date today2=Date.valueOf(today); 
     nf.dateLabel.setText(sdf.format(today2)+"|");
      nf.currentuserTxt.setText(staffname);
  nf.usertypeLabel.setText(" |"+USERTYPE); 
  nf.usertype=USERTYPE;
  
 if(USERTYPE.equalsIgnoreCase("USER")){
    jLabel10.setText(staffname+" | "); jLabel11.setText(USERTYPE);        
     nf.searchPanel.add(nf.searchcallnoTxt);
  homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
    homePanel.add(userdashboardPanel);
      menuBar.setVisible(true);
          checkCallstatus();
      }else{
  loggedinuserLabel.setText(staffname+" | "); usertypeLabel.setText(USERTYPE); 
     nf.searchPanel.add(nf.searchstaffTxt);
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
    homePanel.add(dashboardPanel);
    menuBar.setVisible(true);
        checkCallstatus();
      }  
     
  }
  
  else{
  JOptionPane.showMessageDialog(null, " Your Password is Incorrect!","Access Denied",JOptionPane.ERROR_MESSAGE);
  trialcounter=trialcounter+1;
    if(trialcounter==3&&USERTYPE.equalsIgnoreCase("user")){
  String updatestatus="UPDATE USERS SET STATUS='LOCKED' WHERE OWNER='"+owner+"'";
PreparedStatement pst5=(PreparedStatement)connectDb.prepareStatement(updatestatus);
int t=pst5.executeUpdate();
if(t>0){
JOptionPane.showMessageDialog(null, "Your account has been locked! Please contact administrator for assistance!","Account Locked",JOptionPane.WARNING_MESSAGE);
//
}
  } else if(trialcounter==3&&USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
  JOptionPane.showMessageDialog(null, "You have tried 3 unsuccessful logins! System will exit!","System Exiting...",JOptionPane.WARNING_MESSAGE);
 System.exit(0);
  }
  }
   }
  else{
    JOptionPane.showMessageDialog(null, "Your Password is Incorrect!","Access Denied",JOptionPane.ERROR_MESSAGE);
  if(trialcounter==3){
  String updatestatus="UPDATE USERS SET STATUS='LOCKED' WHERE OWNER='"+owner+"'";
PreparedStatement pst5=(PreparedStatement)connectDb.prepareStatement(updatestatus);
int t=pst5.executeUpdate();
if(t>0){
JOptionPane.showMessageDialog(null, "Your account has been locked! Please contact administrator for assistance!","Account Locked",JOptionPane.WARNING_MESSAGE);
//System.exit(0);
}
  }
  }  
 }
   } 
 else {
 
 }
      }catch (SQLException ex) {
          Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
      }
        }
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      Login();
      
    }//GEN-LAST:event_jButton1ActionPerformed
public String getUser(String technician){
  technician= owner;
    return technician;
   } 

public void loadDashboard(){

 if(USERTYPE.equalsIgnoreCase("USER")){
    jLabel10.setText(staffname+" | "); jLabel11.setText(USERTYPE);        
   ///  nf.searchPanel.add(nf.searchcallnoTxt);
  homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
    homePanel.add(userdashboardPanel);
      menuBar.setVisible(false);
      }else{
  loggedinuserLabel.setText(staffname+" | "); usertypeLabel.setText(USERTYPE); 
   //  nf.searchPanel.add(nf.searchstaffTxt);
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
    homePanel.add(dashboardPanel);
    menuBar.setVisible(true);
      }  
}

    private void logoutMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMenuMouseClicked
       int choice=JOptionPane.showConfirmDialog(null, "Do you want to Log out?","Log Out",JOptionPane.YES_NO_OPTION);
      if(choice==JOptionPane.YES_OPTION){
       homePanel.removeAll();
  homePanel.repaint();
homePanel.revalidate();
homePanel.add(loginPanel);
menuBar.setVisible(false);

  logoutSession();
   getUsernames();
       }
    }//GEN-LAST:event_logoutMenuMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
  
    }//GEN-LAST:event_jMenu4MouseClicked

    private void newcontractMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newcontractMenuMouseClicked
      
    }//GEN-LAST:event_newcontractMenuMouseClicked

    private void pwordTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwordTxtActionPerformed
      Login();
    }//GEN-LAST:event_pwordTxtActionPerformed

    private void groupsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsMenuActionPerformed
//Groups g=new Groups();

homePanel.removeAll();
 homePanel.repaint();
homePanel.revalidate();
//homePanel.add(g.groupsPanel); 

    }//GEN-LAST:event_groupsMenuActionPerformed

    private void homeMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMenuMouseClicked
Userprofile nf=new Userprofile();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(nf.userprofilePanel);
  nf.getSettings();
  
 LocalDate today=LocalDate.now();
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMM, yyyy");
     java.util.Date today2=Date.valueOf(today); 
     nf.dateLabel.setText(sdf.format(today2)+"|");
      nf.currentuserTxt.setText(staffname);
nf.usertypeLabel.setText(" |"+USERTYPE); 
  nf.searchPanel.removeAll();
 nf.searchPanel.repaint();
   nf.searchPanel.revalidate();
     if(USERTYPE.equalsIgnoreCase("USER")){
     nf.searchPanel.add(nf.searchcallnoTxt);
     }else{
     nf.searchPanel.add(nf.searchstaffTxt);
     }
 nf.currentowner=owner; 
 //JOptionPane.showMessageDialog(null, "Currentowner "+owner);
nf.getUser(); nf.getstaffCalls();  nf.getServiceRecords(); 
    }//GEN-LAST:event_homeMenuMouseClicked

   public void logoutSession(){
    try {
     
     String getusersql="DELETE FROM SESSION WHERE USER='"+owner+"'";    
        Connection connectDb=(Connection)DriverManager.getConnection(server, username,dbpword);
        PreparedStatement pst=(PreparedStatement)connectDb.prepareStatement(getusersql); 
     pst.executeUpdate(); //int i=1;
  String registerlogin="UPDATE USERLOG SET LOGOUT_DATE=?, LOGOUT_TIME=? WHERE LOGIN_ID='"+sessionID+"' AND USER_ID='"+owner+"'";
   PreparedStatement pst47=(PreparedStatement)connectDb.prepareStatement(registerlogin);
   
     LocalDate localDate=LocalDate.now();
     
 java.util.Date date3=Date.valueOf(localDate);
  java.sql.Date sqltoday=new java.sql.Date(date3.getTime());
       pst47.setString(1, String.valueOf(sqltoday));  
       LocalTime localTime=LocalTime.now();
     System.out.println("Logout time "+localTime);
    Time date=Time.valueOf(localTime); 
    logouttime=String.valueOf(date);
         pst47.setString(2, logouttime); 
     pst47.executeUpdate(); 
     
     } catch (SQLException ex) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
    }
   } 
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
logoutSession();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
changePword();
    }//GEN-LAST:event_jMenuItem1ActionPerformed
public void changePword(){
  Pwordchange up=new Pwordchange(); 
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(up.userprofilePanel);
   
  up.jTextField1.setText(owner); 
  up.jTextField2.setText(username2);
}
    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
printClaim();
    }//GEN-LAST:event_jMenuItem13ActionPerformed
public void printClaim(){
  Mileage d=new Mileage();
 homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(d.mileagePanel);
  
  d.printedby=staffname;
  d.currentuser= owner;
    d.usertype= USERTYPE;
   
    if(USERTYPE.equalsIgnoreCase("user")){
    d.searchstaffPanel.removeAll();
 d.searchstaffPanel.repaint();
   d.searchstaffPanel.revalidate();
  d.searchstaffPanel.add(d.searchdatePanel);
    }
  d.getClaims();

}
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

   // Loantracking lt=new Loantracking();
 //lt. getLoans();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseClicked

    }//GEN-LAST:event_jMenuItem3MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
 if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
        Clients2 C=new Clients2();
 homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(C.jPanel1);
   C.getclientsList();
   
     int year=LocalDate.now().getYear();    
 Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
  C.cleintnoTxt.setText(String.valueOf("TSC-"+year+"/"+loannumber)); 
   }
 else{
 JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
 if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
        Clients2 C=new Clients2();
 homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(C.jPanel1);
   C.getclientsList();
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
updateCall();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    public void updateCall(){
         Calls nj=new Calls();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(nj.callsPanel); 
  nj.usertype=USERTYPE; 
            nj.callchangerPanel.removeAll();
            nj.callchangerPanel.repaint();
            nj.callchangerPanel.revalidate();
            nj.callchangerPanel.add(nj.existingcallPanel);
               nj.selectedcall="existing call";
                 if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
  nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker);
  
  }else{
   nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker);
  } 
    }
    
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
  
    createClaim();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    public void createClaim(){
      Createmileage cm=new Createmileage();
     homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(cm.mileagecreationPanel); 
     cm.currentowner= owner;
       cm.usertype= USERTYPE;
     cm.getServiceRecords(); 
 
     if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
  cm.unlockclaimPanel.removeAll();
  cm.unlockclaimPanel.repaint();
cm.unlockclaimPanel.revalidate();
cm.unlockclaimPanel.add(cm.unlockholderPanel);
   }else{
   cm.unlockclaimPanel.removeAll();
  cm.unlockclaimPanel.repaint();
cm.unlockclaimPanel.revalidate();
 }  
     
    }
    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
   if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
        Mileagesettings ms=new Mileagesettings();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ms.mileagesettingsPanel); 
   ms.getStaffs();
   }
   else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
   }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
 if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
        Calltracking ct=new Calltracking();
  homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ct.calltrackingPanel); 
   ct. getServiceRecords();
   ct.currentuser=staffname;
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
     if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
         Staff ct=new Staff();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ct.staffPanel); 
   ct.getStaffs();
     }
     else{
 JOptionPane.showMessageDialog(null, "Access Denied!","Restricted",JOptionPane.ERROR_MESSAGE);
     }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
   if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {Gatepass gp=new Gatepass();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(gp.gatepassPanel); 
 gp.currentuser=staffname; 
gp.getGIN(); 
 gp. getEquipment();
   }
   else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
   }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
     if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {   
       Gatepassout gpo=new Gatepassout();
     homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(gpo.gatepassoutPanel); 
 gpo.currentuser=staffname;  
 gpo.getEquipment();
   }
     else{
   JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
     }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
    Editservice es=new Editservice();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(es.editserviceholderPanel); 
   es.currentowner= owner;
   es.usertype=USERTYPE;    es.getServiceRecords();
   es.getTechnician(); es.currentuser=staffname;
   
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
 if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
        Equipment eq=new Equipment();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(eq.equipmentPanel);  
   eq.getEquipment();
   eq.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
     if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
       Userlog ul=new Userlog();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ul.userlogPanel);  
   ul.getUserlogs();
  // ul.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
      if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
     PM_Schedule pms=new PM_Schedule();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(pms.pmschedulePanel);  
   pms.getTechnicians();
   pms.getEquipment();
   pms.getClients();
pms.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
         if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
    Supplyrequest csr=new Supplyrequest();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(csr.csrPanel);  
   csr.getCSR();
  csr.getStaff();
  csr.getCSRnum();
  csr.getCSRStaff();
csr.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
     
    if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
    Contractreports cr=new Contractreports();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(cr.contractreportPanel);  
   
   Mileagereport cr2=new Mileagereport();
   cr.mileagereportPanel.removeAll();
    cr.mileagereportPanel.repaint();
     cr.mileagereportPanel.revalidate();
      cr.mileagereportPanel.add(cr2.contractreportPanel);
    
   cr2.getTechnicians();
   cr2.getContracts();
  cr2.getClients() ;
  cr2.getReport();
//cr.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenu6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu6ActionPerformed
 
    }//GEN-LAST:event_jMenu6ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        
    if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
Partstock ps=new Partstock();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ps.partstockPanel);  
 ps. getStock();
 ps.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
       if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
Partstockout ps=new Partstockout();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ps.partstockoutPanel);  
 ps. getStock();
 ps.getTechnician();
 ps.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
         if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
       Billingrequest br=new Billingrequest();
       homePanel.removeAll();
       homePanel.repaint();
       homePanel.revalidate();
       homePanel.add(br.billrequestPanel);
       br.getCSR();
       br.countRequests();
       //ps. getStock();
       //ps.getTechnician();
     br.currentuser=staffname;
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void contractsmenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractsmenuBtnActionPerformed
       if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
Partstock ps=new Partstock();
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ps.partstockPanel);  
 ps. getStock();
 ps.currentuser=staffname;  
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
 }
    }//GEN-LAST:event_contractsmenuBtnActionPerformed

    private void contractsmenuBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractsmenuBtn1ActionPerformed
        Createmileage cm=new Createmileage();
     homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(cm.mileagecreationPanel); 
     cm.currentowner= owner;
       cm.usertype= USERTYPE;
     cm.getServiceRecords(); 
 
     if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
  cm.unlockclaimPanel.removeAll();
  cm.unlockclaimPanel.repaint();
cm.unlockclaimPanel.revalidate();
cm.unlockclaimPanel.add(cm.unlockholderPanel);
   }else{
   cm.unlockclaimPanel.removeAll();
  cm.unlockclaimPanel.repaint();
cm.unlockclaimPanel.revalidate();
 }  
     
    }//GEN-LAST:event_contractsmenuBtn1ActionPerformed

    private void contractsmenuBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractsmenuBtn2ActionPerformed
       if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
        Contracts cn=new Contracts();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(cn.contractsPanel);
  cn. getContracts();
     //   lr.currentuser=currentowner;
   }else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_contractsmenuBtn2ActionPerformed

    private void contractsmenuBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractsmenuBtn3ActionPerformed
       if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {
        Clients2 C=new Clients2();
 homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(C.jPanel1);
   C.getclientsList();
   
     int year=LocalDate.now().getYear();    
 Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
  C.cleintnoTxt.setText(String.valueOf("TSC-"+year+"/"+loannumber)); 
   }
 else{
 JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_contractsmenuBtn3ActionPerformed

    private void profielmenuBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profielmenuBtn2ActionPerformed
    Userprofile nf=new Userprofile();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(nf.userprofilePanel);
   
      nf.searchPanel.removeAll();
 nf.searchPanel.repaint();
   nf.searchPanel.revalidate();
   // homePanel.add(nf.userprofilePanel);
  nf. getSettings();
    nf.currentowner=owner;
    LocalDate today=LocalDate.now();
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMM, yyyy");
     java.util.Date today2=Date.valueOf(today); 
     nf.dateLabel.setText(sdf.format(today2)+"|");
      nf.currentuserTxt.setText(staffname);
  nf.usertypeLabel.setText(" |"+USERTYPE); 
nf.getstaffCalls(); nf.getServiceRecords(); nf.getSettings(); nf. getUser();
 
    }//GEN-LAST:event_profielmenuBtn2ActionPerformed

    private void callsmenuBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callsmenuBtn1ActionPerformed
      Calls nj=new Calls();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(nj.callsPanel); 
 
   nj.getTechnician();
   
     int year=LocalDate.now().getYear();    
          Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
   nj.admincallnumberTxt.setText(String.valueOf(""+year+"/"+loannumber)); 
   LocalDate todaydate=LocalDate.now();
 String  callreqDate=(DateTimeFormatter.ofPattern("dd MMM,yyyy").format(todaydate));  
   nj.calldateTxt.setText(callreqDate);    
  
   nj.selectedcall="New call";
   nj.technician=owner;  
  nj.staffnameTxt.setText(staffname);  
      nj.usertype=USERTYPE;
     nj.staffname=staffname;
     
  if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
  nj.callchangerPanel.removeAll();
       nj.callchangerPanel.repaint();
      nj.callchangerPanel.revalidate();
      nj.callchangerPanel.add(nj.necallsholderPanel);
               
    nj.necallsholderPanel.removeAll();    
       nj.necallsholderPanel.repaint();
       nj.necallsholderPanel.revalidate();
      nj.necallsholderPanel .add(nj.adminnewcallsPanel);
    
  nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker); 
  
    nj.savebtnholderPanel.removeAll();
  nj.savebtnholderPanel.repaint();
  nj.savebtnholderPanel.revalidate();
   nj.savebtnholderPanel.add(nj.adminsaveBtn); 
  }else{
     
        nj.callchangerPanel.removeAll();
       nj.callchangerPanel.repaint();
      nj.callchangerPanel.revalidate();
      nj.callchangerPanel.add(nj.necallsholderPanel);
      
        nj.necallsholderPanel.removeAll();    
       nj.necallsholderPanel.repaint();
       nj.necallsholderPanel.revalidate();
      nj.necallsholderPanel .add(nj.usersnewcallPanel);
      
   nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker);
  
  nj.savebtnholderPanel.removeAll();
  nj.savebtnholderPanel.repaint();
  nj.savebtnholderPanel.revalidate();
   nj.savebtnholderPanel.add(nj.usersaveBtn);
  } 
    }//GEN-LAST:event_callsmenuBtn1ActionPerformed

    private void callsmenuBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callsmenuBtn3ActionPerformed
       if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR"))
   {Gatepass gp=new Gatepass();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(gp.gatepassPanel); 
 gp.currentuser=staffname; 
gp.getGIN(); 
 gp. getEquipment();
   }
   else{
JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);
   }
    }//GEN-LAST:event_callsmenuBtn3ActionPerformed

    private void callsmenuBtn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callsmenuBtn5ActionPerformed
if(USERTYPE.equalsIgnoreCase("Administrator")){
      Users us=new Users();
   us.getUsers();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
  homePanel.add(us.useraccountsPanel);
}
else{JOptionPane.showMessageDialog(null, "Access Denied!"," Restricted",JOptionPane.ERROR_MESSAGE);}      
    }//GEN-LAST:event_callsmenuBtn5ActionPerformed

    private void callsmenuBtn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callsmenuBtn4ActionPerformed
      if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
         Staff ct=new Staff();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(ct.staffPanel); 
   ct.getStaffs();
     }
     else{
 JOptionPane.showMessageDialog(null, "Access Denied!","Restricted",JOptionPane.ERROR_MESSAGE);
     }
    }//GEN-LAST:event_callsmenuBtn4ActionPerformed

    private void callsmenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callsmenuBtnActionPerformed
        int choice=JOptionPane.showConfirmDialog(null, "Do you want to Log out?","Log Out",JOptionPane.YES_NO_OPTION);
      if(choice==JOptionPane.YES_OPTION){
       homePanel.removeAll();
  homePanel.repaint();
homePanel.revalidate();
homePanel.add(loginPanel);
menuBar.setVisible(false);

  logoutSession();
       }
    }//GEN-LAST:event_callsmenuBtnActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int choice=JOptionPane.showConfirmDialog(null, "Do you want to Log out?","Log Out",JOptionPane.YES_NO_OPTION);
      if(choice==JOptionPane.YES_OPTION){
       homePanel.removeAll();
  homePanel.repaint();
homePanel.revalidate();
homePanel.add(loginPanel);
menuBar.setVisible(false);

  logoutSession();
       }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int choice=JOptionPane.showConfirmDialog(null, "Do you want to Log out?","Log Out",JOptionPane.YES_NO_OPTION);
      if(choice==JOptionPane.YES_OPTION){
       homePanel.removeAll();
  homePanel.repaint();
homePanel.revalidate();
homePanel.add(loginPanel);
menuBar.setVisible(false);

  logoutSession();
       }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
 Userprofile nf=new Userprofile();
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(nf.userprofilePanel);
   
      nf.searchPanel.removeAll();
 nf.searchPanel.repaint();
   nf.searchPanel.revalidate();
   // homePanel.add(nf.userprofilePanel);
  nf. getSettings();
    nf.currentowner=owner;
    LocalDate today=LocalDate.now();
    SimpleDateFormat sdf=new SimpleDateFormat("dd MMM, yyyy");
     java.util.Date today2=Date.valueOf(today); 
     nf.dateLabel.setText(sdf.format(today2)+"|");
      nf.currentuserTxt.setText(staffname);
  nf.usertypeLabel.setText(" |"+USERTYPE); 
nf. getUser(); nf.getstaffCalls(); nf.getServiceRecords(); nf.getSettings(); 
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
   Calls nj=new Calls();
   homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
   homePanel.add(nj.callsPanel); 
    nj.getTechnician();
    int year=LocalDate.now().getYear();    
          Random randomnumber=new Random(); int loannumber;
  loannumber=randomnumber.nextInt(10000-1)+1;
   nj.admincallnumberTxt.setText(String.valueOf(""+year+"/"+loannumber)); 
   LocalDate todaydate=LocalDate.now();
 String  callreqDate=(DateTimeFormatter.ofPattern("dd MMM,yyyy").format(todaydate));  
   nj.calldateTxt.setText(callreqDate);    
  
   nj.selectedcall="New call";
   nj.technician=owner;  
  nj.staffnameTxt.setText(staffname);  
      nj.usertype=USERTYPE;
     nj.staffname=staffname;
     
  if(USERTYPE.equalsIgnoreCase("ADMINISTRATOR")){
  nj.callchangerPanel.removeAll();
       nj.callchangerPanel.repaint();
      nj.callchangerPanel.revalidate();
      nj.callchangerPanel.add(nj.necallsholderPanel);
               
    nj.necallsholderPanel.removeAll();    
       nj.necallsholderPanel.repaint();
       nj.necallsholderPanel.revalidate();
      nj.necallsholderPanel .add(nj.adminnewcallsPanel);
    
  nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker); 
  
    nj.savebtnholderPanel.removeAll();
  nj.savebtnholderPanel.repaint();
  nj.savebtnholderPanel.revalidate();
   nj.savebtnholderPanel.add(nj.adminsaveBtn); 
  }else{
     
        nj.callchangerPanel.removeAll();
       nj.callchangerPanel.repaint();
      nj.callchangerPanel.revalidate();
      nj.callchangerPanel.add(nj.necallsholderPanel);
      
        nj.necallsholderPanel.removeAll();    
       nj.necallsholderPanel.repaint();
       nj.necallsholderPanel.revalidate();
      nj.necallsholderPanel .add(nj.usersnewcallPanel);
      
   nj.servicedatePanel.removeAll();
   nj.servicedatePanel.repaint();
   nj.servicedatePanel.revalidate();
  nj.servicedatePanel.add(nj.servicedatePicker);
  
  nj.savebtnholderPanel.removeAll();
  nj.savebtnholderPanel.repaint();
  nj.savebtnholderPanel.revalidate();
   nj.savebtnholderPanel.add(nj.usersaveBtn);
  }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      updateCall();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       createClaim();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
     printClaim();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
     changePword();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jMenu7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu7ActionPerformed
    
   // menuBar.setVisible(true);
     
    }//GEN-LAST:event_jMenu7ActionPerformed

    private void jMenu7MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu7MenuSelected
      if(USERTYPE.equalsIgnoreCase("USER")){
   jLabel10.setText(staffname+" | "); jLabel11.setText(USERTYPE);        
    homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
    homePanel.add(userdashboardPanel);
      //menuBar.setVisible(true);
      }else{
  loggedinuserLabel.setText(staffname+" | "); usertypeLabel.setText(USERTYPE); 
   //  nf.searchPanel.add(nf.searchstaffTxt);
        homePanel.removeAll();
   homePanel.repaint();
   homePanel.revalidate();
    homePanel.add(dashboardPanel);
      }
    }//GEN-LAST:event_jMenu7MenuSelected

    private void usernameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTxtActionPerformed
      selectedusername=""+usernameTxt.getSelectedItem();
    }//GEN-LAST:event_usernameTxtActionPerformed
public void getStatement(){

}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      /* try{
      // Runtime.getRuntime().exec("C:/xampp/xampp_start.exe");
      
       } catch (IOException ex) {
             Logger.getLogger(Home1.class.getName()).log(Level.SEVERE, null, ex);
         }
       */
       /*  try{
      Thread.sleep(5000);
       } catch(InterruptedException e){
           e.printStackTrace();
       } */
         
    
        try{
        for(javax.swing.UIManager.LookAndFeelInfo info:
                javax.swing.UIManager.getInstalledLookAndFeels()){
            if("Windows".equals(info.getName())){
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        } }
        catch(ClassNotFoundException e){} catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BGlABEL;
    private javax.swing.JLabel bgLabel;
    private javax.swing.JButton callsmenuBtn;
    private javax.swing.JButton callsmenuBtn1;
    private javax.swing.JButton callsmenuBtn3;
    private javax.swing.JButton callsmenuBtn4;
    private javax.swing.JButton callsmenuBtn5;
    private javax.swing.JLabel cologo;
    private javax.swing.JLabel cologoLabel;
    private javax.swing.JButton contractsmenuBtn;
    private javax.swing.JButton contractsmenuBtn1;
    private javax.swing.JButton contractsmenuBtn2;
    private javax.swing.JButton contractsmenuBtn3;
    public javax.swing.JPanel dashboardPanel;
    private javax.swing.JMenu groupsMenu;
    private javax.swing.JMenu homeMenu;
    public javax.swing.JPanel homePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel loggedinuserLabel;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JMenu logoutMenu;
    public javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newcontractMenu;
    private javax.swing.JMenuItem opencallsMenu;
    private javax.swing.JButton profielmenuBtn2;
    private javax.swing.JPasswordField pwordTxt;
    public javax.swing.JPanel userdashboardPanel;
    private javax.swing.JComboBox<String> usernameTxt;
    private javax.swing.JLabel usertypeLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent we) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent we) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent we) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent we) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent we) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
