package auto.hunt;

import java.util.ArrayDeque;
import java.util.Queue;

public final class AutoHuntMovePlanner {
    public interface Passability {
        boolean canStep(int x, int y, int heading);
    }

    private static final int MAX_RANGE = 20;
    private static final int[] DX = {0, 1, 1, 1, 0, -1, -1, -1};
    private static final int[] DY = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] OFFSETS = {0, 1, -1, 2, -2, 3, -3, 4};

    private AutoHuntMovePlanner() {
    }

    public static int nextHeading(int startX, int startY,
                                  int targetX, int targetY,
                                  int engageRange,
                                  Passability passability) {
        if (passability == null) {
            throw new NullPointerException("passability");
        }
        if (engageRange < 0) {
            throw new IllegalArgumentException("engageRange must be non-negative");
        }
        int initialDistance = distance(startX, startY, targetX, targetY);
        if (initialDistance <= engageRange || initialDistance > MAX_RANGE) {
            return -1;
        }

        int size = MAX_RANGE * 2 + 1;
        boolean[][] visited = new boolean[size][size];
        Queue<Node> queue = new ArrayDeque<Node>();
        visited[MAX_RANGE][MAX_RANGE] = true;
        queue.add(new Node(startX, startY, -1));

        while (!queue.isEmpty()) {
            Node node = queue.remove();
            int direct = direction(node.x, node.y, targetX, targetY);
            for (int offset : OFFSETS) {
                int heading = normalize(direct + offset);
                int nx = node.x + DX[heading];
                int ny = node.y + DY[heading];
                int rx = nx - startX;
                int ry = ny - startY;
                if (Math.abs(rx) > MAX_RANGE || Math.abs(ry) > MAX_RANGE) {
                    continue;
                }
                int ix = rx + MAX_RANGE;
                int iy = ry + MAX_RANGE;
                if (visited[ix][iy]) {
                    continue;
                }
                if (!passability.canStep(node.x, node.y, heading)) {
                    continue;
                }
                visited[ix][iy] = true;
                int firstHeading = node.firstHeading < 0 ? heading : node.firstHeading;
                if (distance(nx, ny, targetX, targetY) <= engageRange) {
                    return firstHeading;
                }
                queue.add(new Node(nx, ny, firstHeading));
            }
        }
        return -1;
    }

    private static int distance(int x, int y, int targetX, int targetY) {
        return Math.max(Math.abs(targetX - x), Math.abs(targetY - y));
    }

    private static int direction(int x, int y, int targetX, int targetY) {
        int dx = Integer.compare(targetX, x);
        int dy = Integer.compare(targetY, y);
        if (dx == 0 && dy < 0) return 0;
        if (dx > 0 && dy < 0) return 1;
        if (dx > 0 && dy == 0) return 2;
        if (dx > 0 && dy > 0) return 3;
        if (dx == 0 && dy > 0) return 4;
        if (dx < 0 && dy > 0) return 5;
        if (dx < 0 && dy == 0) return 6;
        if (dx < 0 && dy < 0) return 7;
        return 0;
    }

    private static int normalize(int heading) {
        int result = heading % 8;
        return result < 0 ? result + 8 : result;
    }

    private static final class Node {
        private final int x;
        private final int y;
        private final int firstHeading;

        private Node(int x, int y, int firstHeading) {
            this.x = x;
            this.y = y;
            this.firstHeading = firstHeading;
        }
    }
}
