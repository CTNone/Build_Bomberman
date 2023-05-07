package uet.oop.bomberman.graphics;

import static uet.oop.bomberman.BombermanGame.*;
import uet.oop.bomberman.BombermanGame;

import uet.oop.bomberman.entities.*;

import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.SpeedItem;

import java.io.*;
import java.util.*;

public class createMap {
    public createMap(String level) {
        System.out.println(System.getProperty("user.dir"));
        final File fileName = new File(level);                      // Tạo đối tượng fileName từ class File trong File thư viện File đã nhập.
        try (FileReader inputFile = new FileReader(fileName)) {     // Cố gắng tạo đối tượng mới từ lớp FileReader.
            Scanner ip = new Scanner(inputFile);                    // Tạo ip đối tượng từ class Scanner.
            String line = ip.nextLine();                            // Đầu vào dòng biến trong kiểu dữ liệu chuỗi.

            StringTokenizer tokens = new StringTokenizer(line);     // Tạo object token từ class StringTokenizer trong thư viện imported.

            // parseInt(): Phương thức phân tích cú pháp đối số chuỗi và trả về một int nguyên thủy.
            BombermanGame.level = Integer.parseInt(tokens.nextToken());   // Để tham khảo mức độ biến trong tệp chính.
            height = Integer.parseInt(tokens.nextToken());
            width = Integer.parseInt(tokens.nextToken());

            while (ip.hasNextLine()) {
                id_objects = new int[width][height];                 // Tạo đối tượng mới id_object từ tệp chính.
                list_kill = new int[width][height];                  // Tạo đối tượng mới lít_kill từ tệp chính.   Tệp chính: RunBomberman.java
                for (int i = 0; i < height; ++i) {
                    String lineTile = ip.nextLine();                // Đầu vào biến lineTile trong kiểu dữ liệu chuỗi.
                    StringTokenizer tokenTile = new StringTokenizer(lineTile);      // Tạo đối tượng tokenTile từ class StringTokenizer trong thư viện imported.

                    for (int j = 0; j < width; j++) {
                        int token = Integer.parseInt(tokenTile.nextToken());
                        Entity entity;                              // Tạo object entity từ class Entity.
                        //System.out.print(token);
                        // Câu lệnh chuyển đổi này đang chạy và  đã có một bản đồ đầy đủ cho một trò chơi.
                        // Thông qua chương trình, trong câu lệnh for-loop, chúng ta có thể lấy bản đồ theo từng vòng lặp mà nó đi qua.
                        switch (token) {
                            case 1:
                                entity = new Portal(j, i, Sprite.grass.getFxImage());       // Trong trường hợp 1, đặt đối tượng thực thể bằng cổng đối tượng với kích thước tỷ lệ.
                                token = 0;
                                break;
                            case 2:
                                entity = new Wall(j, i, Sprite.wall.getFxImage());          // Trong trường hợp 2, đặt đối tượng thực thể bằng tường đối tượng có kích thước tỷ lệ.
                                break;
                            case 3:
                                entity = new Brick(j, i, Sprite.brick.getFxImage());        // Trong trường hợp 3, đặt đối tượng thực thể bằng gạch đối tượng có kích thước tỷ lệ.
                                break;
                            case 6:
                                entity = new SpeedItem(j, i, Sprite.brick.getFxImage());
                                break;
                            case 7:
                                entity = new FlameItem(j, i, Sprite.brick.getFxImage());
                                break;
                            default:
                                entity = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                        id_objects[j][i] = token;        //
                        block.add(entity);              //
                    }
                    //    System.out.println();
                }
            }
        } catch (IOException e) {                       // Catch exception
            e.printStackTrace();                        // printStackTrace(): Giúp hiểu vấn đề đang thực sự xảy ra ở đâu.
        }
    }
    /**
    public createMap(String filename) {
      //  System.out.println(System.getProperty("user.dir"));
        try {
            FileReader reader = new FileReader(filename); // khai báo để đọc tệp dữ liệu
            Scanner ip = new Scanner(reader);                    // Create object ip from class Scanner.
            String line = ip.nextLine();                            // Input variable line in string data type.

            StringTokenizer tokens = new StringTokenizer(line);     // Create object tokens from class StringTokenizer in library imported.

            //parseInt(): trả về một int nguyên thủy.
            BombermanGame.level = Integer.parseInt(tokens.nextToken());   // To refer to variable level in main file.
            height = Integer.parseInt(tokens.nextToken());
            width = Integer.parseInt(tokens.nextToken());
            id_objects = new char[width][height];

            while (ip.hasNextLine()) {

                for (int i = 0; i < height; ++i) {
                    String lineTile = ip.nextLine();                // Input variable lineTile in string data type.
                    StringTokenizer tokenTile = new StringTokenizer(lineTile);      // Create object tokenTile from class StringTokenizer in library imported.

                    char token =' ';

                    for (int j = 0; j < width; j++) {
                        token = lineTile.charAt(j);

//                    int j= 0;
//                    for(char token : lineTile.toCharArray()){  // for( int j =0; j< width; j++ )
                        Entity entity;                              // Create object entity from class Entity.
                        System.out.print(token);

                        // Câu lệnh chuyển đổi này đang chạy và chúng tôi đã có một bản đồ đầy đủ cho một trò chơi.
                        // lấy bản đồ theo từng vòng lặp mà nó đi qua.
                        switch (token) {
                            case '#':
                                entity = new Wall(j, i, Sprite.wall.getFxImage());          // In case 2, set entity object equal to object wall with scaled size.
                                break;
                            case '*':
                                entity = new Brick(j, i, Sprite.brick.getFxImage());        // In case 3, set entity object equal to object brick with scaled size.
                                break;
                            default:
                                entity = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                        id_objects[j][i] = token;        //
                        block.add(entity);
                        //   j++;
                    }

                    System.out.println();
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
     */
}
