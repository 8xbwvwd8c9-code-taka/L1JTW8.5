/*
 * Decompiled with CFR 0.152.
 */
package l1r.be;

import java.util.List;
import l1r.be.ServerBasePacket;
import l1r.bh.L1BoardTopic;

public class S_Board
extends ServerBasePacket {
    private static final int a = 8;

    public S_Board(int boardObjId) {
        this.a(boardObjId, 0);
    }

    public S_Board(int boardObjId, int number) {
        this.a(boardObjId, number);
    }

    private void a(int boardObjId, int number) {
        List<L1BoardTopic> topics = L1BoardTopic.a(number, 8);
        this.c(191);
        this.c(0);
        this.a(boardObjId);
        if (number == 0) {
            this.a(Integer.MAX_VALUE);
        } else {
            this.a(number);
        }
        this.c(topics.size());
        if (number == 0) {
            this.c(0);
            this.b(300);
        }
        for (L1BoardTopic topic : topics) {
            this.a(topic.a());
            this.a(topic.b());
            this.a(topic.c());
            this.a(topic.d());
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Board";
    }
}
