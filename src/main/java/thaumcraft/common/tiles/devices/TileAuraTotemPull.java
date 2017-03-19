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
/*    */ public class TileAuraTotemPull
/*    */   extends TileAuraTotem
/*    */ {
/*    */   protected void performMagnet()
/*    */   {
/* 24 */     int x1 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -this.zoneInner, this.zoneInner);
/* 25 */     int z1 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -this.zoneInner, this.zoneInner);
/* 26 */     int x2 = 0;
/* 27 */     int z2 = 0;
/* 28 */     int cc = 0;
/* 29 */     while ((cc < 100) && (x2 >= -this.zoneInner) && (x2 <= this.zoneInner) && (z2 >= -this.zoneInner) && (z2 <= this.zoneInner)) {
/* 30 */       x2 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -(this.zoneInner + this.zoneOuter + 1), this.zoneInner + this.zoneOuter + 1);
/* 31 */       z2 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -(this.zoneInner + this.zoneOuter + 1), this.zoneInner + this.zoneOuter + 1);
/* 32 */       cc++;
/*    */     }
/* 34 */     if (cc >= 100) { return;
/*    */     }
/* 36 */     int x = getPos().getX() + x2 * 16;
/* 37 */     int z = getPos().getZ() + z2 * 16;
/*    */     
/* 39 */     Aspect a = getAspect();
/* 40 */     if (a == null) {
/* 41 */       a = (Aspect)Aspect.getPrimalAspects().get(this.worldObj.rand.nextInt(6));
/*    */     }
/*    */     
/* 44 */     if (AuraHelper.drainAura(getWorld(), new BlockPos(x, 0, z), a, 1)) {
/* 45 */       float tc = 0.0125F * (this.zoneInner + this.zoneOuter + 2) - 0.025F * this.zonePure;
/* 46 */       x = getPos().getX() + x1 * 16;
/* 47 */       z = getPos().getZ() + z1 * 16;
/* 48 */       if (this.worldObj.rand.nextFloat() < tc) {
/* 49 */         AuraHelper.pollute(getWorld(), getPos(), 1, true);
/*    */       } else
/* 51 */         AuraHelper.addAura(getWorld(), new BlockPos(x, 0, z), a, 1);
/*    */     }
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   protected void drawEffect() {
/* 57 */     super.drawEffect();
/* 58 */     float sx = getPos().getX() + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * (5 + this.zoneOuter);
/* 59 */     float sy = getPos().getY() + this.worldObj.rand.nextInt(4) - this.worldObj.rand.nextInt(3) + this.worldObj.rand.nextFloat();
/* 60 */     float sz = getPos().getZ() + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * (5 + this.zoneOuter);
/*    */     
/* 62 */     FXVisSparkle fb = new FXVisSparkle(this.worldObj, sx, sy, sz, getPos().getX() + this.worldObj.rand.nextFloat(), getPos().getY() + this.worldObj.rand.nextFloat(), getPos().getZ() + this.worldObj.rand.nextFloat());
/*    */     
/* 64 */     if (getAspect() != null) {
/* 65 */       Color c = new Color(getAspect().getColor());
/* 66 */       fb.setRBGColorF(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);
/*    */     } else {
/* 68 */       fb.setRBGColorF(0.3F + this.worldObj.rand.nextFloat() * 0.7F, 0.3F + this.worldObj.rand.nextFloat() * 0.7F, 0.3F + this.worldObj.rand.nextFloat() * 0.7F);
/*    */     }
/* 70 */     ParticleEngine.instance.addEffect(this.worldObj, fb);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileAuraTotemPull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */