/*    */ package thaumcraft.api;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
		 import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*    */ import net.minecraftforge.common.util.EnumHelper;
/*    */ 
/*    */ public class ThaumcraftMaterials
/*    */ {
/* 11 */   public static ToolMaterial TOOLMAT_THAUMIUM = EnumHelper.addToolMaterial("THAUMIUM", 3, 500, 7.0F, 2.5F, 22);
/* 12 */   public static ToolMaterial TOOLMAT_VOID = EnumHelper.addToolMaterial("VOID", 4, 150, 8.0F, 3.0F, 10);
/* 13 */   public static ToolMaterial TOOLMAT_ELEMENTAL = EnumHelper.addToolMaterial("THAUMIUM_ELEMENTAL", 3, 1500, 9.0F, 3.0F, 18);
/* 14 */   public static ArmorMaterial ARMORMAT_THAUMIUM = EnumHelper.addArmorMaterial("THAUMIUM", "THAUMIUM", 25, new int[] { 2, 6, 5, 2 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
/* 15 */   public static ArmorMaterial ARMORMAT_SPECIAL = EnumHelper.addArmorMaterial("SPECIAL", "SPECIAL", 25, new int[] { 1, 3, 2, 1 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
/* 16 */   public static ArmorMaterial ARMORMAT_VOID = EnumHelper.addArmorMaterial("VOID", "VOID", 10, new int[] { 3, 7, 6, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
/*    */   
/* 18 */   public static final Material MATERIAL_TAINT = new MaterialTaint();
/*    */   
/*    */   public static class MaterialTaint extends Material {
/*    */     private int mobilityFlag;
/*    */     
/*    */     public MaterialTaint() {
/* 24 */       super(MapColor.OBSIDIAN);
/* 25 */       setNoPushMobility();
/*    */     }
/*    */     
/*    */ 
/*    */     public boolean blocksMovement()
/*    */     {
/* 31 */       return true;
/*    */     }
/*    */     
/*    */ 
/*    */     protected Material setNoPushMobility()
/*    */     {
/* 37 */       this.mobilityFlag = 1;
/* 38 */       return this;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     public int getMaterialMobility()
/*    */     {
/* 46 */       return this.mobilityFlag;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\ThaumcraftMaterials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */