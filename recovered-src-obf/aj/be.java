/*
 * Decompiled with CFR 0.152.
 */
package aj;

import ai.c;
import aj.cv;
import ao.a;
import ao.b;
import aq.v;
import be.bu;
import be.ce;
import bj.d;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

public class be
extends cv {
    private static final Logger a = Logger.getLogger(be.class.getName());

    public be(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        int j2;
        byte[] acc = this.a(128);
        int pwLenth = this.b();
        byte[] pws = this.a(128);
        if (pwLenth < 1 || pwLenth > pws.length / 4) {
            a.log(Level.WARNING, "\u62d2\u7d55\u4e0d\u5408\u6cd5\u7684\u5bc6\u78bc\u5340\u584a\u6578\u91cf: " + pwLenth + " ip=" + client.g());
            client.a(new bu(9));
            return;
        }
        String LOGIN_RSA_MODULUS_N = "A15763CE9AE64683EE7D36A7886AAAC57367390105650530AEDC442FA9B173E1D183C4A96E0CAC1B0B58EA14AAD712FF923E0E17F52579E13E0CDEBF29107EE7CDFF62D3F84FD7D13896DCC80AECAD96A64AB7D3A1F3203E38C130AE70E088295890437ED267E040CB07353F805FA031721FDB8EBBFE827062F1D6B2EAF04961";
        String LOGIN_RSA_PRIVATE_EXPONET_D = "84BFE5C421D9649DBB05D5896509F6F6BD6C6A0FB8039590A7DF5E4F253D77CE8ED7EF6144C63279920D979467B8CCCDF1E3132F8D2A47AD17A3F946AA200379A1D366D4161989AB621D49715BC6EB172FEFA4F1D7871A9860C40A614FAAA8D418EF88E927B66E752C1303F90F7476335E16608BE6221954DD8F5D541D73C2BD";
        PrivateKey privateKey = this.a("A15763CE9AE64683EE7D36A7886AAAC57367390105650530AEDC442FA9B173E1D183C4A96E0CAC1B0B58EA14AAD712FF923E0E17F52579E13E0CDEBF29107EE7CDFF62D3F84FD7D13896DCC80AECAD96A64AB7D3A1F3203E38C130AE70E088295890437ED267E040CB07353F805FA031721FDB8EBBFE827062F1D6B2EAF04961", "84BFE5C421D9649DBB05D5896509F6F6BD6C6A0FB8039590A7DF5E4F253D77CE8ED7EF6144C63279920D979467B8CCCDF1E3132F8D2A47AD17A3F946AA200379A1D366D4161989AB621D49715BC6EB172FEFA4F1D7871A9860C40A614FAAA8D418EF88E927B66E752C1303F90F7476335E16608BE6221954DD8F5D541D73C2BD");
        Cipher _cipher = Cipher.getInstance("RSA");
        _cipher.init(2, privateKey);
        byte[] result = _cipher.doFinal(acc);
        String loginName = new String(result, l1j.server.a.k);
        byte[] pw = _cipher.doFinal(pws);
        int seed = client.a;
        int i2 = pwLenth - 1;
        while (i2 > 0) {
            j2 = 0;
            j2 = 0;
            while (j2 < 4) {
                int n2 = (i2 << 2) + j2;
                pw[n2] = (byte)(pw[n2] ^ pw[(i2 << 2) - 4 + j2]);
                ++j2;
            }
            j2 = 0;
            int var = pw[(i2 << 2) + j2++] & 0xFF;
            var |= pw[(i2 << 2) + j2++] << 8 & 0xFF00;
            var |= pw[(i2 << 2) + j2++] << 16 & 0xFF0000;
            var |= pw[(i2 << 2) + j2++] << 24 & 0xFF000000;
            var = Integer.rotateRight(var, i2) ^ seed;
            j2 = 0;
            pw[(i2 << 2) + j2++] = (byte)(var & 0xFF);
            pw[(i2 << 2) + j2++] = (byte)(var >> 8 & 0xFF);
            pw[(i2 << 2) + j2++] = (byte)(var >> 16 & 0xFF);
            pw[(i2 << 2) + j2++] = (byte)(var >> 24 & 0xFF);
            --i2;
        }
        byte[] keys = new byte[]{(byte)seed, (byte)(seed >> 8), (byte)(seed >> 16), (byte)(seed >> 24)};
        j2 = 0;
        while (j2 < 4) {
            int n3 = j2;
            pw[n3] = (byte)(pw[n3] ^ keys[j2]);
            ++j2;
        }
        bi.a bs2 = new bi.a();
        int i3 = 0;
        while (i3 < pwLenth) {
            int j3 = 0;
            int var = pw[(i3 << 2) + j3++] & 0xFF;
            var |= pw[(i3 << 2) + j3++] << 8 & 0xFF00;
            var |= pw[(i3 << 2) + j3++] << 16 & 0xFF0000;
            if ((var |= pw[(i3 << 2) + j3++] << 24 & 0xFF000000) == 0) break;
            bs2.c(var);
            ++i3;
        }
        String accountInfo = loginName.toLowerCase();
        String password = new String(bs2.b(), l1j.server.a.k);
        bs2.close();
        String ip = client.g();
        String accountName = "";
        String host = "";
        if (accountInfo.contains(":")) {
            accountName = accountInfo.split(":")[0];
            host = accountInfo.split(":")[1];
        } else {
            accountName = accountInfo;
        }
        if (password.contains(":")) {
            String[] splite = password.split(":");
            password = splite[0];
        }
        if (!accountName.matches("[0-9a-zA-Z]*")) {
            a.log(Level.SEVERE, "\u4e0d\u5408\u6cd5\u7684\u5e33\u865f\u5b57\u5143\u3002account=" + accountName + " ip=" + ip);
            client.a(new bu(9));
            return;
        }
        bh.a account = ao.a.a().c(accountName);
        if (account == null) {
            if (!l1j.server.a.n) {
                a.log(Level.SEVERE, "\u76ee\u524d\u4e0d\u53ef\u5275\u5e33\u865f\u3002account=" + accountName + " ip=" + ip);
                client.a(new bu(155));
                return;
            }
            if (!host.equals("")) {
                if (!ao.a.a().b(host)) {
                    a.log(Level.SEVERE, "\u5275\u5e33\u865f\u6578\u91cf\u8d85\u904e\u9650\u5236\u3002account=" + accountName + " mac=" + host);
                    client.a(new bu(156));
                    return;
                }
            } else if (!ao.a.a().a(ip)) {
                a.log(Level.SEVERE, "\u5275\u5e33\u865f\u6578\u91cf\u8d85\u904e\u9650\u5236\u3002account=" + accountName + " ip=" + ip);
                client.a(new bu(156));
                return;
            }
            account = ao.a.a().a(accountName, password, ip, host);
        }
        if (!account.a(password)) {
            a.log(Level.SEVERE, "\u5bc6\u78bc\u932f\u8aa4\u3002account=" + accountName + " ip=" + ip);
            client.a(new bu(149));
            return;
        }
        if (account.n()) {
            boolean isNetConnectionOnline = false;
            for (d netConnection : c.a().c()) {
                if (netConnection.a() == null || !netConnection.a().equals(accountName)) continue;
                a.log(Level.SEVERE, "\u5df2\u4f7f\u7528\u4e2d-\u8e22\u6389\u91cd\u8907\u7684\u9023\u7dda\u53ca\u5e33\u865f\u3002account=" + accountName + " ip=" + ip);
                isNetConnectionOnline = true;
                netConnection.a(0);
            }
            if (!isNetConnectionOnline) {
                ao.a.a().a(account, false);
                ao.a.a().b(account, false);
                ao.a.a().e(account.d());
                a.log(Level.SEVERE, "\u5df2\u4f7f\u7528\u4e2d-\u8e22\u6389\u91cd\u8907\u7684\u5e33\u865f\u3002account=" + accountName + " ip=" + ip);
            }
            client.a(new bu(22));
            return;
        }
        if (account.j()) {
            a.log(Level.SEVERE, "\u7981\u6b62\u767b\u5165\u7684\u5e33\u865f\u5617\u8a66\u767b\u5165\u3002account=" + accountName + " ip=" + ip);
            client.a(new bu(153));
            return;
        }
        if (l1j.server.a.u > 0) {
            int count = 0;
            for (d tempClient : c.a().c()) {
                if (!ip.equalsIgnoreCase(tempClient.g())) continue;
                ++count;
            }
            if (count > l1j.server.a.u) {
                a.log(Level.SEVERE, "\u8d85\u904e\u591a\u958b\u8a2d\u5b9a-\u62d2\u7d55\u767b\u5165\u3002account=" + accountName + " ip=" + ip);
                client.a(new bu(38));
                return;
            }
        }
        try {
            account.d(ip);
            account.f(host);
            ao.a.a().a(account, client);
            client.a(account);
            client.a(new bu(0));
            if (l1j.server.a.x) {
                b.a().a(client);
                client.a(new ce(client.b()));
            } else {
                v.a(client);
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, "\u767b\u5165\u5e33\u865f\u6642\u7570\u5e38\u3002account=" + accountName + " ip=" + ip);
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            client.a(new bu(158));
            client.c();
        }
    }

    private PrivateKey a(String modulus, String privateExponent) throws Exception {
        BigInteger m2 = new BigInteger(modulus, 16);
        BigInteger e2 = new BigInteger(privateExponent, 16);
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m2, e2);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    @Override
    public String a() {
        return "C_Login";
    }
}

