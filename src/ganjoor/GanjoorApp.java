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

    private static void showFalHafez() {
        frame.getContentPane().removeAll();
        responseOmen = new JTextArea(20, 40);
        responseOmen.setEditable(false);
        responseOmen.setLineWrap(true);
        responseOmen.setWrapStyleWord(true);
        responseOmen.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // راست‌چین کردن متن
        JButton fetchButton = new JButton("فال");
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    String falInfo = fetchOmenData();
                    SwingUtilities.invokeLater(() -> responseOmen.setText(falInfo));
                }).start();
            }
        });

        frame.add(new JLabel(" نیت کن و کلیک کن"));
        frame.add(fetchButton);
        frame.add(new JScrollPane(responseOmen));
        frame.revalidate();
        frame.repaint();
    }

    private static String fetchOmenData() {
        try {
            String apiUrl = "https://api.ganjoor.net/api/ganjoor/hafez/faal";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray verses = jsonObject.getJSONArray("verses");
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < verses.length(); i++) {
                result.append(verses.getJSONObject(i).getString("text")).append("\n");
            }

            return result.toString();

        } catch (Exception e) {
            return "خطا در دریافت فال. لطفاً اینترنت خود را بررسی کنید.";
        }
    }

    private static void showBio() {
        frame.getContentPane().removeAll();
        responseBiography = new JTextArea(20, 40);
        responseBiography.setEditable(false);
        responseBiography.setLineWrap(true);
        responseBiography.setWrapStyleWord(true);
        responseBiography.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // راست‌چین کردن متن
        JButton fetchButton = new JButton("دریافت زندگینامه");
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    String bioInfo = fetchPoetsBio();
                    SwingUtilities.invokeLater(() -> responseBiography.setText(bioInfo));
                }).start();
            }
        });

        frame.add(fetchButton);
        frame.add(new JScrollPane(responseBiography));
        frame.revalidate();
        frame.repaint();
    }

    private static String fetchPoetsBio() {
        try {
            String apiUrl = "https://api.ganjoor.net/api/ganjoor/poets";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject poet = jsonArray.getJSONObject(i);
                String name = poet.getString("name");
                String birthYear = poet.optString("birthYearInLHijri", "نامشخص");
                String deathYear = poet.optString("deathYearInLHijri", "نامشخص");
                String birthPlace = poet.optString("birthPlace", "نامشخص");
                String deathPlace = poet.optString("deathPlace", "نامشخص");
                result.append("نام: ").append(name).append("\n")
                      .append("سال تولد: ").append(birthYear).append("\n")
                      .append("سال وفات: ").append(deathYear).append("\n")
                      .append("محل تولد: ").append(birthPlace).append("\n")
                      .append("محل وفات: ").append(deathPlace).append("\n\n");
            }

            return result.toString();

        } catch (Exception e) {
            return "خطا در دریافت اطلاعات شعرا. لطفاً اینترنت خود را بررسی کنید.";
        }
    }
}