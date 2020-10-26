package SanjeevaniApp.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import sanjeevniapp.pojo.DocPojo;
import SanjeevaniApp.dao.DoctorDao;
import SanjeevaniApp.dao.PatientDao;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sanjeevniapp.pojo.PatientPojo;

/**
 *
 * @author HP
 */
public class AddPatientFrame extends javax.swing.JFrame {
    private int refs;
    private String opd;
    private String f_name;
    private String s_name;
    private int age;
    private String p_id;
    private String gender;
    private String m_status;
    private String address;
    private String city;
    private String mo;
    private String BedNo;
    private String WardNo;
    private String doctor_id;
    private java.sql.Date date;
    private java.util.Date d;
    private ArrayList<DocPojo> doctor;
    private String active;
    /**
     * Creates new form AddPatientFrame
     */
    public AddPatientFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        getPatientNewId();
        loadDoctorId();
    }
    public void getPatientNewId()
    {
        d= new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat();
        String src = sdf.format(d);
        txtDate.setText(src);
        try {
        String id = PatientDao.getNewId();
            System.out.println(id);
        txtPid.setText(id);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Some Problem Occured"); 
        }
    }
    private void loadDoctorId()
    {
        try {
            doctor = DoctorDao.getDoctorDetails();
            for(DocPojo s:doctor)
            {
            String id=s.getDoctorid();
                jcDocId.addItem(id);
            }
        }
            catch(SQLException e)
                    {
                        e.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Some Problem Occured Please try Again Later!");
                    }
            
        }
    
