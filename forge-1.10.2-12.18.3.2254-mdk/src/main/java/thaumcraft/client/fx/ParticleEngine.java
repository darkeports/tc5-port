/*     */ package thaumcraft.client.fx;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ParticleEngine
/*     */ {
/*  34 */   public static ParticleEngine instance = new ParticleEngine();
/*     */   
/*  36 */   public static final ResourceLocation particleTexture = new ResourceLocation("thaumcraft", "textures/misc/particles.png");
/*     */   
/*  38 */   public static final ResourceLocation particleTexture2 = new ResourceLocation("thaumcraft", "textures/misc/particles2.png");
/*     */   
/*     */ 
/*     */   protected World worldObj;
/*     */   
/*  43 */   private HashMap<Integer, ArrayList<Particle>>[] particles = { new HashMap(), new HashMap(), new HashMap(), new HashMap() };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  49 */   private Random rand = new Random();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onRenderWorldLast(RenderWorldLastEvent event) {
/*  54 */     float frame = event.partialTicks;
/*  55 */     Entity entity = Minecraft.getMinecraft().thePlayer;
/*  56 */     TextureManager renderer = Minecraft.getMinecraft().renderEngine;
/*  57 */     int dim = Minecraft.getMinecraft().theWorld.provider.getDimension();
/*     */     
/*  59 */     renderer.bindTexture(particleTexture2);
/*     */     
/*  61 */     GL11.glPushMatrix();
/*     */     
/*  63 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  64 */     GlStateManager.enableBlend();
/*  65 */     GL11.glEnable(3042);
/*  66 */     GL11.glAlphaFunc(516, 0.003921569F);
/*  67 */     GlStateManager.depthMask(false);
/*     */     
/*  69 */     boolean rebound = false;
/*  70 */     for (int layer = 3; layer >= 0; layer--)
/*  71 */       if (this.particles[layer].containsKey(Integer.valueOf(dim))) {
/*  72 */         ArrayList<Particle> parts = (ArrayList)this.particles[layer].get(Integer.valueOf(dim));
/*  73 */         if (parts.size() != 0)
/*     */         {
/*  75 */           if ((!rebound) && (layer < 2)) {
/*  76 */             renderer.bindTexture(particleTexture);
/*  77 */             rebound = true;
/*     */           }
/*     */           
/*  80 */           GL11.glPushMatrix();
/*     */           
/*  82 */           switch (layer) {
/*     */           case 0: case 2: 
/*  84 */             GlStateManager.blendFunc(770, 1);
/*  85 */             break;
/*     */           case 1: case 3: 
/*  87 */             GlStateManager.blendFunc(770, 771);
/*     */           }
/*     */           
/*     */           
/*  91 */           float f1 = ActiveRenderInfo.getRotationX();
/*  92 */           float f2 = ActiveRenderInfo.getRotationZ();
/*  93 */           float f3 = ActiveRenderInfo.getRotationYZ();
/*  94 */           float f4 = ActiveRenderInfo.getRotationXY();
/*  95 */           float f5 = ActiveRenderInfo.getRotationXZ();
/*  96 */           Particle.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
/*  97 */           Particle.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
/*  98 */           Particle.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;
/*     */           
/* 100 */           Tessellator tessellator = Tessellator.getInstance();
/* 101 */           VertexBuffer worldrenderer = tessellator.getBuffer();
/* 102 */           worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */           
/* 104 */           for (int j = 0; j < parts.size(); j++) {
/* 105 */             final Particle entityfx = (Particle)parts.get(j);
/* 106 */             if (entityfx != null) {
/*     */               try
/*     */               {
/* 109 */                 entityfx.renderParticle(worldrenderer, entity, frame, f1, f5, f2, f3, f4);
/*     */               } catch (Throwable throwable) {
/* 111 */                 CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
/* 112 */                 CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
/* 113 */                 crashreportcategory.addCrashSectionCallable("Particle", new Callable()
/*     */                 {
/*     */                   public String call() {
/* 116 */                     return entityfx.toString();
/*     */                   }
/* 118 */                 });
/* 119 */                 crashreportcategory.addCrashSectionCallable("Particle Type", new Callable()
/*     */                 {
/*     */                   public String call() {
/* 122 */                     return "ENTITY_PARTICLE_TEXTURE";
/*     */                   }
/* 124 */                 });
/* 125 */                 throw new ReportedException(crashreport);
/*     */               }
/*     */             }
/*     */           }
/* 129 */           tessellator.draw();
/*     */           
/* 131 */           GL11.glPopMatrix();
/*     */         }
/*     */       }
/* 134 */     GlStateManager.depthMask(true);
/* 135 */     GlStateManager.blendFunc(770, 771);
/* 136 */     GlStateManager.disableBlend();
/* 137 */     GlStateManager.alphaFunc(516, 0.1F);
/* 138 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void addEffect(World world, Particle fx) {
/* 142 */     if (!this.particles[fx.getFXLayer()].containsKey(Integer.valueOf(world.provider.getDimension()))) {
/* 143 */       this.particles[fx.getFXLayer()].put(Integer.valueOf(world.provider.getDimension()), new ArrayList());
/*     */     }
/* 145 */     ArrayList<Particle> parts = (ArrayList)this.particles[fx.getFXLayer()].get(Integer.valueOf(world.provider.getDimension()));
/* 146 */     if (parts.size() >= 2000) {
/* 147 */       parts.remove(0);
/*     */     }
/* 149 */     parts.add(fx);
/* 150 */     this.particles[fx.getFXLayer()].put(Integer.valueOf(world.provider.getDimension()), parts);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void updateParticles(TickEvent.ClientTickEvent event) {
/* 156 */     if (event.side == Side.SERVER) return;
/* 157 */     Minecraft mc = FMLClientHandler.instance().getClient();
/* 158 */     World world = mc.theWorld;
/* 159 */     if (mc.theWorld == null) return;
/* 160 */     int dim = world.provider.getDimension();
/* 161 */     if (event.phase == TickEvent.Phase.START) {
/* 162 */       for (int layer = 0; layer < 4; layer++) {
/* 163 */         if (this.particles[layer].containsKey(Integer.valueOf(dim))) {
/* 164 */           ArrayList<Particle> parts = (ArrayList)this.particles[layer].get(Integer.valueOf(dim));
/*     */           
/* 166 */           for (int j = 0; j < parts.size(); j++) {
/* 167 */             final Particle entityfx = (Particle)parts.get(j);
/*     */             try
/*     */             {
/* 170 */               if (entityfx != null) {
/* 171 */                 entityfx.onUpdate();
/*     */               }
/*     */             } catch (Throwable throwable) {
/* 174 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
/*     */               
/* 176 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
/*     */               
/* 178 */               crashreportcategory.addCrashSectionCallable("Particle", new Callable()
/*     */               {
/*     */                 public String call() {
/* 181 */                   return entityfx.toString();
/*     */                 }
/* 183 */               });
/* 184 */               crashreportcategory.addCrashSectionCallable("Particle Type", new Callable()
/*     */               {
/*     */                 public String call() {
/* 187 */                   return "ENTITY_PARTICLE_TEXTURE";
/*     */                 }
/* 189 */               });
/* 190 */               throw new ReportedException(crashreport);
/*     */             }
/*     */             
/* 193 */             if ((entityfx == null) || (entityfx.isDead)) {
/* 194 */               parts.remove(j--);
/* 195 */               this.particles[layer].put(Integer.valueOf(dim), parts);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\ParticleEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */