package edu.school21.chaseLogic;

public class Enemy {

    private final char holderChar;
    private final char enemyChar;
    private final char playerChar;
    private final char wallChar;

    public Enemy (char holderChar, char enemyChar, char playerChar, char wallChar) {
        this.holderChar = holderChar;
        this.enemyChar = enemyChar;
        this.playerChar = playerChar;
        this.wallChar = wallChar;
    }

    public int[] generateTurn(GameObject gameObject, char[][] map) {

        char[][] tempMap = createTempMap(gameObject, map);

        int[] start = {-1, -1};
        for (int i = 0; i < tempMap.length; i++) {
            for (int j = 0; j < tempMap.length; j++) {
                if (map[i][j] == gameObject.getEndCondition()) {
                    start[0] = i;
                    start[1] = j;
                }
            }
        }
        return wave(start, gameObject.getSign(), tempMap);
    }

    private int[] wave(int[] start, char end, char[][] map) {

        int[] res = {-1, -1};
        char d = 48;
        map[start[0]][start[1]] = d;
        boolean isNotEnd = true;
        int[] neighborgood;

        while (isNotEnd) {
            isNotEnd = false;
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map.length; j++) {
                    if (map[i][j] == d) {
                        for (int x = 0; x < 4; x++) {
                            neighborgood = findNeighborgood(x, i, j, map.length);
                            if (neighborgood[0] != -1) {
                                if (map[neighborgood[0]][neighborgood[1]] == end) {
                                    res[0] = i;
                                    res[1] = j;
                                    return res;
                                }
                                if (map[neighborgood[0]][neighborgood[1]] == this.holderChar) {
                                    map[neighborgood[0]][neighborgood[1]] = (char)((int)d + 1);
                                    isNotEnd = true;
                                }
                            }
                        }
                    }
                }
            }
            d += 1;
        }
        return res;
    }

    private int[] findNeighborgood(int x, int i, int j, int mapLength) {
        int[] res = {-1, -1};
        if (x == 0) {
            if (j % mapLength != 0) {
                res[0] = i;
                res[1] = j - 1;
            }
        }
        else if (x == 1) {
            if (i > 0) {
                res[0] = i - 1;
                res[1] = j;
            }
        }
        else if (x == 2) {
            if ((j +1 ) % mapLength != 0) {
                res[0] = i;
                res[1] = j + 1;
            }
        }
        else {
            if (i != mapLength - 1) {
                res[0] = i + 1;
                res[1] = j;
            }
        }
        return res;
    }

    private char[][] createTempMap(GameObject gameObject, char[][] map) {
        char[][] tempMap = new char[map.length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == gameObject.getEndCondition())
                    tempMap[i][j] = gameObject.getEndCondition();
                else if (map[i][j] != this.holderChar)
                    tempMap[i][j] = this.wallChar;
                else
                    tempMap[i][j] = this.holderChar;
            }
        }
        int p = gameObject.getPosition();
        tempMap[p / map.length][p % map.length] = gameObject.getSign();
        return tempMap;
    }
}