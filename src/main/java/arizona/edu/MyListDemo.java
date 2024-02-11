package arizona.edu;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
// import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


// Task 3
public class MyListDemo extends URLLoader{
    protected List<Product> list = new ArrayList<>();
    protected Set<String> resultSet = new HashSet<>();

    // Changed for Task 3, 4, 5
    @Override
    protected void processLine(String[] tokens) {
        if (tokens[7].equalsIgnoreCase("British Columbia")) {
            Product product = new Product();
            product.setId(Long.parseLong(tokens[0]));
            product.setName(tokens[1]);
            product.setAgentName(tokens[2]);
            product.setAgentId(Long.parseLong(tokens[3]));
            product.setPrice(Double.parseDouble(tokens[5]));
            product.setTerritory(tokens[7]);
            product.setCategory(tokens[8]);

            list.add(product);
        }
    }

    // Task 1
    public final void loadData() {
        URL url = null;
        BufferedReader in = null;
        try {
            url = new URL("https://sample-videos.com/csv/Sample-Spreadsheet-1000-rows.csv");
            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1));
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                processLine(split(inputLine));;
            }
        } catch(MalformedURLException e2) {
            e2.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Task 5
    protected void createXLS() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");
            int rowNum = 0;
            for (Product product : list) {
                XSSFRow row = sheet.createRow(rowNum++);
                createList(product, row);
            }
            FileOutputStream out = new FileOutputStream(new File("NewFile3.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Task 5
    private void createList(Product product, XSSFRow row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(product.getId());

        cell = row.createCell(1);
        cell.setCellValue(product.getName());
        
        cell = row.createCell(2);
        cell.setCellValue(product.getAgentName());
        
        cell = row.createCell(3);
        cell.setCellValue(product.getAgentId());
        
        cell = row.createCell(4);
        cell.setCellValue(product.getTerritory());
        
        cell = row.createCell(5);
        cell.setCellValue(product.getCategory());
    }

    protected void applySearch() {
        for (Product product : list) {
            resultSet.add(product.getName());
        }
    }

    // Task 7
    protected String[] split(String inputLine) {
        String[] tokens = inputLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].contains("\"")) {
                tokens[i] = tokens[i].replaceAll("\"\"", "\"");
                tokens[i] = tokens[i].replaceAll("^\"", ""); // beginning
                tokens[i] = tokens[i].replaceAll("\"$", ""); // end
            }
        }
        return tokens;
    }


    // Changed for Task 3, 4, 5, 6
    public static void main(String[] args) {
        MyListDemo demo = new MyListDemo();
        demo.loadData();

        demo.applySearch();

        System.out.println(demo.list.size());
        demo.createXLS();
        System.out.println(demo.resultSet.size());
    }
}