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

public final class d {
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
        String[] descriptorData = new String[]{"\n\u0013PBMessageALL4.proto\u0012 l1j.server.server.datas.protobuf\"\u008d\u0002\n\u0006type11\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0003\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0003\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0003\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0003\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0003\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0003\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0003\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0003\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0003\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0003\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0003\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0003\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0003\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0003\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0003\"\u008d\u0002\n\u0006type12\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4", "\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0003\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0003\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0003\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type13\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012", "\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type14\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007array_9\u0018\t \u0001(\f\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type15\u0012\u000f\n\u0007array_1\u0018\u0001 \u0003(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0003(\f\u0012\u000f\n\u0007value_3\u0018", "\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL4"};
        k.g.a assigner = new k.g.a(){

            @Override
            public m a(k.g root) {
                k = root;
                a = an.d.a().e().get(0);
                b = new p.g(a, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                c = an.d.a().e().get(1);
                d = new p.g(c, new String[]{"Value1", "Array2", "Array3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                e = an.d.a().e().get(2);
                f = new p.g(e, new String[]{"Value1", "Value2", "Value3", "Value4", "Array5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                g = an.d.a().e().get(3);
                h = new p.g(g, new String[]{"Value1", "Value2", "Array3", "Value4", "Value5", "Array6", "Value7", "Value8", "Array9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                i = an.d.a().e().get(4);
                j = new p.g(i, new String[]{"Array1", "Array2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                return null;
            }
        };
        k.g.a(descriptorData, new k.g[0], assigner);
    }

    private d() {
    }

    public static void a(m registry) {
    }

    public static k.g a() {
        return k;
    }

    public static final class an.d$a
    extends p
    implements b {
        private static final an.d$a r;
        private final ap s;
        public static ab<an.d$a> a;
        private int t;
        public static final int b = 1;
        private long u;
        public static final int c = 2;
        private long v;
        public static final int d = 3;
        private long w;
        public static final int e = 4;
        private long x;
        public static final int f = 5;
        private long y;
        public static final int g = 6;
        private long z;
        public static final int h = 7;
        private long A;
        public static final int i = 8;
        private long B;
        public static final int j = 9;
        private long C;
        public static final int k = 10;
        private long D;
        public static final int l = 11;
        private long E;
        public static final int n = 12;
        private long F;
        public static final int o = 13;
        private long G;
        public static final int p = 14;
        private long H;
        public static final int q = 15;
        private long I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

        static {
            a = new a.c<an.d$a>(){

                public an.d$a c(a.h input, n extensionRegistry) throws s {
                    return new an.d$a(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            r = new an.d$a(true);
            r.ag();
        }

        private an.d$a(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private an.d$a(boolean noInit) {
            this.s = ap.c();
        }

        public static an.d$a h() {
            return r;
        }

        public an.d$a k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private an.d$a(a.h input, n extensionRegistry) throws s {
            this.ag();
            boolean mutable_bitField0_ = false;
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
                                this.u = input.f();
                                break;
                            }
                            case 16: {
                                this.t |= 2;
                                this.v = input.f();
                                break;
                            }
                            case 24: {
                                this.t |= 4;
                                this.w = input.f();
                                break;
                            }
                            case 32: {
                                this.t |= 8;
                                this.x = input.f();
                                break;
                            }
                            case 40: {
                                this.t |= 0x10;
                                this.y = input.f();
                                break;
                            }
                            case 48: {
                                this.t |= 0x20;
                                this.z = input.f();
                                break;
                            }
                            case 56: {
                                this.t |= 0x40;
                                this.A = input.f();
                                break;
                            }
                            case 64: {
                                this.t |= 0x80;
                                this.B = input.f();
                                break;
                            }
                            case 72: {
                                this.t |= 0x100;
                                this.C = input.f();
                                break;
                            }
                            case 80: {
                                this.t |= 0x200;
                                this.D = input.f();
                                break;
                            }
                            case 88: {
                                this.t |= 0x400;
                                this.E = input.f();
                                break;
                            }
                            case 96: {
                                this.t |= 0x800;
                                this.F = input.f();
                                break;
                            }
                            case 104: {
                                this.t |= 0x1000;
                                this.G = input.f();
                                break;
                            }
                            case 112: {
                                this.t |= 0x2000;
                                this.H = input.f();
                                break;
                            }
                            case 120: {
                                this.t |= 0x4000;
                                this.I = input.f();
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
                this.s = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return a;
        }

        @Override
        protected p.g l() {
            return b.a(an.d$a.class, a.class);
        }

        public ab<an.d$a> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.t & 1) == 1;
        }

        @Override
        public long p() {
            return this.u;
        }

        @Override
        public boolean q() {
            return (this.t & 2) == 2;
        }

        @Override
        public long r() {
            return this.v;
        }

        @Override
        public boolean s() {
            return (this.t & 4) == 4;
        }

        @Override
        public long t() {
            return this.w;
        }

        @Override
        public boolean u() {
            return (this.t & 8) == 8;
        }

        @Override
        public long v() {
            return this.x;
        }

        @Override
        public boolean w() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public long x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public long z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public long B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public long D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public long F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public long H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public long L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public long T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public long V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public long X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x4000) == 16384;
        }

        @Override
        public long Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0L;
            this.v = 0L;
            this.w = 0L;
            this.x = 0L;
            this.y = 0L;
            this.z = 0L;
            this.A = 0L;
            this.B = 0L;
            this.C = 0L;
            this.D = 0L;
            this.E = 0L;
            this.F = 0L;
            this.G = 0L;
            this.H = 0L;
            this.I = 0L;
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
                output.b(1, this.u);
            }
            if ((this.t & 2) == 2) {
                output.b(2, this.v);
            }
            if ((this.t & 4) == 4) {
                output.b(3, this.w);
            }
            if ((this.t & 8) == 8) {
                output.b(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                output.b(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                output.b(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                output.b(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                output.b(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                output.b(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                output.b(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                output.b(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                output.b(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                output.b(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                output.b(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                output.b(15, this.I);
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
            if ((this.t & 0x10) == 16) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static an.d$a a(a.g data) throws s {
            return a.d(data);
        }

        public static an.d$a a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.d$a a(byte[] data) throws s {
            return a.d(data);
        }

        public static an.d$a a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.d$a a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static an.d$a a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static an.d$a b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static an.d$a b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static an.d$a a(a.h input) throws IOException {
            return a.d(input);
        }

        public static an.d$a a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.d$a.aa();
        }

        public static a a(an.d$a prototype) {
            return an.d$a.aa().a(prototype);
        }

        public a ae() {
            return an.d$a.a(this);
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
            private long b;
            private long c;
            private long d;
            private long e;
            private long f;
            private long g;
            private long h;
            private long i;
            private long j;
            private long k;
            private long l;
            private long m;
            private long n;
            private long o;
            private long p;

            public static final k.a k() {
                return a;
            }

            @Override
            protected p.g l() {
                return b.a(an.d$a.class, a.class);
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
                this.b = 0L;
                this.a &= 0xFFFFFFFE;
                this.c = 0L;
                this.a &= 0xFFFFFFFD;
                this.d = 0L;
                this.a &= 0xFFFFFFFB;
                this.e = 0L;
                this.a &= 0xFFFFFFF7;
                this.f = 0L;
                this.a &= 0xFFFFFFEF;
                this.g = 0L;
                this.a &= 0xFFFFFFDF;
                this.h = 0L;
                this.a &= 0xFFFFFFBF;
                this.i = 0L;
                this.a &= 0xFFFFFF7F;
                this.j = 0L;
                this.a &= 0xFFFFFEFF;
                this.k = 0L;
                this.a &= 0xFFFFFDFF;
                this.l = 0L;
                this.a &= 0xFFFFFBFF;
                this.m = 0L;
                this.a &= 0xFFFFF7FF;
                this.n = 0L;
                this.a &= 0xFFFFEFFF;
                this.o = 0L;
                this.a &= 0xFFFFDFFF;
                this.p = 0L;
                this.a &= 0xFFFFBFFF;
                return this;
            }

            public a n() {
                return an.d$a$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return a;
            }

            public an.d$a I() {
                return an.d$a.h();
            }

            public an.d$a M() {
                an.d$a result = this.N();
                if (!result.a()) {
                    throw an.d$a$a.b(result);
                }
                return result;
            }

            public an.d$a N() {
                an.d$a result = new an.d$a(this);
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
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof an.d$a) {
                    return this.a((an.d$a)other);
                }
                super.a(other);
                return this;
            }

            public a a(an.d$a other) {
                if (other == an.d$a.h()) {
                    return this;
                }
                if (other.o()) {
                    this.a(other.p());
                }
                if (other.q()) {
                    this.b(other.r());
                }
                if (other.s()) {
                    this.c(other.t());
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
                an.d$a parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (an.d$a)e2.a();
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
            public long p() {
                return this.b;
            }

            public a a(long value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public long r() {
                return this.c;
            }

            public a b(long value) {
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public long t() {
                return this.d;
            }

            public a c(long value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
                this.a &= 0xFFFFFFFB;
                this.d = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public long v() {
                return this.e;
            }

            public a d(long value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ab() {
                this.a &= 0xFFFFFFF7;
                this.e = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public long x() {
                return this.f;
            }

            public a e(long value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFEF;
                this.f = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public long z() {
                return this.g;
            }

            public a f(long value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFDF;
                this.g = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public long B() {
                return this.h;
            }

            public a g(long value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a ae() {
                this.a &= 0xFFFFFFBF;
                this.h = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public long D() {
                return this.i;
            }

            public a h(long value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a af() {
                this.a &= 0xFFFFFF7F;
                this.i = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public long F() {
                return this.j;
            }

            public a i(long value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ag() {
                this.a &= 0xFFFFFEFF;
                this.j = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public long H() {
                return this.k;
            }

            public a j(long value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a an() {
                this.a &= 0xFFFFFDFF;
                this.k = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public long L() {
                return this.l;
            }

            public a k(long value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a ao() {
                this.a &= 0xFFFFFBFF;
                this.l = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public long T() {
                return this.m;
            }

            public a l(long value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ap() {
                this.a &= 0xFFFFF7FF;
                this.m = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public long V() {
                return this.n;
            }

            public a m(long value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aq() {
                this.a &= 0xFFFFEFFF;
                this.n = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public long X() {
                return this.o;
            }

            public a n(long value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFDFFF;
                this.o = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public long Z() {
                return this.p;
            }

            public a o(long value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0L;
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

        public long p();

        public boolean q();

        public long r();

        public boolean s();

        public long t();

        public boolean u();

        public long v();

        public boolean w();

        public long x();

        public boolean y();

        public long z();

        public boolean A();

        public long B();

        public boolean C();

        public long D();

        public boolean E();

        public long F();

        public boolean G();

        public long H();

        public boolean K();

        public long L();

        public boolean S();

        public long T();

        public boolean U();

        public long V();

        public boolean W();

        public long X();

        public boolean Y();

        public long Z();
    }

    public static final class c
    extends p
    implements d {
        private static final c r;
        private final ap s;
        public static ab<c> a;
        private int t;
        public static final int b = 1;
        private int u;
        public static final int c = 2;
        private a.g v;
        public static final int d = 3;
        private a.g w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private long y;
        public static final int g = 6;
        private long z;
        public static final int h = 7;
        private long A;
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
            a = new a.c<c>(){

                public c c(a.h input, n extensionRegistry) throws s {
                    return new c(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            r = new c(true);
            r.ag();
        }

        private c(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private c(boolean noInit) {
            this.s = ap.c();
        }

        public static c h() {
            return r;
        }

        public c k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private c(a.h input, n extensionRegistry) throws s {
            this.ag();
            boolean mutable_bitField0_ = false;
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
                                this.t |= 2;
                                this.v = input.l();
                                break;
                            }
                            case 26: {
                                this.t |= 4;
                                this.w = input.l();
                                break;
                            }
                            case 32: {
                                this.t |= 8;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                this.t |= 0x10;
                                this.y = input.f();
                                break;
                            }
                            case 48: {
                                this.t |= 0x20;
                                this.z = input.f();
                                break;
                            }
                            case 56: {
                                this.t |= 0x40;
                                this.A = input.f();
                                break;
                            }
                            case 64: {
                                this.t |= 0x80;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x100;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x200;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x400;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x800;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x1000;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x2000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x4000;
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
                this.s = unknownFields.b();
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
        public a.g r() {
            return this.v;
        }

        @Override
        public boolean s() {
            return (this.t & 4) == 4;
        }

        @Override
        public a.g t() {
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
        public boolean w() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public long x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public long z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public long B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0;
            this.v = a.g.d;
            this.w = a.g.d;
            this.x = 0;
            this.y = 0L;
            this.z = 0L;
            this.A = 0L;
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
            if ((this.t & 8) == 8) {
                output.a(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                output.b(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                output.b(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                output.b(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                output.a(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                output.a(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                output.a(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
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
                size += a.i.c(2, this.v);
            }
            if ((this.t & 4) == 4) {
                size += a.i.c(3, this.w);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
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

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.d$c.aa();
        }

        public static a a(c prototype) {
            return an.d$c.aa().a(prototype);
        }

        public a ae() {
            return an.d$c.a(this);
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
        implements d {
            private int a;
            private int b;
            private a.g c = a.g.d;
            private a.g d = a.g.d;
            private int e;
            private long f;
            private long g;
            private long h;
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            public static final k.a k() {
                return c;
            }

            @Override
            protected p.g l() {
                return d.a(c.class, a.class);
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
                this.c = a.g.d;
                this.a &= 0xFFFFFFFD;
                this.d = a.g.d;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = 0L;
                this.a &= 0xFFFFFFEF;
                this.g = 0L;
                this.a &= 0xFFFFFFDF;
                this.h = 0L;
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
                return an.d$c$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return c;
            }

            public c I() {
                return an.d$c.h();
            }

            public c M() {
                c result = this.N();
                if (!result.a()) {
                    throw an.d$c$a.b(result);
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
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
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
                if (other == an.d$c.h()) {
                    return this;
                }
                if (other.o()) {
                    this.a(other.p());
                }
                if (other.q()) {
                    this.e(other.r());
                }
                if (other.s()) {
                    this.f(other.t());
                }
                if (other.u()) {
                    this.b(other.v());
                }
                if (other.w()) {
                    this.a(other.x());
                }
                if (other.y()) {
                    this.b(other.z());
                }
                if (other.A()) {
                    this.c(other.B());
                }
                if (other.C()) {
                    this.c(other.D());
                }
                if (other.E()) {
                    this.d(other.F());
                }
                if (other.G()) {
                    this.e(other.H());
                }
                if (other.K()) {
                    this.f(other.L());
                }
                if (other.S()) {
                    this.g(other.T());
                }
                if (other.U()) {
                    this.h(other.V());
                }
                if (other.W()) {
                    this.i(other.X());
                }
                if (other.Y()) {
                    this.j(other.Z());
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

            public a a(int value) {
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
            public a.g r() {
                return this.c;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = an.d$c.h().r();
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

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
                this.a &= 0xFFFFFFFB;
                this.d = an.d$c.h().t();
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

            public a b(int value) {
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
            public long x() {
                return this.f;
            }

            public a a(long value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFEF;
                this.f = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public long z() {
                return this.g;
            }

            public a b(long value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFDF;
                this.g = 0L;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public long B() {
                return this.h;
            }

            public a c(long value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a ae() {
                this.a &= 0xFFFFFFBF;
                this.h = 0L;
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

            public a c(int value) {
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

            public a d(int value) {
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

            public a e(int value) {
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

            public a f(int value) {
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

            public a g(int value) {
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

            public a h(int value) {
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

            public a i(int value) {
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

            public a j(int value) {
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

    public static interface d
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public a.g r();

        public boolean s();

        public a.g t();

        public boolean u();

        public int v();

        public boolean w();

        public long x();

        public boolean y();

        public long z();

        public boolean A();

        public long B();

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
        private a.g y;
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
            boolean mutable_bitField0_ = false;
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
                            case 42: {
                                this.t |= 0x10;
                                this.y = input.l();
                                break;
                            }
                            case 48: {
                                this.t |= 0x20;
                                this.z = input.g();
                                break;
                            }
                            case 56: {
                                this.t |= 0x40;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x80;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x100;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x200;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x400;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x800;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x1000;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x2000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x4000;
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
        public boolean w() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public a.g x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x4000) == 16384;
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
            this.y = a.g.d;
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
            if ((this.t & 8) == 8) {
                output.a(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                output.a(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                output.a(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                output.a(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                output.a(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                output.a(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                output.a(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
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
            if ((this.t & 0x10) == 16) {
                size += a.i.c(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
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
            return an.d$e.aa();
        }

        public static a a(e prototype) {
            return an.d$e.aa().a(prototype);
        }

        public a ae() {
            return an.d$e.a(this);
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
            private a.g f = a.g.d;
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
                this.f = a.g.d;
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
                return an.d$e$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return e;
            }

            public e I() {
                return an.d$e.h();
            }

            public e M() {
                e result = this.N();
                if (!result.a()) {
                    throw an.d$e$a.b(result);
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
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
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
                if (other == an.d$e.h()) {
                    return this;
                }
                if (other.o()) {
                    this.a(other.p());
                }
                if (other.q()) {
                    this.b(other.r());
                }
                if (other.s()) {
                    this.c(other.t());
                }
                if (other.u()) {
                    this.d(other.v());
                }
                if (other.w()) {
                    this.e(other.x());
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

            public a a(int value) {
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

            public a b(int value) {
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

            public a c(int value) {
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
            public a.g x() {
                return this.f;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFEF;
                this.f = an.d$e.h().x();
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

        public boolean w();

        public a.g x();

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
        private int v;
        public static final int d = 3;
        private a.g w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private int y;
        public static final int g = 6;
        private a.g z;
        public static final int h = 7;
        private int A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private a.g C;
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
            boolean mutable_bitField0_ = false;
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
                            case 16: {
                                this.t |= 2;
                                this.v = input.g();
                                break;
                            }
                            case 26: {
                                this.t |= 4;
                                this.w = input.l();
                                break;
                            }
                            case 32: {
                                this.t |= 8;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                this.t |= 0x10;
                                this.y = input.g();
                                break;
                            }
                            case 50: {
                                this.t |= 0x20;
                                this.z = input.l();
                                break;
                            }
                            case 56: {
                                this.t |= 0x40;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x80;
                                this.B = input.g();
                                break;
                            }
                            case 74: {
                                this.t |= 0x100;
                                this.C = input.l();
                                break;
                            }
                            case 80: {
                                this.t |= 0x200;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x400;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x800;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x1000;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x2000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x4000;
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
        public a.g t() {
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
        public boolean w() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public a.g z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public a.g F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0;
            this.v = 0;
            this.w = a.g.d;
            this.x = 0;
            this.y = 0;
            this.z = a.g.d;
            this.A = 0;
            this.B = 0;
            this.C = a.g.d;
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
            if ((this.t & 0x10) == 16) {
                output.a(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                output.a(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                output.a(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                output.a(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                output.a(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                output.a(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
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
                size += a.i.c(3, this.w);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.c(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.c(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
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
            return an.d$g.aa();
        }

        public static a a(g prototype) {
            return an.d$g.aa().a(prototype);
        }

        public a ae() {
            return an.d$g.a(this);
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
            private int c;
            private a.g d = a.g.d;
            private int e;
            private int f;
            private a.g g = a.g.d;
            private int h;
            private int i;
            private a.g j = a.g.d;
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
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = a.g.d;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = 0;
                this.a &= 0xFFFFFFEF;
                this.g = a.g.d;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = a.g.d;
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
                return an.d$g$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return g;
            }

            public g I() {
                return an.d$g.h();
            }

            public g M() {
                g result = this.N();
                if (!result.a()) {
                    throw an.d$g$a.b(result);
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
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
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
                if (other == an.d$g.h()) {
                    return this;
                }
                if (other.o()) {
                    this.a(other.p());
                }
                if (other.q()) {
                    this.b(other.r());
                }
                if (other.s()) {
                    this.e(other.t());
                }
                if (other.u()) {
                    this.c(other.v());
                }
                if (other.w()) {
                    this.d(other.x());
                }
                if (other.y()) {
                    this.f(other.z());
                }
                if (other.A()) {
                    this.e(other.B());
                }
                if (other.C()) {
                    this.f(other.D());
                }
                if (other.E()) {
                    this.g(other.F());
                }
                if (other.G()) {
                    this.g(other.H());
                }
                if (other.K()) {
                    this.h(other.L());
                }
                if (other.S()) {
                    this.i(other.T());
                }
                if (other.U()) {
                    this.j(other.V());
                }
                if (other.W()) {
                    this.k(other.X());
                }
                if (other.Y()) {
                    this.l(other.Z());
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

            public a a(int value) {
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

            public a b(int value) {
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

            public a aa() {
                this.a &= 0xFFFFFFFB;
                this.d = an.d$g.h().t();
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
            public a.g z() {
                return this.g;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFDF;
                this.g = an.d$g.h().z();
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

            public a e(int value) {
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

            public a f(int value) {
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
            public a.g F() {
                return this.j;
            }

            public a g(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ag() {
                this.a &= 0xFFFFFEFF;
                this.j = an.d$g.h().F();
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

            public a g(int value) {
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

            public a h(int value) {
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

            public a i(int value) {
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

            public a j(int value) {
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

            public a k(int value) {
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

            public a l(int value) {
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

        public boolean q();

        public int r();

        public boolean s();

        public a.g t();

        public boolean u();

        public int v();

        public boolean w();

        public int x();

        public boolean y();

        public a.g z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public a.g F();

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
        private List<a.g> u;
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
                                if ((mutable_bitField0_ & 1) != 1) {
                                    this.u = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 1;
                                }
                                this.u.add(input.l());
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
                                this.t |= 1;
                                this.w = input.g();
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
                if (mutable_bitField0_ & true) {
                    this.u = Collections.unmodifiableList(this.u);
                }
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
        public List<a.g> o() {
            return this.u;
        }

        @Override
        public int p() {
            return this.u.size();
        }

        @Override
        public a.g a(int index) {
            return this.u.get(index);
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
        public a.g b(int index) {
            return this.v.get(index);
        }

        @Override
        public boolean s() {
            return (this.t & 1) == 1;
        }

        @Override
        public int t() {
            return this.w;
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
            this.u = Collections.emptyList();
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
            int i2 = 0;
            while (i2 < this.u.size()) {
                output.a(1, this.u.get(i2));
                ++i2;
            }
            i2 = 0;
            while (i2 < this.v.size()) {
                output.a(2, this.v.get(i2));
                ++i2;
            }
            if ((this.t & 1) == 1) {
                output.a(3, this.w);
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
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.u.size()) {
                dataSize += a.i.b(this.u.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.o().size();
            dataSize = 0;
            i2 = 0;
            while (i2 < this.v.size()) {
                dataSize += a.i.b(this.v.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.q().size();
            if ((this.t & 1) == 1) {
                size += a.i.g(3, this.w);
            }
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
            return an.d$i.aa();
        }

        public static a a(i prototype) {
            return an.d$i.aa().a(prototype);
        }

        public a ae() {
            return an.d$i.a(this);
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
            private List<a.g> b = Collections.emptyList();
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
                this.b = Collections.emptyList();
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
                return an.d$i$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return i;
            }

            public i I() {
                return an.d$i.h();
            }

            public i M() {
                i result = this.N();
                if (!result.a()) {
                    throw an.d$i$a.b(result);
                }
                return result;
            }

            public i N() {
                i result = new i(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((this.a & 1) == 1) {
                    this.b = Collections.unmodifiableList(this.b);
                    this.a &= 0xFFFFFFFE;
                }
                result.u = this.b;
                if ((this.a & 2) == 2) {
                    this.c = Collections.unmodifiableList(this.c);
                    this.a &= 0xFFFFFFFD;
                }
                result.v = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 1;
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
                if (other instanceof i) {
                    return this.a((i)other);
                }
                super.a(other);
                return this;
            }

            public a a(i other) {
                if (other == an.d$i.h()) {
                    return this;
                }
                if (!other.u.isEmpty()) {
                    if (this.b.isEmpty()) {
                        this.b = other.u;
                        this.a &= 0xFFFFFFFE;
                    } else {
                        this.aw();
                        this.b.addAll(other.u);
                    }
                    this.t_();
                }
                if (!other.v.isEmpty()) {
                    if (this.c.isEmpty()) {
                        this.c = other.v;
                        this.a &= 0xFFFFFFFD;
                    } else {
                        this.ax();
                        this.c.addAll(other.v);
                    }
                    this.t_();
                }
                if (other.s()) {
                    this.c(other.t());
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

            private void aw() {
                if ((this.a & 1) != 1) {
                    this.b = new ArrayList<a.g>(this.b);
                    this.a |= 1;
                }
            }

            @Override
            public List<a.g> o() {
                return Collections.unmodifiableList(this.b);
            }

            @Override
            public int p() {
                return this.b.size();
            }

            @Override
            public a.g a(int index) {
                return this.b.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.b.set(index, value);
                this.t_();
                return this;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.b.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends a.g> values) {
                this.aw();
                p.a.a(values, this.b);
                this.t_();
                return this;
            }

            public a O() {
                this.b = Collections.emptyList();
                this.a &= 0xFFFFFFFE;
                this.t_();
                return this;
            }

            private void ax() {
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
            public a.g b(int index) {
                return this.c.get(index);
            }

            public a b(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ax();
                this.c.set(index, value);
                this.t_();
                return this;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ax();
                this.c.add(value);
                this.t_();
                return this;
            }

            public a b(Iterable<? extends a.g> values) {
                this.ax();
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

            public a c(int value) {
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

    public static interface j
    extends aa {
        public List<a.g> o();

        public int p();

        public a.g a(int var1);

        public List<a.g> q();

        public int r();

        public a.g b(int var1);

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

