package com.student.login;
import com.student.user.UserIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Login extends JFrame{
    JLabel img=new JLabel();
    JLabel usernameLabel= new JLabel("username");//用户名标签
    JLabel passwordLabel= new JLabel("password");//密码标签
    JTextField usernameText=new JTextField(18);//用户名文本框
    JTextField passwordText=new JTextField(18);//密码文本框
    JButton login=new JButton("Login");//登录按钮
    JButton register=new JButton("Register");//注册按钮
    JPanel jp=new JPanel();
    FlowLayout flowLayOut=new FlowLayout(FlowLayout.CENTER);


    public Login(){
        this.setTitle("Learning Journey Application");
        this.setSize(300,400);//窗口大小
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        init();//JFrame初始化
        this.setVisible(true);


    }
    public void init(){
        login.addActionListener(new LoginListen());//登录事件
        register.addActionListener(new RegisterListen());//注册事件
        img.setIcon(new ImageIcon("1.jpg"));//后面可以改logo
        jp.setLayout(flowLayOut);
        /*
        大家可以研究下怎么把页面布局调的好看一点
        现在的问题是flowLayout是从左到右放不下再另起一行
        可以优化为不同类型的组件另起一行
         */
        jp.add(img);
        jp.add(usernameLabel);
        jp.add(usernameText);
        jp.add(passwordLabel);
        jp.add(passwordText);
        jp.add(login);
        jp.add(register);
        this.add(jp);

    }
    class RegisterListen implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                FileWriter fw=new FileWriter("userInfo.txt",true);//写文件
                BufferedWriter bw=new BufferedWriter(fw);//缓冲流
                FileReader fr=new FileReader("userInfo.txt");//读文件
                BufferedReader br=new BufferedReader(fr);

                String s="";//读取每一行

                String name= usernameText.getText();//读取username用于确认是否注册过
                boolean flag=true;
                while ((s = br.readLine()) != null) {
                    String[] arr= s.split(" ");
                    if(arr[0].equals(name)){
                        flag=false;
                    }
                }
                /*
                之前的问题是当用户名相同但密码不同也能成功注册，
                这里我改成了判断依据是用户名相同而不是用户名和密码都相同
                大家还要补充一下输入的合法性
                目前的格式是username password
                要求就是用户名密码不能有空格也不能为空，其他的就看着办
                 */
                if(flag){
                    String info=usernameText.getText()+" "+passwordText.getText();
                    bw.write(info);
                    bw.newLine();
                    bw.flush();
                    bw.close();
                    JOptionPane.showMessageDialog(null,"Register successful!");//注册成功
                }else{
                    JOptionPane.showMessageDialog(null,"User exists!");//用户已存在
                }

            } catch (IOException e1){
            }
        }
    }
    class LoginListen implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                FileReader fr=new FileReader("userInfo.txt");//读文件
                BufferedReader br=new BufferedReader(fr);

                String s="";//读取每一行
                String info= usernameText.getText()+" "+passwordText.getText();//读取username用于确认是否注册过
                boolean flag=false;
                while ((s = br.readLine()) != null) {
                    if(s.equals(info)){
                        flag=true;
                    }
                }
                if(flag){
                    JOptionPane.showMessageDialog(null,"Login successful!");
                    Login.this.dispose();
                    new UserIndex();
                    /*
                    成功注册，跳转到用户首页
                    我还没写用户首页，就搞了个空白框，后面再做吧
                     */
                }else{
                    JOptionPane.showMessageDialog(null,"User name or password error!");
                }

            } catch (IOException e1){
            }
        }

    }
}
