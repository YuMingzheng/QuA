package MainWindow;

import com.baidu.translate.demo.TransApi;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yu Mingzheng
 * @date 2022/10/23 16:59:00
 * @apiNote
 */
public class ThreeTaskMainFrame extends JFrame{
    /**
     * 从界面最上面开始
     */
    JPanel northPanel;
    JLabel idLabel1;
    JLabel idLabel2;
    JLabel totalDuraLabel2;

    /**
     * 中间的界面部分
     */
    JPanel centerPanel;
    //中间左边
    JPanel cLeft;

    JPanel cLeftUpper;
    JLabel cLeftUpperLeftLabel;  // 倒计时：
    JLabel cLeftUpperRightLabel; // n秒

    JPanel cLeftMid;
    JTextArea cLeftMidUpperArea; // 显示题目主要内容的TextArea
    JPanel cLeftMidBelow;
    JRadioButton left1;
    JRadioButton left2;
    JRadioButton left3;
    JRadioButton left4;
    JRadioButton left5;
    ButtonGroup leftButtonGroup;

    JButton cLeftBelowButton;

    //中间中间
    JPanel cCenter;

    JPanel cCenterUpper;
    JLabel cCenterUpperLeftLabel;  // 倒计时：
    JLabel cCenterUpperRightLabel; // n秒

    JPanel cCenterMid;
    JTextArea cCenterMidUpperArea; // 显示题目主要内容的TextArea
    JPanel cCenterMidBelow;
    JRadioButton center1;
    JRadioButton center2;
    JRadioButton center3;
    JRadioButton center4;
    JRadioButton center5;
    ButtonGroup centerButtonGroup;

    //    JPanel cLeftBelow;
    JButton cCenterBelowButton;


    //中间右边
    JPanel cRight;

    JPanel cRightUpper;
    JLabel cRightUpperLeftLabel;  // 倒计时：
    JLabel cRightUpperRightLabel; // n秒

    JPanel cRightMid;
    JTextArea cRightMidUpperArea; // 显示题目主要内容的TextArea
    JPanel cRightMidBelow;
    JRadioButton right1;
    JRadioButton right2;
    JRadioButton right3;
    JRadioButton right4;
    JRadioButton right5;
    ButtonGroup rightButtonGroup;

    JButton cRightBelowButton;


    /**
     * 下面的界面部分
     */
    JPanel southPanel;
    JButton transButton;
    JTextArea transArea;
    JButton startButton;

    Thread mainThread;
    Thread leftPanelThread;
    Thread centerPanelThread;
    Thread rightPanelThread;

    int leftNowPage;
    int centerNowPage;
    int rightNowPage;

    int leftTransTimes;
    int centerTransTimes;
    int rightTransTimes;

    private ArrayList<QA> QAList;

    private IO io;
    User user;

    // 记录当前聚焦在哪个文本区域，从左往右依次 1 2 3，无聚焦为0
    int focusArea = 0;
    // 记录当前三个题目的正确答案是多少 //TODO 三个题目的正确答案
    String[] trueAns;
    boolean leftJump = false;
    boolean centerJump = false;
    boolean rightJump = false;

    // 整个程序运行开始的时间
    long startCur;

    // 整个实验持续时长
    int EXP_DURA = 60;
    boolean EXP_STOP = false;

    long gameStartCur;

    /**
     * 翻译的单词数量
     */
    int leftTransWordNum;
    int centerTransWordNum;
    int rightTransWordNum;

    /**
     * 记录每一轮开始的时刻
     */
    long leftStartCur;
    long centerStartCur;
    long rightStartCur;



