/*     */ package thaumcraft.client.renderers.entity.mob;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderBiped;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.BossStatus;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.common.entities.monster.boss.EntityCultistLeader;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderCultist extends RenderBiped
/*     */ {
/*  32 */   private static final ResourceLocation skin = new ResourceLocation("thaumcraft", "textures/models/creature/cultist.png");
/*  33 */   private static final ResourceLocation fl = new ResourceLocation("thaumcraft", "textures/misc/wispy.png");
/*     */   
/*     */   public RenderCultist(RenderManager p_i46127_1_)
/*     */   {
/*  37 */     super(p_i46127_1_, new ModelBiped(), 0.5F);
/*  38 */     addLayer(new LayerHeldItem(this));
/*  39 */     LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
/*     */     {
/*     */       protected void func_177177_a()
/*     */       {
/*  43 */         this.field_177189_c = new ModelBiped();
/*  44 */         this.field_177186_d = new ModelBiped();
/*     */       }
/*  46 */     };
/*  47 */     addLayer(layerbipedarmor);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_)
/*     */   {
/*  53 */     return skin;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
/*     */   {
/*  59 */     if ((par1EntityLiving instanceof EntityCultistLeader)) {
/*  60 */       BossStatus.setBossStatus((EntityCultistLeader)par1EntityLiving, false);
/*  61 */       GL11.glScalef(1.25F, 1.25F, 1.25F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void doRender(EntityLiving entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
/*     */   {
/*  69 */     ItemStack itemstack = entity.getHeldItem();
/*     */     
/*  71 */     if (itemstack == null)
/*     */     {
/*  73 */       ((ModelBiped)this.mainModel).heldItemRight = 0;
/*     */     }
/*     */     else
/*     */     {
/*  77 */       ((ModelBiped)this.mainModel).heldItemRight = 1;
/*     */     }
/*     */     
/*     */ 
/*  81 */     GL11.glPushMatrix();
/*     */     
/*  83 */     float bob = 0.0F;
/*  84 */     boolean rit = ((entity instanceof EntityCultistCleric)) && (((EntityCultistCleric)entity).getIsRitualist());
/*  85 */     if (rit) {
/*  86 */       int val = new Random(entity.getEntityId()).nextInt(1000);
/*  87 */       float c = ((EntityCultistCleric)entity).ticksExisted + p_76986_9_ + val;
/*  88 */       bob = MathHelper.sin(c / 9.0F) * 0.1F + 0.21F;
/*  89 */       GL11.glTranslated(0.0D, bob, 0.0D);
/*     */     }
/*     */     
/*  92 */     super.doRender(entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
/*     */     
/*  94 */     if (rit) {
/*  95 */       GL11.glPushMatrix();
/*  96 */       GL11.glDepthMask(false);
/*  97 */       drawFloatyLine(entity.posX, entity.posY + entity.getEyeHeight() * 1.2F, entity.posZ, ((EntityCultistCleric)entity).getHomePosition().getX() + 0.5D, ((EntityCultistCleric)entity).getHomePosition().getY() + 1.5D - bob, ((EntityCultistCleric)entity).getHomePosition().getZ() + 0.5D, p_76986_9_, 1114129, -0.03F, Math.min(((EntityCultistCleric)entity).ticksExisted, 10) / 10.0F, 0.25F);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */       GL11.glDepthMask(true);
/* 104 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/* 107 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void drawFloatyLine(double x, double y, double z, double x2, double y2, double z2, float partialTicks, int color, float speed, float distance, float width) {
/* 111 */     Entity player = Minecraft.getMinecraft().getRenderViewEntity();
/* 112 */     double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
/* 113 */     double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
/* 114 */     double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
/*     */     
/* 116 */     double ePX = x2;
/* 117 */     double ePY = y2;
/* 118 */     double ePZ = z2;
/*     */     
/* 120 */     GL11.glTranslated(-iPX + ePX, -iPY + ePY, -iPZ + ePZ);
/*     */     
/* 122 */     float time = (float)(System.nanoTime() / 30000000L);
/*     */     
/* 124 */     Color co = new Color(color);
/* 125 */     float r = co.getRed() / 255.0F;
/* 126 */     float g = co.getGreen() / 255.0F;
/* 127 */     float b = co.getBlue() / 255.0F;
/*     */     
/*     */ 
/*     */ 
/* 131 */     GL11.glEnable(3042);
/* 132 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 134 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/* 136 */     double ds1x = ePX;
/* 137 */     double ds1y = ePY;
/* 138 */     double ds1z = ePZ;
/* 139 */     double dd1x = x;
/* 140 */     double dd1y = y;
/* 141 */     double dd1z = z;
/* 142 */     double dc1x = (float)(dd1x - ds1x);
/* 143 */     double dc1y = (float)(dd1y - ds1y);
/* 144 */     double dc1z = (float)(dd1z - ds1z);
/*     */     
/* 146 */     bindTexture(fl);
/*     */     
/*     */ 
/* 149 */     tessellator.getWorldRenderer().begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 158 */     double dx2 = 0.0D;
/* 159 */     double dy2 = 0.0D;
/* 160 */     double dz2 = 0.0D;
/* 161 */     double d3 = x - ePX;
/* 162 */     double d4 = y - ePY;
/* 163 */     double d5 = z - ePZ;
/*     */     
/* 165 */     float dist = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
/* 166 */     float blocks = Math.round(dist);
/* 167 */     float length = blocks * 6.0F;
/*     */     
/* 169 */     float f9 = 0.0F;
/* 170 */     float f10 = 1.0F;
/*     */     
/* 172 */     for (int i = 0; i <= length * distance; i++)
/*     */     {
/* 174 */       float f2 = i / length;
/* 175 */       float f2a = i * 1.5F / length;
/* 176 */       f2a = Math.min(0.75F, f2a);
/* 177 */       float f3 = 1.0F - Math.abs(i - length / 2.0F) / (length / 2.0F);
/*     */       
/* 179 */       double dx = dc1x + MathHelper.sin((float)((z % 16.0D + dist * (1.0F - f2) * 6.0F - time % 32767.0F / 5.0F) / 4.0D)) * 0.5F * f3;
/* 180 */       double dy = dc1y + MathHelper.sin((float)((x % 16.0D + dist * (1.0F - f2) * 6.0F - time % 32767.0F / 5.0F) / 3.0D)) * 0.5F * f3;
/* 181 */       double dz = dc1z + MathHelper.sin((float)((y % 16.0D + dist * (1.0F - f2) * 6.0F - time % 32767.0F / 5.0F) / 2.0D)) * 0.5F * f3;
/*     */       
/* 183 */       float f13 = (1.0F - f2) * dist - time * speed;
/* 184 */       tessellator.getWorldRenderer().pos(dx * f2, dy * f2 - width, dz * f2).tex(f13, f10).color(r, g, b, 0.8F).endVertex();
/* 185 */       tessellator.getWorldRenderer().pos(dx * f2, dy * f2 + width, dz * f2).tex(f13, f9).color(r, g, b, 0.8F).endVertex();
/*     */     }
/*     */     
/*     */ 
/* 189 */     tessellator.draw();
/*     */     
/* 191 */     tessellator.getWorldRenderer().begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 192 */     for (i = 0; i <= length * distance; i++)
/*     */     {
/* 194 */       float f2 = i / length;
/* 195 */       float f2a = i * 1.5F / length;
/* 196 */       f2a = Math.min(0.75F, f2a);
/* 197 */       float f3 = 1.0F - Math.abs(i - length / 2.0F) / (length / 2.0F);
/*     */       
/* 199 */       double dx = dc1x + MathHelper.sin((float)((z % 16.0D + dist * (1.0F - f2) * 6.0F - time % 32767.0F / 5.0F) / 4.0D)) * 0.5F * f3;
/* 200 */       double dy = dc1y + MathHelper.sin((float)((x % 16.0D + dist * (1.0F - f2) * 6.0F - time % 32767.0F / 5.0F) / 3.0D)) * 0.5F * f3;
/* 201 */       double dz = dc1z + MathHelper.sin((float)((y % 16.0D + dist * (1.0F - f2) * 6.0F - time % 32767.0F / 5.0F) / 2.0D)) * 0.5F * f3;
/*     */       
/*     */ 
/* 204 */       float f13 = (1.0F - f2) * dist - time * speed;
/*     */       
/* 206 */       tessellator.getWorldRenderer().pos(dx * f2 - width, dy * f2, dz * f2).tex(f13, f10).color(r, g, b, 0.8F).endVertex();
/* 207 */       tessellator.getWorldRenderer().pos(dx * f2 + width, dy * f2, dz * f2).tex(f13, f9).color(r, g, b, 0.8F).endVertex();
/*     */     }
/*     */     
/* 210 */     tessellator.draw();
/*     */     
/*     */ 
/* 213 */     GL11.glDisable(3042);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\RenderCultist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */