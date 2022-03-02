import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInterface implements Size {

    MainInterface() {//显示主页面
        JFrame jfm = new JFrame("主界面");
        jfm.setLocation(centerWidth - interfaceWidth / 2, centerHeight - interfaceHeight / 2);
        jfm.setSize(interfaceWidth, interfaceHeight);
        jfm.setVisible(true);
        jfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfm.setResizable(false);

        JPanel jpl = new JPanel();//面板
        jpl.setSize(100, 100);
        jpl.setVisible(true);
        jfm.add(jpl);
        jpl.setLayout(null);

        JLabel jll = new JLabel("单词记忆系统");//标题
        jll.setSize(labelWidth, labelHeight);
        jll.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line * 3);
        jll.setVisible(true);
        jpl.add(jll);
        jll.setFont(ftTitle);

        JButton first = new JButton("英——中挑战");//按钮
        first.setBounds(centInterfaceWidth - buttonWidth / 2, centInterfaceHeight - buttonHeight / 2, buttonWidth, buttonHeight);
        first.setVisible(true);
        jpl.add(first);
        first.setFont(ftButton);
        JButton second = new JButton("中——英挑战");
        jpl.add(second);
        second.setFont(ftButton);
        second.setBounds(centInterfaceWidth - buttonWidth / 2, centInterfaceHeight - buttonHeight / 2 + line, buttonWidth, buttonHeight);
        second.setVisible(true);
        JButton third = new JButton("单词复习");
        jpl.add(third);
        third.setFont(ftButton);
        third.setBounds(centInterfaceWidth - buttonWidth / 2, centInterfaceHeight - buttonHeight / 2 + line * 2, buttonWidth, buttonHeight);
        third.setVisible(true);

        first.addActionListener(new ButtonClickOpeFirst());
        second.addActionListener(new ButtonClickOpeSecond());
        third.addActionListener(new ButtonClickOpeThird());
    }

    static class ButtonClickOpeFirst implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Eng_ChiInterface eci = new Eng_ChiInterface();//生成子页面
                eci.refresh();
                Eng_ChiInterface.TimeTable timetable1 = new Eng_ChiInterface.TimeTable();
                Thread t2 = new Thread(timetable1);
                t2.start();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    static class ButtonClickOpeSecond implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Chi_EngInterface cei = new Chi_EngInterface();//生成子页面
                Chi_EngInterface.TimeTable timetable2 = new Chi_EngInterface.TimeTable();
                Thread t3 = new Thread(timetable2);
                t3.start();
                cei.refresh();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    static class ButtonClickOpeThird implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new ReviewInterface();
        }//生成子页面
    }
}