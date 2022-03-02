import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;

final public class Eng_ChiInterface implements Size {
    private static final JLabel timeNow = new JLabel();//时间提示
    private static final JLabel rightLabel = new JLabel();//答题情况
    private static final JLabel wrongLabel = new JLabel();
    static boolean isEnd;//判断是否终止，用于控制线程
    static private int rightNum;//记录答题情况
    static private int wrongNum;
    private final VocabularyReader vr = new VocabularyReader();
    private final JFrame jfm = new JFrame("子页面：英译汉挑战");
    private final JPanel jpl = new JPanel();
    private final JLabel que = new JLabel();//英语问题
    private final JButton[] jb = new JButton[4];//四个按钮
    private final JLabel hint = new JLabel();//提示
    private final ArrayList wrongWords = new ArrayList();//记录答题的正确与错误
    private final ArrayList rightWords = new ArrayList();
    private final File file = new File("src\\未掌握单词.txt");
    private final FileReader fr = new FileReader(file);
    private final BufferedReader br = new BufferedReader(fr);
    private final File file1 = new File("src\\挑战1.txt");
    private final FileReader fr1 = new FileReader(file1);
    private final BufferedReader br1 = new BufferedReader(fr1);
    boolean isWin;//判断结局
    int rightAns;//用来对照答案
    int myAns;
    private String eng;//读取的中英文
    private String chi;
    private String rightEng;//对应的正确答案，用于提示
    private String rightChin;