    ThreeTaskMainFrame(User user , IO io){
        // 统一设置字体
        InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 20));

        this.user = user;
        this.io = io;
        this.QAList = io.readQAs();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth , screenHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        TitledBorder nullBorder = new TitledBorder("");

        JPanel mainPanel = new JPanel(null);

        // 上面的panel start
        TitledBorder northTitle = new TitledBorder("ID与进度");
        northTitle.setTitleFont(new Font("微软雅黑",Font.PLAIN,13));
        northPanel = new JPanel(new GridLayout(1 , 4));
        northPanel.setSize(screenWidth , 100);
        idLabel1 = new JLabel("ID");
        northPanel.add(idLabel1);
        idLabel2 = new JLabel(user.getID());
        northPanel.add(idLabel2);
        northPanel.add(new JLabel("时长"));
        totalDuraLabel2 = new JLabel();
        totalDuraLabel2.setForeground(new Color(255,0,0));
        northPanel.add(totalDuraLabel2);

        northPanel.setBorder(northTitle);

        northPanel.setSize(screenWidth - 40 , 100);
        northPanel.setLocation(10 , 10);
        mainPanel.add(northPanel);
        // 上面的panel end


        // 中间的panel start
        centerPanel = new JPanel(new GridLayout(1 , 3 , 20 , 0));

        // 左边 start
        TitledBorder leftTitle = new TitledBorder("任务1");
        leftTitle.setTitleFont(new Font("微软雅黑",Font.PLAIN,13));

        cLeft = new JPanel(new BorderLayout(0,10));
        cLeftUpper = new JPanel(new GridLayout(1,2,10 , 0));
        cLeftUpperLeftLabel = new JLabel("倒计时");
        cLeftUpperRightLabel = new JLabel();
        cLeftUpperRightLabel.setForeground(new Color(255,0,0));
        cLeftUpper.add(cLeftUpperLeftLabel);
        cLeftUpper.add(cLeftUpperRightLabel);

        cLeftUpper.setBorder(nullBorder);

        cLeftMid = new JPanel(new GridLayout(2,1,0,10));
        cLeftMidUpperArea = new JTextArea();
        cLeftMidUpperArea.setBackground(new Color(229,229,229));

        cLeftMidUpperArea.setLineWrap(true);
        cLeftMidUpperArea.setRows(20);
        cLeftMidUpperArea.setEditable(false);
        cLeftMidUpperArea.setEnabled(false);
        JScrollPane cLeftScrool = new JScrollPane(cLeftMidUpperArea);
        cLeftMidBelow = new JPanel(new GridLayout(5 , 1));
        left1 = new JRadioButton();
        left2 = new JRadioButton();
        left3 = new JRadioButton();
        left4 = new JRadioButton();
        left5 = new JRadioButton();
        leftButtonGroup = new ButtonGroup();
        leftButtonGroup.add(left1);
        leftButtonGroup.add(left2);
        leftButtonGroup.add(left3);
        leftButtonGroup.add(left4);
        leftButtonGroup.add(left5);
        cLeftMidBelow.add(left1);
        cLeftMidBelow.add(left2);
        cLeftMidBelow.add(left3);
        cLeftMidBelow.add(left4);
        cLeftMidBelow.add(left5);

        cLeftMid.add(cLeftScrool);
        cLeftMid.add(cLeftMidBelow);

        cLeftBelowButton = new JButton("下一题");
        cLeftBelowButton.setBackground(new Color(12, 174, 238));

        cLeft.add(cLeftUpper , BorderLayout.NORTH);
        cLeft.add(cLeftMid , BorderLayout.CENTER);
//        cLeft.add(cLeftBelowButton , BorderLayout.SOUTH);
        cLeft.setBorder(leftTitle);
        centerPanel.add(cLeft);
        // 左边 end

        // 中间 start
        TitledBorder centerTitle = new TitledBorder("任务2");
        centerTitle.setTitleFont(new Font("微软雅黑",Font.PLAIN,13));

        cCenter = new JPanel(new BorderLayout(0,10));
        cCenterUpper = new JPanel(new GridLayout(1,2,10 , 0));
        cCenterUpperLeftLabel = new JLabel("倒计时");
        cCenterUpperRightLabel = new JLabel();
        cCenterUpperRightLabel.setForeground(new Color(255,0,0));
        cCenterUpper.add(cCenterUpperLeftLabel);
        cCenterUpper.add(cCenterUpperRightLabel);

        cCenterUpper.setBorder(nullBorder);

        cCenterMid = new JPanel(new GridLayout(2,1,0,10));
        cCenterMidUpperArea = new JTextArea();
        cCenterMidUpperArea.setBackground(new Color(229,229,229));

        cCenterMidUpperArea.setLineWrap(true);
        cCenterMidUpperArea.setRows(20);
        cCenterMidUpperArea.setEditable(false);
        cCenterMidUpperArea.setEnabled(false);
        JScrollPane cCenterScrool = new JScrollPane(cCenterMidUpperArea);
        cCenterMidBelow = new JPanel(new GridLayout(5 , 1));
        center1 = new JRadioButton();
        center2 = new JRadioButton();
        center3 = new JRadioButton();
        center4 = new JRadioButton();
        center5 = new JRadioButton();
        centerButtonGroup = new ButtonGroup();
        centerButtonGroup.add(center1);
        centerButtonGroup.add(center2);
        centerButtonGroup.add(center3);
        centerButtonGroup.add(center4);
        centerButtonGroup.add(center5);
        cCenterMidBelow.add(center1);
        cCenterMidBelow.add(center2);
        cCenterMidBelow.add(center3);
        cCenterMidBelow.add(center4);
        cCenterMidBelow.add(center5);

        cCenterMid.add(cCenterScrool);
        cCenterMid.add(cCenterMidBelow);

        cCenterBelowButton = new JButton("下一题");
        cCenterBelowButton.setBackground(new Color(12, 174, 238));

        cCenter.add(cCenterUpper , BorderLayout.NORTH);
        cCenter.add(cCenterMid , BorderLayout.CENTER);
