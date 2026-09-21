/*
 * Decompiled with CFR 0.152.
 */
package l1rpb;

import java.io.IOException;
import java.io.InputStream;
import l1rpb.aa;
import l1rpb.ab;
import l1rpb.ap;
import l1rpb.g;
import l1rpb.h;
import l1rpb.k;
import l1rpb.n;
import l1rpb.s;
import l1rpb.y;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface x
extends aa,
y {
    public ab<? extends x> m();

    public boolean equals(Object var1);

    public int hashCode();

    public String toString();

    public a N();

    public a M();

    public static interface a
    extends aa,
    y.a {
        public a j();

        public a c(x var1);

        public x ak();

        public x aj();

        public a i();

        public a d(h var1) throws IOException;

        public a d(h var1, n var2) throws IOException;

        public k.a J();

        public a g(k.f var1);

        public a a(k.f var1);

        public a d(k.f var1, Object var2);

        public a f(k.f var1);

        public a b(k.f var1, int var2, Object var3);

        public a c(k.f var1, Object var2);

        public a e(ap var1);

        public a b(ap var1);

        public a d(g var1) throws s;

        public a d(g var1, n var2) throws s;

        public a d(byte[] var1) throws s;

        public a d(byte[] var1, int var2, int var3) throws s;

        public a d(byte[] var1, n var2) throws s;

        public a d(byte[] var1, int var2, int var3, n var4) throws s;

        public a e(InputStream var1) throws IOException;

        public a e(InputStream var1, n var2) throws IOException;

        public boolean b(InputStream var1) throws IOException;

        public boolean b(InputStream var1, n var2) throws IOException;
    }
}

