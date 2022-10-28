package MainWindow;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author Yu Mingzheng
 * @date 2022/10/18
 * @apiNote
 */
public class LoginFrame extends JFrame{
    // 编号textField
    private JTextField numTextField;
    // 登录按钮
    private JButton loginButton;

    private IO io;


    public LoginFrame(){
        // 统一设置字体
        InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 20));

        this.io = new IO();

        setSize(300 , 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel01 = new JPanel();
        panel01.add(new JLabel("编号"));
        numTextField = new JTextField(15);
        panel01.add(numTextField);

        JPanel panel02 = new JPanel();
        loginButton = new JButton("进入系统");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                    setVisible(false);
                    User user = new User(numTextField.getText());
//                    if("1".equals(user.getTaskNum())){
//                        new OneTaskMainFrame(user , io);
//                    }
//                    if("2".equals(user.getTaskNum())){
//                        new TwoTaskMainFrame(user , io);
//                    }
//                    if("3".equals(user.getTaskNum())){
//                        new ThreeTaskMainFrame(user , io);
//                    }
                }else{
                    JOptionPane.showMessageDialog(null, "输入的ID错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel02.add(loginButton);

        Box vBox = Box.createVerticalBox();
        vBox.add(panel01);
        vBox.add(panel02);

        setContentPane(vBox);
        pack();
        setVisible(true);
    }

    public boolean check(){
        IO io = new IO();
        String[] nos = io.getAllNo();
        String userInput = numTextField.getText();
        boolean flag = false;
        for(String s : nos){
            if(Objects.equals(s , userInput)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    public static void main(String[] args) {
        JFrame test = new LoginFrame();

    }
}
