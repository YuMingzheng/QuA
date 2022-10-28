package MainWindow;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Yu Mingzheng
 * @date 2022/10/18
 * @apiNote
 */
public class User {
    // 用户的ID
    private String ID;
    // 用户对应的任务数
    private String taskNum;

    public User(String ID) {
        this.ID = ID;
        this.taskNum = readTaskNum();
    }

    public User(String ID , String taskNum){
        this.ID = ID;
        this.taskNum = taskNum;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    private String readTaskNum(){
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

        String[] Nos = new String[users.size()];
        for(int i = 0 ; i < users.size() ; ++i){
            if(Objects.equals(this.ID , users.get(i).getID())){
                return users.get(i).getTaskNum();
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "ID:" + this.ID + " taskNum:" + this.taskNum;
    }

    public static void main(String[] args) {
        User u1 = new User("1");
        System.out.println(u1);
    }
}
