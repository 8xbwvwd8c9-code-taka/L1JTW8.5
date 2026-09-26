import importlib.util
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
BOOTSTRAP_PATH = ROOT / "tools" / "850" / "bootstrap" / "bootstrap_core.py"
PACKAGE_MAP_PATH = ROOT / "tools" / "850" / "bootstrap" / "package_map.py"


def load(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class BootstrapCollectionArtifactTests(unittest.TestCase):
    def entries(self):
        pm = load(PACKAGE_MAP_PATH, "fast_dev_collection_artifact_package_map")
        rules = {
            "families": {
                "aj": {"package": "l1j/server/clientpackets", "category": "clientpackets"},
                "ao": {"package": "l1j/server/datatables", "category": "datatables"},
                "ap": {"package": "l1j/server/model/instance", "category": "instances"},
                "ba": {"package": "l1j/server/model/timer", "category": "timers"},
                "be": {"package": "l1j/server/serverpackets", "category": "serverpackets"},
            },
            "overrides": {},
        }
        entries = pm.build_package_map(
            [
                {"Class": "aj.az", "SourceFile": "C_ItemUSe.java"},
                {"Class": "aj.bs", "SourceFile": "C_ProtoBuffers.java"},
                {"Class": "aj.bx", "SourceFile": "C_Result.java"},
                {"Class": "aj.cd", "SourceFile": "C_ShopWorld.java"},
                {"Class": "ao.ba", "SourceFile": "RankingTable.java"},
                {"Class": "ao.bc", "SourceFile": "ShopTable.java"},
                {"Class": "ap.t", "SourceFile": "L1NpcInstance.java"},
                {"Class": "ap.v", "SourceFile": "L1PetInstance.java"},
                {"Class": "ba.h", "SourceFile": "HomeTownTimer.java"},
                {"Class": "be.db", "SourceFile": "S_PrivateShop.java"},
                {"Class": "be.dc", "SourceFile": "S_ProtoBuffers.java"},
            ],
            rules,
        )
        pm.validate_package_map(entries)
        return entries

    def rewrite(self, source_file: str, source: str) -> str:
        mod = load(BOOTSTRAP_PATH, f"fast_dev_{source_file}_collection_artifacts")
        entries = self.entries()
        target = next(entry for entry in entries if entry.source_file == source_file)
        return mod.rewrite_java_source(source, target, entries)

    def test_restores_c_itemuse_l1object_overload_casts(self):
        rewritten = self.rewrite(
            "C_ItemUSe.java",
            "package l1r.aj;\n"
            "public class C_ItemUSe {\n"
            "  void x() {\n"
            "    var3.ct(var3.a(var150));\n"
            "    var3.ct(var3.a(var183));\n"
            "  }\n"
            "}\n",
        )
        self.assertIn("var3.ct(var3.a((L1Object)var150));", rewritten)
        self.assertIn("var3.ct(var3.a((L1Object)var183));", rewritten)
        self.assertNotIn("var3.ct(var3.a(var150));", rewritten)
        self.assertNotIn("var3.ct(var3.a(var183));", rewritten)

    def test_restores_c_protobuf_material_list_generic(self):
        rewritten = self.rewrite(
            "C_ProtoBuffers.java",
            "package l1r.aj;\n"
            "import java.util.ArrayList;\n"
            "public class C_ProtoBuffers {\n"
            "  void x() {\n"
            "    ArrayList var152 = new ArrayList<>();\n"
            "    for (L1ItemInstance var161 : var152) {}\n"
            "  }\n"
            "}\n",
        )
        self.assertIn(
            "ArrayList<L1ItemInstance> var152 = new ArrayList<>();",
            rewritten,
        )

    def test_restores_c_result_typed_claim_and_private_shop_lists(self):
        rewritten = self.rewrite(
            "C_Result.java",
            "package l1r.aj;\n"
            "import java.util.HashMap;\n"
            "import java.util.List;\n"
            "import java.util.concurrent.CopyOnWriteArrayList;\n"
            "public class C_Result {\n"
            "  void x() {\n"
            "    HashMap var32 = LuckyDrawTable.a().c(var2.e().d());\n"
            "    L1ItemInstance var39 = var32.get(var30);\n"
            "    CopyOnWriteArrayList var44 = var37.aU();\n"
            "    L1PrivateShopSellList var65 = var44.get(var60);\n"
            "    List var41 = var35.aV();\n"
            "    L1PrivateShopBuyList var66 = var41.get(var62);\n"
            "  }\n"
            "}\n",
        )
        self.assertIn(
            "HashMap<Integer, L1ItemInstance> var32 = LuckyDrawTable.a().c(var2.e().d());",
            rewritten,
        )
        self.assertIn(
            "CopyOnWriteArrayList<L1PrivateShopSellList> var44 = var37.aU();",
            rewritten,
        )
        self.assertIn(
            "List<L1PrivateShopBuyList> var41 = var35.aV();",
            rewritten,
        )

    def test_restores_c_shopworld_claim_map_generic(self):
        rewritten = self.rewrite(
            "C_ShopWorld.java",
            "package l1r.aj;\n"
            "import java.util.concurrent.ConcurrentHashMap;\n"
            "public class C_ShopWorld {\n"
            "  void x() {\n"
            "    ConcurrentHashMap var13 = ShopWorldTable.a().a(var2.a());\n"
            "    ConcurrentHashMap var28 = ShopWorldTable.a().a(var2.a());\n"
            "    L1ItemInstance var29 = var28.get(var26);\n"
            "  }\n"
            "}\n",
        )
        self.assertIn(
            "ConcurrentHashMap<Integer, L1ItemInstance> var13 = ShopWorldTable.a().a(var2.a());",
            rewritten,
        )
        self.assertIn(
            "ConcurrentHashMap<Integer, L1ItemInstance> var28 = ShopWorldTable.a().a(var2.a());",
            rewritten,
        )

    def test_restores_ranking_value_cast_without_breaking_typed_map_assignments(self):
        rewritten = self.rewrite(
            "RankingTable.java",
            "package l1r.ao;\n"
            "import java.util.ArrayList;\n"
            "import java.util.Collections;\n"
            "import java.util.Comparator;\n"
            "import java.util.HashMap;\n"
            "public class RankingTable {\n"
            "  private HashMap<Integer, Integer> e = new HashMap<>();\n"
            "  void x(ArrayList<RankingTable.L1R_a> var1) {\n"
            "    HashMap var3 = new HashMap<>();\n"
            "    var3 = this.e;\n"
            "    int var6 = 1;\n"
            "    int var7 = var3.get(var6);\n"
            "    Collections.sort(var1, new Comparator<RankingTable.L1R_a>() {\n"
            "      public int a(RankingTable.L1R_a var1, RankingTable.L1R_a var2) { return 0; }\n"
            "      @Override public int compare(Object var1, Object var2) { return this.a((RankingTable.L1R_a)var1, (RankingTable.L1R_a)var2); }\n"
            "    });\n"
            "  }\n"
            "  static class L1R_a {}\n"
            "}\n",
        )
        self.assertIn("HashMap var3 = new HashMap<>();", rewritten)
        self.assertIn("var3 = this.e;", rewritten)
        self.assertIn(
            "int var7 = ((Integer)var3.get(var6)).intValue();",
            rewritten,
        )
        self.assertIn("new Comparator<RankingTable.L1R_a>() {", rewritten)
        self.assertNotIn("HashMap<Object, Object> var3", rewritten)
        self.assertNotIn("new Comparator() {", rewritten)

    def test_restores_shoptable_collection_and_bridge_types(self):
        rewritten = self.rewrite(
            "ShopTable.java",
            "package l1r.ao;\n"
            "import java.util.ArrayList;\n"
            "import java.util.Collections;\n"
            "import java.util.Comparator;\n"
            "import java.util.HashMap;\n"
            "import java.util.List;\n"
            "public class ShopTable {\n"
            "  void a() {\n"
            "    HashMap var4 = ItemTable.a().c();\n"
            "    ArrayList var5 = new ArrayList<>();\n"
            "    for (L1Item var6 : var4.values()) {}\n"
            "    Collections.sort(var5, new Comparator<L1ShopItem>() {\n"
            "      public int a(L1ShopItem var1, L1ShopItem var2x) { return 0; }\n"
            "      @Override public int compare(Object var1, Object var2) { return this.a((L1ShopItem)var1, (L1ShopItem)var2); }\n"
            "    });\n"
            "    List var6 = var1.b();\n"
            "    for (L1ShopItem var7 : var6) {}\n"
            "  }\n"
            "}\n",
        )
        self.assertIn("HashMap<Integer, L1Item> var4 = ItemTable.a().c();", rewritten)
        self.assertIn("ArrayList<L1ShopItem> var5 = new ArrayList<>();", rewritten)
        self.assertIn("new Comparator<L1ShopItem>() {", rewritten)
        self.assertIn("List<L1ShopItem> var6 = var1.b();", rewritten)
        self.assertNotIn("new Comparator() {", rewritten)

    def test_restores_l1npc_ground_inventory_and_path_queue_generics(self):
        rewritten = self.rewrite(
            "L1NpcInstance.java",
            "package l1r.ap;\n"
            "import java.util.ArrayList;\n"
            "import java.util.LinkedList;\n"
            "public class L1NpcInstance {\n"
            "  void j() {\n"
            "    ArrayList var1 = new ArrayList<>();\n"
            "    for (L1ItemInstance var6 : var1.get(0).d()) {}\n"
            "  }\n"
            "  void r() {\n"
            "    ArrayList var1 = new ArrayList<>();\n"
            "    for (L1ItemInstance var6 : var1.get(0).d()) {}\n"
            "  }\n"
            "  void path() {\n"
            "    LinkedList var18 = new LinkedList<>();\n"
            "    int[] var6 = var18.removeFirst();\n"
            "  }\n"
            "}\n",
        )
        self.assertEqual(
            rewritten.count("ArrayList<L1GroundInventory> var1 = new ArrayList<>();"),
            2,
        )
        self.assertIn("LinkedList<int[]> var18 = new LinkedList<>();", rewritten)

    def test_removes_only_donor_proven_l1pet_override_artifacts(self):
        rewritten = self.rewrite(
            "L1PetInstance.java",
            "package l1r.ap;\n"
            "public class L1PetInstance {\n"
            "   @Override\n"
            "   public void d(int var1) {}\n"
            "   @Override\n"
            "   public void b(boolean var1) {}\n"
            "   @Override\n"
            "   public void i() {}\n"
            "   @Override\n"
            "   public void b(L1ItemInstance var1) {}\n"
            "   @Override\n"
            "   public void b(L1PcInstance var1) {}\n"
            "}\n",
        )
        self.assertNotIn("@Override\n   public void d(int var1)", rewritten)
        self.assertNotIn("@Override\n   public void b(boolean var1)", rewritten)
        self.assertNotIn("@Override\n   public void i()", rewritten)
        self.assertNotIn("@Override\n   public void b(L1ItemInstance var1)", rewritten)
        self.assertIn("@Override\n   public void b(L1PcInstance var1)", rewritten)

    def test_restores_hometown_player_collection_generic(self):
        rewritten = self.rewrite(
            "HomeTownTimer.java",
            "package l1r.ba;\n"
            "import java.util.Collection;\n"
            "public class HomeTownTimer {\n"
            "  void c() {\n"
            "    Collection var1 = L1World.a().c();\n"
            "    for (L1PcInstance var2 : var1) {}\n"
            "    for (L1PcInstance var5 : var1) {}\n"
            "    for (L1PcInstance var8 : var1) {}\n"
            "  }\n"
            "}\n",
        )
        self.assertIn(
            "Collection<L1PcInstance> var1 = L1World.a().c();",
            rewritten,
        )

    def test_restores_s_private_shop_list_element_types(self):
        rewritten = self.rewrite(
            "S_PrivateShop.java",
            "package l1r.be;\n"
            "import java.util.List;\n"
            "public class S_PrivateShop {\n"
            "  void x() {\n"
            "    List var5 = var4.aU();\n"
            "    L1PrivateShopSellList var8 = var5.get(0);\n"
            "    List var18 = var4.aV();\n"
            "    L1PrivateShopBuyList var21 = var18.get(0);\n"
            "  }\n"
            "}\n",
        )
        self.assertIn(
            "CopyOnWriteArrayList<L1PrivateShopSellList> var5 = var4.aU();",
            rewritten,
        )
        self.assertIn(
            "ArrayList<L1PrivateShopBuyList> var18 = var4.aV();",
            rewritten,
        )
        self.assertIn("import java.util.ArrayList;", rewritten)
        self.assertIn("import java.util.concurrent.CopyOnWriteArrayList;", rewritten)

    def test_restores_s_protobuf_shadowed_byte_string_calls(self):
        rewritten = self.rewrite(
            "S_ProtoBuffers.java",
            "package l1r.be;\n"
            "import a.g;\n"
            "public class S_ProtoBuffers {\n"
            "  public static final int a = 55;\n"
            "  public static final int g = 76;\n"
            "  void x(byte[] data) {\n"
            "    use(a.g.a(new byte[]{-30, 112, -1}));\n"
            "    use(a.g.a(data));\n"
            "    use(a.g.a(data));\n"
            "    use(a.g.a(new byte[]{-1, 0, -1}));\n"
            "  }\n"
            "  void use(Object value) {}\n"
            "}\n",
        )
        self.assertEqual(rewritten.count("((a.g)null).a("), 4)
        self.assertNotIn("a.g.a(", rewritten)
        self.assertNotIn("g.a(", rewritten)
        self.assertIn("import a.g;", rewritten)


if __name__ == "__main__":
    unittest.main()