//      private  void accessDenied()
//     {
//         String name=userprofile.getUsername();
//         if(name==null)
//         {
//             JOptionPane.showMessageDialog(null,"Access Denied Please Login First!","Error",JOptionPane.ERROR_MESSAGE);
//             Login frame = new Login();
//             frame.setVisible(true);
//             this.dispose();
//         }   
//     }
      private boolean validateInputs()
      {
          f_name = txtFname.getText();
          opd = txtOpd.getText();
          doctor_id =(String) jcDocId.getSelectedItem();
          s_name = txtSname.getText();
          age =Integer.parseInt( txtAge.getText());
          p_id = txtPid.getText();
          gender =(String) jcGender.getSelectedItem();
          m_status =(String) jcStatus.getSelectedItem();
          address = txtAddress.getText();
          city = txtCity.getText();
          mo = txtPhone.getText();
          date = new java.sql.Date(d.getTime());
          BedNo = jcBed.getSelectedItem().toString();
          WardNo = jcWard.getSelectedItem().toString();
          active=jcActive.getSelectedItem().toString();
          if(active.isEmpty()||f_name.isEmpty()||opd.isEmpty()||doctor_id.isEmpty()||s_name.isEmpty()||age==0||p_id.isEmpty()||gender.isEmpty()||m_status.isEmpty()||address.isEmpty()||city.isEmpty()||mo.isEmpty())
              return false;
          return true;
              }
    public void clear()
    {
        txtFname.setText("");
          txtOpd.setText("");
          txtSname.setText("");
          txtAge.setText("");
          txtPid.setText("");
          txtAddress.setText("");
          txtCity.setText("");
          txtPhone.setText("");
         // date = new java.sql.Date(d.getTime());
    }
      
      public String sendSms()
         {
             try
             {

                 
                 String apiKey = "apikey="+"wf/vrCEBFCI-tqs30qlDOplWoRI834RnP6qZ4Y8JaE";
                 String message = "&message="+" Your OTP is "+ refs+" And Patient Id:"+p_id+" from SANJEEVANI APP";

                 String sender = "& sender=" + "TXTLCL";
                 String numbers = "&numbers=" + mo;

                 URL url=new URL("https://api.textlocal.in/send/?");
                 HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                String data = apiKey + numbers + message + sender;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                OutputStream out= conn.getOutputStream();
                out.write(data.getBytes("UTF-8"));

                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader rd = new BufferedReader (isr);
                StringBuffer stringBuffer = new StringBuffer();
                
                String line;
                while((line = rd.readLine())!=null)
                {
                    stringBuffer.append(line);
                }
                rd.close();
                 System.out.println("in send sms"+stringBuffer);;
                 return stringBuffer.toString();
                 
                
             }
             catch(Exception e)
             {
                 System.out.println("Error SMS"+e);
                 return "Error"+0;
             }
         }
      public void addPatientDetails()
      {
          int ans;
          int count=3;
          refs = 1000+(int)(Math.random()*28);
          String message = sendSms();
          if(message.contains("Invalid number"))
          {
              JOptionPane.showMessageDialog(null,"Please enter valid mobile number","Wrong",JOptionPane.ERROR_MESSAGE);
              return;
          }
          do
          {
              ans = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter one time Password","OTP",JOptionPane.INFORMATION_MESSAGE));
              if(refs==ans)
              {
                   PatientPojo p = new PatientPojo(p_id,f_name,s_name,opd,gender,m_status,address,city,mo,doctor_id,date,age,WardNo,BedNo,active);
                  try
                  {
                      boolean result = PatientDao.addpatient(p);
                      if(result)
                      {
                          JOptionPane.showMessageDialog(null,"Success!","Appointment book successfully",JOptionPane.INFORMATION_MESSAGE);
                          clear();
                          ReceptionistOptionsFrame r = new ReceptionistOptionsFrame();
                         r.setVisible(true);
                          this.dispose();
                          break;
                      }
                      else
                      {
                          JOptionPane.showMessageDialog(null,"Failed","Something went Wrong while inserting data",JOptionPane.ERROR_MESSAGE);
                      }
                  }
                  catch(SQLException e)
                  {
                      e.printStackTrace();
                      JOptionPane.showMessageDialog(null,"Something went Wrong Exception in DB","Error",JOptionPane.ERROR_MESSAGE);
                      
                  }
            
            
            
                  
              }
              else
              {
                  JOptionPane.showMessageDialog(null,"Please Enter Valid OTP","Error",JOptionPane.ERROR_MESSAGE);
                  count--;
              }
              
          }
          while(count!=0);
      }
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPid = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jcWard = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jcBed = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jcGender = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtOpd = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jcDocId = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jcStatus = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        btnAddPatient1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jcActive = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 102));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Patient Id");

        txtPid.setEditable(false);
        txtPid.setBackground(new java.awt.Color(102, 255, 153));
        txtPid.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPidActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Ward No");

        jcWard.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jcWard.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "W1", "W2", "W3", "W4", "W5", "W6", "W7", "W8", "W9", "W10", "W11", "W12", "W13", "W14", "W15" }));
        jcWard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcWardActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Bed No");

        jcBed.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jcBed.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jcBed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcBedActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Gender");

        jcGender.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jcGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "TransGender" }));
        jcGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcGenderActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Date");

        txtDate.setBackground(new java.awt.Color(51, 255, 102));
        txtDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Phone No");

        txtPhone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        txtOpd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtOpd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOpdActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("OPD");

        jcDocId.setBackground(new java.awt.Color(153, 255, 153));
        jcDocId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jcDocId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        jcDocId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcDocIdActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Doctor Id");

        txtFname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtFname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFnameActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("First Name");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Second Name");

        txtSname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSnameActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Age");

        txtAge.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAgeActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Marial Status");

        jcStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jcStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Married", "UnMarried" }));
        jcStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcStatusActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("City");

        txtCity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Address");

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAddress.setRows(5);
        jScrollPane1.setViewportView(txtAddress);

        btnAddPatient1.setBackground(new java.awt.Color(255, 255, 51));
        btnAddPatient1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnAddPatient1.setText("Add Patient");
        btnAddPatient1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAddPatient1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPatient1ActionPerformed(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/patient.jpg"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(102, 255, 102));

        jLabel16.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel16.setText("ADD PATIENT");

        btnLogout.setBackground(new java.awt.Color(0, 153, 153));
        btnLogout.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(0, 153, 153));
        btnLogout.setText("Log Out");
        btnLogout.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnHome.setBackground(new java.awt.Color(255, 102, 102));
        btnHome.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnHome.setText("Home");
        btnHome.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(255, 0, 0));
        btnBack.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnBack.setText("Back");
        btnBack.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(btnHome)
                    .addComponent(btnBack))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLogout)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Active");

        jcActive.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jcActive.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "y", "n" }));
        jcActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcActiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel4))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel12))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtPhone)
                                            .addComponent(jcDocId, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtOpd, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtAge)
                                            .addComponent(jcStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(txtCity)
                                            .addComponent(jcGender, 0, 200, Short.MAX_VALUE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtPid, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtSname, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(53, 53, 53))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13))
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(60, 60, 60))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(50, 50, 50)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jcBed, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jcWard, 0, 189, Short.MAX_VALUE))
                                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(btnAddPatient1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(69, 69, 69)
                                .addComponent(jcActive, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel15))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jcWard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtPid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtSname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jcBed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOpd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jcActive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jcDocId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jcStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jcGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(33, 33, 33))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddPatient1)
                        .addGap(31, 31, 31))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        ReceptionistOptionsFrame l= new ReceptionistOptionsFrame();
        l.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        LoginFrame l = new LoginFrame();
        l.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnAddPatient1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPatient1ActionPerformed
        // TODO add your handling code here:
        try {
            if(validateInputs()==false)
            {
               JOptionPane.showMessageDialog(null,"Error","Please fill all fields first",JOptionPane.ERROR_MESSAGE);
               return;
            }
            if(age<0)
            {
                JOptionPane.showMessageDialog(null,"Error","Please input Valid Age",JOptionPane.ERROR_MESSAGE);
                return;
            }
           addPatientDetails();
     
                 
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null,"Error","please input Valid Age",JOptionPane.ERROR_MESSAGE);
        }
         
    }//GEN-LAST:event_btnAddPatient1ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        ReceptionistOptionsFrame p = new ReceptionistOptionsFrame();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtPidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPidActionPerformed

    private void jcBedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcBedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcBedActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void txtAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAgeActionPerformed

    private void txtFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameActionPerformed

    private void txtSnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSnameActionPerformed

    private void jcStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcStatusActionPerformed

    private void txtCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityActionPerformed

    private void jcWardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcWardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcWardActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    private void txtOpdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOpdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOpdActionPerformed

    private void jcGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcGenderActionPerformed

    private void jcDocIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcDocIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcDocIdActionPerformed

    private void jcActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcActiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcActiveActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AddPatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddPatientFrame f =new AddPatientFrame();
                f.setVisible(true);
                //f.accessDenied();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPatient1;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcActive;
    private javax.swing.JComboBox jcBed;
    private javax.swing.JComboBox jcDocId;
    private javax.swing.JComboBox jcGender;
    private javax.swing.JComboBox jcStatus;
    private javax.swing.JComboBox jcWard;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtFname;
    private javax.swing.JTextField txtOpd;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPid;
    private javax.swing.JTextField txtSname;
    // End of variables declaration//GEN-END:variables
}