//        cCenter.add(cCenterBelowButton , BorderLayout.SOUTH);
        cCenter.setBorder(centerTitle);
        centerPanel.add(cCenter);
        // 中间 end

        // 右边 start
        TitledBorder rightTitle = new TitledBorder("任务1");
        rightTitle.setTitleFont(new Font("微软雅黑",Font.PLAIN,13));

        cRight = new JPanel(new BorderLayout(0,10));
        cRightUpper = new JPanel(new GridLayout(1,2,10 , 0));
        cRightUpperLeftLabel = new JLabel("倒计时");
        cRightUpperRightLabel = new JLabel();
        cRightUpperRightLabel.setForeground(new Color(255,0,0));
        cRightUpper.add(cRightUpperLeftLabel);
        cRightUpper.add(cRightUpperRightLabel);

        cRightUpper.setBorder(nullBorder);

        cRightMid = new JPanel(new GridLayout(2,1,0,10));
        cRightMidUpperArea = new JTextArea();
        cRightMidUpperArea.setBackground(new Color(229,229,229));

        cRightMidUpperArea.setLineWrap(true);
        cRightMidUpperArea.setRows(20);
        cRightMidUpperArea.setEditable(false);
        cRightMidUpperArea.setEnabled(false);
        JScrollPane cRightScrool = new JScrollPane(cRightMidUpperArea);
        cRightMidBelow = new JPanel(new GridLayout(5 , 1));
        right1 = new JRadioButton();
        right2 = new JRadioButton();
        right3 = new JRadioButton();
        right4 = new JRadioButton();
        right5 = new JRadioButton();
        rightButtonGroup = new ButtonGroup();
        rightButtonGroup.add(right1);
        rightButtonGroup.add(right2);
        rightButtonGroup.add(right3);
        rightButtonGroup.add(right4);
        rightButtonGroup.add(right5);
        cRightMidBelow.add(right1);
        cRightMidBelow.add(right2);
        cRightMidBelow.add(right3);
        cRightMidBelow.add(right4);
        cRightMidBelow.add(right5);

        cRightMid.add(cRightScrool);
        cRightMid.add(cRightMidBelow);

        cRightBelowButton = new JButton("下一题");
        cRightBelowButton.setBackground(new Color(12, 174, 238));

        cRight.add(cRightUpper , BorderLayout.NORTH);
        cRight.add(cRightMid , BorderLayout.CENTER);
