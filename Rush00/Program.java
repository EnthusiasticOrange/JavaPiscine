import java.util.*;

class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class BFSPoint extends Point {
    public List<BFSPoint> adjacentList;
    public BFSPoint parent;

    public BFSPoint(int x, int y) {
        super(x, y);
        this.adjacentList = new LinkedList<>();
    }

    public void addAdjacent(byte[][] gridMap) {
        if (this.x - 1 > 0 && gridMap[this.x - 1][this.y] != 0) {
            BFSPoint p = new BFSPoint(this.x - 1, this.y);
            p.parent = this;
            this.adjacentList.add(p);
        }
        if (this.x + 1 < gridMap.length - 1 && gridMap[this.x + 1][this.y] != 0) {
            BFSPoint p = new BFSPoint(this.x + 1, this.y);
            p.parent = this;
            this.adjacentList.add(p);
        }
        if (this.y - 1 > 0 && gridMap[this.x][this.y - 1] != 0) {
            BFSPoint p = new BFSPoint(this.x, this.y - 1);
            p.parent = this;
            this.adjacentList.add(p);
        }
        if (this.y + 1 < gridMap.length - 1 && gridMap[this.x][this.y + 1] != 0) {
            BFSPoint p = new BFSPoint(this.x, this.y + 1);
            p.parent = this;
            this.adjacentList.add(p);
        }
    }
}

class Wall {
    public int x;
    public int y;
    public int adjX;
    public int adjY;

    public Wall(int x, int y, int adjX, int adjY) {
        this.x = x;
        this.adjX = adjX;
        this.y = y;
        this.adjY = adjY;
    }
}

public class Program {
    public static void addWalls(int x, int y, byte[][] gridMap, List<Wall> wallLst) {
        if (x + 2 < gridMap.length && gridMap[x + 2][y] != 1) {
            wallLst.add(new Wall(x + 2, y, x + 1, y));
        }
        if (x - 2 >= 0 && gridMap[x - 2][y] != 1) {
            wallLst.add(new Wall(x - 2, y, x - 1, y));
        }
        if (y + 2 < gridMap.length && gridMap[x][y + 2] != 1) {
            wallLst.add(new Wall(x, y + 2, x, y + 1));
        }
        if (y - 2 >= 0 && gridMap[x][y - 2] != 1) {
            wallLst.add(new Wall(x, y - 2, x, y - 1));
        }
    }

    public static void addDeadWalls(int x, int y, byte[][] gridMap, List<Wall> deadWallLst) {
        if (x + 1 < gridMap.length && gridMap[x + 1][y] != 1) {
            deadWallLst.add(new Wall(x + 1, y, x, y));
        }
        if (x - 1 >= 0 && gridMap[x - 1][y] != 1) {
            deadWallLst.add(new Wall(x - 1, y, x - 1, y));
        }
        if (y + 1 < gridMap.length && gridMap[x][y + 1] != 1) {
            deadWallLst.add(new Wall(x, y + 1, x, y + 1));
        }
        if (y - 1 >= 0 && gridMap[x][y - 1] != 1) {
            deadWallLst.add(new Wall(x, y - 1, x, y - 1));
        }
    }

    public static void printMap(byte[][] gridMap) {
        int walls = 0;
        for (int i = 1; i < gridMap.length - 1; ++i) {
            for (int j = 1; j < gridMap.length - 1; ++j) {
                if (gridMap[i][j] == 0) {
                    System.out.print('#');
                    walls += 1;
                } else if (gridMap[i][j] == 1) {
                    System.out.print('.');
                } else if (gridMap[i][j] == 2) {
                    System.out.print('o');
                } else if (gridMap[i][j] == 3) {
                    System.out.print('O');
                } else if (gridMap[i][j] == 4) {
                    System.out.print('*');
                }
            }
            System.out.println();
        }
        System.out.printf("Walls: %d\n", walls);
    }

    public static boolean checkWall(Wall w, byte[][] gridMap) {
        int adjCount = 0;
        if (w.x + 1 < gridMap.length && gridMap[w.x + 1][w.y] == 1) {
            adjCount += 1;
        }
        if (w.x - 1 >= 0 && gridMap[w.x - 1][w.y] == 1) {
            adjCount += 1;
        }
        if (w.y + 1 < gridMap.length && gridMap[w.x][w.y + 1] == 1) {
            adjCount += 1;
        }
        if (w.y - 1 >= 0 && gridMap[w.x][w.y - 1] == 1) {
            adjCount += 1;
        }
        return adjCount == 0;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        int wallsCount = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);

        byte[][] gridMap = new byte[size + 2][size + 2];

        Random random = new Random();

        int randX = random.nextInt(size) + 1;
        int randY = random.nextInt(size) + 1;

        int curWalls = size * size;

        List<Wall> wallLst = new ArrayList<>();
        List<Wall> deadWallsLst = new ArrayList<>();
        List<Wall> emptyLst = new ArrayList<>();
        gridMap[randX][randY] = 1;
        curWalls -= 1;
        addDeadWalls(randX, randY, gridMap, deadWallsLst);

        addWalls(randX, randY, gridMap, wallLst);

        while (!wallLst.isEmpty() && curWalls > wallsCount) {
            int randWall = random.nextInt(wallLst.size());
            Wall w = wallLst.remove(randWall);
            if (gridMap[w.x][w.y] == 1) {
                continue;
            }
            if (!checkWall(w, gridMap)) {
                deadWallsLst.add(w);
            }

            if (0 < w.adjX && w.adjX < gridMap.length - 1
                    && 0 < w.adjY && w.adjY < gridMap.length - 1) {
                curWalls -= 1;
                gridMap[w.adjX][w.adjY] = 1;
                emptyLst.add(new Wall(w.adjX, w.adjY, w.x, w.y));
                addDeadWalls(w.adjX, w.adjY, gridMap, deadWallsLst);
            }
//            addDeadWalls(w.adjX, w.adjY, gridMap, deadWallsLst);

            if (curWalls > wallsCount) {
                if (0 < w.x && w.x < gridMap.length - 1
                        && 0 < w.y && w.y < gridMap.length - 1) {
                    curWalls -= 1;
                    gridMap[w.x][w.y] = 1;
                    emptyLst.add(new Wall(w.x, w.y, w.x, w.y));
                    addDeadWalls(w.x, w.y, gridMap, deadWallsLst);
                    addWalls(w.x, w.y, gridMap, wallLst);
                }
            } else {
                break;
            }
//            addWalls(w.x, w.y, gridMap, wallLst);
//            System.out.printf("Current walls: %d | Walls count %d | WallsLst: %d\n", curWalls, wallsCount, wallLst.size());
//            printMap(gridMap);
//            System.out.println();
        }

        while (!deadWallsLst.isEmpty() && curWalls > wallsCount) {
            int randWall = random.nextInt(deadWallsLst.size());
            Wall w = deadWallsLst.remove(randWall);
            if (0 < w.x && w.x < gridMap.length - 1
                    && 0 < w.y && w.y < gridMap.length - 1
                    && gridMap[w.x][w.y] != 1) {
                gridMap[w.x][w.y] = 1;
                curWalls -= 1;
            }
        }

        int randStart = random.nextInt(emptyLst.size());
        Wall start = emptyLst.remove(randStart);
        gridMap[start.x][start.y] = 2;

        int randEnd = random.nextInt(emptyLst.size());
        Wall end = emptyLst.remove(randEnd);
        gridMap[end.x][end.y] = 3;

        Queue<BFSPoint> bfsQueue = new LinkedList<>();

        byte[][] bfsMap = new byte[size + 2][size + 2];
        for (int i = 0; i < size + 2; ++i) {
            for (int j = 1; j < size + 2; ++j) {
                if (gridMap[i][j] != 0) {
                    bfsMap[i][j] = 1;
                }
            }
        }

        BFSPoint root = new BFSPoint(start.x, start.y);
        bfsMap[root.x][root.y] = -1;
        root.parent = root;
        root.addAdjacent(gridMap);
        bfsQueue.add(root);
        BFSPoint v = root;
        while (!bfsQueue.isEmpty()) {
            v = bfsQueue.remove();
            if (v.x == end.x && v.y == end.y) {
                break;
            }
            for (int i = 0; i < v.adjacentList.size(); ++i) {
                BFSPoint p = v.adjacentList.get(i);

                if (bfsMap[p.x][p.y] == 1) {
                    bfsMap[p.x][p.y] = -1;
                    p.addAdjacent(gridMap);
                    bfsQueue.add(p);
                }
            }
        }

        while (v != root) {
            gridMap[v.x][v.y] = 4;
            v = v.parent;
        }

        gridMap[end.x][end.y] = 3;
        printMap(gridMap);
    }
}