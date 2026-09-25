/*
 * Decompiled with CFR 0.152.
 */
package an;

import a.a;
import a.aa;
import a.ab;
import a.ap;
import a.k;
import a.m;
import a.n;
import a.p;
import a.s;
import a.x;
import a.y;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class g {
    private static k.a a;
    private static p.g b;
    private static k.a c;
    private static p.g d;
    private static k.a e;
    private static p.g f;
    private static k.a g;
    private static p.g h;
    private static k.a i;
    private static p.g j;
    private static k.g k;

    static {
        String[] descriptorData = new String[]{"\n\u0013PBMessageALL7.proto\u0012 l1j.server.server.datas.protobuf\"\u008d\u0002\n\u0006type26\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0003(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u00f9\u0002\n\u0006type27\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4", "\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\barray_18\u0018\u0012 \u0001(\f\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\bvalue_20\u0018\u0014 \u0001(\u0005\u0012\u0010\n\barray_21\u0018\u0015 \u0003(\u0005\"\u008d\u0002\n\u0006type28\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 ", "\u0003(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0003(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0003(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0003(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0003(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type29\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007array_2\u0018\u0002 \u0003(\f\u0012\u000f\n\u0007array_3\u0018\u0003 \u0003(\f\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n", "\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type30\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0003(\f\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL7"};
        k.g.a assigner = new k.g.a(){

            @Override
            public m a(k.g root) {
                k = root;
                a = an.g.a().e().get(0);
                b = new p.g(a, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                c = an.g.a().e().get(1);
                d = new p.g(c, new String[]{"Value1", "Value2", "Array3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Array18", "Value19", "Value20", "Array21"});
                e = an.g.a().e().get(2);
                f = new p.g(e, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                g = an.g.a().e().get(3);
                h = new p.g(g, new String[]{"Value1", "Array2", "Array3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                i = an.g.a().e().get(4);
                j = new p.g(i, new String[]{"Array1", "Array2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                return null;
            }
        };
        k.g.a(descriptorData, new k.g[0], assigner);
    }

    private g() {
    }

    public static void a(m registry) {
    }

    public static k.g a() {
        return k;
    }

    public static final class an.g$a
    extends p
    implements b {
        private static final an.g$a r;
        private final ap s;
        public static ab<an.g$a> a;
        private int t;
        public static final int b = 1;
        private int u;
        public static final int c = 2;
        private int v;
        public static final int d = 3;
        private int w;
        public static final int e = 4;
        private List<Integer> x;
        public static final int f = 5;
        private int y;
        public static final int g = 6;
        private int z;
        public static final int h = 7;
        private int A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private int C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

        static {
            a = new a.c<an.g$a>(){

                public an.g$a c(a.h input, n extensionRegistry) throws s {
                    return new an.g$a(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            r = new an.g$a(true);
            r.ag();
        }

        private an.g$a(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private an.g$a(boolean noInit) {
            this.s = ap.c();
        }

        public static an.g$a h() {
            return r;
        }

        public an.g$a k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private an.g$a(a.h input, n extensionRegistry) throws s {
            this.ag();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block25: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block25;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.t |= 1;
                                this.u = input.g();
                                break;
                            }
                            case 16: {
                                this.t |= 2;
                                this.v = input.g();
                                break;
                            }
                            case 24: {
                                this.t |= 4;
                                this.w = input.g();
                                break;
                            }
                            case 32: {
                                if ((mutable_bitField0_ & 8) != 8) {
                                    this.x = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 8;
                                }
                                this.x.add(input.g());
                                break;
                            }
                            case 34: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 8) != 8 && input.x() > 0) {
                                    this.x = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 8;
                                }
                                while (input.x() > 0) {
                                    this.x.add(input.g());
                                }
                                input.g(limit);
                                break;
                            }
                            case 40: {
                                this.t |= 8;
                                this.y = input.g();
                                break;
                            }
                            case 48: {
                                this.t |= 0x10;
                                this.z = input.g();
                                break;
                            }
                            case 56: {
                                this.t |= 0x20;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x40;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x80;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x100;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x200;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x400;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x800;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x1000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x2000;
                                this.I = input.g();
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 8) == 8) {
                    this.x = Collections.unmodifiableList(this.x);
                }
                this.s = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return a;
        }

        @Override
        protected p.g l() {
            return b.a(an.g$a.class, a.class);
        }

        public ab<an.g$a> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.t & 1) == 1;
        }

        @Override
        public int p() {
            return this.u;
        }

        @Override
        public boolean q() {
            return (this.t & 2) == 2;
        }

        @Override
        public int r() {
            return this.v;
        }

        @Override
        public boolean s() {
            return (this.t & 4) == 4;
        }

        @Override
        public int t() {
            return this.w;
        }

        @Override
        public List<Integer> u() {
            return this.x;
        }

        @Override
        public int v() {
            return this.x.size();
        }

        @Override
        public int a(int index) {
            return this.x.get(index);
        }

        @Override
        public boolean w() {
            return (this.t & 8) == 8;
        }

        @Override
        public int x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0;
            this.v = 0;
            this.w = 0;
            this.x = Collections.emptyList();
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            if ((this.t & 2) == 2) {
                output.a(2, this.v);
            }
            if ((this.t & 4) == 4) {
                output.a(3, this.w);
            }
            int i2 = 0;
            while (i2 < this.x.size()) {
                output.a(4, this.x.get(i2));
                ++i2;
            }
            if ((this.t & 8) == 8) {
                output.a(5, this.y);
            }
            if ((this.t & 0x10) == 16) {
                output.a(6, this.z);
            }
            if ((this.t & 0x20) == 32) {
                output.a(7, this.A);
            }
            if ((this.t & 0x40) == 64) {
                output.a(8, this.B);
            }
            if ((this.t & 0x80) == 128) {
                output.a(9, this.C);
            }
            if ((this.t & 0x100) == 256) {
                output.a(10, this.D);
            }
            if ((this.t & 0x200) == 512) {
                output.a(11, this.E);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(12, this.F);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(13, this.G);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(14, this.H);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.g(1, this.u);
            }
            if ((this.t & 2) == 2) {
                size += a.i.g(2, this.v);
            }
            if ((this.t & 4) == 4) {
                size += a.i.g(3, this.w);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.x.size()) {
                dataSize += a.i.h(this.x.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.u().size();
            if ((this.t & 8) == 8) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static an.g$a a(a.g data) throws s {
            return a.d(data);
        }

        public static an.g$a a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.g$a a(byte[] data) throws s {
            return a.d(data);
        }

        public static an.g$a a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.g$a a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static an.g$a a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static an.g$a b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static an.g$a b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static an.g$a a(a.h input) throws IOException {
            return a.d(input);
        }

        public static an.g$a a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.g$a.aa();
        }

        public static a a(an.g$a prototype) {
            return an.g$a.aa().a(prototype);
        }

        public a ae() {
            return an.g$a.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements b {
            private int a;
            private int b;
            private int c;
            private int d;
            private List<Integer> e = Collections.emptyList();
            private int f;
            private int g;
            private int h;
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            public static final k.a k() {
                return a;
            }

            @Override
            protected p.g l() {
                return b.a(an.g$a.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = 0;
                this.a &= 0xFFFFFFFB;
                this.e = Collections.emptyList();
                this.a &= 0xFFFFFFF7;
                this.f = 0;
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                this.k = 0;
                this.a &= 0xFFFFFDFF;
                this.l = 0;
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                return this;
            }

            public a n() {
                return an.g$a$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return a;
            }

            public an.g$a I() {
                return an.g$a.h();
            }

            public an.g$a M() {
                an.g$a result = this.N();
                if (!result.a()) {
                    throw an.g$a$a.b(result);
                }
                return result;
            }

            public an.g$a N() {
                an.g$a result = new an.g$a(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.u = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.v = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.w = this.d;
                if ((this.a & 8) == 8) {
                    this.e = Collections.unmodifiableList(this.e);
                    this.a &= 0xFFFFFFF7;
                }
                result.x = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 8;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x10;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x80;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x100;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x200;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x400;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x800;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x1000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x2000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof an.g$a) {
                    return this.a((an.g$a)other);
                }
                super.a(other);
                return this;
            }

            public a a(an.g$a other) {
                if (other == an.g$a.h()) {
                    return this;
                }
                if (other.o()) {
                    this.b(other.p());
                }
                if (other.q()) {
                    this.c(other.r());
                }
                if (other.s()) {
                    this.d(other.t());
                }
                if (!other.x.isEmpty()) {
                    if (this.e.isEmpty()) {
                        this.e = other.x;
                        this.a &= 0xFFFFFFF7;
                    } else {
                        this.aw();
                        this.e.addAll(other.x);
                    }
                    this.t_();
                }
                if (other.w()) {
                    this.f(other.x());
                }
                if (other.y()) {
                    this.g(other.z());
                }
                if (other.A()) {
                    this.h(other.B());
                }
                if (other.C()) {
                    this.i(other.D());
                }
                if (other.E()) {
                    this.j(other.F());
                }
                if (other.G()) {
                    this.k(other.H());
                }
                if (other.K()) {
                    this.l(other.L());
                }
                if (other.S()) {
                    this.m(other.T());
                }
                if (other.U()) {
                    this.n(other.V());
                }
                if (other.W()) {
                    this.o(other.X());
                }
                if (other.Y()) {
                    this.p(other.Z());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                an.g$a parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (an.g$a)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a b(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public int r() {
                return this.c;
            }

            public a c(int value) {
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public int t() {
                return this.d;
            }

            public a d(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
                this.a &= 0xFFFFFFFB;
                this.d = 0;
                this.t_();
                return this;
            }

            private void aw() {
                if ((this.a & 8) != 8) {
                    this.e = new ArrayList<Integer>(this.e);
                    this.a |= 8;
                }
            }

            @Override
            public List<Integer> u() {
                return Collections.unmodifiableList(this.e);
            }

            @Override
            public int v() {
                return this.e.size();
            }

            @Override
            public int a(int index) {
                return this.e.get(index);
            }

            public a a(int index, int value) {
                this.aw();
                this.e.set(index, value);
                this.t_();
                return this;
            }

            public a e(int value) {
                this.aw();
                this.e.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends Integer> values) {
                this.aw();
                p.a.a(values, this.e);
                this.t_();
                return this;
            }

            public a ab() {
                this.e = Collections.emptyList();
                this.a &= 0xFFFFFFF7;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public int x() {
                return this.f;
            }

            public a f(int value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFEF;
                this.f = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public int z() {
                return this.g;
            }

            public a g(int value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFDF;
                this.g = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public int B() {
                return this.h;
            }

            public a h(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a ae() {
                this.a &= 0xFFFFFFBF;
                this.h = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public int D() {
                return this.i;
            }

            public a i(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a af() {
                this.a &= 0xFFFFFF7F;
                this.i = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public int F() {
                return this.j;
            }

            public a j(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ag() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public int H() {
                return this.k;
            }

            public a k(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a an() {
                this.a &= 0xFFFFFDFF;
                this.k = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public int L() {
                return this.l;
            }

            public a l(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a ao() {
                this.a &= 0xFFFFFBFF;
                this.l = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.m;
            }

            public a m(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ap() {
                this.a &= 0xFFFFF7FF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.n;
            }

            public a n(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aq() {
                this.a &= 0xFFFFEFFF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.o;
            }

            public a o(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFDFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.p;
            }

            public a p(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }
        }
    }

    public static interface b
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public boolean s();

        public int t();

        public List<Integer> u();

        public int v();

        public int a(int var1);

        public boolean w();

        public int x();

        public boolean y();

        public int z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();

        public boolean G();

        public int H();

        public boolean K();

        public int L();

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();
    }

    public static final class c
    extends p
    implements d {
        private static final c x;
        private final ap y;
        public static ab<c> a;
        private int z;
        public static final int b = 1;
        private int A;
        public static final int c = 2;
        private int B;
        public static final int d = 3;
        private a.g C;
        public static final int e = 4;
        private int D;
        public static final int f = 5;
        private int E;
        public static final int g = 6;
        private int F;
        public static final int h = 7;
        private int G;
        public static final int i = 8;
        private int H;
        public static final int j = 9;
        private int I;
        public static final int k = 10;
        private int J;
        public static final int l = 11;
        private int K;
        public static final int n = 12;
        private int L;
        public static final int o = 13;
        private int M;
        public static final int p = 14;
        private int N;
        public static final int q = 15;
        private int O;
        public static final int r = 16;
        private int P;
        public static final int s = 17;
        private int Q;
        public static final int t = 18;
        private a.g R;
        public static final int u = 19;
        private int S;
        public static final int v = 20;
        private int T;
        public static final int w = 21;
        private List<Integer> U;
        private byte V = (byte)-1;
        private int W = -1;
        private static final long X = 0L;

        static {
            a = new a.c<c>(){

                public c c(a.h input, n extensionRegistry) throws s {
                    return new c(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            x = new c(true);
            x.as();
        }

        private c(p.a<?> builder) {
            super(builder);
            this.y = builder.b_();
        }

        private c(boolean noInit) {
            this.y = ap.c();
        }

        public static c h() {
            return x;
        }

        public c k() {
            return x;
        }

        @Override
        public final ap b_() {
            return this.y;
        }

        private c(a.h input, n extensionRegistry) throws s {
            this.as();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block31: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block31;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.z |= 1;
                                this.A = input.g();
                                break;
                            }
                            case 16: {
                                this.z |= 2;
                                this.B = input.g();
                                break;
                            }
                            case 26: {
                                this.z |= 4;
                                this.C = input.l();
                                break;
                            }
                            case 32: {
                                this.z |= 8;
                                this.D = input.g();
                                break;
                            }
                            case 40: {
                                this.z |= 0x10;
                                this.E = input.g();
                                break;
                            }
                            case 48: {
                                this.z |= 0x20;
                                this.F = input.g();
                                break;
                            }
                            case 56: {
                                this.z |= 0x40;
                                this.G = input.g();
                                break;
                            }
                            case 64: {
                                this.z |= 0x80;
                                this.H = input.g();
                                break;
                            }
                            case 72: {
                                this.z |= 0x100;
                                this.I = input.g();
                                break;
                            }
                            case 80: {
                                this.z |= 0x200;
                                this.J = input.g();
                                break;
                            }
                            case 88: {
                                this.z |= 0x400;
                                this.K = input.g();
                                break;
                            }
                            case 96: {
                                this.z |= 0x800;
                                this.L = input.g();
                                break;
                            }
                            case 104: {
                                this.z |= 0x1000;
                                this.M = input.g();
                                break;
                            }
                            case 112: {
                                this.z |= 0x2000;
                                this.N = input.g();
                                break;
                            }
                            case 120: {
                                this.z |= 0x4000;
                                this.O = input.g();
                                break;
                            }
                            case 128: {
                                this.z |= 0x8000;
                                this.P = input.g();
                                break;
                            }
                            case 136: {
                                this.z |= 0x10000;
                                this.Q = input.g();
                                break;
                            }
                            case 146: {
                                this.z |= 0x20000;
                                this.R = input.l();
                                break;
                            }
                            case 152: {
                                this.z |= 0x40000;
                                this.S = input.g();
                                break;
                            }
                            case 160: {
                                this.z |= 0x80000;
                                this.T = input.g();
                                break;
                            }
                            case 168: {
                                if ((mutable_bitField0_ & 0x100000) != 0x100000) {
                                    this.U = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x100000;
                                }
                                this.U.add(input.g());
                                break;
                            }
                            case 170: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 0x100000) != 0x100000 && input.x() > 0) {
                                    this.U = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x100000;
                                }
                                while (input.x() > 0) {
                                    this.U.add(input.g());
                                }
                                input.g(limit);
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 0x100000) == 0x100000) {
                    this.U = Collections.unmodifiableList(this.U);
                }
                this.y = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return c;
        }

        @Override
        protected p.g l() {
            return d.a(c.class, a.class);
        }

        public ab<c> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.z & 1) == 1;
        }

        @Override
        public int p() {
            return this.A;
        }

        @Override
        public boolean q() {
            return (this.z & 2) == 2;
        }

        @Override
        public int r() {
            return this.B;
        }

        @Override
        public boolean s() {
            return (this.z & 4) == 4;
        }

        @Override
        public a.g t() {
            return this.C;
        }

        @Override
        public boolean u() {
            return (this.z & 8) == 8;
        }

        @Override
        public int v() {
            return this.D;
        }

        @Override
        public boolean w() {
            return (this.z & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.E;
        }

        @Override
        public boolean y() {
            return (this.z & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.F;
        }

        @Override
        public boolean A() {
            return (this.z & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.G;
        }

        @Override
        public boolean C() {
            return (this.z & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.H;
        }

        @Override
        public boolean E() {
            return (this.z & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.I;
        }

        @Override
        public boolean G() {
            return (this.z & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.J;
        }

        @Override
        public boolean K() {
            return (this.z & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.K;
        }

        @Override
        public boolean S() {
            return (this.z & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.L;
        }

        @Override
        public boolean U() {
            return (this.z & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.M;
        }

        @Override
        public boolean W() {
            return (this.z & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.N;
        }

        @Override
        public boolean Y() {
            return (this.z & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.O;
        }

        @Override
        public boolean aa() {
            return (this.z & 0x8000) == 32768;
        }

        @Override
        public int ab() {
            return this.P;
        }

        @Override
        public boolean ae() {
            return (this.z & 0x10000) == 65536;
        }

        @Override
        public int af() {
            return this.Q;
        }

        @Override
        public boolean ag() {
            return (this.z & 0x20000) == 131072;
        }

        @Override
        public a.g I_() {
            return this.R;
        }

        @Override
        public boolean J_() {
            return (this.z & 0x40000) == 262144;
        }

        @Override
        public int K_() {
            return this.S;
        }

        @Override
        public boolean L_() {
            return (this.z & 0x80000) == 524288;
        }

        @Override
        public int M_() {
            return this.T;
        }

        @Override
        public List<Integer> N_() {
            return this.U;
        }

        @Override
        public int an() {
            return this.U.size();
        }

        @Override
        public int a(int index) {
            return this.U.get(index);
        }

        private void as() {
            this.A = 0;
            this.B = 0;
            this.C = a.g.d;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 0;
            this.O = 0;
            this.P = 0;
            this.Q = 0;
            this.R = a.g.d;
            this.S = 0;
            this.T = 0;
            this.U = Collections.emptyList();
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.V;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.V = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.z & 1) == 1) {
                output.a(1, this.A);
            }
            if ((this.z & 2) == 2) {
                output.a(2, this.B);
            }
            if ((this.z & 4) == 4) {
                output.a(3, this.C);
            }
            if ((this.z & 8) == 8) {
                output.a(4, this.D);
            }
            if ((this.z & 0x10) == 16) {
                output.a(5, this.E);
            }
            if ((this.z & 0x20) == 32) {
                output.a(6, this.F);
            }
            if ((this.z & 0x40) == 64) {
                output.a(7, this.G);
            }
            if ((this.z & 0x80) == 128) {
                output.a(8, this.H);
            }
            if ((this.z & 0x100) == 256) {
                output.a(9, this.I);
            }
            if ((this.z & 0x200) == 512) {
                output.a(10, this.J);
            }
            if ((this.z & 0x400) == 1024) {
                output.a(11, this.K);
            }
            if ((this.z & 0x800) == 2048) {
                output.a(12, this.L);
            }
            if ((this.z & 0x1000) == 4096) {
                output.a(13, this.M);
            }
            if ((this.z & 0x2000) == 8192) {
                output.a(14, this.N);
            }
            if ((this.z & 0x4000) == 16384) {
                output.a(15, this.O);
            }
            if ((this.z & 0x8000) == 32768) {
                output.a(16, this.P);
            }
            if ((this.z & 0x10000) == 65536) {
                output.a(17, this.Q);
            }
            if ((this.z & 0x20000) == 131072) {
                output.a(18, this.R);
            }
            if ((this.z & 0x40000) == 262144) {
                output.a(19, this.S);
            }
            if ((this.z & 0x80000) == 524288) {
                output.a(20, this.T);
            }
            int i2 = 0;
            while (i2 < this.U.size()) {
                output.a(21, this.U.get(i2));
                ++i2;
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.W;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.z & 1) == 1) {
                size += a.i.g(1, this.A);
            }
            if ((this.z & 2) == 2) {
                size += a.i.g(2, this.B);
            }
            if ((this.z & 4) == 4) {
                size += a.i.c(3, this.C);
            }
            if ((this.z & 8) == 8) {
                size += a.i.g(4, this.D);
            }
            if ((this.z & 0x10) == 16) {
                size += a.i.g(5, this.E);
            }
            if ((this.z & 0x20) == 32) {
                size += a.i.g(6, this.F);
            }
            if ((this.z & 0x40) == 64) {
                size += a.i.g(7, this.G);
            }
            if ((this.z & 0x80) == 128) {
                size += a.i.g(8, this.H);
            }
            if ((this.z & 0x100) == 256) {
                size += a.i.g(9, this.I);
            }
            if ((this.z & 0x200) == 512) {
                size += a.i.g(10, this.J);
            }
            if ((this.z & 0x400) == 1024) {
                size += a.i.g(11, this.K);
            }
            if ((this.z & 0x800) == 2048) {
                size += a.i.g(12, this.L);
            }
            if ((this.z & 0x1000) == 4096) {
                size += a.i.g(13, this.M);
            }
            if ((this.z & 0x2000) == 8192) {
                size += a.i.g(14, this.N);
            }
            if ((this.z & 0x4000) == 16384) {
                size += a.i.g(15, this.O);
            }
            if ((this.z & 0x8000) == 32768) {
                size += a.i.g(16, this.P);
            }
            if ((this.z & 0x10000) == 65536) {
                size += a.i.g(17, this.Q);
            }
            if ((this.z & 0x20000) == 131072) {
                size += a.i.c(18, this.R);
            }
            if ((this.z & 0x40000) == 262144) {
                size += a.i.g(19, this.S);
            }
            if ((this.z & 0x80000) == 524288) {
                size += a.i.g(20, this.T);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.U.size()) {
                dataSize += a.i.h(this.U.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 2 * this.N_().size();
            this.W = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static c a(a.g data) throws s {
            return a.d(data);
        }

        public static c a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static c a(byte[] data) throws s {
            return a.d(data);
        }

        public static c a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static c a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static c a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static c b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static c b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static c a(a.h input) throws IOException {
            return a.d(input);
        }

        public static c a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a ao() {
            return a.aN();
        }

        public a ap() {
            return an.g$c.ao();
        }

        public static a a(c prototype) {
            return an.g$c.ao().a(prototype);
        }

        public a aq() {
            return an.g$c.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.aq();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.aq();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.ap();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ap();
        }

        public static final class a
        extends p.a<a>
        implements d {
            private int a;
            private int b;
            private int c;
            private a.g d = a.g.d;
            private int e;
            private int f;
            private int g;
            private int h;
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;
            private int q;
            private int r;
            private a.g s = a.g.d;
            private int t;
            private int u;
            private List<Integer> v = Collections.emptyList();

            public static final k.a k() {
                return c;
            }

            @Override
            protected p.g l() {
                return d.a(c.class, a.class);
            }

            private a() {
                this.aM();
            }

            private a(p.b parent) {
                super(parent);
                this.aM();
            }

            private void aM() {
                m;
            }

            private static a aN() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = a.g.d;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = 0;
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                this.k = 0;
                this.a &= 0xFFFFFDFF;
                this.l = 0;
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                this.q = 0;
                this.a &= 0xFFFF7FFF;
                this.r = 0;
                this.a &= 0xFFFEFFFF;
                this.s = a.g.d;
                this.a &= 0xFFFDFFFF;
                this.t = 0;
                this.a &= 0xFFFBFFFF;
                this.u = 0;
                this.a &= 0xFFF7FFFF;
                this.v = Collections.emptyList();
                this.a &= 0xFFEFFFFF;
                return this;
            }

            public a n() {
                return an.g$c$a.aN().a(this.N());
            }

            @Override
            public k.a J() {
                return c;
            }

            public c I() {
                return an.g$c.h();
            }

            public c M() {
                c result = this.N();
                if (!result.a()) {
                    throw an.g$c$a.b(result);
                }
                return result;
            }

            public c N() {
                c result = new c(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.A = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.B = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.C = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.D = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.E = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.F = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.G = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.H = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.I = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.J = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.K = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.L = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.M = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.N = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.O = this.p;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x8000;
                }
                result.P = this.q;
                if ((from_bitField0_ & 0x10000) == 65536) {
                    to_bitField0_ |= 0x10000;
                }
                result.Q = this.r;
                if ((from_bitField0_ & 0x20000) == 131072) {
                    to_bitField0_ |= 0x20000;
                }
                result.R = this.s;
                if ((from_bitField0_ & 0x40000) == 262144) {
                    to_bitField0_ |= 0x40000;
                }
                result.S = this.t;
                if ((from_bitField0_ & 0x80000) == 524288) {
                    to_bitField0_ |= 0x80000;
                }
                result.T = this.u;
                if ((this.a & 0x100000) == 0x100000) {
                    this.v = Collections.unmodifiableList(this.v);
                    this.a &= 0xFFEFFFFF;
                }
                result.U = this.v;
                result.z = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof c) {
                    return this.a((c)other);
                }
                super.a(other);
                return this;
            }

            public a a(c other) {
                if (other == an.g$c.h()) {
                    return this;
                }
                if (other.o()) {
                    this.b(other.p());
                }
                if (other.q()) {
                    this.c(other.r());
                }
                if (other.s()) {
                    this.e(other.t());
                }
                if (other.u()) {
                    this.d(other.v());
                }
                if (other.w()) {
                    this.e(other.x());
                }
                if (other.y()) {
                    this.f(other.z());
                }
                if (other.A()) {
                    this.g(other.B());
                }
                if (other.C()) {
                    this.h(other.D());
                }
                if (other.E()) {
                    this.i(other.F());
                }
                if (other.G()) {
                    this.j(other.H());
                }
                if (other.K()) {
                    this.k(other.L());
                }
                if (other.S()) {
                    this.l(other.T());
                }
                if (other.U()) {
                    this.m(other.V());
                }
                if (other.W()) {
                    this.n(other.X());
                }
                if (other.Y()) {
                    this.o(other.Z());
                }
                if (other.aa()) {
                    this.p(other.ab());
                }
                if (other.ae()) {
                    this.q(other.af());
                }
                if (other.ag()) {
                    this.f(other.I_());
                }
                if (other.J_()) {
                    this.r(other.K_());
                }
                if (other.L_()) {
                    this.s(other.M_());
                }
                if (!other.U.isEmpty()) {
                    if (this.v.isEmpty()) {
                        this.v = other.U;
                        this.a &= 0xFFEFFFFF;
                    } else {
                        this.aO();
                        this.v.addAll(other.U);
                    }
                    this.t_();
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                c parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (c)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a b(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public int r() {
                return this.c;
            }

            public a c(int value) {
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public a.g t() {
                return this.d;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFFB;
                this.d = an.g$c.h().t();
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public int v() {
                return this.e;
            }

            public a d(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFF7;
                this.e = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public int x() {
                return this.f;
            }

            public a e(int value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ao() {
                this.a &= 0xFFFFFFEF;
                this.f = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public int z() {
                return this.g;
            }

            public a f(int value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ap() {
                this.a &= 0xFFFFFFDF;
                this.g = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public int B() {
                return this.h;
            }

            public a g(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a aq() {
                this.a &= 0xFFFFFFBF;
                this.h = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public int D() {
                return this.i;
            }

            public a h(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFFF7F;
                this.i = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public int F() {
                return this.j;
            }

            public a i(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public int H() {
                return this.k;
            }

            public a j(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a at() {
                this.a &= 0xFFFFFDFF;
                this.k = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public int L() {
                return this.l;
            }

            public a k(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a au() {
                this.a &= 0xFFFFFBFF;
                this.l = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.m;
            }

            public a l(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a av() {
                this.a &= 0xFFFFF7FF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.n;
            }

            public a m(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aw() {
                this.a &= 0xFFFFEFFF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.o;
            }

            public a n(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ax() {
                this.a &= 0xFFFFDFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.p;
            }

            public a o(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a ay() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aa() {
                return (this.a & 0x8000) == 32768;
            }

            @Override
            public int ab() {
                return this.q;
            }

            public a p(int value) {
                this.a |= 0x8000;
                this.q = value;
                this.t_();
                return this;
            }

            public a az() {
                this.a &= 0xFFFF7FFF;
                this.q = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ae() {
                return (this.a & 0x10000) == 65536;
            }

            @Override
            public int af() {
                return this.r;
            }

            public a q(int value) {
                this.a |= 0x10000;
                this.r = value;
                this.t_();
                return this;
            }

            public a aG() {
                this.a &= 0xFFFEFFFF;
                this.r = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ag() {
                return (this.a & 0x20000) == 131072;
            }

            @Override
            public a.g I_() {
                return this.s;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x20000;
                this.s = value;
                this.t_();
                return this;
            }

            public a aH() {
                this.a &= 0xFFFDFFFF;
                this.s = an.g$c.h().I_();
                this.t_();
                return this;
            }

            @Override
            public boolean J_() {
                return (this.a & 0x40000) == 262144;
            }

            @Override
            public int K_() {
                return this.t;
            }

            public a r(int value) {
                this.a |= 0x40000;
                this.t = value;
                this.t_();
                return this;
            }

            public a aI() {
                this.a &= 0xFFFBFFFF;
                this.t = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean L_() {
                return (this.a & 0x80000) == 524288;
            }

            @Override
            public int M_() {
                return this.u;
            }

            public a s(int value) {
                this.a |= 0x80000;
                this.u = value;
                this.t_();
                return this;
            }

            public a aJ() {
                this.a &= 0xFFF7FFFF;
                this.u = 0;
                this.t_();
                return this;
            }

            private void aO() {
                if ((this.a & 0x100000) != 0x100000) {
                    this.v = new ArrayList<Integer>(this.v);
                    this.a |= 0x100000;
                }
            }

            @Override
            public List<Integer> N_() {
                return Collections.unmodifiableList(this.v);
            }

            @Override
            public int an() {
                return this.v.size();
            }

            @Override
            public int a(int index) {
                return this.v.get(index);
            }

            public a a(int index, int value) {
                this.aO();
                this.v.set(index, value);
                this.t_();
                return this;
            }

            public a t(int value) {
                this.aO();
                this.v.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends Integer> values) {
                this.aO();
                p.a.a(values, this.v);
                this.t_();
                return this;
            }

            public a aK() {
                this.v = Collections.emptyList();
                this.a &= 0xFFEFFFFF;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }
        }
    }

    public static interface d
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public boolean s();

        public a.g t();

        public boolean u();

        public int v();

        public boolean w();

        public int x();

        public boolean y();

        public int z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();

        public boolean G();

        public int H();

        public boolean K();

        public int L();

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();

        public boolean aa();

        public int ab();

        public boolean ae();

        public int af();

        public boolean ag();

        public a.g I_();

        public boolean J_();

        public int K_();

        public boolean L_();

        public int M_();

        public List<Integer> N_();

        public int an();

        public int a(int var1);
    }

    public static final class e
    extends p
    implements f {
        private static final e r;
        private final ap s;
        public static ab<e> a;
        private int t;
        public static final int b = 1;
        private int u;
        public static final int c = 2;
        private int v;
        public static final int d = 3;
        private int w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private List<Integer> y;
        public static final int g = 6;
        private List<Integer> z;
        public static final int h = 7;
        private List<Integer> A;
        public static final int i = 8;
        private List<Integer> B;
        public static final int j = 9;
        private List<Integer> C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

        static {
            a = new a.c<e>(){

                public e c(a.h input, n extensionRegistry) throws s {
                    return new e(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            r = new e(true);
            r.ag();
        }

        private e(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private e(boolean noInit) {
            this.s = ap.c();
        }

        public static e h() {
            return r;
        }

        public e k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private e(a.h input, n extensionRegistry) throws s {
            this.ag();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block29: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block29;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.t |= 1;
                                this.u = input.g();
                                break;
                            }
                            case 16: {
                                this.t |= 2;
                                this.v = input.g();
                                break;
                            }
                            case 24: {
                                this.t |= 4;
                                this.w = input.g();
                                break;
                            }
                            case 32: {
                                this.t |= 8;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                if ((mutable_bitField0_ & 0x10) != 16) {
                                    this.y = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x10;
                                }
                                this.y.add(input.g());
                                break;
                            }
                            case 42: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 0x10) != 16 && input.x() > 0) {
                                    this.y = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x10;
                                }
                                while (input.x() > 0) {
                                    this.y.add(input.g());
                                }
                                input.g(limit);
                                break;
                            }
                            case 48: {
                                if ((mutable_bitField0_ & 0x20) != 32) {
                                    this.z = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x20;
                                }
                                this.z.add(input.g());
                                break;
                            }
                            case 50: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 0x20) != 32 && input.x() > 0) {
                                    this.z = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x20;
                                }
                                while (input.x() > 0) {
                                    this.z.add(input.g());
                                }
                                input.g(limit);
                                break;
                            }
                            case 56: {
                                if ((mutable_bitField0_ & 0x40) != 64) {
                                    this.A = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x40;
                                }
                                this.A.add(input.g());
                                break;
                            }
                            case 58: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 0x40) != 64 && input.x() > 0) {
                                    this.A = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x40;
                                }
                                while (input.x() > 0) {
                                    this.A.add(input.g());
                                }
                                input.g(limit);
                                break;
                            }
                            case 64: {
                                if ((mutable_bitField0_ & 0x80) != 128) {
                                    this.B = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x80;
                                }
                                this.B.add(input.g());
                                break;
                            }
                            case 66: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 0x80) != 128 && input.x() > 0) {
                                    this.B = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x80;
                                }
                                while (input.x() > 0) {
                                    this.B.add(input.g());
                                }
                                input.g(limit);
                                break;
                            }
                            case 72: {
                                if ((mutable_bitField0_ & 0x100) != 256) {
                                    this.C = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x100;
                                }
                                this.C.add(input.g());
                                break;
                            }
                            case 74: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 0x100) != 256 && input.x() > 0) {
                                    this.C = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x100;
                                }
                                while (input.x() > 0) {
                                    this.C.add(input.g());
                                }
                                input.g(limit);
                                break;
                            }
                            case 80: {
                                this.t |= 0x10;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x20;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x40;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x80;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x100;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x200;
                                this.I = input.g();
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 0x10) == 16) {
                    this.y = Collections.unmodifiableList(this.y);
                }
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.z = Collections.unmodifiableList(this.z);
                }
                if ((mutable_bitField0_ & 0x40) == 64) {
                    this.A = Collections.unmodifiableList(this.A);
                }
                if ((mutable_bitField0_ & 0x80) == 128) {
                    this.B = Collections.unmodifiableList(this.B);
                }
                if ((mutable_bitField0_ & 0x100) == 256) {
                    this.C = Collections.unmodifiableList(this.C);
                }
                this.s = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return e;
        }

        @Override
        protected p.g l() {
            return f.a(e.class, a.class);
        }

        public ab<e> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.t & 1) == 1;
        }

        @Override
        public int p() {
            return this.u;
        }

        @Override
        public boolean q() {
            return (this.t & 2) == 2;
        }

        @Override
        public int r() {
            return this.v;
        }

        @Override
        public boolean s() {
            return (this.t & 4) == 4;
        }

        @Override
        public int t() {
            return this.w;
        }

        @Override
        public boolean u() {
            return (this.t & 8) == 8;
        }

        @Override
        public int v() {
            return this.x;
        }

        @Override
        public List<Integer> w() {
            return this.y;
        }

        @Override
        public int x() {
            return this.y.size();
        }

        @Override
        public int a(int index) {
            return this.y.get(index);
        }

        @Override
        public List<Integer> y() {
            return this.z;
        }

        @Override
        public int z() {
            return this.z.size();
        }

        @Override
        public int b(int index) {
            return this.z.get(index);
        }

        @Override
        public List<Integer> A() {
            return this.A;
        }

        @Override
        public int B() {
            return this.A.size();
        }

        @Override
        public int c(int index) {
            return this.A.get(index);
        }

        @Override
        public List<Integer> C() {
            return this.B;
        }

        @Override
        public int D() {
            return this.B.size();
        }

        @Override
        public int d(int index) {
            return this.B.get(index);
        }

        @Override
        public List<Integer> E() {
            return this.C;
        }

        @Override
        public int F() {
            return this.C.size();
        }

        @Override
        public int e(int index) {
            return this.C.get(index);
        }

        @Override
        public boolean G() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0;
            this.v = 0;
            this.w = 0;
            this.x = 0;
            this.y = Collections.emptyList();
            this.z = Collections.emptyList();
            this.A = Collections.emptyList();
            this.B = Collections.emptyList();
            this.C = Collections.emptyList();
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            if ((this.t & 2) == 2) {
                output.a(2, this.v);
            }
            if ((this.t & 4) == 4) {
                output.a(3, this.w);
            }
            if ((this.t & 8) == 8) {
                output.a(4, this.x);
            }
            int i2 = 0;
            while (i2 < this.y.size()) {
                output.a(5, this.y.get(i2));
                ++i2;
            }
            i2 = 0;
            while (i2 < this.z.size()) {
                output.a(6, this.z.get(i2));
                ++i2;
            }
            i2 = 0;
            while (i2 < this.A.size()) {
                output.a(7, this.A.get(i2));
                ++i2;
            }
            i2 = 0;
            while (i2 < this.B.size()) {
                output.a(8, this.B.get(i2));
                ++i2;
            }
            i2 = 0;
            while (i2 < this.C.size()) {
                output.a(9, this.C.get(i2));
                ++i2;
            }
            if ((this.t & 0x10) == 16) {
                output.a(10, this.D);
            }
            if ((this.t & 0x20) == 32) {
                output.a(11, this.E);
            }
            if ((this.t & 0x40) == 64) {
                output.a(12, this.F);
            }
            if ((this.t & 0x80) == 128) {
                output.a(13, this.G);
            }
            if ((this.t & 0x100) == 256) {
                output.a(14, this.H);
            }
            if ((this.t & 0x200) == 512) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.g(1, this.u);
            }
            if ((this.t & 2) == 2) {
                size += a.i.g(2, this.v);
            }
            if ((this.t & 4) == 4) {
                size += a.i.g(3, this.w);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(4, this.x);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.y.size()) {
                dataSize += a.i.h(this.y.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.w().size();
            dataSize = 0;
            i2 = 0;
            while (i2 < this.z.size()) {
                dataSize += a.i.h(this.z.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.y().size();
            dataSize = 0;
            i2 = 0;
            while (i2 < this.A.size()) {
                dataSize += a.i.h(this.A.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.A().size();
            dataSize = 0;
            i2 = 0;
            while (i2 < this.B.size()) {
                dataSize += a.i.h(this.B.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.C().size();
            dataSize = 0;
            i2 = 0;
            while (i2 < this.C.size()) {
                dataSize += a.i.h(this.C.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.E().size();
            if ((this.t & 0x10) == 16) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static e a(a.g data) throws s {
            return a.d(data);
        }

        public static e a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static e a(byte[] data) throws s {
            return a.d(data);
        }

        public static e a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static e a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static e a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static e b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static e b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static e a(a.h input) throws IOException {
            return a.d(input);
        }

        public static e a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.g$e.aa();
        }

        public static a a(e prototype) {
            return an.g$e.aa().a(prototype);
        }

        public a ae() {
            return an.g$e.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements f {
            private int a;
            private int b;
            private int c;
            private int d;
            private int e;
            private List<Integer> f = Collections.emptyList();
            private List<Integer> g = Collections.emptyList();
            private List<Integer> h = Collections.emptyList();
            private List<Integer> i = Collections.emptyList();
            private List<Integer> j = Collections.emptyList();
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            public static final k.a k() {
                return e;
            }

            @Override
            protected p.g l() {
                return f.a(e.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = 0;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = Collections.emptyList();
                this.a &= 0xFFFFFFEF;
                this.g = Collections.emptyList();
                this.a &= 0xFFFFFFDF;
                this.h = Collections.emptyList();
                this.a &= 0xFFFFFFBF;
                this.i = Collections.emptyList();
                this.a &= 0xFFFFFF7F;
                this.j = Collections.emptyList();
                this.a &= 0xFFFFFEFF;
                this.k = 0;
                this.a &= 0xFFFFFDFF;
                this.l = 0;
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                return this;
            }

            public a n() {
                return an.g$e$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return e;
            }

            public e I() {
                return an.g$e.h();
            }

            public e M() {
                e result = this.N();
                if (!result.a()) {
                    throw an.g$e$a.b(result);
                }
                return result;
            }

            public e N() {
                e result = new e(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.u = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.v = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.w = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.x = this.e;
                if ((this.a & 0x10) == 16) {
                    this.f = Collections.unmodifiableList(this.f);
                    this.a &= 0xFFFFFFEF;
                }
                result.y = this.f;
                if ((this.a & 0x20) == 32) {
                    this.g = Collections.unmodifiableList(this.g);
                    this.a &= 0xFFFFFFDF;
                }
                result.z = this.g;
                if ((this.a & 0x40) == 64) {
                    this.h = Collections.unmodifiableList(this.h);
                    this.a &= 0xFFFFFFBF;
                }
                result.A = this.h;
                if ((this.a & 0x80) == 128) {
                    this.i = Collections.unmodifiableList(this.i);
                    this.a &= 0xFFFFFF7F;
                }
                result.B = this.i;
                if ((this.a & 0x100) == 256) {
                    this.j = Collections.unmodifiableList(this.j);
                    this.a &= 0xFFFFFEFF;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x10;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x20;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x40;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x80;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x100;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x200;
                }
                result.I = this.p;
                result.t = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof e) {
                    return this.a((e)other);
                }
                super.a(other);
                return this;
            }

            public a a(e other) {
                if (other == an.g$e.h()) {
                    return this;
                }
                if (other.o()) {
                    this.f(other.p());
                }
                if (other.q()) {
                    this.g(other.r());
                }
                if (other.s()) {
                    this.h(other.t());
                }
                if (other.u()) {
                    this.i(other.v());
                }
                if (!other.y.isEmpty()) {
                    if (this.f.isEmpty()) {
                        this.f = other.y;
                        this.a &= 0xFFFFFFEF;
                    } else {
                        this.aw();
                        this.f.addAll(other.y);
                    }
                    this.t_();
                }
                if (!other.z.isEmpty()) {
                    if (this.g.isEmpty()) {
                        this.g = other.z;
                        this.a &= 0xFFFFFFDF;
                    } else {
                        this.ax();
                        this.g.addAll(other.z);
                    }
                    this.t_();
                }
                if (!other.A.isEmpty()) {
                    if (this.h.isEmpty()) {
                        this.h = other.A;
                        this.a &= 0xFFFFFFBF;
                    } else {
                        this.ay();
                        this.h.addAll(other.A);
                    }
                    this.t_();
                }
                if (!other.B.isEmpty()) {
                    if (this.i.isEmpty()) {
                        this.i = other.B;
                        this.a &= 0xFFFFFF7F;
                    } else {
                        this.az();
                        this.i.addAll(other.B);
                    }
                    this.t_();
                }
                if (!other.C.isEmpty()) {
                    if (this.j.isEmpty()) {
                        this.j = other.C;
                        this.a &= 0xFFFFFEFF;
                    } else {
                        this.aG();
                        this.j.addAll(other.C);
                    }
                    this.t_();
                }
                if (other.G()) {
                    this.o(other.H());
                }
                if (other.K()) {
                    this.p(other.L());
                }
                if (other.S()) {
                    this.q(other.T());
                }
                if (other.U()) {
                    this.r(other.V());
                }
                if (other.W()) {
                    this.s(other.X());
                }
                if (other.Y()) {
                    this.t(other.Z());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                e parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (e)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a f(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public int r() {
                return this.c;
            }

            public a g(int value) {
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public int t() {
                return this.d;
            }

            public a h(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
                this.a &= 0xFFFFFFFB;
                this.d = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public int v() {
                return this.e;
            }

            public a i(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ab() {
                this.a &= 0xFFFFFFF7;
                this.e = 0;
                this.t_();
                return this;
            }

            private void aw() {
                if ((this.a & 0x10) != 16) {
                    this.f = new ArrayList<Integer>(this.f);
                    this.a |= 0x10;
                }
            }

            @Override
            public List<Integer> w() {
                return Collections.unmodifiableList(this.f);
            }

            @Override
            public int x() {
                return this.f.size();
            }

            @Override
            public int a(int index) {
                return this.f.get(index);
            }

            public a a(int index, int value) {
                this.aw();
                this.f.set(index, value);
                this.t_();
                return this;
            }

            public a j(int value) {
                this.aw();
                this.f.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends Integer> values) {
                this.aw();
                p.a.a(values, this.f);
                this.t_();
                return this;
            }

            public a ac() {
                this.f = Collections.emptyList();
                this.a &= 0xFFFFFFEF;
                this.t_();
                return this;
            }

            private void ax() {
                if ((this.a & 0x20) != 32) {
                    this.g = new ArrayList<Integer>(this.g);
                    this.a |= 0x20;
                }
            }

            @Override
            public List<Integer> y() {
                return Collections.unmodifiableList(this.g);
            }

            @Override
            public int z() {
                return this.g.size();
            }

            @Override
            public int b(int index) {
                return this.g.get(index);
            }

            public a b(int index, int value) {
                this.ax();
                this.g.set(index, value);
                this.t_();
                return this;
            }

            public a k(int value) {
                this.ax();
                this.g.add(value);
                this.t_();
                return this;
            }

            public a b(Iterable<? extends Integer> values) {
                this.ax();
                p.a.a(values, this.g);
                this.t_();
                return this;
            }

            public a ad() {
                this.g = Collections.emptyList();
                this.a &= 0xFFFFFFDF;
                this.t_();
                return this;
            }

            private void ay() {
                if ((this.a & 0x40) != 64) {
                    this.h = new ArrayList<Integer>(this.h);
                    this.a |= 0x40;
                }
            }

            @Override
            public List<Integer> A() {
                return Collections.unmodifiableList(this.h);
            }

            @Override
            public int B() {
                return this.h.size();
            }

            @Override
            public int c(int index) {
                return this.h.get(index);
            }

            public a c(int index, int value) {
                this.ay();
                this.h.set(index, value);
                this.t_();
                return this;
            }

            public a l(int value) {
                this.ay();
                this.h.add(value);
                this.t_();
                return this;
            }

            public a c(Iterable<? extends Integer> values) {
                this.ay();
                p.a.a(values, this.h);
                this.t_();
                return this;
            }

            public a ae() {
                this.h = Collections.emptyList();
                this.a &= 0xFFFFFFBF;
                this.t_();
                return this;
            }

            private void az() {
                if ((this.a & 0x80) != 128) {
                    this.i = new ArrayList<Integer>(this.i);
                    this.a |= 0x80;
                }
            }

            @Override
            public List<Integer> C() {
                return Collections.unmodifiableList(this.i);
            }

            @Override
            public int D() {
                return this.i.size();
            }

            @Override
            public int d(int index) {
                return this.i.get(index);
            }

            public a d(int index, int value) {
                this.az();
                this.i.set(index, value);
                this.t_();
                return this;
            }

            public a m(int value) {
                this.az();
                this.i.add(value);
                this.t_();
                return this;
            }

            public a d(Iterable<? extends Integer> values) {
                this.az();
                p.a.a(values, this.i);
                this.t_();
                return this;
            }

            public a af() {
                this.i = Collections.emptyList();
                this.a &= 0xFFFFFF7F;
                this.t_();
                return this;
            }

            private void aG() {
                if ((this.a & 0x100) != 256) {
                    this.j = new ArrayList<Integer>(this.j);
                    this.a |= 0x100;
                }
            }

            @Override
            public List<Integer> E() {
                return Collections.unmodifiableList(this.j);
            }

            @Override
            public int F() {
                return this.j.size();
            }

            @Override
            public int e(int index) {
                return this.j.get(index);
            }

            public a e(int index, int value) {
                this.aG();
                this.j.set(index, value);
                this.t_();
                return this;
            }

            public a n(int value) {
                this.aG();
                this.j.add(value);
                this.t_();
                return this;
            }

            public a e(Iterable<? extends Integer> values) {
                this.aG();
                p.a.a(values, this.j);
                this.t_();
                return this;
            }

            public a ag() {
                this.j = Collections.emptyList();
                this.a &= 0xFFFFFEFF;
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public int H() {
                return this.k;
            }

            public a o(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a an() {
                this.a &= 0xFFFFFDFF;
                this.k = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public int L() {
                return this.l;
            }

            public a p(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a ao() {
                this.a &= 0xFFFFFBFF;
                this.l = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.m;
            }

            public a q(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ap() {
                this.a &= 0xFFFFF7FF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.n;
            }

            public a r(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aq() {
                this.a &= 0xFFFFEFFF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.o;
            }

            public a s(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFDFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.p;
            }

            public a t(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }
        }
    }

    public static interface f
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public boolean s();

        public int t();

        public boolean u();

        public int v();

        public List<Integer> w();

        public int x();

        public int a(int var1);

        public List<Integer> y();

        public int z();

        public int b(int var1);

        public List<Integer> A();

        public int B();

        public int c(int var1);

        public List<Integer> C();

        public int D();

        public int d(int var1);

        public List<Integer> E();

        public int F();

        public int e(int var1);

        public boolean G();

        public int H();

        public boolean K();

        public int L();

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();
    }

    public static final class g
    extends p
    implements h {
        private static final g r;
        private final ap s;
        public static ab<g> a;
        private int t;
        public static final int b = 1;
        private int u;
        public static final int c = 2;
        private List<a.g> v;
        public static final int d = 3;
        private List<a.g> w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private int y;
        public static final int g = 6;
        private int z;
        public static final int h = 7;
        private int A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private int C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

        static {
            a = new a.c<g>(){

                public g c(a.h input, n extensionRegistry) throws s {
                    return new g(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            r = new g(true);
            r.ag();
        }

        private g(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private g(boolean noInit) {
            this.s = ap.c();
        }

        public static g h() {
            return r;
        }

        public g k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private g(a.h input, n extensionRegistry) throws s {
            this.ag();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block24: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block24;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.t |= 1;
                                this.u = input.g();
                                break;
                            }
                            case 18: {
                                if ((mutable_bitField0_ & 2) != 2) {
                                    this.v = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 2;
                                }
                                this.v.add(input.l());
                                break;
                            }
                            case 26: {
                                if ((mutable_bitField0_ & 4) != 4) {
                                    this.w = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 4;
                                }
                                this.w.add(input.l());
                                break;
                            }
                            case 32: {
                                this.t |= 2;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                this.t |= 4;
                                this.y = input.g();
                                break;
                            }
                            case 48: {
                                this.t |= 8;
                                this.z = input.g();
                                break;
                            }
                            case 56: {
                                this.t |= 0x10;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x20;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x40;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x80;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x100;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x200;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x400;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x800;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x1000;
                                this.I = input.g();
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.v = Collections.unmodifiableList(this.v);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.w = Collections.unmodifiableList(this.w);
                }
                this.s = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return g;
        }

        @Override
        protected p.g l() {
            return h.a(g.class, a.class);
        }

        public ab<g> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.t & 1) == 1;
        }

        @Override
        public int p() {
            return this.u;
        }

        @Override
        public List<a.g> q() {
            return this.v;
        }

        @Override
        public int r() {
            return this.v.size();
        }

        @Override
        public a.g a(int index) {
            return this.v.get(index);
        }

        @Override
        public List<a.g> s() {
            return this.w;
        }

        @Override
        public int t() {
            return this.w.size();
        }

        @Override
        public a.g b(int index) {
            return this.w.get(index);
        }

        @Override
        public boolean u() {
            return (this.t & 2) == 2;
        }

        @Override
        public int v() {
            return this.x;
        }

        @Override
        public boolean w() {
            return (this.t & 4) == 4;
        }

        @Override
        public int x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 8) == 8;
        }

        @Override
        public int z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0;
            this.v = Collections.emptyList();
            this.w = Collections.emptyList();
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            int i2 = 0;
            while (i2 < this.v.size()) {
                output.a(2, this.v.get(i2));
                ++i2;
            }
            i2 = 0;
            while (i2 < this.w.size()) {
                output.a(3, this.w.get(i2));
                ++i2;
            }
            if ((this.t & 2) == 2) {
                output.a(4, this.x);
            }
            if ((this.t & 4) == 4) {
                output.a(5, this.y);
            }
            if ((this.t & 8) == 8) {
                output.a(6, this.z);
            }
            if ((this.t & 0x10) == 16) {
                output.a(7, this.A);
            }
            if ((this.t & 0x20) == 32) {
                output.a(8, this.B);
            }
            if ((this.t & 0x40) == 64) {
                output.a(9, this.C);
            }
            if ((this.t & 0x80) == 128) {
                output.a(10, this.D);
            }
            if ((this.t & 0x100) == 256) {
                output.a(11, this.E);
            }
            if ((this.t & 0x200) == 512) {
                output.a(12, this.F);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(13, this.G);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(14, this.H);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.g(1, this.u);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.v.size()) {
                dataSize += a.i.b(this.v.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.q().size();
            dataSize = 0;
            i2 = 0;
            while (i2 < this.w.size()) {
                dataSize += a.i.b(this.w.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.s().size();
            if ((this.t & 2) == 2) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 4) == 4) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static g a(a.g data) throws s {
            return a.d(data);
        }

        public static g a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static g a(byte[] data) throws s {
            return a.d(data);
        }

        public static g a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static g a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static g a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static g b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static g b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static g a(a.h input) throws IOException {
            return a.d(input);
        }

        public static g a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.g$g.aa();
        }

        public static a a(g prototype) {
            return an.g$g.aa().a(prototype);
        }

        public a ae() {
            return an.g$g.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements h {
            private int a;
            private int b;
            private List<a.g> c = Collections.emptyList();
            private List<a.g> d = Collections.emptyList();
            private int e;
            private int f;
            private int g;
            private int h;
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            public static final k.a k() {
                return g;
            }

            @Override
            protected p.g l() {
                return h.a(g.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = Collections.emptyList();
                this.a &= 0xFFFFFFFD;
                this.d = Collections.emptyList();
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = 0;
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                this.k = 0;
                this.a &= 0xFFFFFDFF;
                this.l = 0;
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                return this;
            }

            public a n() {
                return an.g$g$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return g;
            }

            public g I() {
                return an.g$g.h();
            }

            public g M() {
                g result = this.N();
                if (!result.a()) {
                    throw an.g$g$a.b(result);
                }
                return result;
            }

            public g N() {
                g result = new g(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.u = this.b;
                if ((this.a & 2) == 2) {
                    this.c = Collections.unmodifiableList(this.c);
                    this.a &= 0xFFFFFFFD;
                }
                result.v = this.c;
                if ((this.a & 4) == 4) {
                    this.d = Collections.unmodifiableList(this.d);
                    this.a &= 0xFFFFFFFB;
                }
                result.w = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 2;
                }
                result.x = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 4;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 8;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x10;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x20;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x40;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x80;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x100;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x200;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x400;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x800;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x1000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof g) {
                    return this.a((g)other);
                }
                super.a(other);
                return this;
            }

            public a a(g other) {
                if (other == an.g$g.h()) {
                    return this;
                }
                if (other.o()) {
                    this.c(other.p());
                }
                if (!other.v.isEmpty()) {
                    if (this.c.isEmpty()) {
                        this.c = other.v;
                        this.a &= 0xFFFFFFFD;
                    } else {
                        this.aw();
                        this.c.addAll(other.v);
                    }
                    this.t_();
                }
                if (!other.w.isEmpty()) {
                    if (this.d.isEmpty()) {
                        this.d = other.w;
                        this.a &= 0xFFFFFFFB;
                    } else {
                        this.ax();
                        this.d.addAll(other.w);
                    }
                    this.t_();
                }
                if (other.u()) {
                    this.d(other.v());
                }
                if (other.w()) {
                    this.e(other.x());
                }
                if (other.y()) {
                    this.f(other.z());
                }
                if (other.A()) {
                    this.g(other.B());
                }
                if (other.C()) {
                    this.h(other.D());
                }
                if (other.E()) {
                    this.i(other.F());
                }
                if (other.G()) {
                    this.j(other.H());
                }
                if (other.K()) {
                    this.k(other.L());
                }
                if (other.S()) {
                    this.l(other.T());
                }
                if (other.U()) {
                    this.m(other.V());
                }
                if (other.W()) {
                    this.n(other.X());
                }
                if (other.Y()) {
                    this.o(other.Z());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                g parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (g)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a c(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            private void aw() {
                if ((this.a & 2) != 2) {
                    this.c = new ArrayList<a.g>(this.c);
                    this.a |= 2;
                }
            }

            @Override
            public List<a.g> q() {
                return Collections.unmodifiableList(this.c);
            }

            @Override
            public int r() {
                return this.c.size();
            }

            @Override
            public a.g a(int index) {
                return this.c.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.c.set(index, value);
                this.t_();
                return this;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.c.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends a.g> values) {
                this.aw();
                p.a.a(values, this.c);
                this.t_();
                return this;
            }

            public a P() {
                this.c = Collections.emptyList();
                this.a &= 0xFFFFFFFD;
                this.t_();
                return this;
            }

            private void ax() {
                if ((this.a & 4) != 4) {
                    this.d = new ArrayList<a.g>(this.d);
                    this.a |= 4;
                }
            }

            @Override
            public List<a.g> s() {
                return Collections.unmodifiableList(this.d);
            }

            @Override
            public int t() {
                return this.d.size();
            }

            @Override
            public a.g b(int index) {
                return this.d.get(index);
            }

            public a b(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ax();
                this.d.set(index, value);
                this.t_();
                return this;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ax();
                this.d.add(value);
                this.t_();
                return this;
            }

            public a b(Iterable<? extends a.g> values) {
                this.ax();
                p.a.a(values, this.d);
                this.t_();
                return this;
            }

            public a aa() {
                this.d = Collections.emptyList();
                this.a &= 0xFFFFFFFB;
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public int v() {
                return this.e;
            }

            public a d(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ab() {
                this.a &= 0xFFFFFFF7;
                this.e = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public int x() {
                return this.f;
            }

            public a e(int value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFEF;
                this.f = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public int z() {
                return this.g;
            }

            public a f(int value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFDF;
                this.g = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public int B() {
                return this.h;
            }

            public a g(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a ae() {
                this.a &= 0xFFFFFFBF;
                this.h = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public int D() {
                return this.i;
            }

            public a h(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a af() {
                this.a &= 0xFFFFFF7F;
                this.i = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public int F() {
                return this.j;
            }

            public a i(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ag() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public int H() {
                return this.k;
            }

            public a j(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a an() {
                this.a &= 0xFFFFFDFF;
                this.k = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public int L() {
                return this.l;
            }

            public a k(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a ao() {
                this.a &= 0xFFFFFBFF;
                this.l = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.m;
            }

            public a l(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ap() {
                this.a &= 0xFFFFF7FF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.n;
            }

            public a m(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aq() {
                this.a &= 0xFFFFEFFF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.o;
            }

            public a n(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFDFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.p;
            }

            public a o(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }
        }
    }

    public static interface h
    extends aa {
        public boolean o();

        public int p();

        public List<a.g> q();

        public int r();

        public a.g a(int var1);

        public List<a.g> s();

        public int t();

        public a.g b(int var1);

        public boolean u();

        public int v();

        public boolean w();

        public int x();

        public boolean y();

        public int z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();

        public boolean G();

        public int H();

        public boolean K();

        public int L();

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();
    }

    public static final class i
    extends p
    implements j {
        private static final i r;
        private final ap s;
        public static ab<i> a;
        private int t;
        public static final int b = 1;
        private a.g u;
        public static final int c = 2;
        private List<a.g> v;
        public static final int d = 3;
        private int w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private int y;
        public static final int g = 6;
        private int z;
        public static final int h = 7;
        private int A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private int C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

        static {
            a = new a.c<i>(){

                public i c(a.h input, n extensionRegistry) throws s {
                    return new i(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            r = new i(true);
            r.ag();
        }

        private i(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private i(boolean noInit) {
            this.s = ap.c();
        }

        public static i h() {
            return r;
        }

        public i k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private i(a.h input, n extensionRegistry) throws s {
            this.ag();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block24: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block24;
                                done = true;
                                break;
                            }
                            case 10: {
                                this.t |= 1;
                                this.u = input.l();
                                break;
                            }
                            case 18: {
                                if ((mutable_bitField0_ & 2) != 2) {
                                    this.v = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 2;
                                }
                                this.v.add(input.l());
                                break;
                            }
                            case 24: {
                                this.t |= 2;
                                this.w = input.g();
                                break;
                            }
                            case 32: {
                                this.t |= 4;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                this.t |= 8;
                                this.y = input.g();
                                break;
                            }
                            case 48: {
                                this.t |= 0x10;
                                this.z = input.g();
                                break;
                            }
                            case 56: {
                                this.t |= 0x20;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x40;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x80;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x100;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x200;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x400;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x800;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x1000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x2000;
                                this.I = input.g();
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.v = Collections.unmodifiableList(this.v);
                }
                this.s = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return i;
        }

        @Override
        protected p.g l() {
            return j.a(i.class, a.class);
        }

        public ab<i> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.t & 1) == 1;
        }

        @Override
        public a.g p() {
            return this.u;
        }

        @Override
        public List<a.g> q() {
            return this.v;
        }

        @Override
        public int r() {
            return this.v.size();
        }

        @Override
        public a.g a(int index) {
            return this.v.get(index);
        }

        @Override
        public boolean s() {
            return (this.t & 2) == 2;
        }

        @Override
        public int t() {
            return this.w;
        }

        @Override
        public boolean u() {
            return (this.t & 4) == 4;
        }

        @Override
        public int v() {
            return this.x;
        }

        @Override
        public boolean w() {
            return (this.t & 8) == 8;
        }

        @Override
        public int x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = a.g.d;
            this.v = Collections.emptyList();
            this.w = 0;
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            int i2 = 0;
            while (i2 < this.v.size()) {
                output.a(2, this.v.get(i2));
                ++i2;
            }
            if ((this.t & 2) == 2) {
                output.a(3, this.w);
            }
            if ((this.t & 4) == 4) {
                output.a(4, this.x);
            }
            if ((this.t & 8) == 8) {
                output.a(5, this.y);
            }
            if ((this.t & 0x10) == 16) {
                output.a(6, this.z);
            }
            if ((this.t & 0x20) == 32) {
                output.a(7, this.A);
            }
            if ((this.t & 0x40) == 64) {
                output.a(8, this.B);
            }
            if ((this.t & 0x80) == 128) {
                output.a(9, this.C);
            }
            if ((this.t & 0x100) == 256) {
                output.a(10, this.D);
            }
            if ((this.t & 0x200) == 512) {
                output.a(11, this.E);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(12, this.F);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(13, this.G);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(14, this.H);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.c(1, this.u);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.v.size()) {
                dataSize += a.i.b(this.v.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.q().size();
            if ((this.t & 2) == 2) {
                size += a.i.g(3, this.w);
            }
            if ((this.t & 4) == 4) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static i a(a.g data) throws s {
            return a.d(data);
        }

        public static i a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static i a(byte[] data) throws s {
            return a.d(data);
        }

        public static i a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static i a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static i a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static i b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static i b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static i a(a.h input) throws IOException {
            return a.d(input);
        }

        public static i a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.g$i.aa();
        }

        public static a a(i prototype) {
            return an.g$i.aa().a(prototype);
        }

        public a ae() {
            return an.g$i.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements j {
            private int a;
            private a.g b = a.g.d;
            private List<a.g> c = Collections.emptyList();
            private int d;
            private int e;
            private int f;
            private int g;
            private int h;
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            public static final k.a k() {
                return i;
            }

            @Override
            protected p.g l() {
                return j.a(i.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = a.g.d;
                this.a &= 0xFFFFFFFE;
                this.c = Collections.emptyList();
                this.a &= 0xFFFFFFFD;
                this.d = 0;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = 0;
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                this.k = 0;
                this.a &= 0xFFFFFDFF;
                this.l = 0;
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                return this;
            }

            public a n() {
                return an.g$i$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return i;
            }

            public i I() {
                return an.g$i.h();
            }

            public i M() {
                i result = this.N();
                if (!result.a()) {
                    throw an.g$i$a.b(result);
                }
                return result;
            }

            public i N() {
                i result = new i(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.u = this.b;
                if ((this.a & 2) == 2) {
                    this.c = Collections.unmodifiableList(this.c);
                    this.a &= 0xFFFFFFFD;
                }
                result.v = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result.w = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result.x = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 8;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x10;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x80;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x100;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x200;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x400;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x800;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x1000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x2000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof i) {
                    return this.a((i)other);
                }
                super.a(other);
                return this;
            }

            public a a(i other) {
                if (other == an.g$i.h()) {
                    return this;
                }
                if (other.o()) {
                    this.e(other.p());
                }
                if (!other.v.isEmpty()) {
                    if (this.c.isEmpty()) {
                        this.c = other.v;
                        this.a &= 0xFFFFFFFD;
                    } else {
                        this.aw();
                        this.c.addAll(other.v);
                    }
                    this.t_();
                }
                if (other.s()) {
                    this.b(other.t());
                }
                if (other.u()) {
                    this.c(other.v());
                }
                if (other.w()) {
                    this.d(other.x());
                }
                if (other.y()) {
                    this.e(other.z());
                }
                if (other.A()) {
                    this.f(other.B());
                }
                if (other.C()) {
                    this.g(other.D());
                }
                if (other.E()) {
                    this.h(other.F());
                }
                if (other.G()) {
                    this.i(other.H());
                }
                if (other.K()) {
                    this.j(other.L());
                }
                if (other.S()) {
                    this.k(other.T());
                }
                if (other.U()) {
                    this.l(other.V());
                }
                if (other.W()) {
                    this.m(other.X());
                }
                if (other.Y()) {
                    this.n(other.Z());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                i parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (i)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public a.g p() {
                return this.b;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = an.g$i.h().p();
                this.t_();
                return this;
            }

            private void aw() {
                if ((this.a & 2) != 2) {
                    this.c = new ArrayList<a.g>(this.c);
                    this.a |= 2;
                }
            }

            @Override
            public List<a.g> q() {
                return Collections.unmodifiableList(this.c);
            }

            @Override
            public int r() {
                return this.c.size();
            }

            @Override
            public a.g a(int index) {
                return this.c.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.c.set(index, value);
                this.t_();
                return this;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.c.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends a.g> values) {
                this.aw();
                p.a.a(values, this.c);
                this.t_();
                return this;
            }

            public a P() {
                this.c = Collections.emptyList();
                this.a &= 0xFFFFFFFD;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public int t() {
                return this.d;
            }

            public a b(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
                this.a &= 0xFFFFFFFB;
                this.d = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public int v() {
                return this.e;
            }

            public a c(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ab() {
                this.a &= 0xFFFFFFF7;
                this.e = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public int x() {
                return this.f;
            }

            public a d(int value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFEF;
                this.f = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public int z() {
                return this.g;
            }

            public a e(int value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFDF;
                this.g = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public int B() {
                return this.h;
            }

            public a f(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a ae() {
                this.a &= 0xFFFFFFBF;
                this.h = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public int D() {
                return this.i;
            }

            public a g(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a af() {
                this.a &= 0xFFFFFF7F;
                this.i = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public int F() {
                return this.j;
            }

            public a h(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ag() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public int H() {
                return this.k;
            }

            public a i(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a an() {
                this.a &= 0xFFFFFDFF;
                this.k = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public int L() {
                return this.l;
            }

            public a j(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a ao() {
                this.a &= 0xFFFFFBFF;
                this.l = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.m;
            }

            public a k(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ap() {
                this.a &= 0xFFFFF7FF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.n;
            }

            public a l(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aq() {
                this.a &= 0xFFFFEFFF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.o;
            }

            public a m(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFDFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.p;
            }

            public a n(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }
        }
    }

    public static interface j
    extends aa {
        public boolean o();

        public a.g p();

        public List<a.g> q();

        public int r();

        public a.g a(int var1);

        public boolean s();

        public int t();

        public boolean u();

        public int v();

        public boolean w();

        public int x();

        public boolean y();

        public int z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();

        public boolean G();

        public int H();

        public boolean K();

        public int L();

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();
    }
}