//        cRight.add(cRightBelowButton , BorderLayout.SOUTH);
        cRight.setBorder(rightTitle);
        centerPanel.add(cRight);
        // 右边 end
        centerPanel.setSize(screenWidth - 40 , 700);
        centerPanel.setLocation(10 , northPanel.getY() + northPanel.getHeight() + 20);
        mainPanel.add(centerPanel);
        // 中间的panel end

        // 底部panel
        southPanel = new JPanel(null);
        transButton = new JButton("翻译");
        transButton.setEnabled(false);
        transButton.setFont(new Font("微软雅黑" , Font.PLAIN , 25));
        transButton.setBorderPainted(true);
        transButton.setBackground(new Color(101, 101, 101));
        transButton.setSize(200 , 100);
        transButton.setLocation(20,10);

        transArea = new JTextArea();
        transArea.setEditable(false);
        transArea.setLineWrap(true);
        transArea.setSize(800 , 100);
        transArea.setLocation(transButton.getX() + transButton.getWidth() + 20 , transButton.getY());

        startButton = new JButton("开始");
        startButton.setBackground(new Color(0,255,0));
        startButton.setFont(new Font("微软雅黑" , Font.PLAIN , 25));
        startButton.setBorderPainted(true);
        startButton.setSize(200 , 100);
        startButton.setLocation(screenWidth - startButton.getWidth() - 400 , transButton.getY());

        southPanel.add(transButton);
        southPanel.add(transArea);
        southPanel.add(startButton);

        southPanel.setSize(screenWidth - 40 , 100);
        southPanel.setLocation(10 , centerPanel.getY() + centerPanel.getHeight() + 40);
        mainPanel.add(southPanel);
        // 底部panel end

        // 设置所有radio button初始时不可用
        setAllRadioEnable(false);


        add(mainPanel);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainThread = new MainThread();
                setAllRadioEnable(true);
                startCur = System.currentTimeMillis();
                mainThread.start();
                gameStartCur = System.currentTimeMillis();
                leftStartCur = System.currentTimeMillis();
                centerStartCur = System.currentTimeMillis();
                rightStartCur = System.currentTimeMillis();

                startButton.setBackground(new Color(100,100,100));
                startButton.setEnabled(false);
            }
        });

        cLeftBelowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftJump = true;
            }
        });
        cCenterBelowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerJump = true;
            }
        });
        cRightBelowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightJump = true;
            }
        });


        transButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(focusArea == 1){
                    if(cLeftMidUpperArea.getSelectedText() != null){
                        leftTransTimes += 1;
                        String leftTran = cLeftMidUpperArea.getSelectedText();
                        transArea.setText(translate(leftTran));
                        leftTransWordNum += leftTran.split(" ").length;
                    }
                }else if(focusArea == 2){
                    if(cCenterMidUpperArea.getSelectedText() != null){
                        centerTransTimes += 1;
                        String centerTran = cCenterMidUpperArea.getSelectedText();
                        transArea.setText(translate(centerTran));
                        centerTransWordNum += centerTran.split(" ").length;
                    }
                }else if(focusArea == 3){
                    if(cRightMidUpperArea.getSelectedText() != null){
                        rightTransTimes += 1;
                        String rightTran = cRightMidUpperArea.getSelectedText();
                        transArea.setText(translate(rightTran));
                        rightTransWordNum += rightTran.split(" ").length;
                    }
                }
            }
        });

        cLeftMidUpperArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                focusArea = 1;
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        cCenterMidUpperArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                focusArea = 2;
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        cRightMidUpperArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                focusArea = 3;
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        left1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftJump = true;
            }
        });

        left2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftJump = true;
            }
        });

        left3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftJump = true;
            }
        });

        left4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftJump = true;
            }
        });

        left5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftJump = true;
            }
        });

        center1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerJump = true;
            }
        });

        center2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerJump = true;
            }
        });

        center3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerJump = true;
            }
        });

        center4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerJump = true;
            }
        });

        center5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerJump = true;
            }
        });

        right1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightJump = true;
            }
        });

        right2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightJump = true;
            }
        });

        right3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightJump = true;
            }
        });

        right4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightJump = true;
            }
        });

        right5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightJump = true;
            }
        });



        setVisible(true);
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

    /**
     * 设置所有Radio button的状态
     * @param enable：true 可用；false不可用
     */
    private void setAllRadioEnable(boolean enable){
        left1.setEnabled(enable);
        left2.setEnabled(enable);
        left3.setEnabled(enable);
        left4.setEnabled(enable);
        left5.setEnabled(enable);

        center1.setEnabled(enable);
        center2.setEnabled(enable);
        center3.setEnabled(enable);
        center4.setEnabled(enable);
        center5.setEnabled(enable);

        right1.setEnabled(enable);
        right2.setEnabled(enable);
        right3.setEnabled(enable);
        right4.setEnabled(enable);
        right5.setEnabled(enable);
    }

    /**
     * 清除所有Radio button的选择状态
     */
    private void clareAllRadio(){
        leftButtonGroup.clearSelection();
        centerButtonGroup.clearSelection();
        rightButtonGroup.clearSelection();
    }

    public class MainThread extends Thread{

        @Override
        public void run(){
            cLeftMidUpperArea.setEnabled(true);
            cCenterMidUpperArea.setEnabled(true);
            cRightMidUpperArea.setEnabled(true);

            myTimer();
            ifEnd();

            transButton.setEnabled(true);
            transButton.setBackground(new Color(255 , 0 ,  0));

            gameStartCur = System.currentTimeMillis();
            leftPanelThread = new Thread(new PanelThread(1 , 10));
            leftPanelThread.start();

            centerPanelThread = new Thread(new PanelThread(2 , 10));
            centerPanelThread.start();

            rightPanelThread = new Thread(new PanelThread(3 , 10));
            rightPanelThread.start();

        }
    }

    private void ifEnd(){
        new Thread() {
            @Override
            public void run(){
                while(true){
                    try{
                        if((double)(System.currentTimeMillis() - startCur)/1000 >= EXP_DURA){
                            leftPanelThread.stop();
                            centerPanelThread.stop();
                            rightPanelThread.stop();
                            EXP_STOP = true;
                            JOptionPane.showMessageDialog(null, "实验结束", "提示", JOptionPane.PLAIN_MESSAGE);
                            Thread.sleep(2*1000);
                            System.exit(0);
                        }
                        Thread.sleep(100);
                    }catch(Exception e){

                    }

                }
            }
        }.start();
    }

    public class PanelThread implements Runnable{
        int which;
        double dura;
        long threadStart;
        QA qa;

        PanelThread(int which , double dura){
            this.which = which;
            this.dura = dura;
        }

        @Override
        public void run(){
            qa = refresh(this.which);
            threadStart = System.currentTimeMillis();
            if(this.which == 1){
                leftStartCur = System.currentTimeMillis() - gameStartCur;
                while(true){
                    cLeftUpperRightLabel.setText(String.format("%.1f" , (double) (threadStart + this.dura*1000 + 100 - System.currentTimeMillis() ) / 1000));
                    if((double)(System.currentTimeMillis() - threadStart) / 1000 >= this.dura | leftJump){
                        writerRoundInfo(which);
                        leftTransWordNum = 0;
                        clareAllRadio();
                        qa = refresh(this.which);
                        leftStartCur = System.currentTimeMillis() - gameStartCur;
                        leftJump = false;
                        threadStart = System.currentTimeMillis();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }else if(this.which == 2){
                centerStartCur = System.currentTimeMillis() - gameStartCur;
                while(true){
                    cCenterUpperRightLabel.setText(String.format("%.1f" , (double) (threadStart  + this.dura*1000 + 100 - System.currentTimeMillis()) / 1000));
                    if((double)(System.currentTimeMillis() - threadStart) / 1000 >= this.dura | centerJump){
                        writerRoundInfo(which);
                        centerTransWordNum = 0;
                        clareAllRadio();
                        qa = refresh(this.which);
                        centerStartCur = System.currentTimeMillis() - gameStartCur;
                        centerJump = false;
                        threadStart = System.currentTimeMillis();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }else if(this.which == 3){
                rightStartCur = System.currentTimeMillis() - gameStartCur;
                while(true){
                    cRightUpperRightLabel.setText(String.format("%.1f" , (double) (threadStart  + this.dura*1000 + 100 - System.currentTimeMillis()) / 1000));
                    if((double)(System.currentTimeMillis() - threadStart) / 1000 >= this.dura | rightJump){
                        writerRoundInfo(which);
                        rightTransWordNum = 0;
                        clareAllRadio();
                        qa = refresh(this.which);
                        rightStartCur = System.currentTimeMillis() - gameStartCur;
                        rightJump = false;
                        threadStart = System.currentTimeMillis();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public QA refresh(int which){
            int tempI = randomGetOne();
            QA qa = QAList.get(tempI);
            if(which == 1){
                cLeftMidUpperArea.setText(QAList.get(tempI).getQuestion());
                left1.setText(QAList.get(tempI).getAnswers()[0]);
                left2.setText(QAList.get(tempI).getAnswers()[1]);
                left3.setText(QAList.get(tempI).getAnswers()[2]);
                left4.setText(QAList.get(tempI).getAnswers()[3]);
                left5.setText(QAList.get(tempI).getAnswers()[4]);

                leftNowPage += 1;
                leftJump = false;
                leftTransTimes = 0;
            }else if(which == 2){
                cCenterMidUpperArea.setText(QAList.get(tempI).getQuestion());
                center1.setText(QAList.get(tempI).getAnswers()[0]);
                center2.setText(QAList.get(tempI).getAnswers()[1]);
                center3.setText(QAList.get(tempI).getAnswers()[2]);
                center4.setText(QAList.get(tempI).getAnswers()[3]);
                center5.setText(QAList.get(tempI).getAnswers()[4]);

                centerNowPage += 1;
                centerJump = false;
                centerTransTimes = 0;
            }else if(which == 3){
                cRightMidUpperArea.setText(QAList.get(tempI).getQuestion());
                right1.setText(QAList.get(tempI).getAnswers()[0]);
                right2.setText(QAList.get(tempI).getAnswers()[1]);
                right3.setText(QAList.get(tempI).getAnswers()[2]);
                right4.setText(QAList.get(tempI).getAnswers()[3]);
                right5.setText(QAList.get(tempI).getAnswers()[4]);

                rightNowPage += 1;
                rightJump = false;
                rightTransTimes = 0;
            }
            QAList.remove(tempI);
            return qa;
        }

        public void writerRoundInfo(int which){
            String[] output = new String[9];

            output[0] = user.getID();            // userID
            output[1] = String.valueOf(which);   // panel

            if(which == 1){
                output[2] = String.valueOf(leftNowPage); //round
                output[3] = String.valueOf(leftStartCur / 1000.0); //roundStartCur
                output[4] = String.valueOf((System.currentTimeMillis() - gameStartCur) / 1000.0); //roundEndCur
                output[5] = String.valueOf(leftTransTimes); // transTimes
                output[6] = String.valueOf(leftTransWordNum); // transWordNum
                output[7] = getClicked(which);
                output[8] = qa.getTrueAns();
            }else if(which == 2){
                output[2] = String.valueOf(centerNowPage); //round
                output[3] = String.valueOf(centerStartCur / 1000.0); //roundStartCur
                output[4] = String.valueOf((System.currentTimeMillis() - gameStartCur) / 1000.0); //roundEndCur
                output[5] = String.valueOf(centerTransTimes); // transTimes
                output[6] = String.valueOf(centerTransWordNum); // transWordNum
                output[7] = getClicked(which);
                output[8] = qa.getTrueAns();
            }else if(which == 3){
                output[2] = String.valueOf(rightNowPage); //round
                output[3] = String.valueOf(rightStartCur / 1000.0); //roundStartCur
                output[4] = String.valueOf((System.currentTimeMillis() - gameStartCur) / 1000.0); //roundEndCur
                output[5] = String.valueOf(rightTransTimes); // transTimes
                output[6] = String.valueOf(rightTransWordNum); // transWordNum
                output[7] = getClicked(which);
                output[8] = qa.getTrueAns();
            }
            io.writeOneRound(Arrays.toString(output).replace("[" , "").replace("]" , ""));

        }

    }


    private void myTimer(){
        new Thread() {//线程操作
            @Override
            public void run(){
                while(!EXP_STOP){
                    try {
                        totalDuraLabel2.setText(String.format("%.0f" , (double) (System.currentTimeMillis() - startCur) / 1000));
                        Thread.sleep(1000);//每隔一秒刷新一次
                    }
                    catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    private String translate(String in){
        String APP_ID = "20210317000731202";
        String SECURITY_KEY = "fQVJLHBolm0clop7QcnP";

        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String resp = api.getTransResult(in , "auto" , "zh");
        Pattern pat = Pattern.compile("\"dst\":\".*?\"");
        Matcher mat = pat.matcher(resp);
        if(mat.find()){
            resp =  unicodeDecode(mat.group().replace("\"dst\":\"" , "").replace("\"" , ""));
        }

        return resp;
    }

    public int randomGetOne(){
        Random rand = new Random();
        int lb = 0;
        int ub = this.QAList.size();

        return rand.nextInt(ub-lb) + lb;
    }

    public String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }

    private String getClicked(int which){
        String[] clicked = new String[3];
        //
        if(which == 1){
            if(left1.isSelected()){
                return "A";
            }else if(left2.isSelected()){
                return "B";
            }else if(left3.isSelected()){
                return "C";
            }else if(left4.isSelected()){
                return "D";
            }else if(left5.isSelected()){
                return "E";
            }else{
                return null;
            }
        }else if(which == 2){
            if(center1.isSelected()){
                return "A";
            }else if(center2.isSelected()){
                return "B";
            }else if(center3.isSelected()){
                return "C";
            }else if(center4.isSelected()){
                return "D";
            }else if(center5.isSelected()){
                return "E";
            }else{
                return null;
            }
        }else if(which == 3){
            if(right1.isSelected()){
                return "A";
            }else if(right2.isSelected()){
                return "B";
            }else if(right3.isSelected()){
                return "C";
            }else if(right4.isSelected()){
                return "D";
            }else if(right5.isSelected()){
                return "E";
            }else{
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        IO io_ = new IO();
        User user = new User("3");

        JFrame test = new ThreeTaskMainFrame(user , io_);

    }

}
