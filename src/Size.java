import java.awt.*;

public interface Size {//几个重要的尺寸和字体，用作安排位置
    int centerWidth = 1536 / 2;//屏幕中央
    int centerHeight = 864 / 2;
    int interfaceWidth = 1536 / 8 * 4;//界面大小
    int interfaceHeight = 864 / 8 * 5;
    int buttonWidth = 300;//按钮大小
    int buttonHeight = 40;
    int line = 70;//行间距
    int labelWidth = 200;//标签大小
    int labelHeight = 40;
    int centInterfaceWidth = interfaceWidth / 2;//界面中央
    int centInterfaceHeight = interfaceHeight / 2;
    Font ftTitle = new Font("宋体", Font.BOLD, 30);//几个字体
    Font ftButton = new Font("楷体", Font.BOLD, 16);
    Font ftHint = new Font("宋体", Font.BOLD | Font.ITALIC, 14);
    Font ftQue = new Font("楷体", Font.BOLD, 30);
}