    public Eng_ChiInterface() throws Exception {//初始化
        rightNum = wrongNum = 0;
        myAns = 4;
        isWin = isEnd = false;
        jb[0] = new JButton();
        jb[1] = new JButton();
        jb[2] = new JButton();
        jb[3] = new JButton();
        jfm.setLocation(centerWidth - interfaceWidth / 2, centerHeight - interfaceHeight / 2);//界面
        jfm.setSize(interfaceWidth, interfaceHeight);
        jfm.setVisible(true);
        jfm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭子界面
        jfm.setResizable(false);
        jfm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {//关闭后的操作
                System.out.println("关闭了页面");
                isEnd = true;
                endProcess();
            }
        });

        jpl.setSize(100, 100);//面板
        jpl.setVisible(true);
        jfm.add(jpl);
        jpl.setLayout(null);//面板可调

        JLabel jll = new JLabel("英译汉挑战");//主标题
        jll.setSize(labelWidth, labelHeight);
        jll.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line * 3);
        jll.setVisible(true);
        jpl.add(jll);
        jll.setFont(ftTitle);

        que.setSize(labelWidth + 2, labelHeight);//英语
        que.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line * 2);
        que.setVisible(true);
        jpl.add(que);
        que.setFont(ftQue);

        timeNow.setSize(labelWidth, labelHeight);//时间表
        timeNow.setLocation(100, 90);
        timeNow.setVisible(true);
        jpl.add(timeNow);
        timeNow.setFont(ftHint);

        hint.setSize(labelWidth, labelHeight);//提示信息
        hint.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line * 3 / 2);
        hint.setVisible(true);
        jpl.add(hint);
        hint.setFont(ftHint);

        rightLabel.setSize(labelWidth, labelHeight);//正确的数目
        rightLabel.setLocation(600, 90);
        rightLabel.setVisible(true);
        jpl.add(rightLabel);
        rightLabel.setFont(ftHint);

        wrongLabel.setSize(labelWidth, labelHeight);//错误的数目
        wrongLabel.setLocation(600, 90 + line);
        jpl.add(wrongLabel);
        wrongLabel.setFont(ftHint);

        jb[0].addActionListener(new ButtonClickOpeChoose0());//选项
        jb[1].addActionListener(new ButtonClickOpeChoose1());
        jb[2].addActionListener(new ButtonClickOpeChoose2());
        jb[3].addActionListener(new ButtonClickOpeChoose3());
        for (int i = 0; i < 4; i++) {
            jb[i].setSize(buttonWidth, buttonHeight);
            jb[i].setLocation(centInterfaceWidth - buttonWidth / 2, centInterfaceWidth - buttonHeight / 2 - (2 - i) * line);
            jb[i].setVisible(true);
            jpl.add(jb[i]);
            jb[i].setFont(ftButton);
        }
        while (true) {//读取未掌握单词
            String str = br.readLine();
            if (str == null) {
                break;
            }
            wrongWords.add(str);
        }
        while (true) {//读取已掌握单词
            String str = br1.readLine();
            if (str == null) {
                break;
            }
            rightWords.add(str);
        }
    }

    public void refresh() {//刷新显示,更新按钮和问题的答案
        System.out.println("更新");
        rightAns = (int) (Math.random() * 3);
        for (int i = 0; i < 4; i++) {
            getWord();
            if (i == rightAns) {
                que.setText(eng);
                System.out.println("读取了" + eng);
                rightEng = eng;
                rightChin = chi;
            }
            jb[i].setText(chi);
        }
    }

    public void getWord() {//读取单词
        StringBuilder sb = new StringBuilder((String) vr.getRandomWord());
        int point = sb.indexOf("=");
        eng = sb.substring(0, point);
        chi = sb.substring(point + 1, sb.length());
        System.out.println("读取了一次单词");
    }

    private void update() {//比较答案并判断输赢,且负责记录单词
        if (myAns == rightAns) {
            rightNum++;
            System.out.println("答对了");
            hint.setText("回答正确");
            String str = rightEng + "=" + rightChin;
            if (!rightWords.contains(str)) {
                rightWords.add(str);
            }
        } else {
            wrongNum++;
            System.out.println("答错啦");
            hint.setText("回答错误，正确答案为" + (char) ('A' + rightAns));
            String str = rightEng + "=" + rightChin;
            if (!wrongWords.contains(str)) {
                wrongWords.add(str);
            }
        }
        if (rightNum == 20) {
            isEnd = true;
            isWin = true;
            jpl.removeAll();
            JOptionPane.showMessageDialog(null, "挑战成功");
        } else if (wrongNum == 2) {
            isEnd = true;
            jpl.removeAll();
            JOptionPane.showMessageDialog(null, "挑战失败");
        }
        endProcess();
        refresh();
    }

    private void IO() {//把这一次的结果保存到文件中
        try {
            PrintStream ps = new PrintStream(file);
            for (Object wrongWord : wrongWords) {
                ps.println(wrongWord);
            }
            PrintStream ps1 = new PrintStream(file1);
            for (Object rightWord : rightWords) {
                ps1.println(rightWord);
            }
            br.close();
            fr.close();
            ps.close();
            br1.close();
            fr.close();
            ps1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void endProcess() {//末尾处理，包含正常退出和异常关闭,主要是整理文件和关闭窗口
        if (isEnd) {
            IO();
            try {
                vr.tidyUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
            jfm.dispose();
        }
    }

    static class TimeTable implements Runnable {//时间显示器,负责线程
        private int time;

        TimeTable() {
            time = 0;
        }

        public void run() {
            while (!isEnd) {
                timeNow.setText("已经用时" + time + "秒");
                rightLabel.setText("正确数目: " + rightNum);
                wrongLabel.setText("错误数目: " + wrongNum);
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                time++;
                if (time == 300) {
                    isEnd = true;
                }
            }
        }
    }

    class ButtonClickOpeChoose0 implements ActionListener {//得出答案并进行比较

        public void actionPerformed(ActionEvent e) {
            myAns = 0;
            update();
        }
    }

    class ButtonClickOpeChoose1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            myAns = 1;
            update();
        }
    }

    class ButtonClickOpeChoose2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            myAns = 2;
            update();
        }
    }

    class ButtonClickOpeChoose3 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            myAns = 3;
            update();
        }
    }
}