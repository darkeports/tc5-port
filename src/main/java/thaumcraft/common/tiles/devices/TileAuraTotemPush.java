/*    */ package thaumcraft.common.tiles.devices;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.aura.AuraHelper;
/*    */ import thaumcraft.client.fx.ParticleEngine;
/*    */ import thaumcraft.client.fx.particles.FXVisSparkle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileAuraTotemPush
/*    */   extends TileAuraTotem
/*    */ {
/*    */   protected void performMagnet()
/*    */   {
/* 26 */     int x1 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -this.zoneInner, this.zoneInner);
/* 27 */     int z1 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -this.zoneInner, this.zoneInner);
/* 28 */     int x2 = 0;
/* 29 */     int z2 = 0;
/* 30 */     int cc = 0;
/* 31 */     while ((cc < 100) && (x2 >= -this.zoneInner) && (x2 <= this.zoneInner) && (z2 >= -this.zoneInner) && (z2 <= this.zoneInner)) {
/* 32 */       x2 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -(this.zoneInner + this.zoneOuter + 1), this.zoneInner + this.zoneOuter + 1);
/* 33 */       z2 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -(this.zoneInner + this.zoneOuter + 1), this.zoneInner + this.zoneOuter + 1);
/* 34 */       cc++;
/*    */     }
/* 36 */     if (cc >= 100) { return;
/*    */     }
/* 38 */     int x = getPos().getX() + x1 * 16;
/* 39 */     int z = getPos().getZ() + z1 * 16;
/*    */     
/* 41 */     Aspect a = getAspect();
/* 42 */     if (a == null) {
/* 43 */       a = (Aspect)Aspect.getPrimalAspects().get(this.worldObj.rand.nextInt(6));
/*    */     }
/*    */     
/* 46 */     if (AuraHelper.drainAura(getWorld(), new BlockPos(x, 0, z), a, 1)) {
/* 47 */       float tc = 0.0125F * (this.zoneInner + this.zoneOuter + 2) - 0.1F * this.zonePure;
/* 48 */       x = getPos().getX() + x2 * 16;
/* 49 */       z = getPos().getZ() + z2 * 16;
/* 50 */       if (this.worldObj.rand.nextFloat() < tc) {
/* 51 */         AuraHelper.pollute(getWorld(), getPos(), 1, true);
/*    */       } else {
/* 53 */         AuraHelper.addAura(getWorld(), new BlockPos(x, 0, z), a, 1);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   protected void drawEffect()
/*    */   {
/* 61 */     super.drawEffect();
/* 62 */     float sx = getPos().getX() + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * (5 + this.zoneOuter);
/* 63 */     float sy = getPos().getY() + this.worldObj.rand.nextInt(4) - this.worldObj.rand.nextInt(3) + this.worldObj.rand.nextFloat();
/* 64 */     float sz = getPos().getZ() + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * (5 + this.zoneOuter);
/*    */     
/* 66 */     FXVisSparkle fb = new FXVisSparkle(this.worldObj, getPos().getX() + this.worldObj.rand.nextFloat(), getPos().getY() + this.worldObj.rand.nextFloat(), getPos().getZ() + this.worldObj.rand.nextFloat(), sx, sy, sz);
/*    */     
/* 68 */     if (getAspect() != null) {
/* 69 */       Color c = new Color(getAspect().getColor());
/* 70 */       fb.setRBGColorF(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);
/*    */     } else {
/* 72 */       fb.setRBGColorF(0.3F + this.worldObj.rand.nextFloat() * 0.7F, 0.3F + this.worldObj.rand.nextFloat() * 0.7F, 0.3F + this.worldObj.rand.nextFloat() * 0.7F);
/*    */     }
/* 74 */     ParticleEngine.instance.addEffect(this.worldObj, fb);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileAuraTotemPush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */