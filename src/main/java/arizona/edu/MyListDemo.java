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
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


// Task 3
public class MyListDemo extends URLLoader{
    protected List<Product> list = new ArrayList<>();
    // protected Set<String> resultSet = new HashSet<>();
    protected Set<String> all = new HashSet<>();
    protected Set<String> duplicates = new HashSet<>();
    protected Set<String> oneOccurence = null;
    // protected Map<String, Integer> map = new HashMap<>();
    // protected Map<String, List<Product>> map = null;
    protected Map<String, Double> map = null;

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

    // // Task 5
    // protected void createXLS() {
    //     try {
    //         XSSFWorkbook workbook = new XSSFWorkbook();
    //         XSSFSheet sheet = workbook.createSheet("sheet1");
    //         int rowNum = 0;
    //         for (Product product : list) {
    //             XSSFRow row = sheet.createRow(rowNum++);
    //             createList(product, row);
    //         }
    //         FileOutputStream out = new FileOutputStream(new File("NewFile3.xlsx"));
    //         workbook.write(out);
    //         out.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // Task 8, 9
    // protected void createXLS() {
    //     try {
    //         XSSFWorkbook workbook = new XSSFWorkbook();
    //         XSSFSheet sheet = workbook.createSheet("sheet1");
    //         int rowNum = 0;
    //         // oneOccurence = new HashSet<>(all);
    //         oneOccurence = new TreeSet<>(all);
    //         oneOccurence.removeAll(duplicates);
    //         for (String result : oneOccurence) {
    //             XSSFRow row = sheet.createRow(rowNum++);
    //             Cell cell = row.createCell(0);
    //             cell.setCellValue(result);
    //         }
    //         FileOutputStream out = new FileOutputStream(new File("NewFile3.xlsx"));
    //         workbook.write(out);
    //         out.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // Task 11
    // protected void createXLS() {
    //     try {
    //         XSSFWorkbook workbook = new XSSFWorkbook();
    //         XSSFSheet sheet = workbook.createSheet("sheet1");
    //         int rowNum = 0;
    //         LinkedHashMap<String, Integer> sortedMap = sortByValue(map);
    //         for (Entry<String, Integer> result : sortedMap.entrySet()) {
    //             XSSFRow row = sheet.createRow(rowNum++);
    //             Cell cell = row.createCell(0);
    //             cell.setCellValue(result.getKey());
    //             cell = row.createCell(1);
    //             cell.setCellValue(result.getValue());
    //         }
    //         FileOutputStream out = new FileOutputStream(new File("NewFile3.xlsx"));
    //         workbook.write(out);
    //         out.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // Task 12
    // protected void createXLS() {
    //     try {
    //         XSSFWorkbook workbook = new XSSFWorkbook();
    //         XSSFSheet sheet = workbook.createSheet("sheet1");
    //         int rowNum = 0;
    //         for (Entry<String, List<Product>> result : map.entrySet()) {
    //             XSSFRow row = sheet.createRow(rowNum++);
    //             Cell cell = row.createCell(0);
    //             cell.setCellValue(result.getKey());
    //             boolean skipLine = true;
    //             for (Product product : result.getValue()) {
    //                 if (skipLine) {
    //                     skipLine = false;
    //                 } else {
    //                     row = sheet.createRow(rowNum++);
    //                 }
    //                 cell = row.createCell(1);
    //                 cell.setCellValue(product.getName());
    //                 cell = row.createCell(2);
    //                 cell.setCellValue(product.getTerritory());
    //             }
    //         }
    //         FileOutputStream out = new FileOutputStream(new File("NewFile3.xlsx"));
    //         workbook.write(out);
    //         out.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // Task 11
    protected void createXLS() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");
            int rowNum = 0;
            for (Entry<String, Double> result : map.entrySet()) {
                XSSFRow row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0);
                cell.setCellValue(result.getKey());
                cell = row.createCell(1);
                cell.setCellValue(result.getValue());
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

    // Task 7
    // protected void applySearch() {
    //     for (Product product : list) {
    //         resultSet.add(product.getName());
    //     }
    // }
    
    // Task 8
    // private void applySearch() {
    //     for (Product product : list) {
    //         String name = product.getName();
    //         if (!all.add(name)) {
    //             duplicates.add(name);
    //         }
    //     }
    // }

    // Task 10
    // private void applySearch() {
    //     for (Product product : list) {
    //         String name = product.getName();
    //         Integer count = map.get(name);
    //         if (count == null) {
    //             count = 0;
    //         }
    //         map.put(name, ++count);
    //     }
    // }

    // Task 12
    // private void applySearch() {
    //     map = list.stream().collect(Collectors.groupingBy(Product::getTerritory));
    // }

    // Task 12 part 2
    // private void applySearch() {
    //     map = list.stream()
    //                         .sorted((o1, o2)->o1.getName().compareTo(o2.getName()))
    //                         .collect(Collectors.groupingBy(Product::getTerritory));
    // }

    // Task 13
    private void applySearch() {
        map = list.stream()
                            .sorted((o1, o2)->o1.getTerritory().compareTo(o2.getTerritory()))
                            .collect(Collectors.groupingBy(Product::getTerritory,Collectors.summingDouble(Product::getPrice)));
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

    // Task 10
    // public void printMap() {
    //     for (Entry<String, Integer> entry : map.entrySet()) {
    //         System.out.println(entry.getKey() + " " + entry.getValue());
    //     }
    // }

    // Task 11
    // public static LinkedHashMap<String, Integer> sortByValue(Map<String, Integer> map) {
    //     List<Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
    //     list.sort(Entry.comparingByValue());
    //     list.sort(new Comparator<Map.Entry<String, Integer>>() {
    //         @Override
    //         public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
    //             if (o2.getValue().equals(o1.getValue())) {
    //                 return o1.getKey().compareTo(o2.getKey());
    //             } else {
    //                 return o2.getValue().compareTo(o1.getValue());
    //             }
    //         }
    //     });

    //     LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
    //     for (Entry<String, Integer> entry : list) {
    //         result.put(entry.getKey(), entry.getValue());
    //     }

    //     return result;
    // }

    // Task 12
    public static <K extends Comparable<? super K>, V extends Comparable<? super V>> LinkedHashMap<K, V>
    sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        // list.sort(Entry.comparingByValue());
        list.sort(new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                if(o2.getValue().equals(o1.getValue())) {
                    return o1.getKey().compareTo(o2.getKey());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        LinkedHashMap<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }



    // Changed for Task 3, 4, 5, 6, 8, 10
    public static void main(String[] args) {
        MyListDemo demo = new MyListDemo();
        demo.loadData();

        demo.applySearch();

        //System.out.println(demo.list.size());
        demo.createXLS();
        // System.out.println(demo.resultSet.size());

        // System.out.println("Size all: " + demo.list.size());
        // System.out.println("Size unique: " + demo.all.size());
        // System.out.println("Size duplicates: " + demo.duplicates.size());
        // System.out.println("Size one occurence: " + demo.oneOccurence.size());

        // demo.printMap();
    }
}