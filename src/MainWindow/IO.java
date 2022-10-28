package MainWindow;

import com.csvreader.CsvReader;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.io.*;
import java.util.List;

/**
 * @author Yu Mingzheng
 * @date 2022/10/18
 * @apiNote
 */
public class IO {
    public IO(){

    }

    public ArrayList<User> readUsers(){
        ArrayList<User> users = new ArrayList<>();
        File userFile = new File("UserList\\users.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(userFile));
            String lineDta = "";
            while ((lineDta = br.readLine()) != null) {
                String[] aLine = lineDta.split(",");
                users.add(new User(aLine[0] , aLine[1]));
            }

            br.close();
        }catch (FileNotFoundException e){
            System.out.println("没有找到指定文件");
        }catch (IOException e){
            System.out.println("文件读写出错");
        }

        return users;
    }

    public String[] getAllNo(){
        ArrayList<User> users = readUsers();
        String[] Nos = new String[users.size()];
        for(int i = 0 ; i < users.size() ; ++i){
            Nos[i] = users.get(i).getID();
        }
        return Nos;
    }

    public ArrayList<QA> readQAs(){
        ArrayList<QA> qas = new ArrayList<>();

        try {
            CsvReader csvReader = new CsvReader("Q&As\\qa_database4.csv", '|', Charset.forName("UTF-8"));
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                qas.add(new QA(csvReader.get(1) , new String[]{
                    csvReader.get(2),
                    csvReader.get(3),
                    csvReader.get(4),
                    csvReader.get(5),
                    csvReader.get(6),
                } , csvReader.get(7)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return qas;
    }

    public void writeOneRound(Round round){
        String csvFilePath = "output\\output_4.csv";

        List<String> dataList = new ArrayList<>();
        dataList.add(round.toString());
        writeToCsv("", dataList, csvFilePath, true);
    }

    public void writeOneRound(String roundStr){
        String csvFilePath = "output\\output_4.csv";

        List<String> dataList = new ArrayList<>();
        dataList.add(roundStr);
        writeToCsv("", dataList, csvFilePath, true);
    }

    public void writeToCsv(String headLabel, List<String> dataList, String filePath, boolean addFlag) {
        BufferedWriter buffWriter = null;
        try {
            //根据指定路径构建文件对象
            File csvFile = new File(filePath);
            //文件输出流对象，第二个参数时boolean类型,为true表示文件追加（在已有的文件中追加内容）
            FileWriter writer = new FileWriter(csvFile, addFlag);
            //构建缓存字符输出流（不推荐使用OutputStreamWriter）
            buffWriter = new BufferedWriter(writer, 1024);
            //头部不为空则写入头部，并且换行
            if (StringUtils.isNotBlank(headLabel)) {
                buffWriter.write(headLabel);
                buffWriter.newLine();
            }
            //遍历list
            for (String rowStr : dataList) {
                //如果数据不为空，则写入文件内容,并且换行
                if (StringUtils.isNotBlank(rowStr)) {
                    buffWriter.write(rowStr);
                    buffWriter.newLine();//文件写完最后一个换行不用处理
                }
            }
            //刷新流，也就是把缓存中剩余的内容输出到文件
            buffWriter.flush();
        } catch (Exception e) {
            System.out.println("写入csv出现异常");
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                if (buffWriter != null) {
                    buffWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> readFromCsv(String filePath) {
        ArrayList<String> dataList = new ArrayList<>();
        BufferedReader buffReader = null;
        try {
            //构建文件对象
            File csvFile = new File(filePath);
            //判断文件是否存在
            if (!csvFile.exists()) {
                System.out.println("文件不存在");
                return dataList;
            }
            //构建字符输入流
            FileReader fileReader = new FileReader(csvFile);
            //构建缓存字符输入流
            buffReader = new BufferedReader(fileReader);
            String line = "";
            //根据合适的换行符来读取一行数据,赋值给line
            while ((line = buffReader.readLine()) != null) {
                if (StringUtils.isNotBlank(line)) {
                    //数据不为空则加入列表
                    dataList.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("读取csv文件发生异常");
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                if (buffReader != null) {
                    buffReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }


    public static void main(String[] args) throws IOException {
        IO io = new IO();

        ArrayList<QA> temp = io.readQAs();

        int i=0;
    }

}
