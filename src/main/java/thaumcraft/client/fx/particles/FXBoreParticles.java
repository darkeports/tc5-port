/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.ItemModelMesher;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ 
/*     */ @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */ public class FXBoreParticles extends Particle
/*     */ {
/*     */   private Block blockInstance;
/*     */   private Item itemInstance;
/*     */   private int metadata;
/*     */   private int side;
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   
/*     */   public FXBoreParticles(World par1World, double par2, double par4, double par6, double tx, double ty, double tz, Block par14Block, int par15)
/*     */   {
/*  33 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  34 */     this.blockInstance = par14Block;
/*     */     try {
/*  36 */       setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Item.getItemFromBlock(par14Block), par15));
/*     */     } catch (Exception e) {
/*  38 */       setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Item.getItemFromBlock(Blocks.STONE), 0));
/*  39 */       this.particleMaxAge = 0;
/*     */     }
/*     */     
/*  42 */     this.particleGravity = par14Block.blockParticleGravity;
/*  43 */     this.particleRed = (this.particleGreen = this.particleBlue = 0.6F);
/*  44 */     this.particleScale = (this.rand.nextFloat() * 0.3F + 0.4F);
/*  45 */     this.side = par15;
/*     */     
/*  47 */     this.targetX = tx;
/*  48 */     this.targetY = ty;
/*  49 */     this.targetZ = tz;
/*     */     
/*  51 */     double dx = tx - this.posX;
/*  52 */     double dy = ty - this.posY;
/*  53 */     double dz = tz - this.posZ;
/*  54 */     int base = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 3.0F);
/*  55 */     if (base < 1) base = 1;
/*  56 */     this.particleMaxAge = (base / 2 + this.rand.nextInt(base));
/*     */     
/*  58 */     float f3 = 0.01F;
/*  59 */     this.motionX = ((float)this.worldObj.rand.nextGaussian() * f3);
/*  60 */     this.motionY = ((float)this.worldObj.rand.nextGaussian() * f3);
/*  61 */     this.motionZ = ((float)this.worldObj.rand.nextGaussian() * f3);
/*     */     
/*     */ 
/*  64 */     this.particleGravity = 0.2F;
/*  65 */     this.canCollide = true;
/*     */     
/*  67 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  68 */     int visibleDistance = 64;
/*  69 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) visibleDistance = 32;
/*  70 */     if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) { this.particleMaxAge = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public FXBoreParticles(World par1World, double par2, double par4, double par6, double tx, double ty, double tz, Item item, int par15)
/*     */   {
/*  77 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  78 */     this.itemInstance = item;
/*  79 */     setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item, par15));
/*  80 */     this.particleGravity = Blocks.SNOW_LAYER.blockParticleGravity;
/*  81 */     this.particleRed = (this.particleGreen = this.particleBlue = 0.6F);
/*  82 */     this.particleScale = (this.rand.nextFloat() * 0.3F + 0.4F);
/*  83 */     this.side = par15;
/*     */     
/*  85 */     this.targetX = tx;
/*  86 */     this.targetY = ty;
/*  87 */     this.targetZ = tz;
/*     */     
/*  89 */     double dx = tx - this.posX;
/*  90 */     double dy = ty - this.posY;
/*  91 */     double dz = tz - this.posZ;
/*  92 */     int base = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 3.0F);
/*  93 */     if (base < 1) base = 1;
/*  94 */     this.particleMaxAge = (base / 2 + this.rand.nextInt(base));
/*     */     
/*  96 */     float f3 = 0.01F;
/*  97 */     this.motionX = ((float)this.worldObj.rand.nextGaussian() * f3);
/*  98 */     this.motionY = ((float)this.worldObj.rand.nextGaussian() * f3);
/*  99 */     this.motionZ = ((float)this.worldObj.rand.nextGaussian() * f3);
/*     */     
/*     */ 
/* 102 */     this.particleGravity = 0.2F;
/* 103 */     this.canCollide = true;
/*     */     
/* 105 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/* 106 */     int visibleDistance = 64;
/* 107 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) visibleDistance = 32;
/* 108 */     if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) { this.particleMaxAge = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 114 */     this.prevPosX = this.posX;
/* 115 */     this.prevPosY = this.posY;
/* 116 */     this.prevPosZ = this.posZ;
/*     */     
/*     */ 
/*     */ 
/* 120 */     if ((this.particleAge++ >= this.particleMaxAge) || ((MathHelper.floor_double(this.posX) == MathHelper.floor_double(this.targetX)) && (MathHelper.floor_double(this.posY) == MathHelper.floor_double(this.targetY)) && (MathHelper.floor_double(this.posZ) == MathHelper.floor_double(this.targetZ))))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 125 */       setExpired();
/* 126 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */     //TODO:Remove this?
/* 131 */     if (this.canCollide) pushOutOfBlocks(this.posX, this.posY, this.posZ);
/* 132 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 134 */     this.motionX *= 0.985D;
/* 135 */     this.motionY *= 0.985D;
/* 136 */     this.motionZ *= 0.985D;
/*     */     
/* 138 */     double dx = this.targetX - this.posX;
/* 139 */     double dy = this.targetY - this.posY;
/* 140 */     double dz = this.targetZ - this.posZ;
/* 141 */     double d13 = 0.3D;
/* 142 */     double d11 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
/*     */     
/* 144 */     if (d11 < 4.0D) {
/* 145 */       this.particleScale *= 0.9F;
/* 146 */       d13 = 0.6D;
/*     */     }
/*     */     
/* 149 */     dx /= d11;
/* 150 */     dy /= d11;
/* 151 */     dz /= d11;
/*     */     
/* 153 */     this.motionX += dx * d13;
/* 154 */     this.motionY += dy * d13;
/* 155 */     this.motionZ += dz * d13;
/*     */     
/* 157 */     this.motionX = MathHelper.clamp_float((float)this.motionX, -0.35F, 0.35F);
/* 158 */     this.motionY = MathHelper.clamp_float((float)this.motionY, -0.35F, 0.35F);
/* 159 */     this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.35F, 0.35F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/* 166 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public FXBoreParticles getObjectColor(BlockPos pos)
/*     */   {
/* 172 */     if ((this.blockInstance != null) && (this.worldObj.getBlockState(pos) == this.blockInstance)) {
/* 173 */       if ((this.blockInstance == Blocks.GRASS) && (this.side != 1))
/*     */       {
/* 175 */         return this;
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
					//TODO:???
/* 181 */         int var4 = this.blockInstance.getMapColor(this.blockInstance.getBlockState().getBaseState()).colorIndex;   //(this.worldObj, pos);
/* 182 */         this.particleRed *= (var4 >> 16 & 0xFF) / 255.0F;
/* 183 */         this.particleGreen *= (var4 >> 8 & 0xFF) / 255.0F;
/* 184 */         this.particleBlue *= (var4 & 0xFF) / 255.0F;
/*     */       } catch (Exception e) {}
/* 186 */       return this;
/*     */     }
/*     */     try
/*     */     {
				//TODO:???
/* 190 */       int var4 = 16777215;//this.itemInstance.getColorFromItemStack(new net.minecraft.item.ItemStack(this.itemInstance, 1, this.metadata), 0);
/* 191 */       this.particleRed *= (var4 >> 16 & 0xFF) / 255.0F;
/* 192 */       this.particleGreen *= (var4 >> 8 & 0xFF) / 255.0F;
/* 193 */       this.particleBlue *= (var4 & 0xFF) / 255.0F;
/*     */     } catch (Exception e) {}
/* 195 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
/*     */   {
/* 204 */     float f6 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 205 */     float f7 = f6 + 0.015609375F;
/* 206 */     float f8 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 207 */     float f9 = f8 + 0.015609375F;
/* 208 */     float f10 = 0.1F * this.particleScale;
/*     */     
/* 210 */     if (this.particleTexture != null)
/*     */     {
/* 212 */       f6 = this.particleTexture.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
/* 213 */       f7 = this.particleTexture.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
/* 214 */       f8 = this.particleTexture.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
/* 215 */       f9 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
/*     */     }
/* 217 */     int i = getBrightnessForRender(p_180434_3_);
/* 218 */     int j = i >> 16 & 0xFFFF;
/* 219 */     int k = i & 0xFFFF;
/* 220 */     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_180434_3_ - interpPosX);
/* 221 */     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_180434_3_ - interpPosY);
/* 222 */     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_180434_3_ - interpPosZ);
/* 223 */     p_180434_1_.pos(f11 - p_180434_4_ * f10 - p_180434_7_ * f10, f12 - p_180434_5_ * f10, f13 - p_180434_6_ * f10 - p_180434_8_ * f10).tex(f6, f9).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */     
/* 225 */     p_180434_1_.pos(f11 - p_180434_4_ * f10 + p_180434_7_ * f10, f12 + p_180434_5_ * f10, f13 - p_180434_6_ * f10 + p_180434_8_ * f10).tex(f6, f8).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */     
/* 227 */     p_180434_1_.pos(f11 + p_180434_4_ * f10 + p_180434_7_ * f10, f12 + p_180434_5_ * f10, f13 + p_180434_6_ * f10 + p_180434_8_ * f10).tex(f7, f8).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */     
/* 229 */     p_180434_1_.pos(f11 + p_180434_4_ * f10 - p_180434_7_ * f10, f12 - p_180434_5_ * f10, f13 + p_180434_6_ * f10 - p_180434_8_ * f10).tex(f7, f9).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXBoreParticles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */