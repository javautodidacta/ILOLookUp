/*
 * This work is licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 *
 */

/*
 * This work is licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 *
 */

package iloLookUp;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;

public class Controller implements Initializable {
    public TextField input_number;
    public Button button_buscar;
    public ChoiceBox<String> select_type;
    public ChoiceBox<String> language_picker;
    public ChoiceBox<String> select_section;
    public CheckBox alinear_idiomas;
    public ImageView ilo_logo;

    private ResourceBundle l10n;

    private List<String> esParrafos;
    private List<String> enParrafos;
    private List<String> frParrafos;
    private List<String> idiomas;

    private final String BASE_URL_GB = "https://www.ilo.org/gb/GBSessions/GB";
    private final String BASE_URL_GB_PREVIOUS = "https://www.ilo.org/gb/GBSessions/previous-sessions/GB";
    private final String BASE_URL_CIT = "https://www.ilo.org/ilc/ILCSessions/";
    private final String BASE_URL_CIT_PREVIOUS = "https://www.ilo.org/ilc/ILCSessions/previous-sessions/";
    private final String BASE_URL_TRANSLATORS = "https://www.ilo.org/global/tools/translators/lang--";

    public void initialize(URL location, ResourceBundle resources) {
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals("es")) {
            ilo_logo.setImage(new ImageView("/css/images/logo_esp.png").getImage());
        } else if (locale.getLanguage().equals("fr")) {
            ilo_logo.setImage(new ImageView("/css/images/logo_fra.png").getImage());
        } else {
            ilo_logo.setImage(new ImageView("/css/images/logo_eng.png").getImage());
        }
        esParrafos = new ArrayList<>();
        enParrafos = new ArrayList<>();
        frParrafos = new ArrayList<>();
        idiomas = Arrays.asList("es", "en", "fr");

        l10n = resources;
        select_type.setItems(FXCollections.observableArrayList(
                l10n.getString("convenio_num"),
                l10n.getString("recomendacion_num"),
                l10n.getString("gb"),
                l10n.getString("sesion_cit"),
                l10n.getString("rodis"),
                l10n.getString("mail"),
                l10n.getString("multitrans_web")));
        select_type.setValue(l10n.getString("convenio_num"));

        select_section.setItems(FXCollections.observableList(Arrays.asList("--", "INS", "POL", "LILS", "PFA")));
        select_section.setValue("--");
        select_section.setVisible(false);

        language_picker.setItems(FXCollections.observableArrayList("es", "fr", "en"));
        language_picker.setValue("es");

        input_number.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() {
                        lookUp();
                        return null;
                    }
                };
                Thread th = new Thread(task);
                th.setDaemon(true);
                th.start();
            }
        });
    }

    public void comenzar(ActionEvent actionEvent) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                lookUp();
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    /**
     * Opens the website from the user input.
     */
    private void lookUp() {
        String language = language_picker.getValue();
        String url = "https://www.ilo.org/global/lang--" + language + "/index.htm";
        String item = select_type.getValue();
        if (item.equals(l10n.getString("convenio_num"))) {
            String number = input_number.getText();

            while (number.length() < 3) {
                number = "0" + number;
            }
            String parte1 = "https://www.ilo.org/dyn/normlex/";
            String parte2 = "/f?p=NORMLEXPUB:12100:0::NO::P12100_ILO_CODE:" + "C" + number;
            url = parte1 + language + parte2;
            if (alinear_idiomas.isSelected()) {
                alinearIdiomas(parte1, parte2, item, number);
            }
        } else if (item.equals(l10n.getString("recomendacion_num"))) {
            int number = Integer.parseInt(input_number.getText());
            int numero_recomendacion = 312338;
            numero_recomendacion += number;
            String numero_r_str = String.valueOf(numero_recomendacion);
            String parte1 = "https://www.ilo.org/dyn/normlex/";
            String parte2 = "/f?p=NORMLEXPUB:12100:0::NO:12100:P12100_INSTRUMENT_ID:" + numero_r_str + ":NO";
            url = parte1 + language + parte2;
            if (alinear_idiomas.isSelected()) {
                alinearIdiomas(parte1, parte2, item, input_number.getText());
            }
        } else if (item.equals(l10n.getString("gb"))) {
            String gbNumber = input_number.getText();
            if (gbNumber.equals("330")) {
                url = BASE_URL_GB_PREVIOUS;
            } else {
                url = BASE_URL_GB;
            }
            if (select_section.getValue().equals("--")) {
                url += gbNumber + "/lang--" + language + "/index.htm";
            } else {
                url += gbNumber + "/" + select_section.getValue() + "/lang--" + language + "/index.htm";
            }
        } else if (item.equals(l10n.getString("sesion_cit"))) {
            try {
                URL urlCode = new URL(BASE_URL_CIT + input_number.getText() + "/lang--" + language + "/index.htm");
                HttpURLConnection connection = (HttpURLConnection) urlCode.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int code = connection.getResponseCode();
                if (code == 200) {
                    url = BASE_URL_CIT + input_number.getText() + "/lang--" + language + "/index.htm";
                } else {
                    String number = input_number.getText();
                    String sessionOrder = "thSession";
                    if (number.endsWith("1") && !number.endsWith("11")) {
                        sessionOrder = "stSession";
                    } else if (number.endsWith("2") && !number.endsWith("12")) {
                        sessionOrder = "ndSession";
                    } else if (number.endsWith("3") && !number.endsWith("13")) {
                        sessionOrder = "rdSession";
                    }
                    url = BASE_URL_CIT_PREVIOUS + input_number.getText() + sessionOrder + "/lang--" + language + "/index.htm";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (item.equals(l10n.getString("recursos_traductores"))) {
            url = BASE_URL_TRANSLATORS + language + "/index.htm";
        } else if (item.equals(l10n.getString("mail"))) {
            url = "https://mail.ilo.org";
        } else if (item.equals(l10n.getString("rodis"))) {
            url = "https://rodis.ilo.org";
        } else if (item.equals(l10n.getString("multitrans_web"))) {
            url = "http://www.ilo.org/MultiTransWeb/Account.mvc/DirectAccess?username=Guest";
        }
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the {@code File} to export the text in the three languages.
     *
     * @param parte1 First part of the url.
     * @param parte2 Second part of the url.
     * @param item   Item selected - Convention or Recommendation.
     * @param number Number of selected item.
     */
    private void alinearIdiomas(String parte1, String parte2, String item, String number) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {

                String fileName = item.substring(0, 1).toUpperCase() + number;

                File csv = new File("C:\\Users\\" + System.getProperty("user.name")
                        + "\\Documents\\" + fileName + ".xlsx");
                try {
                    csv.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                getHtmlCode(parte1, parte2);

                createExcelFile(csv);

                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    /**
     * Retrieves and clean the Html code from the website, cleans it up and creates List of paragraphs for each language.
     *
     * @param parte1 First part of the url.
     * @param parte2 Second part of the url.
     */
    private void getHtmlCode(String parte1, String parte2) {
        for (int idioma = 0; idioma < 3; idioma++) {
            HttpURLConnection con = null;
            try {
                CookieManager cookieManager = new CookieManager();
                CookieHandler.setDefault(cookieManager);
                URL urlCode = new URL(parte1 + idiomas.get(idioma) + parte2);
                con = (HttpURLConnection) urlCode.openConnection();
                con.setRequestMethod("GET");
                con.getContent();

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String response_str = response.toString();
                String start = "<div class=\"frame\">";
                int startIndex = response_str.indexOf(start);
                String finish = "<div class=\"featureMultiple FM2 boxWithBorder\"";
                int finishIndex = response_str.indexOf(finish);
                String textWithTags = response_str.substring(startIndex + start.length(), finishIndex);

                textWithTags = textWithTags.replaceAll("(<[/]*[a-z]+[0-9]>)", "$1··");
                textWithTags = textWithTags.replaceAll("</a>", "··");
                textWithTags = textWithTags.replaceAll("\u00a0", " ");
                textWithTags = textWithTags.replace("Articles 5", "··Article 5··");
                textWithTags = textWithTags.replaceAll("([0-9]+\\. )", "··$1");
                String cleanedHTML = Jsoup.clean(textWithTags, Whitelist.none());

                if (idioma == 0) {
                    esParrafos = new ArrayList<>(Arrays.asList(cleanedHTML.trim().split("\\s*··\\s*")));
                    esParrafos.removeAll(Collections.singleton(""));
                } else if (idioma == 1) {
                    enParrafos = new ArrayList<>(Arrays.asList(cleanedHTML.split("\\s*··\\s*")));
                    enParrafos.removeAll(Collections.singleton(""));
                } else {
                    frParrafos = new ArrayList<>(Arrays.asList(cleanedHTML.split("\\s*··\\s*")));
                    frParrafos.removeAll(Collections.singleton(""));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
        }
    }

    /**
     * Creates the Excel file, populates the cells and exports it to Excel format.
     *
     * @param csv nio.File with the path.
     */
    private void createExcelFile(File csv) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(csv.getName());

        List<List<String>> parrafos = new ArrayList<>();

        int limit = Math.min(enParrafos.size(), esParrafos.size());
        limit = Math.min(limit, frParrafos.size());

        for (int i = 0; i < limit; i++) {
            parrafos.add(Arrays.asList(esParrafos.get(i) + "\t",
                    enParrafos.get(i) + "\t", frParrafos.get(i) + "\n"));
        }
        int rowCount = 0;

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setWrapText(true);

        for (List<String> parrafo : parrafos) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (String field : parrafo) {
                Cell cell = row.createCell(columnCount++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new String(field.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            }
        }

        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 10000);

        try (FileOutputStream outputStream = new FileOutputStream(csv.getAbsolutePath())) {
            workbook.write(outputStream);
            workbook.close();
            Desktop.getDesktop().open(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hides/shows info on the page depending on user input.
     *
     * @param actionEvent Connection to the interface.
     */
    public void updateNumbers(ActionEvent actionEvent) {
        if (select_type.getValue().equals(l10n.getString("convenio_num"))
                || select_type.getValue().equals(l10n.getString("recomendacion_num"))) {
            input_number.setPromptText("1, 28, 111...");
            input_number.setVisible(true);
            select_section.setVisible(false);
            alinear_idiomas.setVisible(true);
            language_picker.setVisible(true);
        } else if (select_type.getValue().equals(l10n.getString("gb"))) {
            input_number.setPromptText("320, 331, 335...");
            input_number.setVisible(true);
            select_section.setVisible(true);
            alinear_idiomas.setVisible(false);
            language_picker.setVisible(true);
        } else if (select_type.getValue().equals(l10n.getString("sesion_cit"))) {
            input_number.setPromptText("90, 105, 109...");
            input_number.setVisible(true);
            select_section.setVisible(false);
            alinear_idiomas.setVisible(false);
            language_picker.setVisible(true);
        } else if (select_type.getValue().equals(l10n.getString("recursos_traductores"))) {
            input_number.setVisible(false);
            select_section.setVisible(false);
            alinear_idiomas.setVisible(false);
            language_picker.setVisible(true);
        } else if (select_type.getValue().equals(l10n.getString("mail"))
                || select_type.getValue().equals(l10n.getString("rodis"))
                || select_type.getValue().equals(l10n.getString("multitrans_web"))) {
            input_number.setVisible(false);
            select_section.setVisible(false);
            alinear_idiomas.setVisible(false);
            language_picker.setVisible(false);
        }
    }
}
