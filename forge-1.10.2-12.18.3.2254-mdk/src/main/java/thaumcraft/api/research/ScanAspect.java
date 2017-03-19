/*    */ package thaumcraft.api.research;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.aspects.AspectHelper;
/*    */ import thaumcraft.api.aspects.AspectList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScanAspect
/*    */   implements IScanThing
/*    */ {
/*    */   String research;
/*    */   Aspect aspect;
/*    */   
/*    */   public ScanAspect(String research, Aspect aspect)
/*    */   {
/* 25 */     this.research = research;
/* 26 */     this.aspect = aspect;
/*    */   }
/*    */   
/*    */   public boolean checkThing(EntityPlayer player, Object obj)
/*    */   {
/* 31 */     if (obj == null) { return false;
/*    */     }
/* 33 */     AspectList al = null;
/*    */     
/* 35 */     if (((obj instanceof Entity)) && (!(obj instanceof EntityItem))) {
/* 36 */       al = AspectHelper.getEntityAspects((Entity)obj);
/*    */     } else {
/* 38 */       ItemStack is = null;
/* 39 */       if ((obj instanceof ItemStack))
/* 40 */         is = (ItemStack)obj;
/* 41 */       if (((obj instanceof EntityItem)) && (((EntityItem)obj).getEntityItem() != null))
/* 42 */         is = ((EntityItem)obj).getEntityItem();
/* 43 */       if ((obj instanceof BlockPos)) {
/* 44 */         Block b = player.worldObj.getBlockState((BlockPos)obj).getBlock();
/* 45 */         is = new ItemStack(b, 1, b.getMetaFromState(player.worldObj.getBlockState((BlockPos)obj)));
/*    */       }
/*    */       
/* 48 */       if (is != null) {
/* 49 */         al = AspectHelper.getObjectAspects(is);
/*    */       }
/*    */     }
/*    */     
/* 53 */     return (al != null) && (al.getAmount(this.aspect) > 0);
/*    */   }
/*    */   
/*    */   public String getResearchKey()
/*    */   {
/* 58 */     return this.research;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\research\ScanAspect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */