import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class VocabularyReader {//从词库中读取单词的类
    static HashMap vocabularyLibrary = new HashMap();//单词库
    static int wordNum;//单词数目
    final private ArrayList oldWord = new ArrayList();//记录已经出现的单词的编号，防止两次随机相同
    final private ArrayList masteredWord = new ArrayList();//记录以及掌握的单词

    public VocabularyReader() throws Exception {//读取默认词库和已经出现的单词
        File file = new File("src\\词库.txt");
        File fileMastered = new File("src\\已掌握单词.txt");
        FileReader fr = new FileReader(file);
        FileReader frm = new FileReader(fileMastered);
        BufferedReader br = new BufferedReader(fr);
        BufferedReader brm = new BufferedReader(frm);
        while (true) {
            String str = brm.readLine();
            if (str == null) {
                break;
            }
            masteredWord.add(str);
        }
        for (wordNum = 0; ; wordNum++) {
            String str = br.readLine();
            if (str == null) {
                break;
            }
            vocabularyLibrary.put(wordNum, str);
        }
        br.close();
        fr.close();
        brm.close();
        frm.close();
    }

    public Object getRandomWord() {//返回一个随机单词,且不重复,且未被掌握
        int key;
        do {
            key = (int) (Math.random() * wordNum);
        } while (oldWord.contains(key) || masteredWord.contains(vocabularyLibrary.get(key)));
        oldWord.add(key);
        return vocabularyLibrary.get(key);
    }

    public void tidyUp() throws Exception {//整理单词,进行打包处理
        File file1 = new File("src\\挑战1.txt");
        File file2 = new File("src\\挑战2.txt");
        File file3 = new File("src\\已掌握单词.txt");
        File file4 = new File("src\\未掌握单词.txt");
        FileReader fr1 = new FileReader(file1);
        FileReader fr2 = new FileReader(file2);
        FileReader fr3 = new FileReader(file3);
        FileReader fr4 = new FileReader(file4);
        BufferedReader br1 = new BufferedReader(fr1);
        BufferedReader br2 = new BufferedReader(fr2);
        BufferedReader br3 = new BufferedReader(fr3);
        BufferedReader br4 = new BufferedReader(fr4);
        ArrayList words1 = new ArrayList();
        ArrayList words2 = new ArrayList();
        ArrayList words3 = new ArrayList();
        ArrayList words4 = new ArrayList();
        while (true) {//读取挑战一
            String str = br1.readLine();
            if (str == null) {
                break;
            }
            words1.add(str);
        }
        while (true) {//读取挑战二
            String str = br2.readLine();
            if (str == null) {
                break;
            }
            words2.add(str);
        }
        while (true) {//读取已掌握
            String str = br3.readLine();
            if (str == null) {
                break;
            }
            words3.add(str);
        }
        while (true) {//读取未掌握
            String str = br4.readLine();
            if (str == null) {
                break;
            }
            words4.add(str);
        }
        for (Object word : words1) {//删除挑战1和挑战2共有的单词，且送入已掌握中
            if (words2.contains(word)) {
                words2.remove(word);
                words3.add(word);
            }
        }
        for (Object word : words3) {
            words1.remove(word);
            words2.remove(word);//这个是防止挑战2中还有“漏网之鱼”
            words4.remove(word);
        }
        br1.close();
        br2.close();
        br3.close();
        br4.close();
        fr1.close();
        fr2.close();
        fr3.close();
        fr4.close();
        PrintStream ps1 = new PrintStream(file1);
        PrintStream ps2 = new PrintStream(file2);
        PrintStream ps3 = new PrintStream(file3);
        PrintStream ps4 = new PrintStream(file4);
        for (Object word : words1) {//存储进文件
            ps1.println(word);
        }
        for (Object word : words2) {
            ps2.println(word);
        }
        for (Object word : words3) {
            ps3.println(word);
        }
        for (Object word : words4) {
            ps4.println(word);
        }
        ps1.close();
        ps2.close();
        ps3.close();
        ps4.close();
    }
}