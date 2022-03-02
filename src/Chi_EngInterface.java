import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

final public class Chi_EngInterface implements Size {
    private static final JLabel timeNow = new JLabel();
    private static final JLabel rightLabel = new JLabel();
    private static final JLabel wrongLabel = new JLabel();
    static boolean isEnd;//判断终止
    static private int rightNum;//答题情况
    static private int wrongNum;
    private final VocabularyReader vr = new VocabularyReader();
    private final JFrame jfm = new JFrame("子页面：汉译英挑战");
    private final JPanel jpl = new JPanel();
    private final JLabel que = new JLabel();
    private final JLabel chin = new JLabel();
    private final JLabel hint = new JLabel();
    private final JTextField myAns = new JTextField();
    private final ArrayList wrongWords = new ArrayList();
    private final ArrayList rightWords = new ArrayList();
    private final File file = new File("src\\未掌握单词.txt");
    private final FileReader fr = new FileReader(file);
    private final BufferedReader br = new BufferedReader(fr);
    private final File file1 = new File("src\\挑战2.txt");
    private final FileReader fr1 = new FileReader(file1);
    private final BufferedReader br1 = new BufferedReader(fr1);
    boolean isWin;
    private String eng;//英语汉语
    private String chi;
    private String processedEng;//加工过的英语
    private String ans;//回答

    public Chi_EngInterface() throws Exception {
        rightNum = wrongNum = 0;//初始化
        isWin = isEnd = false;

        jfm.setLocation(centerWidth - interfaceWidth / 2, centerHeight - interfaceHeight / 2);//界面
        jfm.setSize(interfaceWidth, interfaceHeight);
        jfm.setVisible(true);
        jfm.setResizable(false);
        jfm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jfm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("关闭了页面");
                isEnd = true;
                endProcess();
            }
        });

        jpl.setSize(100, 100);//面板
        jpl.setVisible(true);
        jfm.add(jpl);
        jpl.setLayout(null);

        JLabel jll = new JLabel("汉译英挑战");//主标题
        jll.setSize(labelWidth, labelHeight);
        jll.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line * 3);
        jll.setVisible(true);
        jpl.add(jll);
        jll.setFont(ftTitle);

        que.setSize(labelWidth, labelHeight);//英语显示
        que.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line * 2);
        que.setVisible(true);
        jpl.add(que);
        que.setFont(ftQue);

        chin.setSize(labelWidth, labelHeight);//汉语显示
        chin.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight);
        chin.setVisible(true);
        jpl.add(chin);
        chin.setFont(ftButton);

        timeNow.setSize(buttonWidth, buttonHeight);//时间表
        timeNow.setLocation(100, 90);
        timeNow.setVisible(true);
        jpl.add(timeNow);
        timeNow.setFont(ftHint);

        hint.setSize(labelWidth * 3, labelHeight);//提示信息
        hint.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line);
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

        JLabel ansPos = new JLabel("请在此输入单词");//就是提示输入单词位置而已
        ansPos.setSize(labelWidth, labelHeight);
        ansPos.setLocation(centInterfaceWidth - labelWidth / 2 - line * 2, centInterfaceHeight + line);
        jpl.add(ansPos);
        ansPos.setFont(ftHint);

        myAns.setSize(buttonWidth, buttonHeight);//回答框
        myAns.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight + line);
        jpl.add(myAns);
        myAns.setFont(ftButton);
        myAns.addActionListener(new getAns());

        while (true) {//读取未掌握单词
            String str = br.readLine();
            if (str == null) {
                break;
            }
            wrongWords.add(str);
        }
        while (true) {//读取挑战2的单词
            String str = br1.readLine();
            if (str == null) {
                break;
            }
            rightWords.add(str);
        }
        TimeTable timetable1 = new TimeTable();//开始
        Thread t2 = new Thread(timetable1);
        t2.start();
    }

    public void refresh() {//刷新显示
        System.out.println("更新");
        getWord();
        que.setText(processedEng);
        chin.setText(chi);
    }

    private void update() {//更新问题
        System.out.println(ans.length());
        System.out.println(eng.length());
        if (ans.equals(eng)) {
            rightNum++;
            System.out.println("答对了");
            hint.setText("回答正确");
            String str = eng + "=" + chi;
            if (wrongWords.contains(str)) {//答对了之前一个打错的题
                wrongWords.remove(str);
                File file = new File("src\\已掌握单词.txt");
                FileReader fr;
                try {
                    fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    ArrayList wordsR = new ArrayList();
                    while (true) {
                        String str1 = br.readLine();
                        if (str1 == null) {
                            break;
                        }
                        wordsR.add(str1);
                    }
                    wordsR.add(str);
                    br.close();
                    fr.close();
                    PrintStream ps = new PrintStream(file);
                    for (Object word : wordsR) {
                        ps.println(word);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!rightWords.contains(str)) {
                rightWords.add(str);
            }
        } else {
            wrongNum++;
            System.out.println("答错啦");
            hint.setText("回答错误, 正确答案为: " + eng + " " + chi);
            String str = eng + "=" + chi;
            if (!wrongWords.contains(str)) {
                wrongWords.add(str);
            }
        }
        if (rightNum == 20) {
            isEnd = true;
            isWin = true;
            JOptionPane.showMessageDialog(null, "挑战成功");
        } else if (wrongNum == 2) {
            isEnd = true;
            JOptionPane.showMessageDialog(null, "挑战失败");
        }
        endProcess();
        refresh();
    }

    public void getWord() {//读取单词
        StringBuilder sb = new StringBuilder((String) vr.getRandomWord());
        int point = sb.indexOf("=");
        eng = sb.substring(0, point);
        chi = sb.substring(point + 1, sb.length());
        System.out.println("读取了一次单词");
        processEng();
    }

    public void processEng() {//加工英文
        int len = eng.length();
        if (wrongWords.contains(eng + "=" + chi)) {
            StringBuilder sb2 = new StringBuilder(eng);
            for (int c = 0; c < len; c++) {
                sb2.setCharAt(c, '-');
            }
            processedEng = sb2.toString();
            System.out.println("该单词不显示提示字母");
        } else {
            int i = (int) (Math.random() * len);
            int j = -1;
            StringBuilder sb2 = new StringBuilder(eng);
            if (len > 5) {
                do {
                    j = (int) (Math.random() * len);
                } while (i == j);
            }
            for (int c = 0; c < len; c++) {
                if (c != i && c != j) {
                    sb2.setCharAt(c, '_');
                }
            }
            processedEng = sb2.toString();
            System.out.println("处理了单词" + eng + ",长度为" + len + "替换了" + j + "和" + i);
        }
    }

    private void IO() {//将结果导入文件
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

    private void endProcess() {//末尾处理
        if (isEnd) {
            IO();
            jpl.removeAll();
            try {
                vr.tidyUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
            jfm.dispose();
        }
    }

    static class TimeTable implements Runnable {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                time++;
                if (time == 600) {
                    isEnd = true;
                }
            }
        }
    }

    class getAns implements ActionListener {//读取答案

        public void actionPerformed(ActionEvent e) {
            ans = myAns.getText();
            myAns.setText("");
            System.out.println("读取了答案" + ans);
            update();
        }
    }
}
