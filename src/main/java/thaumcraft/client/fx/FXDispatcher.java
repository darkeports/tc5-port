/*      */ package thaumcraft.client.fx;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.particle.ParticleLava.Factory;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.fml.client.FMLClientHandler;
/*      */ import thaumcraft.client.fx.beams.FXBeamBore;
/*      */ import thaumcraft.client.fx.beams.FXBeamWand;
/*      */ import thaumcraft.client.fx.beams.FXBolt;
/*      */ import thaumcraft.client.fx.other.FXBlockWard;
/*      */ import thaumcraft.client.fx.particles.FXBlockRunes;
/*      */ import thaumcraft.client.fx.particles.FXBoreParticles;
/*      */ import thaumcraft.client.fx.particles.FXBoreSparkle;
/*      */ import thaumcraft.client.fx.particles.FXBreakingFade;
/*      */ import thaumcraft.client.fx.particles.FXBubble;
/*      */ import thaumcraft.client.fx.particles.FXBurst;
/*      */ import thaumcraft.client.fx.particles.FXEssentiaTrail2;
/*      */ import thaumcraft.client.fx.particles.FXGeneric;
/*      */ import thaumcraft.client.fx.particles.FXPlane;
/*      */ import thaumcraft.client.fx.particles.FXSmokeSpiral;
/*      */ import thaumcraft.client.fx.particles.FXSpark;
/*      */ import thaumcraft.client.fx.particles.FXSparkle;
/*      */ import thaumcraft.client.fx.particles.FXSwarmRunes;
/*      */ import thaumcraft.client.fx.particles.FXVent;
/*      */ import thaumcraft.client.fx.particles.FXVisSparkle;
/*      */ import thaumcraft.client.fx.particles.FXWisp;
/*      */ import thaumcraft.client.fx.particles.FXWispyOrb;
/*      */ import thaumcraft.common.tiles.crafting.TileCrucible;
/*      */ 
/*      */ public class FXDispatcher
/*      */ {
/*      */   public World getWorld()
/*      */   {
/*   50 */     return FMLClientHandler.instance().getClient().theWorld;
/*      */   }
/*      */   
/*      */   public int particleCount(int base) {
/*   54 */     return FMLClientHandler.instance().getClient().gameSettings.particleSetting == 1 ? base * 1 : FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2 ? base / 2 : base * 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void drawGenericParticles(double x, double y, double z, double x2, double y2, double z2, float r, float g, float b, float alpha, boolean loop, int start, int num, int inc, int age, int delay, float scale, float rot, int layer)
/*      */   {
/*   65 */     FXGeneric fb = new FXGeneric(getWorld(), x, y, z, x2, y2, z2);
/*   66 */     fb.setMaxAge(age, delay);
/*   67 */     fb.setRBGColorF(r, g, b);
/*   68 */     fb.setAlphaF(alpha);
/*   69 */     fb.setLoop(loop);
/*   70 */     fb.setParticles(start, num, inc);
/*   71 */     fb.setScale(scale);
/*   72 */     fb.setLayer(layer);
/*   73 */     fb.setRotationSpeed(rot);
/*   74 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawSwarmRunes(double x, double y, double z, Entity target)
/*      */   {
/*   79 */     FXSwarmRunes fx = new FXSwarmRunes(getWorld(), x, y, z, target, 0.7F + getWorld().rand.nextFloat() * 0.2F, 0.3F + getWorld().rand.nextFloat() * 0.1F, 0.0F, 0.22F, 15.0F, 0.08F);
/*      */     
/*      */ 
/*   82 */     ParticleEngine.instance.addEffect(getWorld(), fx);
/*      */   }
/*      */   
/*      */   public void drawLevitatorParticles(double x, double y, double z, double x2, double y2, double z2) {
/*   86 */     FXGeneric fb = new FXGeneric(getWorld(), x, y, z, x2, y2, z2);
/*   87 */     fb.setMaxAge(400 + getWorld().rand.nextInt(100), 0);
/*   88 */     fb.setAlphaF(0.3F, 0.0F);
/*   89 */     fb.setParticles(24, 1, 1);
/*   90 */     fb.setGridSize(8);
/*   91 */     fb.setScale(2.0F, 5.0F);
/*   92 */     fb.setLayer(2);
/*   93 */     fb.setSlowDown(1.0D);
/*   94 */     fb.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -1.0F : 1.0F);
/*   95 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawGolemFlyParticles(double x, double y, double z, double x2, double y2, double z2) {
/*      */     try {
/*  100 */       FXGeneric fb = new FXGeneric(getWorld(), x, y, z, x2, y2, z2);
/*  101 */       fb.setMaxAge(40 + getWorld().rand.nextInt(20), 0);
/*  102 */       fb.setAlphaF(0.2F, 0.0F);
/*  103 */       fb.setParticles(24, 1, 1);
/*  104 */       fb.setGridSize(8);
/*  105 */       fb.setScale(2.0F, 8.0F);
/*  106 */       fb.setLayer(2);
/*  107 */       fb.setSlowDown(1.0D);
/*  108 */       fb.setWind(0.001D);
/*  109 */       fb.noClip = false;
/*  110 */       fb.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -1.0F : 1.0F);
/*  111 */       ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */     }
/*      */     catch (Exception e) {}
/*      */   }
/*      */   
/*      */   public void drawPollutionParticles(BlockPos p) {
/*  117 */     float x = p.getX() + 0.2F + getWorld().rand.nextFloat() * 0.6F;
/*  118 */     float y = p.getY() + 0.2F + getWorld().rand.nextFloat() * 0.6F;
/*  119 */     float z = p.getZ() + 0.2F + getWorld().rand.nextFloat() * 0.6F;
/*  120 */     FXGeneric fb = new FXGeneric(getWorld(), x, y, z, (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.005D, 0.02D, (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.005D);
/*      */     
/*      */ 
/*      */ 
/*  124 */     fb.setMaxAge(400 + getWorld().rand.nextInt(100), 0);
/*  125 */     fb.setRBGColorF(1.0F, 0.3F, 0.9F);
/*  126 */     fb.setAlphaF(0.5F, 0.0F);
/*  127 */     fb.setParticles(24, 1, 1);
/*  128 */     fb.setGridSize(8);
/*  129 */     fb.setScale(2.0F, 5.0F);
/*  130 */     fb.setLayer(3);
/*  131 */     fb.setSlowDown(1.0D);
/*  132 */     fb.setWind(0.001D);
/*  133 */     fb.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -1.0F : 1.0F);
/*  134 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawTaintCloudParticles(double x, double y, double z) {
/*  138 */     FXGeneric fb = new FXGeneric(getWorld(), x, y, z, 0.0D, -getWorld().rand.nextFloat() * 0.15D, 0.0D);
/*  139 */     fb.setMaxAge(32 + getWorld().rand.nextInt(4), 0);
/*  140 */     fb.setRBGColorF(1.0F, 0.5F, 1.0F);
/*  141 */     fb.setAlphaF(0.3F);
/*  142 */     fb.setLoop(false);
/*  143 */     fb.setParticles(176, 32, 1);
/*  144 */     fb.setScale(25.0F + 15.0F * getWorld().rand.nextFloat());
/*  145 */     fb.setLayer(3);
/*  146 */     fb.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -1.0F - getWorld().rand.nextFloat() : 1.0F + getWorld().rand.nextFloat());
/*  147 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawBlockMistParticles(BlockPos p, int c)
/*      */   {
/*  152 */     Block bs = getWorld().getBlockState(p).getBlock();
/*  153 */     Color color = new Color(c);
/*  154 */     for (int a = 0; a < particleCount(4); a++) {
/*  155 */       double x = p.getX() + bs.getBlockBoundsMinX() + getWorld().rand.nextFloat() * (bs.getBlockBoundsMaxX() - bs.getBlockBoundsMinX());
/*  156 */       double y = p.getY() + bs.getBlockBoundsMinY() + getWorld().rand.nextFloat() * (bs.getBlockBoundsMaxY() - bs.getBlockBoundsMinY());
/*  157 */       double z = p.getZ() + bs.getBlockBoundsMinZ() + getWorld().rand.nextFloat() * (bs.getBlockBoundsMaxZ() - bs.getBlockBoundsMinZ());
/*  158 */       FXGeneric fb = new FXGeneric(getWorld(), x, y, z, getWorld().rand.nextGaussian() * 0.005D, 0.005D, getWorld().rand.nextGaussian() * 0.005D);
/*      */       
/*      */ 
/*      */ 
/*  162 */       fb.setMaxAge(400 + getWorld().rand.nextInt(100), 0);
/*  163 */       fb.setRBGColorF(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/*  164 */       fb.setAlphaF(1.0F, 0.0F);
/*  165 */       fb.setParticles(24, 1, 1);
/*  166 */       fb.setGridSize(8);
/*  167 */       fb.setScale(2.0F, 5.0F);
/*  168 */       fb.setLayer(2);
/*  169 */       fb.setSlowDown(1.0D);
/*  170 */       fb.setWind(0.001D);
/*  171 */       fb.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -1.0F : 1.0F);
/*  172 */       ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */     }
/*      */   }
/*      */   
/*      */   public void drawWispyMotesOnBlock(BlockPos pp, int age) {
/*  177 */     FXWispyOrb fb = new FXWispyOrb(getWorld(), pp.getX() + getWorld().rand.nextFloat(), pp.getY(), pp.getZ() + getWorld().rand.nextFloat(), 0.0D, 0.0D, 0.0D, age);
/*  178 */     fb.setAlphaF(0.5F);
/*  179 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawBlockMistParticlesFlat(BlockPos p, int c) {
/*  183 */     Block bs = getWorld().getBlockState(p).getBlock();
/*  184 */     Color color = new Color(c);
/*  185 */     for (int a = 0; a < particleCount(3); a++) {
/*  186 */       double x = p.getX() + getWorld().rand.nextFloat();
/*  187 */       double y = p.getY() + getWorld().rand.nextFloat() * 0.125F;
/*  188 */       double z = p.getZ() + getWorld().rand.nextFloat();
/*  189 */       FXGeneric fb = new FXGeneric(getWorld(), x, y, z, (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.005D, 0.005D, (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.005D);
/*      */       
/*      */ 
/*      */ 
/*  193 */       fb.setMaxAge(400 + getWorld().rand.nextInt(100), 0);
/*  194 */       fb.setRBGColorF(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/*  195 */       fb.setAlphaF(1.0F, 0.0F);
/*  196 */       fb.setParticles(24, 1, 1);
/*  197 */       fb.setGridSize(8);
/*  198 */       fb.setScale(2.0F, 5.0F);
/*  199 */       fb.setLayer(2);
/*  200 */       fb.setSlowDown(1.0D);
/*  201 */       fb.setWind(0.001D);
/*  202 */       fb.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -1.0F : 1.0F);
/*  203 */       ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */     }
/*      */   }
/*      */   
/*      */   public void crucibleBoil(BlockPos pos, TileCrucible tile, int j) {
/*  208 */     for (int a = 0; a < particleCount(1); a++) {
/*  209 */       FXBubble fb = new FXBubble(getWorld(), pos.getX() + 0.2F + getWorld().rand.nextFloat() * 0.6F, pos.getY() + 0.1F + tile.getFluidHeight(), pos.getZ() + 0.2F + getWorld().rand.nextFloat() * 0.6F, 0.0D, 0.0D, 0.0D, 3);
/*      */       
/*      */ 
/*      */ 
/*  213 */       if (tile.aspects.size() == 0) {
/*  214 */         fb.setRBGColorF(1.0F, 1.0F, 1.0F);
/*      */       } else {
/*  216 */         Color color = new Color(tile.aspects.getAspects()[getWorld().rand.nextInt(tile.aspects.getAspects().length)].getColor());
/*      */         
/*      */ 
/*      */ 
/*  220 */         fb.setRBGColorF(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/*      */       }
/*      */       
/*  223 */       fb.bubblespeed = (0.003D * j);
/*  224 */       ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void drawBamf(BlockPos p, boolean sound, boolean flair, boolean upwards)
/*      */   {
/*  231 */     drawBamf(p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D, sound, flair, upwards);
/*      */   }
/*      */   
/*      */   public void drawBamf(BlockPos p, float r, float g, float b, boolean sound, boolean flair, boolean upwards) {
/*  235 */     drawBamf(p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D, r, g, b, sound, flair, upwards);
/*      */   }
/*      */   
/*      */   public void drawBamf(BlockPos p, int color, boolean sound, boolean flair, boolean upwards) {
/*  239 */     drawBamf(p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D, color, sound, flair, upwards);
/*      */   }
/*      */   
/*      */   public void drawBamf(double x, double y, double z, int color, boolean sound, boolean flair, boolean upwards) {
/*  243 */     Color c = new Color(color);
/*  244 */     float r = c.getRed() / 255.0F;
/*  245 */     float g = c.getGreen() / 255.0F;
/*  246 */     float b = c.getBlue() / 255.0F;
/*  247 */     drawBamf(x, y, z, r, g, b, sound, flair, upwards);
/*      */   }
/*      */   
/*      */   public void drawBamf(double x, double y, double z, boolean sound, boolean flair, boolean upwards) {
/*  251 */     drawBamf(x, y, z, 0.5F, 0.1F, 0.6F, sound, flair, upwards);
/*      */   }
/*      */   
/*      */ 
/*      */   public void drawBamf(double x, double y, double z, float r, float g, float b, boolean sound, boolean flair, boolean upwards)
/*      */   {
/*  257 */     if (sound) {
/*  258 */       getWorld().playSound(x, y, z, "thaumcraft:poof", 0.4F, 1.0F + (float)getWorld().rand.nextGaussian() * 0.05F, false);
/*      */     }
/*      */     
/*  261 */     for (int a = 0; a < particleCount(3) + getWorld().rand.nextInt(3) + 1; a++) {
/*  262 */       double vx = (0.05F + getWorld().rand.nextFloat() * 0.05F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  263 */       double vy = (0.05F + getWorld().rand.nextFloat() * 0.05F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  264 */       double vz = (0.05F + getWorld().rand.nextFloat() * 0.05F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  265 */       if (upwards) {
/*  266 */         vy = Math.abs(vy) * (a + 2);
/*      */       }
/*      */       
/*  269 */       FXGeneric fb2 = new FXGeneric(getWorld(), x + vx * 2.0D, y + (upwards ? -0.5F + getWorld().rand.nextFloat() * 0.1F : vy * 2.0D), z + vz * 2.0D, vx / 2.0D, vy / 2.0D, vz / 2.0D);
/*  270 */       fb2.noClip = false;
/*  271 */       fb2.setMaxAge(20 + getWorld().rand.nextInt(15), 0);
/*  272 */       fb2.setRBGColorF(MathHelper.clamp_float(r * (1.0F + (float)getWorld().rand.nextGaussian() * 0.1F), 0.0F, 1.0F), MathHelper.clamp_float(g * (1.0F + (float)getWorld().rand.nextGaussian() * 0.1F), 0.0F, 1.0F), MathHelper.clamp_float(b * (1.0F + (float)getWorld().rand.nextGaussian() * 0.1F), 0.0F, 1.0F));
/*      */       
/*      */ 
/*      */ 
/*  276 */       fb2.setAlphaF(1.0F, 0.5F);
/*  277 */       fb2.setParticles(59, 5, 1);
/*  278 */       fb2.setGridSize(8);
/*  279 */       fb2.setScale(3.0F, 4.0F + getWorld().rand.nextFloat() * 3.0F);
/*  280 */       fb2.setLayer(3);
/*  281 */       fb2.setSlowDown(0.7D);
/*  282 */       fb2.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -1.0F : 1.0F);
/*  283 */       ParticleEngine.instance.addEffect(getWorld(), fb2);
/*      */     }
/*      */     
/*  286 */     if (flair)
/*      */     {
/*  288 */       for (int a = 0; a < particleCount(3) + getWorld().rand.nextInt(3); a++) {
/*  289 */         double vx = (0.1F + getWorld().rand.nextFloat() * 0.05F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  290 */         double vy = (0.1F + getWorld().rand.nextFloat() * 0.05F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  291 */         double vz = (0.1F + getWorld().rand.nextFloat() * 0.05F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*      */         
/*  293 */         FXWispyOrb fb = new FXWispyOrb(getWorld(), x + vx * 2.0D, y + vy * 2.0D, z + vz * 2.0D, vx, vy, vz, 15 + getWorld().rand.nextInt(10));
/*      */         
/*      */ 
/*  296 */         fb.setAlphaF(0.5F);
/*  297 */         ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */       }
/*      */       
/*      */ 
/*  301 */       FXGeneric fb = new FXGeneric(getWorld(), x, y, z, 0.0D, 0.0D, 0.0D);
/*  302 */       fb.setMaxAge(10, 0);
/*  303 */       fb.setRBGColorF(1.0F, 0.9F, 1.0F);
/*  304 */       fb.setAlphaF(1.0F, 0.0F);
/*  305 */       fb.setParticles(37, 1, 1);
/*  306 */       fb.setGridSize(8);
/*  307 */       fb.setScale(10.0F + getWorld().rand.nextFloat() * 2.0F, 0.0F);
/*  308 */       fb.setLayer(2);
/*  309 */       fb.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, (float)getWorld().rand.nextGaussian());
/*  310 */       ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */     }
/*      */     
/*      */ 
/*  314 */     for (int a = 0; a < particleCount(flair ? 1 : 0) + getWorld().rand.nextInt(3); a++) {
/*  315 */       double vx = (0.0025F + getWorld().rand.nextFloat() * 0.005F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  316 */       double vy = (0.0025F + getWorld().rand.nextFloat() * 0.005F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  317 */       double vz = (0.0025F + getWorld().rand.nextFloat() * 0.005F) * (getWorld().rand.nextBoolean() ? -1 : 1);
/*  318 */       if (upwards) vy = Math.abs(vy);
/*  319 */       FXGeneric fb2 = new FXGeneric(getWorld(), x + vx * 5.0D, y + vy * 5.0D, z + vz * 5.0D, vx, vy, vz);
/*      */       
/*  321 */       if ((a > 0) && (getWorld().rand.nextBoolean())) {
/*  322 */         fb2.setAngles(90.0F * (float)getWorld().rand.nextGaussian(), 90.0F * (float)getWorld().rand.nextGaussian());
/*      */       }
/*  324 */       fb2.noClip = false;
/*  325 */       fb2.setMaxAge(75 + getWorld().rand.nextInt(75 + 20 * a), 0);
/*  326 */       fb2.setRBGColorF((0.9F + getWorld().rand.nextFloat() * 0.1F + r) / 2.0F, (0.1F + g) / 2.0F, (0.5F + getWorld().rand.nextFloat() * 0.1F + b) / 2.0F, 0.1F + getWorld().rand.nextFloat() * 0.1F, 0.0F, 0.5F + getWorld().rand.nextFloat() * 0.1F);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  331 */       fb2.setAlphaF(0.75F, 0.0F);
/*  332 */       fb2.setParticles(28 + getWorld().rand.nextInt(4), 1, 1);
/*  333 */       fb2.setGridSize(8);
/*  334 */       fb2.setScale(5.0F, 10.0F + getWorld().rand.nextFloat() * 4.0F);
/*  335 */       fb2.setLayer(2);
/*  336 */       fb2.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -3.0F - getWorld().rand.nextFloat() * 3.0F : 3.0F + getWorld().rand.nextFloat() * 3.0F);
/*      */       
/*  338 */       ParticleEngine.instance.addEffect(getWorld(), fb2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void pechsCurseTick(double posX, double posY, double posZ) {
/*  343 */     FXGeneric fb2 = new FXGeneric(getWorld(), posX, posY, posZ, 0.0D, 0.0D, 0.0D);
/*  344 */     fb2.setAngles(90.0F * (float)getWorld().rand.nextGaussian(), 90.0F * (float)getWorld().rand.nextGaussian());
/*  345 */     fb2.noClip = false;
/*  346 */     fb2.setMaxAge(50 + getWorld().rand.nextInt(50), 0);
/*  347 */     fb2.setRBGColorF(0.9F, 0.1F, 0.5F, 0.1F + getWorld().rand.nextFloat() * 0.1F, 0.0F, 0.5F + getWorld().rand.nextFloat() * 0.1F);
/*  348 */     fb2.setAlphaF(0.75F, 0.0F);
/*  349 */     fb2.setParticles(28 + getWorld().rand.nextInt(4), 1, 1);
/*  350 */     fb2.setGridSize(8);
/*  351 */     fb2.setScale(3.0F, 5.0F + getWorld().rand.nextFloat() * 2.0F);
/*  352 */     fb2.setLayer(2);
/*  353 */     fb2.setRotationSpeed(getWorld().rand.nextFloat() * 360.0F, getWorld().rand.nextBoolean() ? -3.0F - getWorld().rand.nextFloat() * 3.0F : 3.0F + getWorld().rand.nextFloat() * 3.0F);
/*      */     
/*  355 */     ParticleEngine.instance.addEffect(getWorld(), fb2);
/*      */     
/*  357 */     FXWispyOrb fb = new FXWispyOrb(getWorld(), posX, posY, posZ, 0.0D, 0.0D, 0.0D, 10 + getWorld().rand.nextInt(10));
/*  358 */     fb.setAlphaF(0.75F);
/*  359 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void scanHighlight(float x, float y, float z) {
/*  363 */     Color color = new Color(16695840);
/*  364 */     float r = color.getRed() / 255.0F;
/*  365 */     float g = color.getGreen() / 255.0F;
/*  366 */     float b = color.getBlue() / 255.0F;
/*      */     
/*  368 */     drawGenericParticles(x, y, z, 0.0D, getWorld().rand.nextFloat() * 0.02D, 0.0D, r - 0.2F + getWorld().rand.nextFloat() * 0.4F, g - 0.2F + getWorld().rand.nextFloat() * 0.4F, b - 0.2F + getWorld().rand.nextFloat() * 0.4F, 0.9F, false, 15, 1, 1, 5 + getWorld().rand.nextInt(8), getWorld().rand.nextInt(10), 0.6F + getWorld().rand.nextFloat() * 0.4F, 0.0F, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void sparkle(float x, float y, float z, int color)
/*      */   {
/*  380 */     if (getWorld().rand.nextInt(6) < particleCount(2)) {
/*  381 */       FXSparkle fx = new FXSparkle(getWorld(), x, y, z, 1.5F, color, 6);
/*  382 */       fx.noClip = true;
/*  383 */       ParticleEngine.instance.addEffect(getWorld(), fx);
/*      */     }
/*      */   }
/*      */   
/*      */   public void ssparkle(float x, float y, float z, int color) {
/*  388 */     if (getWorld().rand.nextInt(6) < particleCount(2)) {
/*  389 */       FXSparkle fx = new FXSparkle(getWorld(), x, y, z, 0.5F, color, 1);
/*  390 */       fx.noClip = true;
/*  391 */       ParticleEngine.instance.addEffect(getWorld(), fx);
/*      */     }
/*      */   }
/*      */   
/*      */   public void sparkle(float x, float y, float z, float size, int color, float gravity) {
/*  396 */     if ((getWorld() != null) && (getWorld().rand.nextInt(6) < particleCount(2))) {
/*  397 */       FXSparkle fx = new FXSparkle(getWorld(), x, y, z, size, color, 6);
/*  398 */       fx.noClip = true;
/*  399 */       fx.setGravity(gravity);
/*  400 */       ParticleEngine.instance.addEffect(getWorld(), fx);
/*      */     }
/*      */   }
/*      */   
/*      */   public void visSparkle(int x, int y, int z, int x2, int y2, int z2, int color) {
/*  405 */     FXVisSparkle fb = new FXVisSparkle(getWorld(), x + getWorld().rand.nextFloat(), y + getWorld().rand.nextFloat(), z + getWorld().rand.nextFloat(), x2 + 0.4D + getWorld().rand.nextFloat() * 0.2F, y2 + 0.4D + getWorld().rand.nextFloat() * 0.2F, z2 + 0.4D + getWorld().rand.nextFloat() * 0.2F);
/*      */     
/*      */ 
/*  408 */     Color c = new Color(color);
/*  409 */     fb.setRBGColorF(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);
/*  410 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void crucibleBubble(float x, float y, float z, float cr, float cg, float cb)
/*      */   {
/*  415 */     FXBubble fb = new FXBubble(getWorld(), x, y, z, 0.0D, 0.0D, 0.0D, 1);
/*  416 */     fb.setRBGColorF(cr, cg, cb);
/*  417 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void crucibleFroth(float x, float y, float z) {
/*  421 */     FXBubble fb = new FXBubble(getWorld(), x, y, z, 0.0D, 0.0D, 0.0D, -4);
/*  422 */     fb.setRBGColorF(0.5F, 0.5F, 0.7F);
/*  423 */     fb.setFroth();
/*  424 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void crucibleFrothDown(float x, float y, float z) {
/*  428 */     FXBubble fb = new FXBubble(getWorld(), x, y, z, 0.0D, 0.0D, 0.0D, -4);
/*  429 */     fb.setRBGColorF(0.5F, 0.5F, 0.7F);
/*  430 */     fb.setFroth2();
/*  431 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void splooshFX(Entity e)
/*      */   {
/*  463 */     float f = getWorld().rand.nextFloat() * 3.1415927F * 2.0F;
/*  464 */     float f1 = getWorld().rand.nextFloat() * 0.5F + 0.5F;
/*  465 */     float f2 = MathHelper.sin(f) * 2.0F * 0.5F * f1;
/*  466 */     float f3 = MathHelper.cos(f) * 2.0F * 0.5F * f1;
/*  467 */     FXBreakingFade fx = new FXBreakingFade(getWorld(), e.posX + f2, e.posY + getWorld().rand.nextFloat() * e.height, e.posZ + f3, Items.slime_ball, 0);
/*      */     
/*      */ 
/*  470 */     if (getWorld().rand.nextBoolean()) {
/*  471 */       fx.setRBGColorF(0.6F, 0.0F, 0.3F);
/*  472 */       fx.setAlphaF(0.4F);
/*      */     } else {
/*  474 */       fx.setRBGColorF(0.3F, 0.0F, 0.3F);
/*  475 */       fx.setAlphaF(0.6F);
/*      */     }
/*      */     
/*  478 */     fx.setParticleMaxAge((int)(66.0F / (getWorld().rand.nextFloat() * 0.9F + 0.1F)));
/*  479 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
/*      */   }
/*      */   
/*      */   public void fluxRainSplashFX(BlockPos pos) {
/*  483 */     float f = getWorld().rand.nextFloat() * 3.1415927F * 2.0F;
/*  484 */     float f1 = getWorld().rand.nextFloat() * 0.5F + 0.5F;
/*  485 */     float f2 = MathHelper.sin(f) * 2.0F * 0.5F * f1;
/*  486 */     float f3 = MathHelper.cos(f) * 2.0F * 0.5F * f1;
/*  487 */     FXBreakingFade fx = new FXBreakingFade(getWorld(), pos.getX() + f2 + 0.5D, pos.getY(), pos.getZ() + f3 + 0.5D, Items.slime_ball, 0);
/*      */     
/*      */ 
/*      */ 
/*  491 */     fx.setRBGColorF(1.0F, 0.2F, 1.0F);
/*  492 */     fx.setAlphaF(0.6F);
/*      */     
/*  494 */     fx.setParticleMaxAge((int)(44.0F / (getWorld().rand.nextFloat() * 0.9F + 0.1F)));
/*  495 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
/*      */   }
/*      */   
/*      */   public void taintsplosionFX(Entity e) {
/*  499 */     FXBreakingFade fx = new FXBreakingFade(getWorld(), e.posX, e.posY + getWorld().rand.nextFloat() * e.height, e.posZ, Items.slime_ball);
/*  500 */     if (getWorld().rand.nextBoolean()) {
/*  501 */       fx.setRBGColorF(0.6F, 0.0F, 0.3F);
/*  502 */       fx.setAlphaF(0.4F);
/*      */     } else {
/*  504 */       fx.setRBGColorF(0.3F, 0.0F, 0.3F);
/*  505 */       fx.setAlphaF(0.6F);
/*      */     }
/*  507 */     fx.motionX = ((float)(Math.random() * 2.0D - 1.0D));
/*  508 */     fx.motionY = ((float)(Math.random() * 2.0D - 1.0D));
/*  509 */     fx.motionZ = ((float)(Math.random() * 2.0D - 1.0D));
/*  510 */     float f = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
/*  511 */     float f1 = MathHelper.sqrt_double(fx.motionX * fx.motionX + fx.motionY * fx.motionY + fx.motionZ * fx.motionZ);
/*  512 */     fx.motionX = (fx.motionX / f1 * f * 0.9640000000596046D);
/*  513 */     fx.motionY = (fx.motionY / f1 * f * 0.9640000000596046D + 0.10000000149011612D);
/*  514 */     fx.motionZ = (fx.motionZ / f1 * f * 0.9640000000596046D);
/*  515 */     fx.setParticleMaxAge((int)(66.0F / (getWorld().rand.nextFloat() * 0.9F + 0.1F)));
/*  516 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
/*      */   }
/*      */   
/*      */   public void tentacleAriseFX(Entity e)
/*      */   {
/*  521 */     for (int j = 0; j < 2.0F * e.height; j++) {
/*  522 */       float f = getWorld().rand.nextFloat() * 3.1415927F * e.height;
/*  523 */       float f1 = getWorld().rand.nextFloat() * 0.5F + 0.5F;
/*  524 */       float f2 = MathHelper.sin(f) * e.height * 0.25F * f1;
/*  525 */       float f3 = MathHelper.cos(f) * e.height * 0.25F * f1;
/*  526 */       FXBreakingFade fx = new FXBreakingFade(getWorld(), e.posX + f2, e.posY, e.posZ + f3, Items.slime_ball);
/*      */       
/*  528 */       fx.setRBGColorF(0.4F, 0.0F, 0.4F);
/*  529 */       fx.setAlphaF(0.5F);
/*  530 */       fx.setParticleMaxAge((int)(66.0F / (getWorld().rand.nextFloat() * 0.9F + 0.1F)));
/*  531 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
/*      */       
/*      */ 
/*  534 */       if (!getWorld().isAirBlock(e.getPosition().down())) {
/*  535 */         f = getWorld().rand.nextFloat() * 3.1415927F * e.height;
/*  536 */         f1 = getWorld().rand.nextFloat() * 0.5F + 0.5F;
/*  537 */         f2 = MathHelper.sin(f) * e.height * 0.25F * f1;
/*  538 */         f3 = MathHelper.cos(f) * e.height * 0.25F * f1;
/*      */         
/*  540 */         getWorld().spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.posX + f2, e.posY, e.posZ + f3, 0.0D, 0.0D, 0.0D, new int[] { Block.getStateId(getWorld().getBlockState(e.getPosition().down())) });
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void slimeJumpFX(Entity e, int i)
/*      */   {
/*  549 */     float f = getWorld().rand.nextFloat() * 3.1415927F * 2.0F;
/*  550 */     float f1 = getWorld().rand.nextFloat() * 0.5F + 0.5F;
/*  551 */     float f2 = MathHelper.sin(f) * i * 0.5F * f1;
/*  552 */     float f3 = MathHelper.cos(f) * i * 0.5F * f1;
/*  553 */     FXBreakingFade fx = new FXBreakingFade(getWorld(), e.posX + f2, (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0D, e.posZ + f3, Items.slime_ball, 0);
/*      */     
/*      */ 
/*  556 */     fx.setRBGColorF(0.7F, 0.0F, 1.0F);
/*  557 */     fx.setAlphaF(0.4F);
/*  558 */     fx.setParticleMaxAge((int)(66.0F / (getWorld().rand.nextFloat() * 0.9F + 0.1F)));
/*  559 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void taintLandFX(Entity e)
/*      */   {
/*  571 */     float f = getWorld().rand.nextFloat() * 3.1415927F * 2.0F;
/*  572 */     float f1 = getWorld().rand.nextFloat() * 0.5F + 0.5F;
/*  573 */     float f2 = MathHelper.sin(f) * 2.0F * 0.5F * f1;
/*  574 */     float f3 = MathHelper.cos(f) * 2.0F * 0.5F * f1;
/*  575 */     if (getWorld().isRemote) {
/*  576 */       FXBreakingFade fx = new FXBreakingFade(getWorld(), e.posX + f2, (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0D, e.posZ + f3, Items.slime_ball);
/*      */       
/*      */ 
/*  579 */       fx.setRBGColorF(0.1F, 0.0F, 0.1F);
/*  580 */       fx.setAlphaF(0.4F);
/*  581 */       fx.setParticleMaxAge((int)(66.0F / (getWorld().rand.nextFloat() * 0.9F + 0.1F)));
/*  582 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void drawInfusionParticles1(double x, double y, double z, BlockPos pos, Item id, int md)
/*      */   {
/*  589 */     FXBoreParticles fb = new FXBoreParticles(getWorld(), x, y, z, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, id, md).getObjectColor(pos);
/*      */     
/*  591 */     fb.setAlphaF(0.3F);
/*  592 */     fb.motionX = ((float)getWorld().rand.nextGaussian() * 0.03F);
/*  593 */     fb.motionY = ((float)getWorld().rand.nextGaussian() * 0.03F);
/*  594 */     fb.motionZ = ((float)getWorld().rand.nextGaussian() * 0.03F);
/*  595 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*      */   }
/*      */   
/*      */   public void drawInfusionParticles2(double x, double y, double z, BlockPos pos, Block id, int md) {
/*  599 */     FXBoreParticles fb = new FXBoreParticles(getWorld(), x, y, z, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, id, md).getObjectColor(pos);
/*      */     
/*  601 */     fb.setAlphaF(0.3F);
/*  602 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*      */   }
/*      */   
/*      */   public void drawInfusionParticles3(double x, double y, double z, int x2, int y2, int z2) {
/*  606 */     FXBoreSparkle fb = new FXBoreSparkle(getWorld(), x, y, z, x2 + 0.5D, y2 - 0.5D, z2 + 0.5D);
/*  607 */     fb.setRBGColorF(0.4F + getWorld().rand.nextFloat() * 0.2F, 0.2F, 0.6F + getWorld().rand.nextFloat() * 0.3F);
/*  608 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawInfusionParticles4(double x, double y, double z, int x2, int y2, int z2) {
/*  612 */     FXBoreSparkle fb = new FXBoreSparkle(getWorld(), x, y, z, x2 + 0.5D, y2 - 0.5D, z2 + 0.5D);
/*  613 */     fb.setRBGColorF(0.2F, 0.6F + getWorld().rand.nextFloat() * 0.3F, 0.3F);
/*  614 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawVentParticles(double x, double y, double z, double x2, double y2, double z2, int color)
/*      */   {
/*  619 */     FXVent fb = new FXVent(getWorld(), x, y, z, x2, y2, z2, color);
/*  620 */     fb.setAlphaF(0.4F);
/*  621 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawVentParticles(double x, double y, double z, double x2, double y2, double z2, int color, float scale)
/*      */   {
/*  626 */     FXVent fb = new FXVent(getWorld(), x, y, z, x2, y2, z2, color);
/*  627 */     fb.setAlphaF(0.4F);
/*  628 */     fb.setScale(scale);
/*  629 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void spark(float x, float y, float z, float size, float r, float g, float b, float a)
/*      */   {
/*  658 */     FXSpark fx = new FXSpark(getWorld(), x, y, z, size);
/*  659 */     fx.setRBGColorF(r, g, b);
/*  660 */     fx.setAlphaF(a);
/*  661 */     ParticleEngine.instance.addEffect(getWorld(), fx);
/*      */   }
/*      */   
/*      */ 
/*      */   public void smokeSpiral(double x, double y, double z, float rad, int start, int miny, int color)
/*      */   {
/*  667 */     FXSmokeSpiral fx = new FXSmokeSpiral(getWorld(), x, y, z, rad, start, miny);
/*  668 */     Color c = new Color(color);
/*  669 */     fx.setRBGColorF(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);
/*  670 */     ParticleEngine.instance.addEffect(getWorld(), fx);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void wispFX(double posX, double posY, double posZ, float f, float g, float h, float i)
/*      */   {
/*  677 */     FXWisp ef = new FXWisp(getWorld(), posX, posY, posZ, f, g, h, i);
/*  678 */     ef.setGravity(0.02F);
/*  679 */     ParticleEngine.instance.addEffect(getWorld(), ef);
/*      */   }
/*      */   
/*      */ 
/*      */   public void wispFX2(double posX, double posY, double posZ, float size, int type, boolean shrink, boolean clip, float gravity)
/*      */   {
/*  685 */     FXWisp ef = new FXWisp(getWorld(), posX, posY, posZ, size, type);
/*  686 */     ef.setGravity(gravity);
/*  687 */     ef.shrink = shrink;
/*  688 */     ef.noClip = clip;
/*  689 */     ParticleEngine.instance.addEffect(getWorld(), ef);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void wispFX3(double posX, double posY, double posZ, double posX2, double posY2, double posZ2, float size, int type, boolean shrink, float gravity)
/*      */   {
/*  697 */     FXWisp ef = new FXWisp(getWorld(), posX, posY, posZ, posX2, posY2, posZ2, size, type);
/*      */     
/*  699 */     ef.setGravity(gravity);
/*  700 */     ef.shrink = shrink;
/*  701 */     ParticleEngine.instance.addEffect(getWorld(), ef);
/*      */   }
/*      */   
/*      */ 
/*      */   public void wispFX4(double posX, double posY, double posZ, Entity target, int type, boolean shrink, float gravity)
/*      */   {
/*  707 */     FXWisp ef = new FXWisp(getWorld(), posX, posY, posZ, target, type);
/*  708 */     ef.setGravity(gravity);
/*  709 */     ef.shrink = shrink;
/*  710 */     ParticleEngine.instance.addEffect(getWorld(), ef);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void wispFX5(double posX, double posY, double posZ, double posX2, double posY2, double posZ2, float size, boolean shrink, float gravity, int color)
/*      */   {
/*  717 */     FXWisp ef = new FXWisp(getWorld(), posX, posY, posZ, posX2, posY2, posZ2, size, -1);
/*      */     
/*  719 */     Color c = new Color(color);
/*  720 */     ef.setRBGColorF(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);
/*  721 */     ef.setGravity(gravity);
/*  722 */     ef.shrink = shrink;
/*  723 */     ParticleEngine.instance.addEffect(getWorld(), ef);
/*      */   }
/*      */   
/*      */   public void wispFXEG(double posX, double posY, double posZ, Entity target)
/*      */   {
/*  728 */     for (int a = 0; a < particleCount(1); a++) {
/*  729 */       thaumcraft.client.fx.particles.FXWispEG ef = new thaumcraft.client.fx.particles.FXWispEG(getWorld(), posX, posY, posZ, target);
/*  730 */       ParticleEngine.instance.addEffect(getWorld(), ef);
/*      */     }
/*      */   }
/*      */   
/*      */   public void burst(double sx, double sy, double sz, float size)
/*      */   {
/*  736 */     FXBurst ef = new FXBurst(getWorld(), sx, sy, sz, size);
/*      */     
/*  738 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(ef);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void excavateFX(BlockPos pos, EntityLivingBase p, int progress)
/*      */   {
/*  754 */     RenderGlobal rg = Minecraft.getMinecraft().renderGlobal;
/*  755 */     rg.sendBlockBreakProgress(p.getEntityId(), pos, progress);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object beamCont(EntityLivingBase p, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact)
/*      */   {
/*  777 */     FXBeamWand beamcon = null;
/*  778 */     Color c = new Color(color);
/*  779 */     if ((input instanceof FXBeamWand))
/*  780 */       beamcon = (FXBeamWand)input;
/*  781 */     if ((beamcon == null) || (beamcon.isDead)) {
/*  782 */       beamcon = new FXBeamWand(getWorld(), p, tx, ty, tz, c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 8);
/*      */       
/*      */ 
/*  785 */       beamcon.setType(type);
/*  786 */       beamcon.setEndMod(endmod);
/*  787 */       beamcon.setReverse(reverse);
/*  788 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(beamcon);
/*      */     }
/*      */     else {
/*  791 */       beamcon.updateBeam(tx, ty, tz);
/*  792 */       beamcon.setEndMod(endmod);
/*  793 */       beamcon.impact = impact;
/*      */     }
/*  795 */     return beamcon;
/*      */   }
/*      */   
/*      */ 
/*      */   public Object beamBore(double px, double py, double pz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact)
/*      */   {
/*  801 */     FXBeamBore beamcon = null;
/*  802 */     Color c = new Color(color);
/*  803 */     if ((input instanceof FXBeamBore))
/*  804 */       beamcon = (FXBeamBore)input;
/*  805 */     if ((beamcon == null) || (beamcon.isDead)) {
/*  806 */       beamcon = new FXBeamBore(getWorld(), px, py, pz, tx, ty, tz, c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 8);
/*      */       
/*  808 */       beamcon.setType(type);
/*  809 */       beamcon.setEndMod(endmod);
/*  810 */       beamcon.setReverse(reverse);
/*  811 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(beamcon);
/*      */     } else {
/*  813 */       beamcon.updateBeam(tx, ty, tz);
/*  814 */       beamcon.setEndMod(endmod);
/*  815 */       beamcon.impact = impact;
/*      */     }
/*  817 */     return beamcon;
/*      */   }
/*      */   
/*      */   public void boreDigFx(int x, int y, int z, int x2, int y2, int z2, Block bi, int md)
/*      */   {
/*  822 */     if (getWorld().rand.nextInt(8) == 0) {
/*  823 */       FXBoreSparkle fb = new FXBoreSparkle(getWorld(), x + getWorld().rand.nextFloat(), y + getWorld().rand.nextFloat(), z + getWorld().rand.nextFloat(), x2 + 0.5D, y2 + 0.5D, z2 + 0.5D);
/*      */       
/*      */ 
/*  826 */       ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */     } else {
/*  828 */       FXBoreParticles fb = new FXBoreParticles(getWorld(), x + getWorld().rand.nextFloat(), y + getWorld().rand.nextFloat(), z + getWorld().rand.nextFloat(), x2 + 0.5D, y2 + 0.5D, z2 + 0.5D, bi, md);
/*      */       
/*      */ 
/*      */ 
/*  832 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*      */     }
/*      */   }
/*      */   
/*      */   public void essentiaTrailFx(BlockPos p1, BlockPos p2, int count, int color, float scale, int ext)
/*      */   {
/*  838 */     FXEssentiaTrail2 fb = new FXEssentiaTrail2(getWorld(), p1.getX() + 0.5D, p1.getY() + 0.5D, p1.getZ() + 0.5D, p2.getX() + 0.5D, p2.getY() + 0.5D, p2.getZ() + 0.5D, count, color, scale, ext);
/*      */     
/*  840 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void essentiaDropFx(double x, double y, double z, float r, float g, float b, float alpha)
/*      */   {
/*  845 */     FXGeneric fb = new FXGeneric(getWorld(), x, y, z, getWorld().rand.nextGaussian() * 0.004999999888241291D, getWorld().rand.nextGaussian() * 0.004999999888241291D, getWorld().rand.nextGaussian() * 0.004999999888241291D);
/*      */     
/*      */ 
/*      */ 
/*  849 */     fb.setMaxAge(20 + getWorld().rand.nextInt(10), 0);
/*  850 */     fb.setRBGColorF(r, g, b);
/*  851 */     fb.setAlphaF(alpha);
/*  852 */     fb.setLoop(false);
/*  853 */     fb.setParticles(25, 1, 1);
/*  854 */     fb.setScale(0.4F + getWorld().rand.nextFloat() * 0.2F, 0.2F);
/*  855 */     fb.setLayer(1);
/*  856 */     fb.setGravity(0.01F);
/*  857 */     fb.setRotationSpeed(0.0F);
/*  858 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void jarSplashFx(double x, double y, double z) {
/*  862 */     FXGeneric fb = new FXGeneric(getWorld(), x + getWorld().rand.nextGaussian() * 0.10000000149011612D, y, z + getWorld().rand.nextGaussian() * 0.10000000149011612D, getWorld().rand.nextGaussian() * 0.014999999664723873D, getWorld().rand.nextFloat() * 0.05F, getWorld().rand.nextGaussian() * 0.014999999664723873D);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  869 */     fb.setMaxAge(20 + getWorld().rand.nextInt(10), 0);
/*  870 */     Color c = new Color(2650102);
/*  871 */     fb.setRBGColorF(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);
/*  872 */     fb.setAlphaF(0.5F);
/*  873 */     fb.setLoop(false);
/*  874 */     fb.setParticles(25, 1, 1);
/*  875 */     fb.setScale(0.4F + getWorld().rand.nextFloat() * 0.2F);
/*  876 */     fb.setLayer(1);
/*  877 */     fb.setGravity(0.1F);
/*  878 */     fb.noClip = false;
/*  879 */     fb.setRotationSpeed(0.0F);
/*  880 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void waterTrailFx(BlockPos p1, BlockPos p2, int count, int color, float scale) {
/*  884 */     FXEssentiaTrail2 fb = new FXEssentiaTrail2(getWorld(), p1.getX() + 0.5D, p1.getY() + 0.66D, p1.getZ() + 0.5D, p2.getX() + 0.5D, p2.getY() + 0.5D, p2.getZ() + 0.5D, count, color, scale, 0);
/*      */     
/*      */ 
/*  887 */     fb.motionY += 0.2D;
/*  888 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void furnaceLavaFx(int x, int y, int z, int facingX, int facingZ) {
/*  892 */     net.minecraft.client.particle.EntityFX fb = new EntityLavaFX.Factory().getEntityFX(0, getWorld(), x + 0.5F + (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.3F + facingX * 1.0F, y + 0.3F, z + 0.5F + (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.3F + facingZ * 1.0F, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  897 */     fb.motionY = (0.2F * getWorld().rand.nextFloat());
/*  898 */     float qx = facingX == 0 ? (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.5F : facingX * getWorld().rand.nextFloat();
/*      */     
/*  900 */     float qz = facingZ == 0 ? (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.5F : facingZ * getWorld().rand.nextFloat();
/*      */     
/*  902 */     fb.motionX = (0.15F * qx);
/*  903 */     fb.motionZ = (0.15F * qz);
/*  904 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*      */   }
/*      */   
/*      */   public void blockRunes(double x, double y, double z, float r, float g, float b, int dur, float grav) {
/*  908 */     FXBlockRunes fb = new FXBlockRunes(getWorld(), x + 0.5D, y + 0.5D, z + 0.5D, r, g, b, dur);
/*  909 */     fb.setGravity(grav);
/*  910 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void drawSlash(double x, double y, double z, double x2, double y2, double z2, int dur) {
/*  914 */     FXPlane fb = new FXPlane(getWorld(), x, y, z, x2, y2, z2, dur);
/*  915 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */   
/*      */   public void blockWard(double x, double y, double z, EnumFacing side, float f, float f1, float f2)
/*      */   {
/*  920 */     FXBlockWard fb = new FXBlockWard(getWorld(), x + 0.5D, y + 0.5D, z + 0.5D, side, f, f1, f2);
/*  921 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*      */   }
/*      */   
/*      */   public Object swarmParticleFX(Entity targetedEntity, float f1, float f2, float pg) {
/*  925 */     thaumcraft.client.fx.particles.FXSwarm fx = new thaumcraft.client.fx.particles.FXSwarm(getWorld(), targetedEntity.posX + (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 2.0F, targetedEntity.posY + (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 2.0F, targetedEntity.posZ + (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 2.0F, targetedEntity, 0.8F + getWorld().rand.nextFloat() * 0.2F, getWorld().rand.nextFloat() * 0.4F, 1.0F - getWorld().rand.nextFloat() * 0.2F, f1, f2, pg);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  933 */     ParticleEngine.instance.addEffect(getWorld(), fx);
/*  934 */     return fx;
/*      */   }
/*      */   
/*      */   public void bottleTaintBreak(double x, double y, double z)
/*      */   {
/*  939 */     for (int k1 = 0; k1 < 8; k1++)
/*      */     {
/*  941 */       getWorld().spawnParticle(EnumParticleTypes.ITEM_CRACK, x, y, z, getWorld().rand.nextGaussian() * 0.15D, getWorld().rand.nextDouble() * 0.2D, getWorld().rand.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem(thaumcraft.api.items.ItemsTC.bottleTaint) });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  947 */     getWorld().playSound(x, y, z, "game.potion.smash", 1.0F, getWorld().rand.nextFloat() * 0.1F + 0.9F, false);
/*      */   }
/*      */   
/*      */   public void arcLightning(double x, double y, double z, double tx, double ty, double tz, float r, float g, float b, float h) {
/*  951 */     FXSparkle ef2 = new FXSparkle(getWorld(), tx, ty, tz, tx, ty, tz, 3.0F, 6, 2);
/*  952 */     ef2.setGravity(0.0F);
/*  953 */     ef2.noClip = true;
/*  954 */     ef2.setRBGColorF(r, g, b);
/*  955 */     ParticleEngine.instance.addEffect(getWorld(), ef2);
/*  956 */     thaumcraft.client.fx.beams.FXArc efa = new thaumcraft.client.fx.beams.FXArc(getWorld(), x, y, z, tx, ty, tz, r, g, b, h);
/*  957 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(efa);
/*      */   }
/*      */   
/*      */   public void arcBolt(double x, double y, double z, double tx, double ty, double tz, float r, float g, float b) {
/*  961 */     FXSparkle ef2 = new FXSparkle(getWorld(), tx, ty, tz, tx, ty, tz, 3.0F, 6, 2);
/*  962 */     ef2.setGravity(0.0F);
/*  963 */     ef2.noClip = true;
/*  964 */     ef2.setRBGColorF(r, g, b);
/*  965 */     ParticleEngine.instance.addEffect(getWorld(), ef2);
/*  966 */     FXBolt efa = new FXBolt(getWorld(), x, y, z, tx, ty, tz, r, g, b);
/*  967 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(efa);
/*      */   }
/*      */   
/*      */   public void infusedStoneSparkle(BlockPos pos, int md) {
/*  971 */     int color = 0;
/*  972 */     switch (md) {
/*  973 */     case 1:  color = 1; break;
/*  974 */     case 2:  color = 4; break;
/*  975 */     case 3:  color = 2; break;
/*  976 */     case 4:  color = 3; break;
/*  977 */     case 5:  color = 6; break;
/*  978 */     case 6:  color = 5; break;
/*  979 */     case 7:  color = 0;
/*      */     }
/*  981 */     for (int a = 0; a < particleCount(3); a++) {
/*  982 */       FXSparkle fx = new FXSparkle(getWorld(), pos.getX() + getWorld().rand.nextFloat(), pos.getY() + getWorld().rand.nextFloat(), pos.getZ() + getWorld().rand.nextFloat(), 1.75F, color == -1 ? getWorld().rand.nextInt(5) : color, 3 + getWorld().rand.nextInt(3));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  988 */       fx.setGravity(0.1F);
/*  989 */       ParticleEngine.instance.addEffect(getWorld(), fx);
/*      */     }
/*      */   }
/*      */   
/*      */   public void cultistSpawn(double x, double y, double z, double a, double b, double c) {
/*  994 */     FXGeneric fb = new FXGeneric(getWorld(), x, y, z, a, b, c);
/*  995 */     fb.setMaxAge(10 + getWorld().rand.nextInt(10), 0);
/*  996 */     fb.setRBGColorF(1.0F, 1.0F, 1.0F, 0.6F, 0.0F, 0.0F);
/*  997 */     fb.setAlphaF(0.8F);
/*  998 */     fb.setGridSize(16);
/*  999 */     fb.setParticles(160, 6, 1);
/* 1000 */     fb.setScale(3.0F + getWorld().rand.nextFloat() * 2.0F);
/* 1001 */     fb.setLayer(3);
/* 1002 */     ParticleEngine.instance.addEffect(getWorld(), fb);
/*      */   }
/*      */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\FXDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */