/*    */ package thaumcraft.api.golems.parts;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ import thaumcraft.api.golems.EnumGolemTrait;
/*    */ 
/*    */ public class GolemAddon
/*    */ {
/*  9 */   protected static GolemAddon[] addons = new GolemAddon[1];
/*    */   
/*    */   public byte id;
/*    */   
/*    */   public String key;
/*    */   
/*    */   public String[] research;
/*    */   public ResourceLocation icon;
/*    */   public Object[] components;
/*    */   public EnumGolemTrait[] traits;
/*    */   public IAddonFunction function;
/*    */   public PartModel model;
/*    */   
/*    */   public GolemAddon(String key, String[] research, ResourceLocation icon, PartModel model, Object[] comp, EnumGolemTrait[] tags)
/*    */   {
/* 24 */     this.key = key;
/* 25 */     this.research = research;
/* 26 */     this.icon = icon;
/* 27 */     this.components = comp;
/* 28 */     this.traits = tags;
/* 29 */     this.model = model;
/* 30 */     this.function = null;
/*    */   }
/*    */   
/*    */   public GolemAddon(String key, String[] research, ResourceLocation icon, PartModel model, Object[] comp, IAddonFunction function, EnumGolemTrait[] tags) {
/* 34 */     this(key, research, icon, model, comp, tags);
/* 35 */     this.function = function;
/*    */   }
/*    */   
/* 38 */   private static byte lastID = 0;
/*    */   
/* 40 */   public static void register(GolemAddon thing) { thing.id = lastID;
/* 41 */     lastID = (byte)(lastID + 1);
/*    */     
/* 43 */     if (thing.id >= addons.length) {
/* 44 */       GolemAddon[] temp = new GolemAddon[thing.id + 1];
/* 45 */       System.arraycopy(addons, 0, temp, 0, addons.length);
/* 46 */       addons = temp;
/*    */     }
/* 48 */     addons[thing.id] = thing;
/*    */   }
/*    */   
/*    */   public String getLocalizedName() {
/* 52 */     return I18n.translateToLocal("golem.addon." + this.key.toLowerCase());
/*    */   }
/*    */   
/*    */   public String getLocalizedDescription() {
/* 56 */     return I18n.translateToLocal("golem.addon.text." + this.key.toLowerCase());
/*    */   }
/*    */   
/*    */   public static GolemAddon[] getAddons() {
/* 60 */     return addons;
/*    */   }
/*    */   
/*    */   public static abstract interface IAddonFunction
/*    */     extends IGenericFunction
/*    */   {}
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\golems\parts\GolemAddon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */