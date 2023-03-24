package gui;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static java.lang.Class.forName;

public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JButton updateButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JTable table1;
    private JTextField txtid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public  void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/swingemployee","root","");
            System.out.println("Ok connexion etabli");

        }
        catch (ClassNotFoundException ex)
        {

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empname, salary,mobile;

                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();


                try{
                    pst = con.prepareStatement("INSERT INTO employee (empname, salary, mobile) VALUES(?,?,?)");
                    pst.setString(1,empname);
                    pst.setString(2,salary);
                    pst.setString(3,mobile);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Employee Addeddddddd ! ! !");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }

    // affichage du tableau
    void table_load(){

        try{
            pst = con.prepareStatement("SELECT * FROM employee");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
