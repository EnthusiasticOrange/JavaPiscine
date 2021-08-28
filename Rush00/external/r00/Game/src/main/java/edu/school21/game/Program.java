package edu.school21.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

import edu.school21.chaseLogic.GameObject;
import edu.school21.chaseLogic.Enemy;

@Parameters(separators = "=")
public class Program {

    private char[][] map;
    private ArrayList<Integer> occupiedNumbers;
    private GameObject player;
    private GameObject goal;
    private ArrayList<GameObject> enemies;
    private int[] walls;
    private char holderChar = ' ';
    private char enemyChar;
    private char playerChar;
    private char wallChar;
    private char goalChar;
    private Ansi.BColor enemyColor;
    private Ansi.BColor playerColor;
    private Ansi.BColor wallColor;
    private Ansi.BColor goalColor;
    private Ansi.BColor holderColor;
    private Enemy enemy;

    @Parameter(names = "--enemiesCount", required = true)
    private int enemiesCount;
    @Parameter(names = "--wallsCount", required = true)
    private int wallsCount;
    @Parameter(names = "--size", required = true)
    private int mapSize;
    @Parameter(names = "--profile", required = true)
    private String mode;

    public static void main(String[] args) {
        Program program = new Program();
        try {
            JCommander.newBuilder().addObject(program).build().parse(args);
        } catch (Exception e) {
            System.out.println("Usage: java -cp ../../lib/jcommander-1.79.jar:../../lib/JCDP-4.0.2.jar:."
                    + " edu.school21.game.Program --enemiesCount=<count of enemies> --wallsCount=<count of obstracles>"
                    + " --size=<size of map> --profile=<game mode (production/dev)>");
            System.exit(-1);
        }
        program.go();
    }

    private void go() {
        if (!usage() || !checkWork() || !setProperties())
            System.exit(-1);
        clearnScreen();
        prepareGame();
        mainCircle();
    }

