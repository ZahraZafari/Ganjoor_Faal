package ganjoor;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class GanjoorApp {

    private static JFrame frame;
    private static JButton buttonOmen;
    private static JButton buttonBiography;
    private static JButton buttonWordSearch;

    private static JTextArea responseOmen;
    private static JTextArea responseBiography;
    private static JTextArea responseWordSearch;
    private static JTextField textWordSearch;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("گنجور");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 450);
            frame.setLayout(new FlowLayout());

            buttonOmen = new JButton("فال حافظ");
            buttonBiography = new JButton("زندگینامه شعرا");
            buttonWordSearch = new JButton("جستجوی کلمه و شعر");

            buttonOmen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showFalHafez();
                }
            });

            buttonBiography.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showBio();
                }
            });

            buttonWordSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showSearch();
                }
            });

            frame.add(buttonOmen);
            frame.add(buttonBiography);
            frame.add(buttonWordSearch);
            frame.setVisible(true);
        });
    }

}