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
        final File fileName = new File(level);                      // Create object fileName from class File in File library imported.
        try (FileReader inputFile = new FileReader(fileName)) {     // Try to create new object from class FileReader.
            Scanner ip = new Scanner(inputFile);                    // Create object ip from class Scanner.
            String line = ip.nextLine();                            // Input variable line in string data type.

            StringTokenizer tokens = new StringTokenizer(line);     // Create object tokens from class StringTokenizer in library imported.

            // parseInt(): Method that parses the string argument and returns a primitive int.
            BombermanGame.level = Integer.parseInt(tokens.nextToken());   // To refer to variable level in main file.
            height = Integer.parseInt(tokens.nextToken());
            width = Integer.parseInt(tokens.nextToken());

            while (ip.hasNextLine()) {
                id_objects = new int[width][height];                 // Create new object id_object from main file.
                list_kill = new int[width][height];                  // Create new object lít_kill from main file.   Main file: RunBomberman.java
                for (int i = 0; i < height; ++i) {
                    String lineTile = ip.nextLine();                // Input variable lineTile in string data type.
                    StringTokenizer tokenTile = new StringTokenizer(lineTile);      // Create object tokenTile from class StringTokenizer in library imported.

                    for (int j = 0; j < width; j++) {
                        int token = Integer.parseInt(tokenTile.nextToken());
                        Entity entity;                              // Create object entity from class Entity.
                        //System.out.print(token);
                        // This switch statement running, and we got a full map for a game.
                        // Through the program, in the for-loop statement, we can get the map according to each loop it passed.
                        switch (token) {
                            case 1:
                                entity = new Portal(j, i, Sprite.grass.getFxImage());       // In case 1, set entity object equal to object portal with scaled size.
                                token = 0;
                                break;
                            case 2:
                                entity = new Wall(j, i, Sprite.wall.getFxImage());          // In case 2, set entity object equal to object wall with scaled size.
                                break;
                            case 3:
                                entity = new Brick(j, i, Sprite.brick.getFxImage());        // In case 3, set entity object equal to object brick with scaled size.
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
            e.printStackTrace();                        // printStackTrace(): Help to understand where the problem is actually happening.
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
