import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReviewInterface implements Size {
    int wordNum;

    public ReviewInterface() {
        JFrame jfm = new JFrame("子页面：单词复习");
        jfm.setLocation(centerWidth - interfaceWidth / 2, centerHeight - interfaceHeight / 2);
        jfm.setSize(interfaceWidth, interfaceHeight);
        jfm.setVisible(true);
        jfm.setResizable(false);
        jfm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel jpl = new JPanel();//面板
        jpl.setSize(100, 100);
        jpl.setVisible(true);
        jfm.add(jpl);
        jpl.setLayout(null);

        JLabel jll = new JLabel("单词复习");//主标题
        jll.setSize(labelWidth, labelHeight);
        jll.setLocation(centInterfaceWidth - labelWidth / 2, centInterfaceHeight - line * 3);
        jll.setVisible(true);
        jpl.add(jll);
        jll.setFont(ftTitle);
        try {//搞一个多行文本框并加滚轮
            VocabularyReader vr = new VocabularyReader();
            vr.tidyUp();
            JTextArea jta = new JTextArea(wordNum + 1, 1);
            jta.setEditable(false);
            jta.setSize(buttonWidth, buttonHeight * 5);
            jta.setLocation(centInterfaceWidth - buttonWidth / 2, centInterfaceHeight - buttonHeight / 2);
            jpl.add(jta);
            jta.setFont(ftButton);
            jta.setVisible(true);
            File file = new File("src\\未掌握单词.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while (true) {
                str = br.readLine();
                if (str == null) {
                    break;
                }
                jta.append(str + "\n");
            }
            JScrollPane jsp = new JScrollPane();
            jsp.setBounds(centInterfaceWidth - buttonWidth / 2, centInterfaceHeight - buttonHeight / 2 - line * 2, buttonWidth, buttonHeight * 6);
            jsp.setViewportView(jta);
            jpl.add(jsp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}