    private boolean setProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("resources/application-" + this.mode + ".properties");
            properties.load(fis);
            this.enemyChar = properties.getProperty("enemy.char").toCharArray()[0];
            this.playerChar = properties.getProperty("player.char").toCharArray()[0];
            this.wallChar = properties.getProperty("wall.char").toCharArray()[0];
            this.goalChar = properties.getProperty("goal.char").toCharArray()[0];
            this.enemyColor = Ansi.BColor.valueOf(properties.getProperty("enemy.color"));
            this.playerColor = Ansi.BColor.valueOf(properties.getProperty("player.color"));
            this.wallColor = Ansi.BColor.valueOf(properties.getProperty("wall.color"));
            this.goalColor = Ansi.BColor.valueOf(properties.getProperty("goal.color"));
            this.holderColor = Ansi.BColor.valueOf(properties.getProperty("holder.color"));
            fis.close();
        } catch (Exception e) {
            System.err.println("Incorrect properties file");
            return false;
        }
        return true;
    }

    private void prepareGame() {
        createMap(mapSize);
        createIndexOdObjects();
        this.enemy = new Enemy(this.holderChar, this.enemyChar, this.playerChar, this.wallChar);
        int position[] = enemy.generateTurn(this.player, this.map);
        while (position[0] == -1) {
            createMap(mapSize);
            createIndexOdObjects();
            position = this.enemy.generateTurn(this.player, map);
        }
        printColoredMap();
    }

    private void createMap(int mapSize) {
        this.map = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++)
                this.map[i][j] = this.holderChar;
        }
    }

    private void createIndexOdObjects() {
        this.occupiedNumbers = new ArrayList<>();
        this.goal = new GameObject(generateUniquNumber(), this.goalChar);
        this.player = new GameObject(generateUniquNumber(), this.playerChar, this.goalChar);
        this.enemies = new ArrayList<>();
        for (int i = 0; i < enemiesCount; i++)
            enemies.add(new GameObject(generateUniquNumber(), this.enemyChar, this.playerChar));
        this.walls = new int[wallsCount];
        for (int i = 0; i < wallsCount; i++)
            this.walls[i] = generateUniquNumber();

        putObjectOnMap(this.player.getSign(), this.player.getPosition());
        putObjectOnMap(this.goal.getSign(), this.goal.getPosition());
        enemies.forEach(o -> putObjectOnMap(o.getSign(), o.getPosition()));
        for (int i = 0; i < this.wallsCount; i++)
            putObjectOnMap(wallChar, this.walls[i]);
    }

    private void putObjectOnMap(char sign, int position) {
        int row = 0;
        while (position >= mapSize) {
            row++;
            position -= mapSize;
        }
        this.map[row][position] = sign;
    }

    private void removeObjectOnMap(int position) {
        int row = 0;
        while (position >= mapSize) {
            row++;
            position -= mapSize;
        }
        this.map[row][position] = this.holderChar;
    }

    private void mainCircle() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isNotEnd = true;
        int gameResult = -1;

        while (isNotEnd) {
            if (!thereIsTurn(this.player)) {
                gameResult = 2;
                isNotEnd = false;
                break;
            }
            turnPlayer(reader, this.player);
            if (checkIsEndCondition(this.player, this.goal)) {
                gameResult = 1;
                isNotEnd = false;
                break;
            }
            if (this.mode.equals("dev"))
                printColoredMap();
            for (GameObject e : enemies) {
                turnAI(e, this.map, reader);
                if (checkIsEndCondition(e, this.player)) {
                    gameResult = 0;
                    ;
                    isNotEnd = false;
                    break;
                }
            }
            if (!this.mode.equals("dev")) {
                clearnScreen();
                printColoredMap();
            }
        }
        finalScreen(gameResult);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void clearnScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void finalScreen(int gameResult) {
        System.out.println("");
        System.out.println("**//**//**//");
        if (gameResult == 0)
            System.out.println("You lose!");
        else if (gameResult == 1)
            System.out.println("You WIN!");
        else if (gameResult == 2)
            System.out.println("There is not space for turn, you lose");
    }

    private boolean thereIsTurn(GameObject gob) {
        if (positionInMap(gob.getPosition() - 1) && gob.getPosition() % mapSize != 0
                && (whoOnPosition(gob.getPosition() - 1) == this.holderChar
                        || whoOnPosition(gob.getPosition() - 1) == gob.getEndCondition()))
            return true;
        if (positionInMap(gob.getPosition() + 1) && (gob.getPosition() + 1) % mapSize != 0
                && (whoOnPosition(gob.getPosition() + 1) == this.holderChar
                        || whoOnPosition(gob.getPosition() + 1) == gob.getEndCondition()))
            return true;
        if (positionInMap(gob.getPosition() - mapSize) && (whoOnPosition(gob.getPosition() - mapSize) == this.holderChar
                || whoOnPosition(gob.getPosition() - mapSize) == gob.getEndCondition()))
            return true;
        if (positionInMap(gob.getPosition() + mapSize) && (whoOnPosition(gob.getPosition() + mapSize) == this.holderChar
                || whoOnPosition(gob.getPosition() + mapSize) == gob.getEndCondition()))
            return true;
        return false;
    }

    private void turnAI(GameObject gameObject, char[][] map, BufferedReader reader) {
        int position[] = this.enemy.generateTurn(gameObject, map);
        if (position[0] != -1) {
            int p = (position[0] * map.length) + position[1];
            if (mode.equals("dev")) {
                try {
                    System.out.println("Confirm the sper by entered 8");
                    String str;
                    while (true) {
                        str = reader.readLine();
                        if (str.length() > 0 && str.toCharArray()[0] == '8') {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            putItInNewPosition(gameObject, p);
                            printColoredMap();
            if (mode.equals("dev"))
                printColoredMap();
        }
    }

    private void turnPlayer(BufferedReader reader, GameObject gameObject) {
        int newPosition;
        String str;
        while (true) {
            try {
                str = reader.readLine();
                if (str.length() > 0) {
                    if (str.toCharArray()[0] == '9') {
                        finalScreen(0);
                        System.exit(0);
                    }
                    if ((newPosition = isCorrectTurn(gameObject, str.toCharArray()[0])) != -1) {
                        putItInNewPosition(gameObject, newPosition);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private int isCorrectTurn(GameObject gameObject, char vector) {
        int newPosition = -1;
        if (vector == 'w')
            newPosition = gameObject.getPosition() - mapSize;
        else if (vector == 'a' && gameObject.getPosition() % mapSize != 0)
            newPosition = gameObject.getPosition() - 1;
        else if (vector == 'd' && (gameObject.getPosition() + 1) % mapSize != 0)
            newPosition = gameObject.getPosition() + 1;
        else if (vector == 's')
            newPosition = gameObject.getPosition() + mapSize;
        if (!positionInMap(newPosition) || (whoOnPosition(newPosition) != this.holderChar
                && whoOnPosition(newPosition) != gameObject.getEndCondition())) {
            System.out.println("Incorrect vector");
            newPosition = -1;
        }
        return newPosition;
    }

    private void putItInNewPosition(GameObject gameObject, int newPosition) {
        removeObjectOnMap(gameObject.getPosition());
        putObjectOnMap(gameObject.getSign(), newPosition);
        gameObject.setPosition(newPosition);
    }

    private boolean checkIsEndCondition(GameObject gameObject, GameObject aim) {
        if (gameObject.getPosition() == aim.getPosition())
            return true;
        return false;
    }

    private boolean positionInMap(int i) {
        if (i < 0 || i >= (mapSize * mapSize))
            return false;
        return true;
    }

    private char whoOnPosition(int position) {
        int row = 0;
        while (position >= mapSize) {
            row++;
            position -= mapSize;
        }
        return this.map[row][position];
    }

    private int generateUniquNumber() {
        boolean trigger = true;
        int i = 0;
        while (trigger) {
            i = (int) (Math.random() * (mapSize * mapSize));
            trigger = false;
            for (Integer j : occupiedNumbers)
                if (i == j)
                    trigger = true;
        }
        occupiedNumbers.add(i);
        return i;
    }

    private void printMap() {
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++)
                System.out.print(this.map[i][j]);
            System.out.println("");
        }
        System.out.println("");
    }

    private void printColoredMap() {

        ColoredPrinter pholder = new ColoredPrinter.Builder(0, false).background(holderColor).build();
        ColoredPrinter pplayer = new ColoredPrinter.Builder(0, false).foreground(FColor.BLACK).background(playerColor)
                .build();
        ColoredPrinter pgoal = new ColoredPrinter.Builder(0, false).foreground(FColor.BLACK).background(goalColor)
                .build();
        ColoredPrinter penemy = new ColoredPrinter.Builder(0, false).foreground(FColor.BLACK).background(enemyColor)
                .build();
        ColoredPrinter pwall = new ColoredPrinter.Builder(0, false).foreground(FColor.BLACK).background(wallColor)
                .build();

        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                if (whoOnPosition((i * mapSize) + j) == this.holderChar)
                    pholder.print(this.map[i][j]);
                if (whoOnPosition((i * mapSize) + j) == this.player.getSign())
                    pplayer.print(this.map[i][j]);
                if (whoOnPosition((i * mapSize) + j) == this.goal.getSign())
                    pgoal.print(this.map[i][j]);
                if (whoOnPosition((i * mapSize) + j) == this.enemyChar)
                    penemy.print(this.map[i][j]);
                if (whoOnPosition((i * mapSize) + j) == this.wallChar)
                    pwall.print(this.map[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private boolean checkWork() {
        if (enemiesCount + wallsCount + 1 >= (mapSize * mapSize)) {
            try {
                throw new IllegalParametersException("The map is too small for this number of things");
            } catch (IllegalParametersException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
        return true;
    }

    private boolean usage() {
        if (this.enemiesCount < 0 || this.wallsCount < 0 || this.mapSize <= 0) {
            try {
                throw new IllegalParametersException("Usage: java -cp ../../lib/jcommander-1.79.jar:../../lib/JCDP-4.0.2.jar:." + 
                " edu.school21.game.Program --enemiesCount=<count of enemies> --wallsCount=<count of obstracles>" +
                " --size=<size of map> --profile=<game mode (production/dev)>");
            } catch (IllegalParametersException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}