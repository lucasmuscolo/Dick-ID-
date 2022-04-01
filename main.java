
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


public class main {

    private static String sn = null;
    public static void main(String[] args){
        //System.out.println(getSerialNumber());
        GUI();
    }
    public static final String getSerialNumber() {

        if (sn != null) {
            return sn;
        }

        OutputStream os = null;
        InputStream is = null;

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec(new String[] { "wmic", "bios", "get", "serialnumber" });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        os = process.getOutputStream();
        is = process.getInputStream();

        try {
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = new Scanner(is);
        try {
            while (sc.hasNext()) {
                String next = sc.next();
                if ("SerialNumber".equals(next)) {
                    sn = sc.next().trim();
                    break;
                }
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (sn == null) {
            throw new RuntimeException("Cannot find computer SN");
        }

        return sn;
    }
    public static void GUI(){
        JFrame f = new JFrame("HDI Hard Drive Identifier");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(Color.DARK_GRAY);

        JTextField area = new JTextField("");
        area.setBounds(10,13,180,35);
        area.setHorizontalAlignment(0);
        area.setDisabledTextColor(Color.BLACK);
        area.setEnabled(false);
        area.setFont(new Font("arial",Font.BOLD,25));

        JButton bot = new JButton("Disk ID");
        bot.setBounds(200,13,120,35);
        bot.addActionListener(action ->{
            area.setText(getSerialNumber());
        });

        f.add(bot);
        f.add(area);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setSize(350,100);

    }
}